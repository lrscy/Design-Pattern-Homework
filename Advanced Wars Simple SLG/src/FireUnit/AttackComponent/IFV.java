package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 步兵战车武器属性
 */
public class IFV implements WeaponOfFireUnit, Serializable {

    @Override
    public String getName() {
        return "步兵战车";
    }

    @Override
    public int attackEnhance( int basicAttackDamage ) {
        int damageEnhance = 0;
        return basicAttackDamage + damageEnhance;
    }

    @Override
    public String getHashCode() {
        return "3";
    }
}
