
package main;

import javax.swing.*;
import java.awt.*;

public class GoodEndingPanel extends JPanel {

    private JFrame mainFrame;
    private MainMenuPanel previousPanel;
    

    public GoodEndingPanel(MainMenuPanel previousPanel, JFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        // If previousPanel is null, create a new main menu panel
        if (previousPanel == null) {
            this.previousPanel = new MainMenuPanel(new Game().getPlayers(), mainFrame);
        } else {
            this.previousPanel = previousPanel;
        }

        setLayout(new BorderLayout());
        setBackground(new Color(245, 235, 210));

        //back Button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 235));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        JButton backButton = new JButton("Return to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> returnToMainMenu());

        topPanel.add(backButton, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        //content Panel -> for centering all text
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(240, 240, 235));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createVerticalStrut(40));

        // EndGame Title
        JLabel title = new JLabel("Success!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(title);

        //  explanation
        JLabel gameSubtext = new JLabel(
                "<html><div style='text-align:center; width:700px;'>"
                        + "Congratulations! You have completed the game. Your app was deployed successfully" +
                        " and has been a huge hit within Makers Valley and the rest of Johannesburg! Many people have taken the free" +
                        " lessons about recycling and sustainability, and have taken part in community organised cleanups advertised" +
                        " through EcoJozi. The number of illegal dumping sites throughout the area has been slowly shrinking due to" +
                        " people knowing where they can dispose of  their waste sustainably from the map feature, and also more people have" +
                        " been able to find and clean up the illegal dumping sites through the community reporting map! " +
                        "The app is growing in popularity every day and could soon gain traction outside of Johannesburg!" +
                        " Return to the menu to play again, or return to the game to see the final state of the board before this screen. "
                        + "</div></html>", SwingConstants.CENTER);
        gameSubtext.setFont(new Font("Arial", Font.PLAIN, 16));
        gameSubtext.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(gameSubtext);
        contentPanel.add(Box.createVerticalStrut(30)); // **Even Spacing**

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
}

