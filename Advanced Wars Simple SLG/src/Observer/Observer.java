package Observer;

/**
 * Description: 抽象观察者
 */
public interface Observer {
    String getID();

    void beAttacked( AllyControlCenter acc );

    void help();
}
