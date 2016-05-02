package FireUnit.BasicComponent;

/**
 * Description: 基础移动类型为车辆
 */
class MoveByVehicle implements WayOfMove {
    @Override
    public int maxMoveRange() {
        return 4;
    }

    @Override
    public String getHashCode() {
        return "1";
    }
}
