package Observer;

public class ConcreteAllyControlCenter extends AllyControlCenter {
    @Override
    public void notifyObservers( String id ) {
        for( Observer obs : fireUnits ) {
            if( !obs.getID().equals( id ) ) {
                obs.help();
            }
        }
    }
}
