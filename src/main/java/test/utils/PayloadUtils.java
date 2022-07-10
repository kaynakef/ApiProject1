package test.utils;

import java.util.Random;

public class PayloadUtils {

    public static String getCreateUserPayload(String name, String gender, String email, String status) {
        return "{\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"gender\": \""+gender+"\",\n" +
                "    \"email\": \""+ email+"\",\n" +
                "    \"status\": \""+status+"\"\n" +
                "}";
    }

    //here is a create email method that we can use,
    // because every time we need a unique email to be able to successfully create a user.

    public static String createEmail(){
        Random random= new Random();
        String email= "";
        String alphabet= "qwertyuiopasdfghjklzxcvbnm";
        for (int i = 0; i < 16; i++) {
            int numb= random.nextInt(2);
            if(numb==0){
                int randomIndex= random.nextInt(26);
                email+= alphabet.charAt(randomIndex);
            }else{
                alphabet+=random.nextInt(10);
            }

        }
        email+="@gmail.com";
        return email;
    }


}
