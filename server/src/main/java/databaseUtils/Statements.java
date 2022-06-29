package databaseUtils;

public enum Statements {

    createDragonTable("CREATE TABLE IF NOT EXISTS collection (" +
            "id serial8 PRIMARY KEY , " +
            "name varchar(70) NOT NULL CHECK (name<>'')," +
            "creationDate date DEFAULT (current_date)," +
            "age int NOT NULL CHECK (age > 0)," +
            "description varchar(100) NOT NULL," +
            "color varchar(6), " +
            "CHECK (color='GREEN' OR " +
            "color='BLACK' OR " +
            "color='YELLOW' OR " +
            "color='WHITE' OR " +
            "color IS NULL)," +
            "type varchar(11), " +
            "CHECK (type='WATER' OR " +
            "type='UNDERGROUND' OR " +
            "type='AIR' OR " +
            "type='FIRE' OR " +
            "type IS NULL)," +
            "creatorName VARCHAR REFERENCES users (username));"),

    createCoordinateTable("CREATE TABLE IF NOT EXISTS coordinates (" +
            "dragonId BIGINT PRIMARY KEY REFERENCES collection (id) ON delete cascade," +
            "coordX bigint NOT NULL CHECK (coordX > -417)," +
            "coordY int NOT NULL CHECK (coordY > -225));"),

    createHeadTable("CREATE TABLE IF NOT EXISTS head (" +
            "dragonId BIGINT PRIMARY KEY REFERENCES collection (id) ON delete cascade," +
            "size bigint NOT NULL, " +
            "eyesCount DECIMAL," +
            "toothCount bigint NOT NULL);"),

    createUsersTable("CREATE TABLE IF NOT EXISTS users (" +
            "username varchar(70) PRIMARY KEY NOT NULL," +
            "password varchar(255) NOT NULL);"),

    addDragon("INSERT INTO collection (name, creationDate, age, description," +
            "color, type, creatorName) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id"),

    addDragonCoordinates("INSERT INTO coordinates (dragonId, coordX, coordY) VALUES (?, ?, ?);"),

    addDragonHead("INSERT INTO head (dragonId, size, eyesCount, toothCount) VALUES (?, ?, ?, ?);"),

    clearCollection("DELETE FROM collection WHERE creatorName = ?"),

    removeById("DELETE FROM collection WHERE creatorName = ? AND id = ?"),

    updateByIdDragon("UPDATE collection " +
            "SET name = ?, age = ?, description = ?, color = ?, type = ?" +
            "WHERE creatorName = ? AND id = ?"),

    updateByIdCoordinates("UPDATE coordinates " +
            "SET coordX = ?, coordY = ?" +
            "WHERE dragonId = ?"),

    updateByIdHead("UPDATE head " +
            "SET size = ?, eyesCount = ?, toothCount = ?" +
            "WHERE dragonId = ?"),

    allForUser("SELECT * FROM collection WHERE creatorId=?;"),

    getAll("SELECT * FROM collection;"),

    getCollection("SELECT * FROM collection " + "JOIN coordinates ON coordinates.dragonId = collection.id " +
            "JOIN head ON head.dragonId = collection.id " +
            "JOIN users ON users.username = collection.creatorName"),

    checkUserInData("SELECT * FROM users WHERE (username=? AND password=?);"),

    checkUsernameInData("SELECT * FROM users WHERE username = ?;"),
    addUserToData("INSERT INTO users (username, password) VALUES(?, ?)"),

    deleteAll("DROP TABLE collection CASCADE"),
    deleteUsersTable("DROP TABLE users CASCADE"),
    deleteHeadTable("DROP TABLE head CASCADE"),
    deleteCoordTable("DROP TABLE coordinates CASCADE");

    private final String statement;
    Statements(String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }
}
