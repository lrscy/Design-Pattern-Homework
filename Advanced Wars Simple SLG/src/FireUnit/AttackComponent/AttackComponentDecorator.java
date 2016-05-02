package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 攻击属性装饰者
 */
class AttackComponentDecorator extends AttackComponent implements Serializable {
    // 被装饰的对象
    private AttackComponent attackComponent;

    AttackComponentDecorator( AttackComponent attackComponent ) {
        this.attackComponent = attackComponent;
    }

    @Override
    public String getName() {
        return attackComponent.getName();
    }

    @Override
    public void setAttackDamage( int attackDamages ) {
        attackComponent.setAttackDamage( attackDamages );
    }

    @Override
    public void setWeapon( WeaponOfFireUnit weapon ) {
        attackComponent.setWeapon( weapon );
    }

    @Override
    public WeaponOfFireUnit getWeapon() {
        return attackComponent.getWeapon();
    }

    @Override
    public int getAttackDamage() {
        return attackComponent.getAttackDamage();
    }

    @Override
    public String getHashCode() {
        String hashCode = "1";
        return attackComponent.getWeapon().getHashCode() + hashCode;
    }
}
