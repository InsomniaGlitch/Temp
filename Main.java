import java.util.ArrayList;
import java.util.Scanner;

import fights.fight;
import players.player;

public class Main {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        String command = in.nextLine();
        ArrayList<player> everyone = new ArrayList<player>();
        while(!command.equals("start")) {
            if (command.equals("new player")) {
                everyone.add(create_player());
            } else {
                System.out.print('\n');
            }
            command = in.nextLine();
        }
        System.out.print("The battle starts, and the fighters are: " + everyone.toString());
        fight f = new fight(everyone);
        f.cycle();
    }

    public static player create_player() {
        player p = new player();
        System.out.print("Name: ");
        p.name = in.nextLine();
        System.out.print("Description: ");
        p.description = in.nextLine();
        System.out.print("Health: ");
        p.max_health = in.nextInt();
        p.curr_health = p.max_health;
        System.out.print("Defence: ");
        p.def = in.nextInt();
        System.out.print("Speed: ");
        p.speed = in.nextInt();
        return p;
    }
}