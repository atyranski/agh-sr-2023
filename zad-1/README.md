# Homework 1

## Description:
* Writing a chat application (5 points)
  * Clients are connecting to the server over TCP
  * Server receives messages from each client and sending them to another (along with the client's id/nickname)
  * The server is multi-threaded - each connection from the client has its own thread
  * Please pay attention to the correct handling of threads
* Add another channel for UDP (3 points)
  * The server and each client open an additional UDP channel (same port number as for TCP)
  * After entering the "U" command at the client, a message is sent via UDP to the server, which sends it to other clients
* The message simulates multimedia data (you can e.g. send ASCII Art) Implement the above point in the multicast version (2 points)
  * Not instead, but as an alternative option to select ('M' command)
  * Multicast directly to all via multicast address (server may or may not receive)

## Comments:
* The job can be done for programming
* No network communication frameworks allowed - sockets only! Akka is also not allowed to be used
* Performance of solutions (e.g. thread pool)
* Correctness of solutions (e.g. avoiding sending messages to the sender, handling threads)

## Setup

To build project you have to execute following commands
```
  gradle clean build
```
Or just execute them from the toolbar on the right in IntelliJ

Open terminal and move to particular directories (ofc you can copy them somewhere else)
* `server/build/libs` - server's jar path
* `client/build/libs` - client's jar path

### Server
To run server app you need 3 parameters:
* `address` - address of the server (default `localhost`)
* `port` - port of the server (default `3000`)
* `backlog` - the requested maximum number of pending connections on the socket (default `100`)

Parameters should be passed as a command line parameters.
Example:
```
java -jar .\server-0.0.1.jar --address 127.0.0.1 --port 3000 --backlog 20
```

Order of parameters doesn't matter.

### Client
To run client app you need 3 parameters:
* `nickname` - name of the username displayed on server and in other client's(<b>required</b>)
* `address` - address for the server (default `localhost`)
* `port` - port of the server (default `3000`)

Parameters should be passed as a command line parameters.
Example:
```
java -jar .\client-0.0.1.jar --nickname client-1 --address 127.0.0.1 --port 3000
```

Order of parameters doesn't matter.

## Sending messages
You can send messages from and to client app - either by the TCP and UDP

### TCP
To send a message by TCP you have to specify a prefix `<tcp>`.
Example:
```
0    [main] DEBUG ClientService  - client instantiated successfully
1    [main] DEBUG ClientService  - tcp-socket [/127.0.0.1:56510] running
1    [main] DEBUG ClientService  - udp-socket [/127.0.0.1:56510] running
3    [Thread-0] DEBUG ClientService  - TCP message listener running     
3    [Thread-1] DEBUG ClientService  - UDP message listener running 
<tcp>Wiadomość testowa TCP - prefix
```

In this app, sending a message by TCP is a default option, so you don't have to specify the prefix
Example:
```
0    [main] DEBUG ClientService  - client instantiated successfully
1    [main] DEBUG ClientService  - tcp-socket [/127.0.0.1:56510] running
1    [main] DEBUG ClientService  - udp-socket [/127.0.0.1:56510] running
3    [Thread-0] DEBUG ClientService  - TCP message listener running     
3    [Thread-1] DEBUG ClientService  - UDP message listener running 
Wiadomość testowa TCP - bez prefixu
```

### UDP
To send a message by UDP you have to specify a prefix `<udp>`.
Example:
```
0    [main] DEBUG ClientService  - client instantiated successfully
1    [main] DEBUG ClientService  - tcp-socket [/127.0.0.1:56510] running
1    [main] DEBUG ClientService  - udp-socket [/127.0.0.1:56510] running
3    [Thread-0] DEBUG ClientService  - TCP message listener running     
3    [Thread-1] DEBUG ClientService  - UDP message listener running 
<udp>Sample text UDP - prefix
```