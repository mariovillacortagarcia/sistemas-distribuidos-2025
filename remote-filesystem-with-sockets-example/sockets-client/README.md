# Sockets Client

Client application that sends data to a server through use cases following Hexagonal Architecture.

## Architecture

This client implements the **Ports and Adapters** pattern (Hexagonal Architecture) with clear separation of concerns:

### Layers

```
client/
├── SocketsClient.java              # Main entry point
├── app/                             # Application layer (business logic)
│   ├── application/
│   │   └── SendFileUseCase.java    # Use case: sends file content
│   ├── domain/
│   │   ├── File.java               # Domain entity
│   │   └── FilePort.java           # Port interface for file operations
│   └── infrastructure/
│       └── ConsoleFileRepository.java # Reads from console input
└── middleware/                      # Middleware layer (socket communication)
    ├── domain/
    │   ├── ClientPort.java         # Abstract client port
    │   └── UseCase.java            # Use case interface
    └── infrastructure/
        ├── TCPClientAdapter.java   # TCP implementation
        └── UDPClientAdapter.java   # UDP implementation
```

## Diagrams

### Class Diagram

![Architecture](architecture.mmd)

Full UML class diagram showing all classes, interfaces, relationships, and architectural layers.

**View:** [`architecture.mmd`](architecture.mmd)

### Sequence Diagram

![Sequence](sequence.mmd)

Complete execution flow from user input through socket communication to server.

**View:** [`sequence.mmd`](sequence.mmd)

## Key Components

### Middleware Layer

- **ClientPort** (abstract): Defines the contract for client implementations
- **UseCase** (interface): Contract for data retrieval logic
- **TCPClientAdapter**: Connection-oriented, reliable socket communication
- **UDPClientAdapter**: Connectionless, lightweight socket communication

### Application Layer

- **File** (domain entity): Represents a file with content
- **FilePort** (interface): Input port for file retrieval
- **SendFileUseCase**: Coordinates retrieving and sending file content
- **ConsoleFileRepository**: Reads user input from console

## How to Run

### Build

```bash
mvn clean compile
```

### Run (TCP mode - default)

```bash
mvn exec:java
```

You will be prompted to enter content:

```
Enter content for 'message': Hello Server!
[TCP] Connecting to localhost:1234
[TCP] Connected to 127.0.0.1:1234
[TCP] Sent: Hello Server!
[TCP] Connection closed
```

### Switch to UDP mode

Edit `SocketsClient.java` and uncomment the UDP line:

```java
// final ClientPort client = new TCPClientAdapter(HOST, PORT, sendFileUseCase);
final ClientPort client = new UDPClientAdapter(HOST, PORT, sendFileUseCase);
```

Then rebuild and run.

## Configuration

### Change Server Connection

Edit `SocketsClient.java`:

```java
private static final String HOST = "localhost";  // Server address
private static final int PORT = 1234;            // Server port
```

## Protocol Characteristics

### TCP (Default)

- Connection-oriented
- Reliable delivery
- Maintains order
- Stream-based
- Automatically retries on failure

### UDP (Alternative)

- Connectionless
- Fire-and-forget
- No ordering guarantee
- Datagram-based
- Faster but less reliable

## Usage Examples

### Send a simple message

```bash
mvn exec:java
# When prompted: Hello from client!
```

### Send JSON data

```bash
mvn exec:java
# When prompted: {"name":"John","message":"Hello"}
```

### Send multiple lines (TCP only)

Run the client multiple times, or modify the use case to send multiple lines.

## Extending

### Add a New Use Case

1. Create a class implementing `UseCase` interface
2. Inject dependencies in constructor
3. Pass to client adapter in main class

### Add File-based Input

Replace `ConsoleFileRepository` with file system implementation:

```java
public class FileSystemRepository implements FilePort {
    @Override
    public File getFileFromSrc(String src) {
        String content = Files.readString(Path.of(src));
        return new File(content);
    }
}
```

Then change the source parameter:

```java
final UseCase sendFileUseCase = new SendFileUseCase(filePort, "input.txt");
```

### Add Multiple Messages

Modify the client to send data in a loop:

```java
for (int i = 0; i < 5; i++) {
    final UseCase sendFileUseCase = new SendFileUseCase(filePort, "message-" + i);
    final ClientPort client = new TCPClientAdapter(HOST, PORT, sendFileUseCase);
    client.start();
}
```

## Dependencies

- Java 24 (or compatible version)
- Maven 3.x
- No external libraries (uses only Java standard library)

## Prerequisites

Before running the client, ensure:

1. The `sockets-server` is running
2. The server is listening on the configured port
3. Network connectivity is available (localhost or remote)

## Troubleshooting

### Connection Refused

- Ensure the server is running
- Check that port numbers match between client and server
- Verify firewall settings

### No Data Received on Server

- Ensure both client and server use the same protocol (TCP or UDP)
- Check that data is being flushed (TCP) or sent properly (UDP)

### UDP Packets Lost

- This is normal behavior for UDP
- Consider using TCP if reliability is required
- Or implement application-level acknowledgments

## Author

@mariovillacortagarcia
