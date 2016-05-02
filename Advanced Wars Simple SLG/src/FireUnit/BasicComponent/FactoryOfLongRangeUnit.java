package FireUnit.BasicComponent;

/**
 * Description: 远程火炮基础属性产品簇
 */
public class FactoryOfLongRangeUnit implements AbstractFactoryOfFireUnit {
    @Override
    public String getName() {
        return "远程部队";
    }

    @Override
    public WayOfMove CreateWayOfMoving() {
        return new MoveByVehicle();
    }

    @Override
    public WayOfAttack CreateWayOfAttack() {
        return new AttackByRocket();
    }

    @Override
    public WayOfDefence CreateWayOfDefence() {
        return new DefenceBySoldier();
    }
}
