import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        Machine machine = new Machine();

        while (machine.input(s.nextLine())) {}

    }

}

class Machine {
    private State state;
    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;
    private int money = 550;

    public Machine() {
        goToMainMenu();
    }

    private void goToMainMenu() {
        state = State.MAIN_MENU;
        System.out.print("Write action (buy, fill, take, remaining, exit): ");
    }

    private void printRemaining() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println("$" + money + " of money");
        System.out.println();
        goToMainMenu();
    }

    private void takeMoney() {
        System.out.println();
        System.out.println("I gave you $" + money);
        System.out.println();
        money = 0;
        goToMainMenu();
    }

    private void fill(String input) {
        switch (state) {
            case MAIN_MENU:
                System.out.println();
                System.out.print("Write how many ml of water do you want to add: ");
                state = State.FILL_WATER;
                break;
            case FILL_WATER:
                water += Integer.parseInt(input);
                System.out.print("Write how many ml of milk do you want to add: ");
                state = State.FILL_MILK;
                break;
            case FILL_MILK:
                milk += Integer.parseInt(input);
                System.out.print("Write how many grams of coffee beans do you want to add: ");
                state = State.FILL_BEANS;
                break;
            case FILL_BEANS:
                beans += Integer.parseInt(input);
                System.out.print("Write how many disposable cups of coffee do you want to add: ");
                state = State.FILL_CUPS;
                break;
            case FILL_CUPS:
                cups += Integer.parseInt(input);
                System.out.println();
                goToMainMenu();
                break;
            default:
                break;
        }
    }

    private boolean canMake(int needWater, int needMilk, int needBeans, int needCups) {
        boolean can = true;
        if (needWater > water) {
            System.out.println("Sorry, not enough water!");
            can = false;
        }
        if (needMilk > milk) {
            System.out.println("Sorry, not enough milk!");
            can = false;
        }
        if (needBeans > beans) {
            System.out.println("Sorry, not enough beans!");
            can = false;
        }
        if (needCups > cups) {
            System.out.println("Sorry, not enough cups!");
            can = false;
        }
        if (can) {
            System.out.println("I have enough resources, making you a coffee!");
        }
        System.out.println();
        return can;
    }

    private void buy(String input) {
        switch (state) {
            case MAIN_MENU:
                System.out.println();
                System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                state = State.CHOOSE_COFFEE;
                break;
            case CHOOSE_COFFEE:
                switch (input) {
                    case "1":
                        buyEspresso();
                        goToMainMenu();
                        break;
                    case "2":
                        buyLatte();
                        goToMainMenu();
                        break;
                    case "3":
                        buyCappuccino();
                        goToMainMenu();
                        break;
                    default:
                        System.out.println();
                        goToMainMenu();
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void buyEspresso() {
        if (canMake(250, 0, 16, 1)) {
            cups--;
            water -= 250;
            beans -= 16;
            money += 4;
        }
    }

    private void buyLatte() {
        if (canMake(350, 75, 20, 1)) {
            cups--;
            water -= 350;
            milk -= 75;
            beans -= 20;
            money += 7;
        }
    }

    private void buyCappuccino() {
        if (canMake(200, 100, 12, 6)) {
            cups--;
            water -= 200;
            milk -= 100;
            beans -= 12;
            money += 6;
        }
    }

    public boolean input(String input) {
        if (state == State.MAIN_MENU) {
            switch (input) {
                case "buy":
                    buy(input);
                    break;
                case "fill":
                    fill(input);
                    break;
                case "take":
                    takeMoney();
                    break;
                case "remaining":
                    printRemaining();
                    break;
                default:
                    return false;
            }
        } else if (state == State.FILL_WATER || state == State.FILL_MILK || state == State.FILL_BEANS || state == State.FILL_CUPS) {
            fill(input);
        } else if (state == State.CHOOSE_COFFEE) {
            buy(input);
        }
        return true;
    }
}

enum State {
    MAIN_MENU, CHOOSE_COFFEE, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS,;
}