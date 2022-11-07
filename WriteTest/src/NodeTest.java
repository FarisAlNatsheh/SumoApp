import java.util.Scanner;

public class NodeTest {
	public static void main(String[] args) {
		Node s = new Node();
		//new AdminWindow("127.0.0.1","127.0.0.1");
		s.startSendSocket("127.0.0.1", 5001);
		Scanner in = new Scanner(System.in);
		while(true)
			s.sendMessage(in.next());
	}
}
