package FireUnit.AttackComponent;

import java.io.Serializable;

public class IFV implements WeaponOfFireUnit, Serializable {
    private String hashCode = "3";
    private String weaponName = "步兵战车";
    private int damageEnhance = 0;

    public String getName() { return weaponName; }

    public int attackEnhance( int basicAttackDamage ) { return basicAttackDamage + damageEnhance; }

    public String getHashCode() { return hashCode; }
}
