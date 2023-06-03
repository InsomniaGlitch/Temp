package players;
import weapons.*;

import java.util.ArrayList;
import java.util.Scanner;

import effects.effect;
import paths.path;
import powers.power;

public class player {
    Scanner in = new Scanner(System.in);
    public String name;
    public String description;
    public int max_health;
    public int curr_health;
    public int def;
    public int speed;
    power power;
    path path;
    weapon weapon;
    public int surepiority;
    ArrayList<effect> status;
    public boolean turn;

    public player(String name, String description, int max_health, int def, int speed, power power, path path, weapon weapon) {
        this.name = name;
        this.description = description;
        this.max_health = max_health;
        this.curr_health = max_health;
        this.def = def;
        this.speed = speed;
        this.power = power;
        this.path = path;
        this.weapon = weapon;
        this.turn = false;
    }

    public player() {
    }

    public void equip(weapon new_weapon) {
        if (this.weapon != null && this.weapon.getType() == this.path.getWeaponType()) {
            this.weapon = new_weapon;
        } else {
            System.out.println("Cannot be equipped");
        }
    }

    public void embrace_path(path path) {
        this.path = path;
    }

    public void embrace_power(power power) {
        this.power = power;
    }

    public void kill(player p) {
        p = null;
    }

    public void getHurt(int damage) {
        if (damage > this.def) {
            this.curr_health -= damage += this.def;
        }
        if (damage < 0) {
            this.curr_health -= damage;
        }
        if (this.curr_health <= 0) {
            kill(this);
        }
    }

    public void applyEffects(ArrayList<effect> effects) {
        this.status.addAll(effects);
    }

    public void attack(player target) {
        Double rand = Math.random()*100;
        if (this.weapon == null) {
            System.out.println("No weapon equipped");
        }
        if (this.weapon != null && rand <= this.weapon.getCritRate()) {
            target.getHurt(this.weapon.getDamage()*(this.weapon.getCritDamage() / 100 + 1));
            target.applyEffects(this.weapon.getEffects());
        } else if (this.weapon != null) {
            target.getHurt(this.weapon.getDamage());
        }
    }

    public void use_class_power(player target) {
        if (this.power.getTarget() == "enemy") {
            target.getHurt(this.power.getDamage());
            target.applyEffects(this.power.getEffects());
        } else if (this.power.getTarget() == "player") {
            this.getHurt(this.power.getDamage());
            this.applyEffects(this.power.getEffects());
        } else {
            this.power.use();
        }
    }

    public void use_weapon_power(player target) {
        if (this.weapon.getPower().getTarget() == "enemy") {
            target.getHurt(this.weapon.getPower().getDamage());
            target.applyEffects(this.weapon.getPower().getEffects());
        } else if (this.weapon.getPower().getTarget() == "player") {
            this.getHurt(this.weapon.getPower().getDamage());
            this.applyEffects(this.weapon.getPower().getEffects());
        } else {
            this.weapon.getPower().use();
        }
    }
}
