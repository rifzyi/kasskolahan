import com.formdev.flatlaf.FlatLightLaf;
import form.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 12);
            new LoginFrame().setVisible(true);
        });
    }
}
