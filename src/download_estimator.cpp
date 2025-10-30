#include <algorithm>
#include <cmath>
#include <cstdlib>
#include <iomanip>
#include <iostream>
#include <sstream>
#include <string>

namespace {

double PromptForDouble(const std::string &message, bool allowZero) {
    while (true) {
        std::cout << message;
        std::string input;
        if (!std::getline(std::cin, input)) {
            std::cout << "Unexpected end of input. Exiting.\n";
            std::exit(1);
        }
        try {
            double value = std::stod(input);
            if (!allowZero && value <= 0) {
                std::cout << "Please enter a value greater than 0.\n";
                continue;
            }
            if (allowZero && value < 0) {
                std::cout << "Please enter a value that is zero or greater.\n";
                continue;
            }
            return value;
        } catch (const std::exception &) {
            std::cout << "Invalid number. Please try again.\n";
        }
    }
}

std::string FormatDuration(double seconds) {
    if (seconds < 0) {
        seconds = 0;
    }

    const auto wholeSeconds = static_cast<long long>(std::round(seconds));
    const long long hours = wholeSeconds / 3600;
    const long long minutes = (wholeSeconds % 3600) / 60;
    const long long secs = wholeSeconds % 60;

    std::ostringstream oss;
    if (hours > 0) {
        oss << hours << "h ";
    }
    if (hours > 0 || minutes > 0) {
        oss << minutes << "m ";
    }
    oss << secs << "s";
    return oss.str();
}

void PrintEstimate(double seconds) {
    std::cout << "Estimated download time: " << FormatDuration(seconds) << "\n";
}

}

int main() {
    std::cout << "Download Time Estimator" << std::endl;
    std::cout << "=======================\n";

    const double totalSizeGb = PromptForDouble("Enter the total file size in GB: ", false);
    const double initialSpeedMb = PromptForDouble("Enter the current download speed in MB/s: ", false);

    double remainingMb = totalSizeGb * 1024.0;
    double speedMb = initialSpeedMb;

    if (speedMb <= 0) {
        std::cout << "Download speed must be greater than 0.\n";
        return 1;
    }

    PrintEstimate(remainingMb / speedMb);

    while (remainingMb > 0) {
        std::cout << "\n--- Minute Update ---\n";
        std::cout << "(Wait one minute, then provide the latest values.)\n";

        double actualRemainingGb =
            PromptForDouble("Enter the current remaining file size in GB (0 if finished): ", true);
        double actualSpeedMb = PromptForDouble("Enter the current download speed in MB/s: ", false);

        double actualRemainingMb = actualRemainingGb * 1024.0;
        double predictedRemainingMb = std::max(0.0, remainingMb - speedMb * 60.0);

        double reference = std::max({actualRemainingMb, predictedRemainingMb, 1.0});
        double error = std::fabs(actualRemainingMb - predictedRemainingMb);
        double accuracy = std::max(0.0, 100.0 - (error / reference) * 100.0);

        std::cout << std::fixed << std::setprecision(2);
        std::cout << "Previous estimate accuracy: " << accuracy << "%\n";

        remainingMb = actualRemainingMb;
        speedMb = actualSpeedMb;

        if (remainingMb <= 0) {
            std::cout << "Download complete!\n";
            break;
        }

        PrintEstimate(remainingMb / speedMb);
    }

    return 0;
}
