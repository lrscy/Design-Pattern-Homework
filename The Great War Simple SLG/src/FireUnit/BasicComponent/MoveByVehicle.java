package FireUnit.BasicComponent;

public class MoveByVehicle implements WayOfMove {
    private String hashCode = "1";
    private int moveRange = 4;

    public int maxMoveRange() {
        return moveRange;
    }

    public String getHashCode() {
        return hashCode;
    }
}
