// File: form/MainFrame.java
package form;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.User;
import util.SessionManager;
import util.UIUtils;

public class MainFrame extends JFrame {
    private final JPanel content = new JPanel(new CardLayout());
    private final Map<String, JButton> buttons = new LinkedHashMap<>();
    private final DashboardForm dashboardForm = new DashboardForm();

    public MainFrame() {
        setTitle("SKM - Sistem Keuangan Madrasah"); setDefaultCloseOperation(EXIT_ON_CLOSE); setExtendedState(MAXIMIZED_BOTH); setLayout(new BorderLayout());
        add(createSidebar(), BorderLayout.WEST); content.setBackground(UIUtils.BACKGROUND);
        addCard("Dashboard", dashboardForm); addCard("Siswa", new SiswaForm()); addCard("Kelas", new KelasForm()); addCard("Pemasukan", new PemasukanForm()); addCard("Pengeluaran", new PengeluaranForm()); addCard("Tabungan", new TabunganForm()); addCard("Laporan", new LaporanForm()); addCard("Pengaturan", new PengaturanForm());
        add(content, BorderLayout.CENTER); showCard("Dashboard");
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout(0, 12)); sidebar.setPreferredSize(new Dimension(210, 0)); sidebar.setBackground(UIUtils.PRIMARY); sidebar.setBorder(BorderFactory.createEmptyBorder(18, 12, 18, 12));
        User user = SessionManager.getInstance().getUser();
        JLabel title = new JLabel("<html><b>SKM</b><br><span style='font-size:10px'>" + (user == null ? "Guest" : user.getNama()) + " - " + (user == null ? "" : user.getRole()) + "</span></html>"); title.setForeground(Color.WHITE); title.setFont(new Font("Segoe UI", Font.PLAIN, 16)); sidebar.add(title, BorderLayout.NORTH);
        JPanel nav = new JPanel(new java.awt.GridLayout(0, 1, 0, 8)); nav.setOpaque(false);
        addNav(nav, "Dashboard", "🏠 Dashboard"); addNav(nav, "Siswa", "👥 Siswa"); addNav(nav, "Kelas", "🏫 Kelas"); addNav(nav, "Pemasukan", "💰 Pemasukan"); addNav(nav, "Pengeluaran", "💸 Pengeluaran"); addNav(nav, "Tabungan", "🏦 Tabungan"); addNav(nav, "Laporan", "📊 Laporan"); addNav(nav, "Pengaturan", "⚙️ Pengaturan");
        sidebar.add(nav, BorderLayout.CENTER);
        JButton logout = UIUtils.createDangerButton("🚪 Logout"); logout.addActionListener(e -> { SessionManager.getInstance().logout(); new LoginFrame().setVisible(true); dispose(); }); sidebar.add(logout, BorderLayout.SOUTH);
        return sidebar;
    }

    private void addNav(JPanel nav, String key, String text) {
        JButton b = new JButton(text); b.setHorizontalAlignment(SwingConstants.LEFT); b.setForeground(Color.WHITE); b.setBackground(UIUtils.PRIMARY); b.setFocusPainted(false); b.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12)); b.addActionListener(e -> showCard(key)); buttons.put(key, b); nav.add(b);
    }

    private void addCard(String key, JPanel panel) { content.add(panel, key); }

    private void showCard(String key) {
        ((CardLayout) content.getLayout()).show(content, key);
        buttons.forEach((k, b) -> b.setBackground(k.equals(key) ? UIUtils.ACCENT : UIUtils.PRIMARY));
        if ("Dashboard".equals(key)) dashboardForm.refreshData();
    }
}
