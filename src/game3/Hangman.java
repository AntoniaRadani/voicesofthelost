package game3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class Hangman extends JFrame {
    private final String[] words = {"saved", "dungeon", "curse", "battle", "survival"};
    private String word;
    private char[] guessedWord;
    private int attempts = 6;
    private boolean won = false;
    private final HashSet<Character> guessedLetters = new HashSet<>();
    private JLabel wordLabel, attemptsLabel, messageLabel, timerLabel;
    private JTextField inputField;
    private JTextArea incorrectGuessesArea;
    private JButton guessButton, restartButton;
    private Timer timer;
    private int timeLeft = 60;

    private JPanel gallowsPanel;

    public Hangman() {
        setTitle("Hangman Game");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        setContentPane(new JLabel(new ImageIcon("res/game3/background.png")));
        setLayout(new BorderLayout());

        initializeGame();


        Font gameFont = new Font("Luminari", Font.PLAIN, 22);


        gallowsPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGallows(g);
            }
        };
        gallowsPanel.setPreferredSize(new Dimension(250, 500));
        gallowsPanel.setOpaque(false);
        add(gallowsPanel, BorderLayout.WEST);

        // info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        wordLabel = new JLabel();
        wordLabel.setFont(gameFont.deriveFont(28f));
        wordLabel.setForeground(new Color(255, 204, 51));
        infoPanel.add(wordLabel);

        attemptsLabel = new JLabel();
        attemptsLabel.setFont(gameFont);
        attemptsLabel.setForeground(new Color(255, 204, 51));
        infoPanel.add(attemptsLabel);

        timerLabel = new JLabel();
        timerLabel.setFont(gameFont);
        timerLabel.setForeground(new Color(255, 204, 51));
        infoPanel.add(timerLabel);

        add(infoPanel, BorderLayout.NORTH);

        // input panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        messageLabel = new JLabel("Enter a letter or guess the word:");
        messageLabel.setFont(gameFont.deriveFont(20f));
        messageLabel.setForeground(new Color(255, 204, 51));
        centerPanel.add(messageLabel);

        inputField = new JTextField();
        inputField.setFont(gameFont);
        centerPanel.add(inputField);

        // butoane
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        guessButton = new JButton("Guess");
        styleButton(guessButton);
        guessButton.addActionListener(new GuessButtonListener());
        buttonPanel.add(guessButton);

        restartButton = new JButton("Restart");
        styleButton(restartButton);
        restartButton.addActionListener(e -> restartGame());
        buttonPanel.add(restartButton);

        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);

        // raspunsuri gresite
        incorrectGuessesArea = new JTextArea("Incorrect guesses: ");
        incorrectGuessesArea.setEditable(false);
        incorrectGuessesArea.setFont(gameFont);
        incorrectGuessesArea.setForeground(new Color(255, 204, 51));
        incorrectGuessesArea.setOpaque(false);
        add(incorrectGuessesArea, BorderLayout.SOUTH);

        updateDisplay();
        startTimer();
    }

    private void drawGallows(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(153, 0, 0));
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(50, 300, 150, 300); // base
        g2.drawLine(100, 300, 100, 50); // pole
        g2.drawLine(100, 50, 140, 50);  // beam
        g2.drawLine(140, 50, 140, 80);  // rope

        int wrong = 6 - attempts;
        g2.setColor(Color.BLACK);
        if (wrong > 0) g2.drawOval(125, 80, 30, 30);      // head
        if (wrong > 1) g2.drawLine(140, 110, 140, 170);   // body
        if (wrong > 2) g2.drawLine(140, 120, 120, 150);   // left arm
        if (wrong > 3) g2.drawLine(140, 120, 160, 150);   // right arm
        if (wrong > 4) g2.drawLine(140, 170, 120, 200);   // left leg
        if (wrong > 5) g2.drawLine(140, 170, 160, 200);   // right leg
    }

    private void styleButton(JButton b) {
        b.setBackground(new Color(139, 69, 19));
        b.setForeground(Color.GRAY);
        b.setFont(new Font("Luminari", Font.BOLD, 18));
        b.setFocusPainted(false);
    }

    private void initializeGame() {
        word = words[(int) (Math.random() * words.length)];
        guessedWord = "_".repeat(word.length()).toCharArray();
        guessedLetters.clear();
        attempts = 6;
        timeLeft = 60;
        won = false;
    }

    private void updateDisplay() {
        wordLabel.setText("Word: " + new String(guessedWord));
        attemptsLabel.setText("Attempts remaining: " + attempts);
        timerLabel.setText("Time remaining: " + timeLeft + " seconds");
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                timeLeft--;
                SwingUtilities.invokeLater(() -> timerLabel.setText("Time remaining: " + timeLeft + " seconds"));
                if (timeLeft <= 0) {
                    timer.cancel();
                    SwingUtilities.invokeLater(() -> gameOver("Time's up! The word was: " + word));
                }
            }
        }, 1000, 1000);
    }

    private void gameOver(String msg) {
        messageLabel.setText("Congratulations!");
        messageLabel.setText(msg);
        guessButton.setEnabled(false);

        if (timer != null) {
            timer.cancel();
        }


        new javax.swing.Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setText("You passed this stage! ");
                ((javax.swing.Timer) e.getSource()).stop(); // oprim timer ul
                dispose(); // inchidem fortat fereastra
            }
        }).start();
    }

    private void restartGame() {
        initializeGame();
        updateDisplay();
        messageLabel.setText("Enter a letter or guess the word:");
        incorrectGuessesArea.setText("Incorrect guesses: ");
        guessButton.setEnabled(true);
        gallowsPanel.repaint();
        startTimer();
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText().trim().toLowerCase();
            inputField.setText("");
            inputField.requestFocus();

            if (input.isEmpty()) return;

            if (input.length() == 1) {
                char guess = input.charAt(0);
                if (guessedLetters.contains(guess)) {
                    messageLabel.setText("Already guessed: " + guess);
                    return;
                }

                guessedLetters.add(guess);
                boolean correct = false;
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == guess) {
                        guessedWord[i] = guess;
                        correct = true;
                    }
                }

                if (!correct) {
                    attempts--;
                    incorrectGuessesArea.append(guess + " ");
                    messageLabel.setText("Wrong guess!");
                } else {
                    messageLabel.setText("Correct!");
                }

                if (new String(guessedWord).equals(word)) {
                    gameOver("You won! The word was: " + word);
                } else if (attempts == 0) {
                    gameOver("No attempts left. The word was: " + word);
                }

                updateDisplay();
                gallowsPanel.repaint();

            } else if (input.equals(word)) {
                guessedWord = word.toCharArray();
                gameOver("You guessed the word!");
                updateDisplay();
                gallowsPanel.repaint();
            } else {
                attempts--;
                messageLabel.setText("Wrong guess!");
                updateDisplay();
                gallowsPanel.repaint();
                if (attempts == 0) {
                    gameOver("No attempts left. The word was: " + word);
                }
            }
        }
    }
}