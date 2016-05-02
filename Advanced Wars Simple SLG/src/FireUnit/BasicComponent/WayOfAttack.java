package FireUnit.BasicComponent;

/**
 * Description: 攻击方式接口 抽象攻击方式
 */
interface WayOfAttack {
    int maxAttackRange();

    int attackEffect();

    String getHashCode();
}
