package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private final JFrame mainFrame;
    private final MainMenuPanel mainMenuPanel;
    private static boolean colourblindMode = false;

    public SettingsPanel(MainMenuPanel mainMenuPanel, JFrame mainFrame) {
        this.mainMenuPanel = mainMenuPanel;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 235, 210));

        // Back Button - aligned left
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

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 28));
        centerPanel.add(settingsLabel, gbc);
        
        // Disclaimer Label (Warns users that settings cannot be changed in-game)
        gbc.gridy++;
        JLabel disclaimerLabel = new JLabel("<html><center><i>Settings cannot be changed once the game has started.</i></center></html>");
        disclaimerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        disclaimerLabel.setForeground(Color.RED); // Make it stand out
        centerPanel.add(disclaimerLabel, gbc);

        
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // toggle button - colorblind mode
        JToggleButton colorblindToggle = new JToggleButton();
        updateToggleButtonText(colorblindToggle, colourblindMode);
        colorblindToggle.setFont(new Font("Arial", Font.BOLD, 16));
        colorblindToggle.setPreferredSize(new Dimension(250, 40));
        colorblindToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colourblindMode = !colourblindMode;
                updateToggleButtonText(colorblindToggle, colourblindMode);
                Board.updateBoardColours();

            }
        });
        centerPanel.add(colorblindToggle, gbc);
        gbc.gridy++;
        add(centerPanel, BorderLayout.CENTER);
    }

    private void returnToMainMenu() {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(mainMenuPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    public static boolean checkColourblindMode() {
        return colourblindMode;
    }

    private void updateToggleButtonText(JToggleButton button, boolean state) {
        button.setText("Colorblind Mode" + ": " + (state ? "ON" : "OFF"));
        button.setSelected(state);
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