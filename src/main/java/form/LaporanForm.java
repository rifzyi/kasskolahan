// File: form/LaporanForm.java
package form;

import com.github.lgooddatepicker.components.DatePicker;
import controller.LaporanController;
import controller.PengaturanController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import util.UIUtils;

public class LaporanForm extends JPanel { private final LaporanController controller=new LaporanController(); private final DatePicker from=new DatePicker(), to=new DatePicker(); private final DefaultTableModel model=new DefaultTableModel(new Object[]{"Tanggal","Jenis","Kategori","Nominal","Keterangan"},0); private final JTable table=new JTable(model); private final JLabel footer=new JLabel("Total: Rp 0"); private List<Map<String,Object>> data=new ArrayList<>();
    public LaporanForm(){ setLayout(new BorderLayout(12,12)); setBackground(UIUtils.BACKGROUND); setBorder(javax.swing.BorderFactory.createEmptyBorder(18,18,18,18)); from.setDate(LocalDate.now().withDayOfMonth(1)); to.setDate(LocalDate.now()); JPanel bar=new JPanel(new FlowLayout(FlowLayout.LEFT)); bar.setOpaque(false); javax.swing.JButton show=UIUtils.createPrimaryButton("Tampilkan"), csv=UIUtils.createSuccessButton("Export CSV"), pdf=UIUtils.createDangerButton("Export PDF"); bar.add(from);bar.add(to);bar.add(show);bar.add(csv);bar.add(pdf); add(bar,BorderLayout.NORTH); UIUtils.styleTable(table); table.getColumnModel().getColumn(3).setCellRenderer(UIUtils.rightRenderer()); add(UIUtils.wrapTable(table),BorderLayout.CENTER); add(footer,BorderLayout.SOUTH); show.addActionListener(e->refresh()); csv.addActionListener(e->exportCsv()); pdf.addActionListener(e->exportPdf()); refresh(); }
    private void refresh(){ data=controller.getLaporan(from.getDate(),to.getDate()); model.setRowCount(0); double masuk=0,keluar=0; for(Map<String,Object> r:data){ double n=(double)r.get("nominal"); if("Pemasukan".equals(r.get("jenis"))) masuk+=n; else keluar+=n; model.addRow(new Object[]{r.get("tanggal"),r.get("jenis"),r.get("kategori"),UIUtils.formatRupiah(n),r.get("keterangan")}); } footer.setText("Total Pemasukan: "+UIUtils.formatRupiah(masuk)+" | Total Pengeluaran: "+UIUtils.formatRupiah(keluar)+" | Saldo: "+UIUtils.formatRupiah(masuk-keluar)); }
    private void exportCsv(){ JFileChooser fc=new JFileChooser(); if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION) try{ controller.exportCSV(data, ensure(fc.getSelectedFile(),".csv")); UIUtils.showSuccess(this,"CSV berhasil diexport"); }catch(Exception e){ UIUtils.showError(this,e.getMessage()); } }
    private void exportPdf(){ JFileChooser fc=new JFileChooser(); if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION) try{ controller.exportPDF(data, ensure(fc.getSelectedFile(),".pdf"), new PengaturanController().getPengaturan()); UIUtils.showSuccess(this,"PDF berhasil diexport"); }catch(Exception e){ UIUtils.showError(this,e.getMessage()); } }
    private File ensure(File f,String ext){ return f.getName().toLowerCase().endsWith(ext)?f:new File(f.getAbsolutePath()+ext); }
}
