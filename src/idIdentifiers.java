import java.sql.ResultSet;
import java.sql.SQLException;

public class idIdentifiers extends  SQLConnector{

    public int getUserCreatedID() {
        int userCreatedID = 0;
        String username = "";
        String userEmailAddress = "";

        int numberOfRows = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM users ");
            while (rs.next()){
                numberOfRows++;
            }
            rs.absolute(numberOfRows);
            userCreatedID = rs.getInt("id");
        } catch (SQLException se){
            System.out.println(se);
            System.out.println("Error Code 5");
        }
        System.out.println("Your unique ID: " + userCreatedID + "\n");
        return userCreatedID;
    }

    public boolean checkIfIDExists(int idInputted){
        boolean idExists = false;
        try {
            ResultSet rs = statement.executeQuery("SELECT id FROM users ");
            while(rs.next()){
                if (rs.getInt(1) == idInputted){
                    idExists = true;
                }
            }
        } catch (SQLException se){
            System.out.println(se);
            System.out.println("Error Code 6");
        }
        return idExists;
    }
}
