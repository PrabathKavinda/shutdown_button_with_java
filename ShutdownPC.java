import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ShutdownPC {
    private static int countdown = 20; // Shutdown countdown in seconds

    public static void main(String[] args) {
        // Create and display the pop-up with a countdown
        JFrame frame = createPopup();

        // Schedule the shutdown after 20 seconds
        try {
            Runtime.getRuntime().exec("shutdown -s -t 20"); // Windows shutdown command
            System.out.println("Shutdown scheduled in 20 seconds.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Start the countdown timer
        Timer timer = new Timer(1000, e -> updateCountdown(frame)); // Update every 1 second
        timer.start();
    }

    private static JFrame createPopup() {
        // Create a new JFrame for the pop-up
        JFrame frame = new JFrame("Shutdown Warning");
        frame.setSize(400, 200);

        // Create a panel with a black background
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());

        // Create a text label with styled font and white color for the warning message
        JLabel messageLabel = new JLabel("Your PC will shut down in...");
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create another label for the countdown
        JLabel countdownLabel = new JLabel(countdown + " seconds", JLabel.CENTER);
        countdownLabel.setForeground(Color.RED);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 36));
        countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the labels to the panel
        panel.add(messageLabel, BorderLayout.NORTH);
        panel.add(countdownLabel, BorderLayout.CENTER);

        // Add the panel to the frame
        frame.add(panel);

        // Save the countdown label in the frame's properties for later updates
        frame.getRootPane().putClientProperty("countdownLabel", countdownLabel);

        // Center the frame on the screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return frame;
    }

    private static void updateCountdown(JFrame frame) {
        // Update the countdown and update the label text
        if (countdown > 0) {
            countdown--; // Decrease countdown by 1 second
            JLabel countdownLabel = (JLabel) frame.getRootPane().getClientProperty("countdownLabel");
            countdownLabel.setText(countdown + " seconds");
        } else {
            // Close the pop-up once countdown has reached 0
            frame.dispose();
        }
    }
}