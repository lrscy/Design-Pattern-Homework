package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 冲锋枪武器属性
 */
public class SubMechineGun implements WeaponOfFireUnit, Serializable {
    @Override
    public String getName() {
        return "冲锋枪";
    }

    @Override
    public int attackEnhance( int basicAttackDamage ) {
        int damageEnhance = 0;
        return basicAttackDamage + damageEnhance;
    }

    @Override
    public String getHashCode() {
        return "0";
    }
}
