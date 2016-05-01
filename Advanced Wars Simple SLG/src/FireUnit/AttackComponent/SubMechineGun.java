package FireUnit.AttackComponent;

import java.io.Serializable;

public class SubMechineGun implements WeaponOfFireUnit, Serializable {
    private String hashCode = "0";
    private String weaponName = "冲锋枪";
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
