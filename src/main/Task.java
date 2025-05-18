package main;

public class Task {
    // Task attributes
    private final String description;
    private final int deposit;
    private final int requiredTotalResources;
    private int resourceBalance; 
    private Player owner; 
    private final String objective;
    private boolean allianceStatus;
    private boolean isAvailableToClaim;
    private boolean completed = false;


    // Constructor 
    public Task(String objective, int requiredResources,  Player owner, int deposit, String description) {
        this.description = description;
        this.requiredTotalResources = requiredResources;
        this.owner = owner; // starts off as unclaimed
        this.resourceBalance = 0;         this.deposit = deposit;
        this.allianceStatus = false;
        this.objective = objective;
        this.isAvailableToClaim = true;
    }

    // Getter methods
    public String getDescription() {
        return this.description;
    }

    public int getRequiredTotalResources() {
        return requiredTotalResources;
    }

    public String getObjective() {
        return objective;
    }

    public int checkResourceBalance() {
        return this.resourceBalance;
    }

    public int getDeposit() {
        return this.deposit;
    }

    public void assignOwner(Player player) {
        if (this.owner == null) {
            this.owner = player;
            
            // debugging
            System.out.println(player.getName() + " is now the owner of: " + this.description);
            this.isAvailableToClaim = false;
            
            System.out.println("Confirming task assignment: " + this.description + " -> Owner: " + (this.owner != null ? this.owner.getName() : "None"));
            
        } else {
            System.out.println("Task already has an owner.");
        }
    }

    

    public void setAllianceStatus(boolean isInAlliance) {
        this.allianceStatus = isInAlliance;
    }

    public void updateResourceBalance(int resources) {
        this.resourceBalance += resources;
        System.out.println("Resource balance updated to: " + this.resourceBalance);
    }

    public void applyEffect(Player player) {
        // Default behavior: do nothing.
    }

    public boolean isComplete() {
        System.out.println("Checking completion status for: " + this.description + " | Resource Balance: " + this.resourceBalance + " / " + this.requiredTotalResources);
        return this.resourceBalance >= this.requiredTotalResources;
    }

    
    public Player getOwner() {
        return this.owner;
    }

    // method to mark a task as complete - marks a task as complete (different from isComplete)
    public void markAsComplete() {
        if (!this.isComplete()) {
            System.out.println("Marking task as complete: " + this.description);
            this.resourceBalance = this.requiredTotalResources;
            this.completed = true; // Ensure task is explicitly marked as complete
        } else {
            System.out.println("Task was already completed: " + this.description);
        }
    }

    
    
    
    
    // --- Debugging ----
    public boolean getIsAvailableToClaim() {
        return this.isAvailableToClaim;
    }

    public boolean getIsCompleted() {
        return this.isComplete();
    }

}