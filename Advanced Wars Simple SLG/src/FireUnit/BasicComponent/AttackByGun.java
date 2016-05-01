package FireUnit.BasicComponent;

public class AttackByGun implements WayOfAttack {
    private String hashCode = "0";
    private int attackDamage = 20;
    private int attackRange = 1;

    @Override
    public int maxAttackRange() {
        return attackRange;
    }

    @Override
    public int attackEffect() {
        return attackDamage;
    }

    @Override
    public String getHashCode() {
        return hashCode;
    }
}
