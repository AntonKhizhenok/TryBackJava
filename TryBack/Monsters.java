package TryBack;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.io.*;



class Monsters extends Creature
{
    public String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String typeMonster;
    public String getTypeMonster() {
        return typeMonster;
    }

    public void setTypeMonster(String typeMonster) {
        this.typeMonster = typeMonster;
    }
    /*protected override int randDamage { get; set; }
    public override int currentHealth { get; set; }
    public override int minDamage { get; set; }
    public override int maxDamage{ get; set; }
    public override int fullHealth { get; set; }
    protected override int lvl { get; set; }*/
    public int experience;
    public String rarityMonster; 

    enum TypeMonsters
    {
        Beer,
        Spider,
        Snake
    }
    enum RarityMonster
    {
        Common,//50%
        Uncommon,//20%
        Rare,//15%
        Epic,//8%
        Legendary,//5%
        Mythic//2%
    }
    
    public Monsters(String name, String _TypeMonsters,String lvl,String minDamage,String maxDamage,String currentHealth,String _rarityMonster,String experience)
    {
        this.name=name;
        TypeMonsters[]typeMonsters1=TypeMonsters.values();
        TypeMonsters typeMonster2=typeMonsters1[Integer.parseInt(_TypeMonsters)];
        typeMonster=typeMonster2.toString();
        this.lvl = Integer.parseInt(lvl);
        this.minDamage = Integer.parseInt(minDamage);
        this.maxDamage = Integer.parseInt(maxDamage);
        this.currentHealth = Integer.parseInt(currentHealth);
        fullHealth = Integer.parseInt(currentHealth);
        RarityMonster[] arrRarityMonster=RarityMonster.values();
        RarityMonster rarityMonster2=arrRarityMonster[Integer.parseInt(_rarityMonster)];
        rarityMonster=rarityMonster2.toString();
        this.experience = Integer.parseInt(experience);
    }



    public  void expLvlUp(Monsters monsters)
    {
        monsters.experience = monsters.experience * monsters.lvl;
    }

    public void PrintMonster()
    {
        System.out.print("You have encountered "+ANSI_BLUE+name+ANSI_RESET+typeMonster+", lvl."+lvl+", dmg."+minDamage+"-"+maxDamage+", hp "+currentHealth+",rarityMonster:");
        if (rarityMonster=="Common")
        {
            System.out.println( ANSI_CYAN+rarityMonster+ANSI_RESET+")");
        }
        else if(rarityMonster=="Uncommon")
        {
            System.out.println(ANSI_GREEN+rarityMonster+ANSI_RESET+")");
        }
        else if (rarityMonster == "Rare")
        {
            System.out.println(ANSI_BLUE+rarityMonster+ANSI_RESET+")");
        }
        else if (rarityMonster == "Epic")
        {
            System.out.println(ANSI_MAGENTA+rarityMonster+ANSI_RESET+")");
        }
        else if (rarityMonster == "Legendary")
        {
            System.out.println(ANSI_YELLOW+rarityMonster+ANSI_RESET+")");
        }
        else if (rarityMonster == "Mithic")
        {
            System.out.println(ANSI_RED+rarityMonster+ANSI_RESET+")");
        }
        System.out.println();
    }

    public void InfoFightMonster(Player player)
    {
        if (player.currentHealth >= 0)
        {
            System.out.println(name+" hit You for "+ANSI_RED+randDamage+"damage"+ANSI_RESET+player.name+ANSI_GREEN+"hp:"+player.currentHealth+"/"+player.fullHealth+ANSI_RESET);
        }
        else if (player.currentHealth<0)
        {
            System.out.println(name+" hit You for "+ANSI_RED+randDamage+"damage"+ANSI_RESET);
        }
        System.out.println();
    }


    public void Attack(Player player, Monsters monsters)
    {
        randDamage = MathUtils.GetRandomDamage(minDamage, maxDamage);
        player.currentHealth -= randDamage;
        System.out.println();
    }

    public void Evasion(Player player1, Monsters monsters1)
    {
        int randEvasion = MathUtils.GetRandomNumber(101);
        if (randEvasion > player1.evasion)
        {
            monsters1.Attack(player1, monsters1);
        }
        else if (randEvasion <= player1.evasion)
        {
            System.out.println();
            System.out.println(ANSI_MAGENTA+"you dodged the monster's attack"+ANSI_RESET);
            System.out.println();
            monsters1.randDamage = 0;
        }
    }

    public static void PrintTxt() throws FileNotFoundException
    {
        String line;
        File arrMonster=new File("D:\\Monster.txt");
        Scanner scanArrMonster=new Scanner(arrMonster);
        line = scanArrMonster.nextLine();
        while (line != null)
        {
            System.out.println(line);
            line = scanArrMonster.nextLine();
        }
        scanArrMonster.close();
        
    }
    public static Monsters GetMonster() throws FileNotFoundException,IOException //созднаие монстра
    {
        int[] arrChance = { 0, 50, 70, 85, 93, 98, 100 };//0-50% is Common|50-70% is Uncommon|70-85 is Rare|85-93 is Epic|93-98 is Legendary|98-100 is Mithic
        int randChance = MathUtils.GetRandomNumber(101);
        BufferedReader reader = new BufferedReader(new FileReader("D:\\Monster.txt"));
        String str;
        List<String> list = new ArrayList<>();
        while((str = reader.readLine()) != null ){
            if(!str.isEmpty()){
            list.add(str);
            }
        }
        String[] textMonster = list.toArray(new String[0]);
        //Monsters[] allMonsters = new Monsters[textMonster.length];
        List<Monsters> allMonsterList = new ArrayList<>();
        List<Monsters> rarityMonsterList = new ArrayList<>();
        Monsters monstersObj = null;
        int randMonster = 0;

        for (int i = 0; i < textMonster.length; i++)
        {
            String[] splitedTextMonsters = textMonster[i].split(" ");
            for (int j = 0; j < splitedTextMonsters.length; j++)
            {
                String[] monsterProperties = splitedTextMonsters[j].split(";");
                allMonsterList.add(new Monsters(monsterProperties[0], monsterProperties[1], monsterProperties[2], monsterProperties[3], monsterProperties[4], monsterProperties[5], monsterProperties[6], monsterProperties[7]));                
            }
        }
        if (randChance >= arrChance[0] && randChance <= arrChance[1] )
        {
            rarityMonsterList= allMonsterList.stream().filter(allMonsters1 -> allMonsters1.rarityMonster.contains("Common")).collect(Collectors.toList());
            randMonster=MathUtils.GetRandomNumber(rarityMonsterList.size());
            monstersObj = rarityMonsterList.get(randMonster);
        }
        else if (randChance > arrChance[1] && randChance <= arrChance[2])
        {
            rarityMonsterList= allMonsterList.stream().filter(allMonsters1 -> allMonsters1.rarityMonster.contains("Unommon")).collect(Collectors.toList());
            randMonster = MathUtils.GetRandomNumber(rarityMonsterList.size());
            monstersObj = rarityMonsterList.get(randMonster);
        }
        else if (randChance > arrChance[2] && randChance <= arrChance[3])
        {
            rarityMonsterList= allMonsterList.stream().filter(allMonsters1 -> allMonsters1.rarityMonster.contains("Rare")).collect(Collectors.toList());
            randMonster = MathUtils.GetRandomNumber(rarityMonsterList.size());
            monstersObj = rarityMonsterList.get(randMonster);
        }
        else if (randChance > arrChance[3] && randChance <= arrChance[4])
        {
            rarityMonsterList= allMonsterList.stream().filter(allMonsters1 -> allMonsters1.rarityMonster.contains("Epic")).collect(Collectors.toList());
            randMonster = MathUtils.GetRandomNumber(rarityMonsterList.size());
            monstersObj = rarityMonsterList.get(randMonster);
        }
        else if (randChance > arrChance[4] && randChance <= arrChance[5] )
        {
            rarityMonsterList= allMonsterList.stream().filter(allMonsters1 -> allMonsters1.rarityMonster.contains("Legendary")).collect(Collectors.toList());
            randMonster = MathUtils.GetRandomNumber(rarityMonsterList.size());
            monstersObj = rarityMonsterList.get(randMonster);
        }
        else if (randChance > arrChance[5] && randChance <= arrChance[6] )
        {
            rarityMonsterList= allMonsterList.stream().filter(allMonsters1 -> allMonsters1.rarityMonster.contains("Mithic")).collect(Collectors.toList());
            randMonster = MathUtils.GetRandomNumber(rarityMonsterList.size());
            monstersObj = rarityMonsterList.get(randMonster);
        }
        reader.close();
        return monstersObj;
    }
}
