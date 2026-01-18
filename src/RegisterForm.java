import com.formdev.flatlaf.FlatClientProperties;
import db.DBconnection;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Statement;

public class RegisterForm extends JFrame {

    // Components made public for access in Main2
    public JTextField txtFullname, txtUser, txtContact, txtEmail;
    public JPasswordField txtPass;
    public JComboBox<String> cbRole;
    public JButton btnSubmit, btnBack;

    public RegisterForm() {
        initUI();
    }

    private void initUI() {
        setTitle("Create Account AS Officer - Smart Crop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 780);
        setLocationRelativeTo(null);

        // Main Background Panel
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 20", "[center]", "[center]"));
        mainPanel.setBackground(new Color(30, 30, 30));

        // The Register Card
        JPanel card = new JPanel(new MigLayout("wrap, fillx, insets 35 45 35 45", "[fill]"));
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 40; background: #252525");

        // --- Header ---
        JLabel title = new JLabel("Join Smart Crop");
        title.putClientProperty(FlatClientProperties.STYLE, "font: bold +12; foreground: #FFFFFF");

        // --- Labels with Green Accents ---
        JLabel lblPersonal = createSectionLabel("Personal Information");
        JLabel lblSecurity = createSectionLabel("Account Security");
        JLabel lblRole = createSectionLabel("User Role");

        // --- Fields ---
        txtFullname = createStyledField("Enter Full Name");
        txtContact = createStyledField("Enter Contact Number");
        txtEmail = createStyledField("Enter Email Address");
        txtUser = createStyledField("Create Username");

        txtPass = new JPasswordField();
        txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Create Password");
        txtPass.putClientProperty(FlatClientProperties.STYLE, "arc: 20; margin: 5,10,5,10");

        // Role Dropdown
        String[] Roles = {"Select your Role","Officer", "Buyer"};
        cbRole = new JComboBox<>(Roles);
        cbRole.putClientProperty(FlatClientProperties.STYLE, "arc: 20;hoverBackground: #3a3a3a;borderWidth: 1; padding: 5,10,5,10");
        cbRole.setPreferredSize(new Dimension(cbRole.getPreferredSize().width, 40));


        // Submit Button (Green)
        btnSubmit = new JButton("Create Account");
        btnSubmit.putClientProperty(FlatClientProperties.STYLE,
                "background: #2ecc71; foreground: #FFFFFF; arc: 25; font: bold +2; borderWidth: 0; focusWidth: 0");
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Back Link
        btnBack = new JButton("Already have an account? Click here");
        btnBack.setForeground(new Color(150, 150, 150));
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setHorizontalAlignment(SwingConstants.RIGHT);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- Assemble Components ---
        card.add(title, "center, gapbottom 25");
        card.add(lblPersonal, "gapbottom 5");
        card.add(txtFullname, "h 40!");
        card.add(txtContact, "h 40!");
        card.add(txtEmail, "h 40!");
        card.add(lblSecurity, "gaptop 15, gapbottom 5");
        card.add(txtUser, "h 40!");
        card.add(txtPass, "h 40!");
        card.add(lblRole, "gaptop 15, gapbottom 5");
        card.add(cbRole, "h 40!");
        card.add(btnSubmit, "h 50!, gaptop 30");
        card.add(btnBack, "right, gaptop 10");

        mainPanel.add(card, "width 400!");
        add(mainPanel);
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(46, 204, 113));
        label.putClientProperty(FlatClientProperties.STYLE, "font: bold -2");
        return label;
    }

    private JTextField createStyledField(String placeholder) {
        JTextField field = new JTextField();
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        field.putClientProperty(FlatClientProperties.STYLE, "arc: 20; margin: 5,10,5,10");
        return field;
    }

    /**
     * Database logic for registration.
     * Call this method from your Main2.java listener.
     */
    public void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {
        // Attach this in your constructor or after creating btnSubmit

            String fullName = txtFullname.getText();
            String username = txtUser.getText();
            String contact = txtContact.getText();
            String email = txtEmail.getText();
            String password = new String(txtPass.getPassword());
            String role = cbRole.getSelectedItem().toString();
            String query;

            // Validation
            if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || role.equals("Select Status")) {
                JOptionPane.showMessageDialog(this, "Please fill all fields and select a role.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection con = DBconnection.getConnection()) {
                /*String query = "INSERT INTO login_tbl(fullName, username, password, contactNo, email, role) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);

                pst.setString(1, fullName);
                pst.setString(2, username);
                pst.setString(3, password); // optional: hash the password
                pst.setString(4, contact);
                pst.setString(5, email);
                pst.setString(6, role);*/

                Statement st = (Statement) con.createStatement();

                query = "INSERT INTO register_tbl (fullName,username,contactNo,email,password,role)"+ "VALUES('"+fullName+"','"+username+"','"+contact+"','"+email+"','"+password+"','"+role+"')"; //QL INSERT query is built and run to save data into the signuptabletable.
                int result = st.executeUpdate(query);

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Successfully registered!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    txtFullname.setText("");
                    txtUser.setText("");
                    txtPass.setText("");
                    txtContact.setText("");
                    txtEmail.setText("");
                    cbRole.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Register unsuccessful!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        if (cbRole.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a valid Role.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return; // Stop the code here so it doesn't try to save to DB
        }
        }



}