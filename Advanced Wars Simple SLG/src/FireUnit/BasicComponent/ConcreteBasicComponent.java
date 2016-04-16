package FireUnit.BasicComponent;

public class ConcreteBasicComponent extends BasicComponent {
    private String hashCode = null;
    private String name = null;
    private WayOfMove wayOfMove = null;
    private WayOfAttack wayOfAttack = null;
    private WayOfDefence wayOfDefence = null;

    public String getName() {
        return name;
    }

    public ConcreteBasicComponent() {
    }

    public void createFireUnit( AbstractFactoryOfFireUnit abstractFactoryOfFireUnit ) {
        this.name = abstractFactoryOfFireUnit.getName();
        this.wayOfMove = abstractFactoryOfFireUnit.CreateWayOfMoving();
        this.wayOfAttack = abstractFactoryOfFireUnit.CreateWayOfAttack();
        this.wayOfDefence = abstractFactoryOfFireUnit.CreateWayOfDefence();
    }

    public int maxMoveRange() {
        return wayOfMove.maxMoveRange();
    }

    public int maxAttackRange() {
        return wayOfAttack.maxAttackRange();
    }

    public int attackEffect() {
        return wayOfAttack.attackEffect();
    }

    public int defenceEffect() {
        return wayOfDefence.defenceEffect();
    }

    // TODO:
    public String getHashCode() {
        if( hashCode == null ) {
            hashCode = wayOfMove.getHashCode() + wayOfAttack.getHashCode() + wayOfDefence.getHashCode();
        }
        return hashCode;
    }
}
