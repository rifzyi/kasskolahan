// File: form/PemasukanForm.java
package form;

import com.github.lgooddatepicker.components.DatePicker;
import controller.PemasukanController;
import controller.SiswaController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.YearMonth;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.KategoriPemasukan;
import model.Pemasukan;
import model.Siswa;
import util.Option;
import util.UIUtils;

public class PemasukanForm extends JPanel {
    private final PemasukanController controller = new PemasukanController(); private final SiswaController siswaController = new SiswaController(); private final DatePicker from = new DatePicker(), to = new DatePicker();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Tanggal","Siswa","Kategori","Nominal","Keterangan"},0); private final JTable table = new JTable(model);
    public PemasukanForm(){ setLayout(new BorderLayout(12,12)); setBackground(UIUtils.BACKGROUND); setBorder(javax.swing.BorderFactory.createEmptyBorder(18,18,18,18)); from.setDate(LocalDate.now().withDayOfMonth(1)); to.setDate(LocalDate.now()); JPanel bar=new JPanel(new FlowLayout(FlowLayout.LEFT)); bar.setOpaque(false); javax.swing.JButton apply=UIUtils.createPrimaryButton("Terapkan"), add=UIUtils.createSuccessButton("Tambah"), edit=UIUtils.createPrimaryButton("Edit"), del=UIUtils.createDangerButton("Hapus"); bar.add(from);bar.add(to);bar.add(apply);bar.add(add);bar.add(edit);bar.add(del); add(bar,BorderLayout.NORTH); UIUtils.styleTable(table); table.getColumnModel().getColumn(4).setCellRenderer(UIUtils.rightRenderer()); add(UIUtils.wrapTable(table),BorderLayout.CENTER); apply.addActionListener(e->refresh()); add.addActionListener(e->dialog(null)); edit.addActionListener(e->edit()); del.addActionListener(e->delete()); refresh(); }
    private void refresh(){ model.setRowCount(0); for(Pemasukan p:controller.getAll(from.getDate(),to.getDate())) model.addRow(new Object[]{p.getIdPemasukan(),p.getTanggal(),p.getNamaSiswa(),p.getNamaKategori(),UIUtils.formatRupiah(p.getNominal()),p.getKeterangan()}); }
    private JComboBox<Option> siswaCombo(){ JComboBox<Option> cb=UIUtils.createModernComboBox(); cb.addItem(new Option(0,"- Pilih Siswa -")); for(Siswa s:siswaController.getAll()) cb.addItem(new Option(s.getIdSiswa(),s.getNamaSiswa())); return cb; }
    private JComboBox<KategoriPemasukan> kategoriCombo(){ JComboBox<KategoriPemasukan> cb=UIUtils.createModernComboBox(); for(KategoriPemasukan k:controller.getKategori()) cb.addItem(k); cb.setRenderer((list,value,index,selected,focus)->new javax.swing.JLabel(value==null?"":value.getNamaKategori())); return cb; }
    private void dialog(Pemasukan old){ DatePicker tgl=new DatePicker(); tgl.setDate(old==null?LocalDate.now():old.getTanggal()); JComboBox<Option> siswa=siswaCombo(); JComboBox<KategoriPemasukan> kat=kategoriCombo(); JTextField nominal=UIUtils.createModernTextField(16); JTextArea ket=UIUtils.createModernTextArea(3,16); JComboBox<String> bulan=UIUtils.createModernComboBox(); for(int i=0;i<12;i++) bulan.addItem(YearMonth.now().minusMonths(i).toString()); JPanel p=new JPanel(new GridLayout(0,2,8,8)); p.add(new javax.swing.JLabel("Tanggal"));p.add(tgl);p.add(new javax.swing.JLabel("Kategori"));p.add(kat);p.add(new javax.swing.JLabel("Siswa"));p.add(siswa);p.add(new javax.swing.JLabel("Bulan SPP"));p.add(bulan);p.add(new javax.swing.JLabel("Nominal"));p.add(nominal);p.add(new javax.swing.JLabel("Keterangan"));p.add(new javax.swing.JScrollPane(ket)); java.awt.event.ActionListener l=e->{ KategoriPemasukan k=(KategoriPemasukan)kat.getSelectedItem(); boolean spp=k!=null&&k.isIsSpp(); siswa.setEnabled(spp); bulan.setEnabled(spp); if(k!=null&&spp) nominal.setText(UIUtils.formatRupiah(k.getNominalDefault()));}; kat.addActionListener(l); l.actionPerformed(null); if(old!=null){nominal.setText(UIUtils.formatRupiah(old.getNominal()));ket.setText(old.getKeterangan());} if(javax.swing.JOptionPane.showConfirmDialog(this,p,"Form Pemasukan",javax.swing.JOptionPane.OK_CANCEL_OPTION)==javax.swing.JOptionPane.OK_OPTION){ KategoriPemasukan k=(KategoriPemasukan)kat.getSelectedItem(); Pemasukan pm=old==null?new Pemasukan():old; pm.setTanggal(tgl.getDate()); pm.setIdKategoriPemasukan(k.getIdKategoriPemasukan()); pm.setIdSiswa(k.isIsSpp()?((Option)siswa.getSelectedItem()).getId():null); pm.setBulanSpp(k.isIsSpp()?String.valueOf(bulan.getSelectedItem()):null); pm.setNominal(UIUtils.parseRupiah(nominal.getText())); pm.setKeterangan(ket.getText()); if(old==null?controller.insert(pm):controller.update(pm)) refresh(); } }
    private void edit(){ int r=table.getSelectedRow(); if(r<0)return; Pemasukan p=new Pemasukan(); p.setIdPemasukan((int)model.getValueAt(r,0)); p.setTanggal((LocalDate)model.getValueAt(r,1)); dialog(p); }
    private void delete(){ int r=table.getSelectedRow(); if(r>=0&&UIUtils.showConfirm(this,"Hapus pemasukan?")){controller.delete((int)model.getValueAt(r,0));refresh();} }
}
