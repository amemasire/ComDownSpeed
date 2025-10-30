# ComDownSpeed

A simple C++ console utility that estimates download completion times and reports how accurate the previous estimate was after each one-minute update.

## Building

### Windows (Microsoft Visual C++ / Visual Studio Code)

1. Install the **Desktop development with C++** workload in Visual Studio (or the Build Tools) and ensure the Microsoft C++ compilers are available.
2. Open a *Developer Command Prompt for VS* and navigate to this repository.
3. Compile the program:

   ```batch
   cl /std:c++17 /EHsc /W4 /O2 /Fe:DownloadEstimator.exe src\download_estimator.cpp
   ```

   The resulting `DownloadEstimator.exe` can be run directly in the same prompt.

If you prefer **Visual Studio Code**, install the official *C/C++* extension by Microsoft, open this folder, and configure a build task that runs the command above. You can base the task on the "C/C++: cl.exe build active file" template and adjust the `args` array so it compiles `src\download_estimator.cpp` into `DownloadEstimator.exe`.

### Cross-platform (g++)

The project uses a single C++ source file located at `src/download_estimator.cpp`. Compile it with any C++17-compatible compiler. For example, using `g++`:

```bash
g++ -std=c++17 -O2 -o DownloadEstimator.exe src/download_estimator.cpp
```

## Running

Run the compiled executable and follow the on-screen prompts. Provide the file size in gigabytes (GB) and download speed in megabytes per second (MB/s). After the initial estimate, wait one minute before entering the updated remaining size and speed. The program will display how accurate the previous estimate was and produce a fresh time estimate.

To finish early, enter `0` when prompted for the remaining file size.
