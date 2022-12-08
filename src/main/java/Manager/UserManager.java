package Manager;

import model.User;

import java.io.File;
import java.util.ArrayList;

public class UserManager {
    public ReadWrite readWrite = new ReadWrite();

    public File file = new File("User.txt");
    public ArrayList<User> users = readWrite.read(file);
    final public User admin = new User(-5, "Admin", "1", "Quản trị viên", "qctngi@gmail.com");


    public static void main(String[] args) {
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void deleteUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(i);
                readWrite.write(file, users);
                break;
            }
        }
    }


}
