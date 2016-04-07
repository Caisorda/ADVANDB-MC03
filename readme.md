## Server App Login Flow
1. Client App asks for Server's IP
2. Server App accepts connection from Client App
3. Server App adds Client to Machines ArrayList
4. Server sends a message to all connected Clients saying something like "Marinduque @ 192.168.1.101 has connected to the server."
5. Client App updates UI with other connected clients

## If Client App exits / User closes the app
1. If Client App exits, send a message first to Server saying that the Client has disconnected
2. Server App acknowledges the Client's disconnection and deletes the machine from the ArrayList

## TransactionCoordinator Client-Server Flow
1. Client App sends query request to Server App
2. Server App receives query request
3. Server App sends query to three machines
4. The 3 Client Apps receives the query and replies READY to the Server App
5. Server App sends GLOBAL_COMMIT to the three machines
6. The 3 Client Apps receive GLOBAL_COMMIT and executes the query
7. The 3 Client Apps sends the results to the Server App
8. Server App sends the results to the Client App who asked for it.