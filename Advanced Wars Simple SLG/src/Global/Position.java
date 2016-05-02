package Global;

import java.io.Serializable;

/**
 * Description: 位置
 */
public class Position implements Serializable {
    private int x, y;

    public Position( Position pos ) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public Position( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    void setX( int x ) { this.x = x; }

    public int getX() { return x; }

    void setY( int y ) { this.y = y; }

    public int getY() { return y; }

    @Override
    public boolean equals( Object obj ) {
        Position pos;
        if( obj instanceof Position ) {
            pos = ( Position )obj;
            return ( x == pos.getX() && y == pos.getY() );
        }
        return false;
    }

    @Override
    public String toString() { return x + " " + y; }
}
