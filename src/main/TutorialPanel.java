package main;

import javax.swing.*;
import java.awt.*;

public class TutorialPanel extends JPanel {
    private final JFrame mainFrame;
    private final MainMenuPanel previousPanel;

    public TutorialPanel(MainMenuPanel previousPanel, JFrame mainFrame) {
        this.previousPanel = previousPanel;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 235, 210));

        //back Button Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 235));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(e -> returnToMainMenu());
        applyButtonStyle(backButton);
        topPanel.add(backButton, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        //content Panel -> for centering all text
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(240, 240, 235));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(Box.createVerticalStrut(40));

        // Tutorial Title
        JLabel title = new JLabel("Tutorial: How to Play", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(title);

        // Game explanation
        JLabel gameSubtext = new JLabel(
                "<html><div style='text-align:center; width:700px;'>"
                        + "This game is a simulation which aims to help people take on the role " +
                        "of developers building an app to help the Makers Valley Community. Players will have to " +
                        "take on tasks relating to the cycle of app development, in order to get an understanding of " +
                        "what work may need to be done in order to create this app."
                        + "</div></html>", SwingConstants.CENTER);
        gameSubtext.setFont(new Font("Arial", Font.PLAIN, 16));
        gameSubtext.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(gameSubtext);

        contentPanel.add(Box.createVerticalStrut(30)); // **Even Spacing**

        // Objectives Title
        JLabel objectivesTitle = new JLabel("Objectives and Tasks", SwingConstants.CENTER);
        objectivesTitle.setFont(new Font("Arial", Font.BOLD, 18));
        objectivesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(objectivesTitle);

        // Objectives Text
        JLabel objectivesText = new JLabel(
                "<html><div style='text-align:center; width:700px;'>"
                        + "The game features four objectives, each with a set of four tasks.  Each task has a deposit, either 250 or 500 rands (the local currency in South Africa). "
                        + "As you roll the dice to traverse the board, you can claim ownership of tasks by paying their deposit. " +
                        "In doing so, the task becomes your responsibility to complete."
                        + "</div></html>", SwingConstants.CENTER);
        objectivesText.setFont(new Font("Arial", Font.PLAIN, 16));
        objectivesText.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(objectivesText);
        contentPanel.add(Box.createVerticalStrut(30)); // **Even Spacing**

        // Tasks Title
        JLabel tasksTitle = new JLabel("Completing Tasks", SwingConstants.CENTER);
        tasksTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tasksTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(tasksTitle);

        // Tasks Text
        JLabel tasksText = new JLabel(
                "<html><div style='text-align:center; width:700px;'>"
                        + "Pressing the 'Claim Task' button allows you to purchase the task you are currently on. "
                        + "Once purchased, you will be able to use the 'Allocate Resources' button, to spend rands on your owned tasks. "
                        + "When you have allocated enough rands to complete the task, 500 rands will be added to your balance, and the task will be marked as completed. "
                        + "</div></html>", SwingConstants.CENTER);
        tasksText.setFont(new Font("Arial", Font.PLAIN, 16));
        tasksText.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(tasksText);
        contentPanel.add(Box.createVerticalStrut(30)); // **Even Spacing**

        // Alliance Title
        JLabel allianceTitle = new JLabel("Forming Alliances", SwingConstants.CENTER);
        allianceTitle.setFont(new Font("Arial", Font.BOLD, 18));
        allianceTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(allianceTitle);

        // Alliance Text
        JLabel allianceText = new JLabel(
                "<html><div style='text-align:center; width:700px;'>"
                        + "The 'Form Alliance' button allows you to team up with another player of your choosing in order to share ownership"
                        + " of tasks.  Once you have selected another player and the alliance has been formed, either one of you should be able " +
                        " to allocate resources to each others tasks. Its encouraged that you form alliances with other players to help each other out. The alliance" +
                        " will be reflected in the task list, showing which tasks are owned by the group."
                        + "</div></html>", SwingConstants.CENTER);
        allianceText.setFont(new Font("Arial", Font.PLAIN, 16));
        allianceText.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(allianceText);
        contentPanel.add(Box.createVerticalStrut(30)); // **Even Spacing**

        // Transfer Title
        JLabel transferTitle = new JLabel("Transfering Tasks", SwingConstants.CENTER);
        transferTitle.setFont(new Font("Arial", Font.BOLD, 18));
        transferTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(transferTitle);

        // Transfer Text
        JLabel transferText = new JLabel(
                "<html><div style='text-align:center; width:700px;'>"
                        + "The 'Transfer Task' button allows you to transfer ownership of a task to another player. " +
                        " Pressing this button will allow you to select a player from the dropdown menu, " +
                        "and then transfer the task you are currently on to that player. "
                        + "</div></html>", SwingConstants.CENTER);
        transferText.setFont(new Font("Arial", Font.PLAIN, 16));
        transferText.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(transferText);
        contentPanel.add(Box.createVerticalStrut(30)); // **Even Spacing**

        // EndGame Title
        JLabel endGameTitle = new JLabel("Winning the Game", SwingConstants.CENTER);
        endGameTitle.setFont(new Font("Arial", Font.BOLD, 18));
        endGameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(endGameTitle);

        // EndGame Text
        JLabel endGameText = new JLabel(
                "<html><div style='text-align:center; width:700px;'>"
                        + "In order to win the game, all tasks must be completed. " +
                        "Teamwork and alliances between players will make this much easier and is encouraged," +
                        " as if all players run out of resources, the game will end! Work together" +
                        " and help your fellow players in order to complete the simulation! "
                        + "</div></html>", SwingConstants.CENTER);
        endGameText.setFont(new Font("Arial", Font.PLAIN, 16));
        endGameText.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(endGameText);

        // Making the content panel scrollable if necessary for different screen sizes
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
    }

    protected void returnToMainMenu() {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(previousPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void applyButtonStyle(JButton button) {
        // Base styling:
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2)); // Dark green border
        button.setBackground(new Color(60, 179, 113)); // Medium Sea Green (eco green)
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 45));
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.addPropertyChangeListener("enabled", evt -> {
            boolean enabled = (Boolean) evt.getNewValue();
            if (enabled) {
                button.setBackground(new Color(60, 179, 113)); // Eco green
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.GRAY);
                button.setForeground(Color.LIGHT_GRAY); // Ensures the text is legible on a gray background.
            }
        });
    }
}
