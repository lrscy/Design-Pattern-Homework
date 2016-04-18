package FireUnit.BasicComponent;

public class DefenceByArmour implements WayOfDefence {
    private String hashCode = "1";
    private int reduceDamage = 20;

    public int defenceEffect() { return reduceDamage; }

    public String getHashCode() { return hashCode; }
}
