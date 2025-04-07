package com.example.project;

public class Sprite {
    private int x, y;

    //Sprite constructor with parameters x and y
    //initializes x and y int
    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){return x;}    //returns x value
    public int getY(){return y;}    //returns y value 

    //method to set x value
    //does not return any value 
    public void setX(int newVal){ 
        x = newVal;
    }
       //method to set y value
    //does not return any value 
    public void setY(int newVal){
        y = newVal;
    }

    public String getCoords(){ //returns the coordinates of the sprite ->"(x,y)"
        return "(" + x + "," + y + ")";
    }

    public String getRowCol(int size){ //returns the row and column of the sprite -> "[row][col]"
    int row = (size-y) -1 ; //the row is the inverse of (size - y) - 1
    int col = x;    //set colu value to x
        return "[" + row + "][" + col + "]";    //return [row][col] string
    }
    

    public void move(String direction) { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }

    public void interact() { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }



}
