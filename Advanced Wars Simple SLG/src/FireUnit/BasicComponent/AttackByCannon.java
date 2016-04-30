package FireUnit.BasicComponent;

public class AttackByCannon implements WayOfAttack {
    private String hashCode = "1";
    private int attackDamage = 40;
    private int attackRange = 2;

    public int maxAttackRange() {
        return attackRange;
    }

    public int attackEffect() {
        return attackDamage;
    }

    public String getHashCode() {
        return hashCode;
    }
}
