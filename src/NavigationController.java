import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class NavigationController {

    public static void showWelcome(Point pos) {
        OpenForm open = new OpenForm();
        open.btnBegin.addActionListener(e -> switchFrame(open, NavigationController::showLogin));
        open.btnContact.addActionListener(e -> new Contact(open).setVisible(true));
        open.setVisible(true);
    }

    public static void showLogin(Point pos) {
        LoginForm login = new LoginForm();
        login.setLocation(pos);

        //login.btnLogin.addActionListener(e -> AuthController.handleLogin(login));
        login.btnRegister.addActionListener(e -> switchFrame(login, NavigationController::showRegister));

        login.setVisible(true);
    }

    public static void showRegister(Point pos) {
        RegisterForm reg = new RegisterForm();
        reg.setLocation(pos);

        reg.btnSubmit.addActionListener(e -> reg.btnSubmitActionPerformed());
        reg.btnBack.addActionListener(e -> switchFrame(reg, NavigationController::showLogin));

        reg.setVisible(true);
    }

    private static void switchFrame(JFrame current, Consumer<Point> next) {
        Point p = current.getLocation();
        current.dispose();
        next.accept(p);
    }
}
