package Canvas;

import Battlefield.Battlefield;
import FireUnit.FireUnit;
import Global.Position;
import Menu.ActionMenu;
import Menu.ControlMenu;
import Menu.PropertyMenu;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Description: 主画布 负责整个游戏的业务逻辑 所有前端事物都经此传递分配给后端处理
 */
class MainCanvas extends Canvas {
    // 游戏状态
    private enum Status {NONE, SHOW_MENU, SHOW_PROPERTY, MOVE, ATTACK, END}
    private Status nowStatus = Status.NONE;

    private GraphicsContext gc;
    private Battlefield battlefield;
    private ActionMenu actionMenu;
    private PropertyMenu propertyMenu;
    private ControlMenu controlMenu;
    private Hint hint;
    private int tileWidth = 32, tileHeight = 32;

    private boolean isRunning = true;
    private long sleep = 100;
    private Position lastPosition;

    MainCanvas( int width, int height ) {
        super( width, height );
        String field = "Battlefield_01";    // 设置地图名称
        gc = getGraphicsContext2D();
        battlefield = Battlefield.getInstance();
        battlefield.setBattlefield( field );
        battlefield.roundTurn();

        // 负责显示
        Thread thread = new Thread( () -> {
            while( isRunning ) {
                Platform.runLater( this::draw );
                try {
                    Thread.sleep( sleep );
                } catch( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        } );
        thread.start();

        actionMenu = new ActionMenu( new String[]{ "移动", "攻击", "待命" }, 50, 90 );
        actionMenu.setOnMenuItemClickListener( index -> {
            switch( index ) {
                case 0:
                    if( battlefield.canSelected( lastPosition ) ) {
                        battlefield.drawMovableRange( lastPosition, true );
                        nowStatus = Status.MOVE;
                    } else nowStatus = Status.NONE;
                    break;
                case 1:
                    if( battlefield.canAttack( lastPosition ) ) {
                        battlefield.drawAssaultableRange( lastPosition, true );
                        nowStatus = Status.ATTACK;
                    } else nowStatus = Status.NONE;
                    break;
                case 2:
                    battlefield.setMoveStatus( lastPosition, false );
                    battlefield.setActionStatus( lastPosition, false );
                    nowStatus = Status.NONE;
                    break;
            }
        } );

        propertyMenu = new PropertyMenu( 130, 140 );

        controlMenu = new ControlMenu( new String[]{ "撤回操作", "结束操作", "结束游戏" }, 100, 90 );
        controlMenu.setPosition( 0, 17 );
        controlMenu.setOnMenuItemClickListener( index -> {
            switch( index ) {
                case 0:
                    try {
                        battlefield.restore();
                    } catch( IOException | ClassNotFoundException e ) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    battlefield.roundTurn();
                    break;
                case 2:
                    System.exit( 0 );
                    break;
            }
        } );

        hint = Hint.getInstance();
        hint.setSize( battlefield.getWidth() * 32, 50 );
        hint.setPosition( battlefield.getHeight() - 1, 0 );
        hint.setText( "就绪" );

        setOnMouseClicked( e -> {
            if( e.getButton() == MouseButton.PRIMARY ) {
                // 因为界面获得的坐标和程序内部使用坐标系不同，这里进行一次变换
                int x = ( int )( e.getY() / tileWidth );
                int y = ( int )( e.getX() / tileHeight );
                Position pos = new Position( x, y );
                hint.setText( "就绪" );
                controlMenu.onMousePressed( e );
                switch( nowStatus ) {
                    case NONE:
                        FireUnit fu = battlefield.getFireUnit( pos );
                        if( fu == null ) break;
                        propertyMenu.initFireUnit( fu );
                        // 依据当前点状态确定三个元素的显示颜色及菜单显示位置
                        if( fu.getTroopName().equals( battlefield.getCurrentTroopName() ) ) {
                            actionMenu.setPosition( pos );
                            actionMenu.getTextObjects()[0].setColor(
                                    battlefield.canSelected( pos ) ? Color.WHITE : Color.DARKGRAY );
                            actionMenu.getTextObjects()[1].setColor(
                                    battlefield.canAttack( pos ) ? Color.WHITE : Color.DARKGRAY );
                            actionMenu.getTextObjects()[2].setColor(
                                    ( battlefield.canSelected( pos ) || battlefield.canAttack( pos ) ) ?
                                            Color.WHITE : Color.DARKGRAY );
                            lastPosition = new Position( pos );
                            nowStatus = Status.SHOW_MENU;
                        } else {
                            nowStatus = Status.SHOW_PROPERTY;
                        }
                        break;
                    case SHOW_MENU:
                        actionMenu.onMousePressed( e );
                        break;
                    case MOVE:
                        battlefield.drawMovableRange( lastPosition, false );
                        try {
                            battlefield.move( lastPosition, pos );
                        } catch( IOException | ClassNotFoundException e1 ) {
                            e1.printStackTrace();
                        }
                        nowStatus = Status.NONE;
                        break;
                    case ATTACK:
                        battlefield.drawAssaultableRange( lastPosition, false );
                        try {
                            battlefield.battle( lastPosition, pos );
                        } catch( IOException | ClassNotFoundException e1 ) {
                            e1.printStackTrace();
                        }
                        nowStatus = Status.NONE;
                        break;
                }
            } else if( e.getButton() == MouseButton.SECONDARY ) {
                nowStatus = Status.NONE;
                if( battlefield.getMovingStatus() ) battlefield.drawMovableRange( lastPosition, false );
                if( battlefield.getActiongStatus() ) battlefield.drawAssaultableRange( lastPosition, false );
                lastPosition = null;
            }
        } );
    }

    private void draw() {
        battlefield.draw( gc );
        controlMenu.draw( gc );
        hint.draw( gc );
        switch( nowStatus ) {
            case SHOW_MENU:
                actionMenu.draw( gc );
                propertyMenu.draw( gc );
                break;
            case SHOW_PROPERTY:
                propertyMenu.draw( gc );
                break;
            case END:
                break;
        }
    }
}
