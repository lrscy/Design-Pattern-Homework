package FireUnit.BasicComponent;

public class MoveByWalk implements WayOfMove {
    private String hashCode = "0";
    private int moveRange = 2;

    public int maxMoveRange() { return moveRange; }

    public String getHashCode() { return hashCode; }
}
