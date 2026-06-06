// File: form/LoginFrame.java
package form;

import controller.AuthController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.User;
import util.SessionManager;
import util.UIUtils;

public class LoginFrame extends JFrame {
    private final JTextField txtUsername = UIUtils.createModernTextField(22);
    private final JPasswordField txtPassword = new JPasswordField(22);
    private final JLabel lblError = new JLabel(" ");
    private final AuthController authController = new AuthController();

    public LoginFrame() {
        setTitle("Login SKM"); setUndecorated(true); setSize(420, 520); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel root = new JPanel(new GridBagLayout()); root.setBackground(UIUtils.BACKGROUND); add(root);
        JPanel card = new JPanel(new GridBagLayout()); card.setBackground(Color.WHITE); card.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(UIUtils.BORDER), javax.swing.BorderFactory.createEmptyBorder(28, 32, 28, 32)));
        root.add(card);
        GridBagConstraints g = new GridBagConstraints(); g.gridx = 0; g.fill = GridBagConstraints.HORIZONTAL; g.insets = new Insets(8, 0, 8, 0);
        JLabel logo = new JLabel("SKM Madrasah", JLabel.CENTER); logo.setFont(new Font("Segoe UI", Font.BOLD, 26)); logo.setForeground(UIUtils.PRIMARY); card.add(logo, g);
        g.gridy = 1; card.add(new JLabel("Username"), g); g.gridy = 2; card.add(txtUsername, g);
        g.gridy = 3; card.add(new JLabel("Password"), g); g.gridy = 4; card.add(txtPassword, g);
        g.gridy = 5; lblError.setForeground(UIUtils.DANGER); card.add(lblError, g);
        g.gridy = 6; javax.swing.JButton btnLogin = UIUtils.createPrimaryButton("Login"); card.add(btnLogin, g);
        g.gridy = 7; javax.swing.JButton btnClose = UIUtils.createSecondaryButton("Tutup"); card.add(btnClose, g);
        btnLogin.addActionListener(e -> login()); btnClose.addActionListener(e -> System.exit(0)); txtPassword.addActionListener(e -> btnLogin.doClick());
    }

    private void login() {
        User user = authController.login(txtUsername.getText().trim(), new String(txtPassword.getPassword()));
        if (user == null) { lblError.setText("Username atau password salah"); return; }
        SessionManager.getInstance().setUser(user); new MainFrame().setVisible(true); dispose();
    }
}
