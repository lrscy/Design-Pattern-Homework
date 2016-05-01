package FireUnit.BasicComponent;

public class MoveByWalk implements WayOfMove {
    private String hashCode = "0";
    private int moveRange = 2;

    @Override
    public int maxMoveRange() {
        return moveRange;
    }

    @Override
    public String getHashCode() {
        return hashCode;
    }
}
