package FireUnit.AttackComponent;

import java.io.Serializable;

public class MechineGun implements WeaponOfFireUnit, Serializable {
    private String hashCode = "1";
    private String weaponName = "重机枪";
    private int damageEnhance = 5;

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
