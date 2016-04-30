package Canvas;

import Global.BaseDraw;
import Global.TextObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Hint extends BaseDraw {
    private TextObject textObject;
    private Paint color = Color.BLACK;
    private static Hint hint = null;

    private Hint() {
        textObject = new TextObject();
        textObject.setColor( Color.RED );
    }

    public static Hint getInstance() {
        if( hint == null ) {
            synchronized( Hint.class ) {
                if( hint == null ) {
                    hint = new Hint();
                }
            }
        }
        return hint;
    }

    public void setText( String text ) { textObject.setText( "提示：" + text ); }

    void setSize( int width, int height ) {
        this.width = width;
        this.height = height;
    }

    public void draw( GraphicsContext gc ) {
        gc.save();
        gc.setStroke( color );
        gc.setGlobalAlpha( 0.8f );
        gc.fillRect( position.getY() * 32, position.getX() * 32, width, height );
        if( textObject != null ) {
            textObject.setX( ( getWidth() - textObject.getWidth() ) / 2 + getY() * 32 );
            int spaceLine = 5;
            textObject.setY( getX() * 32 + spaceLine + textObject.getHeight() );
            textObject.draw( gc );
        }
        gc.restore();
    }
}
