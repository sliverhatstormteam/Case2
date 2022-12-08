package view;

import Manager.ItemsManager;
import Manager.ReadWrite;
import Manager.UserManager;
import model.Item;
import model.Items;
import model.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormQuanLyUser extends JFrame implements Serializable {
    private JTextField textUserName;
    private JTextField textUserPassWord;
    private JTextField textUserNameDisplay;
    private JTextField textUserGmail;
    private JTable tableItemOnBoard;
    private JTable tableItemUser;
    private JTable tableUser;
    private JButton btKhoa;
    private JButton btSuaUser;
    private JButton btXoaUser;
    private JLabel lbIdUser;
    private JButton btLogOut;
    private JButton btBack;
    private JPanel panelFormQLUser;
    private JPanel panelUser;
    private JPanel panelItemOnboard;
    private JPanel panelUserItems;
    private JButton btThem;
    private JButton xoaButton;
    private JLabel lbThongBaoChon;
    private JLabel lbClick;
    private JTextField textAmout;
    private JButton mởKhoáButton;
    private JLabel lbStatus;
    ReadWrite readWrite = new ReadWrite();

    public FormQuanLyUser() {
        UserManager userManager = new UserManager();
        ItemsManager itemsManager = new ItemsManager();
        this.setContentPane(this.panelFormQLUser);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocation(200, 220);
        this.setPreferredSize(new Dimension(650, 500));
        panelUser.setLayout(new BorderLayout());
        panelUser.add(tableUser, BorderLayout.CENTER);
        panelUser.add(tableUser.getTableHeader(), BorderLayout.NORTH);

        panelUserItems.setLayout(new BorderLayout());
        panelUserItems.add(tableItemUser, BorderLayout.CENTER);
        panelUserItems.add(tableItemUser.getTableHeader(), BorderLayout.NORTH);

        panelItemOnboard.setLayout(new BorderLayout());
        panelItemOnboard.add(tableItemOnBoard, BorderLayout.CENTER);
        panelItemOnboard.add(tableItemOnBoard.getTableHeader(), BorderLayout.NORTH);

        ImageIcon iconPaimon = new ImageIcon("icon.png");
        ImageIcon iconOk = new ImageIcon("iconOk.png");
        btSuaUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = tableUser.getSelectedRow();
                if (textUserNameDisplay.getText().equals("") || textUserName.getText().equals("") || textUserGmail.getText().equals("") || textUserPassWord.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Thông tin không được để trống", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                    return;
                }
                for (int i = 0; i < userManager.users.size(); i++) {
                    if (userManager.users.get(i).getUsername().equals(textUserName.getText()) || textUserName.getText().equals("Admin")) {
                        if (i != selected || textUserName.getText().equals("Admin")) {
                            JOptionPane.showMessageDialog(null, "Trùng tên đăng nhập", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                            return;
                        }
                    }
                }
                try {
                    TableModel modelUser = tableUser.getModel();
                    userManager.users.get(selected).setName(textUserNameDisplay.getText());
                    userManager.users.get(selected).setUsername(textUserName.getText());
                    userManager.users.get(selected).setPassword((textUserPassWord.getText()));
                    userManager.users.get(selected).setGmailAddress(textUserGmail.getText());
                    tableUser.repaint();
                    readWrite.write(userManager.file, userManager.users);
                    JOptionPane.showMessageDialog(null, "Sửa thành công!", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconOk);
                } catch (Exception o) {
                    JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Lỗi cập nhật", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                }

            }
        });


        FormQuanLyUser.UserManagerTable tableUserModel = new UserManagerTable(userManager.users);
        tableUser.setModel(tableUserModel);
        tableUser.setAutoCreateRowSorter(true);
        FormQuanLyUser.ItemsOnboardTable tableItemOnStoreModel = new ItemsOnboardTable(itemsManager.itemsOnBoards);
        tableItemOnBoard.setModel(tableItemOnStoreModel);
        tableItemOnBoard.setAutoCreateRowSorter(true);
        TableColumnModel columnModel = tableItemOnBoard.getColumnModel();
        TableColumn TC_DisplayName = columnModel.getColumn(1);
        TableColumn TC_Dis = columnModel.getColumn(4);
        TableColumn TC_Id = columnModel.getColumn(0);
        TC_DisplayName.setPreferredWidth(200);
        TC_Id.setPreferredWidth(10);
        TC_Dis.setPreferredWidth(200);
//        FormQuanLyUser.UserItemsTable itemsTable = new UserItemsTable(UserManager.users.get(2).getListItemUser());
//        tableItemsNV.setModel(itemsTable);


        tableUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int selected = tableUser.getSelectedRow();
                    TableModel model = tableUser.getModel();
                    if (userManager.users.get(selected).getRoles()==3){
                        lbStatus.setText("Khoá");
                    }else {
                        lbStatus.setText("Không khoá");
                    }

                    int idchoice = Integer.parseInt(model.getValueAt(selected, 0).toString());
                    textUserNameDisplay.setText(model.getValueAt(selected, 1).toString());
                    textUserName.setText(model.getValueAt(selected, 2).toString());
                    textUserPassWord.setText(model.getValueAt(selected, 3).toString());
                    textUserGmail.setText(model.getValueAt(selected, 4).toString());
                    lbIdUser.setText("ID:" + model.getValueAt(selected, 0).toString());
                    lbClick.setText("");
                    readWrite.read(userManager.file);
                    int index = tableUserModel.getIndexById(idchoice);
                    UserItemsTable userItemsTable = new UserItemsTable(userManager.users.get(index).getListItemUser());
//                    UserItemsTable userItemsTable = new UserItemsTable(userManager.users.get(Integer.parseInt(model.getValueAt(selected, 0).toString())).getListItemUser());
                    tableItemUser.setModel(userItemsTable);
                    tableUser.repaint(20, 100, 100, 100);
                    tableItemUser.repaint();
                } catch (Exception e1) {

                }
            }
        });
        tableItemUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int selected = tableItemUser.getSelectedRow();
                    TableModel model = tableItemUser.getModel();
                    String namechoice;
                    namechoice = model.getValueAt(selected, 1).toString();
                    lbClick.setText(namechoice);
                    tableItemUser.repaint();
                    System.out.println(namechoice);
                } catch (Exception e1) {

                }
            }
        });
        xoaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TableModel modeluser = tableUser.getModel();
                    int userSelected = tableUser.getSelectedRow();

                    TableModel modelItem = tableItemUser.getModel();
                    int itemSelected = tableItemUser.getSelectedRow();

                    int idUserSelected = Integer.parseInt(modeluser.getValueAt(userSelected, 0).toString());
                    int idItemSelected = Integer.parseInt(modelItem.getValueAt(itemSelected, 0).toString());
                    UserItemsTable userItemsTable = new UserItemsTable(userManager.users.get(tableUserModel.getIndexById(userSelected)).getListItemUser());

                    int indexUser = tableUserModel.getIndexById(idUserSelected);
                    int indexItem = userItemsTable.getIndexById(indexUser, idItemSelected);

                    ArrayList<Item> items = userManager.users.get(indexUser).getListItemUser();
                    items.remove(indexItem);
                    userManager.users.get(indexUser).setListItemUser((Items) items);
                    System.out.println(userManager.users.get(indexUser).getListItemUser());
//                itemsManager.deleteUserItem(indexUser, indexItem);
                    readWrite.write(userManager.file, userManager.users);
                    readWrite.read(userManager.file);
//
                    UserItemsTable userItemsTable2 = new UserItemsTable(userManager.users.get(indexUser).getListItemUser());
                    System.out.println(userManager.users.get(tableUserModel.getIndexById(userSelected)).getListItemUser());
                    tableItemUser.setModel(userItemsTable2);
                    tableItemUser.repaint();
                } catch (Exception e1) {

                }


            }
        });
        btXoaUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = tableUser.getSelectedRow();
                TableModel model = tableUser.getModel();
                int idchoice = Integer.parseInt(model.getValueAt(selected, 0).toString());
                System.out.println(idchoice);
                userManager.deleteUser(idchoice);
                readWrite.read(userManager.file);
                FormQuanLyUser.UserManagerTable userManagerTable = new UserManagerTable(userManager.users);
                tableUser.setModel(userManagerTable);
                tableUser.repaint();

            }
        });
        tableItemOnBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int selected = tableItemOnBoard.getSelectedRow();
                    TableModel model = tableItemOnBoard.getModel();
                    int idchoice = Integer.parseInt(model.getValueAt(selected, 0).toString());
                    String namechoice;
                    namechoice = model.getValueAt(selected, 1).toString();
                    lbClick.setText(namechoice);
                    tableItemUser.repaint();
                } catch (Exception e1) {
                }

            }
        });
        btThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedUser = tableUser.getSelectedRow();
                    TableModel modelUser = tableUser.getModel();
                    int lay = 0;
                    int slcon = 0;
                    int slgoc = 0;
                    int userschoice;
                    if (Integer.parseInt(textAmout.getText()) <= 0) {
                        JOptionPane.showMessageDialog(null, "Nhập số tự nhiên lớn hơn không vào đi bạn hiền", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                        return;
                    }
                    try {
                        userschoice = Integer.parseInt(modelUser.getValueAt(selectedUser, 0).toString());
                    } catch (NullPointerException e2) {
                        JOptionPane.showMessageDialog(null, "Chưa chọn user để thêm", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                    }
                    try {
                        int selectedItemsOnBoard = tableItemOnBoard.getSelectedRow();
                        TableModel modelItem = tableItemOnBoard.getModel();
                        int itemchoice = Integer.parseInt(modelItem.getValueAt(selectedItemsOnBoard, 0).toString());
                        int count = 0;
                        slcon = Integer.parseInt(modelItem.getValueAt(selectedItemsOnBoard, 2).toString());
                        for (int i = 0; i < userManager.users.get(selectedUser).getListItemUser().size(); i++) {
                            if (modelItem.getValueAt(selectedItemsOnBoard, 1).toString().equals(userManager.users.get(selectedUser).getListItemUser().get(i).getName())) {
                                slgoc = userManager.users.get(selectedUser).getListItemUser().get(i).getAmount();
                                System.out.println("SLGOC: " + slgoc);
                                if (slcon - Integer.parseInt(textAmout.getText()) < 0) {
                                    JOptionPane.showMessageDialog(null, "Không còn đủ số lượng", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                                } else {
                                    userManager.users.get(selectedUser).getListItemUser().get(i).setAmount(slgoc + Integer.parseInt(textAmout.getText()));
                                    UserItemsTable userItemsTable = new UserItemsTable(userManager.users.get(selectedUser).getListItemUser());
                                    itemsManager.itemsOnBoards.get(selectedItemsOnBoard).setAmount(slcon - Integer.parseInt(textAmout.getText()));
                                    readWrite.write(itemsManager.file, itemsManager.itemsOnBoards);
                                    readWrite.write(userManager.file, userManager.users);
                                    readWrite.read(userManager.file);
                                    tableItemUser.setModel(userItemsTable);
                                    tableItemUser.repaint();
                                    tableItemOnBoard.repaint();
                                }
                                count += 1;
                                break;
                            }
                        }
                        if (count == 0) {
                            if (slcon - Integer.parseInt(textAmout.getText()) < 0) {
                                JOptionPane.showMessageDialog(null, "Không còn đủ số lượng", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                            } else {

                                UserItemsTable userItemsTable = new UserItemsTable(userManager.users.get(selectedUser).getListItemUser());
                                itemsManager.itemsOnBoards.get(selectedItemsOnBoard).setAmount(slcon - Integer.parseInt(textAmout.getText()));
                                userManager.users.get(selectedUser).getListItemUser().add(tableItemOnStoreModel.getItem(itemchoice));
                                int slao = userManager.users.get(selectedUser).getListItemUser().get(lay).getAmount();
                                System.out.println(slao);
                                int sizeItemUser = userManager.users.get(selectedUser).getListItemUser().size();
                                userManager.users.get(selectedUser).getListItemUser().get(sizeItemUser - 1).setAmount(Integer.parseInt(textAmout.getText()));
                                tableItemUser.setModel(userItemsTable);
                                tableItemUser.repaint();
                                tableItemOnBoard.repaint();
                                readWrite.write(itemsManager.file, itemsManager.itemsOnBoards);
                                readWrite.write(userManager.file, userManager.users);
                                readWrite.read(userManager.file);
                                UserItemsTable userItemsTable1 = new UserItemsTable(userManager.users.get(selectedUser).getListItemUser());
                                tableItemUser.setModel(userItemsTable1);
                                tableItemUser.repaint();
                            }


                        }
                    } catch (Exception e2) {
                    }

                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(null, "Nhập số nguyên đi", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);

                }
                ;


            }
        });
        btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        btLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logOut();
            }
        });
        btKhoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = tableUser.getSelectedRow();
                userManager.users.get(selected).setRoles(3);
                readWrite.write(userManager.file, userManager.users);
                readWrite.read(userManager.file);
                lbStatus.setText("Khoá");

            }
        });
        mởKhoáButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = tableUser.getSelectedRow();
                userManager.users.get(selected).setRoles(0);
                readWrite.write(userManager.file, userManager.users);
                readWrite.read(userManager.file);
                lbStatus.setText("Không khoá");
            }
        });
    }

    public void logOut() {
        User.LOGININDEX = -1;
        new formDangNhap();
        this.dispose();
    }

    public static class UserManagerTable extends AbstractTableModel {

        private final String[] COLUMNNAME = {"ID", "Tên hiển thị", "Tài khoản", "Mật khẩu", "Gmail"};
        public List<User> users;
        UserManager userManager = new UserManager();

        public UserManagerTable(List<User> users) {
            this.users = users;
        }

        @Override
        public int getRowCount() {
            return users.size();
        }

        public int getIndexById(int id) {
            for (int i = 0; i < userManager.users.size(); i++) {
                if (userManager.users.get(i).getId() == id) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public int getColumnCount() {
            return COLUMNNAME.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                if (users.size() != 0)
                    return switch (columnIndex) {
                        case 0 -> users.get(rowIndex).getId();
                        case 1 -> users.get(rowIndex).getName();
                        case 2 -> users.get(rowIndex).getUsername();
                        case 3 -> users.get(rowIndex).getPassword();
                        case 4 -> users.get(rowIndex).getGmailAddress();
                        default -> "-";
                    };

            } catch (Exception e) {

            }
            return null;
        }

        @Override

        public String getColumnName(int column) {
            return COLUMNNAME[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {

            return User.class;
        }
    }

    public void goBack() {
        new TrangChu();
        this.dispose();
    }

    public static class UserItemsTable extends AbstractTableModel {
        UserManager userManager = new UserManager();
        private final String[] COLUMNNAME = {"ID", "Tên item", "Số lượng", "Giá", "Mô tả"};

        public UserItemsTable(List<Item> items) {
            this.items = items;
        }

        public List<Item> items;

        public int getIndexById(int iduser, int iditem) {

            for (int j = 0; j < userManager.users.get(iduser).getListItemUser().size(); j++) {
                if (userManager.users.get(iduser).getListItemUser().get(j).getId() == iditem) {
                    return j;
                }
            }


            return -1;
        }

        @Override
        public int getRowCount() {
            return items.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNNAME.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                if (items.size() != 0)
                    return switch (columnIndex) {

                        case 0 -> items.get(rowIndex).getId();
                        case 1 -> items.get(rowIndex).getName();
                        case 2 -> items.get(rowIndex).getAmount();
                        case 3 -> items.get(rowIndex).getPrice();
                        case 4 -> items.get(rowIndex).getDescription();
                        default -> "-";
                    };

            } catch (Exception e) {

            }
            return null;
        }

        @Override

        public String getColumnName(int column) {
            return COLUMNNAME[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {

            return Item.class;
        }
    }

    public static class ItemsOnboardTable extends AbstractTableModel {
        ItemsManager itemsManager = new ItemsManager();
        private final String[] COLUMNNAME = {"ID", "Tên item", "Số lượng", "Giá", "Mô tả"};

        public ItemsOnboardTable(List<Item> items) {
            this.items = items;
        }

        public List<Item> items;

        public Item getItem(int index) {
            for (int i = 0; i < itemsManager.itemsOnBoards.size(); i++) {
                if (itemsManager.itemsOnBoards.get(i).getId() == index) {
                    return itemsManager.itemsOnBoards.get(i);
                }
            }
            return null;
        }

        public int getIndexById(int id) {
            for (int i = 0; i < itemsManager.itemsOnBoards.size(); i++) {
                if (itemsManager.itemsOnBoards.get(i).getId() == id) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public int getRowCount() {
            return items.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNNAME.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                if (items.size() != 0)
                    return switch (columnIndex) {

                        case 0 -> items.get(rowIndex).getId();
                        case 1 -> items.get(rowIndex).getName();
                        case 2 -> items.get(rowIndex).getAmount();
                        case 3 -> items.get(rowIndex).getPrice();
                        case 4 -> items.get(rowIndex).getDescription();
                        default -> "-";
                    };

            } catch (Exception e) {

            }
            return null;
        }

        @Override

        public String getColumnName(int column) {
            return COLUMNNAME[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {

            return Item.class;
        }
    }

    public static void main(String[] args) {
        FormQuanLyUser FormQuanLyUser = new FormQuanLyUser();
    }

}
