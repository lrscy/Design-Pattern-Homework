package FireUnit;

public class AttackByRocket implements WayOfAttack {
    private int attackDamage = 40;
    private int attackRange = 2;

    public int maxAttackRange() {
        return attackRange;
    }

    public int attackEffect() {
        return attackDamage;
    }
}
