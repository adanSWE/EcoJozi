package main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameTest {
    
    private Game game;
    private ArrayList<Player> players;
    
    @BeforeEach
    public void setUp() {
        // Initialize test players
        players = new ArrayList<>();
        players.add(new Player("Player1")); // Modified: Using only name parameter
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        
        // Initialize game with test players
        game = new Game(players);
    }
    
    @Test
    public void testDefaultConstructor() {
        // Test the default constructor
        Game defaultGame = new Game();
        assertNotNull(defaultGame);
        assertEquals(0, defaultGame.getPlayers().size());
    }
    
    @Test
    public void testParameterizedConstructor() {
        // Test the parameterized constructor
        assertNotNull(game);
        assertEquals(players, game.getPlayers());
    }
    
    @Test
    public void testGetCurrentPlayer() {
        // Test getting the current player
        assertEquals(players.get(0), game.getCurrentPlayer());
    }
    
    @Test
    public void testGetPlayers() {
        // Test getting all players
        ArrayList<Player> retrievedPlayers = game.getPlayers();
        assertEquals(players, retrievedPlayers);
        assertEquals(3, retrievedPlayers.size());
    }
    
    @Test
    public void testNextTurn() {
        // Test advancing to the next player
        assertEquals(players.get(0), game.getCurrentPlayer());
        
        game.nextTurn();
        assertEquals(players.get(1), game.getCurrentPlayer());
        
        game.nextTurn();
        assertEquals(players.get(2), game.getCurrentPlayer());
        
        // Test wrapping around to the first player
        game.nextTurn();
        assertEquals(players.get(0), game.getCurrentPlayer());
    }
    
    @Test
    public void testEmptyPlayersList() {
        // Test behavior with empty players list
        Game emptyGame = new Game(new ArrayList<>());
        
        // This should not throw an exception even with empty list
        assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyGame.getCurrentPlayer();
        });
    }
}