package FireUnit.BasicComponent;

/**
 * Description: 装甲兵基础属性产品簇
 */
public class FactoryOfArmourUnit implements AbstractFactoryOfFireUnit {
    @Override
    public String getName() {
        return "装甲车";
    }

    @Override
    public WayOfMove CreateWayOfMoving() {
        return new MoveByVehicle();
    }

    @Override
    public WayOfAttack CreateWayOfAttack() {
        return new AttackByCannon();
    }

    @Override
    public WayOfDefence CreateWayOfDefence() {
        return new DefenceByArmour();
    }
}
