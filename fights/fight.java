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

public abstract class fight {
    Scanner in = new Scanner(System.in);
    ArrayList<player> playerlist;
    ArrayList<custom_weapon> weaponlist;
    ArrayList<custom_power> powerlist;
    ArrayList<custom_effect> effectlist;
    ArrayList<custom_path> pathlist;

    public fight(ArrayList<player> players) {
        this.playerlist = players;
    }

    public void listWeapons() {
        for (weapon weapon : weaponlist) {
            System.out.println(weapon.name);
        }
    }

    public void listPowers() {
        for (power power : powerlist) {
            System.out.println(power.name + '\n' + '\t' + power.description);
        }
    }

    public void listEffects() {
        for (effect effect : effectlist) {
            System.out.println(effect.name + '\n' + '\t' + effect.description);
        }
    }

    public void listPlayers() {
        for (player player : playerlist) {
            System.out.println(player.name + '\n' + '\t' + player.description);
        }
    }

    public void listPaths() {
        for (path path : pathlist) {
            System.out.println(path.name + '\n' + '\t' + path.description);
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
        pathlist.add(p);
    }

    public void add_power() {
        custom_power p = new custom_power();
        System.out.print("Name: ");
        p.name = in.nextLine();
        System.out.print("Description: ");
        p.description = in.nextLine();
        p.setTarget();
        System.out.print("Damage: ");
        p.damage = in.nextInt();
        System.out.print("Effect(s): ");
        for (String s : in.nextLine().split(", ")) {
            for (effect e : effectlist) {
                if (e.name == s) {
                    p.effects.add(e);
                    break;
                }
            }
        }
        powerlist.add(p);
    }

    public void add_weapon() {
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
        System.out.print("Effect (s): ");
        System.out.print("Effect(s): ");
        for (String s : in.nextLine().split(", ")) {
            for (effect e : effectlist) {
                if (e.name == s) {
                    w.effects.add(e);
                    break;
                }
            }
        }
        System.out.print("Power: ");
        String s = in.nextLine();
        for (power p : powerlist) {
            if (p.name == s) {
                w.power = p;
                break;
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
                String s = in.nextLine();
                for (path pt : pathlist) {
                    if (pt.name == s) {
                        getByName(p.name).embrace_path(pt);
                        break;
                    }
                }
            case "equip":
                String st = in.nextLine();
                for (weapon w : weaponlist) {
                    if (w.name == st) {
                        getByName(p.name).equip(w);
                        break;
                    }
                }
            case "embrace power":
                String str = in.nextLine();
                for (power po : powerlist) {
                    if (po.name == str) {
                        getByName(p.name).embrace_power(po);
                        break;
                    }
                }
            case "attack":
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
        if (p.surepiority > playerlist.get(playerlist.indexOf(p) - 1).speed) {
            getByName(p.name).surepiority -= playerlist.get(playerlist.indexOf(p) - 1).speed;
            turn(getByName(p.name));
        }
        if (p.speed > playerlist.get(playerlist.indexOf(p) - 1).speed) {
            getByName(p.name).surepiority += p.speed - playerlist.get(playerlist.indexOf(p) - 1).speed;
        }
        if (playerlist.indexOf(p) != playerlist.size() - 1 && playerlist.size() > 1) {
            turn(playerlist.get(playerlist.indexOf(p) + 1));
        }
    }

    public player getByName(String name) {
        for (player p : playerlist) {
            if (p.name == name) {
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
}
