package view;

import Manager.ReadWrite;
import Manager.CharacterManager;
import model.Character;
import model.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class formQuanLyNhanVat extends JFrame implements Serializable {
    private JTextField textAge;
    private JButton themButton;
    private JButton suaButton;
    private JButton xoaButton;
    private JTable tableNV;
    private JButton quayLaiButton;
    private JTextField textHp;
    private JTextField textAtk;
    private JTextField textName;
    private JButton dangXuatButton;
    private JPanel QuanlyNV;
    private JLabel lbID;
    private JPanel panel;
    CharacterManager characterManager = new CharacterManager();
    ReadWrite readWrite = new ReadWrite();
    File file = new File("NV.txt");
    ArrayList<Character> characters = readWrite.read(file);
    int idg = 0;

    public formQuanLyNhanVat() {
//        this formQuanLyNhanVat = new formQuanLyNhanVat();
        this.setContentPane(this.QuanlyNV);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocation(700, 320);
        panel.setLayout(new BorderLayout());
        panel.add(tableNV, BorderLayout.CENTER);
        panel.add(tableNV.getTableHeader(), BorderLayout.NORTH);
        ImageIcon iconPaimon = new ImageIcon("icon.png");
        ImageIcon iconOk = new ImageIcon("iconOk.png");
        themButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textName.getText().equals("") || textAge.getText().toString().equals("") || textHp.getText().toString().equals("") || textAtk.getText().toString().equals("")) {
                    JOptionPane.showMessageDialog(null, "Thông tin không được để trống");
                    return;
                }
                if (!textName.getText().matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(null, "Ôi không, tên có số rồi", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                    return;
                }
                idg += 1;
                try {
                    Character character = new Character(idg, textName.getText(), Integer.parseInt(textAge.getText()), Integer.parseInt(textHp.getText()), Integer.parseInt(textAtk.getText()));
                    characterManager.addCharacter(character);
                    tableNV.repaint();
                    System.out.println(readWrite.read(file));
                } catch (InputMismatchException | NumberFormatException o) {
                    JOptionPane.showMessageDialog(null, "Hãy suy nghĩ như người bình thường, xin cảm ơn.", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);

                }
            }
        });

        tableNV.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int selected = tableNV.getSelectedRow();
                    TableModel model = tableNV.getModel();
                    textName.setText(model.getValueAt(selected, 1).toString());
                    textAge.setText(model.getValueAt(selected, 2).toString());
                    textHp.setText(model.getValueAt(selected, 3).toString());
                    textAtk.setText(model.getValueAt(selected, 4).toString());
                    lbID.setText("ID:" + model.getValueAt(selected, 0).toString());
                    tableNV.repaint();
                }
                catch (Exception e1) {

                }
            }
        });
        suaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selected = tableNV.getSelectedRow();
                TableModel model = tableNV.getModel();
                if (textName.getText().equals("") || textAge.getText().toString().equals("") || textHp.getText().toString().equals("") || textAtk.getText().toString().equals("")) {
                    JOptionPane.showMessageDialog(null, "Thông tin không được để trống");
                    return;
                }
                if (!textName.getText().matches("[a-zA-Z]+")) {
                    JOptionPane.showMessageDialog(null, "Ôi không, tên có số rồi", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                    return;
                }
                try {
                    int idchoice = Integer.parseInt(model.getValueAt(selected, 0).toString());
                    System.out.println(idchoice);
                    for (int i = 0; i < characterManager.characters.size(); i++) {
                        if (characterManager.characters.get(i).getId() == idchoice) {
                            characterManager.characters.get(i).setName(textName.getText());
                            characterManager.characters.get(i).setAge(Integer.parseInt(textAge.getText()));
                            characterManager.characters.get(i).setHp(Integer.parseInt(textHp.getText()));
                            characterManager.characters.get(i).setAtk(Integer.parseInt(textAtk.getText().toString()));
                            tableNV.repaint();
                            readWrite.write(file, characters);
                            JOptionPane.showMessageDialog(null, "Sửa thành công!", "Thông điệp từ vũ trụ", JOptionPane.INFORMATION_MESSAGE, iconOk);
                            break;

                        }
                    }
                } catch (Exception o) {
                    JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Lỗi cập nhật", JOptionPane.INFORMATION_MESSAGE, iconPaimon);
                }


            }
        });
        xoaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, "Xoá");
                int selected = tableNV.getSelectedRow();
                TableModel model = tableNV.getModel();
                int idchoice = Integer.parseInt(model.getValueAt(selected, 0).toString());
                System.out.println(idchoice);

                characterManager.deleteCharacter(idchoice);
                readWrite.read(file);
                CharacterTable characterTable = new CharacterTable(characterManager.characters);
                tableNV.setModel(characterTable);
                tableNV.repaint();
            }
        });
        CharacterTable characterTable = new CharacterTable(characterManager.characters);
        tableNV.setModel(characterTable);
        tableNV.setAutoCreateRowSorter(true);

        dangXuatButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dangXuatTaiKhoan();
            }
        });
        quayLaiButton.addActionListener(new ActionListener() {
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
    public void dangXuatTaiKhoan() {
        User.LOGININDEX=-1;
        new formDangNhap();
        this.dispose();
    }


    public static class CharacterTable extends AbstractTableModel {
        private final String[] COLUMNNAME = {"ID", "Tên", "Tuổi", "HP", "ATK"};
        public List<Character> characters;

        public CharacterTable(List<Character> characters) {

            this.characters = characters;
        }

        @Override
        public int getRowCount() {
            return characters.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNNAME.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                if (characters.size() != 0)
                    return switch (columnIndex) {
                        case 0 -> characters.get(rowIndex).getId();
                        case 1 -> characters.get(rowIndex).getName();
                        case 2 -> characters.get(rowIndex).getAge();
                        case 3 -> characters.get(rowIndex).getHp();
                        case 4 -> characters.get(rowIndex).getAtk();
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

            return Character.class;
        }
    }

    public static void main(String[] args) {
        formQuanLyNhanVat formQuanLyNhanVat = new formQuanLyNhanVat();
//        formQuanLyNhanVat.setContentPane(new formQuanLyNhanVat().QuanlyNV);
//        formQuanLyNhanVat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        formQuanLyNhanVat.setVisible(true);
//        formQuanLyNhanVat.pack();
    }
}
