package databaseUtils;

import data.*;
import log.Log;


import java.sql.*;
import java.util.ArrayDeque;
import java.util.Map;

public class DBDragon {
    private final Connection connection;

    public DBDragon(Connection connection) {
        this.connection = connection;
    }

    public synchronized boolean addDragon(Dragon dragon, Map.Entry<String, String> user) {
        try {
            PreparedStatement addDragon = connection.prepareStatement(Statements.addDragon.getStatement(), Statement.RETURN_GENERATED_KEYS);
            PreparedStatement addCoordinates = connection.prepareStatement(Statements.addDragonCoordinates.getStatement());
            PreparedStatement addHead = connection.prepareStatement(Statements.addDragonHead.getStatement());


            addDragon.setString(1, dragon.getName());
            addDragon.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            addDragon.setInt(3, dragon.getAge());
            addDragon.setString(4, dragon.getDescription());
            if (dragon.getColor() == null) {
                addDragon.setString(5,null);
            } else {
                addDragon.setString(5, String.valueOf(dragon.getColor()));
            }
            if (dragon.getType() == null) {
                addDragon.setString(6,null);
            } else {
                addDragon.setString(6, String.valueOf(dragon.getType()));
            }
            addDragon.setString(7, user.getKey());
            addCoordinates.setLong(2, dragon.getCoordinates().getX());
            addCoordinates.setInt(3, dragon.getCoordinates().getY());

            addHead.setLong(2, dragon.getHead().getSize());
            addHead.setFloat(3, dragon.getHead().getEyesCount());
            addHead.setLong(4, dragon.getHead().getToothCount());

            int res1 = addDragon.executeUpdate();
            ResultSet resultSet = addDragon.getGeneratedKeys();
            if (resultSet.next()) {
                long dragonId = resultSet.getLong("id");  //как будто бы легче сделать по id с методом
                addCoordinates.setLong(1, dragonId);
                addHead.setLong(1, dragonId);
                int res = (addHead.executeUpdate() + addCoordinates.executeUpdate());
                return true;
            } else {
                connection.rollback();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized ArrayDeque<Dragon> getCollection() {
        try {
            PreparedStatement getCollection = connection.prepareStatement(Statements.getCollection.getStatement());
            ResultSet res = getCollection.executeQuery();
            ArrayDeque<Dragon> collection = resultSetToCollection(res);
            return collection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private synchronized Dragon rowToDragon(ResultSet row) {
        try {
            Long id = row.getLong("id");
            String name = row.getString("name");
            Date creationDate = row.getDate("creationDate");
            int age = row.getInt("age");
            String description = row.getString("description");
            Color color = (row.getString("color")!= null ?
                    Color.valueOf(row.getString("color")) : null);

            DragonType type = (row.getString("type")!= null ?
                    DragonType.valueOf(row.getString("type")) : null);
            //int creatorId = row.getInt("creatorId");
            String creatorName = row.getString("username");

            long coordX = row.getLong("coordX");
            int coordY = row.getInt("coordY");
            Coordinates coordinates = new Coordinates(coordX, coordY);

            Long size = row.getLong("size");
            float eyesCount = row.getFloat("eyesCount");
            Long toothCount = row.getLong("toothCount");
            DragonHead head = new DragonHead(size, eyesCount, toothCount);

            return new Dragon(id, name, coordinates, creationDate, age, description,
                    color, type, head, creatorName);
        } catch (SQLException e) {
            return null;
        }
    }

    public synchronized ArrayDeque<Dragon> resultSetToCollection(ResultSet resultSet) {
        ArrayDeque<Dragon> collection = new ArrayDeque<>();
        try {
            int errorCount = 0;
            while (resultSet.next()) {
                Dragon p = rowToDragon(resultSet);
                if (p == null) {
                    errorCount++;
                } else {
                    collection.add(p);
                }
            }
            if (errorCount != 0) {
                Log.logger.error("Couldn't read" + errorCount + " entries in the collection."); //че тут
            }
            return collection;
        } catch (SQLException e) {
            Log.logger.error(e.getMessage());
            return collection;
        }
    }

    public synchronized boolean updateById(Dragon dragon, long id, Map.Entry<String, String> user) {
        try {
            PreparedStatement updateDragon = connection.prepareStatement(Statements.updateByIdDragon.getStatement());
            PreparedStatement updateCoordinates = connection.prepareStatement(Statements.updateByIdCoordinates.getStatement());
            PreparedStatement updateHead = connection.prepareStatement(Statements.updateByIdHead.getStatement());
            updateDragon.setString(1, dragon.getName());
            updateDragon.setInt(2, dragon.getAge());
            updateDragon.setString(3, dragon.getDescription());
            updateDragon.setString(4, dragon.getColor() == null ? null : String.valueOf(dragon.getColor()));
            updateDragon.setString(5, dragon.getType() == null ? null : String.valueOf(dragon.getType()));
            updateDragon.setString(6, user.getKey());
            updateDragon.setLong(7, id);

            int res = updateDragon.executeUpdate();
            if (res == 1) {
                updateCoordinates.setLong(1, dragon.getCoordinates().getX());
                updateCoordinates.setInt(2, dragon.getCoordinates().getY());
                updateCoordinates.setLong(3, id);
                updateHead.setLong(1, dragon.getHead().getSize());
                updateHead.setFloat(2, dragon.getHead().getEyesCount());
                updateHead.setLong(3, dragon.getHead().getToothCount());
                updateHead.setLong(4, id);
                res += updateCoordinates.executeUpdate();
                res += updateHead.executeUpdate();
            } else {
                connection.rollback();
            }
            return res > 0;
        } catch (SQLException e) {
            Log.logger.error("SQL problem with removing by id");
            e.printStackTrace();
            return false;
        }
    }

    public synchronized int removeDragon(Long id, Map.Entry<String, String> user) {
        try {

            PreparedStatement removePerson = connection.prepareStatement(Statements.removeById.getStatement());
            removePerson.setString(1, user.getKey());
            removePerson.setLong(2, id);
            int res = removePerson.executeUpdate();
            return res;
        } catch (SQLException e) {
            Log.logger.error(e.getMessage());
            return -1;
        }
    }

    public synchronized boolean clearCollection(Map.Entry<String, String> user) {
        try {
            PreparedStatement clear = connection.prepareStatement(Statements.clearCollection.getStatement());
            clear.setString(1, user.getKey());
            int res = clear.executeUpdate();
            if (res > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            Log.logger.error(e.getMessage());
            return false;
        }
    }
}
