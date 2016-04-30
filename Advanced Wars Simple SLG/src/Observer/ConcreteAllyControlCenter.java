package Observer;

public class ConcreteAllyControlCenter extends AllyControlCenter {
    public void notifyObservers( String id ) {
        for( Observer obs : fireUnits ) {
            if( !obs.getID().equals( id ) ) {
                obs.help();
            }
        }
    }
}
