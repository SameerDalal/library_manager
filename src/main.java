import java.util.Scanner;

public class main {

    Scanner scan = new Scanner(System.in);
    MySQL ms = new MySQL();


    public static void main(String[] args){
        main m = new main();
        m.loginToUserAccount();

        //m.loginToUserAccount();

        // int ID, String name, String email_address, String phone_number, String user
        // int id, String author, String name, String stock, String books
        //ms.deleteFromDatabase("users","Name","Sam Dal");


        //EmailSender em = new EmailSender();
        //em.sendEmail("sameerdalal747@gmail.com", "hello", "test");
    }

    public void loginToUserAccount(){
        ms.SQLConnector();

        System.out.println("1). New User\n2). Existing User");

        switch (scan.nextInt()){
            case (1):

                int id_value = (ms.getUserAccountData()) + 1;
                System.out.println(id_value);

                System.out.println("Enter your name: ");
                String name = scan.next();

                System.out.println("Enter your email address: ");
                String email_address = scan.next();

                System.out.println("Enter your phone number: ");
                String phone_number = scan.next();

                ms.addToDatabase(id_value,name,email_address,phone_number,"users");

                ms.SQLDisconnector();
                break;
            case (2):
                System.out.println("To log into your account type your id number: ");
                int id_number = scan.nextInt();
                break;
        }


    }




}
