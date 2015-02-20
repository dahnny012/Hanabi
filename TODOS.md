Hanabi TODOS:

1. Server needs 3 functions
  - Recieves a ping to join or create a room -- check
  - Add sockets to room -- check
  	- GameRoom should be some sort of object --unsure
  	- Need to be able to broadcast in a room. --- check
  	
  	
2. Server testing that needs to be done.

- Can concurrently send a request
	- join -- check
	- create -- check
	- message -- check
- After initial request can send additional request.
	-- check
- Client echo messages from server.
  	- For join , and create -- check
  	- For message -- check
  	
  	
Known bugs and concerns:
	- How to detect Disconnecting Clients ? 
		- maybe handle all exit signals on client side and send a msg
	- Sometimes client socket isnt "recieved"
		- random i dont know whats happening --- fixed
