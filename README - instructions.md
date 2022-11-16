# ECM2414 CA 2022

## How to Test

We use JUnit 4 and Java 17 to test our code.
Run Tests
java -cp ".;lib/*" org.junit.runner.JUnitCore CardGameTestSuite


## How to Run

Compile to bytecode:
javac -cp .;lib/junit-4.13.1.jar *.java

Run code
java CardGame

Logs will be outputted to the `logs` folder