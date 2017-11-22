package com.example.gsu_g6.g6_grocerylist;

/**
 * Created by Nikki on 11/19/2017.
 */

public class CustomList {
    private int ListID_PK;
    private String listName;

    CustomList(int listid, String listName){
        this.ListID_PK = listid;
        this.listName = listName;
    }

    public void setListID_PK(int x){
        this.ListID_PK = x;
    }

    public void setListName(String input){
        this.listName = input;
    }

    public int getListID_PK(){
        return this.ListID_PK;
    }

    public String getListName(){ return listName; }

}
