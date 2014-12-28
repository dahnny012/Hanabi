all: Server.java Client.java
	javac Server.java
	javac Client.java


clean:
	rm -rf *.class