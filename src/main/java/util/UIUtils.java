// File: util/UIUtils.java
package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public final class UIUtils {
    public static final Color PRIMARY = new Color(0x1E3A5F);
    public static final Color ACCENT = new Color(0x2563EB);
    public static final Color SUCCESS = new Color(0x10B981);
    public static final Color DANGER = new Color(0xEF4444);
    public static final Color WARNING = new Color(0xF59E0B);
    public static final Color BACKGROUND = new Color(0xF8FAFC);
    public static final Color BORDER = new Color(0xE2E8F0);
    private static final Locale ID_LOCALE = new Locale("in", "ID");

    private UIUtils() {
    }

    public static JButton createPrimaryButton(String text) { return coloredButton(text, ACCENT); }
    public static JButton createSuccessButton(String text) { return coloredButton(text, SUCCESS); }
    public static JButton createDangerButton(String text) { return coloredButton(text, DANGER); }

    public static JButton createSecondaryButton(String text) {
        JButton button = baseButton(text);
        button.setForeground(PRIMARY);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER), new EmptyBorder(6, 18, 6, 18)));
        return button;
    }

    private static JButton coloredButton(String text, Color color) {
        JButton button = baseButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(new EmptyBorder(7, 18, 7, 18));
        return button;
    }

    private static JButton baseButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }

    public static JTextField createModernTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER), new EmptyBorder(7, 9, 7, 9)));
        return field;
    }

    public static JTextArea createModernTextArea(int rows, int cols) {
        JTextArea area = new JTextArea(rows, cols);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER), new EmptyBorder(7, 9, 7, 9)));
        return area;
    }

    public static <T> JComboBox<T> createModernComboBox() {
        JComboBox<T> combo = new JComboBox<>();
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setPreferredSize(new Dimension(180, 34));
        return combo;
    }

    public static String formatRupiah(double nominal) {
        NumberFormat format = NumberFormat.getCurrencyInstance(ID_LOCALE);
        format.setMaximumFractionDigits(0);
        return format.format(nominal).replace("Rp", "Rp ").replace(",00", "");
    }

    public static double parseRupiah(String text) {
        if (text == null || text.isBlank()) {
            return 0;
        }
        String normalized = text.replace("Rp", "").replace(" ", "").trim();
        try {
            return NumberFormat.getNumberInstance(ID_LOCALE).parse(normalized).doubleValue();
        } catch (ParseException e) {
            return Double.parseDouble(normalized.replace(".", "").replace(",", "."));
        }
    }

    public static JPanel createKPICard(String title, String value, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER), new EmptyBorder(18, 18, 18, 18)));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(new Color(0x64748B));
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JLabel valueLabel = new JLabel(value);
        valueLabel.setName("valueLabel");
        valueLabel.setForeground(accentColor);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    public static void setKPIValue(JPanel card, String value) {
        for (Component component : card.getComponents()) {
            if (component instanceof JLabel label && "valueLabel".equals(label.getName())) {
                label.setText(value);
            }
        }
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(32);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(0xDBEAFE));
        table.setSelectionForeground(PRIMARY);
        table.setFillsViewportHeight(true);
        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);
    }

    public static JScrollPane wrapTable(JTable table) {
        styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        return scrollPane;
    }

    public static DefaultTableCellRenderer rightRenderer() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.RIGHT);
        return renderer;
    }

    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean showConfirm(Component parent, String message) {
        return JOptionPane.showConfirmDialog(parent, message, "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static JPanel page(String title) {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new InsetsBorder(18));
        JLabel label = new JLabel(title);
        label.setForeground(PRIMARY);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(label, BorderLayout.NORTH);
        return panel;
    }

    private static class InsetsBorder extends EmptyBorder {
        InsetsBorder(int inset) { super(new Insets(inset, inset, inset, inset)); }
    }
}
