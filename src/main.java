import java.util.Scanner;

public class main {

    Scanner scan = new Scanner(System.in);
    SQLConnector ms = new SQLConnector();
    public int id_number = 0;


    public static void main(String[] args){
        main m = new main();
        m.loginToUserAccount();


        //EmailSender em = new EmailSender();
        //em.sendEmail("akhilgosala12@gmail.com")

    public void loginToUserAccount() {
        ms.SQLConnector();

        System.out.println("1). New User\n2). Existing User");

        switch (scan.nextInt()) {
            case (1):

                int id_value = 0;

                System.out.println("Enter your name: ");
                String name = scan.next();

                System.out.println("Enter your email address: ");
                String email_address = scan.next();

                String bookCheckedOut = "0";

                ms.addToDatabase(id_value, name, email_address, bookCheckedOut, "users");

                ms.getUserCreatedID();

                goTo();
                break;

            case (2):
                System.out.println("To log into your account type your id number: ");
                int idNumber = scan.nextInt();

                boolean IDFalse = true;
                while (IDFalse) {
                    if (!(ms.checkIfIDExists(idNumber))) {
                        System.out.println("The ID you entered is not in the database, try again.");
                        idNumber = scan.nextInt();
                    } else {
                        IDFalse = false;
                    }
                }
                goTo();
                break;
        }
    }

    public void goTo(){

    System.out.println("Options: \n1). Checkout a book\n2). Search for a book in the database\n3). Return a book");

        switch (scan.nextInt()){
            case (1):
                // checkout a book
                break;
            case (2):
                // search a book
                break;
            case (3):
                // return a book
                break;
        }

        ms.SQLDisconnector();
    }
}
