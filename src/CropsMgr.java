import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import db.DBconnection;

public class CropsMgr extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtCropName, txtSearch;
    private JComboBox<String> comboCategory, comboSeason, comboFarmer;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private final int[] selectedCropId = {-1};

    public CropsMgr() {
        setLayout(new BorderLayout());
        setOpaque(false);
        initUI();
        loadCropData();
    }

    private void initUI() {
        JPanel managerPanel = new JPanel(new MigLayout("fill, insets 0", "[320!]25[grow, fill]", "[fill]"));
        managerPanel.setOpaque(false);

        // --- LEFT COLUMN: FORM ---
        JPanel formPanel = new JPanel(new MigLayout("wrap, fillx, insets 25", "[fill]"));
        formPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #2a2a2a");

        JLabel title = new JLabel("Crop Registration");
        title.putClientProperty(FlatClientProperties.STYLE, "font: bold +4; foreground: #FFFFFF");
        formPanel.add(title, "gapbottom 20");

        txtCropName = new JTextField();
        comboCategory = new JComboBox<>(new String[]{"Vegetable", "Fruit", "Grains", "Spices", "Other"});
        comboSeason = new JComboBox<>(new String[]{"Yala", "Maha", "All Season"});
        comboFarmer = new JComboBox<>();
        loadFarmerDropdown();

        formPanel.add(createFieldLabel("Crop Name")); formPanel.add(txtCropName, "h 40!");
        formPanel.add(createFieldLabel("Category")); formPanel.add(comboCategory, "h 40!");
        formPanel.add(createFieldLabel("Season")); formPanel.add(comboSeason, "h 40!");
        formPanel.add(createFieldLabel("Assigned Farmer")); formPanel.add(comboFarmer, "h 40!");

        btnAdd = new JButton("Add Crop");
        btnAdd.putClientProperty(FlatClientProperties.STYLE, "background: #2ecc71; foreground: #FFFFFF; arc: 15; font: bold");
        btnUpdate = new JButton("Update");
        btnUpdate.putClientProperty(FlatClientProperties.STYLE, "background: #3498db; foreground: #FFFFFF; arc: 15");
        btnDelete = new JButton("Delete");
        btnDelete.putClientProperty(FlatClientProperties.STYLE, "background: #e74c3c; foreground: #FFFFFF; arc: 15");
        btnClear = new JButton("Clear");
        btnClear.putClientProperty(FlatClientProperties.STYLE, "background: #444444; foreground: #FFFFFF; arc: 15");

        formPanel.add(btnAdd, "gaptop 20, h 42!");
        formPanel.add(btnUpdate, "split 2, h 38!, growx");
        formPanel.add(btnDelete, "h 38!, growx");
        formPanel.add(btnClear, "h 38!");

        // --- RIGHT COLUMN: TABLE ---
        JPanel tableArea = new JPanel(new MigLayout("fill, insets 0", "[grow]", "[]15[grow]"));
        tableArea.setOpaque(false);

        txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by Name or NIC...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        txtSearch.putClientProperty(
                FlatClientProperties.STYLE,
                "arc: 20; background: #2a2a2a; margin: 5,10,5,10; outlineColor: #2ecc71"
        );

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.putClientProperty(
                FlatClientProperties.STYLE,
                "background: #1e1e1e; foreground: #2ecc71; arc: 15; borderWidth: 1; borderColor: #2ecc71"
        );


        model = new DefaultTableModel(new String[]{"ID", "Crop Name", "Category", "Season", "Farmer"}, 0);
        table = new JTable(model);
        table.setRowHeight(40);
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "background: #2a2a2a; foreground: #AAAAAA; font: bold");

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.getViewport().setBackground(new Color(30, 30, 30));

        tableArea.add(txtSearch, "growx, h 38!, wrap");
        tableArea.add(tableScroll, "grow");

        // --- EVENT LISTENERS ---
        btnAdd.addActionListener(e -> saveCrop());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int r = table.getSelectedRow();
                selectedCropId[0] = (int) model.getValueAt(r, 0);
                txtCropName.setText(model.getValueAt(r, 1).toString());
                comboCategory.setSelectedItem(model.getValueAt(r, 2).toString());
                comboSeason.setSelectedItem(model.getValueAt(r, 3).toString());
                comboFarmer.setSelectedItem(model.getValueAt(r, 4).toString());
                btnAdd.setEnabled(false);
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { searchCropData(txtSearch.getText()); }
        });

        btnClear.addActionListener(e -> clearFields());

        managerPanel.add(new JScrollPane(formPanel), "growy");
        managerPanel.add(tableArea, "grow");
        add(managerPanel);
    }

    private void saveCrop() {
        try (Connection conn = DBconnection.getConnection()) {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO crops_tbl (cropName, category, season, farmerName) VALUES (?,?,?,?)");
            pst.setString(1, txtCropName.getText());
            pst.setString(2, comboCategory.getSelectedItem().toString());
            pst.setString(3, comboSeason.getSelectedItem().toString());
            pst.setString(4, comboFarmer.getSelectedItem().toString());
            pst.executeUpdate();
            loadCropData();
            clearFields();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadCropData() {
        model.setRowCount(0);
        try (Connection c = DBconnection.getConnection(); Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM crops_tbl")) {
            while (r.next()) model.addRow(new Object[]{r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5)});
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void searchCropData(String q) {
        model.setRowCount(0);
        try (Connection c = DBconnection.getConnection()) {
            PreparedStatement p = c.prepareStatement("SELECT * FROM crops_tbl WHERE cropName LIKE ? OR farmerName LIKE ?");
            p.setString(1, "%"+q+"%"); p.setString(2, "%"+q+"%");
            ResultSet r = p.executeQuery();
            while (r.next()) model.addRow(new Object[]{r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5)});
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadFarmerDropdown() {
        try (Connection conn = DBconnection.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT fullName FROM farmer_tbl")) {
            comboFarmer.removeAllItems();
            while (rs.next()) comboFarmer.addItem(rs.getString("fullName"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void clearFields() {
        txtCropName.setText("");
        selectedCropId[0] = -1;
        btnAdd.setEnabled(true);
        table.clearSelection();
    }

    private JLabel createFieldLabel(String t) {
        JLabel l = new JLabel(t);
        l.putClientProperty(FlatClientProperties.STYLE, "foreground: #AAAAAA; font: -1");
        return l;
    }
}