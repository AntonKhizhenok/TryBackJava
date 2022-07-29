package TryBack;
import java.util.Scanner;
import java.io.*;

class Player extends Creature
{
    public String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String typePlayer;
    public String getTypePlayer() {
        return typePlayer;
    }

    public void setTypePlayer(String typePlayer) {
        this.typePlayer = typePlayer;
    }

    //protected int lvl;
    //protected int randDamage;
    //public int currentHealth;
    //public int minDamage;
        //public int maxDamage;
        //public int fullHealth;
        public int chanceEscape;
        public int evasion;
        public double initialExperience = 0;
        public double experienceRequaired = 100;

        static Scanner scaner =new Scanner(System.in);

        public Player(String name,String lvl,String typePlayer,String minDamage,String maxDamage,String currentHealth,String fullHealth,String chanceEscape,String evasion)
        {
            this.name = name;
            this.typePlayer = typePlayer;
            this.lvl = Integer.parseInt(lvl);
            this.minDamage = Integer.parseInt(minDamage);
            this.maxDamage = Integer.parseInt(maxDamage);
            this.currentHealth =Integer.parseInt(currentHealth);
            this.fullHealth = Integer.parseInt(fullHealth);
            this.chanceEscape = Integer.parseInt(chanceEscape);
            this.evasion = Integer.parseInt(evasion);
        }
        
        public static Player CreatePlayer() throws IOException
        {

            //crate name
            System.out.println("Enter player name");
            String playerName = scaner.nextLine();
            System.out.print("\033[H\033[J");
            //read from file txt
            System.out.println("Select player class");
            readClassPlayer();
            FileReader filePlay=new FileReader("D:\\PlayerClass.txt");
            Scanner scan=new Scanner(filePlay);
            String[]arrPlayerClass=new String[3];
            for (int i = 0; i < arrPlayerClass.length; i++) {
                while (scan.hasNextLine()) {
                    String playerClass =scan.nextLine();
                    arrPlayerClass[i]=playerClass;
                    break;
                }
            }
            int select = scaner.nextInt();
            String[] playerProperties = arrPlayerClass[select - 1].split(";");
            Player playerObj = new Player(playerName,playerProperties[0], playerProperties[1], playerProperties[2], playerProperties[3],playerProperties[4], playerProperties[5], playerProperties[6], playerProperties[7]);
            scan.close();
            filePlay.close();
            return playerObj;
        }

        public static void readClassPlayer() throws IOException
        {
            
            FileReader filePlayerClass=new FileReader("D:\\PlayerClass.txt");
            Scanner scan1=new Scanner(filePlayerClass);
            String playerClass1;
            String[]arrPlayerClass1=new String[3];
            for (int i = 0; i < arrPlayerClass1.length; i++) {
            while (scan1.hasNextLine()) {
                    playerClass1 =scan1.nextLine();
                    arrPlayerClass1[i]=playerClass1; 
                    break; 
                }
            }
            for (int i = 0; i < arrPlayerClass1.length; i++)
            {
                String[]PlayerProp=arrPlayerClass1[i].split(";");
                System.out.println((i+1)+")"+PlayerProp[1]); 
            }
            scan1.close();
            filePlayerClass.close();
        }

        public void WriteInfoPlayer() throws IOException
        {
            FileWriter writerPlayer = new FileWriter("D:\\Player.txt");
            writerPlayer.write(name+";"+lvl+";"+typePlayer+";"+minDamage+";"+maxDamage+";"+currentHealth+";"+fullHealth+";"+chanceEscape+";"+evasion);
            writerPlayer.close();
        }

        public static Player ReadDataPlayer() throws FileNotFoundException
        {
            File filePlayerClass=new File("D:\\Player.txt");
            Scanner scan1=new Scanner(filePlayerClass);
            String playerClass1 =scan1.nextLine();
            String[] playerClass=playerClass1.split(" ");
            String[] playerProperties = playerClass[0].split(";");
            Player readPlayer = new Player(playerProperties[0], playerProperties[1], playerProperties[2], playerProperties[3], playerProperties[4], playerProperties[5], playerProperties[6], playerProperties[7], playerProperties[8]);
            scan1.close();
            return readPlayer;
        }

        public void PrintInfoPlayer(Player player)
        {
            System.out.print("\033[H\033[J");
            System.out.println();
            System.out.println(name+"\t"+typePlayer+"\tlvl."+lvl+"\tDamage:"+minDamage+"-"+maxDamage+"\tHP:"+player.fullHealth+"\tescape chance:"+chanceEscape+"%\tevasion:"+evasion+"%\texpirience:"+initialExperience+"/"+experienceRequaired);
            System.out.println();

        }
        public void InfoFightPlayer(Monsters monsters)
        {
            if (monsters.currentHealth >= 0)
            {
                System.out.println("You hit the " +monsters.name+" for"+ANSI_RED+" "+randDamage+" damage"+ANSI_RESET);
                System.out.println();
                System.out.println(monsters.name+" "+ANSI_GREEN+"hp:"+monsters.currentHealth+"/"+monsters.fullHealth+ANSI_RESET);
            }
            else if (monsters.currentHealth<0)
            {
                System.out.print("You hit the "+monsters.name+" for "+ANSI_RED+randDamage+"damage"+ANSI_RESET);
            }
            System.out.println();
        }
        
        public void Attack(Player player, Monsters monsters)
        {
            randDamage = MathUtils.GetRandomDamage(minDamage, maxDamage); 
            monsters.currentHealth -= randDamage;
        }

        public static void DeletePlayer() throws IOException
        {
            FileWriter sr = new FileWriter("D:\\Player.txt",false);
            sr.close();
        }

        public static void InfoPlayer() throws FileNotFoundException
        {
            String line;
            File arrPlayer=new File("D:\\Player.txt");
            Scanner scanner1=new Scanner(arrPlayer);
                line = scanner1.nextLine();
                while (line != null)
                {
                    System.out.println(line);
                    line =scanner1.nextLine();
                }
                scanner1.close();
        }

        public void SkeilOfRarity(Monsters monsters)
        {
            if (typePlayer=="robber")
            {
                if(monsters.rarityMonster=="Rare")
                {
                    minDamage += 1;
                    maxDamage += 2;
                    if (chanceEscape < 75)
                        chanceEscape = chanceEscape + 1;
                    evasion = evasion + 1;
                }
                else if (monsters.rarityMonster == "Epic")
                {
                    minDamage += 3;
                    maxDamage += 4;
                    if (chanceEscape < 75)
                        chanceEscape = chanceEscape + 3;
                    evasion = evasion + 2;
                }
                else if (monsters.rarityMonster == "Legendary")
                {
                    minDamage += 5;
                    maxDamage += 6;
                    if (chanceEscape < 75)
                        chanceEscape = chanceEscape + 6;
                    evasion = evasion + 4;
                }
                else if (monsters.rarityMonster == "Mythic")
                {
                    minDamage += 7;
                    maxDamage += 8;
                    if (chanceEscape < 75)
                        chanceEscape = chanceEscape + 10;
                    evasion = evasion + 7;
                }
            }
            if (typePlayer== "warrior")
            {
                if(monsters.rarityMonster=="Rare")
                {
                    fullHealth = fullHealth + 10+5;
                    minDamage += 2;
                    maxDamage += 3;
                }
                else if(monsters.rarityMonster=="Epic")
                {
                    fullHealth = fullHealth+ 10 + 10;
                    minDamage += 3;
                    maxDamage += 4;
                }
                else if(monsters.rarityMonster=="Legendary")
                {
                    fullHealth = fullHealth  + 10 + 15;
                    minDamage += 5;
                    maxDamage += 5;
                }
                else if(monsters.rarityMonster == "Mythic")
                {
                    fullHealth = fullHealth + 10 + 25;
                    minDamage += 6;
                    maxDamage += 8;
                }
            }
            if(typePlayer== "shooter")
            {
                if(monsters.rarityMonster=="Rare")
                {
                    minDamage += 2;
                    maxDamage += 3;
                    evasion = evasion + 2;
                }
                else if(monsters.rarityMonster == "Epic")
                {
                    minDamage += 5;
                    maxDamage += 6;
                    evasion = evasion + 3;
                }
                else if (monsters.rarityMonster == "Legendary")
                {
                    minDamage += 6;
                    maxDamage += 8;
                    evasion = evasion + 4;
                }
                else if (monsters.rarityMonster == "Mythic")
                {
                    minDamage += 8;
                    maxDamage += 10;
                    evasion = evasion + 5;
                }

            }
        }

        public void Experience(Player player,Monsters monster)
        {
            monster.expLvlUp(monster);
            initialExperience += monster.experience;
            if(initialExperience>=experienceRequaired)
            {
                lvl += 1;
                experienceRequaired *= 2.5;//difference in experience between levels
                if (typePlayer=="robber")
                {
                    

                    player.fullHealth = (int)(player.fullHealth * 1.2);
                    minDamage += 1;
                    maxDamage += 2;
                    if(chanceEscape<75)
                    chanceEscape = chanceEscape + 1;
                    evasion = evasion + 1;

                }
                if (typePlayer == "warrior")
                {
                    player.fullHealth = (int)(player.fullHealth * 1.2)+10;
                    minDamage += 1;
                    maxDamage += 1;
                }
                if(typePlayer=="shooter")
                {
                    player.fullHealth = (int)(player.fullHealth * 1.2);
                    minDamage += 2;
                    maxDamage += 3;
                    evasion = evasion + 2;
                }
                System.out.println();
                System.out.println(ANSI_YELLOW+"Congratulations!!!You have reached level "+lvl+ANSI_RESET);
                System.out.println();
            }
        }
}




