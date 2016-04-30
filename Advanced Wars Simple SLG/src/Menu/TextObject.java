package Menu;

import Global.BaseDraw;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class TextObject extends BaseDraw {
    private String text;
    private Font font = Font.getDefault();
    private double fontSize = Font.getDefault().getSize();
    private Paint color = Color.BLACK;

    public TextObject() {}

    public TextObject( String text ) { this.text = text; }

    public void draw( GraphicsContext gc ) {
        gc.save();
        gc.setFont( font );
        gc.setFill( color );
        if( text != null ) gc.fillText( text, position.getX(), position.getY() );
        gc.restore();
    }

    public int getWidth() {
        FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics( font );
        return (int)fm.computeStringWidth( text );
    }

    public int getHeight() {
        FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics( font );
        return (int)fm.getLineHeight();
    }

    public void setText( String text ) { this.text = text; }

    public String getText() { return text; }

    public void setFont( Font font ) { this.font = font; }

    public Font getFont() { return font; }

    public void setColor( Paint color ) { this.color = color; }

    public Paint getColor() { return color; }

    public void setFontSize( double fontSize ) {
        this.fontSize = fontSize;
        this.font = new Font( font.getFamily(), fontSize );
    }
}
