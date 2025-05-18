package main;

import javax.swing.*;
import java.awt.*;

public class AboutPanel extends JPanel {
    private JFrame mainFrame;
    private MainMenuPanel previousPanel;

    public AboutPanel(MainMenuPanel previousPanel, JFrame mainFrame) {
        this.previousPanel = previousPanel;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 235));

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
        applyButtonStyle(backButton);

        backButton.addActionListener(e -> returnToMainMenu());

        topPanel.add(backButton, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        //content Panel -> for centering all text
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(240, 240, 235));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(Box.createVerticalStrut(40));

        // Mission Title
        JLabel missionTitle = new JLabel("🌍 OUR MISSION", SwingConstants.CENTER);
        missionTitle.setFont(new Font("Arial", Font.BOLD, 22));
        missionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(missionTitle);

        // Mission TEXT
        JLabel missionText = new JLabel(
                "<html><div style='text-align:center; width:700px;'>"
                        + "EcoJozi is a gamified simulation where players take on the role of developers building "
                        + "the EcoJozi app—an initiative tackling Johannesburg’s waste problem through sustainable waste "
                        + "management and community engagement."
                        + "</div></html>", SwingConstants.CENTER);
        missionText.setFont(new Font("Arial", Font.PLAIN, 16));
        missionText.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(missionText);

        contentPanel.add(Box.createVerticalStrut(30)); // **Spacing**

        // Conclusion Text
        JLabel conclusionText = new JLabel(
                "<html><div style='text-align:center; width:700px;'>"
                        + "EcoJozi blends education, technology, and community action, turning waste into a resource "
                        + "and empowering players to build a cleaner, more sustainable Johannesburg!"
                        + "</div></html>", SwingConstants.CENTER);
        conclusionText.setFont(new Font("Arial", Font.PLAIN, 16));
        conclusionText.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(conclusionText);

        // Making the content panel scrollable (only vertically, incase if too much text needed)
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void returnToMainMenu() {
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
