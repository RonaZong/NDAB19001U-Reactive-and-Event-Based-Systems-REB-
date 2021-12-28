# A1 - Part 2 Implementing a conformance checker for DCR Graphs

## Requirements
* Maven
    * MACOS with brew: ``brew install maven``
* Java SDK

## Running
1. Build project with Maven in ``/DCR CHECKER/``: ``mvn clean compile assembly:single``
2. In ``/target/`` folder : ``java -jar DCR_Checker-1.0-SNAPSHOT-jar-with-dependencies.jar $PATH$``
    * Where path ``$PATH$`` is the absolute path to ``log.csv`` (don't forget quotes).
3. Result: Once the program is run, the outputs will show on terminal. Also, a "result.txt" can be found in the folder "DCR Checker". 
