import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import db.DBconnection;

public class FarmerMgr extends JPanel {

    private final JLabel lblTotalFarmers;
    private final int[] selectedFarmerId = {-1};

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtName, txtNIC, txtPhone, txtAddress, txtSearch;
    private JComboBox<String> comboDist;
    private JButton btnAdd, btnUpdate, btnDelete;

    public FarmerMgr(JLabel lblTotalFarmers) {
        this.lblTotalFarmers = lblTotalFarmers;
        initUI();
        loadFarmerData();
    }

    // ================= UI =================

    private void initUI() {
        setLayout(new MigLayout("fill, insets 0", "[320!]25[grow,fill]", "[fill]"));
        setOpaque(false);

        // ---------- FORM PANEL ----------
        JPanel formPanel = new JPanel(new MigLayout("wrap, fillx, insets 25", "[fill]"));
        formPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #2a2a2a");

        formPanel.add(title("Registration Form"), "gapbottom 20");

        txtName = new JTextField();
        txtNIC = new JTextField();
        txtPhone = new JTextField();
        txtAddress = new JTextField();
        comboDist = new JComboBox<>(new String[]{
                "Colombo", "Gampaha", "Galle", "Kandy", "Matara", "Kurunegala"
        });

        formPanel.add(label("Full Name"));    formPanel.add(txtName, "h 40!");
        formPanel.add(label("NIC Number"));   formPanel.add(txtNIC, "h 40!");
        formPanel.add(label("Phone Number")); formPanel.add(txtPhone, "h 40!");
        formPanel.add(label("Address"));      formPanel.add(txtAddress, "h 40!");
        formPanel.add(label("District"));     formPanel.add(comboDist, "h 40!");

        btnAdd = button("Add Farmer", "#2ecc71");
        btnUpdate = button("Update", "#3498db");
        btnDelete = button("Delete", "#e74c3c");
        JButton btnClear = button("Clear Form", "#444444");

        formPanel.add(btnAdd, "gaptop 20, h 42!");
        formPanel.add(btnUpdate, "split 2, h 38!, growx");
        formPanel.add(btnDelete, "h 38!, growx");
        formPanel.add(btnClear, "h 38!");

        // ---------- TABLE AREA ----------
        JPanel tableArea = new JPanel(new MigLayout("fillx, insets 0", "[grow,fill]", "[]15[grow]"));
        tableArea.setOpaque(false);

        txtSearch = new JTextField();
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by Name or NIC...");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background: #2a2a2a; margin: 5,10,5,10; outlineColor: #2ecc71");

        JPanel topRow = new JPanel(new MigLayout("fillx, insets 0", "[grow,fill]15[100!]"));
        topRow.setOpaque(false);
        topRow.add(txtSearch, "growx, pushx");
        tableArea.add(topRow, "growx, wrap");

        model = new DefaultTableModel(new String[]{"ID", "Name", "NIC", "Phone", "Address", "District"}, 0);
        table = new JTable(model);
        table.setRowHeight(40);
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "background: #2a2a2a; foreground: #AAAAAA; font: bold");

        tableArea.add(new JScrollPane(table), "grow");

        // ---------- EVENTS ----------


        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchFarmerData(txtSearch.getText());
            }
        });

        btnAdd.addActionListener(e -> addFarmer());
        btnUpdate.addActionListener(e -> updateFarmer());
        btnDelete.addActionListener(e -> deleteFarmer());
        btnClear.addActionListener(e -> clearField());

        table.getSelectionModel().addListSelectionListener(e -> selectRow());

        add(new JScrollPane(formPanel), "growy");
        add(tableArea, "growx, growy");
    }

    // ================= CRUD =================

    private void addFarmer() {
        try (Connection conn = DBconnection.getConnection()) {
            PreparedStatement p = conn.prepareStatement("INSERT INTO farmer_tbl (fullName, nic, phone, address, district) VALUES (?,?,?,?,?)");
            p.setString(1, txtName.getText());
            p.setString(2, txtNIC.getText());
            p.setString(3, txtPhone.getText());
            p.setString(4, txtAddress.getText());
            p.setString(5, comboDist.getSelectedItem().toString());
            p.executeUpdate();
            loadFarmerData();
            clearField();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateFarmer() {
        if (selectedFarmerId[0] == -1) return;

        try (Connection conn = DBconnection.getConnection()) {
            PreparedStatement p = conn.prepareStatement("UPDATE farmer_tbl SET fullName=?, nic=?, phone=?, address=?, district=? WHERE fId=?");
            p.setString(1, txtName.getText());
            p.setString(2, txtNIC.getText());
            p.setString(3, txtPhone.getText());
            p.setString(4, txtAddress.getText());
            p.setString(5, comboDist.getSelectedItem().toString());
            p.setInt(6, selectedFarmerId[0]);
            p.executeUpdate();
            loadFarmerData();
            clearField();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteFarmer() {
        if (selectedFarmerId[0] == -1) return;

        try (Connection c = DBconnection.getConnection()) {PreparedStatement p = c.prepareStatement("DELETE FROM farmer_tbl WHERE fId=?");
            p.setInt(1, selectedFarmerId[0]);
            p.executeUpdate();
            loadFarmerData();
            clearField();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadFarmerData() {
        model.setRowCount(0);
        int count = 0;

        try (Connection c = DBconnection.getConnection();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM farmer_tbl")) {

            while (r.next()) {
                model.addRow(new Object[]{
                        r.getInt(1),
                        r.getString(2),
                        r.getString(3),
                        r.getString(4),
                        r.getString(5),
                        r.getString(6)
                });
                count++;
            }
            lblTotalFarmers.setText(String.valueOf(count));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void searchFarmerData(String q) {
        model.setRowCount(0);
        try (Connection c = DBconnection.getConnection()) {
            PreparedStatement p = c.prepareStatement("SELECT * FROM farmer_tbl WHERE fullName LIKE ? OR nic LIKE ?");
            p.setString(1, "%" + q + "%");
            p.setString(2, "%" + q + "%");
            ResultSet r = p.executeQuery();

            while (r.next()) {
                model.addRow(new Object[]{
                        r.getInt(1),
                        r.getString(2),
                        r.getString(3),
                        r.getString(4),
                        r.getString(5),
                        r.getString(6)
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void selectRow() {
        int r = table.getSelectedRow();
        if (r == -1) return;

        selectedFarmerId[0] = (int) model.getValueAt(r, 0);
        txtName.setText(model.getValueAt(r, 1).toString());
        txtNIC.setText(model.getValueAt(r, 2).toString());
        txtPhone.setText(model.getValueAt(r, 3).toString());
        txtAddress.setText(model.getValueAt(r, 4).toString());
        comboDist.setSelectedItem(model.getValueAt(r, 5));
        btnAdd.setEnabled(false);
    }

    private void clearField() {
        txtName.setText("");
        txtNIC.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        comboDist.setSelectedIndex(0);
        selectedFarmerId[0] = -1;
        btnAdd.setEnabled(true);
        table.clearSelection();
    }

    // ================= UI HELPERS =================

    private JLabel label(String t) {JLabel l = new JLabel(t);l.putClientProperty(FlatClientProperties.STYLE, "foreground: #AAAAAA");
        return l;
    }

    private JLabel title(String t) {JLabel l = new JLabel(t);l.putClientProperty(FlatClientProperties.STYLE, "font: bold +4; foreground: #FFFFFF");
        return l;
    }

    private JButton button(String t, String color) {
        JButton b = new JButton(t);
        b.putClientProperty(FlatClientProperties.STYLE, "background: " + color + "; foreground: #FFFFFF; arc: 15");
        return b;
    }
}
