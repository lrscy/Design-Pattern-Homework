package FireUnit.BasicComponent;

public class DefenceByArmour implements WayOfDefence {
    private String hashCode = "1";
    private int reduceDamage = 20;

    @Override
    public int defenceEffect() {
        return reduceDamage;
    }

    @Override
    public String getHashCode() {
        return hashCode;
    }
}
