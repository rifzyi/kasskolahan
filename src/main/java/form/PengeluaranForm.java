// File: form/PengeluaranForm.java
package form;

import com.github.lgooddatepicker.components.DatePicker;
import controller.PengeluaranController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.KategoriPengeluaran;
import model.Pengeluaran;
import util.UIUtils;

public class PengeluaranForm extends JPanel {
    private final PengeluaranController controller=new PengeluaranController(); private final DatePicker from=new DatePicker(), to=new DatePicker(); private final DefaultTableModel model=new DefaultTableModel(new Object[]{"ID","Tanggal","Kategori","Nominal","Keterangan"},0); private final JTable table=new JTable(model);
    public PengeluaranForm(){ setLayout(new BorderLayout(12,12)); setBackground(UIUtils.BACKGROUND); setBorder(javax.swing.BorderFactory.createEmptyBorder(18,18,18,18)); from.setDate(LocalDate.now().withDayOfMonth(1)); to.setDate(LocalDate.now()); JPanel bar=new JPanel(new FlowLayout(FlowLayout.LEFT)); bar.setOpaque(false); javax.swing.JButton apply=UIUtils.createPrimaryButton("Terapkan"), add=UIUtils.createSuccessButton("Tambah"), edit=UIUtils.createPrimaryButton("Edit"), del=UIUtils.createDangerButton("Hapus"); bar.add(from);bar.add(to);bar.add(apply);bar.add(add);bar.add(edit);bar.add(del); add(bar,BorderLayout.NORTH); UIUtils.styleTable(table); table.getColumnModel().getColumn(3).setCellRenderer(UIUtils.rightRenderer()); add(UIUtils.wrapTable(table),BorderLayout.CENTER); apply.addActionListener(e->refresh()); add.addActionListener(e->dialog(null)); edit.addActionListener(e->edit()); del.addActionListener(e->delete()); refresh(); }
    private void refresh(){ model.setRowCount(0); for(Pengeluaran p:controller.getAll(from.getDate(),to.getDate())) model.addRow(new Object[]{p.getIdPengeluaran(),p.getTanggal(),p.getNamaKategori(),UIUtils.formatRupiah(p.getNominal()),p.getKeterangan()}); }
    private JComboBox<KategoriPengeluaran> kategori(){ JComboBox<KategoriPengeluaran> cb=UIUtils.createModernComboBox(); for(KategoriPengeluaran k:controller.getKategori()) cb.addItem(k); cb.setRenderer((l,v,i,s,f)->new javax.swing.JLabel(v==null?"":v.getNamaKategori())); return cb; }
    private void dialog(Pengeluaran old){ DatePicker tgl=new DatePicker(); tgl.setDate(old==null?LocalDate.now():old.getTanggal()); JComboBox<KategoriPengeluaran> kat=kategori(); JTextField nominal=UIUtils.createModernTextField(16); JTextArea ket=UIUtils.createModernTextArea(3,16); JPanel p=new JPanel(new GridLayout(0,2,8,8)); p.add(new javax.swing.JLabel("Tanggal"));p.add(tgl);p.add(new javax.swing.JLabel("Kategori"));p.add(kat);p.add(new javax.swing.JLabel("Nominal"));p.add(nominal);p.add(new javax.swing.JLabel("Keterangan"));p.add(new javax.swing.JScrollPane(ket)); if(old!=null){nominal.setText(UIUtils.formatRupiah(old.getNominal()));ket.setText(old.getKeterangan());} if(javax.swing.JOptionPane.showConfirmDialog(this,p,"Form Pengeluaran",javax.swing.JOptionPane.OK_CANCEL_OPTION)==javax.swing.JOptionPane.OK_OPTION){ Pengeluaran pe=old==null?new Pengeluaran():old; pe.setTanggal(tgl.getDate()); pe.setIdKategoriPengeluaran(((KategoriPengeluaran)kat.getSelectedItem()).getIdKategoriPengeluaran()); pe.setNominal(UIUtils.parseRupiah(nominal.getText())); pe.setKeterangan(ket.getText()); if(old==null?controller.insert(pe):controller.update(pe))refresh(); } }
    private void edit(){ int r=table.getSelectedRow(); if(r<0)return; Pengeluaran p=new Pengeluaran(); p.setIdPengeluaran((int)model.getValueAt(r,0)); p.setTanggal((LocalDate)model.getValueAt(r,1)); dialog(p); }
    private void delete(){ int r=table.getSelectedRow(); if(r>=0&&UIUtils.showConfirm(this,"Hapus pengeluaran?")){controller.delete((int)model.getValueAt(r,0));refresh();} }
}
