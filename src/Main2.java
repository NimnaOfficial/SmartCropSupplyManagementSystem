import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import java.awt.*;

public class Main2 {

    public static void main(String[] args) {

        try {
            FlatDarkLaf.setup();
            UIManager.put("Button.arc", 20);
            UIManager.put("Component.focusWidth", 1);
        } catch (Exception e) {
            System.err.println("FlatLaf init failed");
        }

        SwingUtilities.invokeLater(Main2::showWelcome);
    }

    // ---------- WELCOME ----------
    private static void showWelcome() {
        OpenForm open = new OpenForm();

        open.btnBegin.addActionListener(e -> switchFrame(open, Main2::showLogin));
        open.btnContact.addActionListener(e -> new Contact(open).setVisible(true));

        open.setVisible(true);
    }

    // ---------- LOGIN ----------
    private static void showLogin(Point pos) {
        LoginForm login = new LoginForm();
        login.setLocation(pos);

        login.btnLogin.addActionListener(e -> handleLogin(login));
        login.btnRegister.addActionListener(e -> switchFrame(login, Main2::showRegister));

        login.setVisible(true);
    }

    // ---------- LOGIN LOGIC ----------
    private static void handleLogin(LoginForm f) {
        String user = f.txtUser.getText().trim();
        String pass = new String(f.txtPass.getPassword()).trim();

        f.txtUser.putClientProperty(FlatClientProperties.OUTLINE, user.isEmpty() ? "error" : null);
        f.txtPass.putClientProperty(FlatClientProperties.OUTLINE, pass.isEmpty() ? "error" : null);

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(f, "Please fill all fields", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("Login attempt: " + user);
    }

    // ---------- REGISTER ----------
    private static void showRegister(Point pos) {
        RegisterForm reg = new RegisterForm();
        reg.setLocation(pos);

        reg.btnSubmit.addActionListener(e -> reg.btnSubmitActionPerformed(e));
        reg.btnBack.addActionListener(e -> switchFrame(reg, Main2::showLogin));

        reg.setVisible(true);
    }

    // ---------- COMMON FRAME SWITCH ----------
    private static void switchFrame(JFrame current, java.util.function.Consumer<Point> next) {
        Point p = current.getLocation();
        current.dispose();
        next.accept(p);
    }
}
