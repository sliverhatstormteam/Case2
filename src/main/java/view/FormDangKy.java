package view;

import Manager.ReadWrite;
import Manager.UserManager;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class FormDangKy extends JFrame implements Serializable {

    private JTextField textDKName;
    private JTextField textDkUserName;
    private JPasswordField passwordFDk;
    private JPasswordField passwordDKre;
    private JTextField textDkEmail;
    private JButton btDangKy;
    private JPanel DangNhapPanel;
    private JButton btBack;
    ReadWrite readWrite = new ReadWrite();
    static int idgUser = 0;

    public FormDangKy() {
        this.setContentPane(this.DangNhapPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocation(700, 320);

        ImageIcon iconPaimon = new ImageIcon("icon.png");
        ImageIcon iconOk = new ImageIcon("iconOk.png");
        UserManager userManager = new UserManager();
        btDangKy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readWrite.read(userManager.file);
                if (textDKName.getText().equals("")||textDkUserName.getText().equals("")||passwordFDk.getText().equals("")||textDkEmail.getText().equals("")){
                    JOptionPane.showMessageDialog(DangNhapPanel, "Điền đủ thông tin đi bạn hiền", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                    return;
                }
                int check;
                do {
                     check = 0;
                    for (int i = 0; i < userManager.users.size(); i++) {
                        if (userManager.users.get(i).getId() == idgUser) {
                            idgUser+=1;
                            check += 1;
                        }
                    }
                }
                while (check!=0);
                User user = new User();
                user.setId(idgUser);
                user.setName(textDKName.getText());
                user.setUsername(textDkUserName.getText());
                user.setGmailAddress(textDkEmail.getText());
                System.out.println(passwordFDk.getPassword());
                System.out.println(passwordDKre.getPassword());
                int checkname=0;
                readWrite.read(userManager.file);
                for (int i = 0; i < userManager.users.size(); i++) {
                    if (userManager.users.get(i).getUsername().equals(textDkUserName.getText())||textDkUserName.getText().equals("Admin"))
                        checkname+=1;

                }
                if (checkname!=0) {
                    JOptionPane.showMessageDialog(null,"Tên đăng nhập đã được sử dụng","Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                }else {
                    if (passwordFDk.getText().equals(passwordDKre.getText())) {
                        user.setPassword(new String(passwordFDk.getPassword()));
                        userManager.addUser(user);
                        readWrite.write(userManager.file, userManager.users);
                        JOptionPane.showMessageDialog(null, "Đăng ký thành công!");
                        goback();
                    } else {
                        JOptionPane.showMessageDialog(null, "Mật khẩu đã nhập không trùng!", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon)
                        ;
                    }
                }
            }
        });
        btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goback();
            }
        });


    }
    public void goback() {
        new formDangNhap();
        this.dispose();
    }

    public static void main(String[] args) {
        FormDangKy FormDangKy= new FormDangKy();
    }
}
