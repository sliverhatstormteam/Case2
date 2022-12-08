package view;

import Manager.ItemsManager;
import Manager.ReadWrite;
import Manager.UserManager;
import model.Item;
import model.ItemKinds;
import model.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.List;

public class FormQuanLyLoaiItems extends JFrame implements Serializable {
    private JTextField textKindname;
    private JTextField textKinddes;
    private JButton btThem;
    private JButton sửaButton;
    private JButton xoáButton;
    private JButton quayLạiButton;
    private JTable tableLoai;
    private JPanel panelTableLoai;
    private JPanel panelQuanLyPhanLoai;

    public FormQuanLyLoaiItems() {
        ReadWrite readWrite = new ReadWrite();
        ItemsManager itemsManager = new ItemsManager();
        Item item = new Item();
        User user = new User();
        this.setContentPane(this.panelQuanLyPhanLoai);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocation(700, 320);
        ImageIcon iconPaimon = new ImageIcon("icon.png");
        ImageIcon iconOk = new ImageIcon("iconOk.png");
        btThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textKindname.getText().equals("") || textKinddes.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Thông tin không được để trống");
                    return;
                }
                for (int i = 0; i < itemsManager.itemskinds.size(); i++) {
                    if (itemsManager.itemskinds.get(i).getNameOfKinds().equals(textKindname.getText())) {
                            JOptionPane.showMessageDialog(null, "Ôi không, trùng tên rồi ","Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                            return;
                    }
                }
                ItemKinds itemKinds = new ItemKinds(textKindname.getText(), textKinddes.getText());
                itemsManager.itemskinds.add(itemKinds);
                tableLoai.repaint();
                readWrite.write(itemsManager.fileKinds, itemsManager.itemskinds);

            }
        });
        readWrite.read(itemsManager.fileKinds);
        TablePhanLoaiModel tablePhanLoaiModel = new TablePhanLoaiModel(itemsManager.itemskinds);
        tableLoai.setModel(tablePhanLoaiModel);
        tableLoai.setAutoCreateRowSorter(true);
        sửaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textKindname.getText().equals("") || textKinddes.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Thông tin không được để trống");
                        return;
                    }
                    int selected = tableLoai.getSelectedRow();
                    TableModel model = tableLoai.getModel();
                    for (int i = 0; i < itemsManager.itemskinds.size(); i++) {
                        if (itemsManager.itemskinds.get(i).getNameOfKinds().equals(textKindname.getText())) {
                            if (i != selected) {
                                JOptionPane.showMessageDialog(null, "Ôi không, trùng tên rồi ","Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                                return;

                            }
                        }
                    }
                            itemsManager.itemskinds.get(selected).setNameOfKinds(textKindname.getText());
                            itemsManager.itemskinds.get(selected).setKindsDescription(textKinddes.getText());
                            tableLoai.repaint();
                            readWrite.write(itemsManager.fileKinds, itemsManager.itemskinds);
                            readWrite.read(itemsManager.fileKinds);


                } catch (Exception e2) {
                }
                ;

                readWrite.write(itemsManager.fileKinds, itemsManager.itemskinds);
                readWrite.read(itemsManager.fileKinds);

            }
        });
        tableLoai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int selected = tableLoai.getSelectedRow();
                    TableModel model = tableLoai.getModel();
                    textKindname.setText(model.getValueAt(selected, 0).toString());
                    textKinddes.setText(model.getValueAt(selected, 1).toString());
                } catch (Exception e1) {
                }
                ;

            }
        });
        xoáButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TableModel model = tableLoai.getModel();
                    int selected = tableLoai.getSelectedRow();
                            itemsManager.itemskinds.remove(selected);
                            tableLoai.repaint();
                            TablePhanLoaiModel tablePhanLoaiModel = new TablePhanLoaiModel(itemsManager.itemskinds);
                            tableLoai.setModel(tablePhanLoaiModel);
                            tableLoai.setAutoCreateRowSorter(true);
                            readWrite.write(itemsManager.fileKinds, itemsManager.itemskinds);
                            readWrite.read(itemsManager.fileKinds);
                } catch (Exception e1) {
                }
                ;

            }
        });
        quayLạiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }

    public void goBack() {
        new TrangChu();
        this.dispose();
    }

    public static class TablePhanLoaiModel extends AbstractTableModel {

        private final String[] COLUMNNAME = {"Tên Loại", "Mô tả"};
        public List<ItemKinds> itemKinds;
        UserManager userManager = new UserManager();
        ItemsManager itemsManager = new ItemsManager();

        public TablePhanLoaiModel(List<ItemKinds> itemKinds) {
            this.itemKinds = itemKinds;
        }

        @Override
        public int getRowCount() {
            return itemKinds.size();
        }

        public int getIndexByName(String name) {
            for (int i = 0; i < itemsManager.itemskinds.size(); i++) {
                if (itemsManager.itemskinds.get(i).getNameOfKinds().equals(name)) {
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
                if (itemKinds.size() != 0)
                    return switch (columnIndex) {
                        case 0 -> itemKinds.get(rowIndex).getNameOfKinds();
                        case 1 -> itemKinds.get(rowIndex).getKindsDescription();
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

            return ItemKinds.class;
        }
    }

    public static void main(String[] args) {
        FormQuanLyLoaiItems formQuanLyLoaiItems = new FormQuanLyLoaiItems();
    }
}
