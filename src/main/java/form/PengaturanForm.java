// File: form/PengaturanForm.java
package form;

import controller.PengaturanController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Pengaturan;
import model.User;
import util.UIUtils;

public class PengaturanForm extends JPanel { private final PengaturanController controller=new PengaturanController(); private Pengaturan setting; private final JTextField nama=UIUtils.createModernTextField(20), telepon=UIUtils.createModernTextField(20), kepala=UIUtils.createModernTextField(20), bendahara=UIUtils.createModernTextField(20), logo=UIUtils.createModernTextField(20); private final JTextArea alamat=UIUtils.createModernTextArea(4,20); private final DefaultTableModel userModel=new DefaultTableModel(new Object[]{"ID","Nama","Username","Role"},0); private final JTable userTable=new JTable(userModel);
    public PengaturanForm(){ setLayout(new BorderLayout()); setBackground(UIUtils.BACKGROUND); setBorder(javax.swing.BorderFactory.createEmptyBorder(18,18,18,18)); JTabbedPane tabs=new JTabbedPane(); tabs.addTab("Profil Sekolah", profil()); tabs.addTab("Manajemen User", users()); add(tabs); loadSetting(); refreshUsers(); }
    private JPanel profil(){ JPanel p=new JPanel(new BorderLayout(8,8)); p.setOpaque(false); JPanel f=new JPanel(new GridLayout(0,2,8,8)); f.setOpaque(false); javax.swing.JButton browse=UIUtils.createSecondaryButton("Upload Logo"), save=UIUtils.createSuccessButton("Simpan Profil"); f.add(new JLabel("Nama Sekolah"));f.add(nama);f.add(new JLabel("Alamat"));f.add(new javax.swing.JScrollPane(alamat));f.add(new JLabel("Telepon"));f.add(telepon);f.add(new JLabel("Kepala Sekolah"));f.add(kepala);f.add(new JLabel("Bendahara"));f.add(bendahara);f.add(new JLabel("Logo Path"));f.add(logo); f.add(browse);f.add(save); p.add(f,BorderLayout.NORTH); browse.addActionListener(e->{JFileChooser fc=new JFileChooser(); if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) logo.setText(fc.getSelectedFile().getAbsolutePath());}); save.addActionListener(e->saveSetting()); return p; }
    private JPanel users(){ JPanel p=new JPanel(new BorderLayout(8,8)); p.setOpaque(false); JPanel bar=new JPanel(new FlowLayout(FlowLayout.LEFT)); bar.setOpaque(false); javax.swing.JButton add=UIUtils.createSuccessButton("Tambah"), edit=UIUtils.createPrimaryButton("Edit"), del=UIUtils.createDangerButton("Hapus"); bar.add(add);bar.add(edit);bar.add(del); p.add(bar,BorderLayout.NORTH); p.add(UIUtils.wrapTable(userTable),BorderLayout.CENTER); add.addActionListener(e->userDialog(null)); edit.addActionListener(e->editUser()); del.addActionListener(e->deleteUser()); return p; }
    private void loadSetting(){ setting=controller.getPengaturan(); nama.setText(setting.getNamaSekolah()); alamat.setText(setting.getAlamat()); telepon.setText(setting.getTelepon()); kepala.setText(setting.getKepalaSekolah()); bendahara.setText(setting.getBendahara()); logo.setText(setting.getLogoPath()); }
    private void saveSetting(){ setting.setNamaSekolah(nama.getText()); setting.setAlamat(alamat.getText()); setting.setTelepon(telepon.getText()); setting.setKepalaSekolah(kepala.getText()); setting.setBendahara(bendahara.getText()); setting.setLogoPath(logo.getText()); if(controller.savePengaturan(setting)) UIUtils.showSuccess(this,"Pengaturan tersimpan"); }
    private void refreshUsers(){ userModel.setRowCount(0); for(User u:controller.getAllUsers()) userModel.addRow(new Object[]{u.getIdUser(),u.getNama(),u.getUsername(),u.getRole()}); }
    private void userDialog(User old){ JTextField n=UIUtils.createModernTextField(18), u=UIUtils.createModernTextField(18), p=UIUtils.createModernTextField(18); JComboBox<String> role=UIUtils.createModernComboBox(); role.addItem("admin"); role.addItem("operator"); if(old!=null){n.setText(old.getNama());u.setText(old.getUsername());role.setSelectedItem(old.getRole());} JPanel form=new JPanel(new GridLayout(0,2,8,8)); form.add(new JLabel("Nama"));form.add(n);form.add(new JLabel("Username"));form.add(u);form.add(new JLabel(old==null?"Password":"Password Baru"));form.add(p);form.add(new JLabel("Role"));form.add(role); if(javax.swing.JOptionPane.showConfirmDialog(this,form,"Form User",javax.swing.JOptionPane.OK_CANCEL_OPTION)==javax.swing.JOptionPane.OK_OPTION){ User user=old==null?new User():old; user.setNama(n.getText()); user.setUsername(u.getText()); user.setPassword(p.getText()); user.setRole(String.valueOf(role.getSelectedItem())); if(controller.saveUser(user)) refreshUsers(); } }
    private void editUser(){ int r=userTable.getSelectedRow(); if(r<0)return; User u=new User(); u.setIdUser((int)userModel.getValueAt(r,0)); u.setNama(String.valueOf(userModel.getValueAt(r,1))); u.setUsername(String.valueOf(userModel.getValueAt(r,2))); u.setRole(String.valueOf(userModel.getValueAt(r,3))); userDialog(u); }
    private void deleteUser(){ int r=userTable.getSelectedRow(); if(r>=0&&UIUtils.showConfirm(this,"Hapus user?")){controller.deleteUser((int)userModel.getValueAt(r,0)); refreshUsers();} }
}
