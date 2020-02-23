# Requirements

* UNIX environment
* Java 11 installed and configured to be default java
* Maven 3.6+ installed and configured
* working network connection for Maven repository access

# Assumptions

Input file is expected to be ASCII or ISO 8859-1 (single byte character encoded).
Standard input, output and error are used.
Included data.txt is to be used for unit tests.

# Execute

```
mvn verify # prepare and test

mvn # executes the application with parameter set to data.txt

java -jar target/homework-1.0-SNAPSHOT-jar-with-dependencies.jar <optional file name> # execute with any file as parameter or none at all
```

# In-code/javadoc documentation

Documentation in code does not copy-paste specification for simplicity. To build javadoc execute javadoc:javadoc goal.