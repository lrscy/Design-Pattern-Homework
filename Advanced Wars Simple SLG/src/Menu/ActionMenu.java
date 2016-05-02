package Menu;

import Global.BaseDraw;
import Global.TextObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Description: 动作菜单 在点击火力单元时出现在火力单元右侧
 */
public class ActionMenu extends BaseDraw {
    private TextObject[] textObjects;
    private Paint color = Color.BLACK;

    private OnMenuItemClickListener onMenuItemClickListener;

    public ActionMenu( String[] strs, int width, int height ) {
        setWidth( width );
        setHeight( height );
        textObjects = new TextObject[strs.length];
        for( int i = 0; i < textObjects.length; ++i ) {
            textObjects[i] = new TextObject();
            textObjects[i].setText( strs[i] );
            textObjects[i].setColor( Color.WHITE );
            textObjects[i].setFontSize( 16 );
        }
    }

    public void onMousePressed( MouseEvent e ) {
        if( onMenuItemClickListener != null ) {
            for( int i = 0; i < textObjects.length; ++i ) {
                if( textObjects[i].isIn( ( int )e.getX(), ( int )e.getY() ) ) {
                    onMenuItemClickListener.onMenuItemClick( i );
                }
            }
        }
    }

    @Override
    public void draw( GraphicsContext gc ) {
        gc.save();
        gc.setGlobalAlpha( 0.8f );
        gc.setStroke( color );
        gc.fillRect( position.getY() * 32 + 32, position.getX() * 32, width, height );
        for( int i = 0; i < textObjects.length; ++i ) {
            textObjects[i].setX( getY() * 32 + 32 + ( getWidth() - textObjects[i].getWidth() ) / 2 );
            int spaceLine = 5;
            textObjects[i].setY( getX() * 32 + spaceLine * ( i + 1 ) + textObjects[i].getHeight() * ( i + 1 ) );
            textObjects[i].draw( gc );
        }
        gc.restore();
    }

    public TextObject[] getTextObjects() { return textObjects; }

    public void setOnMenuItemClickListener( OnMenuItemClickListener onMenuItemClickListener ) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick( int index );
    }
}
