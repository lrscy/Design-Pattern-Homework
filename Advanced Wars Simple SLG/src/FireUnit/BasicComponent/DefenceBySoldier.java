package FireUnit.BasicComponent;

/**
 * Description: 基础防御类型为士兵
 */
class DefenceBySoldier implements WayOfDefence {
    @Override
    public int defenceEffect() {
        return 10;
    }

    @Override
    public String getHashCode() {
        return "0";
    }
}
