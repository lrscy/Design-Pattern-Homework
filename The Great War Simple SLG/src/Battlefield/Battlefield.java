package Battlefield;

import java.util.Arrays;

public class Battlefield {
    private String fireUnits[][] = null;
    private int width = 0, height = 0;
    private boolean canMove[][] = null;
    private boolean canAction[][] = null;
    private static Battlefield battlefield = null;

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

    public String getFireUnitID( int x, int y ) {
        return fireUnits[x][y];
    }

    public boolean canSelected( int x, int y ) {
        return canMove[x][y];
    }

    public boolean canAttack( int x, int y ) {
        return canAction[x][y];
    }

    // TODO: 和前端显示衔接
    public void display() {
        ;
    }
}
