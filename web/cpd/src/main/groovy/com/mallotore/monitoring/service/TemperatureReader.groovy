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

class TemperatureReader implements SerialPortEventListener {

	static Logger LOG = LoggerFactory.getLogger(TemperatureReader.class)

	SerialPort serialPort

	static final PORT_NAMES = [
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
                        "/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	]
	private BufferedReader input
	private OutputStream output
	private static final int TIME_OUT = 2000
	private static final int DATA_RATE = 9600
	private TemperatureRepository temperatureRepository
	private int intervalInSeconds

	public void setRepository(TemperatureRepository temperatureRepository){
		this.temperatureRepository = temperatureRepository
	}

	public void setInterval(int intervalInSeconds){
		this.intervalInSeconds = intervalInSeconds
	}

	public void initialize() {
        withPortId(){ portId ->
        	try {
				serialPort = (SerialPort) portId.open(this.getClass().getName(),TIME_OUT);
				serialPort.setSerialPortParams(DATA_RATE,
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE)
				input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()))
				output = serialPort.getOutputStream()
				serialPort.addEventListener(this)
				serialPort.notifyOnDataAvailable(true)
			} catch (Exception e) {
				LOG.error("Unhandled exception initializing serial port listener",e)
			}
        }
	}

	private withPortId(Closure closure){
		System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0")
		def portId = findAvailablePort()
		if(portId)
			closure(portId)
		else
			LOG.error("Could not find COM port.")
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
			input.close()
			output.close()
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String temperature = input.readLine()
				temperatureRepository.save(new Temperature(temperature:temperature))
			} catch (Exception e) {
				LOG.error("Unhandled exception on serial port event",e)
			}
		}
	}

	public synchronized void writeData(String data) {
	    LOG.info("Sent: ${data}")
	    try {
	        output.write(data.getBytes())
	    } catch (Exception e) {
	        LOG.error("Unhandled exception writing to port", e)
	    }
	}
}