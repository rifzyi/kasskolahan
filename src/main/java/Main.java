// File: form/Main.java
import com.formdev.flatlaf.FlatLightLaf;
import form.LoginFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        UIManager.put("Button.arc", 8);
        UIManager.put("Component.arc", 8);
        UIManager.put("TextComponent.arc", 8);
        UIManager.put("Button.margin", new Insets(6, 18, 6, 18));
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("Table.rowHeight", 32);
        UIManager.put("Table.showVerticalLines", false);
        UIManager.put("Table.alternateRowColor", new Color(0xF8FAFC));
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
