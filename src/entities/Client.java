package entities;

import java.io.*;
import java.net.*;

public class Client {
	private static final String SERVER_ADDRESS = "YOURIPHERE";
	private static final int SERVER_PORT = 6789;

	public static void main(String[] args) {
		try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

			String serverResponse;
			while ((serverResponse = in.readLine()) != null) {
				System.out.println(serverResponse);
				if (serverResponse.startsWith("Informe seu nome:")) {

					String clientName = userInput.readLine();
					out.println(clientName);
				} else {
					String message = userInput.readLine();
					out.println(message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
