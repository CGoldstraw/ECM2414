# ECM2414 CA 2022

## Development Notes

Commit as regularly as possible and run `git pull` before coding to get any new changes

## Structure

`src` - Source code

`bin` - Bytecode

## Build Notes

Compile to bytecode:

```bash
javac -cp src:lib/junit-4.13.1.jar -d bin src/*.java
```

Run code

```bash
java -cp bin CardGame
```

Logs will be ouputterd to the `logs` folder

# Testing

Compile to bytecode:

```bash
javac -cp src:lib/junit-4.13.1.jar -d bin src/*.java
```

Run tests

```bash
java -cp "bin:lib/*" org.junit.runner.JUnitCore CardGameTestSuite
```
