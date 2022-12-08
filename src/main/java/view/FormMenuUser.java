package view;

import Manager.ItemsManager;
import Manager.ReadWrite;
import Manager.UserManager;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class FormMenuUser extends JFrame implements Serializable {
    private JLabel lbUserName;
    private JLabel lbID;
    private JButton itemsButton;
    private JButton btLogOut;
    private JPanel panelMenuUser;
    private JLabel lbAvatar;
    private JLabel lbGmail;
    UserManager userManager = new UserManager();
ItemsManager itemsManager = new ItemsManager();
ReadWrite readWrite = new ReadWrite();
    public FormMenuUser() {
        this.setContentPane(this.panelMenuUser);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocation(700, 320);
        readWrite.read(userManager.file);
        lbID.setText(Integer.toString(userManager.users.get(User.LOGININDEX).getId()));
        String username = userManager.users.get(User.LOGININDEX).getName();
        ImageIcon iconPaimon = new ImageIcon("icon.png");
        ImageIcon iconOk = new ImageIcon("iconOk.png");
        lbAvatar.setIcon(iconOk);
        lbUserName.setText(username);
        lbGmail.setText(userManager.users.get(User.LOGININDEX).getGmailAddress());
        itemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeFrom();
            }
        });
        btLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User.LOGININDEX=-1;
                logout();
            }
        });
    }

    public void storeFrom(){
        new FormStore();
        this.dispose();
    }
public void logout(){
        User.LOGININDEX=-1;
        new formDangNhap();
        this.dispose();
}
    public static void main(String[] args) {
        FormMenuUser formMenuUser   = new FormMenuUser();
    }
}
