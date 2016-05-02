package Observer;

import java.util.ArrayList;

/**
 * Description: 抽象广播平台
 */
abstract public class AllyControlCenter {
    ArrayList<Observer> fireUnits = new ArrayList<>();

    public void join( Observer obs ) { fireUnits.add( obs ); }

    public void quit( Observer obs ) {
        for( Observer o : fireUnits ) {
            if( o.getID().equals( obs.getID() )  && obs.equals( o ) ) {
                fireUnits.remove( obs );
                break;
            }
        }
    }

    abstract public void notifyObservers( String id );
}
