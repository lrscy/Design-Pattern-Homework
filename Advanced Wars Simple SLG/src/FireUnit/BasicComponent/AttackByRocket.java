package FireUnit.BasicComponent;

/**
 * Description: 基础攻击类型为远程火箭弹
 */
class AttackByRocket implements WayOfAttack {
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
        return "2";
    }
}
