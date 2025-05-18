package main;

import java.util.*;

public class Alliance {
    private List<Player> members;
    private Task task; // This can be null when the alliance isn't tied to a task.
    private static List<Alliance> alliances = new ArrayList<>();


    //constructor
    public Alliance(List<Player> members) {
    	
    	// adding a check for max 2 players in an alliance
    	if (members.size() != 2) { 
            System.out.println("An alliance must have exactly 2 players.");
            return;
        }
    	
    	// checking  if current player is in a pre-existing alliance
        if (isPlayerInAnyAlliance(members.get(0)) || isPlayerInAnyAlliance(members.get(1))) {
            System.out.println("One of the players is already in an alliance.");
            return;
        }
        
        this.members = new ArrayList<>(members);
        this.task = null;
        alliances.add(this);
    }
    
    
    // checking  if current player is in a pre-existing alliance
    public static boolean isPlayerInAnyAlliance(Player player) {
        for (Alliance alliance : alliances) {
            if (alliance.isMember(player)) {
                return true;
            }
        }
        return false;
    }

    
    
    public void addMember(Player player) {
    	if (members.size() >= 2) { // another check
            System.out.println("An alliance can only have 2 players.");
            return;
        }
    	
        if (!members.contains(player)) {
            members.add(player);
            System.out.println(player.getName() + " joined the alliance.");
            updateTaskForAlliance();
        }
    }


    public boolean isMember(Player player) {
        return members.contains(player);
    }

    // Updates the associated task's alliance status, if a task is present.
    private void updateTaskForAlliance() {
        if (task != null) {
            if (!members.isEmpty()) {
                task.setAllianceStatus(true);
                System.out.println("The task is now marked as an alliance task.");
            } else {
                task.setAllianceStatus(false);
                System.out.println("The task is no longer marked as an alliance task.");
            }
        }
    }

    public Task getTask() {
        return this.task;
    }

    public List<Player> getAlliance() {
        return this.members;
    }

    public static List<Alliance> getAllAlliances() {
        return alliances;
    }

    // Returns the alliance associated with the given task, or null if none exists.
    public static Alliance getAllianceByTask(Task task) {
        for (Alliance alliance : alliances) {
            if (alliance.getTask() != null && alliance.getTask().equals(task)) {
                return alliance;
            }
        }
        return null;
    }

    // Returns an alliance between two players if it exists; otherwise, null.
    public static Alliance getAllianceBetween(Player a, Player b) {
        for (Alliance alliance : alliances) {
            if (alliance.members.contains(a) && alliance.members.contains(b)) {
                return alliance;
            }
        }
        return null;
    }
}

