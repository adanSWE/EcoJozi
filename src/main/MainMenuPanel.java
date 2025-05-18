package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenuPanel extends JPanel {
    private final ArrayList<Player> players;
    private final JFrame mainFrame;

    public MainMenuPanel(ArrayList<Player> players, JFrame mainFrame) {
        this.players = players;
        this.mainFrame = mainFrame;
        showMainMenu();
    }

    private void showMainMenu() {
        removeAll();
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 235, 210));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Logo
        gbc.insets = new Insets(20, 10, 10, 10);
        ImageIcon welcomeIcon = new ImageIcon("src/main/images/EcoJozi_Full.png");
        JLabel welcomeLabel = new JLabel(welcomeIcon);
        Image image = welcomeIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        welcomeIcon = new ImageIcon(image);
        welcomeLabel.setIcon(welcomeIcon);
        add(welcomeLabel, gbc);

        // Reduce spacing between buttons
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 0);

        JButton startGameButton = new JButton("Start Game");
        styleMenuButton(startGameButton);
        startGameButton.addActionListener(e -> showPlayerSelection());
        add(startGameButton, gbc);

        gbc.gridy++;
        JButton settingsButton = new JButton("Settings");
        styleMenuButton(settingsButton);
        settingsButton.addActionListener(e -> showSettings());
        add(settingsButton, gbc);

        gbc.gridy++;
        JButton tutorialButton = new JButton("Tutorial");
        styleMenuButton(tutorialButton);
        tutorialButton.addActionListener(e -> tutorial());
        add(tutorialButton, gbc);

        gbc.gridy++;
        JButton helpButton = new JButton("About");
        styleMenuButton(helpButton);
        helpButton.addActionListener(e -> aboutMenu());
        add(helpButton, gbc);

        revalidate();
        repaint();
    }

    private void showPlayerSelection() {
        removeAll();
        setLayout(new BorderLayout()); // Use BorderLayout for consistency
        setBackground(new Color(240, 240, 235));

        // ---- Top Panel for Back Button ----
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 235));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0)); // Same padding

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(100, 30));
        applyButtonStyle(backButton); // Apply the same button style

        backButton.addActionListener(e -> showMainMenu());

        topPanel.add(backButton, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH); // Ensures exact same position as other panels

        // ---- Main Content Panel (Centered) ----
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 240, 235));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 20, 10);

        JLabel questionLabel = new JLabel("How many players?");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(questionLabel, gbc);

        gbc.gridy++;
        JTextField playerInput = new JTextField(5);
        playerInput.setFont(new Font("Arial", Font.PLAIN, 18));
        playerInput.setHorizontalAlignment(JTextField.CENTER);
        playerInput.setPreferredSize(new Dimension(100, 30));
        contentPanel.add(playerInput, gbc);

        gbc.gridy++;
        JButton submitButton = new JButton("Submit");
        applyButtonStyle(submitButton);
        submitButton.addActionListener(e -> handlePlayerCountSubmission(playerInput));
        contentPanel.add(submitButton, gbc);

        add(contentPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    // Ensure you have this method for consistent button styling
    private void applyButtonStyle(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2)); // Dark green border
        button.setBackground(new Color(60, 179, 113)); // Medium Sea Green
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
                button.setForeground(Color.LIGHT_GRAY);
            }
        });
    }


    private void handlePlayerCountSubmission(JTextField playerInput) {
        String input = playerInput.getText();
        try {
            int numPlayers = Integer.parseInt(input);
            if (numPlayers < 2 || numPlayers > 4) {
                JOptionPane.showMessageDialog(mainFrame, "Please enter a number between 2 and 4.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } else {
                setupNameInputPanel(numPlayers);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupNameInputPanel(int numPlayers) {
        removeAll();
        setLayout(new BorderLayout()); // Use BorderLayout for consistent layout
        setBackground(new Color(240, 240, 235));

        // ---- Top Panel for Back Button ----
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

        backButton.addActionListener(e -> showPlayerSelection()); // Go back to the previous screen

        topPanel.add(backButton, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH); // Ensures the back button is on the top left

        // ---- Main Content Panel ----
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 240, 235));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Enter Player Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;

        ArrayList<JTextField> nameFields = new ArrayList<>();
        ArrayList<JComboBox<String>> characterDropdowns = new ArrayList<>();
        String[] availableCharacters = {"🍳", "🏭", "🌍","🚑"};

        for (int i = 0; i < numPlayers; i++) {
            gbc.gridx = 0;
            JLabel nameLabel = new JLabel("Player " + (i + 1) + " Name:");
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            contentPanel.add(nameLabel, gbc);

            gbc.gridx = 1;
            JTextField nameField = new JTextField(10);
            nameField.setFont(new Font("Arial", Font.PLAIN, 16));
            nameFields.add(nameField);
            contentPanel.add(nameField, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            JLabel characterLabel = new JLabel("Select Character:");
            characterLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
            contentPanel.add(characterLabel, gbc);

            gbc.gridx = 1;
            JComboBox<String> characterDropdown = new JComboBox<>(availableCharacters);
            characterDropdown.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            characterDropdowns.add(characterDropdown);
            contentPanel.add(characterDropdown, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            contentPanel.add(Box.createVerticalStrut(10), gbc);
            gbc.gridy++;
        }

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton nameSubmitButton = new JButton("Start Game");
        applyButtonStyle(nameSubmitButton);
        nameSubmitButton.addActionListener(e -> handleNameSubmission(nameFields, characterDropdowns));
        contentPanel.add(nameSubmitButton, gbc);

        add(contentPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }


    private void showSettings() {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(new SettingsPanel(this, mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void tutorial() {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(new TutorialPanel(this, mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void aboutMenu() {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(new AboutPanel(this, mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void handleNameSubmission(ArrayList<JTextField> nameFields, ArrayList<JComboBox<String>> characterDropdowns) {
        ArrayList<String> playerNames = new ArrayList<>();
        ArrayList<String> selectedCharacters = new ArrayList<>();

        for (int i = 0; i < nameFields.size(); i++) {
            String playerName = nameFields.get(i).getText().trim();
            String selectedCharacter = (String) characterDropdowns.get(i).getSelectedItem();

            if (playerName.isEmpty() || playerNames.contains(playerName) || selectedCharacters.contains(selectedCharacter)) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid Input. Either empty name or duplicate characters", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            playerNames.add(playerName);
            selectedCharacters.add(selectedCharacter);
        }

        for (int i = 0; i < playerNames.size(); i++) {
            Player player = new Player(playerNames.get(i));
            player.setCharacter(selectedCharacters.get(i));
            players.add(player);
        }

        // Adjusting starting resources based on number of players
        adjustStartingResources(players.size());

        // initializing the game GUI and board
        Board gui = new Board(new Game(players));
        JPanel gameBoardPanel = gui.initializeGameBoard();

        mainFrame.getContentPane().removeAll();
        mainFrame.add(gameBoardPanel);
        mainFrame.revalidate();
        mainFrame.repaint();

        // making sure the colorblind mode updates after the game board is visible
        SwingUtilities.invokeLater(gui::applyBoardColours);
    }

    /**
     * Adjusts each player's starting resources based on the number of players in the game.
     * More players means fewer resources per player to maintain game balance.
     * @param playerCount The number of players in the game
     */
    private void adjustStartingResources(int playerCount) {
        // Mapping player count to starting resources amount
        int startingResources = switch (playerCount) {
            case 2 -> 3000;
            case 3 -> 2000;
            case 4 -> 1500;
            default -> 0;
        };

        // Applying the resources to all players
        if (startingResources > 0) {
            for (int i = 0; i < playerCount; i++) {
                players.get(i).addResources(startingResources);
            }
        }
    }

    protected void styleMenuButton(JButton button) {
        // Get DPI scale factor (using 96 DPI as baseline)
        float dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        float scaleFactor = dpi / 96.0f;

        // Scale the font and dimensions based on the DPI factor
        int fontSize = (int)(14 * scaleFactor);
        button.setFont(new Font("Arial", Font.BOLD, fontSize));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2));
        button.setBackground(new Color(60, 179, 113));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        int baseWidth = 180;
        int baseHeight = 45;
        int scaledWidth = (int)(baseWidth * scaleFactor);
        int scaledHeight = (int)(baseHeight * scaleFactor);

        Dimension buttonSize = new Dimension(scaledWidth, scaledHeight);
        // Force the same size by setting preferred, minimum, and maximum sizes
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setRolloverEnabled(true);
        button.addChangeListener(e -> {
            if (button.getModel().isRollover()) {
                button.setBackground(new Color(72, 209, 146)); // Lighter green for hover
            } else {
                button.setBackground(new Color(60, 179, 113));
            }
        });
    }
}