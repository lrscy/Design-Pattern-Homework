package Global;

import java.io.Serializable;

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

    public void setX( int x ) { this.x = x; }

    public int getX() { return x; }

    public void setY( int y ) { this.y = y; }

    public int getY() { return y; }

    public boolean equals( Object obj ) {
        Position pos = ( Position )obj;
        return ( x == pos.getX() && y == pos.getY() );
    }

    public String toString() { return x + " " + y; }
}
