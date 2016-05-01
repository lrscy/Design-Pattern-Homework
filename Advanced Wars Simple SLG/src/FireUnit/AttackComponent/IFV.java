package FireUnit.AttackComponent;

import java.io.Serializable;

public class IFV implements WeaponOfFireUnit, Serializable {
    private String hashCode = "3";
    private String weaponName = "步兵战车";
    private int damageEnhance = 0;

    @Override
    public String getName() {
        return weaponName;
    }

    @Override
    public int attackEnhance( int basicAttackDamage ) {
        return basicAttackDamage + damageEnhance;
    }

    @Override
    public String getHashCode() {
        return hashCode;
    }
}
