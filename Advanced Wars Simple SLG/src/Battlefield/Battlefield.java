package Battlefield;

import Canvas.Hint;
import ConnectionPool.PoolManager;
import FactoryOfFireUnit.FactoryOfFireUnit;
import FireUnit.FireUnit;
import Global.BaseDraw;
import Global.Position;
import Memeto.LastAction;
import Memeto.Memento;
import Memeto.MementoCaretaker;
import Observer.AllyControlCenter;
import Observer.ConcreteAllyControlCenter;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Battlefield extends BaseDraw {
    private static Battlefield battlefield = null;
    private String fireUnits[][] = null;
    private boolean canMove[][] = null;
    private boolean canAction[][] = null;
    private boolean terrainStatus[][] = null;
    private PoolManager poolManager = null;

    private int numOfTileset;
    private Tileset tilesets[] = null;
    private int canvas[][] = null;
    private List<Position> moveList, aimList;
    private List<Integer> aim, dis;
    private String troopNames[] = null;
    private int troopNumbers[] = null;
    private int roundNow = -1;
    private boolean moving, actioning;
    private AllyControlCenter acc[] = null;

    public void debug() {
        for( int i = 0; i < height; ++i ) {
            for( int j = 0; j < width; ++j ) {
                System.out.print( terrainStatus[i][j] + " " );
            }
            System.out.println();
        }
    }

    private Battlefield() {
        moving = actioning = false;
        moveList = new LinkedList<>();
        aimList = new LinkedList<>();
        aim = new LinkedList<>();
        dis = new LinkedList<>();
        poolManager = PoolManager.getInstance();
    }

    public static Battlefield getInstance() {
        if( battlefield == null ) {
            synchronized( Battlefield.class ) {
                if( battlefield == null ) {
                    battlefield = new Battlefield();
                }
            }
        }
        return battlefield;
    }

    public void setBattlefield( String name ) { queryXML( name ); }

    public boolean getMovingStatus() { return moving; }

    public boolean getActiongStatus() { return actioning; }

    public String roundTurn() {
        ++roundNow;
        if( roundNow >= numOfTileset ) roundNow -= numOfTileset;
        System.out.println( roundNow + " " + numOfTileset );
        String troopName = troopNames[roundNow];
        for( int i = 0; i < height; ++i ) {
            Arrays.fill( canMove[i], false );
            Arrays.fill( canAction[i], false );
            for( int j = 0; j < width; ++j ) {
                FireUnit fu = poolManager.get( fireUnits[i][j] );
                if( fu != null && fu.getTroopName().equals( troopName ) ) {
                    canMove[i][j] = true;
                    canAction[i][j] = true;
                }
            }
        }
        int cnt = 0;
        String win = null;
        for( int i = 0; i < troopNumbers.length; ++i ) {
            if( troopNumbers[i] > 0 ) {
                ++cnt;
                win = troopNames[i];
            }
        }
        if( cnt == 1 ) return win;
        MementoCaretaker.getInstance().save( null );
        return null;
    }

    public String getCurrentTroopName() { return troopNames[roundNow]; }

    public void move( Position from, Position to )
            throws IOException, ClassNotFoundException {
        int x1 = from.getX(), y1 = from.getY(), x2 = to.getX(), y2 = to.getY();
        boolean flag = false;
        for( Position pos : moveList ) {
            if( to.equals( pos ) ) {
                flag = true;
                break;
            }
        }
        if( !flag ) return;
        FireUnit fu = PoolManager.getInstance().get( fireUnits[x1][y1] );
        fu.setPosition( to );
        fireUnits[x2][y2] = fireUnits[x1][y1];
        fireUnits[x1][y1] = "0";
        canvas[x2][y2] = canvas[x1][y1];
        canvas[x1][y1] = 76;
        setMoveStatus( to, false );
        setMoveStatus( from, false );
        setActionStatus( to, canAttack( from ) );
        setActionStatus( from, false );
        LastAction lastAction = new LastAction( "move", fu, from );
        MementoCaretaker.getInstance().save( new Memento( lastAction ) );
        moveList.clear();
        dis.clear();
    }

    public void battle( Position p1, Position p2 )
            throws IOException, ClassNotFoundException {
        int x1 = p1.getX(), y1 = p1.getY(), x2 = p2.getX(), y2 = p2.getY();
        boolean flag = false;
        for( Position pos : aimList ) {
            if( p2.equals( pos ) ) {
                flag = true;
                break;
            }
        }
        if( !flag ) return;
        FireUnit fu1, fu2;
        fu1 = PoolManager.getInstance().get( fireUnits[x1][y1] );
        fu2 = PoolManager.getInstance().get( fireUnits[x2][y2] );
        setActionStatus( p1, false );
        LastAction lastAction = new LastAction( "battle", fu1, fu2 );
        MementoCaretaker.getInstance().save( new Memento( lastAction ) );
        int damage1 = fu2.getAttackValue() - fu1.getDefenceValue();
        int damage2 = fu1.getAttackValue() - fu2.getDefenceValue();
        fu1.setHealthValue( Math.max( fu1.getHealthValue() - damage1, 0 ) );
        fu2.setHealthValue( Math.max( fu2.getHealthValue() - damage2, 0 ) );
        notice( fu1, damage1 );
        notice( fu2, damage2 );
        moveList.clear();
        aim.clear();
        dis.clear();
        aimList.clear();
    }

    private void notice( FireUnit fu, int damage ) {
        if( damage > 0 ) {
            int p = 0;
            for( String str : troopNames ) {
                if( fu.getTroopName().equals( str ) ) break;
                ++p;
            }
            fu.beAttacked( acc[p] );
        }
    }

    public void removeFireUnit( FireUnit fu ) {
        String tn = fu.getTroopName();
        Position pos = fu.getPosition();
        setMoveStatus( pos, false );
        setActionStatus( pos, false );
        int p = 0;
        for( String str : troopNames ) {
            if( tn.equals( str ) ) break;
            ++p;
        }
        acc[p].quit( fu );
        --troopNumbers[p];
        canvas[pos.getX()][pos.getY()] = 76;
        fireUnits[pos.getX()][pos.getY()] = "0";
    }

    public void restore() throws IOException, ClassNotFoundException {
        Memento memento = MementoCaretaker.getInstance().restore();
        if( memento == null ) return;
        LastAction lastAction = memento.getLastAction();
        String state = lastAction.getState();
        System.out.println( state );
        if( state.equals( "move" ) ) {
            moveList.clear();
            moveList.add( lastAction.getFireUnit2().getPosition() );
            move( lastAction.getFireUnit1().getPosition(), lastAction.getFireUnit2().getPosition() );
        } else {
            FireUnit fu1 = lastAction.getFireUnit1();
            FireUnit fu2 = lastAction.getFireUnit2();
            int x1 = fu1.getPosition().getX(), y1 = fu1.getPosition().getY();
            int x2 = fu2.getPosition().getX(), y2 = fu2.getPosition().getY();
            int health1 = fu1.getHealthValue(), health2 = fu2.getHealthValue();
            if( fireUnits[x1][y1].equals( "0" ) ) {
                Hint.getInstance().setText( "己方部队已死亡" );
            } else if( fireUnits[x2][y2].equals( "0" ) ) {
                Hint.getInstance().setText( "敌方部队已死亡" );
            } else {
                fu1 = PoolManager.getInstance().get( fireUnits[x1][y1] );
                fu2 = PoolManager.getInstance().get( fireUnits[x2][y2] );
                fu1.setHealthValue( health1 );
                fu2.setHealthValue( health2 );
            }
        }
    }

    public FireUnit getFireUnit( Position pos ) {
        String fid = fireUnits[pos.getX()][pos.getY()];
        return PoolManager.getInstance().get( fid );
    }

    public void setMoveStatus( Position pos, boolean flag ) { canMove[pos.getX()][pos.getY()] = flag; }

    public boolean canSelected( Position pos ) { return canMove[pos.getX()][pos.getY()]; }

    public void setActionStatus( Position pos, boolean flag ) { canAction[pos.getX()][pos.getY()] = flag; }

    public boolean canAttack( Position pos ) { return canAction[pos.getX()][pos.getY()]; }

    public void draw( GraphicsContext gc ) {
        gc.save();
        for( int x = 0; x < height; ++x ) {
            for( int y = 0; y < width; ++y ) {
                int pos = getLayer( canvas[x][y] );
                int tmp = canvas[x][y] - tilesets[pos].getFirestgid();
                int px = tmp / tilesets[pos].getColumns();
                int py = tmp % tilesets[pos].getColumns();
                int tTileWidth = tilesets[pos].getTilewidth();
                int tTileHeight = tilesets[pos].getTileheight();
                gc.drawImage( tilesets[pos].getImage(),
                        py * tTileWidth, px * tTileHeight, tTileWidth, tTileHeight,
                        y * tTileWidth, x * tTileHeight, tTileWidth, tTileHeight );
            }
        }
        gc.restore();
    }

    private int getLayer( int num ) {
        int pos;
        for( pos = numOfTileset - 1; pos >= 0; --pos ) {
            if( tilesets[pos].getFirestgid() <= num )
                return pos;
        }
        return 0;
    }

    private void queryXML( String name ) {
        try {
            String rootPath = System.getProperty( "user.dir" );
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbFactory.newDocumentBuilder();
            Document doc = db.parse( rootPath + "/src/Battlefield/" + name + "/" + name + ".tmx" );
            Element map = doc.getDocumentElement();
            width = Integer.parseInt( map.getAttribute( "width" ) );
            height = Integer.parseInt( map.getAttribute( "height" ) );
            fireUnits = new String[height][width];
            canMove = new boolean[height][width];
            canAction = new boolean[height][width];
            terrainStatus = new boolean[height][width];
            canvas = new int[height][width];
            for( int i = 0; i < height; ++i ) Arrays.fill( fireUnits[i], "0" );
            NodeList nodeListTileset = map.getElementsByTagName( "tileset" );
            if( nodeListTileset == null ) return;
            numOfTileset = nodeListTileset.getLength();
            tilesets = new Tileset[numOfTileset];
            for( int i = 0; i < numOfTileset; ++i ) {
                Element tilesetElement = ( Element )nodeListTileset.item( i );
                setTilesets( i, name, tilesetElement );
            }
            NodeList nodeListLayer = map.getElementsByTagName( "layer" );
            if( nodeListLayer == null ) return;
            int numOfLayers = nodeListLayer.getLength();
            troopNames = new String[numOfLayers - 2];
            troopNumbers = new int[numOfLayers - 2];
            acc = new ConcreteAllyControlCenter[numOfLayers - 2];
            Element terrainElement = ( Element )nodeListLayer.item( 0 );
            setTerrain( terrainElement );
            for( int i = 1; i < numOfLayers - 1; ++i ) {
                acc[i - 1] = new ConcreteAllyControlCenter();
                Element fireUnitElement = ( Element )nodeListLayer.item( i );
                setFireUnits( i - 1, fireUnitElement );
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
        Element imageElement = ( Element )tilesetElement.getElementsByTagName( "image" ).item( 0 );
        String source = imageElement.getAttribute( "source" );
        Image image = new Image( getClass().getResourceAsStream( "/Battlefield/" + name + File.separator + source ) );
        tilesets[i].setImage( image );
    }

    private void setTerrain( Element terrainElement ) {
        Element data = ( Element )terrainElement.getElementsByTagName( "data" ).item( 0 );
        String str[] = data.getFirstChild().getNodeValue().substring( 1 ).split( "\n" );
        for( int i = 0; i < str.length; ++i ) {
            String str2[] = str[i].trim().split( "," );
            for( int j = 0; j < str2.length; ++j ) {
                int tmp = Integer.parseInt( str2[j] );
                if( tmp != 0 ) canvas[i][j] = tmp;
            }
        }
    }

    private void setFireUnits( int No, Element fireUnitElement )
            throws IOException, ClassNotFoundException {
        String troopName = fireUnitElement.getAttribute( "name" );
        troopNames[No] = troopName;
        Element data = ( Element )fireUnitElement.getElementsByTagName( "data" ).item( 0 );
        String str[] = data.getFirstChild().getNodeValue().substring( 1 ).split( "\n" );
        for( int i = 0; i < str.length; ++i ) {
            String str2[] = str[i].trim().split( "," );
            for( int j = 0; j < str2.length; ++j ) {
                int tmp = Integer.parseInt( str2[j] );
                if( tmp != 0 ) {
                    canvas[i][j] = tmp;
                    addFireUnit( No, str2[j], new Position( i, j ) );
                }
            }
        }
    }

    private void setTerrainStatus( Element lastLayer ) {
        Element data = ( Element )lastLayer.getElementsByTagName( "data" ).item( 0 );
        String str[] = data.getFirstChild().getNodeValue().substring( 1 ).split( "\n" );
        for( int i = 0; i < str.length; ++i ) {
            String str2[] = str[i].trim().split( "," );
            for( int j = 0; j < str2.length; ++j ) {
                int tmp = Integer.parseInt( str2[j] );
                terrainStatus[i][j] = ( tmp == 0 );
            }
        }
    }

    private void addFireUnit( int No, String category, Position position )
            throws IOException, ClassNotFoundException {
        FireUnit fireUnit = FactoryOfFireUnit.getInstance().produceFireUnit( troopNames[No], category, position );
        ++troopNumbers[No];
        acc[No].join( fireUnit );
        poolManager.add( fireUnit, true );
        fireUnits[position.getX()][position.getY()] = fireUnit.getID();
    }

    private void getMovableRange( Position position ) {
        int dx[] = { -1, 0, 1, 0 }, dy[] = { 0, 1, 0, -1 };
        int range = poolManager.get( fireUnits[position.getX()][position.getY()] ).getMoveRange();
        moveList.clear();
        dis.clear();
        moveList.add( position );
        dis.add( range );
        for( int i = 0; i < moveList.size(); ++i ) {
            Position tp = moveList.get( i );
            for( int j = 0; j < 4; ++j ) {
                int tx = tp.getX() + dx[j], ty = tp.getY() + dy[j];
                if( 0 <= tx && tx <= height && 0 <= ty && ty <= width && terrainStatus[tx][ty] &&
                        fireUnits[tx][ty].equals( "0" ) && dis.get( i ) > 0 ) {
                    Position newp = new Position( tx, ty );
                    moveList.add( newp );
                    dis.add( dis.get( i ) - 1 );
                }
            }
        }
        moveList.remove( 0 );
        dis.remove( 0 );
    }

    public void drawMovableRange( Position position, boolean flag ) {
        if( flag ) getMovableRange( position );
        for( Position pos : moveList ) {
            int tx = pos.getX(), ty = pos.getY();
            canvas[tx][ty] = flag ? 94 : 76;
        }
        moving = flag;
    }

    private void getAssaultableRange( Position position, String troopName ) {
        int dx[] = { -1, 0, 1, 0 }, dy[] = { 0, 1, 0, -1 };
        int range = poolManager.get( fireUnits[position.getX()][position.getY()] ).getAttackRange();
        aimList.clear();
        aim.clear();
        dis.clear();
        moveList.clear();
        moveList.add( position );
        dis.add( range );
        for( int i = 0; i < moveList.size(); ++i ) {
            Position tp = moveList.get( i );
            for( int j = 0; j < 4; ++j ) {
                int tx = tp.getX() + dx[j], ty = tp.getY() + dy[j];
                if( 0 <= tx && tx <= height && 0 <= ty && ty <= width &&
                        terrainStatus[tx][ty] && dis.get( i ) > 0 ) {
                    Position newp = new Position( tx, ty );
                    moveList.add( newp );
                    dis.add( dis.get( i ) - 1 );
                    FireUnit fu = poolManager.get( fireUnits[tx][ty] );
                    if( fu != null && !fu.getTroopName().equals( troopName ) ) {
                        aim.add( canvas[tx][ty] );
                        aimList.add( newp );
                    }
                }
            }
        }
    }

    public void drawAssaultableRange( Position position, boolean flag ) {
        String troopName = troopNames[roundNow];
        if( flag ) getAssaultableRange( position, troopName );
        for( int i = 0; i < aimList.size(); ++i ) {
            Position pos = aimList.get( i );
            int tx = pos.getX(), ty = pos.getY();
            canvas[tx][ty] = flag ? 95 : aim.get( i );
        }
        actioning = flag;
    }
}
