package Memeto;

import FireUnit.FireUnit;
import Global.Position;

import java.io.IOException;

public class LastAction {
    private String state = null;
    private FireUnit fireUnit1, fireUnit2;

    public LastAction( String state, FireUnit fireUnit1, Position position )
            throws IOException, ClassNotFoundException {
        this.state = state;
        this.fireUnit1 = fireUnit1.deepClone();
        this.fireUnit2 = fireUnit1.deepClone();
        this.fireUnit2.setPosition( position );
    }

    public LastAction( String state, FireUnit fireUnit1, FireUnit fireUnit2 )
            throws IOException, ClassNotFoundException {
        this.state = state;
        this.fireUnit1 = fireUnit1.deepClone();
        this.fireUnit2 = fireUnit2.deepClone();
    }

    public String getState() { return state; }

    public FireUnit getFireUnit1() { return fireUnit1; }

    public FireUnit getFireUnit2() { return fireUnit2; }
}
