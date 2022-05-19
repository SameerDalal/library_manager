import java.sql.SQLException;

public class addToDataBase  extends  SQLConnector{

    public String addDatabaseSQLString(int ID1, String val1, String val2, String val3, String table1) {
        return "INSERT INTO " + table1 + " " + "VALUES (" + ID1 + "," + "'" + val1 + "','" + val2 + "'," + val3 + ")";
    }

    public String deleteDatabaseSQLString(String table2, String rowName, String rowValue) {
        return "DELETE FROM " + table2 + " WHERE " + rowName + "='" + rowValue + "'";
    }

    public void addToDatabase(int ID, String val1, String val2, String val3, String table) {
        int counter = 0;
        try {
            statement.executeUpdate(addDatabaseSQLString(ID,val1, val2, val3, table));
        } catch (SQLException se) {
            System.out.println(se + "\n");
            counter++;
            System.out.println("Error Code 3");
        } finally {
            if (counter == 0) {
                System.out.println("Added " + ID + " " + val1 + " " + val2 + " " + val3 + " to the Database!\n");
            } else {
                System.out.println("Data: " + ID + " " + val1 + " " + val2 + " " + val3 + " has NOT been successfully added!\n");
            }
        }
    }

    @SuppressWarnings("unused")
    public void deleteFromDatabase(String table3, String rowName1, String rowValue1) {
        int counter1 = 0;
        try {
            statement.executeUpdate(deleteDatabaseSQLString(table3, rowName1, rowValue1));
        } catch (SQLException se) {
            System.out.println(se);
            counter1++;
            System.out.println("Error Code 4");
        } finally {
            if (counter1 == 0) {
                System.out.println("Deleted from the Database!\n");
            } else {
                System.out.println("Data has NOT been successfully deleted!\n");
            }
        }
    }
}
