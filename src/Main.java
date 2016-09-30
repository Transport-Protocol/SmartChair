import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Main {
	public static void main(String... args) {
		ConnectionFactory factory = new ConnectionFactory();
		try {
			
			Connection conn = factory.newConnection();
			Channel channel = conn.createChannel();
			
			byte[] messageBodyBytes = "Hello, world!".getBytes();
			channel.basicPublish("my-exchange", "my-routing-key", null, messageBodyBytes);
			
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
