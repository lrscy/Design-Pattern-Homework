package Observer;

import java.util.ArrayList;

abstract public class AllyControlCenter {
    protected ArrayList<Observer> fireUnits = new ArrayList<>();

    public void join( Observer obs ) { fireUnits.add( obs ); }

    public void quit( Observer obs ) { fireUnits.remove( obs ); }

    abstract public void notifyObservers( String id );
}
