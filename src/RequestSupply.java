import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import db.DBconnection;

public class RequestSupply extends JPanel {

    private JTable orderListTable;
    private DefaultTableModel model;
    private JComboBox<String> comboCrop;
    private JTextField txtQuantity;
    private JDateChooser deliveryDate;
    private JButton btnPlaceOrder, btnClear;
    private JLabel lblRequestCount;

    public RequestSupply(JLabel lblRequestCount) {
        this.lblRequestCount = lblRequestCount;
        setLayout(new BorderLayout());
        setOpaque(false);
        initUI();
        loadCropDropdown();
        loadOrderHistory();
    }

    private void initUI() {
        JPanel managerPanel = new JPanel(new MigLayout("fill, insets 0", "[320!]25[grow, fill]", "[fill]"));
        managerPanel.setOpaque(false);

        // --- LEFT COLUMN: REQUEST FORM ---
        JPanel formPanel = new JPanel(new MigLayout("wrap, fillx, insets 25", "[fill]"));
        formPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #2a2a2a");

        JLabel title = new JLabel("New Supply Request");
        title.putClientProperty(FlatClientProperties.STYLE, "font: bold +4; foreground: #FFFFFF");
        formPanel.add(title, "gapbottom 20");

        comboCrop = new JComboBox<>();
        txtQuantity = new JTextField();
        txtQuantity.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "e.g., 200kg or 50 Units");

        deliveryDate = new JDateChooser();
        deliveryDate.putClientProperty(FlatClientProperties.STYLE, "background: #1e1e1e; foreground: #FFFFFF");

        formPanel.add(createFieldLabel("Select Available Crop"));
        formPanel.add(comboCrop, "h 40!");

        formPanel.add(createFieldLabel("Required Quantity"));
        formPanel.add(txtQuantity, "h 40!");

        formPanel.add(createFieldLabel("Preferred Delivery Date"));
        formPanel.add(deliveryDate, "h 40!");

        btnPlaceOrder = new JButton("Place Order Request");
        btnPlaceOrder.putClientProperty(FlatClientProperties.STYLE, "background: #2ecc71; foreground: #FFFFFF; arc: 15; font: bold");

        btnClear = new JButton("Clear Form");
        btnClear.putClientProperty(FlatClientProperties.STYLE, "background: #444444; foreground: #FFFFFF; arc: 15");

        formPanel.add(btnPlaceOrder, "gaptop 20, h 42!");
        formPanel.add(btnClear, "h 38!");

        // --- RIGHT COLUMN: ORDER HISTORY ---
        JPanel tableArea = new JPanel(new MigLayout("fill, insets 0", "[grow]", "[]15[grow]"));
        tableArea.setOpaque(false);

        JLabel tableTitle = new JLabel("Your Recent Requests");
        tableTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold +2; foreground: #FFFFFF");

        model = new DefaultTableModel(new String[]{"Req ID", "Crop", "Qty", "Date", "Status"}, 0);
        orderListTable = new JTable(model);
        orderListTable.setRowHeight(40);

        JScrollPane tableScroll = new JScrollPane(orderListTable);
        tableScroll.getViewport().setBackground(new Color(30, 30, 30));

        tableArea.add(tableTitle, "wrap");
        tableArea.add(tableScroll, "grow");

        // --- EVENTS ---
        btnPlaceOrder.addActionListener(e -> placeOrder());
        btnClear.addActionListener(e -> clearFields());

        managerPanel.add(new JScrollPane(formPanel), "growy");
        managerPanel.add(tableArea, "grow");
        add(managerPanel);
    }

    private void loadCropDropdown() {
        try (Connection conn = DBconnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT cropName FROM crops_tbl")) {
            comboCrop.removeAllItems();
            while (rs.next()) comboCrop.addItem(rs.getString("cropName"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void placeOrder() {
        if (txtQuantity.getText().isEmpty() || deliveryDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please complete all fields!");
            return;
        }

        try (Connection conn = DBconnection.getConnection()) {
            String sql = "INSERT INTO buyer_requests_tbl (cropName, quantity, requestDate, status) VALUES (?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, comboCrop.getSelectedItem().toString());
            pst.setString(2, txtQuantity.getText());
            pst.setDate(3, new java.sql.Date(deliveryDate.getDate().getTime()));
            pst.setString(4, "Pending"); // Initial status

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Order Request Sent Successfully!");
            loadOrderHistory();
            clearFields();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadOrderHistory() {
        model.setRowCount(0);
        int count = 0;
        try (Connection c = DBconnection.getConnection();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM buyer_requests_tbl")) {
            while (r.next()) {
                model.addRow(new Object[]{r.getInt(1), r.getString(2), r.getString(3), r.getDate(4), r.getString(5)});
                count++;
            }
            lblRequestCount.setText(String.valueOf(count));
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void clearFields() {
        txtQuantity.setText("");
        deliveryDate.setDate(null);
    }

    private JLabel createFieldLabel(String t) {
        JLabel l = new JLabel(t);
        l.putClientProperty(FlatClientProperties.STYLE, "foreground: #AAAAAA; font: -1");
        return l;
    }
}