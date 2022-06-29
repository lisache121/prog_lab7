package data;

/**
 * Enumeration with types of dragon
 */
public enum DragonType {
    WATER,
    UNDERGROUND,
    AIR,
    FIRE;

    /**
     *
     * @return String with all types
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (DragonType category : values()) {
            nameList.append(category.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }

}
