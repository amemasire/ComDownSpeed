import java.util.Locale;
import java.util.Scanner;

public final class DownloadEstimator {
    private static final Scanner SCANNER = new Scanner(System.in);

    private DownloadEstimator() {
    }

    public static void main(String[] args) {
        SCANNER.useLocale(Locale.US);

        System.out.println("Download Time Estimator");
        System.out.println("=======================");

        double totalSizeGb = promptForDouble("Enter the total file size in GB: ", false);
        double initialSpeedMb = promptForDouble("Enter the current download speed in MB/s: ", false);

        double remainingMb = totalSizeGb * 1024.0;
        double speedMb = initialSpeedMb;

        if (speedMb <= 0) {
            System.out.println("Download speed must be greater than 0.");
            return;
        }

        printEstimate(remainingMb / speedMb);

        while (remainingMb > 0) {
            System.out.println();
            System.out.println("--- Minute Update ---");
            System.out.println("(Wait one minute, then provide the latest values.)");

            double actualRemainingGb =
                    promptForDouble("Enter the current remaining file size in GB (0 if finished): ", true);
            double actualSpeedMb = promptForDouble("Enter the current download speed in MB/s: ", false);

            double actualRemainingMb = actualRemainingGb * 1024.0;
            double predictedRemainingMb = Math.max(0.0, remainingMb - speedMb * 60.0);

            double reference = Math.max(Math.max(actualRemainingMb, predictedRemainingMb), 1.0);
            double error = Math.abs(actualRemainingMb - predictedRemainingMb);
            double accuracy = Math.max(0.0, 100.0 - (error / reference) * 100.0);

            System.out.printf(Locale.US, "Previous estimate accuracy: %.2f%%%n", accuracy);

            remainingMb = actualRemainingMb;
            speedMb = actualSpeedMb;

            if (remainingMb <= 0) {
                System.out.println("Download complete!");
                break;
            }

            printEstimate(remainingMb / speedMb);
        }
    }

    private static double promptForDouble(String message, boolean allowZero) {
        while (true) {
            System.out.print(message);
            if (!SCANNER.hasNextLine()) {
                System.out.println("Unexpected end of input. Exiting.");
                System.exit(1);
            }

            String input = SCANNER.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (!allowZero && value <= 0) {
                    System.out.println("Please enter a value greater than 0.");
                    continue;
                }
                if (allowZero && value < 0) {
                    System.out.println("Please enter a value that is zero or greater.");
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static void printEstimate(double seconds) {
        System.out.println("Estimated download time: " + formatDuration(seconds));
    }

    private static String formatDuration(double seconds) {
        if (seconds < 0) {
            seconds = 0;
        }

        long wholeSeconds = Math.round(seconds);
        long hours = wholeSeconds / 3600;
        long minutes = (wholeSeconds % 3600) / 60;
        long secs = wholeSeconds % 60;

        StringBuilder builder = new StringBuilder();
        if (hours > 0) {
            builder.append(hours).append("h ");
        }
        if (hours > 0 || minutes > 0) {
            builder.append(minutes).append("m ");
        }
        builder.append(secs).append('s');
        return builder.toString();
    }
}
