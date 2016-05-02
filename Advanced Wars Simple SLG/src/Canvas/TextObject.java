package Canvas;

import Global.BaseDraw;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * Description: 基础显示元素
 */
public class TextObject extends BaseDraw {
    private String text;
    private Font font = Font.getDefault();
    private Paint color = Color.BLACK;

    public TextObject() {}

    @Override
    public void draw( GraphicsContext gc ) {
        gc.save();
        gc.setFont( font );
        gc.setFill( color );
        if( text != null ) gc.fillText( text, position.getX(), position.getY() );
        gc.restore();
    }

    @Override
    public int getWidth() {
        FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics( font );
        return ( int )fm.computeStringWidth( text );
    }

    @Override
    public int getHeight() {
        FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics( font );
        return ( int )fm.getLineHeight();
    }

    public void setText( String text ) { this.text = text; }

    public void setColor( Paint color ) { this.color = color; }

    public void setFontSize( double fontSize ) { this.font = new Font( font.getFamily(), fontSize ); }
}
