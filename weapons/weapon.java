package weapons;

import java.util.ArrayList;
import java.util.Scanner;

import effects.effect;
import powers.*;

public abstract class weapon {
    Scanner in = new Scanner(System.in);
    public String type;
    public String name;
    public int damage;
    public Double crit_rate;
    public int crit_damage;
    public ArrayList<effect> effects;
    public power power;

    public String getType() {
        return this.type;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getCritDamage() {
        return this.crit_damage;
    }

    public Double getCritRate() {
        return this.crit_rate;
    }

    public ArrayList<effect> getEffects() {
        return this.effects;
    }

    public power getPower() {
        return this.power;
    }
}
