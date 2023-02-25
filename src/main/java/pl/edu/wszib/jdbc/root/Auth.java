package pl.edu.wszib.jdbc.root;
import pl.edu.wszib.jdbc.model.User;
import pl.edu.wszib.jdbc.database.UserDAO;
import org.apache.commons.codec.digest.DigestUtils;

public class Auth {
    final UserDAO userDAO = UserDAO.getInstance();
    private User loggedUser = null;
    private final String seed = "OK4wkjJ15XD@T*41pO9M21t^rLhlt#&9srznHWyo";
    private static final Auth instance = new Auth();

    private Auth() {

    }
    public void authentication(User user) {
        User userFromDB = this.userDAO.findByLogin(user.getLogin());
        if(userFromDB != null && userFromDB.getPassword().equals(DigestUtils.md5Hex(user.getPassword() + this.seed))) {
            loggedUser = userFromDB;
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void logOut(){
        loggedUser = null;
    }

    public static boolean validate(User user) {
        if ((user.getPassword() == null || user.getLogin() == null) || (user.getPassword().length() < 4 || user.getLogin().length() < 4)){
            return false;
        }
        return true;
    }

    public static boolean doesExist(User user, UserDAO userDAO){
        User existingUser = userDAO.findByLogin(user.getLogin());

        if(existingUser != null){
            return true; // zwracamy true poniewaz obiekt nie jest nullem czyl istnieje w naszej bazie danych
        }else{
            return false; //zwracamy false poniewaz obiekt jest nullem co oznacza ze nie istnieje w naszym db
        }
    }

    public String getSeed() {
        return seed;
    }

    public static Auth getInstance() {
        return instance;
    }

}