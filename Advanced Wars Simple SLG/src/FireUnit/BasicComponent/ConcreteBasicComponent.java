package FireUnit.BasicComponent;

/**
 * Description: 具体基础属性 具体享元 基础属性抽象工厂的客户端
 */
class ConcreteBasicComponent extends BasicComponent {
    private String hashCode = null;
    private String name = null;
    private WayOfMove wayOfMove = null;
    private WayOfAttack wayOfAttack = null;
    private WayOfDefence wayOfDefence = null;

    @Override
    public String getName() {
        return name;
    }

    ConcreteBasicComponent() {
    }

    @Override
    public void createFireUnit( AbstractFactoryOfFireUnit abstractFactoryOfFireUnit ) {
        this.name = abstractFactoryOfFireUnit.getName();
        this.wayOfMove = abstractFactoryOfFireUnit.CreateWayOfMoving();
        this.wayOfAttack = abstractFactoryOfFireUnit.CreateWayOfAttack();
        this.wayOfDefence = abstractFactoryOfFireUnit.CreateWayOfDefence();
    }

    @Override
    public int maxMoveRange() {
        return wayOfMove.maxMoveRange();
    }

    @Override
    public int maxAttackRange() {
        return wayOfAttack.maxAttackRange();
    }

    @Override
    public int attackEffect() {
        return wayOfAttack.attackEffect();
    }

    @Override
    public int defenceEffect() {
        return wayOfDefence.defenceEffect();
    }

    @Override
    public String getHashCode() {
        if( hashCode == null ) {
            hashCode = wayOfMove.getHashCode() + wayOfAttack.getHashCode() + wayOfDefence.getHashCode();
        }
        return hashCode;
    }
}
