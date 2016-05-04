package Battlefield;

import Canvas.Hint;
import ConnectionPool.PoolManager;
import FactoryOfFireUnit.FactoryOfFireUnit;
import FireUnit.BasicComponent.FactoryOfLongRangeUnit;
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

/**
 * Description: 地图类，记录地图中每个点的信息并进行移动及战斗动作的处理。
 */
public class Battlefield extends BaseDraw {
    private static Battlefield battlefield = null;
    private String fireUnits[][] = null;    // 记录每个点的火力单元ID
    private boolean canMove[][] = null;     // 记录每个点是否可以移动
    private boolean canAction[][] = null;   // 记录每个点是否可以攻击
    private boolean terrainStatus[][] = null;   // 记录每个点是否可达
    private PoolManager poolManager = null; // 对象池

    private int numOfTileset;           // 地图文件中图片数
    private Tileset tilesets[] = null;
    private int canvas[][] = null;      // 每个点的显示图像的标号
    private List<Position> moveList, aimList;   // 移动和攻击范围信息
    private List<Integer> aim, dis;
    private String troopNames[] = null; // 地图中每个玩家部队的名称
    private int troopNumbers[] = null;  // 每个部队的活力单元个数
    private int roundNow = -1;
    private boolean moving, actioning;
    private AllyControlCenter acc[] = null; // 每个部队同一通信频道

    private Battlefield() {
        moving = actioning = false;
        moveList = new LinkedList<>();
        aimList = new LinkedList<>();
        aim = new LinkedList<>();
        dis = new LinkedList<>();
        poolManager = PoolManager.getInstance();
        for( int i = 0; i < 40; ++i ) {
            FireUnit fu = FactoryOfFireUnit.getInstance().
                    produceFireUnit( "", Integer.toString( i ), new Position( 0, 0 ) );
            if( fu != null ) {
                try {
                    PoolManager.getInstance().add( fu, false );
                } catch( IOException | ClassNotFoundException e ) {
                    e.printStackTrace();
                }
            }
        }
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

    public boolean getActionStatus() { return actioning; }

    /**
     * Description: 回合转换
     * @return      是否有部队胜利
     */
    public String roundTurn() {
        // 回合转换
        ++roundNow;
        if( roundNow >= troopNames.length ) roundNow -= troopNames.length;

        Hint.getInstance().setText( troopNames[roundNow] + "回合" );
        // 激活可操作部队的所有火力单元
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

        // 判断是否有部队最终胜出
        int cnt = 0;
        String win = null;
        for( int i = 0; i < troopNumbers.length; ++i ) {
            if( troopNumbers[i] > 0 ) {
                ++cnt;
                win = troopNames[i];
            }
        }
        if( cnt == 1 ) return win;

        // 部队转换后上一步清空
        MementoCaretaker.getInstance().save( null );
        return null;
    }

    public String getCurrentTroopName() { return troopNames[roundNow]; }

    /**
     * Description: 移动操作
     * @param from  起始移动坐标
     * @param to    目标移动坐标
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void move( Position from, Position to )
            throws IOException, ClassNotFoundException {
        // 判断目标移动坐标是否在可移动范围内
        boolean flag = false;
        for( Position pos : moveList ) {
            if( to.equals( pos ) ) {
                flag = true;
                break;
            }
        }
        if( !flag ) return;

        // 移动火力单元并更新画布及该单元状态
        int x1 = from.getX(), y1 = from.getY(), x2 = to.getX(), y2 = to.getY();
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

        // 记录当前操作
        LastAction lastAction = new LastAction( "move", fu, from );
        MementoCaretaker.getInstance().save( new Memento( lastAction ) );

        moveList.clear();
        dis.clear();
    }

    /**
     * Description: 战斗操作
     * @param p1    战斗攻击方坐标
     * @param p2    战斗防御方坐标
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void battle( Position p1, Position p2 )
            throws IOException, ClassNotFoundException {
        // 判断目标攻击坐标是否在可攻击范围内
        boolean flag = false;
        for( Position pos : aimList ) {
            if( p2.equals( pos ) ) {
                flag = true;
                break;
            }
        }
        if( !flag ) return;

        // 进行战斗操作并更新画布及双方状态
        int x1 = p1.getX(), y1 = p1.getY(), x2 = p2.getX(), y2 = p2.getY();
        FireUnit fu1, fu2;
        fu1 = PoolManager.getInstance().get( fireUnits[x1][y1] );
        fu2 = PoolManager.getInstance().get( fireUnits[x2][y2] );
        setActionStatus( p1, false );
        // 记录当前操作
        LastAction lastAction = new LastAction( "battle", fu1, fu2 );
        MementoCaretaker.getInstance().save( new Memento( lastAction ) );
        int damage1 = fu2.getAttackValue() - fu1.getDefenceValue();
        int damage2 = fu1.getAttackValue() - fu2.getDefenceValue();
        if( fu1.getBasicComponent().getHashCode().equals( "120" ) ) damage1 = 0;
        fu1.setHealthValue( Math.max( fu1.getHealthValue() - damage1, 0 ) );
        fu2.setHealthValue( Math.max( fu2.getHealthValue() - damage2, 0 ) );

        // 提醒玩家
        notice( fu1, damage1 );
        notice( fu2, damage2 );

        moveList.clear();
        aim.clear();
        dis.clear();
        aimList.clear();
    }

    /**
     * Description: 战场状况实时播报
     * @param fu     受伤火力单元
     * @param damage 收到的伤害值
     */
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

    /**
     * Description: 从地图中删去一个火力单元
     * @param fu    需要删除的火力单元
     */
    public void removeFireUnit( FireUnit fu ) {
        String tn = fu.getTroopName();
        Position pos = fu.getPosition();
        // 更新状态
        setMoveStatus( pos, false );
        setActionStatus( pos, false );
        int p = 0;
        for( String str : troopNames ) {
            if( tn.equals( str ) ) break;
            ++p;
        }
        acc[p].quit( fu );
        --troopNumbers[p];
        // 更新画布及地图信息
        canvas[pos.getX()][pos.getY()] = 76;
        fireUnits[pos.getX()][pos.getY()] = "0";
    }

    /**
     * Description: 恢复上一步操作
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void restore() throws IOException, ClassNotFoundException {
        Memento memento = MementoCaretaker.getInstance().restore();
        if( memento == null ) return;

        // 获取上一步操作
        LastAction lastAction = memento.getLastAction();
        String state = lastAction.getState();
        if( state.equals( "move" ) ) {
            moveList.clear();
            Position pos1 = lastAction.getFireUnit1().getPosition();
            Position pos2 = lastAction.getFireUnit2().getPosition();
            moveList.add( pos2 );
            boolean flag = canMove[pos1.getX()][pos1.getY()];
            move( lastAction.getFireUnit1().getPosition(), lastAction.getFireUnit2().getPosition() );
            canMove[pos2.getX()][pos2.getY()] = !flag;
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
            canAction[x1][y1] = true;
            MementoCaretaker.getInstance().save( null );
        }
    }

    /**
     * Description: 从地图中获取当前坐标的活力单元
     * @param pos   要获取的坐标
     * @return      该坐标的火力单元
     */
    public FireUnit getFireUnit( Position pos ) {
        String fid = fireUnits[pos.getX()][pos.getY()];
        return PoolManager.getInstance().get( fid );
    }

    public void setMoveStatus( Position pos, boolean flag ) { canMove[pos.getX()][pos.getY()] = flag; }

    public boolean canSelected( Position pos ) { return canMove[pos.getX()][pos.getY()]; }

    public void setActionStatus( Position pos, boolean flag ) { canAction[pos.getX()][pos.getY()] = flag; }

    public boolean canAttack( Position pos ) { return canAction[pos.getX()][pos.getY()]; }

    @Override
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

    /**
     * Description: 判断当前图片ID属于地图文件中的哪副图片
     * @param num   当前图片ID
     * @return      当前图片ID输于哪副图片
     */
    private int getLayer( int num ) {
        int pos;
        for( pos = numOfTileset - 1; pos >= 0; --pos ) {
            if( tilesets[pos].getFirestgid() <= num )
                return pos;
        }
        return 0;
    }

    /**
     * Description: 解析地图文件
     * @param name  需解析的地图文件名
     */
    private void queryXML( String name ) {
        try {
            // 获取地图文件
            String rootPath = System.getProperty( "user.dir" );
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbFactory.newDocumentBuilder();
            Document doc = db.parse( rootPath + "/src/Battlefield/" + name + "/" + name + ".tmx" );

            // 设置地图基础信息
            Element map = doc.getDocumentElement();
            width = Integer.parseInt( map.getAttribute( "width" ) );
            height = Integer.parseInt( map.getAttribute( "height" ) );
            fireUnits = new String[height][width];
            canMove = new boolean[height][width];
            canAction = new boolean[height][width];
            terrainStatus = new boolean[height][width];
            canvas = new int[height][width];
            for( int i = 0; i < height; ++i ) Arrays.fill( fireUnits[i], "0" );

            // 获取地图文件中所有图片信息
            NodeList nodeListTileset = map.getElementsByTagName( "tileset" );
            if( nodeListTileset == null ) return;
            numOfTileset = nodeListTileset.getLength();
            tilesets = new Tileset[numOfTileset];
            for( int i = 0; i < numOfTileset; ++i ) {
                Element tilesetElement = ( Element )nodeListTileset.item( i );
                setTilesets( i, name, tilesetElement );
            }

            // 获取地图文件中所有图层信息
            NodeList nodeListLayer = map.getElementsByTagName( "layer" );
            if( nodeListLayer == null ) return;
            int numOfLayers = nodeListLayer.getLength();
            troopNames = new String[numOfLayers - 2];
            troopNumbers = new int[numOfLayers - 2];
            acc = new ConcreteAllyControlCenter[numOfLayers - 2];
            // 设置地形
            Element terrainElement = ( Element )nodeListLayer.item( 0 );
            setTerrain( terrainElement );
            // 设置各部队火力单元
            for( int i = 1; i < numOfLayers - 1; ++i ) {
                acc[i - 1] = new ConcreteAllyControlCenter();
                Element fireUnitElement = ( Element )nodeListLayer.item( i );
                setFireUnits( i - 1, fireUnitElement );
            }
            // 设置可走区域
            Element lastLayer = ( Element )nodeListLayer.item( nodeListLayer.getLength() - 1 );
            setTerrainStatus( lastLayer );
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * Description: 解析每幅图片
     * @param i     图编号
     * @param name  地图文件名
     * @param tilesetElement    该图的节点信息
     */
    private void setTilesets( int i, String name, Element tilesetElement ) {
        tilesets[i] = new Tileset();
        tilesets[i].setFirestgid( tilesetElement.getAttribute( "firstgid" ) );
        tilesets[i].setName( tilesetElement.getAttribute( "name" ) );
        tilesets[i].setTilewidth( tilesetElement.getAttribute( "tilewidth" ) );
        tilesets[i].setTileheight( tilesetElement.getAttribute( "tileheight" ) );
        tilesets[i].setColumns( tilesetElement.getAttribute( "columns" ) );
        Element imageElement = ( Element )tilesetElement.getElementsByTagName( "image" ).item( 0 );
        String source = imageElement.getAttribute( "source" );
        Image image = new Image( getClass().getResourceAsStream( "/Battlefield/" + name + File.separator + source ) );
        tilesets[i].setImage( image );
    }

    /**
     * Description: 设置地形
     * @param terrainElement    地形的节点信息
     */
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

    /**
     * Description: 向地图中添加某部队的火力单元
     * @param No    部队编号
     * @param fireUnitElement   部队火力单元节点信息
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     * Description: 设置地形
     * @param lastLayer 地形节点信息
     */
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

    /**
     * Description: 向对象池中添加火力单元，向地图中添加火力单元信息
     * @param No    部队编号
     * @param category  火力单元类别号
     * @param position  火力单元位置
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void addFireUnit( int No, String category, Position position )
            throws IOException, ClassNotFoundException {
        FireUnit fireUnit = FactoryOfFireUnit.getInstance().produceFireUnit( troopNames[No], category, position );
        ++troopNumbers[No];
        acc[No].join( fireUnit );
        poolManager.add( fireUnit, true );
        fireUnits[position.getX()][position.getY()] = fireUnit.getID();
    }

    /**
     * Description: 查询该位置上的火力单元的移动范围
     * @param position  查询火力单元的坐标
     */
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

    /**
     * Description: 在画布上画出该火力单元的活动范围
     * @param position  查询的火力单元位置
     * @param flag      画出范围(true)或是恢复原样(false)
     */
    public void drawMovableRange( Position position, boolean flag ) {
        if( flag ) getMovableRange( position );
        for( Position pos : moveList ) {
            int tx = pos.getX(), ty = pos.getY();
            canvas[tx][ty] = flag ? 94 : 76;
        }
        moving = flag;
    }

    /**
     * Description: 查询该位置上的火力单元的攻击范围
     * @param position  查询火力单元的坐标
     * @param troopName 查询火力单元所属部队
     */
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

    /**
     * Description: 在画布上画出该火力单元的攻击范围
     * @param position  查询的火力单元位置
     * @param flag      画出范围(true)或是恢复原样(false)
     */
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
