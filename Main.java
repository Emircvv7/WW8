import java.util.Random;

class Player {
    String name;
    int health;

    Player(String name, int health) {
        this.name = name;
        this.health = health;
    }

    void takeDamage(int damage) {
        health -= damage;
    }

    boolean isAlive() {
        return health > 0;
    }
}

class Boss {
    int health;

    Boss(int health) {
        this.health = health;
    }

    void attack(Player player) {
        Random random = new Random();
        int damage = random.nextInt(10) + 1;
        player.takeDamage(damage);
    }

    boolean isAlive() {
        return health > 0;
    }
}

class Golem extends Player {
    Golem(String name, int health) {
        super(name, health);
    }

    void takeDamage(int damage) {
        super.takeDamage(damage / 5);
    }
}

class Lucky extends Player {
    Lucky(String name, int health) {
        super(name, health);
    }

    boolean evadeAttack() {
        Random random = new Random();
        return random.nextBoolean();
    }
}

class Witcher extends Player {
    boolean revived = false;

    Witcher(String name, int health) {
        super(name, health);
    }

    void revive(Player player) {
        if (!revived) {
            player.health = 1;
            revived = true;
        }
    }
}

class Thor extends Player {
    Thor(String name, int health) {
        super(name, health);
    }

    boolean stunBoss() {
        Random random = new Random();
        return random.nextBoolean();
    }
}

public class Game {
    public static void main(String[] args) {
        Boss boss = new Boss(100);
        Player[] players = {
                new Player("Beka", 100),
                new Player("Amir", 100),
                new Golem("Golem", 150),
                new Lucky("Lucky", 100),
                new Witcher("Witcher", 100),
                new Thor("Thor", 100)
        };

        while (boss.isAlive()) {
            for (Player player : players) {
                if (!player.isAlive()) {
                    continue;
                }

                if (player instanceof Golem) {
                    int damage = 10;
                    boss.attack(player);
                    player.takeDamage(damage);
                } else if (player instanceof Lucky) {
                    if (((Lucky) player).evadeAttack()) {
                        System.out.println(player.name + " уклонился от удара босса!");
                    } else {
                        boss.attack(player);
                    }
                } else if (player instanceof Witcher) {
                    if (!player.isAlive()) {
                        continue;
                    }

                    boss.attack(player);
                    if (!player.isAlive()) {
                        System.out.println(player.name + " погиб, Witcher оживляет его.");
                        Witcher witcher = (Witcher) player;
                        for (Player revivedPlayer : players) {
                            if (revivedPlayer != witcher && !revivedPlayer.isAlive()) {
                                witcher.revive(revivedPlayer);
                                break;
                            }
                        }
                    }
                } else if (player instanceof Thor) {
                    if (((Thor) player).stunBoss()) {
                        System.out.println(player.name + " оглушил босса!");
                    } else {
                        boss.attack(player);
                    }
                } else {
                    boss.attack(player);
                }
            }
        }

        System.out.println("Босс побежден!");
    }
}


