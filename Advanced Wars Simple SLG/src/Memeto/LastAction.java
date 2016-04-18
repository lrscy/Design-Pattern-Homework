package Memeto;

import FireUnit.FireUnit;
import Global.Position;

public class LastAction {
    private String state = null;
    private FireUnit fireUnit1, fireUnit2;

    public LastAction( String state, FireUnit fireUnit1, Position position ) {
        this.state = state;
        this.fireUnit1 = fireUnit1;
        this.fireUnit2 = fireUnit1;
        this.fireUnit2.setPosition( position );
    }

    public LastAction( String state, FireUnit fireUnit1, FireUnit fireUnit2 ) {
        this.state = state;
        this.fireUnit1 = fireUnit1;
        this.fireUnit2 = fireUnit2;
    }

    public String getState() { return state; }

    public FireUnit getFireUnit1() { return fireUnit1; }

    public FireUnit getFireUnit2() { return fireUnit2; }
}
