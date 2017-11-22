package com.example.gsu_g6.g6_grocerylist;

/**
 * Created by Ejiroghene on 11/22/2017.
 */

public class Users {
    private int id;
    private String email;

    Users(int id, String email){
        this.id = id;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
