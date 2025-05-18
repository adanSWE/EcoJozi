package main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PlayerTest {

    private Player player;
    private Task task;

    @BeforeEach
    void setUp() {
        player = new Player("John Doe");
        task = new Task("Collect resources", 100, player, 50, "Collect 100 wood units");
        Player.addTask(player, task);
    }

    @Test
    void testRollDice() {
        int result = player.rollDice();
        assertTrue(result >= 1 && result <= 6, "Dice roll should be between 1 and 6.");
    }

    @Test
    void testAddTask() {
        assertEquals(1, player.getTasks().size(), "Player should have one task.");
    }

    @Test
    void testAllocateResourcesToSquareNotEnoughResources() {
        assertFalse(player.allocateResourcesToSquare(task, 200), "Should return false if not enough resources.");
    }

    @Test
    void testAllocateResourcesToSquareEnoughResources() {
        player.addResources(150);
        assertTrue(player.allocateResourcesToSquare(task, 100), "Should return true if enough resources and task completes.");
        assertEquals(550, player.getResources(), "Player should have 550 resources after refund.");
    }

    @Test
    void testAddResources() {
        player.addResources(100);
        assertEquals(100, player.getResources(), "Player should have 100 resources.");
    }

    @Test
    void testDeductResources() {
        player.addResources(100);
        player.deductResources(50);
        assertEquals(50, player.getResources(), "Player should have 50 resources after deduction.");
    }

    @Test
    void testGetName() {
        assertEquals("John Doe", player.getName(), "Name should match the constructor input.");
    }

    @Test
    void testGetPosition() {
        player.setPosition(10);
        assertEquals(10, player.getPosition(), "Position should be 10 after setting it.");
    }

    @Test
    void testGetCharacter() {
        player.setCharacter("Knight");
        assertEquals("Knight", player.getCharacter(), "Character should be 'Knight'.");
    }

    @Test
    void testGetPreviousPosition() {
        player.setPosition(10);
        player.setPosition(20);
        assertEquals(10, player.getPreviousPosition(), "Previous position should be 10 after moving to 20.");
    }

    @Test
    void testRemoveTask() {
        assertTrue(player.removeTask(task), "Should return true when task is removed successfully.");
        assertFalse(player.getTasks().contains(task), "Task list should not contain the task after removal.");
    }

    @Test
    void testDiscardCurrentTask() {
        player.discardCurrentTask();
        assertTrue(player.getTasks().isEmpty(), "Tasks list should be empty after discarding the task.");
    }

}
