# ComDownSpeed

A simple Java console utility that estimates download completion times and reports how accurate the previous estimate was after
 each one-minute update.

## Building

### Requirements

* Java Development Kit (JDK) 11 or later.

### Compile

From the repository root, run:

```bash
javac -d out src/DownloadEstimator.java
```

This compiles the program into the `out` directory.

## Running

Execute the compiled program with:

```bash
java -cp out DownloadEstimator
```

The application will prompt for the file size in gigabytes (GB) and download speed in megabytes per second (MB/s). After the
 initial estimate, wait one minute before entering the updated remaining size and speed. The program will display how accurate
 the previous estimate was and produce a fresh time estimate.

To finish early, enter `0` when prompted for the remaining file size.
