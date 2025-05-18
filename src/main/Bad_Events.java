package main;

import java.util.*;

public enum Bad_Events {
    SCOPE_CREEP(
        "New feature requests keep piling up, delaying development. \n-125 rands") {
        @Override
        public void applyEffect(Player player) {
            player.deductResources(125);
            System.out.println("Project scope expanded! More work, fewer resources.");
        }
    },
    SYSTEM_CRASH(
        "A server failure set you back, and you need to recover lost progress. \nYou have moved back one square.") {
        @Override
        public void applyEffect(Player player) {
            player.updatePosition(player, -1);
            System.out.println("Unexpected system failure! You lost some progress.");
        }
    },
    SECURITY_BREACH(
        "A vulnerability was discovered in your code. You must fix it immediately!\n-125R rands") {
        @Override
        public void applyEffect(Player player) {
            player.deductResources(125);
            //player.discardCurrentTask(); - ruined everything 
            System.out.println("A security flaw has pushed your schedule back.");
        }
    },
    RELEASE_DELAY(
        "Unexpected issues push your release date back.\nYou have moved back one square.") {
        @Override
        public void applyEffect(Player player) {
            player.updatePosition(player, -1);
            System.out.println("Your deployment was delayed due to last-minute fixes.");
        }
    };

    private final String description;
    private static final List<Bad_Events> VALUES = List.of(values());
    public static List<Bad_Events> getBadEvent() {
        return VALUES;
    }

    Bad_Events(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public abstract void applyEffect(Player player); // Abstract method for the effect
}