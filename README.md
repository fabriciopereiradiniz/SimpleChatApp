# SimpleChatApp

SimpleChatApp is a basic chat application that enables communication between clients and a server. It's a simple Java chat implementation.

## Features
- Client-server connection.
- Sending messages between clients and the server.
- Simulation of message delivery with ACK (Acknowledgment) and NACK (Negative Acknowledgment).

## Prerequisites
- Java (JDK) installed on your system.
- How to Use

## How to Use
1. Clone the repository:
```bash
git clone https://github.com/your-username/SimpleChatApp.git
```

2. Compile the server code:
```bash
javac entities/Server.java
```

3. Compile the client code:
```bash
javac entities/Client.java
```

4. Run the server:
```bash
java entities.Server
```

5. Run one or more clients:
```bash
java entities.Client
```

6. Follow the instructions to provide a name for the client and start sending messages.
Note: Ensure you replace `"YOURIPHERE"` with the actual IP address of the server in the `private static final String SERVER_ADDRESS = "YOURIPHERE"`; line in the `Client.java` file.
