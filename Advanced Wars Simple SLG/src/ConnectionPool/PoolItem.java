package ConnectionPool;

import FireUnit.FireUnit;

public class PoolItem {
    public boolean isUsed;
    public String id;
    public FireUnit fireUnit;

    public PoolItem( FireUnit fireUnit, boolean flag ) {
        this.fireUnit = fireUnit;
        isUsed = flag;
    }
}
