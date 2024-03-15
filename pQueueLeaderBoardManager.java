import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Manages the leaderboard for the guessing game, storing entries in a priority queue sorted by score.
 * This class handles loading and saving leaderboard entries from and to a file, and provides functionality
 * for displaying the leaderboard and calculating a player's rank.
 */
public class pQueueLeaderBoardManager {
    // Priority queue to store leaderboard entries, sorted by score in descending order.
    private PriorityQueue<LeaderBoardEntry> entries;
    // File path for storing leaderboard data.
    private String filePath;

    /**
     * Constructs a pQueueLeaderBoardManager object with a specified file path for the leaderboard data.
     * Initializes the priority queue with a custom comparator to sort entries by score in descending order
     * and loads existing leaderboard data from the file.
     *
     * @param filePath The path to the leaderboard data file.
     */
    public pQueueLeaderBoardManager(String filePath) {
        this.filePath = filePath;
        // Comparator to sort leaderboard entries by score in descending order.
        Comparator<LeaderBoardEntry> comparator = (entry1, entry2) -> Integer.compare(entry2.getScore(), entry1.getScore());
        entries = new PriorityQueue<>(11, comparator);
        loadLeaderboard();
    }

    /**
     * Loads leaderboard entries from the file into the priority queue.
     * If the file does not exist, a message is printed, and a new file will be created upon saving.
     */
    private void loadLeaderboard() {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    entries.add(new LeaderBoardEntry(name, score));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Leaderboard file not found. A new one will be created.");
        }
    }

    /**
     * Updates the leaderboard with a new entry and saves the updated leaderboard to the file.
     *
     * @param name  The name of the player.
     * @param score The score achieved by the player.
     */
    public void updateLeaderboard(String name, int score) {
        entries.add(new LeaderBoardEntry(name, score));
        saveLeaderboard();
    }

    /**
     * Saves the current state of the leaderboard to the file, overwriting any existing data.
     * Each entry is written in a "name,score" format, one per line.
     */
    private void saveLeaderboard() {
        try (PrintWriter out = new PrintWriter(filePath)) {
            PriorityQueue<LeaderBoardEntry> tempQueue = new PriorityQueue<>(entries.comparator());
            tempQueue.addAll(entries);
            while (!tempQueue.isEmpty()) {
                LeaderBoardEntry e = tempQueue.poll();
                out.println(e.getName() + "," + e.getScore());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Failed to save the leaderboard.");
        }
    }

    /**
     * Displays the current leaderboard in descending order of score.
     */
    public void displayLeaderboard() {
        System.out.println("\nLeaderboard:");
        PriorityQueue<LeaderBoardEntry> copy = new PriorityQueue<>(entries.comparator());
        copy.addAll(entries);

        while (!copy.isEmpty()) {
            LeaderBoardEntry entry = copy.poll();
            System.out.println(entry.getName() + " - " + entry.getScore());
        }
    }

    /**
     * Calculates and returns the rank of a given score in the current leaderboard.
     *
     * @param score The score for which to find the rank.
     * @return The rank of the score in the leaderboard.
     */
    public int getRank(int score) {
        PriorityQueue<LeaderBoardEntry> tempQueue = new PriorityQueue<>(entries.comparator());
        tempQueue.addAll(entries);

        int rank = 1; // The best possible rank.
        while (!tempQueue.isEmpty()) {
            LeaderBoardEntry currentEntry = tempQueue.poll();
            if (currentEntry.getScore() > score) {
                rank++;
            } else {
                break; // The given score is either equal to or greater than the current entry's score.
            }
        }
        return rank;
    }
}
