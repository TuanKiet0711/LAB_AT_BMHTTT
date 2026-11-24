package week_03;

import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class frm_ECC_Signature extends javax.swing.JFrame {

    private final ECCSignature ecdsa = new ECCSignature();

    private JButton btn_generate;
    private JButton btn_loadkeys;
    private JButton btn_savekeys;
    private JButton btn_sign;
    private JButton btn_verify;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JLabel lbl_result;
    private JLabel lbl_status;
    private JTextArea txt_message;
    private JTextArea txt_private;
    private JTextArea txt_public;
    private JTextArea txt_signature;

    public frm_ECC_Signature() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /** Hiện khóa công khai / khóa riêng (Base64) lên form. */
    private void showKeys() {
        txt_public.setText(ecdsa.getPublicKeyBase64());
        txt_private.setText(ecdsa.getPrivateKeyBase64());
        txt_public.setCaretPosition(0);
        txt_private.setCaretPosition(0);
    }

    private void initComponents() {
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jScrollPane1 = new JScrollPane();
        txt_public = new JTextArea();
        jLabel3 = new JLabel();
        jScrollPane2 = new JScrollPane();
        txt_private = new JTextArea();
        btn_generate = new JButton();
        btn_savekeys = new JButton();
        btn_loadkeys = new JButton();
        jLabel4 = new JLabel();
        jScrollPane3 = new JScrollPane();
        txt_message = new JTextArea();
        jLabel5 = new JLabel();
        jScrollPane4 = new JScrollPane();
        txt_signature = new JTextArea();
        btn_sign = new JButton();
        btn_verify = new JButton();
        lbl_result = new JLabel();
        lbl_status = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ECDSA Signature");

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        jLabel1.setText("CHỮ KÝ SỐ ECDSA (SHA256withECDSA)");

        jLabel2.setText("Public key:");
        txt_public.setEditable(false);
        txt_public.setLineWrap(true);
        txt_public.setRows(2);
        jScrollPane1.setViewportView(txt_public);

        jLabel3.setText("Private key:");
        txt_private.setEditable(false);
        txt_private.setLineWrap(true);
        txt_private.setRows(2);
        jScrollPane2.setViewportView(txt_private);

        btn_generate.setText("Generate Keys");
        btn_generate.addActionListener(this::btn_generateActionPerformed);

        btn_savekeys.setText("Save Keys");
        btn_savekeys.addActionListener(this::btn_savekeysActionPerformed);

        btn_loadkeys.setText("Load Keys");
        btn_loadkeys.addActionListener(this::btn_loadkeysActionPerformed);

        jLabel4.setText("Message:");
        txt_message.setLineWrap(true);
        txt_message.setRows(2);
        jScrollPane3.setViewportView(txt_message);

        jLabel5.setText("Signature:");
        txt_signature.setLineWrap(true);
        txt_signature.setRows(2);
        jScrollPane4.setViewportView(txt_signature);

        btn_sign.setText("Sign");
        btn_sign.addActionListener(this::btn_signActionPerformed);

        btn_verify.setText("Verify");
        btn_verify.addActionListener(this::btn_verifyActionPerformed);

        lbl_result.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl_result.setText("");

        lbl_status.setText("Bấm Generate Keys hoặc Load Keys.");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_generate, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btn_savekeys, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btn_loadkeys, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_sign, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btn_verify, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_result, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_status, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_generate, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_savekeys, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_loadkeys, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(btn_sign, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_verify, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_result, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(lbl_status, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        pack();
    }

    private void btn_generateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ecdsa.generateKeys();
            showKeys();
            lbl_status.setText("Đã tạo cặp khóa mới. Bản mã/chữ ký cũ sẽ không dùng được với khóa mới.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không tạo được khóa: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_savekeysActionPerformed(java.awt.event.ActionEvent evt) {
        if (txt_public.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có khóa để lưu.");
            return;
        }
        String content = CryptoSupport.toKeyFile(txt_public.getText(), txt_private.getText());
        String path = FileTextIO.saveString(this, content, "ecdsa_keys.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu khóa vào: " + path + " (giữ bí mật dòng PRIVATE).");
        }
    }

    private void btn_loadkeysActionPerformed(java.awt.event.ActionEvent evt) {
        String content = FileTextIO.openString(this);
        if (content == null) {
            return;
        }
        try {
            String[] keys = CryptoSupport.fromKeyFile(content);
            ecdsa.loadKeys(keys[0], keys[1]);
            txt_public.setText(keys[0]);
            txt_private.setText(keys[1]);
            lbl_status.setText("Đã nạp khóa từ tệp.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Tệp khóa không hợp lệ: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_signActionPerformed(java.awt.event.ActionEvent evt) {
        String message = txt_message.getText();
        try {
            txt_signature.setText(ecdsa.sign(message));
            lbl_result.setText("");
            lbl_status.setText("Đã ký bằng KHÓA RIÊNG. "
                    + HistoryDao.tryInsert("ECDSA", "SIGN", message.length(), true));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ký thất bại: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btn_verifyActionPerformed(java.awt.event.ActionEvent evt) {
        String message = txt_message.getText();
        try {
            boolean ok = ecdsa.verify(message, txt_signature.getText());
            lbl_result.setText(ok ? "VALID" : "INVALID");
            lbl_result.setForeground(ok ? new java.awt.Color(0, 128, 0) : java.awt.Color.RED);
            lbl_status.setText("Đã xác minh bằng KHÓA CÔNG KHAI. "
                    + HistoryDao.tryInsert("ECDSA", "VERIFY", message.length(), ok));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Xác minh thất bại: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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
        java.awt.EventQueue.invokeLater(() -> new frm_ECC_Signature().setVisible(true));
    }
}
