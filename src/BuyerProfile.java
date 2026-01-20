import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DBconnection;

public class BuyerProfile extends JPanel {

    private JTextField txtusername, txtPassword, txtContactNo, txtEmail;
    private JButton btnUpdateProfile;
    private int currentUserId;

    public BuyerProfile(int usid) {
        this.currentUserId = usid;
        setLayout(new BorderLayout());
        setOpaque(false);
        initUI();
        loadUserData();
    }

    private void initUI() {
        JPanel managerPanel = new JPanel(new MigLayout("fill, insets 0", "[400!]25[grow, fill]", "[fill]"));
        managerPanel.setOpaque(false);

        // --- LEFT COLUMN: UPDATE FORM ---
        JPanel formPanel = new JPanel(new MigLayout("wrap, fillx, insets 30", "[fill]"));
        formPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #2a2a2a");

        JLabel title = new JLabel("Account Settings");
        title.putClientProperty(FlatClientProperties.STYLE, "font: bold +5; foreground: #FFFFFF");
        formPanel.add(title, "gapbottom 20");

        // Input Fields
        txtusername = new JTextField();
        txtPassword = new JTextField();
        txtContactNo = new JTextField();
        txtEmail = new JTextField();

        // Styling Fields
        applyFieldStyle(txtusername, "username");
        applyFieldStyle(txtPassword, "password");
        applyFieldStyle(txtContactNo, "ContactNo");
        applyFieldStyle(txtEmail, "email");

        formPanel.add(new JLabel("User Name"));
        formPanel.add(txtusername, "h 40!");
        formPanel.add(new JLabel("Password"), "gaptop 10");
        formPanel.add(txtPassword, "h 40!");
        formPanel.add(new JLabel("Contact Number"), "gaptop 10");
        formPanel.add(txtContactNo, "h 40!");
        formPanel.add(new JLabel("Email Address"), "gaptop 10");
        formPanel.add(txtEmail, "h 40!");

        btnUpdateProfile = new JButton("Save Changes");
        btnUpdateProfile.putClientProperty(FlatClientProperties.STYLE, "background: #2ecc71; foreground: #FFFFFF; arc: 15; font: bold");

        formPanel.add(btnUpdateProfile, "gaptop 30, h 45!");

        // --- RIGHT COLUMN: PROFILE CARD (Visual) ---
        JPanel infoPanel = new JPanel(new MigLayout("wrap, center, insets 50", "[center]"));
        infoPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 30; background: #1e1e1e");

        JLabel avatar = new JLabel("👤");
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));

        JLabel infoTitle = new JLabel("Your Identity");
        infoTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold +3; foreground: #AAAAAA");

        JLabel infoDesc = new JLabel("<html><center>Keep your contact information up to date to ensure <br>accurate delivery and billing of your crop requests.</center></html>");
        infoDesc.setForeground(new Color(100, 100, 100));

        infoPanel.add(avatar, "gapbottom 20");
        infoPanel.add(infoTitle, "gapbottom 10");
        infoPanel.add(infoDesc);

        // --- EVENTS ---
        btnUpdateProfile.addActionListener(e -> updateProfile());

        managerPanel.add(formPanel, "growy");
        managerPanel.add(infoPanel, "grow");
        add(managerPanel);
    }

    private void applyFieldStyle(JTextField f, String placeholder) {
        f.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        f.putClientProperty(FlatClientProperties.STYLE, "arc: 15; background: #1e1e1e; margin: 5,10,5,10");
    }

    private void loadUserData() {
        try (Connection conn = DBconnection.getConnection()) {
            // Assuming your user table is called 'users' and has these columns
            String sql = "SELECT username, password, contactNo, email FROM register_tbl WHERE userId = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, currentUserId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtusername.setText(rs.getString("name"));
                txtPassword.setText(rs.getString("email"));
                txtContactNo.setText(rs.getString("contact"));
                txtEmail.setText(rs.getString("address"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void updateProfile() {
        try (Connection conn = DBconnection.getConnection()) {
            String sql = "UPDATE register_tbl SET username=?, password=?, contactNo=?, email=? WHERE userId=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtusername.getText());
            pst.setString(2, txtPassword.getText());
            pst.setString(3, txtContactNo.getText());
            pst.setString(4, txtEmail.getText());
            pst.setInt(5, currentUserId);

            int result = pst.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Profile Updated! Please log in again.");

                // SAFE TRANSITION LOGIC
                SwingUtilities.invokeLater(() -> {
                    // 1. Find the Dashboard Frame that contains this Panel
                    Window parentWindow = SwingUtilities.getWindowAncestor(this);

                    if (parentWindow != null) {
                        parentWindow.dispose(); // Close the Dashboard
                    }

                    // 2. Open your Login/Starting Form
                    // Change 'OpenForm' to 'LoginForm' if that is your class name
                    LoginForm login = new LoginForm();
                    login.setVisible(true);
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}