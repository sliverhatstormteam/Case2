package view;

import Manager.ItemsManager;
import Manager.ReadWrite;
import model.Item;
import model.ItemKinds;
import model.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class formQuanLyItems extends JFrame {
    private JButton btSua;
    private JButton btXoa;
    private JButton btThem;
    private JButton btDangxuat;
    private JButton btQuayLai;
    private JTable tableItems;
    private JTextField textItemAmount;
    private JTextField textItemName;
    private JTextField textItemsPrice;
    private JTextField textMota;
    private JLabel lbIdItem;
    private JPanel panelQLItems;
    private JPanel panelItems;
    private JComboBox cbbEditItems;
    private JButton btFilter;
    private JButton xoáLọcButton;
    static int itemId;
    ReadWrite readWrite = new ReadWrite();
    ItemsManager itemsManager = new ItemsManager();
    ItemKinds itemKinds = new ItemKinds();


    public formQuanLyItems() {
        Item item = new Item();
        User user = new User();
        this.setContentPane(this.panelQLItems);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocation(700, 320);
        setCbbEditItems();
        panelItems.setLayout(new BorderLayout());
        panelItems.add(tableItems, BorderLayout.CENTER);
        panelItems.add(tableItems.getTableHeader(), BorderLayout.NORTH);

        ImageIcon iconPaimon = new ImageIcon("icon.png");
        ImageIcon iconOk = new ImageIcon("iconOk.png");
        btThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int check = 0;
                    readWrite.read(itemsManager.file);
                    for (int i = 0; i < itemsManager.itemsOnBoards.size(); i++) {
                        if (textItemName.getText().equals(itemsManager.itemsOnBoards.get(i).getName())) {
                            check = 1;
                            break;
                        }
                    }

                    if (check == 1) {
                        JOptionPane.showMessageDialog(null, "Vật phẩm đã tồn tại", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                    } else {
                        int checkID;
                        do {
                            checkID = 0;

                            for (int j = 0; j < itemsManager.itemsOnBoards.size(); j++) {
                                if (itemsManager.itemsOnBoards.get(j).getId() == itemId) {
                                    itemId += 1;
                                    checkID += 1;
                                }
                            }
                        } while (checkID != 0);
                        System.out.println("không");
                        try {
                            Item newitem = new Item(itemId, textItemName.getText(), Integer.parseInt(textItemAmount.getText()), Integer.parseInt(textItemsPrice.getText()), textMota.getText());
                        } catch (NumberFormatException e1) {
                            JOptionPane.showMessageDialog(null, "Số lượng và giá tiền là số nguyên", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                        return;
                        }
                        Item newitem = new Item(itemId, textItemName.getText(), Integer.parseInt(textItemAmount.getText()), Integer.parseInt(textItemsPrice.getText()), textMota.getText());
                        newitem.setKinds(cbbEditItems.getSelectedItem().toString());
                        itemsManager.addItem(newitem);
                        readWrite.write(itemsManager.file, itemsManager.itemsOnBoards);
                        readWrite.read(itemsManager.file);
                        tableItems.repaint();
                        System.out.println("không");
                    }
                } catch (Exception o) {
                    o.printStackTrace();
                }

            }
        });

        formQuanLyItems.TableItem tableItem = new formQuanLyItems.TableItem(itemsManager.itemsOnBoards);
        tableItems.setModel(tableItem);
        tableItems.setAutoCreateRowSorter(true);
        btSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        tableItems.addComponentListener(new ComponentAdapter() {
        });
        tableItems.addMouseListener(new MouseAdapter() {
        });
        tableItems.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int selected = tableItems.getSelectedRow();
                    TableModel model = tableItems.getModel();
                    textItemName.setText(model.getValueAt(selected, 1).toString());
                    textItemAmount.setText(model.getValueAt(selected, 2).toString());
                    textItemsPrice.setText(model.getValueAt(selected, 3).toString());
                    textMota.setText(model.getValueAt(selected, 4).toString());
                    lbIdItem.setText("ID:" + model.getValueAt(selected, 0).toString());
                    tableItems.repaint();
                } catch (Exception e1) {
                }
            }
        });
        btSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = tableItems.getSelectedRow();
                TableModel model = tableItems.getModel();
                if (textItemName.getText().equals("") || textItemAmount.getText().equals("") || textItemsPrice.getText().equals("") || textMota.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Thông tin không được để trống");
                    return;
                }
                for (int i = 0; i < itemsManager.itemsOnBoards.size(); i++) {
                    if (itemsManager.itemsOnBoards.get(i).getName().equals(textItemName.getText())){
                        if (i!=selected){
                            JOptionPane.showMessageDialog(null, "Trùng tên rồi ", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                        return;
                        }
                    }
                }
                try {
                    int idchoice = Integer.parseInt(model.getValueAt(selected, 0).toString());
                    System.out.println(idchoice);
                    for (int i = 0; i < itemsManager.itemsOnBoards.size(); i++) {
                        if (itemsManager.itemsOnBoards.get(i).getId() == idchoice) {
                            itemsManager.itemsOnBoards.get(i).setName(textItemName.getText());
                            itemsManager.itemsOnBoards.get(i).setAmount(Integer.parseInt(textItemAmount.getText()));
                            itemsManager.itemsOnBoards.get(i).setPrice(Integer.parseInt(textItemsPrice.getText()));
                            itemsManager.itemsOnBoards.get(i).setDescription(textMota.getText());
                            itemsManager.itemsOnBoards.get(i).setKinds(cbbEditItems.getSelectedItem().toString());
                            tableItems.repaint();
                            readWrite.write(itemsManager.file, itemsManager.itemsOnBoards);
                            JOptionPane.showMessageDialog(null, "Sửa thành công!", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconOk);
                            break;

                        }
                    }
                } catch (Exception o) {
                    JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Lỗi cập nhật", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                }

            }
        });
        btQuayLai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        btXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selected = tableItems.getSelectedRow();
                    TableModel model = tableItems.getModel();
                    itemsManager.itemsOnBoards.remove(selected);
                    tableItems.repaint();
                    readWrite.read(itemsManager.file);
                    TableItem tableItem = new TableItem(itemsManager.itemsOnBoards);
                    tableItems.setModel(tableItem);
                    tableItems.repaint();
                } catch (Exception e1) {
                }
            }
        });
        btDangxuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        btFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> listFilter = new ArrayList<Item>();
                for (int i = 0; i < itemsManager.itemsOnBoards.size(); i++) {
                    if (itemsManager.itemsOnBoards.get(i).getKinds().equals(cbbEditItems.getSelectedItem().toString())) {
                        listFilter.add(itemsManager.itemsOnBoards.get(i));
                    }
                }
                TableItem tableItem1 = new TableItem(listFilter);
                tableItems.setModel(tableItem1);
                tableItems.setAutoCreateRowSorter(true);
                tableItems.repaint();
            }
        });
        xoáLọcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableItem tableItem1= new TableItem(itemsManager.itemsOnBoards);
                tableItems.setModel(tableItem1);
                tableItems.setAutoCreateRowSorter(true);
                tableItems.repaint();
            }
        });
    }
    public void setCbbEditItems(){
        for (int i = 0; i < itemsManager.itemskinds.size(); i++) {
            cbbEditItems.addItem(itemsManager.itemskinds.get(i).getNameOfKinds());
        }
    }

    public void logout() {
        User.LOGININDEX = -1;
        new formDangNhap();
        this.dispose();
    }

    public static class TableItem extends AbstractTableModel {
        ItemsManager itemsManager = new ItemsManager();
        private final String[] COLUMNNAME = {"ID", "Tên", "Số lượng", "Giá", "Mô tả","Loại"};
        public List<Item> items;

        public TableItem(List<Item> items) {
            this.items = items;
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
                if (items.size() != 0) return switch (columnIndex) {
                    case 0 -> items.get(rowIndex).getId();
                    case 1 -> items.get(rowIndex).getName();
                    case 2 -> items.get(rowIndex).getAmount();
                    case 3 -> items.get(rowIndex).getPrice();
                    case 4 -> items.get(rowIndex).getDescription();
                    case 5 -> items.get(rowIndex).getKinds();
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
        new TrangChu();
        this.dispose();
    }

    public static void main(String[] args) {
        ReadWrite readWrite = new ReadWrite();
        ItemsManager itemsManager = new ItemsManager();
        formQuanLyItems formQuanLyItems = new formQuanLyItems();
        System.out.println(readWrite.read(itemsManager.file));
    }
}
