package com.skillfactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.*;

public class Realm {
    private static BufferedReader bufferedReader;
    private static FantasyCharacter player = null;
    private static BattleScene battleScene = null;

    public interface FightCallback {
        void fightWin();

        void fightLost();
    }
    public static void main(String[] args) {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        battleScene = new BattleScene();
        System.out.println("Enter username:");
        try {
            command(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String string) throws IOException {
        if (player == null) {
            player = new Hero(string, 100, 20, 20, 0, 0);
        }
        out.println(String.format("Volunteered to save our world from monsters %s! May his armor be strong and his sword sharp!", player.getName()));
        printNavigation();
        switch (string) {
            case "1": {
                out.println("The merchant hasn't arrived yet.");
                command(bufferedReader.readLine());
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3":
                System.exit(1);
                break;
            case "yes":
                command("2");
                break;
            case "no": {
                printNavigation();
                command(bufferedReader.readLine());
            }
        }
        command((bufferedReader.readLine()));
    }
    private static void printNavigation() {
        System.out.println("Where do you want to go?");
        System.out.println("1.To the merchant ");
        System.out.println("2.Into the dark forest ");
        System.out.println("3.Exit");
    }
    private static void commitFight() {
        battleScene.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s won! Now you have %d experiences and  %d gold,and also left  %d health",
                        player.getName(), player.getXp(), player.getGold(), player.getHealthPoints()));
                System.out.println("Would you like to continue hiking or return to the city?? (yes/no)");
                try {
                    command(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {

            }
        });
    }
    private static FantasyCharacter createMonster(){
        int random = (int)(Math.random()*10);
        if (random %2 ==0)
            return new Goblin("com.skill-factory.Goblin", 50, 10, 10, 100, 20);
        else return new Skeleton("com.skill-factory.Skeleton", 25, 20, 20, 100, 10);
    }
}
