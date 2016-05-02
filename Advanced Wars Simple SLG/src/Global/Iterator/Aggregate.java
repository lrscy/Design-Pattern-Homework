package Global.Iterator;

/**
 * Description: 抽象聚合接口
 */
public interface Aggregate {
    void add( Object obj );

    void remove( Object obj );

    Iterator iterator();
}
