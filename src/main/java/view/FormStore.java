package view;

import Manager.ItemsManager;
import Manager.ReadWrite;
import Manager.UserManager;
import model.Item;
import model.User;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormStore extends JFrame implements Serializable {
    private JTable tableStore;
    private JTable tableUserItems;
    private JButton muaButton;
    private JPanel panelStore;
    private JPanel panelUserItems;
    private JTextField textAmout;
    private JPanel panelStoreFrom;
    private JButton btGoBack;
    private JTextField textTim;
    private JLabel lbTongTien;
    private JButton btThanhToan;
    private JButton btSold;
    private JComboBox ccbLocItems;
    private JButton btFilter;
    private JTextField textTuGia;
    private JTextField textDenGia;
    private JButton btLocTheoGia;
    private JButton btXoaLoc;
    ItemsManager itemsManager = new ItemsManager();
    ReadWrite readWrite = new ReadWrite();

    public FormStore() {
        readWrite.read(itemsManager.fileKinds);
        readWrite.read(itemsManager.file);
        UserManager userManager = new UserManager();
        ItemsManager itemsManager = new ItemsManager();
        this.setContentPane(this.panelStoreFrom);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocation(500, 220);
        panelStore.setLayout(new BorderLayout());
        panelStore.add(tableStore, BorderLayout.CENTER);
        panelStore.add(tableStore.getTableHeader(), BorderLayout.NORTH);
        setccbLocItems();
        panelUserItems.setLayout(new BorderLayout());
        panelUserItems.add(tableUserItems, BorderLayout.CENTER);
        panelUserItems.add(tableUserItems.getTableHeader(), BorderLayout.NORTH);

        ImageIcon iconPaimon = new ImageIcon("icon.png");
        ImageIcon iconOk = new ImageIcon("iconOk.png");
        lbTongTien.setText(Integer.toString(userManager.users.get(User.LOGININDEX).getUnpay()));


        tableUserItems.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        tableStore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                muaButton.setVisible(true);
            }
        });
        FormStore.TableStore tableStoremodel = new TableStore(itemsManager.itemsOnBoards);
        tableStore.setModel(tableStoremodel);
        tableStore.setAutoCreateRowSorter(true);
        TableUserItems tableUserItemsModel = new TableUserItems(userManager.users.get(User.LOGININDEX).getListItemUser());
        tableUserItems.setModel(tableUserItemsModel);
        tableUserItems.setAutoCreateRowSorter(true);

        TableColumnModel columnModel = tableStore.getColumnModel();
        TableColumn TC_DisplayName = columnModel.getColumn(1);
        TableColumn TC_Dis = columnModel.getColumn(4);
        TableColumn TC_Id = columnModel.getColumn(0);
        TC_DisplayName.setPreferredWidth(200);
        TC_Id.setPreferredWidth(10);
        TC_Dis.setPreferredWidth(200);
        muaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int curentUser = User.LOGININDEX;
                int lay = 0;
                int StoreAmount = 0;
                int UserItemAmount = 0;
                int selectedItems = tableStore.getSelectedRow();
                int itemchoice;
                if (textAmout.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Chưa nhập số lượng kìa", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                    return;
                }
                try {
                    if (Integer.parseInt(textAmout.getText()) <= 0) {
                        JOptionPane.showMessageDialog(null, "Nhập số tự nhiên lớn hơn không vào đi bạn hiền", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                        return;
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Ô số lượng xin hãy nhập số", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);

                }

                try {
                    TableModel modelItem = tableStore.getModel();
                    TableModel modelItemStore = tableStore.getModel();
                    itemchoice = Integer.parseInt(modelItemStore.getValueAt(selectedItems, 0).toString());
                    int count = 0;
                    StoreAmount = Integer.parseInt(modelItemStore.getValueAt(selectedItems, 2).toString());
                    for (int i = 0; i < userManager.users.get(curentUser).getListItemUser().size(); i++) {
                        if (modelItemStore.getValueAt(selectedItems, 1).toString().equals(userManager.users.get(curentUser).getListItemUser().get(i).getName())) {
                            UserItemAmount = userManager.users.get(curentUser).getListItemUser().get(i).getAmount();
                            if (StoreAmount - Integer.parseInt(textAmout.getText()) < 0) {
                                JOptionPane.showMessageDialog(null, "Không còn đủ số lượng", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                            } else {
                                userManager.users.get(curentUser).getListItemUser().get(i).setAmount(UserItemAmount + Integer.parseInt(textAmout.getText()));
                                TableUserItems userItemsTable = new TableUserItems((userManager.users.get(curentUser).getListItemUser()));
                                itemsManager.itemsOnBoards.get(tableStoremodel.getIndexById(itemchoice)).setAmount(StoreAmount - Integer.parseInt(textAmout.getText()));
                                userManager.users.get(curentUser).setUnpay(userManager.users.get(curentUser).getUnpay() + Integer.parseInt(textAmout.getText()) * itemsManager.itemsOnBoards.get(tableStoremodel.getIndexById(itemchoice)).getPrice());
                                lbTongTien.setText(Integer.toString(userManager.users.get(curentUser).getUnpay()));
                                itemsManager.itemsOnBoards.get(tableStoremodel.getIndexById(itemchoice)).setSold(itemsManager.itemsOnBoards.get(tableStoremodel.getIndexById(itemchoice)).getSold() + Integer.parseInt(textAmout.getText()));
                                itemsManager.itemsOnBoards.get(tableStoremodel.getIndexById(itemchoice)).getSold();
                                tableUserItems.setModel(userItemsTable);
                                tableUserItems.repaint();
                                tableStore.repaint();
                            }
                            count += 1;
                            break;
                        }
                    }
                    if (count == 0) {
                        if (StoreAmount - Integer.parseInt(textAmout.getText()) < 0) {
                            JOptionPane.showMessageDialog(null, "Không còn đủ số lượng", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                        } else {
                            TableModel modelItemStore2 = tableStore.getModel();
                            itemchoice = Integer.parseInt(modelItemStore2.getValueAt(selectedItems, 0).toString());
                            System.out.println(tableStoremodel.getItem(itemchoice));
                            TableUserItems userItemsTable = new TableUserItems(userManager.users.get(curentUser).getListItemUser());
                            itemsManager.itemsOnBoards.get(tableStoremodel.getIndexById(itemchoice)).setAmount(StoreAmount - Integer.parseInt(textAmout.getText()));
                            userManager.users.get(curentUser).getListItemUser().add(tableStoremodel.getItem(itemchoice));
                            System.out.println(tableStoremodel.getItem(itemchoice));
                            int sizeItemUser = userManager.users.get(curentUser).getListItemUser().size();
                            userManager.users.get(curentUser).getListItemUser().get(sizeItemUser - 1).setAmount(Integer.parseInt(textAmout.getText()));
                            userManager.users.get(curentUser).setUnpay(userManager.users.get(curentUser).getUnpay() + Integer.parseInt(textAmout.getText()) * itemsManager.itemsOnBoards.get(tableStoremodel.getIndexById(itemchoice)).getPrice());
                            itemsManager.itemsOnBoards.get(tableStoremodel.getIndexById(itemchoice)).setSold(itemsManager.itemsOnBoards.get(tableStoremodel.getIndexById(itemchoice)).getSold() + Integer.parseInt(textAmout.getText()));

                            lbTongTien.setText(Integer.toString(userManager.users.get(curentUser).getUnpay()));
                            tableUserItems.setModel(userItemsTable);
                            tableStore.setModel(tableStoremodel);
                            tableStore.repaint();
                            tableUserItems.repaint();
                            tableUserItems.repaint();

                            TableUserItems userItemsTable1 = new TableUserItems(userManager.users.get(curentUser).getListItemUser());
                            tableUserItems.setModel(userItemsTable1);
                            tableUserItems.repaint();

                        }


                    }
                } catch (Exception e2) {
                }


            }
        });
        btGoBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(lbTongTien.getText()) == 0) {
                    goBack();
                } else {
                    int click = JOptionPane.showConfirmDialog(null, "Chưa thanh toán sẽ bị mất những items đang giao dịch");
                    if (click == JOptionPane.YES_OPTION) {
                        goBack();
                    }
                }

            }
        });
        textTim.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                TableModel model = tableStore.getModel();
                TableRowSorter<TableStore> tr = new TableRowSorter<TableStore>(tableStoremodel);
                tableStore.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter(textTim.getText().trim()));
            }
        });
        btThanhToan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userManager.users.get(User.LOGININDEX).setUnpay(0);
                lbTongTien.setText(Integer.toString(userManager.users.get(User.LOGININDEX).getUnpay()));
                readWrite.write(itemsManager.file, itemsManager.itemsOnBoards);
                readWrite.write(userManager.file, userManager.users);
                JOptionPane.showMessageDialog(null, "Thanh toán thành công", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconOk);
            }
        });
        btSold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topsale();
            }
        });
        btFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textTim.setVisible(false);
                ArrayList<Item> listFilter = new ArrayList<Item>();
                for (int i = 0; i < itemsManager.itemsOnBoards.size(); i++) {
                    if (itemsManager.itemsOnBoards.get(i).getKinds().equals(ccbLocItems.getSelectedItem().toString())) {
                        listFilter.add(itemsManager.itemsOnBoards.get(i));
                    }
                }
                FormStore.TableStore tableStoremodel = new TableStore(listFilter);
                tableStore.setModel(tableStoremodel);
                tableStore.setAutoCreateRowSorter(true);
                tableStore.repaint();
            }
        });
        btLocTheoGia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textTim.setVisible(false);

                ArrayList<Item> listFilterTheoGia = new ArrayList<Item>();
                int a = 0;
                int b = 0;
                try {
                    a = Integer.parseInt(textTuGia.getText());
                    b = Integer.parseInt(textDenGia.getText());
                } catch (Exception e3) {
                    JOptionPane.showMessageDialog(null, "Số tại ô lọc giá là số nguyên lớn hơn 0");
                }
                for (int i = 0; i < itemsManager.itemsOnBoards.size(); i++) {
                    if (itemsManager.itemsOnBoards.get(i).getPrice() >= a && itemsManager.itemsOnBoards.get(i).getPrice() <= b || itemsManager.itemsOnBoards.get(i).getPrice() >= b && itemsManager.itemsOnBoards.get(i).getPrice() <= a) {
                        listFilterTheoGia.add(itemsManager.itemsOnBoards.get(i));
                    }
                }
                FormStore.TableStore tableStoremode2 = new TableStore(listFilterTheoGia);
                tableStore.setModel(tableStoremode2);
                tableStore.setAutoCreateRowSorter(true);
                tableStore.repaint();
            }
        });
        btXoaLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textTim.setVisible(true);
                FormStore.TableStore tableStoremodel = new TableStore(itemsManager.itemsOnBoards);
                tableStore.setModel(tableStoremodel);
                tableStore.setAutoCreateRowSorter(true);
                tableStore.repaint();
            }
        });
    }

    public void setccbLocItems() {
        for (int i = 0; i < itemsManager.itemskinds.size(); i++) {
            ccbLocItems.addItem(itemsManager.itemskinds.get(i).getNameOfKinds());
        }

    }

    public void topsale() {
        new FormSold();
    }
//    FormQuanLyUser.UserManagerTable userManagerTable = new FormQuanLyUser.UserManagerTable(userManager.users);

    public static class TableStore extends AbstractTableModel {
        ItemsManager itemsManager = new ItemsManager();
        private final String[] COLUMNNAME = {"ID", "Tên item", "Số lượng", "Giá", "Mô tả"};


        public List<Item> items;

        public TableStore(List<Item> items) {
            this.items = items;
        }

        public int getIndexById(int iditem) {

            for (int j = 0; j < itemsManager.itemsOnBoards.size(); j++) {
                if (itemsManager.itemsOnBoards.get(j).getId() == iditem) {
                    return j;
                }
            }


            return -1;
        }

        public Item getItem(int index) {
            for (int i = 0; i < itemsManager.itemsOnBoards.size(); i++) {
                if (itemsManager.itemsOnBoards.get(i).getId() == index) {
                    return itemsManager.itemsOnBoards.get(i);
                }
            }
            return null;
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

    public void goBack() {
        new FormMenuUser();
        this.dispose();
    }

    public static class TableUserItems extends AbstractTableModel {
        ItemsManager itemsManager = new ItemsManager();
        UserManager userManager = new UserManager();
        private final String[] COLUMNNAME = {"Tên item", "Số lượng đang có", "Mô tả"};


        public List<Item> items;

        public TableUserItems(List<Item> items) {
            this.items = items;
        }

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

                        case 0 -> items.get(rowIndex).getName();
                        case 1 -> items.get(rowIndex).getAmount();
                        case 2 -> items.get(rowIndex).getDescription();
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
        FormStore FormStore = new FormStore();
    }
}
