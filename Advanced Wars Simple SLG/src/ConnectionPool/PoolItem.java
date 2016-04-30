package ConnectionPool;

import FireUnit.FireUnit;

import java.io.IOException;

class PoolItem {
    boolean isUsed;
    String id;
    FireUnit fireUnit;

    PoolItem( FireUnit fireUnit, boolean flag )
            throws IOException, ClassNotFoundException {
        this.fireUnit = fireUnit.deepClone();
        this.id = this.fireUnit.getID();
        isUsed = flag;
    }
}
