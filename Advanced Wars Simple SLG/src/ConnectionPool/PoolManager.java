package ConnectionPool;

import FireUnit.FireUnit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class PoolManager {
    private static PoolManager poolManager = null;
    private ArrayList<PoolItem> list = null;

    public void debug() {
        System.out.println( list.size() );
        for( PoolItem pi : list ) {
            System.out.println( pi.id + " --- " + pi.isUsed + "\n" + pi.fireUnit );
        }
    }

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

    public synchronized void add( FireUnit fireUnit, boolean flag )
            throws IOException, ClassNotFoundException {
        for( PoolItem pi : list ) {
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
        for( PoolItem pi : list ) {
            String str = pi.id;
            if( str.equals( id ) && pi.isUsed ) {
                return pi.fireUnit;
            }
        }
        return null;
    }

    public synchronized void release( String id ) {
        for( PoolItem pi : list ) {
            if( pi.id.equals( id ) ) {
                pi.isUsed = false;
                pi.id = "0";
                return;
            }
        }
    }
}
