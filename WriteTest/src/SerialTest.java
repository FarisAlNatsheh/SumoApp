import java.util.Scanner;
import com.fazecast.jSerialComm.*;


public class SerialTest{
	String portName;
	SerialPort mainPort;
	public SerialTest(String portName) {
		this.portName = portName;
		findPort();
	}
	public void findPort() {
		SerialPort[] ports = SerialPort.getCommPorts();
		for (SerialPort port: ports) 
			System.out.println(port.getSystemPortName());
		for (SerialPort port: ports) {
			if(port.getSystemPortName().equals(portName)) {
				mainPort = port;
				System.out.println("Port found");
				command((byte)101);
				return;
			}
		}
		System.out.println("Port not found, exiting...");
		System.exit(0);
	}
//	public static void main(String args[]) {
//		new SerialTest("COM23");
//		//23
//		//27
//
//	}
	public void command(byte command) {

		SerialPort userPort = mainPort;

		//Initializing port
		userPort.openPort();
		if (userPort.isOpen()) {
			System.out.println("Port initialized");
			userPort.setBaudRate(9600);
			//timeout not needed for event based reading
			//userPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
		} else {
			System.out.println("Port not available");
			return;
		}
		userPort.writeBytes(new byte[] {98}, 1);
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.println("enter command");
			userPort.writeBytes(new byte[] {in.nextByte()}, 1);
		}

	}
	public static void main(String[] args) {
		new SerialTest("COM7");
	}
}