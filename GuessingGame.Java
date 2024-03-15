import java.util.Scanner;

/**
 * The GuessingGame class provides a structure for implementing a number guessing game with
 * different difficulty levels (easy, medium, hard). It includes functionality for generating random numbers
 * within specified ranges based on the chosen difficulty, taking user guesses, and managing a leaderboard
 * for each difficulty level.
 */
public class GuessingGame {
    private int randomNum;
    private Scanner scan;
    boolean win;
    // The selected difficulty level.
    String difficulty;
    // The score for the current game session.
    int score;
    // The player's name.
    String name;
    // Leaderboard managers for each difficulty level, managing scores and rankings.
    pQueueLeaderBoardManager leaderBoardManagerEasy;
    pQueueLeaderBoardManager leaderBoardManagerMedium;
    pQueueLeaderBoardManager leaderBoardManagerHard;

    /**
     * Constructor for initializing the game. Sets up leaderboards for each difficulty level and prepares
     * the system for user input.
     */
    GuessingGame() {
        // Initialize leaderboard managers with the file names for storing leaderboard data.
        leaderBoardManagerEasy = new pQueueLeaderBoardManager("easyLeaderBoard.txt");
        leaderBoardManagerMedium = new pQueueLeaderBoardManager("mediumLeaderBoard.txt");
        leaderBoardManagerHard = new pQueueLeaderBoardManager("hardLeaderBoard.txt");
        // Initialize the scanner for user input.
        scan = new Scanner(System.in);
        // Initially, the player has not won the game.
        win = false;
    }

    /**
     * Starts the game loop, handling initial setup, gameplay, and post-game actions. It supports
     * replayability by allowing the user to play multiple rounds until they choose to exit.
     */
    public void startGame(){
        // Introduction and input for the player's name.
        System.out.println("Welcome to the guessing game! Enter your name ");
        name = scan.next();
        System.out.println("Good luck");

        // Main game loop.
        while(true){
            // Difficulty selection loop. Validates user input and sets up the game difficulty.
            while(true){
                System.out.println("Do you want to play 'easy' 'medium' or 'hard'");
                difficulty = scan.next();
                // Generate a random number within the range based on the selected difficulty.
                if (!difficulty.equalsIgnoreCase("easy") && !difficulty.equalsIgnoreCase("medium") && !difficulty.equalsIgnoreCase("hard")) {
                    System.out.println("Invalid Input ");
                }
                else {
                    // Generate a random number based on the chosen difficulty level.
                    randomNum = (int) (Math.random() * (difficulty.equalsIgnoreCase("easy") ? 25 : (difficulty.equalsIgnoreCase("medium") ? 50 : 100))) + 1;
                    break;
                }
            }
             System.out.println(randomNum); // Debug: Display the generated number.

            // Gameplay loop: allows the player a fixed number of guesses.
            for (int j = 10; j > 0; j--) {
                score++;
                // Prompt the player to make a guess, indicating the number of remaining attempts.
                System.out.printf("Guess the number between 1 and %d you have %d more chances! \n", difficulty.equalsIgnoreCase("easy") ? 25 : (difficulty.equalsIgnoreCase("medium") ? 50 : 100), j);

                int guess = scan.nextInt();

                // Check if the guess is outside the valid range, based on difficulty.
                if ((difficulty.equalsIgnoreCase("easy") && (guess > 25 || guess < 1)) ||
                        (difficulty.equalsIgnoreCase("medium") && (guess > 50 || guess < 1)) ||
                        (difficulty.equalsIgnoreCase("hard") && (guess > 100 || guess < 1))) {
                    System.out.printf("Invalid input. Guess the number between 1 and %d\n", difficulty.equalsIgnoreCase("easy") ? 25 : (difficulty.equalsIgnoreCase("medium") ? 50 : 100));
                    j++; // Compensate for the invalid attempt.
                }
                // Check if the guess matches the random number.
                if(guess == randomNum){
                    win = true;
                    System.out.println("You win! ");
                    // Update and display the leaderboard based on the current difficulty level and score.
                    if(difficulty.equalsIgnoreCase("easy")){
                        leaderBoardManagerEasy.updateLeaderboard(name,score);
                        System.out.println("Your score is ranked #" + leaderBoardManagerEasy.getRank(score) + " on the all-time leaderboard!");
                        leaderBoardManagerEasy.displayLeaderboard();
                    }
                    else if (difficulty.equalsIgnoreCase("medium")) {
                        leaderBoardManagerMedium.updateLeaderboard(name,score);
                        System.out.println("Your score is ranked #" + leaderBoardManagerMedium.getRank(score) + " on the all-time leaderboard!");
                        leaderBoardManagerMedium.displayLeaderboard();
                    }
                    else {
                        leaderBoardManagerHard.updateLeaderboard(name,score);
                        System.out.println("Your score is ranked #" + leaderBoardManagerHard.getRank(score) + " on the all-time leaderboard!");
                        leaderBoardManagerHard.displayLeaderboard();
                    }
                    break; // Exit the guessing loop upon winning.
                }
                // Provide feedback on the guess.
                if(guess > randomNum){
                    System.out.println("Your guess is greater than the hidden number.");
                }else {
                    System.out.println("Your guess is less than the hidden number.");
                }
            }

            // If the game ends without a win, notify the player.
            if(!win){
                System.out.println("You lose!");
            }
            // Reset score for a new game.
            score = 0;

            // Prompt for replay.
            System.out.println("Would you like to play again? 'yes' or 'no' ");
            String input = scan.next();

            // Exit the game loop if the player chooses not to play again.
            if(!input.equalsIgnoreCase("yes")){
                break;
            }else {
                // Reset game settings for a new round.
                resetGame();
            }
        }
        // Farewell message upon exiting the game.
        System.out.println("Thank you for playing !");
    }

    /**
     * Resets the game for a new round by re-initializing the random number based on the
     * current difficulty level and resetting the win flag.
     */
    public void resetGame(){
        // Generate a new random number for the new game round.
        randomNum = (int) (Math.random() * (difficulty.equalsIgnoreCase("easy") ? 25 : (difficulty.equalsIgnoreCase("medium") ? 50 : 100))) + 1;
        win = false;
    }
}
