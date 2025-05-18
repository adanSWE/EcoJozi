package main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

public class AllianceTest {
    
    private Player player1;
    private Player player2;
    private Player player3;
    private List<Player> validMembers;
    
    @BeforeEach
    public void setUp() {
        // Generate test players
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        player3 = new Player("Player3");
        
        // Authentic alliance members(two players only)
        validMembers = new ArrayList<>();
        validMembers.add(player1);
        validMembers.add(player2);
        
        // Before every test, clear all affiliations
        clearAllAlliances();
    }
   
    
    @AfterEach
    public void tearDown() {
        // After every test, clear all alliances
        clearAllAlliances();
    }
    
    // A helpful 
    private void clearAllAlliances() {
        try {
            Field alliancesField = Alliance.class.getDeclaredField("alliances");
            alliancesField.setAccessible(true);
            ((List<Alliance>)alliancesField.get(null)).clear();
        } catch (Exception e) {
            System.out.println("Failed to clear alliances: " + e.getMessage());
        }
    }
    
    @Test
    public void testValidAllianceCreation() {
        // Test creating a valid alliance
        Alliance alliance = new Alliance(validMembers);
        
        // Verify alliance was added to the static list
        List<Alliance> allAlliances = Alliance.getAllAlliances();
        assertEquals(1, allAlliances.size());
        assertTrue(allAlliances.contains(alliance));
        
        // Verify members were added correctly
        List<Player> allianceMembers = alliance.getAlliance();
        assertEquals(2, allianceMembers.size());
        assertTrue(allianceMembers.contains(player1));
        assertTrue(allianceMembers.contains(player2));
    }
    
    @Test
    public void testInvalidAllianceSize() {
        // Test creating an alliance with too few members
        List<Player> tooFewMembers = new ArrayList<>();
        tooFewMembers.add(player1);
        
        Alliance invalidAlliance = new Alliance(tooFewMembers);
        
        // Alliance should not be created
        List<Alliance> allAlliances = Alliance.getAllAlliances();
        assertEquals(0, allAlliances.size());
        
        // Test creating an alliance with too many members
        List<Player> tooManyMembers = new ArrayList<>();
        tooManyMembers.add(player1);
        tooManyMembers.add(player2);
        tooManyMembers.add(player3);
        
        invalidAlliance = new Alliance(tooManyMembers);
        
        // Alliance should not be created
        allAlliances = Alliance.getAllAlliances();
        assertEquals(0, allAlliances.size());
    }
    
    @Test
    public void testPlayerAlreadyInAlliance() {
        // First create a valid alliance
        Alliance alliance1 = new Alliance(validMembers);
        
        // Try to create a new alliance with player1 and player3
        List<Player> newMembers = new ArrayList<>();
        newMembers.add(player1);
        newMembers.add(player3);
        
        Alliance alliance2 = new Alliance(newMembers);
        
        // Second alliance should not be created
        List<Alliance> allAlliances = Alliance.getAllAlliances();
        assertEquals(1, allAlliances.size());
    }
    
    @Test
    public void testIsPlayerInAnyAlliance() {
        // No alliances initially
        assertFalse(Alliance.isPlayerInAnyAlliance(player1));
        
        // Create an alliance
        Alliance alliance = new Alliance(validMembers);
        
        // Now player1 and player2 should be in an alliance
        assertTrue(Alliance.isPlayerInAnyAlliance(player1));
        assertTrue(Alliance.isPlayerInAnyAlliance(player2));
        
        // player3 should not be in any alliance
        assertFalse(Alliance.isPlayerInAnyAlliance(player3));
    }
    
    @Test
    public void testAddMember() {
        // Create alliance with one fewer member for testing
        List<Player> singleMember = new ArrayList<>();
        singleMember.add(player1);
        
        // Create alliance and manually modify its members list
        Alliance alliance = new Alliance(validMembers);
        try {
            Field membersField = Alliance.class.getDeclaredField("members");
            membersField.setAccessible(true);
            List<Player> members = new ArrayList<>();
            members.add(player1);
            membersField.set(alliance, members);
        } catch (Exception e) {
            fail("Failed to set up test: " + e.getMessage());
        }
        
        // Add player2 to the alliance
        alliance.addMember(player2);
        
        // Verify player2 was added
        List<Player> allianceMembers = alliance.getAlliance();
        assertEquals(2, allianceMembers.size());
        assertTrue(allianceMembers.contains(player2));
        
        // Try adding player3 (should fail)
        alliance.addMember(player3);
        
        // Alliance should still have only 2 members
        allianceMembers = alliance.getAlliance();
        assertEquals(2, allianceMembers.size());
        assertFalse(allianceMembers.contains(player3));
    }
    
    @Test
    public void testIsMember() {
        Alliance alliance = new Alliance(validMembers);
        
        // Test membership
        assertTrue(alliance.isMember(player1));
        assertTrue(alliance.isMember(player2));
        assertFalse(alliance.isMember(player3));
    }
    
    @Test
    public void testGetAlliance() {
        Alliance alliance = new Alliance(validMembers);
        
        List<Player> members = alliance.getAlliance();
        assertEquals(2, members.size());
        assertTrue(members.contains(player1));
        assertTrue(members.contains(player2));
    }
    
    @Test
    public void testGetAllAlliances() {
        // No alliances initially
        List<Alliance> alliances = Alliance.getAllAlliances();
        assertEquals(0, alliances.size());
        
        // Create two alliances
        Alliance alliance1 = new Alliance(validMembers);
        
        // Create valid members for a second alliance
        List<Player> otherMembers = new ArrayList<>();
        Player player4 = new Player("Player4");
        Player player5 = new Player("Player5");
        otherMembers.add(player4);
        otherMembers.add(player5);
        
        Alliance alliance2 = new Alliance(otherMembers);
        
        // Should now have two alliances
        alliances = Alliance.getAllAlliances();
        assertEquals(2, alliances.size());
        assertTrue(alliances.contains(alliance1));
        assertTrue(alliances.contains(alliance2));
    }
    
    @Test
    public void testGetAllianceByTask() {
        // Make a test task with the appropriate constructor parameters.
    	// The task constructor requires (String, int, Player, int, String) based on the error message
        Task task = new Task("Test Task", 1, player1, 0, "Description");
        
        // Form an alliance 
        Alliance alliance = new Alliance(validMembers);
        
        // Use reflection to set the alliance's task
        try {
            Field taskField = Alliance.class.getDeclaredField("task");
            taskField.setAccessible(true);
            taskField.set(alliance, task);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set task on alliance: " + e.getMessage());
        }
        
        // Try locating the alliance by objective
        Alliance foundAlliance = Alliance.getAllianceByTask(task);
        assertNotNull(foundAlliance);
        assertEquals(alliance, foundAlliance);
        
        // Test and try with a different task
        Task otherTask = new Task("Other Task", 2, player1, 0, "Other Description");
        Alliance notFoundAlliance = Alliance.getAllianceByTask(otherTask);
        assertNull(notFoundAlliance);
    }
    
    @Test
    public void testGetAllianceBetween() {
        // start an alliance
        Alliance alliance = new Alliance(validMembers);
        
        // Test identifying two player's alliance
        Alliance foundAlliance = Alliance.getAllianceBetween(player1, player2);
        assertNotNull(foundAlliance);
        assertEquals(alliance, foundAlliance);
        
        // Test against a player who is not part of any alliance 
        Alliance notFoundAlliance = Alliance.getAllianceBetween(player1, player3);
        assertNull(notFoundAlliance);
    }
}