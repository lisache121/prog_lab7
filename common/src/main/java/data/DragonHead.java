package data;

import java.io.Serializable;
import java.util.Objects;

/**
 * class for dragon's head
 */
public class DragonHead implements Serializable {

    private Long size; //Поле может быть null

    private Float eyesCount;

    private Long toothCount; //Поле может быть null

    public DragonHead() {
    }



    public DragonHead(Long size, Float eyesCount, Long toothCount) {
        this.size = size;
        this.eyesCount = eyesCount;
        this.toothCount = toothCount;
    }

    /**
     *
     * @return size value of head
     */
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    /**
     *
     * @return number of eyes
     */
    public Float getEyesCount() {
        return eyesCount;
    }

    public void setEyesCount(Float eyesCount) {
        this.eyesCount = eyesCount;
    }

    /**
     *
     * @return number of teeth
     */
    public Long getToothCount() {
        return toothCount;
    }

    public void setToothCount(Long toothCount)  {
        this.toothCount = toothCount;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DragonHead)) return false;
        DragonHead that = (DragonHead) o;
        return Float.compare(that.eyesCount, eyesCount) == 0 && Objects.equals(size, that.size) && Objects.equals(toothCount, that.toothCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, eyesCount, toothCount);
    }

    @Override
    public String toString() {
        return "DragonHead{" +
                "size=" + size +
                ", eyesCount=" + eyesCount +
                ", toothCount=" + toothCount +
                '}';
    }

    /**
     *
     * @return String with all fields
     */
    public String toConsole(){
        String str = "";
        if (size==null){
            str += ", size of head is undefined";
        }else{
            str += ", size of head = " + size;
        }
        if(eyesCount == null){
            str += ", count of eyes undefined";
        }else{
            str += " count of eyes = " + eyesCount;
        }
        if(toothCount==null){
            str += ", count of teeth undefined";
        }else{
            str += ", count of teeth = " + toothCount;
        }
        return str;
    }
}
