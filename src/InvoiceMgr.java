import com.formdev.flatlaf.FlatClientProperties;
import db.DBconnection;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;


// JasperReports imports
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class InvoiceMgr extends JPanel {

    private JTable invoiceTable;
    private DefaultTableModel model;
    private JLabel lblTotalAmount;
    private JButton btnDownloadInvoice, btnRefresh;

    public InvoiceMgr() {
        setLayout(new BorderLayout());
        setOpaque(false);
        initUI();
        loadInvoices();
    }

    private void initUI() {
        JPanel managerPanel = new JPanel(new MigLayout("fill, insets 0", "[300!]25[grow, fill]", "[fill]"));
        managerPanel.setOpaque(false);

        // --- LEFT COLUMN ---
        JPanel summaryPanel = new JPanel(new MigLayout("wrap, fillx, insets 25", "[fill]"));
        summaryPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 25; background: #2a2a2a");

        JLabel title = new JLabel("Invoice Center");
        title.putClientProperty(FlatClientProperties.STYLE, "font: bold +4; foreground: #FFFFFF");
        summaryPanel.add(title, "gapbottom 20");

        // Amount Card
        JPanel amountCard = new JPanel(new MigLayout("wrap, insets 15", "[fill]"));
        amountCard.putClientProperty(FlatClientProperties.STYLE, "arc: 20; background: #1e1e1e");

        JLabel lblLabel = new JLabel("ACCUMULATED TOTAL");
        lblLabel.setForeground(new Color(150, 150, 150));

        lblTotalAmount = new JLabel("LKR 0.00");
        lblTotalAmount.putClientProperty(FlatClientProperties.STYLE, "font: bold +12; foreground: #2ecc71");

        amountCard.add(lblLabel);
        amountCard.add(lblTotalAmount);
        summaryPanel.add(amountCard, "h 100!, gapbottom 20");

        btnDownloadInvoice = new JButton("Generate Invoice PDF");
        btnDownloadInvoice.putClientProperty(FlatClientProperties.STYLE, "background: #3498db; foreground: #FFFFFF; arc: 15; font: bold");

        btnRefresh = new JButton("Refresh Records");
        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "background: #34495e; foreground: #FFFFFF; arc: 15");

        summaryPanel.add(btnDownloadInvoice, "h 45!");
        summaryPanel.add(btnRefresh, "h 40!");

        // Formula Legend
        JLabel formulaDetails = new JLabel("<html><body style='width: 180px; color: #888888; font-size: 9px;'>" +
                "<hr><br><b>Billing Logic:</b><br>" +
                "Total = (Qty × 1000) + 200 - Discount<br><br>" +
                "<b>Discount Tiers:</b><br>" +
                "• Over 200,000 &nbsp;&rarr; 40%<br>" +
                "• Over 100,000 &nbsp;&rarr; 25%<br>" +
                "• Over 50,000 &nbsp;&nbsp;&rarr; 10%</body></html>");
        summaryPanel.add(formulaDetails, "pushy, bottom");

        // --- RIGHT COLUMN ---
        JPanel tableArea = new JPanel(new MigLayout("fill, insets 0", "[grow]", "[]15[grow]"));
        tableArea.setOpaque(false);

        JLabel tableTitle = new JLabel("Financial Statement");
        tableTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold +2; foreground: #FFFFFF");

        model = new DefaultTableModel(new String[]{"ID", "Crop", "Quantity", "Base Subtotal", "Discount", "Net Amount"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        invoiceTable = new JTable(model);
        invoiceTable.setRowHeight(40);
        JScrollPane tableScroll = new JScrollPane(invoiceTable);
        tableScroll.getViewport().setBackground(new Color(30, 30, 30));

        tableArea.add(tableTitle, "wrap");
        tableArea.add(tableScroll, "grow");

        // --- EVENTS ---
        btnRefresh.addActionListener(e -> loadInvoices());
        btnDownloadInvoice.addActionListener(e -> generateJasperReport());

        managerPanel.add(summaryPanel, "growy");
        managerPanel.add(tableArea, "grow");
        add(managerPanel);
    }

    private void loadInvoices() {
        model.setRowCount(0);
        double cumulativeNetTotal = 0;

        String sql = "SELECT requestId, cropName, quantity FROM buyer_requests_tbl";

        try (Connection c = DBconnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("requestId");
                String crop = rs.getString("cropName");
                double qty = rs.getDouble("quantity");

                double subtotal = (qty * 1000) + 200;
                double discountRate = 0;

                if (subtotal >= 200000) discountRate = 0.40;
                else if (subtotal >= 100000) discountRate = 0.25;
                else if (subtotal >= 50000) discountRate = 0.10;

                double discountAmt = subtotal * discountRate;
                double netTotal = subtotal - discountAmt;

                model.addRow(new Object[]{
                        "INV-" + id,
                        crop,
                        qty + " Units",
                        String.format("%,.2f", subtotal),
                        String.format("%,.2f (%d%%)", discountAmt, (int)(discountRate*100)),
                        String.format("%,.2f", netTotal)
                });

                cumulativeNetTotal += netTotal;
            }
            lblTotalAmount.setText("LKR " + String.format("%,.2f", cumulativeNetTotal));

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Calculation Error: " + ex.getMessage());
        }
    }

    private void generateJasperReport() {
        int selectedRow = invoiceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record from the table first.");
            return;
        }

        int reqId = Integer.parseInt(model.getValueAt(selectedRow, 0).toString().replace("INV-", ""));
        String crop = model.getValueAt(selectedRow, 1).toString();
        String qty = model.getValueAt(selectedRow, 2).toString();
        double subtotal = Double.parseDouble(model.getValueAt(selectedRow, 3).toString().replace(",", ""));
        String discountStr = model.getValueAt(selectedRow, 4).toString().split(" ")[0].replace(",", "");
        double discount = Double.parseDouble(discountStr);
        double netTotal = Double.parseDouble(model.getValueAt(selectedRow, 5).toString().replace(",", ""));

        try (Connection conn = DBconnection.getConnection()) {
            String insertSQL = "INSERT INTO invoice_tbl (requestId, description, qty, subtotal, discount, netTotal, invDate) VALUES (?, ?, ?, ?, ?, ?, CURDATE())";
            PreparedStatement pst = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, reqId);
            pst.setString(2, "Purchase of " + crop);
            pst.setString(3, qty);
            pst.setDouble(4, subtotal);
            pst.setDouble(5, discount);
            pst.setDouble(6, netTotal);

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            int newInvoiceId = 0;
            if (rs.next()) newInvoiceId = rs.getInt(1);

            // --- Generate JasperReport ---
            String jasperPath = "/reports/buyer_invoice.jasper";
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(jasperPath));

            Map<String, Object> params = new HashMap<>();
            params.put("INV_ID", newInvoiceId);

            JasperPrint jp = JasperFillManager.fillReport(jr, params, conn);
// 1. Define output folder and filename
            String outputDir = "C:\\Users\\SANDANIMNE\\Desktop\\EAD fnl\\reports\\"; // your folder
            new java.io.File(outputDir).mkdirs(); // create folder if it doesn't exist
            String outputFile = outputDir + "\\Invoice_" + newInvoiceId + ".pdf";

// 2. Export PDF
            JasperExportManager.exportReportToPdfFile(jp, outputFile);
            JOptionPane.showMessageDialog(this, "Invoice saved to database and generated!");
// 3. Optionally open the PDF automatically
            Desktop.getDesktop().open(new java.io.File(outputFile));

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing invoice: " + ex.getMessage());
        }
    }
}
