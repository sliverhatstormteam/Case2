package view;

import Manager.ItemsManager;
import Manager.ReadWrite;
import Manager.UserManager;
import model.Item;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormSold extends JFrame implements Serializable {
    private JTable tableSold;
    private JPanel panelTableSold;
    private JPanel panelFormSold;
    private JButton đóngButton;

    public FormSold() {
        ReadWrite readWrite = new ReadWrite();
        UserManager userManager = new UserManager();
        ItemsManager itemsManager = new ItemsManager();
        readWrite.read(userManager.file);
        readWrite.read(itemsManager.file);
        this.setContentPane(this.panelFormSold);
        this.setVisible(true);
        this.pack();
        this.setLocation(0, 220);
        panelTableSold.setLayout(new BorderLayout());
        panelTableSold.add(tableSold, BorderLayout.CENTER);
        panelTableSold.add(tableSold.getTableHeader(), BorderLayout.NORTH);
        tableSold.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        TableSoldModel tableSoldModel = new TableSoldModel(itemsManager.itemsOnBoards);
        tableSold.setModel(tableSoldModel);
        tableSold.setAutoCreateRowSorter(true);
        TableRowSorter<TableSoldModel> sorter = new TableRowSorter<TableSoldModel>(tableSoldModel);
//        TableRowSorter<> sorter = new TableRowSorter<TableModel>(table.getModel());
        tableSold.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(0);
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        int selected = tableSold.getSelectedRow();
        TableModel modelsort = tableSold.getModel();
        ArrayList<Item> listSort = new ArrayList<Item>();
        for (int i = 0; i < 3; i++) {
            listSort.add(itemsManager.itemsOnBoards.get(tableSold.convertRowIndexToModel(i)));
        }
        TableSoldModel tableSoldModel2 = new TableSoldModel(listSort);
        tableSold.setModel(tableSoldModel2);
        tableSold.setAutoCreateRowSorter(true);


        đóngButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goback();
            }
        });
    }
    public  void goback() {
        this.dispose();
    }
    public static class TableSoldModel extends AbstractTableModel {

        private final String[] COLUMNNAME = {"Tên","Số lượng đã bán","Giá bán","Loại","Mô tả"};
        public java.util.List<Item> items;
        UserManager userManager = new UserManager();
        ItemsManager itemsManager = new ItemsManager();

        public TableSoldModel(List<Item> items) {
            this.items = items;
        }

        @Override
        public int getRowCount() {
            return items.size();
        }

        public int getIndexById(int id) {
            for (int i = 0; i < itemsManager.itemsOnBoards.size(); i++) {
                if (itemsManager.itemsOnBoards.get(i).getId()==(id)) {
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
                if (items.size() != 0)
                    return switch (columnIndex) {
                        case 0 -> items.get(rowIndex).getName();
                        case 1 -> items.get(rowIndex).getSold();
                        case 2 -> items.get(rowIndex).getPrice();
                        case 3 -> items.get(rowIndex).getKinds();
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
        FormSold FormSold = new FormSold();
    }
}
