package Global.Iterator;

import java.util.ArrayList;
import java.util.List;

public class ConcreteAggregate implements Aggregate {
    private List<Object> list = new ArrayList<>();

    public void add( Object obj ) { list.add( obj ); }

    public void remove( Object obj ) { list.remove( obj ); }

    public Iterator iterator() { return new ConcreteIterator( list ); }
}
