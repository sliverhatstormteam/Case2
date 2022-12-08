package model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private int unpay;
    private String username;
    private String password;
    private String name;
    private String gmailAddress;
    private int roles = 0;
    private Characters listcharacters;
    private Items listItemUser;
    public static int LOGININDEX;

    public Items getListItemUser() {
        if (listItemUser == null) {
            listItemUser = new Items();
        }
        return listItemUser;
    }

    public void setListItemUser(Items listItemUser) {
        this.listItemUser = listItemUser;
    }

    public User() {
    }

    public User(int id, String username, String password, String name, String gmailAddress) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.gmailAddress = gmailAddress;
    }


    public String getGmailAddress() {
        return gmailAddress;
    }

    public void setGmailAddress(String gmailAddress) {
        this.gmailAddress = gmailAddress;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Characters getListcharacters() {
        return listcharacters;
    }

    public int getUnpay() {
        return unpay;
    }

    public void setUnpay(int unpay) {
        this.unpay = unpay;
    }

    public void setListcharacters(Characters listcharacters) {
        this.listcharacters = listcharacters;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gmailAddress='" + gmailAddress + '\'' +
                ", roles=" + roles +
                ", listcharacters=" + listcharacters +
                ", listItemUser=" + listItemUser +
                '}';
    }

    //    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", name='" + name + '\'' +
//                ", roles=" + roles +
//                ", characters=" + characters.size() +
//                ", items=" + items +
//                '}';
//    }
}
