package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class cricket {
    private JFrame mainFrame;
    private JPanel startPanel, playPanel, resultPanel;
    private JTextField scoreInput;
    private JTextArea resultArea;
    private JButton startButton, playButton, nextButton, batButton, bowlButton;
    private JLabel computerScoreLabel, playerScoreLabel, targetLabel, tossLabel, ballsLeftLabel;
    private int computerScore = 0, playerScore = 0, targetScore = 0;
    private String tossWinner;
    private Random random;
    private int ballsLeft = 12;
    private boolean isUserBatting;
    private int inningBit = 1; // 1 for first inning, 2 for second inning

    public cricket() {
        random = new Random();
        initializeUI();
    }

    private void initializeUI() {
        mainFrame = new JFrame("Code Cricket: Java Edition");
        mainFrame.setSize(600, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new CardLayout());
        mainFrame.setResizable(false);

        createStartPanel();
        createPlayPanel();
        createResultPanel();

        mainFrame.add(startPanel, "Start");
        mainFrame.add(playPanel, "Play");
        mainFrame.add(resultPanel, "Result");

        mainFrame.setVisible(true);
    }

    private void createStartPanel() {
        startPanel = new JPanel();
        startPanel.setBackground(Color.CYAN);
        startPanel.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Code Cricket!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.BLUE);

        startButton = new JButton("Start Game");
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toss();
            }
        });

        startPanel.add(welcomeLabel, BorderLayout.CENTER);
        startPanel.add(startButton, BorderLayout.SOUTH);
    }

    private void createPlayPanel() {
        playPanel = new JPanel();
        playPanel.setBackground(Color.LIGHT_GRAY);
        playPanel.setLayout(new GridLayout(8, 1));

        scoreInput = new JTextField();
        scoreInput.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreInput.setForeground(Color.BLACK);

        playButton = new JButton("Play");
        playButton.setBackground(Color.ORANGE);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(new Font("Arial", Font.PLAIN, 18));

        computerScoreLabel = new JLabel("Computer Score: 0", SwingConstants.CENTER);
        computerScoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        playerScoreLabel = new JLabel("Your Score: 0", SwingConstants.CENTER);
        playerScoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        targetLabel = new JLabel("", SwingConstants.CENTER); // Initially empty
        targetLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        tossLabel = new JLabel("", SwingConstants.CENTER);
        tossLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tossLabel.setForeground(Color.RED);

        ballsLeftLabel = new JLabel("Balls Left: 12", SwingConstants.CENTER);
        ballsLeftLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playGame();
            }
        });

        // Allow Enter key input for score
        scoreInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    playGame();
                }
            }
        });

        playPanel.add(tossLabel);
        playPanel.add(new JLabel("Enter your score (1-6):", SwingConstants.CENTER));
        playPanel.add(scoreInput);
        playPanel.add(playButton);
        playPanel.add(computerScoreLabel);
        playPanel.add(playerScoreLabel);
        playPanel.add(targetLabel);
        playPanel.add(ballsLeftLabel);
    }

    private void createResultPanel() {
        resultPanel = new JPanel();
        resultPanel.setBackground(Color.MAGENTA);
        resultPanel.setLayout(new BorderLayout());

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Arial", Font.PLAIN, 18));
        resultArea.setEditable(false);

        nextButton = new JButton("Play Again");
        nextButton.setBackground(Color.YELLOW);
        nextButton.setForeground(Color.BLACK);
        nextButton.setFont(new Font("Arial", Font.PLAIN, 18));

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                toss(); // Reset the toss
            }
        });

        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        resultPanel.add(nextButton, BorderLayout.SOUTH);
    }

    private void toss() {
        // Even or Odd Toss
        int playerToss = JOptionPane.showOptionDialog(mainFrame,
                "Choose Even (0) or Odd (1)", "Toss",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{"Even", "Odd"}, null);

        int computerToss = random.nextInt(2); // 0 for even, 1 for odd
        tossWinner = (playerToss == computerToss) ? "You win the toss!" : "Computer wins the toss!";

        tossLabel.setText(tossWinner);
        CardLayout cl = (CardLayout) (mainFrame.getContentPane().getLayout());
        cl.show(mainFrame.getContentPane(), "Play");

        if (playerToss == computerToss) {
            chooseBatOrBowl(); // Ask user if they want to bat or bowl
        } else {
            startComputerBatting(); // Computer bats if it wins the toss
        }
    }

    private void chooseBatOrBowl() {
        int choice = JOptionPane.showOptionDialog(mainFrame,
                "You won the toss! Choose:",
                "Bat or Bowl",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Bat", "Bowl"},
                null);

        if (choice == 0) {
            startUserBatting(); // User chooses to bat
        } else {
            startComputerBatting(); // User chooses to bowl
        }
    }

    private void startUserBatting() {
        isUserBatting = true;
        inningBit = 1; // User batting in first inning
        playerScore = 0; // Reset player score
        playerScoreLabel.setText("Your Score: 0");
        targetLabel.setText(""); // No target displayed in the first innings
        ballsLeft = 12; // Reset balls left
        ballsLeftLabel.setText("Balls Left: " + ballsLeft);
        scoreInput.requestFocus(); // Set focus on the score input field
    }

    private void startComputerBatting() {
        isUserBatting = false;
        inningBit = 1; // Computer batting in first inning
        computerScore = random.nextInt(6) + 1; // Computer's score between 1-6
        computerScoreLabel.setText("Computer Score: " + computerScore);
        playerScore = 0; // Reset player score
        playerScoreLabel.setText("Your Score: " + playerScore);
        targetScore = computerScore + 1; // Set target to be one run more than computer's score
        targetLabel.setText("Target: " + targetScore); // Display target during second inning
        ballsLeft = 12; // Reset balls left
        ballsLeftLabel.setText("Balls Left: " + ballsLeft);
        scoreInput.requestFocus(); // Set focus on the score input field
    }

    private void playUserBatting() {
        String input = scoreInput.getText();
        if (isValidInput(input)) {
            int playerInput = Integer.parseInt(input);
            int computerInput = random.nextInt(6) + 1;

            if (playerInput != computerInput) {
                playerScore += playerInput; // Update player score only if the user is batting
                playerScoreLabel.setText("Your Score: " + playerScore);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "You got out!", "Out!", JOptionPane.WARNING_MESSAGE);
                endUserInnings();
            }

            ballsLeft--; // Decrement balls left
            ballsLeftLabel.setText("Balls Left: " + ballsLeft);

            // Check if balls are exhausted
            if (ballsLeft <= 0) {
                endUserInnings();
            }
        }
        scoreInput.setText(""); // Clear input field
    }

    private void endUserInnings() {
        if (inningBit == 1) {
            inningBit = 2; // Move to the second inning
            startComputerBatting(); // Start computer's batting
        } else {
            checkFinalResult(); // Check final result if both innings are complete
        }
    }

    private void playComputerBatting() {
        int computerInput = random.nextInt(6) + 1; // Generate random score for computer
        int playerInput = random.nextInt(6) + 1; // Simulate player's score

        if (computerInput != playerInput) {
            computerScore += computerInput; // Update computer score only if it is batting
            computerScoreLabel.setText("Computer Score: " + computerScore);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Computer is out!", "Out!", JOptionPane.WARNING_MESSAGE);
            endComputerInnings();
        }

        ballsLeft--; // Decrement balls left
        ballsLeftLabel.setText("Balls Left: " + ballsLeft);

        // Check if balls are exhausted
        if (ballsLeft <= 0) {
            endComputerInnings();
        }
    }

    private void endComputerInnings() {
        if (inningBit == 2) {
            checkFinalResult(); // Check final result after both innings are complete
        } else {
            inningBit = 2; // Move to the second inning
            startUserBatting(); // Start user's batting for second inning
        }
    }

    private void checkFinalResult() {
        if (playerScore > computerScore) {
            resultArea.setText("You scored " + playerScore + " runs and won the match!");
        } else if (playerScore == computerScore) {
            resultArea.setText("It's a draw! Both scored " + playerScore + " runs.");
        } else {
            resultArea.setText("Computer scored " + computerScore + " runs and won the match!");
        }
        CardLayout cl = (CardLayout) (mainFrame.getContentPane().getLayout());
        cl.show(mainFrame.getContentPane(), "Result");
    }

    private boolean isValidInput(String input) {
        try {
            int score = Integer.parseInt(input);
            return score >= 1 && score <= 6; // Valid if score is between 1-6
        } catch (NumberFormatException e) {
            return false; // Invalid input
        }
    }

    private void resetGame() {
        computerScore = 0;
        playerScore = 0;
        targetScore = 0;
        ballsLeft = 12;
        computerScoreLabel.setText("Computer Score: 0");
        playerScoreLabel.setText("Your Score: 0");
        ballsLeftLabel.setText("Balls Left: 12");
        scoreInput.setText(""); // Clear score input
        resultArea.setText(""); // Clear result area
    }

    private void playGame() {
        if (isUserBatting) {
            playUserBatting(); // Play user's turn
        } else {
            playComputerBatting(); // Play computer's turn
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new cricket(); // Start the game
            }
        });
    }
}
