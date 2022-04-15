public class main {

    public static void main(String[] args){
        MySQL ms = new MySQL();
        ms.SQLConnector();
        ms.addToDatabase(123,"Sam Dal","sam_dal@gmail.com","1233334567","users");

        //EmailSender em = new EmailSender();
        //em.sendEmail("sameerdalal747@gmail.com", "hello", "test");
    }



    //sql = "INSERT INTO users " + "VALUES (123,'Sam Dal','sam_dal@gmail.com',1233334567)";
}
