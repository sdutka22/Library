package pl.edu.wszib.jdbc;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.jdbc.model.User;
import pl.edu.wszib.jdbc.root.Auth;

public class App3 {
    public static void main(String[] args) {
        String hash = DigestUtils.md5Hex("1234" + Auth.getInstance().getSeed());

        System.out.println(hash);

        //User user = new User("admin", "asdlui43iu4hj5", User.Role.ADMIN);
    }
}
