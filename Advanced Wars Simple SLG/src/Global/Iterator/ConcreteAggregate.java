package Global.Iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 具体聚合类
 */
public class ConcreteAggregate implements Aggregate {
    private List<Object> list = new ArrayList<>();

    @Override
    public void add( Object obj ) { list.add( obj ); }

    @Override
    public void remove( Object obj ) { list.remove( obj ); }

    @Override
    public Iterator iterator() { return new ConcreteIterator( list ); }
}
