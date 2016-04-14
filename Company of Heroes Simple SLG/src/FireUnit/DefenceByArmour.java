package FireUnit;

public class DefenceByArmour implements WayOfDefence {
    private int reduceDamage = 20;

    public int defenceEffect() {
        return reduceDamage;
    }
}
