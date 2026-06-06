// File: form/SiswaForm.java
package form;

import controller.KelasController;
import controller.SiswaController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Kelas;
import model.Siswa;
import util.Option;
import util.UIUtils;

public class SiswaForm extends JPanel {
    private final SiswaController controller = new SiswaController(); private final KelasController kelasController = new KelasController();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "NIS", "Nama", "Kelas", "JK"}, 0); private final JTable table = new JTable(model); private final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    public SiswaForm() { setLayout(new BorderLayout(12,12)); setBackground(UIUtils.BACKGROUND); setBorder(javax.swing.BorderFactory.createEmptyBorder(18,18,18,18)); table.setRowSorter(sorter); JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT)); bar.setOpaque(false); JTextField search = UIUtils.createModernTextField(20); javax.swing.JButton add=UIUtils.createSuccessButton("Tambah"), edit=UIUtils.createPrimaryButton("Edit"), del=UIUtils.createDangerButton("Hapus"); bar.add(search); bar.add(add); bar.add(edit); bar.add(del); add(bar, BorderLayout.NORTH); add(UIUtils.wrapTable(table), BorderLayout.CENTER); search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){ public void insertUpdate(javax.swing.event.DocumentEvent e){filter(search.getText());} public void removeUpdate(javax.swing.event.DocumentEvent e){filter(search.getText());} public void changedUpdate(javax.swing.event.DocumentEvent e){filter(search.getText());}}); add.addActionListener(e->dialog(null)); edit.addActionListener(e->edit()); del.addActionListener(e->delete()); refresh(); }
    private void filter(String q){ sorter.setRowFilter(q.isBlank()?null: RowFilter.regexFilter("(?i)"+java.util.regex.Pattern.quote(q))); }
    private void refresh(){ model.setRowCount(0); for(Siswa s:controller.getAll()) model.addRow(new Object[]{s.getIdSiswa(),s.getNis(),s.getNamaSiswa(),s.getNamaKelas(),s.getJenisKelamin()}); }
    private JComboBox<Option> kelasCombo(){ JComboBox<Option> cb=UIUtils.createModernComboBox(); cb.addItem(new Option(0,"- Tanpa Kelas -")); for(Kelas k:kelasController.getAll()) cb.addItem(new Option(k.getIdKelas(),k.getNamaKelas())); return cb; }
    private void dialog(Siswa old){ JTextField nis=UIUtils.createModernTextField(18), nama=UIUtils.createModernTextField(18); JComboBox<Option> kelas=kelasCombo(); JComboBox<String> jk=UIUtils.createModernComboBox(); jk.addItem("L"); jk.addItem("P"); JTextArea alamat=UIUtils.createModernTextArea(3,18); if(old!=null){nis.setText(old.getNis()); nama.setText(old.getNamaSiswa()); jk.setSelectedItem(old.getJenisKelamin()); alamat.setText(old.getAlamat()); select(kelas, old.getIdKelas()==null?0:old.getIdKelas());} JPanel p=new JPanel(new GridLayout(0,2,8,8)); p.add(new javax.swing.JLabel("NIS"));p.add(nis);p.add(new javax.swing.JLabel("Nama"));p.add(nama);p.add(new javax.swing.JLabel("Kelas"));p.add(kelas);p.add(new javax.swing.JLabel("JK"));p.add(jk);p.add(new javax.swing.JLabel("Alamat"));p.add(new javax.swing.JScrollPane(alamat)); if(javax.swing.JOptionPane.showConfirmDialog(this,p,"Form Siswa",javax.swing.JOptionPane.OK_CANCEL_OPTION)==javax.swing.JOptionPane.OK_OPTION){ Siswa s=old==null?new Siswa():old; s.setNis(nis.getText());s.setNamaSiswa(nama.getText());s.setIdKelas(((Option)kelas.getSelectedItem()).getId());s.setJenisKelamin(String.valueOf(jk.getSelectedItem()));s.setAlamat(alamat.getText()); if(old==null?controller.insert(s):controller.update(s)) refresh(); } }
    private void select(JComboBox<Option> cb,int id){ for(int i=0;i<cb.getItemCount();i++) if(cb.getItemAt(i).getId()==id) cb.setSelectedIndex(i); }
    private void edit(){ int r=table.getSelectedRow(); if(r<0)return; int m=table.convertRowIndexToModel(r); Siswa old=controller.getAll().stream().filter(s->s.getIdSiswa()==(int)model.getValueAt(m,0)).findFirst().orElse(null); if(old!=null) dialog(old); }
    private void delete(){ int r=table.getSelectedRow(); if(r>=0&&UIUtils.showConfirm(this,"Hapus siswa?")){ controller.delete((int)model.getValueAt(table.convertRowIndexToModel(r),0)); refresh(); } }
}
