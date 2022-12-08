package view;

import Manager.ReadWrite;
import Manager.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.User.LOGININDEX;

//import static Manager.UserManager.users;

public class formDangNhap extends JFrame {
    private static final long serialVersionUID = 6529685098267757690L;
    private JButton buttonDangKy;
    private JLabel labelTest;
    private JPanel Main;
    private JTextField textUsernam;
    private JTextField textPassword;
    private JButton btDangNhap;
    private JButton btDangKy;
    private JLabel lbThongBao;
    UserManager userManager = new UserManager();
    ReadWrite readWrite = new ReadWrite();


    public formDangNhap() {

        this.setContentPane(this.Main);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocation(700, 320);
        this.setPreferredSize(new Dimension(600, 350));

        ImageIcon iconPaimon = new ImageIcon("icon.png");
        ImageIcon iconOk = new ImageIcon("iconOk.png");

        btDangNhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = 0;
                if (textUsernam.getText().equals("Admin")){
                    if(textPassword.getText().equals("1")){
                        JOptionPane.showMessageDialog(null, "Đăng nhập quyền Admin","Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconOk);
                   dangNhapAdmin();
                   return;
                    }else {
                        JOptionPane.showMessageDialog(null, "Sai mật khẩu","Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                    return;
                    }
                }

                if (login() == -1) {
                    JOptionPane.showMessageDialog(null, "Sai tên mật khẩu", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                } else if (login() == -2) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy tên đăng nhập", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                } else {
                    LOGININDEX = key;
                    if (userManager.users.get(LOGININDEX).getRoles()==3){
                        JOptionPane.showMessageDialog(null, "Tài khoản bị khoá", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                    return;
                    }
                    System.out.println(login());
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
                    dangNhapUser();
                }

            }
        });

        btDangKy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dangky();
            }
        });
    }

    public void dangky() {
        new FormDangKy();
        this.dispose();
    }

    public void dangNhapUser() {
        new FormMenuUser();
        this.dispose();
    }

    public void dangNhapAdmin() {
        new TrangChu();
        this.dispose();
    }
int key;
    public int login() {
        int dem = 0;
        for (int i = 0; i < userManager.users.size(); i++) {
            if (textUsernam.getText().equals(userManager.users.get(i).getUsername())) {
                key =i;
                dem = 1;
            }
        }
        if (dem == 1) {
            for (int h = 0; h < userManager.users.size(); h++) {
                if (dem == 1 && textPassword.getText().equals(userManager.users.get(h).getPassword())) {

                    return h;
                }
            }
            return -1;
        } else return -2;

    }

    public static void main(String[] args) {
        formDangNhap home = new formDangNhap();

    }

}
