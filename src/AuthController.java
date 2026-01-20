import com.formdev.flatlaf.FlatClientProperties;
import db.DBconnection;
import javax.swing.*;
import java.sql.*;

public class AuthController {

    public static void handleLogin(LoginForm f) {

        String username = f.txtUser.getText().trim();
        String password = new String(f.txtPass.getPassword()).trim();

        f.txtUser.putClientProperty(FlatClientProperties.OUTLINE, username.isEmpty() ? "error" : null);
        f.txtPass.putClientProperty(FlatClientProperties.OUTLINE, password.isEmpty() ? "error" : null);

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(f, "Please enter both username and password.");
            return;
        }

        String sql = "SELECT userId, username, fullname, role FROM register_tbl WHERE username=? AND password=?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("userId");
                String fullName = rs.getString("fullname");
                String role = rs.getString("role");
                String userName2 = rs.getString("username");
                JOptionPane.showMessageDialog(f, "System Login has Succeeded!", "Access Granted", JOptionPane.INFORMATION_MESSAGE);

                f.dispose();

                switch (role.toUpperCase()) {
                    case "OFFICER" -> new OfficerDash(fullName, userName2, id).setVisible(true);
                    case "ADMIN"   -> new AdminDash(fullName, userName2, id).setVisible(true);
                    case "BUYER"   -> new BuyerDash(fullName, userName2, id).setVisible(true);
                    default -> JOptionPane.showMessageDialog(null, "Unknown role");


                }


            } else {
                JOptionPane.showMessageDialog(f, "Invalid username or password","Access Denied",JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE
            );ex.printStackTrace();
        }
    }
}
