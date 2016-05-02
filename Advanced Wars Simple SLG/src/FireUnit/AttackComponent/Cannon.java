package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 加农炮武器属性
 */
public class Cannon implements WeaponOfFireUnit, Serializable {
    @Override
    public String getName() {
        return "远程火炮";
    }

    @Override
    public int attackEnhance( int basicAttackDamage ) {
        int damageEnhance = 0;
        return basicAttackDamage + damageEnhance;
    }

    @Override
    public String getHashCode() {
        return "5";
    }
}
