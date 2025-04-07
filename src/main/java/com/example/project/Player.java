package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Player extends Sprite {
    private int treasureCount;
    private int numLives;
    private boolean win;

    public Player(int x, int y) { //set treasureCount = 0 and numLives = 2 
        super(x, y);
        treasureCount = 0; //initalizes treasureCount to 0
        numLives = 2; //initializes numLives to 2
        win = false; //initializes win to false
    }


    public int getTreasureCount(){return treasureCount;} //return current amount of treasures
    public int getLives(){return numLives;} //return current amount of lives
    public boolean getWin(){return win;} //return whether the player has won

    //method to set the number of lives
    public void setLives(int val) { 
        numLives = val; //set the num of lives to argument val
    }

    @Override //Override method in Sprite 
    //return the player's coordinates
    public String getCoords() {
        return "Player:" + super.getCoords(); 
    }

    @Override //Override method in sprite 
    //returns the player's row col
    public String getRowCol(int size) {
        return "Player:" + super.getRowCol(size);
    }

  
    //move method should override parent class, sprite
    @Override
    public void move(String direction) { //move the (x,y) coordinates of the player
        if (direction.equals("w")) { //if player moves up 
            super.setY(super.getY() + 1); //set y value to y + 1
        } else if (direction.equals("s")) { //if player moves down
            super.setY(super.getY() - 1); //set y value to y - 1
        } else if (direction.equals("a")) { //if player moves left
            super.setX(super.getX() - 1); //set x value to x - 1
        } else if (direction.equals("d")) { //if player moves right
            super.setX(super.getX() + 1); //set x value to x + 1
        }
    }

    public void interact(int size, String direction, int numTreasures, Object obj) { // interact with an object in the position you are moving to 
    //numTreasures is the total treasures at the beginning of the game
    if (obj instanceof Treasure && !(obj instanceof Trophy)) { //if the object is a treasure but not trophy, treasureCount increments by 1
        treasureCount++;
    }
    if (obj instanceof Trophy) { //if the object is a trophy, check if player has all the treasures, if so, win, or else, return nothing / do nothing
        if (treasureCount == numTreasures) {
            win = true;
        }
        else {
            return;
        }
    }
    if (obj instanceof Enemy) { //if the object is an enemy, decrease a life
        numLives--;
    }
    }


    public boolean isValid(int size, String direction){ //check grid boundaries
        if (direction.equals("w")) {
            if (super.getY() == size -1) { //checks if y value is within grid boundaries, when the sprite is at the max y value and they're trying to move up
                return false; //then return false because the sprite will go out of bound
            }
        } else if (direction.equals("s")) {
            if (super.getY() == 0) {  //checks if y value is within grid boundaries, when the sprite is at y = 0 and they're trying to move down
                return false; 
            }
        } else if (direction.equals("a")) {
            if (super.getX() == 0) { //checks if x value is within grid boundaries, when the sprite is at x = 0 and they're trying to move left
                return false; //then return false because the sprite will go out of bound
            }
        } else if (direction.equals("d")) {
            if(super.getX() == size - 1) { //checks if x value is within grid boundaries, when the sprite is at the max x value and they're trying to move right
                return false; //then return false because the sprite will go out of bound
            }
        }
        return true; //everything else is valid and will be within grid boundaries
    }


   
}



