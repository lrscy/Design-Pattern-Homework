package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 重机枪武器属性
 */
public class MechineGun implements WeaponOfFireUnit, Serializable {

    @Override
    public String getName() {
        return "重机枪";
    }

    @Override
    public int attackEnhance( int basicAttackDamage ) {
        int damageEnhance = 5;
        return basicAttackDamage + damageEnhance;
    }

    @Override
    public String getHashCode() {
        return "1";
    }
}
