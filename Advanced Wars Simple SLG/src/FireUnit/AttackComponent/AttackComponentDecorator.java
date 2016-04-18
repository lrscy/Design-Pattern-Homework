package FireUnit.AttackComponent;

public class AttackComponentDecorator extends AttackComponent {
    private String hashCode = "1";
    private AttackComponent attackComponent;

    public AttackComponentDecorator( AttackComponent attackComponent ) { this.attackComponent = attackComponent; }

    public String getName() { return attackComponent.getName(); }

    public void setAttackDamage( int attackDamages ) { attackComponent.setAttackDamage( attackDamages ); }

    public void setWeapon( WeaponOfFireUnit weapon ) { attackComponent.setWeapon( weapon ); }

    public WeaponOfFireUnit getWeapon() { return attackComponent.getWeapon(); }

    public int getAttackDamage() { return attackComponent.getAttackDamage(); }

    public String getHashCode() { return hashCode + attackComponent.getWeapon().getHashCode(); }
}
