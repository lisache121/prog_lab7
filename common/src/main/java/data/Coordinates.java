package data;



import exceptions.InvalidArgumentsOfCoordinatesException;

import java.io.Serializable;
import java.util.Objects;

/**
 * class for coordinates values (x, y)
 */
public class Coordinates implements Serializable {

    private long x; //Значение поля должно быть больше -417
    private int y; //Значение поля должно быть больше -225

    public Coordinates(long x, int y)  {
        if (x > -417 && y > -225) {
            this.x = x;
            this.y = y;
        }


    }
    public Coordinates(){}

    /**
     *
     * @return X coordinate
     */
    public long getX() {
        return x;
    }

    public void setX(long x) throws InvalidArgumentsOfCoordinatesException {
        if (x > -417) {
            this.x = x;
        }

    }

    /**
     *
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    public void setY(int y) throws InvalidArgumentsOfCoordinatesException {
        if (y > -225) {
            this.y = y;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return x==((Coordinates) o).getX() && y == ((Coordinates) o).getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public String toConsole(){
        return "x = " + x + ", y = " + y;
    }
}
