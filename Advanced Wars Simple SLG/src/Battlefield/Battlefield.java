package Battlefield;

import ConnectionPool.PoolManager;
import Factory.FactoryOfFireUnit;
import Global.Position;

import java.util.ArrayList;
import java.util.Arrays;

public class Battlefield {
    private String fireUnits[][] = null;
    private int width = 0, height = 0;
    private boolean canMove[][] = null;
    private boolean canAction[][] = null;
    private static Battlefield battlefield = null;
    private ArrayList<FactoryOfFireUnit> factoryOfFireUnits = new ArrayList<>();

    private Battlefield( int height, int width ) {
        if( canMove == null ) canMove = new boolean[height][width];
        if( canAction == null ) canAction = new boolean[height][width];
        if( fireUnits == null ) fireUnits = new String[height][width];
        Arrays.fill( canMove, true );
        Arrays.fill( canAction, true );
        Arrays.fill( fireUnits, "" );
    }

    public Battlefield getInstance( int height, int width ) {
        if( battlefield == null ) {
            synchronized( Battlefield.class ) {
                if( battlefield == null ) {
                    battlefield = new Battlefield( height, width );
                }
            }
        }
        return battlefield;
    }

    // TODO: 系统随机生成id
    public void addFactoryOfFireUnit( String troopName, FactoryOfFireUnit factoryOfFireUnit ) {
        ;
    }

    // TODO: 依据对象池中的对象进行回合转换
    public void Round( String troopName ) {
        for( int i = 0; i < height; ++i ) {
            for( int j = 0; j < width; ++j ) {
                ;
            }
        }
    }

    public void move( Position from, Position to ) {
        fireUnits[to.getX()][to.getY()] = fireUnits[from.getX()][from.getY()];
        fireUnits[from.getX()][from.getY()] = "0";
    }

    public String getFireUnitID( int x, int y ) {
        return fireUnits[x][y];
    }

    public void setMoveStatus( int x, int y, boolean flag ) { canMove[x][y] = flag; }

    public boolean canSelected( int x, int y ) {
        return canMove[x][y];
    }

    public void setActionStatus( int x, int y, boolean flag ) { canAction[x][y] = flag; }

    public boolean canAttack( int x, int y ) {
        return canAction[x][y];
    }

    // TODO: 和前端显示衔接
    public void display() {
        ;
    }
}
