package entities;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private static final int PORT = 6789;
	private static List<ClientHandler> clientHandlers = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("Servidor aguardando conexões na porta " + PORT + "...");
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				ClientHandler clientHandler = new ClientHandler(clientSocket);
				clientHandlers.add(clientHandler);
				clientHandler.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ClientHandler extends Thread {
		private Socket socket;
		private PrintWriter out;
		private BufferedReader in;
		private String clientName;

		public ClientHandler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				out.println("Informe seu nome:");
				clientName = in.readLine();
				out.println("Bem-vindo, " + clientName + "!");

				String message;
				while ((message = in.readLine()) != null) {
					System.out.println("Recebido de " + clientName + ": " + message);

					// Simula a verificação de entrega
					boolean messageDelivered = simulateMessageDelivery();

					// Verificar se a mensagem foi entregue com sucesso
					if (messageDelivered) {
						// Enviar ACK de volta para o cliente
						out.println("ACK recebido de " + clientName);
						out.flush(); // Força a saída imediatamente
						System.out.println("ACK para " + clientName + ": Mensagem entregue com sucesso!");

						// Enviar a mensagem para todos os clientes, incluindo o remetente
						synchronized (clientHandlers) {
							for (ClientHandler handler : clientHandlers) {
								handler.sendMessage("Mensagem de " + clientName + ": " + message);
							}
						}
					} else {
						// Enviar NACK de volta para o cliente
						out.println("NACK recebido de " + clientName);
						out.flush(); // Força a saída imediatamente
						System.out.println("NACK para " + clientName + ": Problema na entrega da mensagem.");
					}
				}
			} catch (IOException e) {	
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				synchronized (clientHandlers) {
					clientHandlers.remove(this);
				}
			}
		}

		public void sendMessage(String message) {
			out.println(message);
			out.flush();
		}

		private boolean simulateMessageDelivery() {
			// 80% entrega!
			return Math.random() < 0.8;
		}
	}
}
