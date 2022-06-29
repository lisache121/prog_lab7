package utils;

import data.*;
import exceptions.*;
import exceptions.IncorrectInputInScriptException;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * class for making new elements of collection
 */
public class DragonMaker {
    private final Dragon dragon= new Dragon();
    private Scanner scanner;
    private final Coordinates coordinates = new Coordinates();
    private final DragonHead head = new DragonHead();
    private Scanner userScanner;
    private boolean fileMode;

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public DragonMaker(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * Sets a scanner to scan user input.
     * @param userScanner Scanner to set.
     */


    /**
     * @return Scanner, which uses for user input.
     */
    public Scanner getScanner() {
        return userScanner;
    }

    /**
     * Sets marine asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets marine asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }

    public DragonMaker() {
    }

    public Dragon getDragon() {
        return dragon;
    }

    /**
     *
     * @return String with new name
     */
    public String makeName() throws IncorrectInputInScriptException {
        String name;
        boolean repeat = true;
        while(repeat) {
            try{
                if (!fileMode){
                    System.out.println("Enter dragon's name. Name value must be string.");
                }
                name = scanner.nextLine().trim();
                if (name.isEmpty()) throw new EmptyFieldException();
                dragon.setName(name);
                repeat = false;
            } catch (EmptyFieldException e) {
                System.out.println("Name value cannot be empty. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (InputMismatchException e){
                System.out.println("Name value must be string. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();
            }
        }
        return dragon.getName();
    }

    /**
     *
     * @return new x coordinate
     */
    public long makeX() throws IncorrectInputInScriptException {
        String xStr;
        long x;
        boolean repeat = true;
        while (repeat) {
            try{
                if (!fileMode){
                    System.out.println("Enter x coordinate. X value must be a number, greater than -417");
                }
                xStr = scanner.nextLine().trim();
                if (xStr.isEmpty()) throw new EmptyFieldException();
                x = Long.parseLong(xStr);
                if (x <= -417) throw new InvalidArgumentsOfCoordinatesException();
                coordinates.setX(x);
                repeat = false;
            }catch (EmptyFieldException e) {
                System.out.println("X coordinate must not be empty.");
                if (fileMode) throw new IncorrectInputInScriptException();

            } catch (InvalidArgumentsOfCoordinatesException e) {
                System.out.println("X value must be greater than -417. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            } catch (NumberFormatException e){
                System.out.println("X coordinate must be a number. Type long. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            }
        }
        return coordinates.getX();
    }

    /**
     *
     * @return new y coordinate
     */
    public int makeY() throws IncorrectInputInScriptException {
        String yStr;
        int y;
        boolean repeat = true;
        while (repeat) {
            try{
                if (!fileMode){
                    System.out.println("Enter Y coordinate. Y value must be integer, greater than -225");
                }
                yStr = scanner.nextLine().trim();
                if (yStr.isEmpty()) throw new EmptyFieldException();
                y = Integer.parseInt(yStr);
                if (y <= -225) throw new InvalidArgumentsOfCoordinatesException();
                coordinates.setY(y);
                repeat = false;
            } catch (InvalidArgumentsOfCoordinatesException e) {
                System.out.println("Y value must be greater than -225. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (EmptyFieldException e) {
                System.out.println("Y coordinate cannot be empty");
                if (fileMode) throw new IncorrectInputInScriptException();

            }catch (NumberFormatException e){
                System.out.println("Y coordinate must be integer. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            }
        }
        return coordinates.getY();
    }

    /**
     *
     * @return new instance of Coordinates
     * @throws InvalidArgumentsOfCoordinatesException if x or y are incorrect
     */
    public Coordinates makeCoordinates() throws InvalidArgumentsOfCoordinatesException, IncorrectInputInScriptException {
        long x;
        int y;
        x = makeX();
        y = makeY();
        return new Coordinates(x, y);
    }

    /**
     *
     * @return new age of element
     */
    public int makeAge() throws IncorrectInputInScriptException {
        String ageStr;
        int age;
        boolean repeat = true;
        while (repeat) {
            try {
                if (!fileMode){
                    System.out.println("Enter dragon's age. Age value must be greater than 0.");
                }

                ageStr = scanner.nextLine().trim();
                if (ageStr.isEmpty()) throw new EmptyFieldException();
                age = Integer.parseInt(ageStr);
                if (age <=0) throw new InvalidAgeInputException();
                dragon.setAge(age);
                repeat = false;
            } catch (NumberFormatException e) {
                System.out.println("Age value must be integer. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            } catch (EmptyFieldException e) {
                System.out.println("Age cannot be empty");
                if (fileMode) throw new IncorrectInputInScriptException();

            } catch (InvalidAgeInputException e) {
                System.out.println("Age must be greater than 0.");
                if (fileMode) throw new IncorrectInputInScriptException();

            }
        }
        return dragon.getAge();
    }

    /**
     *
     * @return String with description of element
     */
    public String makeDescription() throws IncorrectInputInScriptException {
        String description;
        boolean repeat = true;
        while (repeat) {
            try {
                if (!fileMode){
                    System.out.println("Enter description of the dragon. It must not be empty");
                }
                description = scanner.nextLine().trim();
                if (description.isEmpty()) throw new EmptyFieldException();
                dragon.setDescription(description);
                repeat = false;
            } catch (EmptyFieldException e) {
                System.out.println("Description value must not be empty. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            } catch (InputMismatchException e){
                System.out.println("Description value must be string. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            }catch (NullPointerException e){
                System.out.println("Description cannot be null");
                if (fileMode) throw new IncorrectInputInScriptException();

            }
        }
        return dragon.getDescription();
    }

    /**
     *
     * @return color of element
     */
    public Color makeColor() throws IncorrectInputInScriptException {
        Color color;
        String colorStr;
        boolean repeat = true;
        while (repeat){
            try{
                if (!fileMode){
                    System.out.println("Enter the color of the dragon from list: " + Color.nameList());
                }
                colorStr = scanner.nextLine();
                if (colorStr.isEmpty() || colorStr.equals(" ")) {
                    dragon.setColor(null);
                    return dragon.getColor();
                }
                colorStr = colorStr.trim();
                color = Color.valueOf(colorStr.toUpperCase());
                dragon.setColor(color);
                repeat = false;
            } catch (IllegalArgumentException e) {
                System.out.println("Color must be from list of colors or null. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            }
        }
        return dragon.getColor();
    }

    /**
     *
     * @return new type of element
     */
    public DragonType makeType() throws IncorrectInputInScriptException {
        DragonType type;
        String typeStr;
        boolean repeat = true;
        while (repeat) {
            try{
                if (!fileMode){
                    System.out.println("Enter type of the dragon from list: " + DragonType.nameList());
                }
                typeStr = scanner.nextLine();
                if (typeStr.isEmpty() || typeStr.equals(" ")){
                    dragon.setType(null);
                    return dragon.getType();
                }
                typeStr = typeStr.trim();
                type = DragonType.valueOf(typeStr.toUpperCase());
                dragon.setType(type);

                repeat = false;
            } catch (IllegalArgumentException e) {
                System.out.println("Type must be from type list. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            }
        }
        return dragon.getType();
    }

    /**
     *
     * @return new size of head
     */
    public Long makeSize() throws IncorrectInputInScriptException {
        long size;
        String sizeStr;
        boolean repeat = true;
        while (repeat) {
            try{
                if (!fileMode){
                    System.out.println("Enter size of a dragon. It must be a number (long).");
                }
                sizeStr = scanner.nextLine().trim();
                if (sizeStr.isEmpty() || sizeStr.equals(" ")){
                    head.setSize(null);
                    return head.getSize();
                }
                sizeStr = sizeStr.trim();
                size = Long.parseLong(sizeStr);
                head.setSize(size);

                repeat = false;
            } catch (NumberFormatException e) {
                System.out.println("Size must be a number (long). Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            }
        }
        return head.getSize();
    }

    /**
     *
     * @return new eyes count
     */
    public Float makeEyesCount() throws IncorrectInputInScriptException {
        float eyesCount;
        String countStr;
        boolean repeat = true;
        while (repeat) {
            try{
                if (!fileMode){
                    System.out.println("Enter count of eyes of a dragon. It must be a number (float).");
                }
                countStr = scanner.nextLine().trim();
                if (countStr.isEmpty()) throw new EmptyFieldException();
                eyesCount = Float.parseFloat(countStr);
                head.setEyesCount(eyesCount);
                repeat = false;
            } catch (NumberFormatException e) {
                System.out.println("Size must be a number (float). Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            } catch (EmptyFieldException e){
                System.out.println("Eyescount value must not be empty. Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();
            }
        }
        return head.getEyesCount();
    }

    /**
     *
     * @return new tooth count
     */
    public Long makeToothCount() throws IncorrectInputInScriptException {
        long toothCount;
        String countStr;
        boolean repeat = true;
        while (repeat) {
            try{
                if (!fileMode){
                    System.out.println("Enter count of tooth of a dragon. It must be a number (long).");
                }
                countStr = scanner.nextLine();
                if (countStr.isEmpty() || countStr.equals(" ")){
                    head.setToothCount(null);
                    return head.getToothCount();

                }
                countStr = countStr.trim();
                toothCount = Long.parseLong(countStr);
                head.setToothCount(toothCount);
                repeat = false;
            } catch (NumberFormatException e) {
                System.out.println("Tooth count must be a number (long). Try again.");
                if (fileMode) throw new IncorrectInputInScriptException();

            }
        }
        return head.getToothCount();
    }

    /**
     * return new DragonHead
     * @return
     */
    public DragonHead makeHead() throws IncorrectInputInScriptException {
        return new DragonHead(makeSize(), makeEyesCount(), makeToothCount());
    }

    /**
     * method for asking user to enter fields
     * @param question String with question
     * @return user's answer
     */
    public boolean makeQuestion(String question) {
        String ans;
        while (true){
            try {

                System.out.println(question);
                ans = scanner.nextLine().trim();
                if (!ans.equals("YES") && !ans.equals("NO")) throw new WrongInputException();
                break;
            } catch (WrongInputException e) {
                System.out.println("Answer must be 'YES' or 'NO'");
            }
        }
        return ans.equals("YES");
    }

    public Dragon makeDragon() throws IncorrectInputInScriptException{
        try{
            return new Dragon(
                    makeName(),
                    makeCoordinates(),
                    new Date(), makeAge(), makeDescription(), makeColor(), makeType(), makeHead());
        } catch (InvalidArgumentsOfCoordinatesException e) {
            e.printStackTrace();
        } catch(IncorrectInputInScriptException e){
            System.out.println("incorrect input in script. execute_script command will be stopped");
            return null;
        }
        return null;
    }


}
