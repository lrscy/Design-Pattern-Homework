package Battlefield;

import ConnectionPool.PoolManager;
import FactoryOfFireUnit.FactoryOfFireUnit;
import FireUnit.FireUnit;
import Global.Position;
import Memeto.LastAction;
import Memeto.Memento;
import Memeto.MementoCaretaker;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Battlefield {
    private String fireUnits[][] = null;
    private int width = 0, height = 0;
    private boolean canMove[][] = null;
    private boolean canAction[][] = null;
    private boolean terrainStatus[][] = null;
    private static Battlefield battlefield = null;
    private PoolManager poolManager = null;

    private int tileWidth, tileHeight, numOfTileset;
    private Tileset tilesets[] = null;
    private int canvas[][] = null;
    private List<Position> moveList, aimList;
    private List<Integer> aim;

    private Battlefield( String name ) {
        moveList = new LinkedList<>();
        aimList = new LinkedList<>();
        aim = new LinkedList<>();
        poolManager = PoolManager.getInstance();
        queryXML( name );
    }

    public static Battlefield getInstance( String name ) {
        if( battlefield == null ) {
            synchronized( Battlefield.class ) {
                if( battlefield == null ) {
                    battlefield = new Battlefield( name );
                }
            }
        }
        return battlefield;
    }

    // TODO: check依据对象池中的对象进行回合转换
    public void Round( String troopName ) {
        for( int i = 0; i < height; ++i ) {
            Arrays.fill( canMove[i], false );
            for( int j = 0; j < width; ++j ) {
                if( poolManager.get( fireUnits[i][j] ) != null ) {
                    canMove[i][j] = true;
                }
            }
        }
    }

    public void move( Position from, Position to ) {
        int x1 = from.getX(), y1 = from.getY(), x2 = to.getX(), y2 = to.getY();
        FireUnit fu = PoolManager.getInstance().get( fireUnits[x1][y1] );
        fu.setPosition( to );
        fireUnits[x2][y2] = fireUnits[x1][y1];
        fireUnits[x1][y1] = "0";
        canvas[x2][y2] = canvas[x1][y1];
        canvas[x1][y1] = 76;
        setMoveStatus( x1, y1, false );
        LastAction lastAction = new LastAction( "move", fu, from );
        MementoCaretaker.getInstance().save( new Memento( lastAction ) );
    }

    public void battle( Position p1, Position p2 ) {
        int x1 = p1.getX(), y1 = p1.getY(), x2 = p2.getX(), y2 = p2.getY();
        FireUnit fu1 = PoolManager.getInstance().get( fireUnits[x1][y1] );
        FireUnit fu2 = PoolManager.getInstance().get( fireUnits[x2][y2] );
    }

    public void restore() {
        LastAction lastAction = MementoCaretaker.getInstance().restore().getLastAction();
        String state = lastAction.getState();
        if( state.equals( "move" ) ) {
            move( lastAction.getFireUnit2().getPosition(), lastAction.getFireUnit1().getPosition() );
        }
    }

    public String getFireUnitID( int x, int y ) { return fireUnits[x][y]; }

    public void setMoveStatus( int x, int y, boolean flag ) { canMove[x][y] = flag; }

    public boolean canSelected( int x, int y ) { return canMove[x][y]; }

    public void setActionStatus( int x, int y, boolean flag ) { canAction[x][y] = flag; }

    public boolean canAttack( int x, int y ) { return canAction[x][y]; }

    // TODO: check和前端显示衔接
    public void drawCanvas( GraphicsContext gc ) {
        for( int x = 0; x < height; ++x ) {
            for( int y = 0; y < width; ++y ) {
                int pos = getLayer( canvas[x][y] );
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

    private int getLayer( int num ) {
        int pos = 0;
        for( pos = 0; pos < numOfTileset; ++pos ) {
            if( tilesets[pos].getFirestgid() <= num )
                return pos;
        }
        return 0;
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
            if( nodeListTileset == null ) return;
            numOfTileset = nodeListTileset.getLength();
            for( int i = 0; i < numOfTileset; ++i ) {
                Element tilesetElement = ( Element )nodeListTileset.item( i );
                setTilesets( i, name, tilesetElement );
            }
            NodeList nodeListLayer = map.getElementsByTagName( "layer" );
            if( nodeListLayer == null ) return;
            Element terrainElement = ( Element )nodeListLayer.item( 0 );
            setTerrain( terrainElement );
            for( int i = 1; i < nodeListLayer.getLength() - 1; ++i ) {
                Element fireUnitElement = ( Element )nodeListLayer.item( i );
                setFireUnits( i, fireUnitElement );
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

    private void setTerrain( Element terrainElement ) {
        Element data = ( Element )terrainElement.getFirstChild();
        String str[] = data.getAttribute( "data" ).split( "," );
        for( int i = 0; i < str.length; ++i ) {
            int x = i % width, y = i / width, tmp = Integer.parseInt( str[i] );
            if( tmp != 0 ) {
                canvas[x][y] = tmp;
            }
        }
    }

    private void setFireUnits( int No, Element fireUnitElement ) {
        String troopName = fireUnitElement.getAttribute( "name" );
        Element data = ( Element )fireUnitElement.getFirstChild();
        String str[] = data.getNodeValue().split( "," );
        for( int i = 0; i < str.length; ++i ) {
            int x = i % width, y = i / width, tmp = Integer.parseInt( str[i] );
            if( tmp != 0 ) {
                canvas[x][y] = tmp;
                addFireUnit( troopName, str[i], new Position( x, y ) );
            }
        }
    }

    private void setTerrainStatus( Element lastLayer ) {
        String str[] = lastLayer.getAttribute( "data" ).split( "," );
        for( int i = 0; i < str.length; ++i ) {
            int x = i % width, y = i / width, tmp = Integer.parseInt( str[i] );
            terrainStatus[x][y] = ( tmp != 0 );
        }
    }

    private void addFireUnit( String troopName, String category, Position position ) {
        FireUnit fireUnit = FactoryOfFireUnit.getInstance().produceFireUnit( troopName, category, position );
        poolManager.add( fireUnit, true );
    }

    private void getMovableRange( Position position ) {
        int dx[] = { -1, 0, 1, 0 }, dy[] = { 0, 1, 0, -1 };
        int range = poolManager.get( fireUnits[position.getX()][position.getY()] ).getMoveRange();
        moveList.clear();
        moveList.add( position );
        for( int i = 0; i < moveList.size(); ++i ) {
            Position tp = moveList.get( i );
            --range;
            for( int j = 0; j < 4; ++j ) {
                int tx = tp.getX() + dx[j], ty = tp.getY() + dy[j];
                if( 0 <= tx && tx <= height && 0 <= ty && ty <= width &&
                        !fireUnits[tx][ty].equals( "0" ) && range > 0 ) {
                    Position newp = new Position( tx, ty );
                    moveList.add( newp );
                }
            }
        }
        moveList.remove( 0 );
    }

    public void drawMovableRange( Position position, boolean flag ) {
        if( flag ) getMovableRange( position );
        for( Position pos : moveList ) {
            int tx = pos.getX(), ty = pos.getY();
            canvas[tx][ty] = flag ? 94 : 76;
        }
        if( !flag ) moveList.clear();
    }

    private void getAttackableRange( Position position, String troopName ) {
        int dx[] = { -1, 0, 1, 0 }, dy[] = { 0, 1, 0, -1 };
        int range = poolManager.get( fireUnits[position.getX()][position.getY()] ).getAttackRange();
        aimList.clear(); aim.clear(); moveList.clear();
        moveList.add( position );
        for( int i = 0; i < moveList.size(); ++i ) {
            Position tp = moveList.get( i );
            --range;
            for( int j = 0; j < 4; ++j ) {
                int tx = tp.getX() + dx[j], ty = tp.getY() + dy[j];
                if( 0 <= tx && tx <= height && 0 <= ty && ty <= width &&
                        !fireUnits[tx][ty].equals( "0" ) && range > 0 ) {
                    Position newp = new Position( tx, ty );
                    moveList.add( newp );
                    if( poolManager.get( fireUnits[tx][ty] ).getTroopName().equals( troopName ) ) {
                        aim.add( canvas[tx][ty] );
                        aimList.add( newp );
                    }
                }
            }
        }
    }

    public void drawAttackableRange( Position position, boolean flag, String troopName ) {
        if( flag ) getAttackableRange( position, troopName );
        for( int i = 0; i < moveList.size(); ++i ) {
            Position pos = moveList.get( i );
            int tx = pos.getX(), ty = pos.getY();
            canvas[tx][ty] = flag ? 95 : aim.get( i );
        }
        if( !flag ) { moveList.clear(); aim.clear(); aimList.clear(); }
    }
}
