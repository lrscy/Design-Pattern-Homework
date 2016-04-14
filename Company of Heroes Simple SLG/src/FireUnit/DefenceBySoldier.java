package FireUnit;

public class DefenceBySoldier implements WayOfDefence {
    private int reduceDamage = 10;

    public int defenceEffect() {
        return reduceDamage;
    }
}
