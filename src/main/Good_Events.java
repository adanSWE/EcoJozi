package main;

import java.util.*;

public enum Good_Events {
    REQUIREMENTS_CLARIFIED(
            "Stakeholders provided clear project requirements, boosting your efficiency! \n+250 rands") {
            @Override
            public void applyEffect(Player player) {
                player.addResources(250);
                System.out.println("You saved money by having a well-defined project scope!");
            }
        },
        SUCCESSFUL_USER_TESTING(
            "Beta testers are not finding many bugs! Your team gains confidence and support. \n+250 rands") {
            @Override
            public void applyEffect(Player player) {
                player.addResources(250);
                System.out.println("Positive feedback has put you ahead of schedule!.");
            }
        },
        PRODUCT_LAUNCH_HYPE(
            "Your marketing team successfully built excitement for the product launch! \n+250 rands") {
            @Override
            public void applyEffect(Player player) {
                player.addResources(250);
                System.out.println("Your launch campaign is a success! You gained donations.");
            }
        },
        COMMUNITY_SUPPORT(
            "The developer community contributed insights that accelerated your progress. \n+250 rands") {
            @Override
            public void applyEffect(Player player) {
                player.addResources(250);
                System.out.println("You gained valuable feedback and support, speeding up development!");
            }
        };

    private final String description;
    private static final List<Good_Events> VALUES = List.of(values());
   

    public static List<Good_Events> getGoodEvent() {
        return VALUES;
    }
    
    Good_Events(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public abstract void applyEffect(Player player); // Abstract method for the effect
}
