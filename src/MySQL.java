
import java.sql.*;
import java.sql.DriverManager;


public class MySQL {

    Connection connection = null;
    Statement statement = null;
    String sql = "";
    int counter = 0;

    public void SQLConnector(){

        // registering JDBC Driver

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to Database...\n");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_database","root","");
            System.out.println("Connected to Database Successfully!\n");
            statement = connection.createStatement();
        } catch (Exception e){
            System.out.println(e + "\n");
            counter++;
        }
    }

    public String createSQLString(int ID1, String name1, String email_address1, String phone_number1, String table1){
        return "INSERT INTO " + table1 + " " + "VALUES (" + ID1 + "," + "'" + name1 + "','" + email_address1 + "'," + phone_number1 + ")";

    }

    public void addToDatabase(int ID, String name, String email_address, String phone_number, String table){

        try {
            sql = createSQLString(ID,name,email_address,phone_number,table);
            statement.executeUpdate(sql);
        } catch (SQLException se){
            System.out.println(se + "\n");
            System.out.println("A duplicate entry has been added, please try again!\n");
            counter++;
        } finally {
            if (counter == 0) {
                System.out.println("Added " + sqlSplitter(sql) + " to the Database!\n");
            } else {
                System.out.println("Data: "  + sqlSplitter(sql) +  " has NOT been successfully added!\n");
            }
            try {
                connection.close();
            } catch (SQLException se){
                System.out.println(se + "\n");
            }
        }
    }

    public static String sqlSplitter(String sql2){
        String[] sqlSplit = sql2.split(",", 2);
        return sqlSplit[1];

    }
}
