package weapons;

import java.util.ArrayList;

import effects.effect;
import powers.power;

public class custom_weapon extends weapon {
    public custom_weapon(String type, String name, int damage, Double crit_rate, int crit_damage, ArrayList<effect> effects, power power) {
        this.type = type;
        this.name = name;
        this.damage = damage;
        this.crit_rate = crit_rate;
        this.crit_damage = crit_damage;
        this.effects = effects;
        this.power = power;
    }

    public custom_weapon() {
    }
}
