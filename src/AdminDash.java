import javax.swing.*;

public class AdminDash extends JFrame {

    public AdminDash(String username) {
        setTitle("Admin Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lbl = new JLabel("Welcome Admin");
        lbl.setHorizontalAlignment(JLabel.CENTER);

        add(lbl);
    }
}
