package powers;

import java.util.ArrayList;

import effects.effect;

public class custom_power extends power {
    public custom_power(String name, String description, String target, int damage, ArrayList<effect> effects) {
        this.name = name;
        this.description = description;
        this.target = target;
        this.damage = damage;
        this.effects = effects;
    }

    public custom_power() {
    }
    
}
