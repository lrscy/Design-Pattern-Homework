package FireUnit.BasicComponent;

public class DefenceBySoldier implements WayOfDefence {
    private String hashCode = "0";
    private int reduceDamage = 10;

    @Override
    public int defenceEffect() {
        return reduceDamage;
    }

    @Override
    public String getHashCode() {
        return hashCode;
    }
}
