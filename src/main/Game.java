package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private int currentPlayerIndex;

    public Game() {
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
    }

    public Game(ArrayList<Player> players) { //--- constructor to accept existing players and board
        this.players = players; 
        this.currentPlayerIndex = 0;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        Player currentPlayer = players.get(currentPlayerIndex);

        System.out.println("Switching to next player: " + currentPlayer.getName());

        // Check if the player completed a lap
        if (currentPlayer.getPosition() == 0) {
            System.out.println(currentPlayer.getName() + " completed a lap! Awarding 1000 rands.");
            currentPlayer.addResources(1000);

            //Debug: Check if tasks are incorrectly marked complete
            for (Task task : currentPlayer.getTasks()) {
                System.out.println("Before lap completion - Task: " + task.getDescription() + ", Completed: " + task.isComplete());
            }
        }
        
        // Check if the player still has incomplete tasks
        if (currentPlayer.hasIncompleteTasks()) {
            System.out.println(currentPlayer.getName() + " still has incomplete tasks.");
        } else {
            System.out.println("No pending tasks left for " + currentPlayer.getName());
        }
        
        
    }

    public static void main(String[] args) {
        // Initialize the Game instance
        System.setProperty("sun.java2d.uiScale", "1.0");
        Game game = new Game();

        // Create the main frame for the game
        JFrame mainFrame = new JFrame("EcoJozi Board Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setUndecorated(false);
        mainFrame.setLocationRelativeTo(null); // Centre the frame on the screen

        // Initialize the Player Setup Panel
        MainMenuPanel mainMenuPanel = new MainMenuPanel(game.getPlayers(), mainFrame);
        mainFrame.add(mainMenuPanel, BorderLayout.CENTER);
        
        Board.updateBoardColours(); // Apply colourblind settings if enabled

        // Show the setup panel initially
        mainFrame.setVisible(true);
    }
}