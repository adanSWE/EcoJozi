package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    private Task task;
    private Player player;
    private final String OBJECTIVE = "Collect 100 coins";
    private final int REQUIRED_RESOURCES = 100;
    private final int DEPOSIT = 50;
    private final String DESCRIPTION = "Sample task";

    @BeforeEach
    public void setUp() {
        player = new Player("TestPlayer");
        task = new Task(OBJECTIVE, REQUIRED_RESOURCES, null, DEPOSIT, DESCRIPTION);
    }

    // Testing Constructor initialisation 
    @Test
    public void testConstructorInitialisation() {
        assertNull(task.getOwner());
        assertEquals(OBJECTIVE, task.getObjective());
        assertEquals(REQUIRED_RESOURCES, task.getRequiredTotalResources());
        assertEquals(DEPOSIT, task.getDeposit());
        assertEquals(DESCRIPTION, task.getDescription());
        assertEquals(0, task.checkResourceBalance());
    }

    // Testing assignOwner() when task dose'nt have an owner 
    @Test
    public void testAssignOwnerWhenNoOwner() {
        task.assignOwner(player);
        assertEquals(player, task.getOwner());
        // Verify subsequent assignment fails
        Player newPlayer = new Player("NewPlayer");
        task.assignOwner(newPlayer);
        assertEquals(player, task.getOwner());
    }

    // Testing updateResourceBalance() 
    @Test
    public void testUpdateResourceBalance() {
        task.updateResourceBalance(30);
        assertEquals(30, task.checkResourceBalance());
        task.updateResourceBalance(20);
        assertEquals(50, task.checkResourceBalance());
    }

    // Testing isComplete() in different condition
    @Test
    public void testIsCompleteWhenBalanceMatchesRequired() {
        task.updateResourceBalance(REQUIRED_RESOURCES);
        assertTrue(task.isComplete());
    }

    @Test
    public void testIsCompleteWhenBalanceExceedsRequired() {
        task.updateResourceBalance(REQUIRED_RESOURCES + 50);
        assertTrue(task.isComplete());
    }

    @Test
    public void testIsCompleteWhenBalanceBelowRequired() {
        task.updateResourceBalance(REQUIRED_RESOURCES - 10);
        assertFalse(task.isComplete());
    }

    // Testing  markAsComplete() 
    @Test
    public void testMarkAsCompleteWhenNotComplete() {
        task.markAsComplete();
        assertEquals(REQUIRED_RESOURCES, task.checkResourceBalance());
        assertTrue(task.isComplete());
    }

    @Test
    public void testMarkAsCompleteWhenAlreadyComplete() {
        task.updateResourceBalance(REQUIRED_RESOURCES);
        task.markAsComplete(); // Should do nothing
        assertEquals(REQUIRED_RESOURCES, task.checkResourceBalance());
    }

    // Testing  edge case: Negative resource balance
    @Test
    public void testNegativeResourceBalance() {
        task.updateResourceBalance(-20);
        assertEquals(-20, task.checkResourceBalance());
    }
}