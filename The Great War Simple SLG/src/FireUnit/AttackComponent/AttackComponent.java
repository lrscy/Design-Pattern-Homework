package FireUnit.AttackComponent;

abstract public class AttackComponent {
    abstract public String getName();

    abstract public void setAttackDamage( int attackDamage );

    abstract public void setWeapon( WeaponOfFireUnit weapon );

    abstract public WeaponOfFireUnit getWeapon();

    abstract public int getAttackDamage();

    abstract public String getHashCode();
}
