package Menu;

import FireUnit.FireUnit;
import Global.BaseDraw;
import Global.TextObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PropertyMenu extends BaseDraw {
    private TextObject[] textObjects;
    private Paint color = Color.BLACK;

    public PropertyMenu( int width, int height ) {
        this.width = width;
        this.height = height;
        textObjects = new TextObject[5];
        for( int i = 0; i < 5; ++i ) {
            textObjects[i] = new TextObject();
            textObjects[i].setColor( Color.WHITE );
        }
    }

    public void initFireUnit( FireUnit fireUnit ) {
        setProperty( textObjects[0], "隶属", fireUnit.getTroopName() );
        setProperty( textObjects[1], "兵种", fireUnit.getUnitName() );
        setProperty( textObjects[2], "武器", fireUnit.getWeaponName() );
        setProperty( textObjects[3], "生命值", Integer.toString( fireUnit.getHealthValue() ) + "/100" );
        setProperty( textObjects[4], "状态", fireUnit.getHealthStatus() );
    }

    private void setProperty( TextObject textObject, String propertyName, String value ) {
        textObject.setText( propertyName + ": " + value );
        textObject.setFontSize( 16 );
    }

    public void draw( GraphicsContext gc ) {
        gc.save();
        gc.setStroke( color );
        gc.setGlobalAlpha( 0.8f );
        gc.fillRect( position.getY() * 32, position.getX() * 32, width, height );
        if( textObjects != null ) {
            for( int i = 0; i < textObjects.length; i++ ) {
                textObjects[i].setX( ( getWidth() - textObjects[i].getWidth() ) / 2 + getY() * 32 );
                int spaceLine = 5;
                textObjects[i].setY( getX() * 32 + spaceLine * ( i + 1 ) + textObjects[i].getHeight() * ( i + 1 ) );
                textObjects[i].draw( gc );
            }
        }
        gc.restore();
    }
}
