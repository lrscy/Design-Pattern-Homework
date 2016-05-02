package FireUnit.BasicComponent;

/**
 * Description: 基础属性接口 抽象享元
 */
abstract public class BasicComponent {
    abstract public String getName();

    abstract public void createFireUnit( AbstractFactoryOfFireUnit abstractFactoryOfFireUnit );

    abstract public int maxMoveRange();

    abstract public int maxAttackRange();

    abstract public int attackEffect();

    abstract public int defenceEffect();

    abstract public String getHashCode();
}
