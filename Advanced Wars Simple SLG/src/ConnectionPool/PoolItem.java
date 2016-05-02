package ConnectionPool;

import FireUnit.FireUnit;

import java.io.IOException;

/**
 * Description: 对象池中的元素
 */
class PoolItem {
    boolean isUsed;
    String id;
    FireUnit fireUnit;

    PoolItem( FireUnit fireUnit, boolean flag )
            throws IOException, ClassNotFoundException {
        // 确保每一个元素是独立完整的个体而不是外界某个元素的引用
        this.fireUnit = fireUnit.deepClone();
        this.id = this.fireUnit.getID();
        isUsed = flag;
    }
}
