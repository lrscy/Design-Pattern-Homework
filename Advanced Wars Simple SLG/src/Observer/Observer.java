package Observer;

public interface Observer {
    public String getID();

    public void beAttacked( AllyControlCenter acc );

    public void help();
}
