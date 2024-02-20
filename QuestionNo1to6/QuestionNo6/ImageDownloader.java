package QuestionNo1to6.QuestionNo6;
/*
 * This program implements an image downloader application using Java Swing.
 * It allows users to input URLs of web pages containing images and downloads those images to a specified folder.
 */
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ImageDownloader extends JFrame {
    private List<JTextField> urlFields; // List to hold JTextField components for entering URLs
    private JButton downloadButton; // Button to initiate downloads
    private JButton pauseButton; // Button to pause downloads
    private JButton resumeButton; // Button to resume downloads
    private JButton cancelButton; // Button to cancel downloads
    private JPanel statusPanel; // Panel to display download status
    private ExecutorService executor; // ExecutorService for managing download tasks
    private final String DOWNLOAD_FOLDER = System.getProperty("user.home") + "/Desktop/DownloadedImagesDSA/"; // Folder to save downloaded images
   
    // Constructor
    public ImageDownloader() {
        setTitle("Image Downloader"); // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the window is closed
        setLayout(new BorderLayout()); // Set BorderLayout for the frame
        
        // Initialize components
        urlFields = new ArrayList<>(); // Initialize list for URL input fields
        for (int i = 0; i < 3; i++) { // Create 3 text fields for entering URLs
            JTextField textField = new JTextField(30);
            urlFields.add(textField);
        }
        // Set default URLs for testing
        urlFields.get(0).setText("https://www.pinterest.com/mirmbr/nepal/");
        urlFields.get(1).setText("https://www.pinterest.com/mirmbr/india/");
        urlFields.get(2).setText("https://www.pinterest.com/mirmbr/china/");

        downloadButton = new JButton("Download"); // Initialize download button
        pauseButton = new JButton("Pause"); // Initialize pause button
        resumeButton = new JButton("Resume"); // Initialize resume button
        cancelButton = new JButton("Cancel"); // Initialize cancel button
        statusPanel = new JPanel(new GridLayout(0, 1)); // Initialize status panel with GridLayout
        
        // Add action listeners to buttons
        downloadButton.addActionListener(e -> downloadImages());
        pauseButton.addActionListener(e -> pauseDownloads());
        resumeButton.addActionListener(e -> resumeDownloads());
        cancelButton.addActionListener(e -> cancelDownloads());

        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // Set BoxLayout for input panel
        inputPanel.add(new JLabel("URL(s):")); // Add label for URLs
        for (JTextField urlField : urlFields) { // Add URL input fields to the input panel
            inputPanel.add(urlField);
        }
        inputPanel.add(downloadButton); // Add download button to the input panel

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(pauseButton); // Add pause button
        buttonPanel.add(resumeButton); // Add resume button
        buttonPanel.add(cancelButton); // Add cancel button

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH); // Add input panel to the top
        add(new JScrollPane(statusPanel), BorderLayout.CENTER); // Add scrollable status panel to the center
        add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom

        executor = Executors.newFixedThreadPool(5); // Initialize executor service with a fixed thread pool of size 5
        setSize(600, 500); // Set window size
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true); // Set window visible

        createDownloadFolder(); // Create download folder
    }

    // Method to create download folder
    private void createDownloadFolder() {
        File folder = new File(DOWNLOAD_FOLDER); // Create File object for download folder
        if (!folder.exists()) { // Check if folder does not exist
            folder.mkdirs(); // Create the folder and its parent directories if they do not exist
        }
    }

    // Method to initiate image downloads
    private void downloadImages() {
        for (JTextField urlField : urlFields) { // Iterate through URL input fields
            String url = urlField.getText(); // Get the URL from the text field
            if (!url.isEmpty()) { // Check if URL is not empty
                CompletableFuture.runAsync(() -> { // Asynchronously execute the download task
                    try {
                        URL imageURL = new URL(url); // Create URL object for the image URL
                        BufferedReader reader = new BufferedReader(new InputStreamReader(imageURL.openStream())); // Create a buffered reader to read from the URL
                        String line;
                        while ((line = reader.readLine()) != null) { // Read each line from the URL
                            if (line.contains("<img")) { // Check if the line contains an image tag
                                String imgUrl = line.substring(line.indexOf("src=\"") + 5, line.indexOf("\"", line.indexOf("src=\"") + 5)); // Extract the image URL from the line
                                ImageDownloadTask task = new ImageDownloadTask(imgUrl); // Create a new download task for the image URL
                                SwingUtilities.invokeLater(() -> statusPanel.add(task.getStatusPanel())); // Add the task's status panel to the UI
                                executor.execute(task); // Execute the download task
                            }
                        }
                        reader.close(); // Close the reader
                    } catch (IOException e) { // Handle IO exception
                        JOptionPane.showMessageDialog(this, "Error downloading images: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Show error message dialog
                    }
                });
            }
        }
    }

    // Method to pause downloads
    private void pauseDownloads() {
        for (Component comp : statusPanel.getComponents()) { // Iterate through components in the status panel
            if (comp instanceof JPanel) { // Check if the component is a JPanel
                ImageDownloadTask task = ((ImageDownloadTask) ((JPanel) comp).getClientProperty("task")); // Get the download task associated with the panel
                if (task != null) { // Check if task is not null
                    task.pauseDownload(); // Pause the download task
                }
            }
        }
    }

    // Method to resume downloads
    private void resumeDownloads() {
        for (Component comp : statusPanel.getComponents()) { // Iterate through components in the status panel
            if (comp instanceof JPanel) { // Check if the component is a JPanel
                ImageDownloadTask task = ((ImageDownloadTask) ((JPanel) comp).getClientProperty("task")); // Get the download task associated with the panel
                if (task != null) { // Check if task is not null
                    task.resumeDownload(); // Resume the download task
                }
            }
        }
    }

    // Method to cancel downloads
    private void cancelDownloads() {
        for (Component comp : statusPanel.getComponents()) { // Iterate through components in the status panel
            if (comp instanceof JPanel) { // Check if the component is a JPanel
                ImageDownloadTask task = ((ImageDownloadTask) ((JPanel) comp).getClientProperty("task")); // Get the download task associated with the panel
                if (task != null) { // Check if task is not null
                    task.cancelDownload(); // Cancel the download task
                }
            }
        }
    }

    // Inner class representing a download task
    private class ImageDownloadTask implements Runnable {
        private String imageUrl; // URL of the image to download
        private JProgressBar progressBar; // Progress bar to display download progress
        private JLabel statusLabel; // Label to display download status
        private boolean paused; // Flag to indicate if download is paused
        private boolean canceled; // Flag to indicate if download is canceled
        private JPanel statusPanel; // Panel to display download status

        // Constructor
        public ImageDownloadTask(String imageUrl) {
            this.imageUrl = imageUrl; // Initialize image URL
            progressBar = new JProgressBar(0, 100); // Initialize progress bar
            progressBar.setStringPainted(true); // Set progress bar to display percentage
            statusLabel = new JLabel(imageUrl); // Initialize status label with image URL
            statusPanel = new JPanel(new BorderLayout()); // Initialize status panel with BorderLayout
            statusPanel.add(statusLabel, BorderLayout.NORTH); // Add status label to the top
            statusPanel.add(progressBar, BorderLayout.SOUTH); // Add progress bar to the bottom
            statusPanel.putClientProperty("task", this); // Associate the download task with the status panel
            paused = false; // Set paused flag to false
            canceled = false; // Set canceled flag to false
        }

        // Method to get the status panel
        public JPanel getStatusPanel() {
            return statusPanel; // Return the status panel
        }

        // Method to run the download task
        @Override
        public void run() {
            try {
                URL url = new URL(imageUrl); // Create URL object for the image URL
                InputStream in = new BufferedInputStream(url.openStream()); // Open input stream for the URL
                ByteArrayOutputStream out = new ByteArrayOutputStream(); // Create byte array output stream
                byte[] buffer = new byte[1024]; // Buffer for reading data from the input stream
                int bytesRead;
                long totalBytesRead = 0; // Total bytes read
                long fileSize = url.openConnection().getContentLengthLong(); // Get file size
                while (!canceled && (bytesRead = in.read(buffer)) != -1) { // Read data from the input stream
                    if (!paused) { // Check if download is not paused
                        out.write(buffer, 0, bytesRead); // Write data to the output stream
                        totalBytesRead += bytesRead; // Update total bytes read
                        int percentCompleted = (int) (totalBytesRead * 100 / fileSize); // Calculate percentage completed
                        SwingUtilities.invokeLater(() -> { // Update UI on the Event Dispatch Thread
                            progressBar.setValue(percentCompleted); // Update progress bar value
                            progressBar.setString(percentCompleted + "%"); // Update progress bar text
                        });
                    } else { // If download is paused
                        Thread.sleep(100); // Sleep for a short period
                    }
                }
                in.close(); // Close input stream
                if (!canceled) { // If download is not canceled
                    byte[] imageBytes = out.toByteArray(); // Get downloaded image bytes
                    out.close(); // Close output stream
                    saveImage(imageBytes); // Save the image to disk
                    SwingUtilities.invokeLater(() -> statusLabel.setText(imageUrl + " - Completed")); // Update status label
                } else { // If download is canceled
                    out.close(); // Close output stream
                    SwingUtilities.invokeLater(() -> statusLabel.setText(imageUrl + " - Canceled")); // Update status label
                }
            } catch (IOException | InterruptedException e) { // Handle IO exception or interrupted exception
                SwingUtilities.invokeLater(() -> { // Update UI on the Event Dispatch Thread
                    statusLabel.setText(imageUrl + " - Error"); // Update status label
                    progressBar.setString("Error"); // Update progress bar text
                });
                JOptionPane.showMessageDialog(ImageDownloader.this, "Error downloading image: " + imageUrl + " - " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Show error message dialog
            }
        }

        // Method to save the downloaded image to disk
        private void saveImage(byte[] imageBytes) throws IOException {
            Path imagePath = Paths.get(DOWNLOAD_FOLDER + imageUrl.substring(imageUrl.lastIndexOf('/') + 1)); // Create Path object for saving the image
            Files.write(imagePath, imageBytes); // Write image bytes to file
        }

        // Method to pause the download
        public void pauseDownload() {
            paused = true; // Set paused flag to true
        }

        // Method to resume the download
        public void resumeDownload() {
            paused = false; // Set paused flag to false
        }

        // Method to cancel the download
        public void cancelDownload() {
            canceled = true; // Set canceled flag to true
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageDownloader::new); // Create and show the GUI on the Event Dispatch Thread
    }
}
