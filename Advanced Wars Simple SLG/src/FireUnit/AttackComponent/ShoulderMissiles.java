package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 肩扛式火箭弹武器属性
 */
public class ShoulderMissiles implements WeaponOfFireUnit, Serializable {
    @Override
    public String getName() {
        return "肩扛式火箭弹";
    }

    @Override
    public int attackEnhance( int basicAttackDamage ) {
        int damageEnhance = 10;
        return basicAttackDamage + damageEnhance;
    }

    @Override
    public String getHashCode() {
        return "2";
    }
}
