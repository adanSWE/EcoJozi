package main;

import java.util.*;

public class Player {
    private String name;
    private int resources;
    private int position;
    private int taskCounter;
    private final int turnsCompleted;
    private final int tasksCompleted;
    private final boolean isCurrentPlayer; // Tracks if this is the current player
    private final List<Task> tasks; // Stores tasks assigned to the player
    private String character; // Stores the player's character/icon
    private int previousPosition; // Initial value for no previous position

    // Constructor
    public Player(String name) {
        setName(name); // Validates and assigns the player's nam
        this.position = 0;
        this.taskCounter = 0;
        this.turnsCompleted = 0;
        this.tasksCompleted = 0;
        this.isCurrentPlayer = false; 
        this.tasks = new ArrayList<Task>();
        this.character = ""; 
        //this.position = 0;
        this.resources = 0;
        this.previousPosition = 0;
    }

    // Rolls a dice (1-6)
    public int rollDice() {
        int diceRoll = (int) (Math.random() * 6) + 1;
        System.out.println(this.name + " rolled a " + diceRoll + ".");
        return diceRoll;
    }

    public static void addTask(Player player, Task task) {
        System.out.println("Assigning task to player " + player.getName() + ": " + task.getDescription());
        player.tasks.add(task);
        System.out.println("Task added: " + task.getDescription() + " (Total tasks: " + player.tasks.size() + ")");
        
        // Extra debug
        for (Task t : player.tasks) {
            System.out.println("Player " + player.getName() + " now owns: " + t.getDescription());
        }
    }


    public boolean allocateResourcesToSquare(Task task, int amount) {
        // Check if the player has enough resources.
        if (amount > this.resources) {
        	System.out.println("Not enough resources to allocate.");
            return false;
        }
        
        
        // Check if task already completed
        if (task.isComplete()) {
            System.out.println("Task '" + task.getDescription() + "' is already completed.");
            return false;
        }
        
        
        // Deducts the amount from the player's resources.
        this.resources -= amount;
        
        System.out.println("Before Allocation - Task: " + task.getDescription() + ", Balance: " + task.checkResourceBalance());
        task.updateResourceBalance(amount);
        System.out.println("After Allocation - Task: " + task.getDescription() + ", Balance: " + task.checkResourceBalance());

        // Increments the task counter
        this.taskCounter++;
        task.updateResourceBalance(amount);

        System.out.println("Task Update - " + task.getDescription() + " | New Balance: " + task.checkResourceBalance());

        // Check again if task is now complete
        if (task.isComplete()) {
            System.out.println("Task '" + task.getDescription() + "' is now complete!");
            task.markAsComplete();
            this.addResources(500); // Refund some resources
            return true;
        }

        return false;
    }
    
    
    public boolean hasIncompleteTasks() {
        System.out.println("Checking if " + this.name + " has incomplete tasks.");
        
        for (Task task : this.tasks) {
            System.out.println("   - Task: " + task.getDescription() + " | Completed: " + task.isComplete());
            
            if (!task.isComplete()) {  
                return true;
            }
        }
        
        return false;
    }

    

    // Adds resources to the player's balance
    public void addResources(int amount) {
        this.resources += amount;
    }

    // Deducts resources from the player's balance
    public void deductResources(int amount) {
        this.resources -= amount;
    }

    // Getter for name
    public String getName() {
        return this.name;
    }

    // Getter for resources
    public int getResources() {
        return this.resources;
    }

    // Getter for position on board
    public int getPosition() {
        return this.position;
    }

    // Getter for tasks
    public List<Task> getTasks() {
    	if (tasks.isEmpty()) {
            System.out.println("No tasks found for player: " + this.name);
        }
        
        return tasks;
    }
    
    // to get players character
    public String getCharacter() {
        return this.character;
    }
    
    // to get the prev position of the player
    public int getPreviousPosition() {
        return previousPosition;
    }
    
    // Setters:
    // setting the position of a player on the game board
    public void setPosition(int newPosition) {
        this.previousPosition = this.position; // Update previous position
        this.position = newPosition; // Update current position
    }
    
    // Setter for name
    private void setName(String pName) {
        if (pName == null || pName.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be null or blank");
        }
        this.name = pName;
    }

    // setting the character of the player
    public void setCharacter(String character) {
        this.character = character;
    }

    public void updatePosition(Player player, int steps) {
        player.setPosition(player.getPosition() + steps);
        System.out.println(player.getName() + " moved to position: " + player.getPosition());
    }

    public void discardCurrentTask() {
        if (!tasks.isEmpty()) {
            int pos = tasks.size() - 1;
            Task discardedTask = tasks.remove(pos);
            System.out.println("Discarding task: " + discardedTask.getDescription() + " from player: " + this.name);
        } else {
            System.out.println("No tasks to discard for player: " + this.name);
        }
    }

	public boolean removeTask(Task task) {
		 if (tasks.contains(task)) {
	            tasks.remove(task);
	            return true;
	        }
		return false;
	}
}
