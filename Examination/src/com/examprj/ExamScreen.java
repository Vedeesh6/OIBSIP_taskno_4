package com.examprj;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

class Question {
    private String question;
    private String answer;
    private String[] options;

    public Question(String question, String answer, String[] options) {
        this.question = question;
        this.answer = answer;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String[] getOptions() {
        return options;
    }
}


public class ExamScreen extends JFrame{

    //private static final long serialVersionUID = 1L;
    private int score = 0;
    private int currentQuestionIndex = 0;
    private JLabel timerLabel;
    private Timer timer;
    private List<Question> questions;

    public ExamScreen() {
        // Set up the JFrame
        setTitle("Online Examination System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ExamScreen.class.getResource("/com/examprj/icon.png"))); // Change the icon

        // Create a JPanel for the timer
        JPanel timerPanel = new JPanel(new GridLayout(1, 2));
        timerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a label to display the remaining time
        timerLabel = new JLabel(String.valueOf("60"), SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timerPanel.add(timerLabel);

        // Create a JPanel for the question and answer buttons
        JPanel questionPanel = new JPanel(new GridLayout(6, 1));
        questionPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a JPanel for the submit button
        JPanel submitPanel = new JPanel(new GridLayout(1, 1));
        submitPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a list of questions
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", "Paris", new String[]{"Rome", "Madrid", "Paris", "Berlin"}));
        questions.add(new Question("What is the tallest mountain in the world?", "Mount Everest", new String[]{"Mount Kilimanjaro", "Mount Everest", "Mount Fuji", "Mount Denali"}));
        questions.add(new Question("What is the largest ocean in the world?", "Pacific", new String[]{"Atlantic", "Indian", "Arctic", "Pacific"}));
        questions.add(new Question("What is the capital of Japan?", "Tokyo", new String[]{"Kyoto", "Osaka", "Tokyo", "Hiroshima"}));
        questions.add(new Question("What is the currency of Australia?", "Australian dollar", new String[]{"US dollar", "Euro", "Australian dollar", "Pound Sterling"}));

        // Shuffle the questions so they appear in a random order
        Collections.shuffle(questions);

        // Add the first question and answer buttons to the panel
        updateQuestionPanel(questionPanel,submitPanel);

       // Create the submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Your score is " + score + " out of " + questions.size(), "Exam completed", JOptionPane.INFORMATION_MESSAGE);
                endQuiz();
            }
        });
        submitPanel.add(submitButton);

        // Add the timer panel, question panel, and submit panel to the JFrame
        add(timerLabel, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);

        // Create a timer that counts down from EXAM_TIME seconds
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int remainingTime = Integer.parseInt(timerLabel.getText());
                if (remainingTime > 0) {
                    remainingTime--;
                    timerLabel.setText(String.valueOf(remainingTime));
                } else {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Time's up! Your score is " + score + " out of " + questions.size(), "Time's up", JOptionPane.INFORMATION_MESSAGE);
                    endQuiz();
                }
            }
        });
        timer.start();
    }

    

private void updateQuestionPanel(JPanel questionPanel, JPanel submitPanel) {
    // Get the current question
    Question currentQuestion = questions.get(currentQuestionIndex);
    questionPanel.removeAll();

    // Create the question label
    JLabel questionLabel = new JLabel(currentQuestion.getQuestion());
    questionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
    questionPanel.add(questionLabel);

    // Create the answer buttons
    String[] options = currentQuestion.getOptions();
    for (int i = 0; i < options.length; i++) {
        JButton answerButton = new JButton(options[i]);
        answerButton.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add an action listener to check the answer and update the score
        answerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the answer is correct
                if (answerButton.getText().equals(currentQuestion.getAnswer())) {
                    score++;
                }

                // Move to the next question
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    updateQuestionPanel(questionPanel,submitPanel);
                } else {
                    // End the quiz when all questions have been answered
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Your score is " + score + " out of " + questions.size(), "Exam completed", JOptionPane.INFORMATION_MESSAGE);
                    endQuiz();
                }
            }
        });

        questionPanel.add(answerButton);
    }

    // Update the content pane
    getContentPane().removeAll();
    getContentPane().add(timerLabel, BorderLayout.NORTH);
    getContentPane().add(questionPanel, BorderLayout.CENTER);
    getContentPane().add(submitPanel, BorderLayout.SOUTH);
    revalidate();
    repaint();
}
protected void endQuiz() {
    // Read the username from the credentials file
    String username = null;
    try {
        Scanner reader = new Scanner(LoginScreen.class.getResourceAsStream("/com/examprj/credentials.txt"));
        username = reader.nextLine();
        reader.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    // Return to the profile screen with the correct username
    ProfileScreen profileScreen = new ProfileScreen(username);
    profileScreen.setVisible(true);
    dispose();
}
}

