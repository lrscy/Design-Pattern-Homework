package Battlefield;

import ConnectionPool.PoolManager;
import Factory.FactoryOfFireUnit;
import Global.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class Battlefield {
    private String fireUnits[][] = null;
    private int width = 0, height = 0;
    private boolean canMove[][] = null;
    private boolean canAction[][] = null;
    private boolean terrainStatus[][] = null;
    private static Battlefield battlefield = null;
    private ArrayList<FactoryOfFireUnit> factoryOfFireUnits = null;

    private int tileWidth, tileHeight, numOfTileset;
    private Tileset tilesets[] = null;
    private int canvas[][] = null;

    private Battlefield( String name ) {
        if( factoryOfFireUnits == null ) factoryOfFireUnits = new ArrayList<>();
        queryXML( name );
    }

    public Battlefield getInstance( String name ) {
         if( battlefield == null ) {
             synchronized( Battlefield.class ) {
                 if( battlefield == null ) {
                     battlefield = new Battlefield( name );
                 }
             }
         }
         return battlefield;
     }

   // TODO: 系统随机生成id
    public void addFactoryOfFireUnit( String troopName, FactoryOfFireUnit factoryOfFireUnit ) {
    }

    // TODO: 依据对象池中的对象进行回合转换
    public void Round( String troopName ) {
        for( int i = 0; i < height; ++i ) {
            for( int j = 0; j < width; ++j ) {
                ;
            }
        }
    }

    public void move( Position from, Position to ) {
        fireUnits[to.getX()][to.getY()] = fireUnits[from.getX()][from.getY()];
        fireUnits[from.getX()][from.getY()] = "0";
        // TODO: 修改图像
    }

    public String getFireUnitID( int x, int y ) {
        return fireUnits[x][y];
    }

    public void setMoveStatus( int x, int y, boolean flag ) { canMove[x][y] = flag; }

    public boolean canSelected( int x, int y ) {
        return canMove[x][y];
    }

    public void setActionStatus( int x, int y, boolean flag ) { canAction[x][y] = flag; }

    public boolean canAttack( int x, int y ) {
        return canAction[x][y];
    }

    // TODO: 和前端显示衔接
    public void display( GraphicsContext gc ) {
        for( int x = 0; x < height; ++x ) {
            for( int y = 0; y < width; ++y ) {
                int pos;
                for( pos = 0; pos < numOfTileset; ++pos ) {
                    if( tilesets[pos].getFirestgid() <= canvas[x][y] )
                        break;
                }
                int tmp = canvas[x][y] - tilesets[pos].getFirestgid();
                int px = tmp / tilesets[pos].getColumns();
                int py = tmp % tilesets[pos].getColumns();
                int tTileWidth = tilesets[pos].getTilewidth(), tTileHeight = tilesets[pos].getTileheight();
                gc.drawImage( tilesets[pos].getImage(),
                        py * tTileWidth, px * tTileHeight, tTileWidth, tTileHeight,
                        y * tTileWidth, x * tTileHeight, tTileWidth, tTileHeight );
            }
        }
    }

    // TODO: check文件能否找到
    private void queryXML( String name ) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbFactory.newDocumentBuilder();
            Document doc = db.parse( name + File.separator + name + ".xml" );
            Element map = doc.getDocumentElement();
            width = Integer.parseInt( map.getAttribute( "width" ) );
            height = Integer.parseInt( map.getAttribute( "height" ) );
            fireUnits = new String[width][height];
            canMove = new boolean[width][height];
            canAction = new boolean[width][height];
            terrainStatus = new boolean[width][height];
            canvas = new int[width][height];
            tileWidth = Integer.parseInt( map.getAttribute( "tilewidth" ) );
            tileHeight = Integer.parseInt( map.getAttribute( "tileheight" ) );
            NodeList nodeListTileset = map.getElementsByTagName( "tileset" );
            if( nodeListTileset == null ) return ;
            numOfTileset = nodeListTileset.getLength();
            for( int i = 0; i < numOfTileset; ++i ) {
                Element tilesetElement = ( Element )nodeListTileset.item( i );
                setTilesets( i, name, tilesetElement );
            }
            NodeList nodeListLayer = map.getElementsByTagName( "layer" );
            if( nodeListLayer == null ) return ;
            for( int i = 0; i < nodeListLayer.getLength() - 1; ++i ) {
                Element layerElement = ( Element )nodeListLayer.item( i );
                setCanvas( layerElement );
            }
            Element lastLayer = ( Element )nodeListLayer.item( nodeListLayer.getLength() - 1 );
            setTerrainStatus( lastLayer );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    private void setTilesets( int i, String name, Element tilesetElement ) {
        tilesets[i] = new Tileset();
        tilesets[i].setFirestgid( tilesetElement.getAttribute( "firstgid" ) );
        tilesets[i].setName( tilesetElement.getAttribute( "name" ) );
        tilesets[i].setTilewidth( tilesetElement.getAttribute( "tilewidth" ) );
        tilesets[i].setTileheight( tilesetElement.getAttribute( "tileheight" ) );
        tilesets[i].setTilecount( tilesetElement.getAttribute( "tilecount" ) );
        tilesets[i].setColumns( tilesetElement.getAttribute( "columns" ) );
        Element imageElement = ( Element )tilesetElement.getFirstChild();
        String source = imageElement.getAttribute( "source" );
        Image image = new Image( getClass().getResourceAsStream( name + File.separator + source ) );
        tilesets[i].setImage( image );
    }

    private void setCanvas( Element layerElement ) {
        String str[] = layerElement.getAttribute( "data" ).split( "," );
        for( int i = 0; i < str.length; ++i ) {
            int x = i % width, y = i / width, tmp = Integer.parseInt( str[i] );
            if( tmp != 0 ) canvas[x][y] = tmp;
        }
    }

    private void setTerrainStatus( Element lastLayer ) {
        String str[] = lastLayer.getAttribute( "data" ).split( "," );
        for( int i = 0; i < str.length; ++i ) {
            int x = i % width, y = i / width, tmp = Integer.parseInt( str[i] );
            terrainStatus[x][y] = ( tmp != 0 );
        }
    }
}
