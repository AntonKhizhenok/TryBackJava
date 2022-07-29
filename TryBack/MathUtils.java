package TryBack;

public class MathUtils {
    public static int GetRandomNumber(int n)
    {
        return (int)(Math.random()*++n);
    }
    public static int GetRandomDamage(int minDamage,int maxDamage)
    {
        return (int)(Math.random()*((++maxDamage)-minDamage)+minDamage);
    }

}
