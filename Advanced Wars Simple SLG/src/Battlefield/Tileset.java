package Battlefield;

import javafx.scene.image.Image;

/**
 * Description: 地图文件中的图片信息
 */
public class Tileset {
    private Image image;
    private String name;
    private int firestgid;
    private int tilewidth, tileheight;
    private int columns;

    public void setImage( Image image ) {
        this.image = image;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setFirestgid( String firstgid ) {
        this.firestgid = Integer.parseInt( firstgid );
    }

    public void setTilewidth( String tilewidth ) {
        this.tilewidth = Integer.parseInt( tilewidth );
    }

    public void setTileheight( String tileheight ) {
        this.tileheight = Integer.parseInt( tileheight );
    }

    public void setColumns( String columns ) {
        this.columns = Integer.parseInt( columns );
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getFirestgid() {
        return firestgid;
    }

    public int getTilewidth() {
        return tilewidth;
    }

    public int getTileheight() {
        return tileheight;
    }

    public int getColumns() {
        return columns;
    }
}
