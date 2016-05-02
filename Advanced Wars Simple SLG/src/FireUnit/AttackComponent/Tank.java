package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 坦克武器属性
 */
public class Tank implements WeaponOfFireUnit, Serializable {
    @Override
    public String getName() {
        return "坦克";
    }

    @Override
    public int attackEnhance( int basicAttackDamage ) {
        int damageEnhance = 15;
        return basicAttackDamage + damageEnhance;
    }

    @Override
    public String getHashCode() {
        return "4";
    }
}
