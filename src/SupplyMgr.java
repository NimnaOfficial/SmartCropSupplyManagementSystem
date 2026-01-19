import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.JDateChooser; // Requires JCalendar library
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import db.DBconnection;

public class SupplyMgr extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtQuantity;
    private JComboBox<String> comboCrops;
    private JDateChooser dateChooser;
    private JButton btnPrepare, btnCancel;
    private JLabel lblTotalSupplies;

    public SupplyMgr(JLabel lblTotalSupplies) {
        this.lblTotalSupplies = lblTotalSupplies;
        setLayout(new BorderLayout());
        setOpaque(false);
        initUI();
        loadSupplyData();
    }

    private void initUI() {
        JPanel managerPanel = new JPanel(new MigLayout("fill, insets 0", "[320!]25[grow, fill]", "[fill]"));
        managerPanel.setOpaque(false);

        // --- LEFT COLUMN: FORM ---
        JPanel formPanel = new JPanel(new MigLayout("wrap, fillx, insets 25", "[fill]"));
        formPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #2a2a2a");

        JLabel title = new JLabel("Prepare Supply");
        title.putClientProperty(FlatClientProperties.STYLE, "font: bold +4; foreground: #FFFFFF");
        formPanel.add(title, "gapbottom 20");

        // Components
        comboCrops = new JComboBox<>();
        loadCropsDropdown();

        txtQuantity = new JTextField();
        txtQuantity.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g. 500kg");

        dateChooser = new JDateChooser();
        dateChooser.setBackground(new Color(42, 42, 42));
        dateChooser.setForeground(Color.WHITE);

        formPanel.add(createFieldLabel("Select Crop"));
        formPanel.add(comboCrops, "h 40!");

        formPanel.add(createFieldLabel("Quantity"));
        formPanel.add(txtQuantity, "h 40!");

        formPanel.add(createFieldLabel("Supply Date"));
        formPanel.add(dateChooser, "h 40!");

        // Buttons
        btnPrepare = new JButton("Prepare Supply");
        btnPrepare.putClientProperty(FlatClientProperties.STYLE, "background: #2ecc71; foreground: #FFFFFF; arc: 15; font: bold");

        btnCancel = new JButton("Cancel / Clear");
        btnCancel.putClientProperty(FlatClientProperties.STYLE, "background: #444444; foreground: #FFFFFF; arc: 15");

        formPanel.add(btnPrepare, "gaptop 20, h 42!");
        formPanel.add(btnCancel, "h 38!");

        // --- RIGHT COLUMN: TABLE ---
        JPanel tableArea = new JPanel(new MigLayout("fill, insets 0", "[grow]", "[]15[grow]"));
        tableArea.setOpaque(false);

        JLabel tableTitle = new JLabel("Recent Supply Dispatches");
        tableTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold +2; foreground: #FFFFFF");
        tableArea.add(tableTitle, "wrap");

        model = new DefaultTableModel(new String[]{"Supply ID","Crop Name", "Quantity", "Date"}, 0);
        table = new JTable(model);
        table.setRowHeight(40);
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE, "background: #2a2a2a; foreground: #AAAAAA; font: bold");

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.getViewport().setBackground(new Color(30, 30, 30));
        tableArea.add(tableScroll, "grow");

        // --- EVENT LISTENERS ---
        btnPrepare.addActionListener(e -> saveSupply());
        btnCancel.addActionListener(e -> clearFields());

        managerPanel.add(new JScrollPane(formPanel), "growy");
        managerPanel.add(tableArea, "grow");
        add(managerPanel);
    }

    private void saveSupply() {
        if (txtQuantity.getText().isEmpty() || dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try (Connection conn = DBconnection.getConnection()) {
            String sql = "INSERT INTO supply_tbl (cropName, quantity, supplyDate) VALUES (?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, comboCrops.getSelectedItem().toString());
            pst.setString(2, txtQuantity.getText());

            // Convert JDateChooser date to SQL date
            java.util.Date utilDate = dateChooser.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pst.setDate(3, sqlDate);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Supply Prepared Successfully!");
            loadSupplyData();
            clearFields();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadSupplyData() {
        model.setRowCount(0);
        int count = 0;
        try (Connection c = DBconnection.getConnection();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM supply_tbl")) {
            while (r.next()) {
                model.addRow(new Object[]{
                        r.getInt(1),
                        r.getString(2),
                        r.getString(3),
                        r.getDate(4)
                });
                count++;
            }
            lblTotalSupplies.setText(String.valueOf(count));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadCropsDropdown() {
        try (Connection conn = DBconnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT cropName FROM crops_tbl")) {
            comboCrops.removeAllItems();
            while (rs.next()) comboCrops.addItem(rs.getString("cropName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtQuantity.setText("");
        dateChooser.setDate(null);
        table.clearSelection();
    }

    private JLabel createFieldLabel(String t) {
        JLabel l = new JLabel(t);
        l.putClientProperty(FlatClientProperties.STYLE, "foreground: #AAAAAA; font: -1");
        return l;
    }
}