# ComDownSpeed

A simple C++ console utility that estimates download completion times and reports how accurate the previous estimate was after each one-minute update.

## Building

The project uses a single C++ source file located at `src/download_estimator.cpp`. Compile it with any C++17-compatible compiler. For example, using `g++`:

```bash
g++ -std=c++17 -O2 -o DownloadEstimator.exe src/download_estimator.cpp
```

## Running

Run the compiled executable and follow the on-screen prompts. Provide the file size in gigabytes (GB) and download speed in megabytes per second (MB/s). After the initial estimate, wait one minute before entering the updated remaining size and speed. The program will display how accurate the previous estimate was and produce a fresh time estimate.

To finish early, enter `0` when prompted for the remaining file size.
