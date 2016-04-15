package FireUnit.BasicComponent;

public class AttackByGun implements WayOfAttack {
    private String hashCode = "0";
    private int attackDamage = 20;
    private int attackRange = 1;

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
