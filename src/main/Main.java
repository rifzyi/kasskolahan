package main;

import form.LoginFrame;
import javax.swing.SwingUtilities;
import util.UIUtils;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      // Aktifkan FlatLaf - tampilan modern
      UIUtils.installFlatLafIfAvailable();
      
      // JANGAN set Windows Look and Feel!
      // Baris ini yang membuat tombol tidak bisa biru solid
      
      new LoginFrame().setVisible(true);
    });
  }
}