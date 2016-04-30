package Global;

import javafx.scene.canvas.GraphicsContext;

public abstract class BaseDraw {
    public int width, height;
    public Position position = new Position( 0, 0 );

    abstract public void draw( GraphicsContext gc );

    public void setPosition( int x, int y ) {
        position.setX( x );
        position.setY( y );
    }

    public void setPosition( Position pos ) {
        position.setX( pos.getX() );
        position.setY( pos.getY() );
    }

    public void setX( int x ) { position.setX( x ); }

    public int getX() { return position.getX(); }

    public void setY( int y ) { position.setY( y ); }

    public int getY() { return position.getY(); }

    public void setHeight( int height ) { this.height = height; }

    public int getHeight() { return height; }

    public void setWidth( int width ) { this.width = width; }

    public int getWidth() {
        return width;
    }

    public boolean isIn( int x, int y ) {
        return ( getX() <= x && x <= getX() + getWidth() &&
                getY() - getHeight() <= y && y <= getY() );
    }
}
