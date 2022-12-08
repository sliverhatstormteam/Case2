package Manager;

import model.Item;
import model.ItemKinds;

import java.io.File;
import java.util.ArrayList;

public class ItemsManager {
    ReadWrite readWrite = new ReadWrite();

    public File file = new File("Item.txt");
    public ArrayList<Item> itemsOnBoards = readWrite.read(file);
    public File fileKinds = new File("ItemKinds.txt");
    public ArrayList<ItemKinds> itemskinds = readWrite.read(fileKinds);




    public static void main(String[] args) {

    }

    public void deleteUserItem(int iduser, int iditem) {
        UserManager userManager = new UserManager();
        userManager.users.get(iduser).getListItemUser().remove(iditem);
        readWrite.write(userManager.file, userManager.users);

    }

    public void deleteItems(int index) {
        itemsOnBoards.remove(index);
        readWrite.write(file, itemsOnBoards);
    }

    public void addItem(Item newitem) {
        itemsOnBoards.add(newitem);
        readWrite.write(file, itemsOnBoards);
    }
}
