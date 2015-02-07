app: AppThread App.java
	javac App.java AppThread.java
	
AppThread: AppThread.java
	javac AppThread.java
	
all: Server.java Client.java
	javac Server.java
	javac Client.java
	
clean:
	rm -rf *.class
