import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import db.DBconnection;

public class BuyerDash extends JFrame {

    public JButton btnFarmers, btnCrops, btnSupply, btnInventory, btnReports, btnLogout, btnExit;
    private String officerName, username2;
    private int usid;
    private JPanel tableContainer;
    private JLabel pageTitle, lblTotalFarmers;
    private JButton currentActiveBtn = null;

    public BuyerDash(String name, String usern, int id) {
        this.officerName = name;
        this.username2 = usern;
        this.usid = id;
        initUI();
    }

    private void initUI() {
        setTitle("Buyer Dashboard - Smart Crop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1250, 850);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 0, gap 0", "[260!]0[grow,fill]", "[fill]"));

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel(new MigLayout("wrap, fillx, insets 30", "[fill]", "[]10[]30[]10[]10[]10[]push[]10[]"));
        sidebar.setBackground(new Color(32, 32, 32));

        // Logo Image
        try {
            ImageIcon originalIcon = new ImageIcon("C:\\Users\\SANDANIMNE\\Desktop\\EAD fnl\\logo.png");
            Image scaledImg = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            sidebar.add(new JLabel(new ImageIcon(scaledImg)), "center, gapbottom 10");
        } catch (Exception e) {}

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

        pageTitle = new JLabel("Buyer Overview");
        pageTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold +6; foreground: #FFFFFF");

        JLabel userProfile = new JLabel("<html>👤 Hi " + username2 + "<br><br><code><b>UID: " + usid + "</b></code></html>");
        userProfile.setForeground(new Color(180, 180, 180));
        topHeader.add(pageTitle);
        topHeader.add(userProfile);

        JPanel body = new JPanel(new MigLayout("wrap, fill, insets 20 25 25 25", "[grow,fill]", "[]15[]15[grow,fill]"));
        body.setOpaque(false);

        JLabel welcome = new JLabel("Welcome back, " + officerName + "!");
        welcome.putClientProperty(FlatClientProperties.STYLE, "font: bold +14; foreground: #FFFFFF");

        JPanel statsRow = new JPanel(new MigLayout("fillx, insets 0", "[fill]20[fill]20[fill]"));
        statsRow.setOpaque(false);

        lblTotalFarmers = new JLabel("0");
        statsRow.add(createStatCard("Total Farmers", lblTotalFarmers, "#3498db"));
        statsRow.add(createStatCard("Active Crops", new JLabel("45"), "#f1c40f"));
        statsRow.add(createStatCard("Supplies Dispatched", new JLabel("892"), "#2ecc71"));

        tableContainer = new JPanel(new BorderLayout());
        tableContainer.putClientProperty(FlatClientProperties.STYLE, "arc: 30; background: #1e1e1e");
        tableContainer.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel hint = new JLabel("Select a module to view management data.", SwingConstants.CENTER);
        hint.setForeground(new Color(100, 100, 100));
        tableContainer.add(hint);

        // Sidebar Actions
        btnFarmers.addActionListener(e -> {
            setActiveButton(btnFarmers);
            tableContainer.removeAll();
            tableContainer.add(createFarmerManager(), BorderLayout.CENTER);
            tableContainer.revalidate();
            tableContainer.repaint();
            pageTitle.setText("Farmer Management System");
        });

        btnExit.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Exit Application?", "Confirm", JOptionPane.YES_NO_OPTION) == 0) {
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

    private JPanel createFarmerManager() {
        JPanel managerPanel = new JPanel(new MigLayout("fill, insets 0", "[320!]25[grow, fill]", "[fill]"));
        managerPanel.setOpaque(false);

        final int[] selectedFarmerId = {-1};

        // --- FORM ---
        JPanel formPanel = new JPanel(new MigLayout("wrap, fillx, insets 25", "[fill]"));
        formPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #2a2a2a");

        JLabel title = new JLabel("Registration Form");
        title.putClientProperty(FlatClientProperties.STYLE, "font: bold +4; foreground: #FFFFFF");
        formPanel.add(title, "gapbottom 20");

        JTextField txtName = new JTextField();
        JTextField txtNIC = new JTextField();
        JTextField txtPhone = new JTextField();
        JTextField txtAddress = new JTextField();
        JComboBox<String> comboDist = new JComboBox<>(new String[]{"Colombo", "Gampaha", "Galle", "Kandy", "Matara", "Kurunegala"});

        formPanel.add(createFieldLabel("Full Name")); formPanel.add(txtName, "h 40!");
        formPanel.add(createFieldLabel("NIC Number")); formPanel.add(txtNIC, "h 40!");
        formPanel.add(createFieldLabel("Phone Number")); formPanel.add(txtPhone, "h 40!");
        formPanel.add(createFieldLabel("Address")); formPanel.add(txtAddress, "h 40!");
        formPanel.add(createFieldLabel("District")); formPanel.add(comboDist, "h 40!");

        JButton btnAdd = new JButton("Add Farmer");
        btnAdd.putClientProperty(FlatClientProperties.STYLE, "background: #2ecc71; foreground: #FFFFFF; arc: 15; font: bold");
        JButton btnUpdate = new JButton("Update");
        btnUpdate.putClientProperty(FlatClientProperties.STYLE, "background: #3498db; foreground: #FFFFFF; arc: 15");
        JButton btnDelete = new JButton("Delete");
        btnDelete.putClientProperty(FlatClientProperties.STYLE, "background: #e74c3c; foreground: #FFFFFF; arc: 15");
        JButton btnClear = new JButton("Clear Form");
        btnClear.putClientProperty(FlatClientProperties.STYLE, "background: #444444; foreground: #FFFFFF; arc: 15");

        formPanel.add(btnAdd, "gaptop 20, h 42!");
        formPanel.add(btnUpdate, "split 2, h 38!, growx");
        formPanel.add(btnDelete, "h 38!, growx");
        formPanel.add(btnClear, "h 38!");

        // --- TABLE & SEARCH ---
        JPanel tableArea = new JPanel(new MigLayout("fill, insets 0", "[grow]", "[]15[grow]"));
        tableArea.setOpaque(false);

        JTextField txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by Name or NIC...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background: #2a2a2a; margin: 5,10,5,10; outlineColor: #2ecc71");

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "background: #1e1e1e; foreground: #2ecc71; arc: 15; borderWidth: 1; borderColor: #2ecc71");

        JPanel topRow = new JPanel(new MigLayout("fill, insets 0", "[grow]10[]"));
        topRow.setOpaque(false);
        topRow.add(txtSearch, "growx"); topRow.add(btnRefresh, "h 38!");
        tableArea.add(topRow, "growx, wrap");

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "NIC", "Phone", "Address", "District"}, 0);
        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "background: #2a2a2a; foreground: #AAAAAA; font: bold");

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.getViewport().setBackground(new Color(30, 30, 30));
        tableArea.add(tableScroll, "grow");

        // --- BUTTON LOGIC ---
        btnRefresh.addActionListener(e -> loadFarmerData(table));

        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { searchFarmerData(table, txtSearch.getText()); }
        });

        btnAdd.addActionListener(e -> {
            if(txtName.getText().isEmpty() || txtNIC.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Name and NIC are required!");
                return;
            }
            try (Connection conn = DBconnection.getConnection()) {
                PreparedStatement pst = conn.prepareStatement("INSERT INTO farmer_tbl (fullName, nic, phone, address, district) VALUES (?,?,?,?,?)");
                pst.setString(1, txtName.getText()); pst.setString(2, txtNIC.getText());
                pst.setString(3, txtPhone.getText()); pst.setString(4, txtAddress.getText());
                pst.setString(5, comboDist.getSelectedItem().toString());
                pst.executeUpdate();
                loadFarmerData(table);
                clearFields(txtName, txtNIC, txtPhone, txtAddress);
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnUpdate.addActionListener(e -> {
            if (selectedFarmerId[0] == -1) return;
            try (Connection conn = DBconnection.getConnection()) {
                PreparedStatement pst = conn.prepareStatement("UPDATE farmer_tbl SET fullName=?, nic=?, phone=?, address=?, district=? WHERE fId=?");
                pst.setString(1, txtName.getText()); pst.setString(2, txtNIC.getText());
                pst.setString(3, txtPhone.getText()); pst.setString(4, txtAddress.getText());
                pst.setString(5, comboDist.getSelectedItem().toString()); pst.setInt(6, selectedFarmerId[0]);
                pst.executeUpdate();
                loadFarmerData(table);
                selectedFarmerId[0] = -1; btnAdd.setEnabled(true);
                clearFields(txtName, txtNIC, txtPhone, txtAddress);
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnDelete.addActionListener(e -> {
            if (selectedFarmerId[0] == -1) {
                JOptionPane.showMessageDialog(this, "Please select a record!");
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Delete this farmer?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == 0) {
                try (Connection conn = DBconnection.getConnection()) {
                    PreparedStatement pst = conn.prepareStatement("DELETE FROM farmer_tbl WHERE fId=?");
                    pst.setInt(1, selectedFarmerId[0]); pst.executeUpdate();
                    loadFarmerData(table);
                    clearFields(txtName, txtNIC, txtPhone, txtAddress);
                    selectedFarmerId[0] = -1; btnAdd.setEnabled(true);
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });

        btnClear.addActionListener(e -> {
            clearFields(txtName, txtNIC, txtPhone, txtAddress);
            selectedFarmerId[0] = -1; btnAdd.setEnabled(true);
            table.clearSelection();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int r = table.getSelectedRow();
                selectedFarmerId[0] = (int) model.getValueAt(r, 0);
                txtName.setText(model.getValueAt(r, 1).toString());
                txtNIC.setText(model.getValueAt(r, 2).toString());
                txtPhone.setText(model.getValueAt(r, 3).toString());
                txtAddress.setText(model.getValueAt(r, 4).toString());
                comboDist.setSelectedItem(model.getValueAt(r, 5).toString());
                btnAdd.setEnabled(false);
            }
        });

        loadFarmerData(table);
        managerPanel.add(new JScrollPane(formPanel), "growy");
        managerPanel.add(tableArea, "grow");
        return managerPanel;
    }

    private void loadFarmerData(JTable t) {
        DefaultTableModel m = (DefaultTableModel) t.getModel();
        m.setRowCount(0);
        int count = 0;
        try (Connection c = DBconnection.getConnection(); Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT fId, fullName, nic, phone, address, district FROM farmer_tbl")) {
            while (r.next()) {
                m.addRow(new Object[]{r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6)});
                count++;
            }
            lblTotalFarmers.setText(String.valueOf(count));
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void searchFarmerData(JTable t, String q) {
        DefaultTableModel m = (DefaultTableModel) t.getModel();
        m.setRowCount(0);
        try (Connection c = DBconnection.getConnection()) {
            PreparedStatement p = c.prepareStatement("SELECT * FROM farmer_tbl WHERE fullName LIKE ? OR nic LIKE ?");
            p.setString(1, "%"+q+"%"); p.setString(2, "%"+q+"%");
            ResultSet r = p.executeQuery();
            while (r.next()) m.addRow(new Object[]{r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6)});
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void setActiveButton(JButton btn) {
        if (currentActiveBtn != null) {
            currentActiveBtn.putClientProperty(FlatClientProperties.STYLE, "background: #202020; foreground: #D0D0D0");
        }
        btn.putClientProperty(FlatClientProperties.STYLE, "background: #2ecc71; foreground: #FFFFFF");
        currentActiveBtn = btn;
    }

    private void clearFields(JTextField... f) { for (JTextField j : f) j.setText(""); }
    private JLabel createFieldLabel(String t) {
        JLabel l = new JLabel(t); l.putClientProperty(FlatClientProperties.STYLE, "foreground: #AAAAAA; font: -1"); return l;
    }
    private JButton createMenuButton(String t) {
        JButton b = new JButton(t); b.setHorizontalAlignment(SwingConstants.LEFT);
        b.putClientProperty(FlatClientProperties.STYLE, "arc: 15; background: #202020; foreground: #D0D0D0; borderWidth: 0; focusWidth: 0; margin: 10,20,10,20");
        return b;
    }
    private JPanel createStatCard(String t, JLabel lblValue, String color) {
        JPanel p = new JPanel(new MigLayout("wrap, insets 20", "[fill]"));
        p.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #2a2a2a");
        lblValue.putClientProperty(FlatClientProperties.STYLE, "font: bold +15; foreground: " + color);
        p.add(new JLabel(t.toUpperCase())); p.add(lblValue); return p;
    }
}