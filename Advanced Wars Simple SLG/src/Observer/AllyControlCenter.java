package Observer;

import java.util.ArrayList;

abstract public class AllyControlCenter {
    ArrayList<Observer> fireUnits = new ArrayList<>();

    public void join( Observer obs ) { fireUnits.add( obs ); }

    public void quit( Observer obs ) {
        for( Observer o : fireUnits ) {
            if( o.getID().equals( obs.getID() ) && obs.equals( o ) )
                fireUnits.remove( obs );
        }
    }

    abstract public void notifyObservers( String id );
}
