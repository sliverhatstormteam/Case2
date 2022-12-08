package view;

import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrangChu extends JFrame {
    private JButton quảnLíLoạiItemsButton;
    private JButton btQlItem;
    private JButton btQLUser;
    private JButton btLogOut;
    private JPanel panelHome;

    public TrangChu() {
        this.setContentPane(this.panelHome);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocation(700, 320);

        ImageIcon iconPaimon = new ImageIcon("icon.png");
        ImageIcon iconOk = new ImageIcon("iconOk.png");
        btQLUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goUserManagement();
            }
        });
        btLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });
        btQlItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goItemManagement();
            }
        });
        quảnLíLoạiItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quanlyLoaiItems();
            }
        });
    }
public void quanlyLoaiItems() {
        new FormQuanLyLoaiItems();
        this.dispose();
}
    public void back() {
        User.LOGININDEX=-1;
        new formDangNhap();
        this.dispose();
    }

    public void goUserManagement() {
        new FormQuanLyUser();
        this.dispose();
    }

    public void goItemManagement() {
        new formQuanLyItems();
        this.dispose();
    }

    public static void main(String[] args) {
        TrangChu trangChu = new TrangChu();
    }
}
