package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 抽象攻击属性
 */
abstract public class AttackComponent implements Serializable {
    abstract public String getName();

    abstract public void setAttackDamage( int attackDamage );

    abstract public void setWeapon( WeaponOfFireUnit weapon );

    abstract public WeaponOfFireUnit getWeapon();

    abstract public int getAttackDamage();

    abstract public String getHashCode();
}
