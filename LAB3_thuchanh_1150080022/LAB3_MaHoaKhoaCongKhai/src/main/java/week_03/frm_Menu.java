package week_03;

import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class frm_Menu extends javax.swing.JFrame {

    private JButton btn_ecc;
    private JButton btn_history;
    private JButton btn_rsa;
    private JButton btn_rsaaes;
    private JButton btn_signature;
    private JButton btn_sql;
    private JLabel jLabel1;
    private JLabel jLabel2;

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        btn_rsa = new JButton();
        btn_ecc = new JButton();
        btn_rsaaes = new JButton();
        btn_signature = new JButton();
        btn_sql = new JButton();
        btn_history = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("LAB 3 - Mã hóa khóa công khai");

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jLabel1.setText("LAB 3 - MÃ HÓA KHÓA CÔNG KHAI");

        jLabel2.setText("Chọn một bài để mở form tương ứng (bài 3.1 chạy RSADemo.java bằng Run File):");

        btn_rsa.setText("3.2 RSA (Swing)");
        btn_rsa.addActionListener(this::btn_rsaActionPerformed);

        btn_ecc.setText("3.3 ECC");
        btn_ecc.addActionListener(this::btn_eccActionPerformed);

        btn_rsaaes.setText("3.4 RSA + AES");
        btn_rsaaes.addActionListener(this::btn_rsaaesActionPerformed);

        btn_signature.setText("3.5 Chữ ký ECDSA");
        btn_signature.addActionListener(this::btn_signatureActionPerformed);

        btn_sql.setText("Kết nối SQL Server");
        btn_sql.addActionListener(this::btn_sqlActionPerformed);

        btn_history.setText("Xem lịch sử");
        btn_history.addActionListener(this::btn_historyActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_rsa, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(btn_ecc, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(btn_rsaaes, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_signature, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(btn_sql, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(btn_history, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_rsa, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ecc, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_rsaaes, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_signature, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_sql, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_history, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }

    private void btn_rsaActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_RSA().setVisible(true);
    }

    private void btn_eccActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_ECC().setVisible(true);
    }

    private void btn_rsaaesActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_RSA_AES().setVisible(true);
    }

    private void btn_signatureActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_ECC_Signature().setVisible(true);
    }

    private void btn_sqlActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_KetNoiSQL().setVisible(true);
    }

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {
        new frm_LichSu().setVisible(true);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }
        java.awt.EventQueue.invokeLater(() -> new frm_Menu().setVisible(true));
    }
}
