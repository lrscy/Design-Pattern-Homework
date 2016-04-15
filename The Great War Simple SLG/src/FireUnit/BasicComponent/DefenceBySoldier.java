package FireUnit.BasicComponent;

public class DefenceBySoldier implements WayOfDefence {
    private String hashCode = "0";
    private int reduceDamage = 10;

    public int defenceEffect() {
        return reduceDamage;
    }

    public String getHashCode() {
        return hashCode;
    }
}
