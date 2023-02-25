package pl.edu.wszib.library.root;

import org.junit.jupiter.api.*;
import pl.edu.wszib.library.model.User;

public class AuthTest {
    private final Auth auth = Auth.getInstance();

    @BeforeEach
    public void prepare(){
        System.out.println("Przygotowanie do testu");
    }

    @AfterEach
    public void clean(){
        System.out.println("czyszczenie po teście");
    }

    @BeforeAll
    public static void prepareAll(){
        System.out.println("Przygotowanie przed wszystkimi testami ");
    }

    @AfterAll
    public static void clearAfterAll(){
        System.out.println("Czyszczenie po wszystkich testach");
    }

    @Test
    public void testAuthenticationWithMatchingCredentials(){
        String login = "admin";
        String password = "admin";

        User userFromDB = new User(login, password);
        auth.authentication(userFromDB);

        Assertions.assertTrue(auth.getLoggedUser() != null);
    }

    @Test
    public void testAuthenticationWithNonMatchingCredentials(){
        String login = "adam";
        String password = "1234";

        User userFromDB = new User(login, password);
        auth.authentication(userFromDB);

        Assertions.assertFalse(auth.getLoggedUser() != null);
    }

    @Test
    public void testAuthentication(){
        String login = "janusz123";
        String password = "janusz";

        User userFromDB = new User(login, password);
        auth.authentication(userFromDB);

        Assertions.assertEquals(login, this.auth.getLoggedUser().getLogin());
    }

    @Test
    public void testValidateWithMatchingCredentials(){
        String login = "sdutka";
        String password = "1234";

        User userFromDB = new User(login, password);

        Assertions.assertTrue(auth.validate(userFromDB));
    }


    @Test
    public void testValidateWithNonMatchingCredentials() {
        String login = "kuba";
        String password = "";

        User userFromDB = new User(login, password);

        Assertions.assertFalse(auth.validate(userFromDB));
    }
}
