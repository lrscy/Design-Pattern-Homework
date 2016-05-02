package FireUnit.BasicComponent;

/**
 * Description: 基础移动类型为走路
 */
class MoveByWalk implements WayOfMove {
    @Override
    public int maxMoveRange() {
        return 2;
    }

    @Override
    public String getHashCode() {
        return "0";
    }
}
