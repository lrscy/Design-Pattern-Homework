package Global;

/**
 * Description: 火力单元ID生成器
 */
public class IDGenerator {
    private int number;
    private static IDGenerator idGenerator = null;

    private IDGenerator() {
        number = 1;
    }

    public static IDGenerator getInstance() {
        if( idGenerator == null ) {
            synchronized( IDGenerator.class ) {
                if( idGenerator == null ) {
                    idGenerator = new IDGenerator();
                }
            }
        }
        return idGenerator;
    }

    public String getID() {
        ++number;
        return String.format( "%06d", number );
    }
}
