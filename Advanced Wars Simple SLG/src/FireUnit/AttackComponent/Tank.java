package FireUnit.AttackComponent;

public class Tank implements WeaponOfFireUnit {
    private String hashCode = "4";
    private String weaponName = "坦克";
    private int damageEnhance = 15;

    public String getName() {
        return weaponName;
    }

    public int attackEnhance( int basicAttackDamage ) {
        return basicAttackDamage + damageEnhance;
    }

    public String getHashCode() {
        return hashCode;
    }
}
