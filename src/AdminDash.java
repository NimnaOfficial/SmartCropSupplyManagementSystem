import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDash extends JFrame {

    public JButton btnUserMgmt, btnFarmers, btnReports, btnLogout, btnExit;
    private String adminName, username2;
    private int usid;
    private JPanel tableContainer;
    private JLabel pageTitle, lblTotalUsers, lblTotalFarmers;
    private JButton currentActiveBtn = null;

    public AdminDash(String name, String usern, int id) {
        this.adminName = name;
        this.username2 = usern;
        this.usid = id;
        initUI();
    }

    private void initUI() {
        setTitle("Administrator Console - Smart Crop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1250, 850);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 0, gap 0", "[260!]0[grow,fill]", "[fill]"));

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel(new MigLayout("wrap, fillx, insets 30", "[fill]", "[]10[]30[]10[]10[]push[]10[]"));
        sidebar.setBackground(new Color(32, 32, 32));

        // Logo
        try {
            ImageIcon originalIcon = new ImageIcon("C:\\Users\\SANDANIMNE\\Desktop\\EAD fnl\\logo.png");
            Image scaledImg = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            sidebar.add(new JLabel(new ImageIcon(scaledImg)), "center, gapbottom 10");
        } catch (Exception e) { /* Handle path */ }

        JLabel logoText = new JLabel("SMART CROP");
        logoText.putClientProperty(FlatClientProperties.STYLE, "font: bold +18; foreground: #2ecc71");
        sidebar.add(logoText, "center, gapbottom 40");

        // Admin Specific Buttons
        btnUserMgmt = createMenuButton("👥  User Management");
        btnFarmers  = createMenuButton("🧑‍🌾  Farmers");
        btnReports  = createMenuButton("📊  System Reports");
        btnLogout   = createMenuButton("🚪  Logout");
        btnExit     = createMenuButton("❌  Exit System");

        btnLogout.setForeground(new Color(231, 76, 60));
        btnExit.setForeground(new Color(149, 165, 166));

        sidebar.add(btnUserMgmt);
        sidebar.add(btnFarmers);
        sidebar.add(btnReports);
        sidebar.add(btnLogout);
        sidebar.add(btnExit);

        // ===== CONTENT AREA =====
        JPanel contentArea = new JPanel(new MigLayout("wrap, fill, insets 0", "[grow,fill]", "[]0[grow,fill]"));
        contentArea.setBackground(new Color(24, 24, 24));

        JPanel topHeader = new JPanel(new MigLayout("fill, insets 0 25 0 25", "[]push[]"));
        topHeader.setBackground(new Color(30, 30, 30));
        topHeader.setPreferredSize(new Dimension(0, 70));

        pageTitle = new JLabel("Admin Overview");
        pageTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold +6; foreground: #FFFFFF");

        JLabel userProfile = new JLabel("<html>👤 Hi " + username2 + "<br><code><b>UID: " + usid + "</b></code></html>");
        userProfile.setForeground(new Color(180, 180, 180));
        topHeader.add(pageTitle);
        topHeader.add(userProfile);

        JPanel body = new JPanel(new MigLayout("wrap, fill, insets 20 25 25 25", "[grow,fill]", "[]15[]15[grow,fill]"));
        body.setOpaque(false);

        JLabel welcome = new JLabel("System Control Panel - " + adminName);
        welcome.putClientProperty(FlatClientProperties.STYLE, "font: bold +14; foreground: #FFFFFF");

        // Stat Cards: Total Users and Total Farmers
        JPanel statsRow = new JPanel(new MigLayout("fillx, insets 0", "[fill]20[fill]"));
        statsRow.setOpaque(false);

        lblTotalUsers = new JLabel("0");
        statsRow.add(createStatCard("Total Users", lblTotalUsers, "#3498db"));
        lblTotalFarmers = new JLabel("0");
        statsRow.add(createStatCard("Total Farmers", lblTotalFarmers, "#f1c40f"));

        tableContainer = new JPanel(new BorderLayout());
        tableContainer.putClientProperty(FlatClientProperties.STYLE, "arc: 30; background: #1e1e1e");
        tableContainer.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel hint = new JLabel("Select a management category to view system data.", SwingConstants.CENTER);
        hint.setForeground(new Color(100, 100, 100));
        tableContainer.add(hint);

        // --- SIDEBAR ACTIONS ---
        btnUserMgmt.addActionListener(e -> {
            setActiveButton(btnUserMgmt);
            showForm(new UserMgr(lblTotalUsers), "System User Management");
        });

        btnFarmers.addActionListener(e -> {
            setActiveButton(btnFarmers);
            showForm(new FarmerMgr2(lblTotalFarmers), "Farmer Registry Control");
        });

        btnReports.addActionListener(e -> {
            setActiveButton(btnReports);
            showForm(new ReportMgr2(), "Generate Analytical System Reports");
        });

        btnLogout.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {

                dispose();
                new LoginForm().setVisible(true);
            }
        });

        btnExit.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Shut down the application?", "Confirm Exit", JOptionPane.YES_NO_OPTION) == 0) {
                System.exit(0);
            }
        });

        body.add(welcome);
        body.add(statsRow, "h 130!");
        body.add(tableContainer, "grow");
        contentArea.add(topHeader, "h 70!");
        contentArea.add(body, "grow");
        mainPanel.add(sidebar);
        mainPanel.add(contentArea);
        setContentPane(mainPanel);
    }

    private void showForm(JPanel form, String title) {
        tableContainer.removeAll();
        tableContainer.add(form, BorderLayout.CENTER);
        tableContainer.revalidate();
        tableContainer.repaint();
        pageTitle.setText(title);
    }

    private JButton createMenuButton(String t) {
        JButton b = new JButton(t);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.putClientProperty(FlatClientProperties.STYLE, "arc: 15; background: #202020; foreground: #D0D0D0; borderWidth: 0; focusWidth: 0; margin: 10,20,10,20");
        return b;
    }

    private JPanel createStatCard(String t, JLabel lblValue, String color) {
        JPanel p = new JPanel(new MigLayout("wrap, insets 20", "[fill]"));
        p.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #2a2a2a");
        lblValue.putClientProperty(FlatClientProperties.STYLE, "font: bold +15; foreground: " + color);
        p.add(new JLabel(t.toUpperCase()));
        p.add(lblValue);
        return p;
    }

    private void setActiveButton(JButton btn) {
        if (currentActiveBtn != null) {
            currentActiveBtn.putClientProperty(FlatClientProperties.STYLE, "background: #202020; foreground: #D0D0D0");
        }
        btn.putClientProperty(FlatClientProperties.STYLE, "background: #2ecc71; foreground: #FFFFFF");
        currentActiveBtn = btn;
    }
}