package fights;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import effects.custom_effect;
import effects.effect;
import paths.custom_path;
import paths.path;
import players.player;
import powers.custom_power;
import powers.power;
import weapons.custom_weapon;
import weapons.weapon;

public class fight {
    Scanner in = new Scanner(System.in);
    ArrayList<player> playerlist = new ArrayList<player>();
    ArrayList<custom_weapon> weaponlist = new ArrayList<custom_weapon>();
    ArrayList<custom_power> powerlist = new ArrayList<custom_power>();
    ArrayList<custom_effect> effectlist = new ArrayList<custom_effect>();
    ArrayList<custom_path> pathlist = new ArrayList<custom_path>();

    public fight(ArrayList<player> players) {
        this.playerlist = players;
    }

    public void listWeapons() {
        for (weapon weapon : weaponlist) {
            System.out.println(weapon.name + '\n' + '\t'+ "Type: " + weapon.type + '\n' + '\t'+ "Damage: " + weapon.damage + '\n' + '\t' + "Crit rate: " + weapon.crit_rate + '\n' + '\t'
             + "Crit damage: " + weapon.crit_damage  + '\n' + '\t'+ "Effects: " + getWEffects(weapon));
        }
    }

    public void listPowers() {
        for (power power : powerlist) {
            System.out.println(power.name + '\n' + '\t'+ "Description: " + power.description);
        }
    }

    public void listEffects() {
        for (effect effect : effectlist) {
            System.out.println(effect.name + '\n' + '\t' + "Description: " + effect.description);
        }
    }

    public void listPlayers() {
        for (player player : playerlist) {
            System.out.println(player.name + '\n' + '\t' + "Description: " + player.description + '\n' + '\t' + "Health: " + player.curr_health + "/" + player.max_health
            + '\n' + '\t' + "Path: " + getPName(player.path) + '\n' + '\t' + "Weapon: " + getWName(player.weapon) + '\n' + '\t' + "Status: " + getPEffects(player));
        }
    }

    public void listPaths() {
        for (path path : pathlist) {
            System.out.println(path.name + '\n' + '\t' + "Description: " + path.description + '\n' + '\t' + "Weapon type: " + path.weapon_type);
        }
    }

    public void add_effect() {
        custom_effect e = new custom_effect();
        System.out.print("Name: ");
        e.name = in.nextLine();
        System.out.print("Description: ");
        e.description = in.nextLine();
        effectlist.add(e);
    }

    public void add_path() {
        custom_path p = new custom_path();
        System.out.print("Name: ");
        p.name = in.nextLine();
        System.out.print("Description: ");
        p.description = in.nextLine();
        System.out.print("Weapon type: ");
        p.weapon_type = in.nextLine();
        pathlist.add(p);
    }

    public void add_power() throws NullPointerException {
        custom_power p = new custom_power();
        System.out.print("Name: ");
        p.name = in.nextLine();
        System.out.print("Description: ");
        p.description = in.nextLine();
        p.setTarget();
        System.out.print("Damage: ");
        p.damage = in.nextInt();
        if (!effectlist.isEmpty()) {
        System.out.print("Effect(s): ");
        String[] ef = in.nextLine().split(", ");
        for (String s : ef) {
            for (effect e : effectlist) {
                if (e.name.equals(s)) {
                    p.effects.add(e);
                    break;
                }
            }
        }
    }
        powerlist.add(p);
    }

    public void add_weapon() throws NullPointerException {
        custom_weapon w = new custom_weapon();
        System.out.print("Type: ");
        w.type = in.nextLine();
        System.out.print("Name: ");
        w.name = in.nextLine();
        System.out.print("Damage: ");
        w.damage = in.nextInt();
        System.out.print("Crit rate: ");
        w.crit_rate = in.nextDouble();
        System.out.print("Crit damage: ");
        w.crit_damage = in.nextInt();
        if (!effectlist.isEmpty()) {
        System.out.print("Effect(s): ");
        String[] ef = in.nextLine().split(", ");
        for (String s : ef) {
            for (effect e : effectlist) {
                if (e.name.equals(s)) {
                    w.effects.add(e);
                    break;
                }
            }
        }
    }
        if (!powerlist.isEmpty()) {
        System.out.print("Power: ");
        String s = in.nextLine();
        for (power p : powerlist) {
            if (p.name.equals(s)) {
                w.power = p;
                break;
            }
        }
    }
        weaponlist.add(w);
    }

    public ArrayList<player> SpeedSort(ArrayList<player> p) {
        ArrayList<Integer> speeds = new ArrayList<>();
        ArrayList<player> plrlst = new ArrayList<>();
        for (player pl : p) {
            speeds.add(pl.speed);
        }
        Collections.sort(speeds);
        for (int speed : speeds) {
            plrlst.add(getBySpeed(speed));
        }
        Collections.reverse(plrlst);
        return plrlst;
    }

    public void cycle() {
        playerlist = SpeedSort(playerlist);
        turn(playerlist.get(0));
        if (playerlist.size() == 1) {
            System.out.println(playerlist.get(0).name + " won");
        } else if (playerlist.size() == 0) {
            System.out.println("Only dead bodies lie around. No living souls are on this field. No one won");
        } else {
            cycle();
        }
    }

    public void turn(player p) {
        System.out.println('\n' + p.name + "'s turn");
        switch (in.nextLine()) {
            case "new effect":
                add_effect();
                break;
            case "new power":
                add_power();
                break;
            case "new weapon":
                add_weapon();
                break;
            case "new path":
                add_path();
                break;
            case "list paths":
                listPaths();
                turn(getByName(p.name));
            case "list effects":
                listEffects();
                turn(getByName(p.name));
            case "list powers":
                listPowers();
                turn(getByName(p.name));
            case "list weapons":
                listWeapons();
                turn(getByName(p.name));
            case "list players":
                listPlayers();
                turn(getByName(p.name));
            case "embrace path":
                System.out.print("Path: ");
                String s = in.nextLine();
                for (path pt : pathlist) {
                    if (pt.name.equals(s)) {
                        getByName(p.name).embrace_path(pt);
                        System.out.println(p.name + " became " + pt.name);
                        break;
                    }
                }
                System.out.println("No such path");
                turn(p);
            case "equip":
                System.out.print("Weapon: ");
                String st = in.nextLine();
                for (weapon w : weaponlist) {
                    if (w.name.equals(st)) {
                        getByName(p.name).equip(w);
                        System.out.println(p.name + " equipped " + w.name);
                        break;
                    }
                }
                System.out.println("No such weapon");
                turn(p);
            case "embrace power":
                System.out.print("Power: ");
                String str = in.nextLine();
                for (power po : powerlist) {
                    if (po.name.equals(str)) {
                        getByName(p.name).embrace_power(po);
                        System.out.println(p.name + " learned " + po.name);
                        break;
                    }
                }
                System.out.println("No such power");
                turn(p);
            case "attack":
                System.out.print("Player: ");
                getByName(p.name).attack(getByName(in.nextLine()));
                break;
            case "Use class power":
                getByName(p.name).use_class_power(getByName(in.nextLine()));
                break;
            case "Use weapon power":
                getByName(p.name).use_weapon_power(getByName(in.nextLine()));
                break;
        }
        if (p.curr_health > p.max_health) {
            p.curr_health = p.max_health;
        }
        for (player player : playerlist) {
            if (player.equals(null)) {
                playerlist.remove(player);
            }
        }
        if (playerlist.size() > 1 && playerlist.indexOf(getByName(p.name)) != playerlist.size() - 1 && p.surepiority > playerlist.get(playerlist.indexOf(getByName(p.name)) + 1).speed) {
            getByName(p.name).surepiority -= playerlist.get(playerlist.indexOf(getByName(p.name)) + 1).speed;
            turn(getByName(p.name));
        }
        if (playerlist.size() > 1 && playerlist.indexOf(getByName(p.name)) != playerlist.size() - 1 && p.speed > playerlist.get(playerlist.indexOf(getByName(p.name)) + 1).speed) {
            getByName(p.name).surepiority += p.speed - playerlist.get(playerlist.indexOf(getByName(p.name)) + 1).speed;
        }
        if (playerlist.size() > 1 && playerlist.indexOf(getByName(p.name)) != playerlist.size() - 1) {
            turn(playerlist.get(playerlist.indexOf(getByName(p.name)) + 1));
        }
    }

    public player getByName(String name) {
        for (player p : playerlist) {
            if (p.name.equals(name)) {
                return p;
            }
        }
        return null;
    }

    public player getBySpeed(int speed) {
        for (player p : playerlist) {
            if (p.speed == speed) {
                return p;
            }
        }
        return null;
    }

    public String getWName(weapon w) {
        if (w != null) {
            return w.name;
        } else {
            return null;
        }
    }

    public String getPName(path p) {
        if (p != null) {
            return p.name;
        } else {
            return null;
        }
    }

    public String getWEffects(weapon w) {
        if (w != null && w.effects != null) {
            return w.effects.toString();
        } else {
            return null;
        }
    }

    public String getPEffects(player p) {
        if (p.status != null) {
            return p.status.toString();
        } else {
            return null;
        }
    }
}
