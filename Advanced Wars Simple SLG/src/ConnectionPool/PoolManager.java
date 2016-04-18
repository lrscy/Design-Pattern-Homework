package ConnectionPool;

import FireUnit.FireUnit;

import java.util.ArrayList;
import java.util.Iterator;

public class PoolManager {
    private static PoolManager poolManager = null;
    private ArrayList<PoolItem> list = null;

    private PoolManager() { list = new ArrayList<>(); }

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

    public synchronized void add( FireUnit fireUnit, boolean flag ) {
        Iterator iter = list.iterator();
        while( iter.hasNext() ) {
            PoolItem pi = ( PoolItem )iter.next();
            if( !pi.isUsed && fireUnit.equals( pi.fireUnit ) ) {
                pi.isUsed = true;
                return;
            }
        }
        list.add( new PoolItem( fireUnit, flag ) );
    }

    public synchronized FireUnit get( String id ) {
        Iterator iter = list.iterator();
        while( iter.hasNext() ) {
            PoolItem pi = ( PoolItem )iter.next();
            String str = pi.id;
            if( str.equals( id ) && !pi.isUsed ) {
                pi.isUsed = true;
                return pi.fireUnit;
            }
        }
        return null;
    }

    public synchronized void release( String id ) {
        Iterator iter = list.iterator();
        while( iter.hasNext() ) {
            PoolItem pi = ( PoolItem )iter.next();
            String str = pi.id;
            if( pi.id.equals( id ) ) {
                pi.isUsed = false;
                pi.id = "0";
                return;
            }
        }
    }
}
