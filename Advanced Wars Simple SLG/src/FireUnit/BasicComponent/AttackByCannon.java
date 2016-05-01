package FireUnit.BasicComponent;

public class AttackByCannon implements WayOfAttack {
    private String hashCode = "1";
    private int attackDamage = 40;
    private int attackRange = 2;

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
