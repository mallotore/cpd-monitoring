package com.mallotore.monitoring.service

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import gnu.io.CommPortIdentifier
import gnu.io.SerialPort
import gnu.io.SerialPortEvent
import gnu.io.SerialPortEventListener
import java.util.Enumeration

class TemperatureReader implements SerialPortEventListener {
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

	public void initialize() {
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0")
		CommPortIdentifier portId = null
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers()
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement()
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId
					break
				}
			}
		}
		if (portId == null) {
			println("Could not find COM port.")
			return
		}

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
			println(e.toString())
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener()
			serialPort.close()
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=input.readLine()
				println(inputLine)
			} catch (Exception e) {
				println(e.toString())
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}
}