package ro.paha.serialtools.repository;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;


public class Database implements Repository {

    Connection dbConnection;

    @Override
    public boolean open() {
        try {
            Properties props = readConfigFile();
            String driver = props.getProperty("jdbc.driver");
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            Class.forName(driver);
            dbConnection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void write(String message) {
        String insertTableSQL = "INSERT INTO logs"
                + "(message) VALUES "
                + "(?)";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, message);
            preparedStatement.executeUpdate();
            System.out.println("Record is inserted into database! " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean close() {
        try {
            dbConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private Properties readConfigFile() throws Exception {
        Properties props = new Properties();
        InputStream in = getClass()
                .getClassLoader().getResourceAsStream("db.properties");
        if (in == null) {
            throw new Exception("Sorry, unable to find db.properties");
        }
        props.load(in);
        in.close();

        return props;
    }
}
