package data;


import exceptions.EmptyFieldException;
import exceptions.InvalidAgeInputException;
import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Objects;

/**
 * class with main type of element in collection
 */
public class Dragon implements Serializable, Comparable<Dragon> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null

    private Date date = new Date(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0
    private String description; //Поле не может быть null
    private Color color; //Поле может быть null
    private DragonType type; //Поле может быть null
    private DragonHead head;
    private int creatorId;
    private String creatorName;
    public Dragon() {
    }

    public Dragon(Long id,  String name, Coordinates coordinates, Date date, int age, String description, Color color, DragonType type,  DragonHead head, String creatorName) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.date = date;
        this.age = age;
        this.description = description;
        this.color = color;
        this.type = type;
        this.head = head;

        this.creatorName = creatorName;
    }

    public Dragon(String name, Coordinates coordinates, Date date, int age, String description, Color color, DragonType type,  DragonHead head) {
        this.name = name;
        this.coordinates = coordinates;
        this.date = date;
        this.age = age;
        this.description = description;
        this.color = color;
        this.type = type;
        this.head = head;
    }

    /**
     *
     * @return id of Dragon
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return name of Dragon
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return coordinates of the Dragon
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @return age of the dragon
     */
    public int getAge() {
        return age;
    }

    /**
     * @return description of the dragon
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return color of the dragon
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @return type of the dragon
     */
    public DragonType getType() {
        return type;
    }

    /**
     *
     * @return head of the dragon
     */
    public DragonHead getHead() {
        return head;
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + date +
                ", age=" + age +
                ", description='" + description + '\'' +
                ", color=" + color +
                ", type=" + type +
                ", size=" + head.getSize() +
                ", eyesCount=" + head.getEyesCount() +
                ", toothCount=" + head.getToothCount() +
                '}';
    }

    /**
     *
     * @return String with all fields
     */
    public String toConsole(){
        String str;
        str = "id = " + id + ", name = " + name + ", coordinates: " + coordinates.toConsole() + ", creationDate=" + date +
                ", age=" + age +
                ", description = '" + description +  "'";
        if (color==null){
            str += ", color is undefined";
            }
        else {
            str += ", color = " + color;
        }
        if(type==null){
            str += ", type is undefined";
        } else{
            str += ", type = " + type;
        }
        str += head.toConsole();
        str += ", creatorName = " + creatorName;
        return str;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragon = (Dragon) o;
        return age == dragon.age && Objects.equals(id, dragon.id) && Objects.equals(name, dragon.name) && Objects.equals(coordinates, dragon.coordinates) && Objects.equals(date, dragon.date) && Objects.equals(description, dragon.description) && color == dragon.color && type == dragon.type && Objects.equals(head, dragon.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, date, age, description, color, type, head);
    }

    public void setName(String name) throws EmptyFieldException {
        if (!name.equals("") & !name.isEmpty()){
            this.name = name;
        }
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }



    public void setAge(int age) throws InvalidAgeInputException {
        if (age>0){
            this.age = age;
        }


    }

    public void setDescription(String description) throws EmptyFieldException {
        if (!description.equals("") && !description.isEmpty()){
            this.description = description;
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setType(DragonType type) {
        this.type = type;
    }

    public void setHead(DragonHead head) {
        this.head = head;
    }
    public String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return dateFormat.format(date);
    }
    public Date retDate(){
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public int compareTo(Dragon dragon){
        return Integer.compare(age, dragon.getAge());
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
