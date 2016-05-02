package Memeto;

import FireUnit.FireUnit;
import Global.Position;

import java.io.IOException;

/**
 * Description: 需记录的动作
 */
public class LastAction {
    private String state = null;
    private FireUnit fireUnit1, fireUnit2;

    /**
     * Description: 为移动操作设计的记录函数
     * @param state 当前动作方式
     * @param fireUnit1 要移动的火力单元
     * @param position  移动目标位置
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public LastAction( String state, FireUnit fireUnit1, Position position )
            throws IOException, ClassNotFoundException {
        this.state = state;
        // 所有记录元素均是独立的而非某个外部元素的引用
        this.fireUnit1 = fireUnit1.deepClone();
        this.fireUnit2 = fireUnit1.deepClone();
        this.fireUnit2.setPosition( position );
    }

    /**
     * Description: 为战斗操作设计的记录函数
     * @param state 当前动作方式
     * @param fireUnit1 战斗攻击方
     * @param fireUnit2 战斗防御方
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public LastAction( String state, FireUnit fireUnit1, FireUnit fireUnit2 )
            throws IOException, ClassNotFoundException {
        this.state = state;
        // 所有记录元素均是独立的而非某个外部元素的引用
        this.fireUnit1 = fireUnit1.deepClone();
        this.fireUnit2 = fireUnit2.deepClone();
    }

    public String getState() { return state; }

    public FireUnit getFireUnit1() { return fireUnit1; }

    public FireUnit getFireUnit2() { return fireUnit2; }
}
