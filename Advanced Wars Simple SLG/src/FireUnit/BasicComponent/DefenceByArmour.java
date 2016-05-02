package FireUnit.BasicComponent;

/**
 * Description: 基础防御类型为装甲
 */
class DefenceByArmour implements WayOfDefence {
    @Override
    public int defenceEffect() {
        return 20;
    }

    @Override
    public String getHashCode() {
        return "1";
    }
}
