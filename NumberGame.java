import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class NumberGame extends JFrame {
    private int randomNum, attemptsLeft, totalAttempts, roundsWon, currentRound;
    private final int min = 1, max = 100, maxAttempts = 7;
    private JLabel promptLabel, feedbackLabel, attemptsLabel, scoreLabel, roundLabel;
    private JTextField guessField;
    private JButton guessButton, playAgainButton, exitButton;

    public NumberGame() {
        setTitle("Guess the Number Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8,1));

        roundLabel = new JLabel();
        promptLabel = new JLabel("Enter your guess (1-100):");
        feedbackLabel = new JLabel("");
        attemptsLabel = new JLabel();
        scoreLabel = new JLabel();
        guessField = new JTextField();
        guessButton = new JButton("Guess");
        playAgainButton = new JButton("Play Again");
        exitButton = new JButton("Exit");

        add(roundLabel);
        add(promptLabel);
        add(guessField);
        add(guessButton);
        add(feedbackLabel);
        add(attemptsLabel);
        add(scoreLabel);
        add(playAgainButton);
        add(exitButton);

        playAgainButton.setEnabled(false);

        guessButton.addActionListener(e -> processGuess());
        playAgainButton.addActionListener(e -> startNewRound());
        exitButton.addActionListener(e -> System.exit(0));

        startNewGame();
        setVisible(true);
    }

    private void startNewGame() {
        roundsWon = 0;
        totalAttempts = 0;
        currentRound = 1;
        startNewRound();
    }

    private void startNewRound() {
        randomNum = new Random().nextInt(max - min + 1) + min;
        attemptsLeft = maxAttempts;
        feedbackLabel.setText("");
        guessField.setText("");
        guessField.setEditable(true);
        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false);
        updateLabels();
    }

    private void updateLabels() {
        roundLabel.setText("Round: " + currentRound);
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        scoreLabel.setText("Rounds Won: " + roundsWon + " | Total Attempts: " + totalAttempts);
    }

    private void processGuess() {
        String input = guessField.getText();
        int guess;

        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Invalid input! Enter a number between " + min + " and " + max + ".");
            return;
        }
        if (guess < min || guess > max) {
            feedbackLabel.setText("Guess out of range! Try between 1 and 100.");
            return;
        }

        attemptsLeft--;
        totalAttempts++;

        if (guess == randomNum) {
            feedbackLabel.setText("Correct! You guessed it!");
            roundsWon++;
            guessButton.setEnabled(false);
            guessField.setEditable(false);
            playAgainButton.setEnabled(true);
            currentRound++;
        } else if (guess < randomNum) {
            feedbackLabel.setText("Too low! Try again.");
        } else {
            feedbackLabel.setText("Too high! Try again.");
        }

        if (attemptsLeft == 0 && guess != randomNum) {
            feedbackLabel.setText("No attempts left! The number was: " + randomNum);
            guessButton.setEnabled(false);
            guessField.setEditable(false);
            playAgainButton.setEnabled(true);
            currentRound++;
        }

        updateLabels();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGame::new);
    }
}
