app: AppThread App.java
	javac App.java AppThread.java
	
AppThread: AppThread.java
	javac AppThread.java
	
network: Server.java Client.java
	javac Server.java
	javac Client.java
	
clean:
	rm -rf *.class
