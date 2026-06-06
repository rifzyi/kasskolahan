// File: form/TabunganForm.java
package form;

import controller.SiswaController;
import controller.TabunganController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Siswa;
import model.Tabungan;
import util.Option;
import util.UIUtils;

public class TabunganForm extends JPanel { private final TabunganController controller=new TabunganController(); private final JComboBox<Option> siswa=UIUtils.createModernComboBox(); private final JLabel saldo=new JLabel("Saldo: Rp 0"); private final JRadioButton setor=new JRadioButton("Setor",true), tarik=new JRadioButton("Tarik"); private final JTextField nominal=UIUtils.createModernTextField(14); private final JTextArea ket=UIUtils.createModernTextArea(2,18); private final DefaultTableModel model=new DefaultTableModel(new Object[]{"Tanggal","Jenis","Nominal","Saldo Setelah","Keterangan"},0); private final JTable table=new JTable(model);
    public TabunganForm(){ setLayout(new BorderLayout(12,12)); setBackground(UIUtils.BACKGROUND); setBorder(javax.swing.BorderFactory.createEmptyBorder(18,18,18,18)); JPanel top=new JPanel(new FlowLayout(FlowLayout.LEFT)); top.setOpaque(false); top.add(new JLabel("Siswa")); top.add(siswa); top.add(saldo); add(top,BorderLayout.NORTH); JPanel form=new JPanel(new FlowLayout(FlowLayout.LEFT)); form.setOpaque(false); ButtonGroup bg=new ButtonGroup(); bg.add(setor); bg.add(tarik); form.add(setor);form.add(tarik);form.add(new JLabel("Nominal"));form.add(nominal);form.add(new JLabel("Keterangan"));form.add(new javax.swing.JScrollPane(ket)); javax.swing.JButton proses=UIUtils.createSuccessButton("Proses"); form.add(proses); add(form,BorderLayout.CENTER); UIUtils.styleTable(table); table.getColumnModel().getColumn(2).setCellRenderer(UIUtils.rightRenderer()); table.getColumnModel().getColumn(3).setCellRenderer(UIUtils.rightRenderer()); add(UIUtils.wrapTable(table),BorderLayout.SOUTH); loadSiswa(); siswa.addActionListener(e->refresh()); proses.addActionListener(e->process()); }
    private void loadSiswa(){ siswa.removeAllItems(); for(Siswa s:new SiswaController().getAll()) siswa.addItem(new Option(s.getIdSiswa(),s.getNamaSiswa())); refresh(); }
    private int selectedId(){ return siswa.getSelectedItem()==null?0:((Option)siswa.getSelectedItem()).getId(); }
    private void refresh(){ int id=selectedId(); double s=controller.getSaldoAktif(id); saldo.setText("Saldo: "+UIUtils.formatRupiah(s)); model.setRowCount(0); if(id>0) for(Tabungan t:controller.getMutasiBySiswa(id)) model.addRow(new Object[]{t.getTanggal(),t.getJenis(),UIUtils.formatRupiah(t.getNominal()),UIUtils.formatRupiah(t.getSaldoSetelah()),t.getKeterangan()}); }
    private void process(){ int id=selectedId(); if(id<=0)return; double n=UIUtils.parseRupiah(nominal.getText()); double aktif=controller.getSaldoAktif(id); if(tarik.isSelected()&&n>aktif){ UIUtils.showError(this,"Saldo tidak cukup!\nSaldo aktif: "+UIUtils.formatRupiah(aktif)); return;} Tabungan t=new Tabungan(); t.setIdSiswa(id); t.setTanggal(LocalDate.now()); t.setNominal(n); t.setKeterangan(ket.getText()); boolean ok=setor.isSelected()?controller.setor(t):controller.tarik(t); if(ok){ UIUtils.showSuccess(this,"Transaksi berhasil"); nominal.setText(""); ket.setText(""); refresh(); } }
}
