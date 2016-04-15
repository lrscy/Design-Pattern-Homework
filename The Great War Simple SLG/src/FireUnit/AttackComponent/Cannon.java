package FireUnit.AttackComponent;

public class Cannon implements WeaponOfFireUnit {
    private String hashCode = "5";
    private String weaponName = "远程火炮";
    private int damageEnhance = 0;

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