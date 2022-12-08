package Manager;

import model.Character;
import model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CharacterManager {
    ReadWrite readWrite = new ReadWrite();
    File file = new File("NV.txt");
    public ArrayList<Character> characters = readWrite.read(file);

    public void addCharacter(Character character) {
        characters.add(character);
        readWrite.write(file, characters);
    }


    public void deleteCharacter(int id) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getId() == id) {
                characters.remove(i);
                readWrite.write(file, characters);
                break;
            }
        }
    }

    public int findByName(String name) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public void editCharacter(int id, Character character) {
        Scanner scanner = new Scanner(System.in);
        characters.get(id).setName(character.getName());
        characters.get(id).setAge(character.getAge());
        characters.get(id).setHp(character.getHp());
        characters.get(id).setAtk(character.getAtk());
    }
}
