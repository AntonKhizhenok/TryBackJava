package TryBack;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

class Game 
    {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_MAGENTA = "\033[0;35m";
        static Scanner scanner=new Scanner(System.in);
        public static void Start() throws IOException
        {
            Player player = Player.CreatePlayer();
            PrintMenu();
            input(player);
        }
        public static void Continue() throws IOException
        {
            Player readPlayer = Player.ReadDataPlayer();
            PrintMenu();
            input(readPlayer);
        }
        public static void PrintMenu()
        {
            System.out.println("\033[H\033[J");
            System.out.println("Menu game");
            System.out.println();
            System.out.println("1.View hero stats ");
            System.out.println("2.Fight the monsters");
            System.out.println("3.View inventory");
            System.out.println("Exit from the game (Esc)");
        }


        public static void input(Player inputPlayer) throws FileNotFoundException, IOException
        {

            int key=scanner.nextInt();
            switch (key)
            {
                case 1:
                    {
                        inputPlayer.PrintInfoPlayer(inputPlayer);
                        System.out.print("Use (E)NTER to continie");
                        String MenuKey = scanner.next();
                        switch (MenuKey)
                        {
                            case "E":
                                PrintMenu();
                                input(inputPlayer);
                                break;
                        }
                        break;
                    }
                case 2:
                    {
                        System.out.println("\033[H\033[J");
                        if (inputPlayer.isAlive() == false)
                        {
                            System.out.print("You dead(");
                            scanner.next();
                            PrintMenu();
                            input(inputPlayer);

                        }
                        else if (inputPlayer.isAlive())
                        {
                            Monsters monsters1 = Monsters.GetMonster();
                            Fight(inputPlayer, monsters1);
                        }
                        break;
                    }
            }
            String exit=scanner.next();
            switch (exit) {
                case "E":
                    Save(inputPlayer);
                    Exit();
                    break;
            }
        }
        public static void Fight(Player player1, Monsters monsters1) throws FileNotFoundException, IOException
        {
            monsters1 = Monsters.GetMonster();  //getNewMonster
            monsters1.PrintMonster();
            player1.SkeilOfRarity(monsters1);
            while (monsters1.isAlive())
            {
                if (player1.isAlive())
                {
                    System.out.print("(a)ttack/(r)un away:");
                    String attOrRun = scanner.next();
                    switch (attOrRun)
                    {
                        case "a":
                            player1.Attack(player1, monsters1);
                            monsters1.Evasion(player1, monsters1);
                            player1.InfoFightPlayer(monsters1);
                            monsters1.InfoFightMonster(player1);
                            break;
                        case "r":
                            int randNum = MathUtils.GetRandomNumber(101);
                            System.out.println();
                            if (randNum > player1.chanceEscape)
                            {
                                System.out.println(ANSI_RED+"You failed to escape"+ANSI_RESET);
                                player1.Attack(player1, monsters1);
                                monsters1.Evasion(player1, monsters1);
                                player1.InfoFightPlayer(monsters1);
                                monsters1.InfoFightMonster(player1);
                            }
                            else if (randNum <= player1.chanceEscape)
                            {
                                System.out.println(ANSI_GREEN+"You have run away successfully\n"+ANSI_RESET);
                                Fight(player1, monsters1);
                            }      
                            break;
                    }
                    if (monsters1.isAlive() == false)
                    {
                        System.out.println();
                        System.out.println(ANSI_GREEN+monsters1.name+" is dead"+ANSI_RESET);
                        player1.Experience(player1,monsters1);//expirince lvl up
                        System.out.print("Use (E)NTER to continie");
                        String MonsterDeadKey = scanner.next();
                        switch (MonsterDeadKey)
                        {
                            case "E":
                                PrintMenu();
                                input(player1);
                                break;
                        }
                    }
                }
                else if (player1.isAlive() == false)
                {
                    System.out.println();
                    System.out.println(ANSI_RED+"YOU DEAD"+ANSI_RESET);
                    System.out.println();
                    System.out.println("Use (E)NTER to continie");
                    String PlayerDeadKey = scanner.next();
                    switch (PlayerDeadKey)
                    {
                        case "E":
                            PrintMenu();
                            input(player1);
                            break;
                    }
                }

            }
        }
        public static void Save(Player player) throws IOException
        {
            player.WriteInfoPlayer();
        }
        public static void Exit()
        {
            System.exit(0);
        }
    
}