// File: form/DashboardForm.java
package form;

import controller.DashboardController;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import util.UIUtils;

public class DashboardForm extends JPanel {
    private final DashboardController controller = new DashboardController();
    private final JPanel cardPemasukan = UIUtils.createKPICard("Pemasukan Bulan Ini", "Rp 0", UIUtils.SUCCESS);
    private final JPanel cardPengeluaran = UIUtils.createKPICard("Pengeluaran Bulan Ini", "Rp 0", UIUtils.DANGER);
    private final JPanel cardSaldo = UIUtils.createKPICard("Saldo Keseluruhan", "Rp 0", UIUtils.ACCENT);
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Tanggal", "Jenis", "Kategori", "Nominal", "Keterangan"}, 0);

    public DashboardForm() {
        setLayout(new BorderLayout(12, 12)); setBackground(UIUtils.BACKGROUND); setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 18, 18, 18));
        JPanel cards = new JPanel(new GridLayout(1, 3, 12, 12)); cards.setOpaque(false); cards.add(cardPemasukan); cards.add(cardPengeluaran); cards.add(cardSaldo); add(cards, BorderLayout.NORTH);
        JTable table = new JTable(model); UIUtils.styleTable(table); table.getColumnModel().getColumn(3).setCellRenderer(UIUtils.rightRenderer()); add(UIUtils.wrapTable(table), BorderLayout.CENTER); refreshData();
    }

    public void refreshData() {
        LocalDate now = LocalDate.now(); UIUtils.setKPIValue(cardPemasukan, UIUtils.formatRupiah(controller.getTotalPemasukan(now.getMonthValue(), now.getYear()))); UIUtils.setKPIValue(cardPengeluaran, UIUtils.formatRupiah(controller.getTotalPengeluaran(now.getMonthValue(), now.getYear()))); UIUtils.setKPIValue(cardSaldo, UIUtils.formatRupiah(controller.getSaldoKeseluruhan()));
        model.setRowCount(0); for (Map<String, Object> r : controller.getRecentTransactions(10)) model.addRow(new Object[]{r.get("tanggal"), r.get("jenis"), r.get("nama_kategori"), UIUtils.formatRupiah((double) r.get("nominal")), r.get("keterangan")});
    }
}
