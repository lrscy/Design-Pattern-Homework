package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 火箭弹武器属性
 */
public class RocketGun implements WeaponOfFireUnit, Serializable {
    @Override
    public String getName() {
        return "火箭炮";
    }

    @Override
    public int attackEnhance( int basicAttackDamage ) {
        int damageEnhance = 20;
        return basicAttackDamage + damageEnhance;
    }

    @Override
    public String getHashCode() {
        return "6";
    }
}
