package FireUnit.BasicComponent;

/**
 * Description: 基础攻击类型为机枪
 */
class AttackByGun implements WayOfAttack {
    @Override
    public int maxAttackRange() {
        return 1;
    }

    @Override
    public int attackEffect() {
        return 20;
    }

    @Override
    public String getHashCode() {
        return "0";
    }
}
