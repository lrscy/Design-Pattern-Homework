package FireUnit.AttackComponent;

import java.io.Serializable;

public class AttackComponentDecorator extends AttackComponent implements Serializable {
    private String hashCode = "1";
    private AttackComponent attackComponent;

    public AttackComponentDecorator( AttackComponent attackComponent ) {
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
        return attackComponent.getWeapon().getHashCode() + hashCode;
    }
}
