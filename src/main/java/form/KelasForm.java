// File: form/KelasForm.java
package form;

import controller.KelasController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Kelas;
import util.UIUtils;

public class KelasForm extends JPanel {
    private final KelasController controller = new KelasController();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Kode", "Nama Kelas"}, 0);
    private final JTable table = new JTable(model);

    public KelasForm() { setLayout(new BorderLayout(12, 12)); setBackground(UIUtils.BACKGROUND); setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 18, 18, 18)); JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT)); bar.setOpaque(false); javax.swing.JButton add = UIUtils.createSuccessButton("Tambah"), edit = UIUtils.createPrimaryButton("Edit"), del = UIUtils.createDangerButton("Hapus"); bar.add(add); bar.add(edit); bar.add(del); add(bar, BorderLayout.NORTH); add(UIUtils.wrapTable(table), BorderLayout.CENTER); add.addActionListener(e -> showDialog(null)); edit.addActionListener(e -> edit()); del.addActionListener(e -> delete()); refresh(); }
    private void refresh() { model.setRowCount(0); for (Kelas k : controller.getAll()) model.addRow(new Object[]{k.getIdKelas(), k.getKodeKelas(), k.getNamaKelas()}); }
    private void showDialog(Kelas old) { String kode = JOptionPane.showInputDialog(this, "Kode kelas", old == null ? "" : old.getKodeKelas()); if (kode == null) return; String nama = JOptionPane.showInputDialog(this, "Nama kelas", old == null ? "" : old.getNamaKelas()); if (nama == null) return; Kelas k = old == null ? new Kelas() : old; k.setKodeKelas(kode); k.setNamaKelas(nama); if (old == null ? controller.insert(k) : controller.update(k)) refresh(); }
    private void edit() { int r = table.getSelectedRow(); if (r < 0) return; Kelas k = new Kelas(); k.setIdKelas((int) model.getValueAt(r, 0)); k.setKodeKelas(String.valueOf(model.getValueAt(r, 1))); k.setNamaKelas(String.valueOf(model.getValueAt(r, 2))); showDialog(k); }
    private void delete() { int r = table.getSelectedRow(); if (r >= 0 && UIUtils.showConfirm(this, "Hapus kelas?")) { if (!controller.delete((int) model.getValueAt(r, 0))) UIUtils.showError(this, "Kelas masih memiliki siswa aktif atau gagal dihapus"); refresh(); } }
}
