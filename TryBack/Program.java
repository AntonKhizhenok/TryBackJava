package TryBack;
import java.io.IOException;
import java.util.Scanner;
public class Program {
    public static void main(String[] args) throws IOException {
        Scanner scan1=new Scanner(System.in);
        System.out.println("1)Start new game");
        System.out.println("2)Continue game");
        int newOrCont = scan1.nextInt();
        System.out.println("\033[H\033[J");
        switch (newOrCont)
        {
            case 1:
                Player.DeletePlayer();
                Game.Start();
                break;
            case 2:
                Game.Continue(); 
                break;
        }
        scan1.close();
    }
}
