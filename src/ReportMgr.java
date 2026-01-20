import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import db.DBconnection;

public class ReportMgr extends JPanel {

    private JTable reportTable;
    private DefaultTableModel model;
    private JComboBox<String> comboReportType;
    private JButton btnViewData, btnGenerateJasper;

    public ReportMgr() {
        setLayout(new BorderLayout());
        setOpaque(false);
        initUI();
    }

    // ================= UI =================

    private void initUI() {

        JPanel managerPanel = new JPanel(
                new MigLayout("fill, insets 0", "[320!]25[grow,fill]", "[fill]")
        );
        managerPanel.setOpaque(false);

        // -------- LEFT PANEL --------
        JPanel controlPanel = new JPanel(new MigLayout("wrap, fillx, insets 25", "[fill]"));
        controlPanel.putClientProperty(
                FlatClientProperties.STYLE,
                "arc: 25; background: #2a2a2a"
        );

        JLabel title = new JLabel("Jasper Reports");
        title.putClientProperty(
                FlatClientProperties.STYLE,
                "font: bold +4; foreground: #FFFFFF"
        );

        comboReportType = new JComboBox<>(new String[]{
                "Select Report",
                "Farmer",
                "Crop",
                "Supply"
        });

        btnViewData = new JButton("Preview Data Table");
        btnViewData.putClientProperty(
                FlatClientProperties.STYLE,
                "background: #34495e; foreground: #FFFFFF; arc: 15"
        );

        btnGenerateJasper = new JButton("Generate Jasper Report");
        btnGenerateJasper.putClientProperty(
                FlatClientProperties.STYLE,
                "background: #2ecc71; foreground: #FFFFFF; arc: 15; font: bold"
        );

        controlPanel.add(title, "gapbottom 20");
        controlPanel.add(label("Report Type"));
        controlPanel.add(comboReportType, "h 40!");
        controlPanel.add(btnViewData, "gaptop 20, h 40!");
        controlPanel.add(btnGenerateJasper, "h 45!");

        // -------- RIGHT PANEL --------
        JPanel tableArea = new JPanel(
                new MigLayout("fillx, insets 0", "[grow,fill]", "[]15[grow]")
        );
        tableArea.setOpaque(false);

        JLabel tableTitle = new JLabel("Live Data Preview");
        tableTitle.putClientProperty(
                FlatClientProperties.STYLE,
                "font: bold +2; foreground: #FFFFFF"
        );

        model = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        reportTable = new JTable(model);
        reportTable.setRowHeight(35);

        JScrollPane tableScroll = new JScrollPane(reportTable);

        tableArea.add(tableTitle, "wrap");
        tableArea.add(tableScroll, "grow");

        btnViewData.addActionListener(e -> previewInTable());
        btnGenerateJasper.addActionListener(e -> generateReport());

        managerPanel.add(controlPanel, "growy");
        managerPanel.add(tableArea, "grow");

        add(managerPanel);
    }

    // ================= PREVIEW =================

    private void previewInTable() {

        String selection = comboReportType.getSelectedItem().toString();
        if (selection.equals("Select Report")) return;

        String query;
        if (selection.equals("Farmer"))
            query = "SELECT fId, fullName, nic, phone, district FROM farmer_tbl";
        else if (selection.equals("Crop"))
            query = "SELECT * FROM crops_tbl";
        else
            query = "SELECT * FROM supply_tbl";

        try (Connection c = DBconnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(query)) {

            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();

            model.setColumnCount(0);
            model.setRowCount(0);

            for (int i = 1; i <= cols; i++) {
                model.addColumn(meta.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Preview Error:\n" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // ================= JASPER =================

    private void generateReport() {

        String selection = comboReportType.getSelectedItem().toString();
        if (selection.equals("Select Report")) {
            JOptionPane.showMessageDialog(this, "Please select a report type!");
            return;
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try (Connection conn = DBconnection.getConnection()) {

            String reportPath;
            if (selection.equals("Farmer"))
                reportPath = "/reports/farmerRpo.jasper";
            else if (selection.equals("Crop"))
                reportPath = "/reports/cropsRpo.jasper";
            else
                reportPath = "/reports/inventoryRpo.jasper";

            // -------- LOAD REPORT SAFELY --------
            InputStream is = getClass().getResourceAsStream(reportPath);
            if (is == null) {
                throw new RuntimeException("Report file not found: " + reportPath);
            }

            JasperReport jr = (JasperReport) JRLoader.loadObject(is);

            // -------- PARAMETERS --------
            Map<String, Object> params = new HashMap<>();
            params.put("printedBy", "SmartCrop Officer System");

            // OPTIONAL LOGO (safe)
            ImageIcon logo = new ImageIcon("C:/Users/SANDANIMNE/Desktop/EAD fnl/logo.png");
            params.put("logo", logo.getImage());
            if (logo != null) {
                params.put("logo", logo);
            }

            JasperPrint jp = JasperFillManager.fillReport(jr, params, conn);
// 1. Define output folder and filename
            String outputDir = "C:\\Users\\SANDANIMNE\\Desktop\\EAD fnl\\reports\\"; // your folder
            new java.io.File(outputDir).mkdirs(); // create folder if it doesn't exist
            String outputFile = outputDir + "\\Invoice_" + selection + ".pdf";

// 2. Export PDF
            JasperExportManager.exportReportToPdfFile(jp, outputFile);
            JOptionPane.showMessageDialog(this, "Invoice saved to database and generated!");
// 3. Optionally open the PDF automatically
            Desktop.getDesktop().open(new java.io.File(outputFile));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Report Error:\n" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    // ================= HELPERS =================

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.putClientProperty(
                FlatClientProperties.STYLE,
                "foreground: #AAAAAA"
        );
        return l;
    }
}
