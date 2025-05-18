/*
 *
 * simple hierarchy in swing:
 * frame: this is the main window of the app
 * 		panels: these exist within the main frame, they contain buttons, labels etc (helps organise GUI)
 * 			labels: basically these are used for display, you can use html for formatting text
 *
 * link: https://docs.oracle.com/javase/tutorial/uiswing///components/html.html
 */

package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import javax.swing.JOptionPane;
import java.util.stream.Collectors;

public class Board {

    // Core game and GUI components
    private final Game game;
    private JTextArea gameLog;
    private JPanel playersPanel;
    private JButton rollDiceButton;
    private JButton endTurnButton;


    // Board square labels
    private final String[] squareLabels = {
            "Start", "Define Goals", "Designing MVC Architecture", "Random Event", "Develop Core Functionalities",
            "Conduct Unit and Integration Testing", "Random Event", "Research Community Needs",
            "Visual Design and Branding", "Random Event", "Integrate APIs and External Data",
            "Beta Testing with Local Users", "Hire Developers", "Random Event", "Prototype Core Features",
            "Set Up Databases", "Random Event", "Performance and Stress Testing",
            "Budget and Resource Planning", "Random Event", "Accessibility and Inclusivity",
            "Developing User Interface", "Feedback and Issue Resolution", "Random Event", "Start"
    };

    // Board and task tracking
    private ArrayList<Task> boardTasks;
    private final List<JLabel> boardSquares; // Linear representation of board squares
    private boolean hasRolledDice; // to track

    // Objective categories
    private final List<Task> planningTasks;
    private final List<Task> designTasks;
    private final List<Task> developmentTasks;
    private final List<Task> testingTasks;

    // Panels for the board and tasks
    private JPanel boardPanel; // Main board panel
    private JPanel objectivePanel;

    // Board instance
    private static Board instance;
    
    

    // Constructor
    public Board(Game game) {
        this.game = game;
        this.boardSquares = new ArrayList<>();

        // these arraylists are for the objectives panel & linking to the boardTasks list
        planningTasks = new ArrayList<>();
        designTasks = new ArrayList<>();
        developmentTasks = new ArrayList<>();
        testingTasks = new ArrayList<>();

        // Planning
        planningTasks.add(new Task("Planning", 1000, null, 250,  "Define Goals"));
        planningTasks.add(new Task("Planning", 1000, null, 250,  "Research Community Needs"));
        planningTasks.add(new Task("Planning", 1500, null, 500,  "Budget and Resource Planning"));
        planningTasks.add(new Task("Planning", 1500, null, 500,  "Hire Developers"));

        // Design
        designTasks.add(new Task("Design", 1000, null, 250,  "Designing MVC Architecture"));
        designTasks.add(new Task("Design", 1500, null, 500, "Visual Design and Branding"));
        designTasks.add(new Task("Design", 1500, null, 500, "Prototype Core Features"));
        designTasks.add(new Task("Design", 1000, null, 250,  "Accessibility and Inclusivity"));

        // Development
        developmentTasks.add(new Task("Development", 1500, null, 500, "Develop Core Functionalities"));
        developmentTasks.add(new Task("Development", 1000, null, 250,  "Integrate API's and External Data"));
        developmentTasks.add(new Task("Development", 1000, null, 250,  "Set up Databases"));
        developmentTasks.add(new Task("Development", 1500, null, 500, "Developing User Interface"));

        // Testing
        testingTasks.add(new Task("Testing", 1000, null,  250,  "Conduct Unit and Integration Testing"));
        testingTasks.add(new Task("Testing", 1500, null,  500,  "Beta Testing with Local Users"));
        testingTasks.add(new Task("Testing", 1500, null,  500,  "Performance and Stress Testing"));
        testingTasks.add(new Task("Testing", 1000, null, 250,  "Feedback and Issue Resolution"));

        initialiseBoardTasks();

        instance = this; // Storing for global access within code
    }


    
    private void initialiseBoardTasks() {
        int boardSize = 24;
        boardTasks = new ArrayList<>(boardSize);
        // Filling boardTasks with null placeholders
        for (int i = 0; i < boardSize; i++) {
            boardTasks.add(null);
        }

        // Setting start square
        boardTasks.set(0, new Task("Start", 0,  null, 0, "Start"));
        // Setting random event squares
        int[] eventPositions = {3, 6, 9, 13, 16, 19, 23};
        for (int pos : eventPositions) {
            boardTasks.set(pos, getRandomEvent());
        }

        // 3. Setting task squares
        int[] objectivePositions = {1, 2, 4, 5, 7, 8, 10, 11, 12, 14, 15, 17, 18, 20, 21, 22};

        int planningIndex = 0;
        int designIndex = 0;
        int developmentIndex = 0;
        int testingIndex = 0;
        
        for (int i = 0; i < objectivePositions.length; i++) {
            int pos = objectivePositions[i];
            int cycle = i % 4;
            Task task = null;
            switch (cycle) {
                case 0: // Planning
                	if (pos == 12) {  // Forcing "Hire Developers" to position 12
                        task = planningTasks.get(3);
                    } else if (pos == 18) {  // Forcing "Budget and Resource Planning" to position 18
                        task = planningTasks.get(2);
                    } else if (planningIndex < planningTasks.size()) {
                        task = planningTasks.get(planningIndex++);
                    }
                    break;
                case 1: // Design
                    if (designIndex < designTasks.size()) {
                        task = designTasks.get(designIndex++);
                    }
                    break;
                case 2: // Development
                    if (developmentIndex < developmentTasks.size()) {
                        task = developmentTasks.get(developmentIndex++);
                    }
                    break;
                case 3: // Testing
                    if (testingIndex < testingTasks.size()) {
                        task = testingTasks.get(testingIndex++);
                    }
                    break;
            }
            
            
            boardTasks.set(pos, task);
        }
        
    }
    

    /*
     * Creates an event square by randomly choosing between a Good_Event and a Bad_Event.
     * We use the enums from package main to pick an event, then create a Task that represents
     * an event square. In this Task, the 'claimable' flag is set to false and the event enum
     * is passed as a reference (assuming Task is designed to hold such data).
     */
    private Task getRandomEvent() {
        Random random = new Random();
        boolean chooseGood = random.nextBoolean(); // 50/50 chance

        if (chooseGood) {
            List<Good_Events> goodEvents = Good_Events.getGoodEvent();
            Good_Events selectedEvent = goodEvents.get(random.nextInt(goodEvents.size()));
            return new Task("Random Event", 0,  null, 0, selectedEvent.getDescription()) {
                @Override
                public void applyEffect(Player player) {
                    selectedEvent.applyEffect(player);
                }
            };
        } else {
            List<Bad_Events> badEvents = Bad_Events.getBadEvent(); // method returns list of Bad_Events
            Bad_Events selectedEvent = badEvents.get(random.nextInt(badEvents.size()));
            return new Task("Random Event", 0,  null, 0, selectedEvent.getDescription()) {
                @Override
                public void applyEffect(Player player) {
                    selectedEvent.applyEffect(player);
                    updatePlayerPosition(player);
                }
            };
        }
    }

    public void applyBoardColours() {
        boolean colourblindMode = SettingsPanel.checkColourblindMode();

        Color[] normalColors = {
                new Color(167, 199, 231), // Pastel Blue
                new Color(248, 212, 163), // Pastel Orange
                new Color(178, 230, 161), // Pastel Green
                new Color(209, 178, 240)  // Pastel Purple
        };

        // colourblind palette
        Color[] colourblindColours = {
                new Color(0, 119, 200),   // Deep Blue (for blue)
                new Color(255, 140, 0),   // Stronger Orange (for orange)
                new Color(189, 183, 107), // Olive (used here for green)
                new Color(147, 112, 219)  // Adjusted Purple (for purple)
        };

        // Forced red colors for the two modes:
        Color forcedRedNormal = new Color(244, 166, 166); // Pastel Red (normal mode)
        Color forcedRedColourblind = new Color(204, 85, 0);  // Darker Red (colourblind mode)

        if (boardSquares == null || boardSquares.isEmpty()) {
            System.out.println("Board squares list is empty! Colours not applied.");
            return;
        }

        // Counter to track non-forced squares only (so the cyclic order is maintained)
        int normalColourCounter = 0;

        // updating board squares
        for (int i = 0; i < boardSquares.size(); i++) {
            JLabel square = boardSquares.get(i);
            if (square == null) continue;

            square.setOpaque(true);

            if (i == 0) {
                // Always set the first square to Pastel Yellow: (250, 243, 168)
                square.setBackground(new Color(250, 243, 168));
            }
            // Override specific indices with forced red:
            else if (i == 3 || i == 6 || i == 9 || i == 13 || i == 16 || i == 19 || i == 23) {
                if (colourblindMode) {
                    square.setBackground(forcedRedColourblind);
                } else {
                    square.setBackground(forcedRedNormal);
                }
            }
            // For all other squares, apply the cyclic pattern (starting from blue at i == 1)
            else {
                if (colourblindMode) {
                    square.setBackground(colourblindColours[normalColourCounter % colourblindColours.length]);
                } else {
                    square.setBackground(normalColors[normalColourCounter % normalColors.length]);
                }
                normalColourCounter++; // Increment only when a cyclic colour is applied
            }
            square.repaint();
        }

        // Updating objective task List Boxes
        Component[] phasePanels = objectivePanel.getComponents();
        for (int i = 0; i < phasePanels.length; i++) {
            if (phasePanels[i] instanceof JPanel phasePanel) {

                for (Component comp : phasePanel.getComponents()) {
                    if (comp instanceof JTextArea taskList) {
                        taskList.setBackground(colourblindMode
                                ? colourblindColours[i % colourblindColours.length]
                                : Color.WHITE);
                        taskList.repaint();
                    }
                }
            }
        }

        // Refreshing UI
        boardPanel.revalidate();
        boardPanel.repaint();
        objectivePanel.revalidate();
        objectivePanel.repaint();
        System.out.println("Colorblind mode applied successfully!");
    }

    public static void updateBoardColours() {
        if (instance != null) {
            instance.applyBoardColours();
        }
    }
    
    private void openSettingsPanel() {
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(boardPanel);
        mainFrame.getContentPane().removeAll();
        mainFrame.add(new SettingsPanel(new MainMenuPanel(game.getPlayers(), mainFrame), mainFrame));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public JPanel initializeGameBoard() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 235));
        
        // ---- Top Panel for Settings Button ----
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 235));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel to keep settings button at the top-right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        buttonPanel.setOpaque(false); 

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Arial", Font.BOLD, 14));
        settingsButton.setFocusPainted(false);
        settingsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        settingsButton.setContentAreaFilled(true);
        settingsButton.setBackground(new Color(60, 179, 113)); // Same green as other buttons
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingsButton.setPreferredSize(new Dimension(120, 35));

        settingsButton.addActionListener(e -> openSettingsPanel());

        buttonPanel.add(settingsButton);
        topPanel.add(buttonPanel, BorderLayout.EAST); // Ensure it stays at the top-right
        mainPanel.add(topPanel, BorderLayout.NORTH); // Add to main panel
        
        
        // objective panels - top
        objectivePanel = createTaskPhasesPanel();
        objectivePanel.setOpaque(false);
        mainPanel.add(objectivePanel, BorderLayout.NORTH);

        // player display panel - left Side
        JPanel playersDisplayPanel = new JPanel();
        playersPanel = playersDisplayPanel;
        updatePlayersPanel(playersDisplayPanel, game.getPlayers());
        playersDisplayPanel.setPreferredSize(new Dimension(220, 0)); // fixed width
        playersDisplayPanel.setOpaque(false);

        // board panel - center
        boardPanel = createBoardPanel();
        boardPanel.setOpaque(true);

        applyBoardColours(); // if on - colourblind colors are displayed

        // displaying all players on the Start square initially
        initializePlayerPositions();

        // game log + player Menu - right Side
        JPanel gameLogPanel = createGameLogPanel();
        JPanel moveMenuPanel = createMoveMenuPanel(playersDisplayPanel);

        gameLogPanel.setOpaque(false);
        moveMenuPanel.setOpaque(false);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // manual layout control
        rightPanel.setPreferredSize(new Dimension(260, boardPanel.getPreferredSize().height));
        rightPanel.setOpaque(false);

        int panelHeight = boardPanel.getPreferredSize().height;
        gameLogPanel.setPreferredSize(new Dimension(260, (int) (panelHeight * 0.35)));
        moveMenuPanel.setPreferredSize(new Dimension(260, (int) (panelHeight * 0.55)));

        rightPanel.add(gameLogPanel);
        rightPanel.add(moveMenuPanel);

        // assembling Main Panel
        mainPanel.add(playersDisplayPanel, BorderLayout.WEST); // players on the left
        mainPanel.add(boardPanel, BorderLayout.CENTER); // game board in the center
        mainPanel.add(rightPanel, BorderLayout.EAST); // game log and move menu on the right

        return mainPanel;
    }

    private void initializePlayerPositions() {
        List<Player> players = game.getPlayers();
        JLabel startingSquare = boardSquares.get(0); // starting on start square

        StringBuilder playersText = new StringBuilder("<html><center>Start<br>");

        for (Player player : players) {
            player.setPosition(0); // setting all player positions to 0 (start square)
            playersText.append(" ").append(player.getCharacter());
        }

        playersText.append("</center></html>");

        startingSquare.setText(playersText.toString());
        startingSquare.revalidate();
        startingSquare.repaint();
    }

    private JPanel createTaskPhasesPanel() {
        JPanel taskPhasesPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        taskPhasesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
                "Objectives", 0, 0, new Font("Arial", Font.BOLD, 14)));

        // checking if colourblind mode is enabled
        boolean isColorblindMode = SettingsPanel.checkColourblindMode();

        // defining normal and colourblind colors for objective categories
        Color[] normalPhaseColors = {
                new Color(200, 230, 255), // Light Blue (Planning)
                new Color(255, 230, 200), // Light Orange (Design)
                new Color(200, 255, 200), // Light Green (Development)
                new Color(230, 200, 255) // Light Purple (Testing)
        };

        Color[] colourblindPhaseColors = {
                new Color(0, 119, 200), // Deep Blue (Planning)
                new Color(204, 85, 0), // Darker Red (Design)
                new Color(189, 183, 107), // Olive (Development)
                new Color(147, 112, 219) // Adjusted Purple (Testing)
        };

        // adding panels for each task category explicitly, just so colours match the current mode
        taskPhasesPanel.add(createObjectivesPanel("Planning", planningTasks,
                isColorblindMode ? colourblindPhaseColors[0] : normalPhaseColors[0]));
        taskPhasesPanel.add(createObjectivesPanel("Design", designTasks,
                isColorblindMode ? colourblindPhaseColors[1] : normalPhaseColors[1]));
        taskPhasesPanel.add(createObjectivesPanel("Development", developmentTasks,
                isColorblindMode ? colourblindPhaseColors[2] : normalPhaseColors[2]));
        taskPhasesPanel.add(createObjectivesPanel("Testing", testingTasks,
                isColorblindMode ? colourblindPhaseColors[3] : normalPhaseColors[3]));

        return taskPhasesPanel;
    }

    private JPanel createObjectivesPanel(String objectiveLabel, List<Task> tasks, Color color) {
        JPanel phasePanel = new JPanel(new BorderLayout());
        phasePanel.setOpaque(false);

        Color gameBackgroundColor = new Color(240, 240, 235);
        phasePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        phasePanel.setBackground(gameBackgroundColor);

        // objectiveLabel label
        JLabel phaseLabel = new JLabel("<html><b>" + objectiveLabel + "</b></html>", SwingConstants.CENTER);
        phaseLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // converting tasks to a list format
        JList<String> taskList = getStringJList(tasks, color);

        // Adding hover effect (tooltip) with brief details
        taskList.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                JList<String> list = (JList<String>) e.getSource();
                int index = list.locationToIndex(e.getPoint());
                if (index != -1) {
                    String taskDetails = "<html><b>" + tasks.get(index).getDescription() + "</b><br>"
                            + "Deposit: " + tasks.get(index).getDeposit()  + "</b><br>"
                            + "Total Required: " + tasks.get(index).getRequiredTotalResources()  + "</b><br>"
                            + "Currently Allocated: " + tasks.get(index).checkResourceBalance()  + "</b><br>";
                    list.setToolTipText(taskDetails);
                }
            }
        });

        JScrollPane taskScrollPane = new JScrollPane(taskList);
        taskScrollPane.setPreferredSize(new Dimension(180, 80));

        phasePanel.add(phaseLabel, BorderLayout.NORTH);
        phasePanel.add(taskScrollPane, BorderLayout.CENTER);

        return phasePanel;
    }

    private static JList<String> getStringJList(List<Task> tasks, Color color) {
        DefaultListModel<String> taskModel = new DefaultListModel<>();
        for (Task task : tasks) {
            String text = null;
            if(task.getOwner()!=null) {
                Player owner =task.getOwner();
                text = owner.getCharacter();
            }

            text = " - " + task.getDescription();
            if (task.isComplete()) {
                text += " [DONE]";
            }
            taskModel.addElement(text);
        }

        // using JList to display tasks
        JList<String> taskList = new JList<>(taskModel);
        taskList.setBackground(color);
        taskList.setFont(new Font("Arial", Font.PLAIN, 12));
        taskList.setOpaque(true);

        taskList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        taskList.setSelectionModel(new DefaultListSelectionModel() { // disabling click selection
            @Override
            public void setSelectionInterval(int index0, int index1) {
                super.setSelectionInterval(-1, -1);
            }
        });
        return taskList;
    }

    // on the left - the players area
    private void updatePlayersPanel(JPanel playersPanel, List<Player> players) {
        playersPanel.removeAll();
        playersPanel.setLayout(new GridLayout(players.size(), 1, 10, 10));
        Player currentPlayer = game.getCurrentPlayer();
        for (Player player : players) {
            String playerName = String.format("%s %s", player.getName(), player.getCharacter());
            String playerResources = String.format("Resources: R %d", player.getResources());

            // Determine alliance status for the player.
            String allianceInfo = "";
            for (Alliance a : Alliance.getAllAlliances()) {
                if (a.isMember(player)) {
                    if (a.getAlliance().size() == 1) {
                        allianceInfo = "Alliance needed (1/2)";
                    } else if (a.getAlliance().size() >= 2) {
                        allianceInfo = "Alliance formed";
                    }
                    break;
                }
            }

            JLabel playerLabel = new JLabel(String.format("<html><b>%s</b><br>%s<br>%s</html>",
                    playerName, playerResources, allianceInfo));
            playerLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            playerLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            playerLabel.setOpaque(true);
            if (player == currentPlayer) {
                playerLabel.setBackground(new Color(220, 240, 255));
            } else {
                playerLabel.setBackground(Color.WHITE);
            }
            playersPanel.add(playerLabel);
        }
        playersPanel.revalidate();
        playersPanel.repaint();
    }

    private JPanel createBoardPanel() {
        int boardSize = 680; // Fixed board size
        int rowHeight = boardSize / 6; // Uniform height for rows
        int columnWidth = boardSize / 6; // Uniform width for columns

        JPanel boardPanel = new JPanel(new BorderLayout());
        boardPanel.setBackground(new Color(245, 235, 210));

        JPanel topRow = new JPanel(new GridLayout(1, 7, 2, 2));
        JPanel rightColumn = new JPanel(new GridLayout(5, 1, 2, 2));
        JPanel bottomRow = new JPanel(new GridLayout(1, 7, 2, 2));
        JPanel leftColumn = new JPanel(new GridLayout(5, 1, 2, 2));

        int index = 0;

        // top row
        for (int i = 0; i < 7; i++, index++) {
            JLabel square = createSquareLabel(index, columnWidth, rowHeight);
            boardSquares.add(square);
            topRow.add(square);
        }

        // right column
        for (int i = 0; i < 5; i++, index++) {
            JLabel square = createSquareLabel(index, columnWidth, rowHeight);
            boardSquares.add(square);
            rightColumn.add(square);
        }

        // bottom row (reverse order - for movement)
        for (int i = 6; i >= 0; i--, index++) {
            JLabel square = createSquareLabel(index, columnWidth, rowHeight);
            boardSquares.add(square);
            bottomRow.add(square, 0);
        }

        // left column (reverse order - for movement)
        for (int i = 4; i >= 0; i--, index++) {
            JLabel square = createSquareLabel(index, columnWidth, rowHeight);
            boardSquares.add(square);
            leftColumn.add(square, 0);
        }

        boardPanel.add(topRow, BorderLayout.NORTH);
        boardPanel.add(rightColumn, BorderLayout.EAST);
        boardPanel.add(bottomRow, BorderLayout.SOUTH);
        boardPanel.add(leftColumn, BorderLayout.WEST);

        // fix board panel inside a wrapper
        JPanel boardWrapper = new JPanel(new GridBagLayout()); // center board
        boardWrapper.setPreferredSize(new Dimension(700, 700));
        boardWrapper.add(boardPanel);

        return boardWrapper;
    }

    private JLabel createSquareLabel(int index, int width, int height) {
        String text = squareLabels[index];

        JLabel square = new RoundedLabel("<html><div style='text-align:center; width:90px;'>"
                + text.replace("\n", "<br>")
                + "</div></html>", getSquareColor(text));

        square.setFont(new Font("Arial", Font.BOLD, 12));
        square.setForeground(Color.DARK_GRAY);

        // set fixed size
        square.setPreferredSize(new Dimension(width, height));
        square.setMinimumSize(new Dimension(width, height));
        square.setMaximumSize(new Dimension(width, height));

        return square;
    }

    static class RoundedLabel extends JLabel {
        private final Color bgColor;

        public RoundedLabel(String text, Color bgColor) {
            super(text, SwingConstants.CENTER);
            this.bgColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // fill the background color (Rounded Rectangle)
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

            // add White Curved Border
            g2.setColor(Color.WHITE); // Border Color
            g2.setStroke(new BasicStroke(3)); // Border Thickness
            g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);

            super.paintComponent(g);
            g2.dispose();
        }
    }

    private Color getSquareColor(String squareText) {
        Color color;

        if (squareText.contains("Define Goals") || squareText.contains("Budget and Resource Planning")
                || squareText.contains("Research Community Needs") || squareText.contains("Hire Developers")) {
            color = new Color(173, 216, 230); // light blue - Planning
        } else if (squareText.contains("Designing MVC") || squareText.contains("Visual Design and Branding")
                || squareText.contains("Prototype Core Features") || squareText.contains("Accessibility and Inclusivity")) {
            color = new Color(255, 204, 153); // light orange - Design
        } else if (squareText.contains("Develop Core Functionalities") || squareText.contains("Developing User Interface")
                || squareText.contains("Set Up Databases") || squareText.contains("Integrate APIs and External Data")) {
            color = new Color(144, 238, 144); // light green - Development
        } else if (squareText.contains("Conduct Unit and Integration Testing") || squareText.contains("Performance and Stress Testing")
                || squareText.contains("Beta Testing") || squareText.contains("Feedback and Issue Resolution")) {
            color = new Color(221, 160, 221); // light purple - Testing
        } else if (squareText.contains("Random Event")) {
            color = new Color(240, 128, 128);
        } else {
            color = Color.LIGHT_GRAY; // default for anything unspecified
        }

        return color;
    }

    private JPanel createGameLogPanel() {
        gameLog = new JTextArea(15, 30);
        gameLog.setEditable(false);
        gameLog.setLineWrap(true);
        gameLog.setWrapStyleWord(true);
        gameLog.setFont(new Font("Arial", Font.PLAIN, 14));
        gameLog.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(gameLog);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel gameLogPanel = new JPanel(new BorderLayout());
        gameLogPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Game Log", 0, 0, new Font("Arial", Font.BOLD, 14), Color.DARK_GRAY));
        gameLogPanel.setBackground(new Color(245, 245, 245));
        gameLogPanel.add(scrollPane, BorderLayout.CENTER);

        return gameLogPanel;
    }

    private boolean checkIsRandomEvent(int position) {
        Task task = boardTasks.get(position);
        return "Random Event".equals(task.getObjective());
    }

    /*
        Also checks for end game condition
     */
    private JPanel createMoveMenuPanel(JPanel playersPanel) {
        JPanel moveMenuPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        moveMenuPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                "Player Menu", 0, 0, new Font("Arial", Font.BOLD, 14), Color.DARK_GRAY));

        // Roll Dice button
        getRollDiceButton(playersPanel, moveMenuPanel);

        // End Turn button action listener
        endTurnButton.addActionListener(e -> {

            // Check if all tasks are complete, if true, runs endgame sequence
            if (boardTasks.stream().allMatch(Task::isComplete)) {
                logEvent("All tasks complete!");
                JOptionPane.showMessageDialog(boardPanel,"All tasks complete! Congratulations!","Victory!",JOptionPane.INFORMATION_MESSAGE);
                runEndGameSequence(true);            }

            if (!hasRolledDice) {
                JOptionPane.showMessageDialog(boardPanel, "Play your turn!", "Action Blocked", JOptionPane.WARNING_MESSAGE);
                return;
            }

            rollDiceButton.setEnabled(true);
            endTurnButton.setEnabled(false);
            hasRolledDice = false;
            if(!areAllPlayersOutOfResources()) {
                game.nextTurn();
                updatePlayersPanel(playersPanel, game.getPlayers());
                refreshObjectivePanel();
                logEvent(game.getCurrentPlayer().getName() + "'s turn");
            } else {
                logEvent("All the players have run out of resources.");
                JOptionPane.showMessageDialog(boardPanel,"All the players have run out of resources.","Game Over!",JOptionPane.WARNING_MESSAGE );
                runEndGameSequence(false);
            }

        });

        // Claim Task button
        JButton claimTaskButton = getClaimTaskButton();

        // Allocate Resources button
        JButton allocateResourcesButton = getAllocateResourcesButton();

        // Form Alliance button
        JButton formAllianceButton = getAllianceButton(playersPanel);

        //Transfer Task button
        JButton transferTaskButton = getTransferTaskButton();

        JButton tutorialButton = getTutorialButton();
        

        // Debug button that simulates max resource allocation for testing purposes
//        JButton debugButton = new JButton("Simulate Endgame");
//        debugButton.addActionListener(e -> simulateMaxResourceAllocation());
//        playersPanel.add(debugButton);
        
        
        // Add buttons to the panel.
        moveMenuPanel.add(rollDiceButton);
        moveMenuPanel.add(claimTaskButton);
        moveMenuPanel.add(allocateResourcesButton);
        moveMenuPanel.add(formAllianceButton);
        moveMenuPanel.add(transferTaskButton);
        moveMenuPanel.add(endTurnButton);
        moveMenuPanel.add(tutorialButton);

        return moveMenuPanel;
    }

    private boolean areAllPlayersOutOfResources() {
        return game.getPlayers().stream().allMatch(player -> player.getResources() == 0);
    }

    private void simulateMaxResourceAllocation() {
        // simulates good ending
        for (Task t : boardTasks){
            t.markAsComplete();
        }

        // simulates bad ending
//        for (Player p : game.getPlayers()) {
//            p.deductResources(p.getResources());
//        }
    }

    private void getRollDiceButton(JPanel playersPanel, JPanel moveMenuPanel) {
        // Initialize the standard buttons.
        rollDiceButton = new JButton("Roll Dice");
        applyButtonStyle(rollDiceButton);

        endTurnButton = new JButton("End Turn");
        applyButtonStyle(endTurnButton);

        rollDiceButton.setEnabled(true);
        endTurnButton.setEnabled(false);

        // Roll Dice button action listener
        rollDiceButton.addActionListener(e -> {
            rollDiceButton.setEnabled(false);
            hasRolledDice = false;
            Player currentPlayer = game.getCurrentPlayer();
            int diceRoll = currentPlayer.rollDice();
            logEvent(currentPlayer.getName() + " rolled a " + diceRoll);

            new Thread(() -> {
                try {
                    for (int i = 1; i <= diceRoll; i++) {
                        SwingUtilities.invokeAndWait(() -> {
                            int nextPosition = getNextPosition(currentPlayer.getPosition());
                            currentPlayer.setPosition(nextPosition);
                            updatePlayerPosition(currentPlayer);

                            if (nextPosition == 0) {
                                currentPlayer.addResources(1000);
                                logEvent("Next Round!!!");
                                logEvent(currentPlayer.getName() + " has completed a lap of the board and received 1000 rands");
                            }
                        });
                        Thread.sleep(500);
                    }

                    // After moving the full dice roll, trigger a random event if the final position qualifies.
                    int finalPosition = currentPlayer.getPosition();
                    if (checkIsRandomEvent(finalPosition)) {
                        SwingUtilities.invokeAndWait(() -> {
                            Task randomEventTask = getRandomEvent();
                            String eventMessage = "Random Event: " + randomEventTask.getDescription();
                            JOptionPane.showMessageDialog(boardPanel, eventMessage, "Random Event", JOptionPane.INFORMATION_MESSAGE);
                            logEvent(eventMessage);
                            randomEventTask.applyEffect(currentPlayer);
                        });
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    SwingUtilities.invokeLater(() -> {
                        updatePlayersPanel(playersPanel, game.getPlayers());
                        hasRolledDice = true;
                        endTurnButton.setEnabled(true);


                        logEvent(currentPlayer.getName() + "'s turn is over. Click 'End Turn' to proceed.");
                        
                        moveMenuPanel.revalidate();
                        moveMenuPanel.repaint();
                    });
                }
            }).start();
        });
    }

    private JButton getClaimTaskButton() {
        JButton claimTaskButton = new JButton("Claim Task");
        applyButtonStyle(claimTaskButton);

        claimTaskButton.addActionListener(e ->
                SwingUtilities.invokeLater(() -> {
                    Player currentPlayer = game.getCurrentPlayer();
                    int pos = currentPlayer.getPosition();
                    Task task = boardTasks.get(pos);
                    // Check for Random Event first.
                    if (task != null && "Random Event".equals(task.getObjective())) {
                        JOptionPane.showMessageDialog(boardPanel, "You can't claim a random square.");
                        return;
                    }

                    if (pos == 0) {
                        JOptionPane.showMessageDialog(boardPanel,
                                "You cannot claim the Start square.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    claimTask(task);
                })
        );
        return claimTaskButton;
    }

    private JButton getAllocateResourcesButton() {
        JButton allocateResourcesButton = new JButton("Allocate Resources");
        applyButtonStyle(allocateResourcesButton);

        allocateResourcesButton.addActionListener(e ->
                SwingUtilities.invokeLater(() -> {
                    Player currentPlayer = game.getCurrentPlayer();
                    
                    
                    // Debugging: Check what tasks the player owns
                    System.out.println("🔍 Checking player's claimed tasks: " + currentPlayer.getTasks());
                    for (Task t : currentPlayer.getTasks()) {
                        System.out.println("  - Task: " + t.getDescription() + ", Owner: " + (t.getOwner() != null ? t.getOwner().getName() : "None"));
                    }
                    
                    List<Task> availableTasks = new ArrayList<>();

                    // Get all tasks owned by the current player
                    availableTasks.addAll(currentPlayer.getTasks().stream()
                            .filter(task -> !task.isComplete())
                            .collect(Collectors.toList()));
                    
                    // debugging
                    System.out.println("🔍 Checking all claimed tasks for allocation:");
                    for (Task task : currentPlayer.getTasks()) {
                        System.out.println("  - Task: " + task.getDescription() + ", Completed: " + task.isComplete() + ", Balance: " + task.checkResourceBalance());
                    }

                    
                    // Add tasks from alliance partners
                    for (Alliance alliance : Alliance.getAllAlliances()) {
                        if (alliance.isMember(currentPlayer)) {
                            for (Player partner : alliance.getAlliance()) {
                                if (partner != currentPlayer) {
                                    availableTasks.addAll(partner.getTasks().stream()
                                            .filter(task -> !task.isComplete())
                                            .collect(Collectors.toList()));
                                }
                            }
                        }
                    }

                    // Check if player has any tasks available
                    if (availableTasks.isEmpty()) {
                        JOptionPane.showMessageDialog(boardPanel,
                                "You don't have any incomplete tasks to allocate resources to.",
                                "No Tasks", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    // dropdown list for task selection
                    Task[] tasksArray = availableTasks.toArray(new Task[0]);
                    JComboBox<Task> taskComboBox = new JComboBox<>(tasksArray);

                    //  task information in dropdown
                    taskComboBox.setRenderer(new DefaultListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                                      int index, boolean isSelected, boolean cellHasFocus) {
                            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                            if (value instanceof Task) {
                                Task task = (Task) value;
                                String ownerInfo = task.getOwner() == currentPlayer ? "" : " [" + task.getOwner().getName() + "]";
                                setText(task.getDescription() + ownerInfo + " - Progress: R" +
                                        task.checkResourceBalance() + "/R" + task.getRequiredTotalResources());
                            }
                            return this;
                        }
                    });

                    // Create a panel for the dropdown
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.add(new JLabel("Select task to allocate resources to:"), BorderLayout.NORTH);
                    panel.add(taskComboBox, BorderLayout.CENTER);

                    // Show the task selection dialog
                    int result = JOptionPane.showConfirmDialog(
                            boardPanel, panel, "Select Task",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result != JOptionPane.OK_OPTION) {
                        return;
                    }

                    // Get the selected task
                    Task selectedTask = (Task) taskComboBox.getSelectedItem();
                    Player taskOwner = selectedTask.getOwner();

                    // Handle case where selected task is already completed
                    if (selectedTask.isComplete()) {
                        JOptionPane.showMessageDialog(boardPanel,
                                "This task is already completed. You cannot allocate further resources.");
                        return;
                    }

                    // Prompt the user to enter the allocation amount
                    String taskOwnerInfo = taskOwner == currentPlayer ? "" : " (Owned by " + taskOwner.getName() + ")";
                    String input = JOptionPane.showInputDialog(
                            "Enter the amount to allocate to task: " + selectedTask.getDescription() + taskOwnerInfo + "\n" +
                                    "Required total: R " + selectedTask.getRequiredTotalResources() + "\n" +
                                    "Currently allocated: R " + selectedTask.checkResourceBalance() + "\n" +
                                    "Your resources: R " + currentPlayer.getResources());

                    if (input == null) {
                        return;
                    }

                    try {
                        int amount = Integer.parseInt(input);
                        if (amount <= 0) {
                            JOptionPane.showMessageDialog(boardPanel, "Please enter a positive amount.");
                            return;
                        }
                        if (amount > currentPlayer.getResources()) {
                            JOptionPane.showMessageDialog(boardPanel, "Insufficient funds.");
                            return;
                        }

                        // Ensure the allocation does not exceed the required total
                        int currentAllocated = selectedTask.checkResourceBalance();
                        if (currentAllocated + amount > selectedTask.getRequiredTotalResources()) {
                            JOptionPane.showMessageDialog(boardPanel, "You cannot allocate more than the required resources for this task. " +
                                    "Currently allocated: R " + currentAllocated + ", Required total: R " + selectedTask.getRequiredTotalResources());
                            return;
                        }

                        // Deduct resources from the current player
                        currentPlayer.deductResources(amount);

                        // Update task's resource balance
                        selectedTask.updateResourceBalance(amount);

                        // Check if task is now complete
                        boolean taskCompleted = selectedTask.isComplete();

                        if (taskCompleted) {
                            // If task completed, the task owner gets the 500 resources back
                            taskOwner.addResources(500);
                            selectedTask.markAsComplete();

                            String completionMessage = "Task complete!";
                            if (taskOwner == currentPlayer) {
                                completionMessage += " You have been awarded 500 resources back!";
                            } else {
                                completionMessage += " " + taskOwner.getName() + " has received 500 resources back!";
                            }

                            JOptionPane.showMessageDialog(boardPanel, completionMessage);
                        } else {
                            JOptionPane.showMessageDialog(boardPanel, "Allocated R " + amount + " to task: " + selectedTask.getDescription());
                        }

                        // Refresh the objectives panel so that the task displays correctly
                        refreshObjectivePanel();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(boardPanel, "Invalid number entered.");
                    }
                })
        );
        return allocateResourcesButton;
    }
    
    /*
        Runs the endgame sequence if the players have won or lost the game
     */
    private void runEndGameSequence(boolean b) {
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(boardPanel);
        Container originalContentPane = mainFrame.getContentPane();

        JPanel endGame = null;
        if (b == true) {    // Calls good ending if all objectives are complete
            endGame = new GoodEndingPanel(null, mainFrame);
        }
        else {               // Calls bad ending if all players went bankrupt
            endGame = new BadEndingPanel(null, mainFrame);
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 235));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setOpaque(false);

        // Back button (returns to the game board)
        JButton backButton = new JButton("Back to Game");
        applyButtonStyle(backButton);
        backButton.setPreferredSize(new Dimension(130, 30));
        backButton.addActionListener(event -> {
            mainFrame.setContentPane(originalContentPane);
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        // Main Menu button (returns to the start screen)
        JButton mainMenuButton = new JButton("Return to Main Menu");
        applyButtonStyle(mainMenuButton);
        mainMenuButton.setPreferredSize(new Dimension(170, 30));
        mainMenuButton.addActionListener(event -> {
            // Reset the frame to its default state
            mainFrame.dispose(); // Close the current frame

            // Create a new frame with proper settings
            JFrame newFrame = new JFrame("EcoJozi Board Game");
            newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newFrame.setLayout(new BorderLayout());
            newFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            newFrame.setUndecorated(false);
            newFrame.setLocationRelativeTo(null);

            // Reset the game
            Game newGame = new Game();

            // Create a fresh main menu panel
            MainMenuPanel mainMenuPanel = new MainMenuPanel(newGame.getPlayers(), newFrame);

            // Add the panel to the new frame
            newFrame.add(mainMenuPanel, BorderLayout.CENTER);

            // Make the new frame visible
            newFrame.setVisible(true);

            // Update color settings
            Board.updateBoardColours();
        });

        // Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(mainMenuButton);

        // Add the button panel to the top panel
        topPanel.add(buttonPanel, BorderLayout.WEST);
        endGame.add(topPanel, BorderLayout.NORTH);

        mainFrame.setContentPane(endGame);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private JButton getAllianceButton(JPanel playersPanel) {
        JButton formAllianceButton = new JButton("Form Alliance");
        applyButtonStyle(formAllianceButton);

        formAllianceButton.addActionListener(e ->
                SwingUtilities.invokeLater(() -> {
                    Player currentPlayer = game.getCurrentPlayer();
                    Task task = boardTasks.get(currentPlayer.getPosition());
                    
                    // checking if player exists in a pre-existing alliance
                    if (Alliance.isPlayerInAnyAlliance(currentPlayer)) {
                        JOptionPane.showMessageDialog(boardPanel, "You are already in an alliance and cannot form another one.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // If on a Random Event square, prevent alliance formation.
                    if (task != null && "Random Event".equals(task.getObjective())) {
                        JOptionPane.showMessageDialog(boardPanel, "You cannot form an alliance on a random square.");
                        return;
                    }

                    if (currentPlayer.getPosition() == 0) {
                        JOptionPane.showMessageDialog(boardPanel,
                                "You cannot form an alliance on the Start square.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else

                        // Prevent alliance formation if the task is already complete.
                        if (task != null && task.isComplete()) {
                            JOptionPane.showMessageDialog(boardPanel, "This task is already completed. You cannot form an alliance on a completed task.");
                            return;
                        }

                    // Only allow alliance formation if the current player owns the task.
                    if (task == null || task.getOwner() == null || !task.getOwner().equals(currentPlayer)) {
                        JOptionPane.showMessageDialog(boardPanel, "You can only enter an alliance on a task you own.");
                        return;
                    }
                    
                    
                    // filtering the players and forming alliances
                    List<Player> otherPlayers = new ArrayList<>();
                    for (Player p : game.getPlayers()) {
                        if (!p.equals(currentPlayer) && !Alliance.isPlayerInAnyAlliance(p)) {
                            otherPlayers.add(p);
                        }
                    }
                    if (otherPlayers.isEmpty()) {
                        JOptionPane.showMessageDialog(boardPanel, "No other players available for alliance.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    String[] playerNames = new String[otherPlayers.size()];
                    for (int i = 0; i < otherPlayers.size(); i++) {
                        playerNames[i] = otherPlayers.get(i).getName();
                    }

                    JComboBox<String> comboBox = new JComboBox<>(playerNames);
                    int option = JOptionPane.showConfirmDialog(null, comboBox, "Select a player to form alliance with", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        String selectedName = (String) comboBox.getSelectedItem();
                        Player selectedPlayer = null;
                        for (Player p : otherPlayers) {
                            if (p.getName().equals(selectedName)) {
                                selectedPlayer = p;
                                break;
                            }
                        }
                        if (selectedPlayer != null) {
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    selectedPlayer.getName() + ", do you accept an alliance with " + currentPlayer.getName() + "?",
                                    "Alliance Request", JOptionPane.YES_NO_OPTION);

                            if (confirm == JOptionPane.YES_OPTION) {
                                Alliance alliance = Alliance.getAllianceBetween(currentPlayer, selectedPlayer);

                                if (alliance == null) {
                                    List<Player> allianceMembers = new ArrayList<>();
                                    allianceMembers.add(currentPlayer);
                                    allianceMembers.add(selectedPlayer);
                                    new Alliance(allianceMembers);

                                    JOptionPane.showMessageDialog(null,
                                            "Alliance formed between " + currentPlayer.getName() + " and " + selectedPlayer.getName(),
                                            "Alliance Formed", JOptionPane.INFORMATION_MESSAGE);

                                    gameLog.append("Alliance formed between " + currentPlayer.getName() + " and " + selectedPlayer.getName() + "\n");
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Alliance already exists between " + currentPlayer.getName() + " and " + selectedPlayer.getName() + " for this task.",
                                            "Alliance Exists", JOptionPane.WARNING_MESSAGE);

                                    gameLog.append("Alliance already exists between " + currentPlayer.getName() + " and " + selectedPlayer.getName() + " for this task." + "\n");
                                }

                                updatePlayersPanel(playersPanel, game.getPlayers());
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        selectedPlayer.getName() + " declined alliance with " + currentPlayer.getName(),
                                        "Alliance Declined", JOptionPane.INFORMATION_MESSAGE);

                                gameLog.append(selectedPlayer.getName() + " declined alliance with " + currentPlayer.getName() + "\n");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Alliance formation cancelled by " + currentPlayer.getName(),
                                    "Alliance Cancelled", JOptionPane.ERROR_MESSAGE);

                            gameLog.append("Alliance formation cancelled by " + currentPlayer.getName() + "\n");
                        }
                    }
                })
        );
        return formAllianceButton;
    }

    private JButton getTransferTaskButton() {
        JButton transferTaskButton = new JButton("Transfer Task");
        applyButtonStyle(transferTaskButton);

        transferTaskButton.addActionListener(e ->
                SwingUtilities.invokeLater(() -> {
                    Player currentPlayer = game.getCurrentPlayer();

                    // Get all tasks owned by the current player
                    List<Task> ownedTasks = currentPlayer.getTasks();

                    // Check if player owns any tasks
                    if (ownedTasks.isEmpty()) {
                        JOptionPane.showMessageDialog(boardPanel,
                                "You don't own any tasks to transfer.",
                                "No Tasks", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    // Filter out completed tasks and random event tasks
                    List<Task> transferableTasks = ownedTasks.stream()
                            .filter(task -> !task.isComplete() && !"Random Event".equals(task.getObjective()))
                            .collect(Collectors.toList());

                    if (transferableTasks.isEmpty()) {
                        JOptionPane.showMessageDialog(boardPanel,
                                "You don't have any tasks that can be transferred.",
                                "No Transferable Tasks", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    // Create a dropdown list for task selection
                    Task[] tasksArray = transferableTasks.toArray(new Task[0]);
                    JComboBox<Task> taskComboBox = new JComboBox<>(tasksArray);

                    taskComboBox.setRenderer(new DefaultListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                                      int index, boolean isSelected, boolean cellHasFocus) {
                            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                            if (value instanceof Task) {
                                Task task = (Task) value;
                                setText(task.getDescription() + " - Progress: R" +
                                        task.checkResourceBalance() + "/R" + task.getRequiredTotalResources());
                            }
                            return this;
                        }
                    });

                    // Create a panel for the task dropdown
                    JPanel taskPanel = new JPanel(new BorderLayout());
                    taskPanel.add(new JLabel("Select task to transfer:"), BorderLayout.NORTH);
                    taskPanel.add(taskComboBox, BorderLayout.CENTER);

                    // Show the task selection dialog
                    int taskResult = JOptionPane.showConfirmDialog(
                            boardPanel, taskPanel, "Select Task to Transfer",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (taskResult != JOptionPane.OK_OPTION) {
                        return;
                    }

                    // Get the selected task
                    Task selectedTask = (Task) taskComboBox.getSelectedItem();

                    // Get the list of other players to transfer to
                    List<Player> otherPlayers = new ArrayList<>();
                    for (Player p : game.getPlayers()) {
                        if (!p.equals(currentPlayer)) {
                            otherPlayers.add(p);
                        }
                    }

                    String[] playerNames = new String[otherPlayers.size()];
                    for (int i = 0; i < otherPlayers.size(); i++) {
                        playerNames[i] = otherPlayers.get(i).getName();
                    }

                    JComboBox<String> playerComboBox = new JComboBox<>(playerNames);

                    // Create a panel for player selection
                    JPanel playerPanel = new JPanel(new BorderLayout());
                    playerPanel.add(new JLabel("Select a player to transfer the task to:"), BorderLayout.NORTH);
                    playerPanel.add(playerComboBox, BorderLayout.CENTER);

                    int playerOption = JOptionPane.showConfirmDialog(
                            boardPanel, playerPanel, "Select Player",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (playerOption == JOptionPane.OK_OPTION) {
                        String selectedName = (String) playerComboBox.getSelectedItem();
                        Player selectedPlayer = null;
                        for (Player p : otherPlayers) {
                            if (p.getName().equals(selectedName)) {
                                selectedPlayer = p;
                                break;
                            }
                        }

                        if (selectedPlayer != null) {
                            String transferPrompt = selectedPlayer.getName() + ", do you accept the transfer of the task: '" +
                                    selectedTask.getDescription() + "'?";
                            int acceptTransfer = JOptionPane.showConfirmDialog(
                                    boardPanel, transferPrompt, "Task Transfer Request",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                            if (acceptTransfer == JOptionPane.YES_OPTION) {
                                boolean status = currentPlayer.removeTask(selectedTask);
                                if (status) {
                                    Player.addTask(selectedPlayer, selectedTask);
                                    logEvent(currentPlayer.getName() + " transferred task '" +
                                            selectedTask.getDescription() + "' to " + selectedPlayer.getName());
                                    JOptionPane.showMessageDialog(
                                            boardPanel,
                                            "Task successfully transferred to " + selectedPlayer.getName(),
                                            "Transfer Complete",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    updatePlayersPanel(playersPanel, game.getPlayers());
                                    refreshObjectivePanel();
                                }
                            } else {
                                JOptionPane.showMessageDialog(
                                        boardPanel,
                                        selectedPlayer.getName() + " declined the task transfer.",
                                        "Transfer Declined",
                                        JOptionPane.INFORMATION_MESSAGE);
                                logEvent(selectedPlayer.getName() + " declined task transfer from " +
                                        currentPlayer.getName());
                            }
                        }
                    }
                })
        );
        return transferTaskButton;
    }

    private JButton getTutorialButton() {
        JButton tutorialButton = new JButton("Tutorial");
        applyButtonStyle(tutorialButton);

        tutorialButton.addActionListener(e -> {
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(boardPanel);
            Container originalContentPane = mainFrame.getContentPane();

            TutorialPanel tutorialPanel = new TutorialPanel(null, mainFrame);

            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(new Color(240, 240, 235));
            topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

            JButton backButton = new JButton("Back");
            applyButtonStyle(backButton);
            backButton.setPreferredSize(new Dimension(100, 30));

            backButton.addActionListener(event -> {
                mainFrame.setContentPane(originalContentPane);
                mainFrame.revalidate();
                mainFrame.repaint();
            });

            topPanel.add(backButton, BorderLayout.WEST);
            tutorialPanel.add(topPanel, BorderLayout.NORTH);

            mainFrame.setContentPane(tutorialPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        return tutorialButton;
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

    private Task getTaskFromObjective(int taskIndex, JPanel objectivePanel) {
        if (objectivePanel.getComponent(0) instanceof JLabel label) {
            String objective = label.getText().replaceAll("<[^>]+>", "");

            List<Task> taskList = switch (objective) {
                case "Planning" -> planningTasks;
                case "Design" -> designTasks;
                case "Development" -> developmentTasks;
                case "Testing" -> testingTasks;
                default -> null;
            };

            if (taskList != null && taskIndex < taskList.size()) {
                return taskList.get(taskIndex);
            }
        }
        return null;
    }

    private int getNextPosition(int currentPosition) {
        currentPosition += 1;
        if (currentPosition >= boardSquares.size()) {
            currentPosition %= boardSquares.size();
        }
        return currentPosition;
    }

    private void updatePlayerPosition(Player player) {
        // removing player from the previous square
        JLabel previousSquare = boardSquares.get(player.getPreviousPosition());
        updateSquareText(previousSquare, player.getPreviousPosition());

        // adding player to the current square
        JLabel currentSquare = boardSquares.get(player.getPosition());
        updateSquareText(currentSquare, player.getPosition());

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    // helper to update the text of a square
    private void updateSquareText(JLabel square, int squareIndex) {
        String baseText = squareLabels[squareIndex];

        StringBuilder squareText = new StringBuilder("<html><center>" + baseText.replace("\n", "<br>") + "<br>");

        // Append player icons dynamically
        String playersOnSquare = getPlayersOnSquare(squareIndex);
        if (!playersOnSquare.isEmpty()) {
            squareText.append(playersOnSquare);
        }

        squareText.append("</center></html>");
        square.setText(squareText.toString());
    }

    // helper method to get all players' characters on a specific square
    private String getPlayersOnSquare(int squareIndex) {
        StringBuilder playersOnSquare = new StringBuilder();
        for (Player p : game.getPlayers()) {
            if (p.getPosition() == squareIndex) {
                playersOnSquare.append(p.getCharacter()).append(" ");
            }
        }
        return playersOnSquare.toString().trim();
    }

    private void logEvent(String event) {
        gameLog.append(event + "\n");
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }

    private void refreshObjectivePanel() {
        // Iterate over each phase panel in taskPhasesPanel
        for (Component comp : objectivePanel.getComponents()) {
            if (comp instanceof JPanel phasePanel) {
                JList<String> taskList = getTaskListFromObjectivePanel(phasePanel);
                if (taskList != null) {
                    updateTaskList(taskList, phasePanel);
                }
            }
        }
        objectivePanel.revalidate();
        objectivePanel.repaint();
    }

    // Helper method to extract the JList from a objective panel.
    private JList<String> getTaskListFromObjectivePanel(JPanel objectivePanel) {
        for (Component innerComp : objectivePanel.getComponents()) {
            if (innerComp instanceof JScrollPane scrollPane) {
                Component view = scrollPane.getViewport().getView();
                if (view instanceof JList<?>) {
                    @SuppressWarnings("unchecked")
                    JList<String> taskList = (JList<String>) view;
                    return taskList;
                }
            }
        }
        return null;
    }

    // Helper method to update the task list model based on the tasks in the given objective.
    private void updateTaskList(JList<String> taskList, JPanel objectivePanel) {
        DefaultListModel<String> model = (DefaultListModel<String>) taskList.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            Task task = getTaskFromObjective(i, objectivePanel);
            if (task != null) {
                model.set(i, generateTaskDisplayText(task));
            }
        }
    }

    // Helper method to generate the display text for a task.
    private String generateTaskDisplayText(Task task) {
        String description = task.getDescription();
        Player owner = task.getOwner();

        if (task.isComplete()) {
            return String.format("<html>- %s [COMPLETED] <span>%s</span></html>", description, owner.getCharacter());
        }

        if (owner != null) {
            return String.format("<html>- %s [CLAIMED] <span>%s</span></html>", description, owner.getCharacter());
        }

        Alliance alliance = Alliance.getAllianceByTask(task);
        if (alliance != null) {
            int allianceSize = alliance.getAlliance().size();
            if (allianceSize == 1) {
                return String.format("<html>- %s (Alliance 1/2)</html>", description);
            } else if (allianceSize >= 2) {
                StringBuilder allianceIcons = new StringBuilder();
                allianceIcons.append("<span style='white-space: nowrap;'>");
                for (Player member : alliance.getAlliance()) {
                    // Appends each icon inline
                    allianceIcons.append(member.getCharacter()).append(" ");
                }
                allianceIcons.append("</span>");
                // Display alliance member icons after "[CLAIMED]" inline
                return String.format("<html>- %s [CLAIMED] %s</html>",
                        description, allianceIcons.toString().trim());
            }
        }

        return String.format("<html>- %s</html>", description);
    }

    public void claimTask(Task task) {
    	// Debugging Task Object and Position
        Player currentPlayer = game.getCurrentPlayer();
        int pos = currentPlayer.getPosition();
        Task expectedTask = boardTasks.get(pos);
        
        // debugging
        System.out.println("\n[Debugging] Player: " + currentPlayer.getName() + " at Position: " + pos);
        
        if (expectedTask == null) {
            System.out.println("ERROR: No task found at position " + pos);
            return;
        }
        
        // debugging
        System.out.println("Expected Task at Position " + pos + ": " + expectedTask.getDescription());
        System.out.println("Passed Task: " + task.getDescription());


        if (!task.getDescription().equals(expectedTask.getDescription())) {
            System.out.println("ERROR: Task Mismatch! Expected: " + expectedTask.getDescription() + ", but Found: " + task.getDescription());
            return;
        }
    	
    	
        if (task.getOwner() != null) {
        	// debugging:
        	System.out.println("Task '" + task.getDescription() + "' is already owned by " + task.getOwner().getName());
        	
            String alreadyClaimedMsg = "This task has already been claimed.";
            JOptionPane.showMessageDialog(null, alreadyClaimedMsg, "Error", JOptionPane.ERROR_MESSAGE);
            logEvent(alreadyClaimedMsg);
            
            return;
            
          // debugging:
        } else {
            System.out.println("Task '" + task.getDescription() + "' is currently unclaimed.");
        }
        
        System.out.println("Can this task be claimed? " + task.getIsAvailableToClaim());

        // Logging Before Prompting Claim Dialog
        System.out.println("Prompting claim: " + task.getDescription() + " for " + currentPlayer.getName());
        
        
        //Player currentPlayer = game.getCurrentPlayer();
        int cost = task.getDeposit();
        String promptMsg = "Your current balance is R" + currentPlayer.getResources() + "\n"
                + "Do you want to claim the task '" + task.getDescription() + "'\n"
                + "for a cost of R" + cost + "?";
        logEvent("Prompting claim: " + promptMsg);
        int response = JOptionPane.showConfirmDialog(null, promptMsg, "Claim Task",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (response == JOptionPane.YES_OPTION) {
            if (currentPlayer.getResources() >= cost) {
                // Deduct resources and assign the task.
                currentPlayer.deductResources(cost);
                Player.addTask(currentPlayer, task);
                task.assignOwner(currentPlayer);
                task.updateResourceBalance(cost);
                
                
                String successMsg = "Task claimed successfully! " + currentPlayer.getName() + " claimed: " + task.getDescription();
                JOptionPane.showMessageDialog(null, successMsg);
                logEvent(successMsg);
                updatePlayersPanel(playersPanel, game.getPlayers());
                refreshObjectivePanel();  // Update the objectives panel to show claimed status.
            } else {
                String insufficientMsg = "Insufficient funds to claim this task.";
                JOptionPane.showMessageDialog(null, insufficientMsg);
                logEvent(insufficientMsg);
            }
        } else {
            String notClaimedMsg = "Task not claimed.";
            JOptionPane.showMessageDialog(null, notClaimedMsg);
            logEvent(notClaimedMsg);
        }
        
    }
    

}
