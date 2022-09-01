<h1>Krakatoa Ventures</h1>

<h3>How to Build:</h3>
1. requires java 17
2. unzip KrakatoaVentures.zip
3. mvn clean package
4. go to %JAR_DIR%/target/
5. java -jar KrakatoaVentures-1.0.jar <Amount of Cash>

<h3>Design Decisions</h3>
1. It is assumed that the firms and member information is being drawn from a database
   so the majority of the business logic is placed in the controller rather than the models.
2. The firm class was made abstract since we are assuming Krakatoa Ventures is not the only firm.
3. It is assumed ALL the proceeds will be distributed at once, the logic would change if not all the proceeds are distributed.
4. If there are many more member classes, then having a hash map of class types rather than objects may make more sense.