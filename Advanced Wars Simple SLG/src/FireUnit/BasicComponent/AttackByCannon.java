package FireUnit.BasicComponent;

/**
 * Description: 基础攻击类型为火炮
 */
class AttackByCannon implements WayOfAttack {
    @Override
    public int maxAttackRange() {
        return 2;
    }

    @Override
    public int attackEffect() {
        return 30;
    }

    @Override
    public String getHashCode() {
        return "1";
    }
}
