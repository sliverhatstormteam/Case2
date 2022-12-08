package Manager;

import java.io.*;
import java.util.ArrayList;

public class ReadWrite<E> {

    public void write(File file, ArrayList<E> list){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<E> read(File file){
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<E> list = (ArrayList<E>) objectInputStream.readObject();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
