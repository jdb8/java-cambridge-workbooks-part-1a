This is the Java work for the term, hosted on git so I can access it wherever I am (and keep track of stuff!).

Reference: http://www.cl.cam.ac.uk/teaching/1112/ProgJava/materials.html

Useful snippets:

Compiling to a .class:

	javac uk/ac/cam/jdb75/<folder>/<file.java>

Creating the .jar file:
	
	jar cfe <file.jar> uk.ac.cam.jdb75.<folder>.<file> uk/ac/cam/jdb75/<folder>/<file.*>

Running the .jar file:
	
	java -jar <file.jar>
