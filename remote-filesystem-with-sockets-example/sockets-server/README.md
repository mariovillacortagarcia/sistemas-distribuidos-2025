# Sockets Server

Server application that receives data from clients and processes it through use cases following Hexagonal Architecture.

## Architecture

This server implements the **Ports and Adapters** pattern (Hexagonal Architecture) with clear separation of concerns:

### Layers

```
server/
├── SocketsServer.java              # Main entry point
├── app/                             # Application layer (business logic)
│   ├── application/
│   │   └── SaveFileUseCase.java    # Use case: saves received files
│   ├── domain/
│   │   ├── File.java               # Domain entity
│   │   └── FilePort.java           # Port interface for file operations
│   └── infrastructure/
│       └── MockFileRepository.java # Mock implementation (prints to console)
└── middleware/                      # Middleware layer (socket communication)
    ├── domain/
    │   ├── ServerPort.java         # Abstract server port
    │   └── UseCase.java            # Use case interface
    └── infrastructure/
        ├── TCPServerAdapter.java   # TCP implementation
        └── UDPServerAdapter.java   # UDP implementation
```

## Diagrams

### Class Diagram

![Architecture](architecture.mmd)

Full UML class diagram showing all classes, interfaces, relationships, and architectural layers.

**View:** [`architecture.mmd`](architecture.mmd)

### Sequence Diagram

![Sequence](sequence.mmd)

Complete execution flow from server startup through client connection and data processing.

**View:** [`sequence.mmd`](sequence.mmd)

## Key Components

### Middleware Layer

- **ServerPort** (abstract): Defines the contract for server implementations
- **UseCase** (interface): Contract for business logic execution
- **TCPServerAdapter**: Connection-oriented, reliable socket communication
- **UDPServerAdapter**: Connectionless, lightweight socket communication

### Application Layer

- **File** (domain entity): Represents a file with content
- **FilePort** (interface): Output port for file persistence
- **SaveFileUseCase**: Coordinates saving files received from clients
- **MockFileRepository**: Console-based mock implementation for testing

## How to Run

### Build

```bash
mvn clean compile
```

### Run (TCP mode - default)

```bash
mvn exec:java
```

Expected output:

```
[TCP] Server listening on port 1234
```

### Switch to UDP mode

Edit `SocketsServer.java` and uncomment the UDP line:

```java
// final ServerPort server = new TCPServerAdapter(PORT, saveFileUseCase);
final ServerPort server = new UDPServerAdapter(PORT, saveFileUseCase);
```

Then rebuild and run.

## Configuration

### Change Port

Edit `SocketsServer.java`:

```java
private static final int PORT = 1234;  // Change to desired port
```

## Protocol Characteristics

### TCP (Default)

- Connection-oriented
- Reliable delivery
- Maintains order
- Stream-based
- Higher overhead

### UDP (Alternative)

- Connectionless
- No delivery guarantee
- No ordering
- Datagram-based
- Lower overhead

## Testing

1. Start the server
2. Use the companion `sockets-client` application to send data
3. The server will print received data to the console

## Extending

### Add a New Use Case

1. Create a class implementing `UseCase` interface
2. Inject dependencies in constructor
3. Pass to server adapter in main class

### Add a Real File Repository

Replace `MockFileRepository` with actual file system implementation:

```java
public class FileSystemRepository implements FilePort {
    @Override
    public void saveFileToSrc(File file, String src) {
        Files.writeString(Path.of(src), file.getContent());
    }
}
```

### Add Concurrent Server

Modify adapters to handle multiple clients concurrently using threads or async I/O.

## Dependencies

- Java 24 (or compatible version)
- Maven 3.x
- No external libraries (uses only Java standard library)

## Author

@mariovillacortagarcia
