package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class UIUtils {
    public static final Color NAVY = new Color(15, 23, 42), BLUE = new Color(37, 99, 235), CYAN = new Color(6, 182, 212), GREEN = new Color(22, 163, 74), ORANGE = new Color(249, 115, 22), RED = new Color(220, 38, 38), MUTED = new Color(100, 116, 139), WHITE = Color.WHITE;
    public static final Color SUCCESS = GREEN, DANGER = RED, ACCENT = CYAN, BACKGROUND = new Color(248, 250, 252);
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22), FONT_BOLD = new Font("SansSerif", Font.BOLD, 14), FONT_PLAIN = new Font("SansSerif", Font.PLAIN, 14);

    public static class Option {
        public int id; public String label;
        public Option(int id, String label) { this.id = id; this.label = label; }
        @Override public String toString() { return label; }
    }

    public static String rupiah(double v) { return NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(v); }
    public static JLabel formLabel(String text) { JLabel l = new JLabel(text); l.setFont(FONT_BOLD); l.setForeground(NAVY); return l; }
    public static JTextField textField(int cols) { JTextField f = new JTextField(cols); f.setFont(FONT_PLAIN); f.putClientProperty("JTextField.placeholderText", "Ketik di sini"); return f; }
    public static JButton button(String text, Color bg) { JButton b = new JButton(text); b.setFont(FONT_BOLD); b.setBackground(bg); b.setForeground(WHITE); b.setFocusPainted(false); return b; }
    public static JPanel page(String title) { JPanel p = new JPanel(new BorderLayout(12,12)); p.setBackground(BACKGROUND); p.setBorder(new EmptyBorder(18,18,18,18)); JLabel h = new JLabel(title); h.setFont(FONT_TITLE); h.setForeground(NAVY); p.add(h, BorderLayout.NORTH); return p; }
    public static JPanel card() { JPanel c = new JPanel(new BorderLayout(8,8)); c.setBackground(WHITE); c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(226,232,240)), new EmptyBorder(14,14,14,14))); return c; }
    public static JPanel createKPICard(String title, String value, Color accent) { JPanel c = card(); c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,5,0,0,accent), new EmptyBorder(14,14,14,14))); JLabel t = new JLabel(title); t.setForeground(MUTED); t.setFont(FONT_BOLD); JLabel v = new JLabel(value); v.setForeground(NAVY); v.setFont(new Font("SansSerif", Font.BOLD, 20)); c.add(t, BorderLayout.NORTH); c.add(v, BorderLayout.CENTER); return c; }
    public static JScrollPane tableScroll(JTable table) { table.setRowHeight(28); table.getTableHeader().setFont(FONT_BOLD); JScrollPane s = new JScrollPane(table); s.setBorder(BorderFactory.createLineBorder(new Color(226,232,240))); return s; }
    public static JPanel toolbar(JComponent... components) { JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8)); p.setOpaque(false); for (JComponent c: components) p.add(c); return p; }
    public static void bindSearch(JTextField field, TableRowSorter<?> sorter) { field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){ public void insertUpdate(javax.swing.event.DocumentEvent e){filter();} public void removeUpdate(javax.swing.event.DocumentEvent e){filter();} public void changedUpdate(javax.swing.event.DocumentEvent e){filter();} void filter(){ sorter.setRowFilter(field.getText().isBlank()? null : RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(field.getText()))); }}); }
    public static JLabel schoolLogo(int size) { JLabel l = new JLabel("SKM", SwingConstants.CENTER); l.setPreferredSize(new Dimension(size, size)); l.setOpaque(true); l.setBackground(BLUE); l.setForeground(WHITE); l.setFont(new Font("SansSerif", Font.BOLD, Math.max(12, size/4))); return l; }
}
