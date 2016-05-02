package ConnectionPool;

import FireUnit.FireUnit;
import Global.Iterator.Aggregate;
import Global.Iterator.ConcreteAggregate;
import Global.Iterator.Iterator;

import java.io.IOException;

/**
 * Description: 对象池管理者
 */
public class PoolManager {
    private static PoolManager poolManager = null;
    private Aggregate list = null;  // 自定义线性数据结构

    private PoolManager() { list = new ConcreteAggregate(); }

    public static PoolManager getInstance() {
        if( poolManager == null ) {
            synchronized( PoolManager.class ) {
                if( poolManager == null ) {
                    poolManager = new PoolManager();
                }
            }
        }
        return poolManager;
    }

    /**
     * Description: 往对象池中添加元素
     * @param fireUnit  需要添加的对象
     * @param flag      该对象是否已经被激活
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public synchronized void add( FireUnit fireUnit, boolean flag )
            throws IOException, ClassNotFoundException {
        Iterator it = list.iterator();
        while( it.hasNext() ) {
            PoolItem pi = ( PoolItem )it.next();
            if( fireUnit.equals( pi.fireUnit ) ) {
                if( !pi.isUsed ) {
                    pi.isUsed = flag;
                    pi.fireUnit.attrCopy( fireUnit );
                    pi.id = fireUnit.getID();
                } else {
                    PoolItem p = new PoolItem( pi.fireUnit, flag );
                    p.fireUnit.attrCopy( fireUnit );
                    p.id = fireUnit.getID();
                    list.add( p );
                }
                return;
            }
        }
        list.add( new PoolItem( fireUnit, flag ) );
    }

    public synchronized FireUnit get( String id ) {
        Iterator it = list.iterator();
        while( it.hasNext() ) {
            PoolItem pi = ( PoolItem )it.next();
            String str = pi.id;
            if( str.equals( id ) && pi.isUsed ) {
                return pi.fireUnit;
            }
        }
        return null;
    }

    public synchronized void release( String id ) {
        Iterator it = list.iterator();
        while( it.hasNext() ) {
            PoolItem pi = ( PoolItem )it.next();
            // 不删除该对象实体，只删除其使用标记，便于以后使用
            if( pi.id.equals( id ) ) {
                pi.isUsed = false;
                pi.id = "0";
                return;
            }
        }
    }
}
