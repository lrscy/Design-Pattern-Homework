package FireUnit;

public class AttackByGun implements WayOfAttack {
    private int attackDamage = 20;
    private int attackRange = 1;

    public int maxAttackRange() {
        return attackRange;
    }

    public int attackEffect() {
        return attackDamage;
    }
}
