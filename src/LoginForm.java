import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Statement;
import db.DBconnection;
import java.sql.ResultSet;

public class LoginForm extends JFrame
{
    // Components made accessible for the Main class
    public JTextField txtUser;
    public JPasswordField txtPass;
    public JButton btnLogin;
    public JButton btnRegister;

    public LoginForm() {
        initUI();
    }
        private void initUI() {
            setTitle("App Login");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(450, 600);
            setLocationRelativeTo(null);

            // Main Background Panel
            JPanel mainPanel = new JPanel(new MigLayout("fill, insets 20", "[center]", "[center]"));
            mainPanel.setBackground(new Color(30, 30, 30));

            // The Login Card
            JPanel card = new JPanel(new MigLayout("wrap, fillx, insets 35 45 35 45", "[fill]"));
            card.putClientProperty(FlatClientProperties.STYLE, "arc: 40; background: #252525");

            // Header
            JLabel title = new JLabel("Welcome Back");
            title.putClientProperty(FlatClientProperties.STYLE, "font: bold +12; foreground: #FFFFFF");

            // Username Field
            txtUser = new JTextField();
            txtUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Username");
            // FIXED: Removed the brackets from margin
            txtUser.putClientProperty(FlatClientProperties.STYLE, "arc: 20; margin: 5,10,5,10");

            // Password Field
            txtPass = new JPasswordField();
            txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
            // FIXED: Removed the brackets from margin
            txtPass.putClientProperty(FlatClientProperties.STYLE, "arc: 20; margin: 5,10,5,10");

            // Green Login Button
            btnLogin = new JButton("Login");
            btnLogin.putClientProperty(FlatClientProperties.STYLE,
                    "background: #4CAF50; foreground: #FFFFFF; arc: 20; font: bold; borderWidth: 0; focusWidth: 0");
            btnLogin.addActionListener(e -> btnLoginActionPerformed());


            // Register Link
            btnRegister = new JButton("Create an account");
            btnRegister.setForeground(new Color(76, 175, 80));
            btnRegister.setBorderPainted(false);
            btnRegister.setContentAreaFilled(false);
            btnRegister.setFocusPainted(false);
            btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Labels with color set correctly
            JLabel lblUser = new JLabel("Username");
            lblUser.setForeground(new Color(187, 187, 187)); // This is #BBBBBB

            JLabel lblPass = new JLabel("Password");
            lblPass.setForeground(new Color(187, 187, 187));

            // Assemble components
            card.add(title, "center, gapbottom 30");
            card.add(lblUser, "gaptop 10"); // FIXED: Removed 'foreground' from constraint
            card.add(txtUser, "h 40!");
            card.add(lblPass, "gaptop 10"); // FIXED: Removed 'foreground' from constraint
            card.add(txtPass, "h 40!");
            card.add(btnLogin, "h 45!, gaptop 30");
            card.add(btnRegister, "center, gaptop 10");

            mainPanel.add(card, "width 350!");
            add(mainPanel);
        }

    private void btnLoginActionPerformed() {

    }

}
