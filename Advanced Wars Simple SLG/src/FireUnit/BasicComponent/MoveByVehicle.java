package FireUnit.BasicComponent;

public class MoveByVehicle implements WayOfMove {
    private String hashCode = "1";
    private int moveRange = 4;

    @Override
    public int maxMoveRange() {
        return moveRange;
    }

    @Override
    public String getHashCode() {
        return hashCode;
    }
}
