package data;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Enumeration with colors
 */
@XmlEnum
public enum Color {
    GREEN,
    BLACK,
    YELLOW,
    WHITE;

    /**
     *
     * @return String with all enum values of color
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (Color category : values()) {
            nameList.append(category.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}

