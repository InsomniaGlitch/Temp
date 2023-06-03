package powers;

import java.util.ArrayList;
import java.util.Scanner;

import effects.effect;

public abstract class power {
    Scanner in = new Scanner(System.in);
    public String name;
    public String description;
    public String target;
    public int damage;
    public ArrayList<effect> effects;

    public String getTarget() {
        return this.target;
    }

    public int getDamage() {
        return this.damage;
    }

    public ArrayList<effect> getEffects() {
        return this.effects;
    }

    public void setTarget() {
        System.out.print("Target: ");
        String str = in.nextLine();
        if(str.equals("enemy") || str.equals( "player")) {
            this.target = str;
        } else {
            System.out.println("Wrong type of target. Try again.");
            this.setTarget();
        }
    }

    public void use() {}
}