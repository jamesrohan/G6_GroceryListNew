package com.example.gsu_g6.g6_grocerylist;

/**
 * Created by Ejiroghene on 11/21/2017.
 */

public class Application {
    private static int id = 1;
    private static CustomList chosenList;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setChosenList(CustomList chosenList) {
        this.chosenList = chosenList;
    }

    public CustomList getChosenList() {
        return chosenList;
    }
}
