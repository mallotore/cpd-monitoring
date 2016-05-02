package com.mallotore.monitoring.service

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import gnu.io.CommPortIdentifier
import gnu.io.SerialPort
import gnu.io.SerialPortEvent
import gnu.io.SerialPortEventListener
import java.util.Enumeration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.mallotore.monitoring.repository.TemperatureRepository
import com.mallotore.monitoring.model.Temperature
import com.mallotore.configuration.TemperatureConfiguration
import com.mallotore.monitoring.alert.AlertSenderService

class TemperatureReader implements SerialPortEventListener {

	static Logger LOG = LoggerFactory.getLogger(TemperatureReader.class)

	SerialPort serialPort

	static final PORT_NAMES = [
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
                        "/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	]
	private BufferedReader inputReader
	private OutputStream outputStream
	private static final int TIME_OUT = 2000
	private static final int DATA_RATE = 9600
	private TemperatureRepository temperatureRepository
	private TemperatureConfiguration configuration
	private AlertSenderService alertSenderService

	public TemperatureReader(TemperatureRepository temperatureRepository, TemperatureConfiguration configuration, AlertSenderService alertSenderService){
		this.temperatureRepository = temperatureRepository
		this.configuration = configuration
		this.alertSenderService = alertSenderService
	}

	public void updateConfiguration(TemperatureConfiguration configuration){
		this.configuration = configuration	
	}

	public void initialize() {
        withPortId(){ portId ->
        	try {
				serialPort = (SerialPort) portId.open(this.getClass().getName(),TIME_OUT);
				serialPort.setSerialPortParams(DATA_RATE,
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE)
				inputReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()))
				outputStream = serialPort.getOutputStream()
				serialPort.addEventListener(this)
				serialPort.notifyOnDataAvailable(true)
			} catch (Exception e) {
				sendConnectivityAlertIfActivated("Problema de conectividad con el sensor de temperatura. Inicializando el listener del puerto serial")
				LOG.error("Unhandled exception initializing serial port listener",e)
			}
        }
	}

	private withPortId(Closure closure){
		System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0")
		def portId = findAvailablePort()
		if(portId){
			LOG.info("Connected to COM port ${portId.getName()}")
			closure(portId)
		}
		else{
			sendConnectivityAlertIfActivated("Problema de conectividad con el sensor de temperatura. No se puede encontrar el puerto COM")
			LOG.error("Could not find COM port.")
		}
	}

	private findAvailablePort(){
		for(def portName in PORT_NAMES){

			def portId = CommPortIdentifier.getPortIdentifiers().find{ 
				it.getName().equals(portName) 
			}

			if(portId) return portId
		}
	}

	public synchronized void close() {
		if (serialPort) {
			serialPort.removeEventListener()
			serialPort.close()
			inputReader.close()
			outputStream.close()
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String temperature = inputReader.readLine()
				def currentTemperature = Float.parseFloat(temperature)
				if(configuration.overTemperatureAlert && currentTemperature > configuration.overTemperatureAlert){
					alertSenderService.send("Peligro de temperatura. Actualmente es ${currentTemperature}")
				}
				temperatureRepository.save(new Temperature(temperature:temperature))
			} catch (Exception e) {
				sendConnectivityAlertIfActivated("Problema de conectividad con el sensor de temperatura. Error al tratar el evento del puerto serial")
				LOG.error("Unhandled exception on serial port event",e)
			}
		}
	}

	public synchronized void writeData(int intervalInSeconds) {
		def intervalInMilliSeconds = convertToMilliseconds(intervalInSeconds)
	    LOG.info("Sent: ${intervalInMilliSeconds}")
	    try {
	        outputStream.write(intervalInMilliSeconds.getBytes())
	    } catch (Exception e) {
	    	sendConnectivityAlertIfActivated("Problema de conectividad con el sensor de temperatura. Error al enviar el nuevo intervalo de sondeo")
	        LOG.error("Unhandled exception writing to port", e)
	    }
	}

	private sendConnectivityAlertIfActivated(message){
		if(configuration.connectivityAlert){
			alertSenderService.send(message)
		}
	}

	private convertToMilliseconds(intervalInSeconds){
		"${intervalInSeconds * 1000}"
	}
}