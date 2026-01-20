import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OfficerDash extends JFrame {

    public JButton btnFarmers, btnCrops, btnSupply, btnInventory, btnReports, btnLogout, btnExit;
    private String officerName, username2;
    private int usid;
    private JPanel tableContainer;
    private JLabel pageTitle, lblTotalFarmers, lblTotalCrops, lblSuppliesDispatched;
    private JButton currentActiveBtn = null;

    public OfficerDash(String name, String usern, int id) {
        this.officerName = name;
        this.username2 = usern;
        this.usid = id;
        initUI();
    }

    private void initUI() {
        setTitle("Officer Dashboard - Smart Crop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1250, 850);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 0, gap 0", "[260!]0[grow,fill]", "[fill]"));

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel(new MigLayout("wrap, fillx, insets 30", "[fill]", "[]10[]30[]10[]10[]10[]10[]push[]10[]"));
        sidebar.setBackground(new Color(32, 32, 32));

        // Logo Image
        try {
            ImageIcon originalIcon = new ImageIcon("C:\\Users\\SANDANIMNE\\Desktop\\EAD fnl\\logo.png");
            Image scaledImg = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            sidebar.add(new JLabel(new ImageIcon(scaledImg)), "center, gapbottom 10");
        } catch (Exception e) { /* Icon path handling */ }

        JLabel logoText = new JLabel("SMART CROP");
        logoText.putClientProperty(FlatClientProperties.STYLE, "font: bold +18; foreground: #2ecc71");
        sidebar.add(logoText, "center, gapbottom 40");

        btnFarmers   = createMenuButton("👨‍🌾  Farmers");
        btnCrops     = createMenuButton("🌾  Crops");
        btnSupply    = createMenuButton("📦  Prepare Supply");
        btnInventory = createMenuButton("📊  Inventory");
        btnReports   = createMenuButton("📄  Reports");
        btnLogout    = createMenuButton("🚪  Logout");
        btnExit      = createMenuButton("❌  Exit System");

        btnLogout.setForeground(new Color(231, 76, 60));
        btnExit.setForeground(new Color(149, 165, 166));

        sidebar.add(btnFarmers);
        sidebar.add(btnCrops);
        sidebar.add(btnSupply);
        sidebar.add(btnInventory);
        sidebar.add(btnReports);
        sidebar.add(btnLogout);
        sidebar.add(btnExit);

        // ===== CONTENT AREA =====
        JPanel contentArea = new JPanel(new MigLayout("wrap, fill, insets 0", "[grow,fill]", "[]0[grow,fill]"));
        contentArea.setBackground(new Color(24, 24, 24));

        JPanel topHeader = new JPanel(new MigLayout("fill, insets 0 25 0 25", "[]push[]"));
        topHeader.setBackground(new Color(30, 30, 30));
        topHeader.setPreferredSize(new Dimension(0, 70));

        pageTitle = new JLabel("Officer Overview");
        pageTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold +6; foreground: #FFFFFF");

        JLabel userProfile = new JLabel("<html>👤 Hi " + username2 + "<br><code><b>UID: " + usid + "</b></code></html>");
        userProfile.setForeground(new Color(180, 180, 180));
        topHeader.add(pageTitle);
        topHeader.add(userProfile);

        JPanel body = new JPanel(new MigLayout("wrap, fill, insets 20 25 25 25", "[grow,fill]", "[]15[]15[grow,fill]"));
        body.setOpaque(false);

        JLabel welcome = new JLabel("Welcome back, " + officerName + "!");
        welcome.putClientProperty(FlatClientProperties.STYLE, "font: bold +14; foreground: #FFFFFF");

        // Stats Cards
        JPanel statsRow = new JPanel(new MigLayout("fillx, insets 0", "[fill]20[fill]20[fill]"));
        statsRow.setOpaque(false);

        lblTotalFarmers = new JLabel("0");
        statsRow.add(createStatCard("Total Farmers", lblTotalFarmers, "#3498db"));
        lblTotalCrops = new JLabel("0");
        statsRow.add(createStatCard("Active Crops", lblTotalCrops, "#f1c40f"));
        lblSuppliesDispatched = new JLabel("0");
        statsRow.add(createStatCard("Supplies Dispatched", lblSuppliesDispatched, "#2ecc71"));

        tableContainer = new JPanel(new BorderLayout());
        tableContainer.putClientProperty(FlatClientProperties.STYLE, "arc: 30; background: #1e1e1e");
        tableContainer.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel hint = new JLabel("Select a module from the sidebar to begin management.", SwingConstants.CENTER);
        hint.setForeground(new Color(100, 100, 100));
        tableContainer.add(hint);

        // ==========================================
        // SIDEBAR ACTIONS (Logic Section)
        // ==========================================

        btnFarmers.addActionListener(e -> {
            setActiveButton(btnFarmers);
            showForm(new FarmerMgr(lblTotalFarmers), "Farmer Management System");
        });

        btnCrops.addActionListener(e -> {
            setActiveButton(btnCrops);
            showForm(new CropsMgr(lblTotalCrops), "Crops Inventory System");
        });

        btnSupply.addActionListener(e -> {
            setActiveButton(btnSupply);
            showForm(new SupplyMgr(lblSuppliesDispatched), "Supply Preparation & Logistics");
        });

        btnInventory.addActionListener(e -> {
            setActiveButton(btnInventory);
            showForm(new InventoryMgr(), "Inventory Overview (Read-Only)");
        });

        btnReports.addActionListener(e -> {
            setActiveButton(btnReports);
            showForm(new ReportMgr(), "Generate Analytical Reports");
        });

        btnLogout.addActionListener(e ->{
            if (JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {

                dispose();
                new LoginForm().setVisible(true);
            }
        });

        btnExit.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Exit Application?", "Confirm Exit", JOptionPane.YES_NO_OPTION) == 0) {
                System.exit(0);
            }
        });

        // Assemble Dashboard
        body.add(welcome);
        body.add(statsRow, "h 130!");
        body.add(tableContainer, "grow");
        contentArea.add(topHeader, "h 70!");
        contentArea.add(body, "grow");
        mainPanel.add(sidebar);
        mainPanel.add(contentArea);
        setContentPane(mainPanel);
    }

    // Helper method to clear container and show new form
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
        b.putClientProperty(FlatClientProperties.STYLE,
                "arc: 15; background: #202020; foreground: #D0D0D0; " +
                        "borderWidth: 0; focusWidth: 0; margin: 10,20,10,20"
        );
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