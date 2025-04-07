package com.example.project;


//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    private Sprite[][] grid;
    private int size;

    public Grid(int size) { //initialize and create a grid with all DOT objects
        this.size = size; //set private variable size to constructor argument size
        grid = new Sprite[size][size]; //initialize the grid 2D array with size for both rows and columns
        for (int i = 0; i < grid.length; i++) { //iterates through the rows of the 2D list
            for (int j = 0; j < grid[i].length; j++) { //iterates through the columns of the 2D list
                grid[i][j] = new Dot(j, (size-i) - 1); //set the grid to corrensponding dot values
            }
        }
    }

 
    public Sprite[][] getGrid(){return grid;}


    //method that places sprite in a new slot. 
    public void placeSprite(Sprite s){ //place sprite in new spot
        int x = s.getX(); //set x to Sprite s' x value
        int y = s.getY();//set y to Sprite s' y value
            grid[(size - y) - 1][x] = s; //sets the grid element to the sprite 
    }

    public void placeSprite(Sprite s, String direction) { //place sprite in a new spot based on direction
        int newX = s.getX(); //initializing newX to x value of the sprite 
        int newY = s.getY(); //initializing newY to y value of the sprite 

        if (direction.equals("w")) { //if the direction is up and the user types "w"
            grid[(size - (newY-1)) - 1][newX] = new Dot(newX, (size - (newY-1)) - 1); //this goes to the element from before move() was called
            grid[(size - newY) - 1][newX] = s; //sets the current element, where Sprite s is, to display Sprite s on the grid
        } else if (direction.equals("s")) { //if the direction is down and the user types "s"
            grid[(size - (newY+1)) - 1][newX] = new Dot(newX, (size - (newY+1)) - 1);
            grid[(size - newY) - 1][newX] = s;
        } else if (direction.equals("a")) {//if the direction is left and the user types "a"
            grid[(size - newY) - 1][newX+1] = new Dot(newX - 1, (size - newY) - 1);
            grid[(size - newY) - 1][newX] = s;
        } else if (direction.equals("d") ) {//if the direction is right and the user types "d"
            grid[(size - newY) - 1][newX-1] = new Dot(newX + 1, (size - newY) - 1);
            grid[(size - newY) - 1][newX] = s;
        }

    }
    //displays the current grid on the screen
    public void display() { //print out the current grid to the screen 
        for (int i = 0; i < grid.length; i++) {    //iterates through the 2D grid in rows
            for (int j = 0; j < grid[i].length; j++) { //iterates through the 2D grid in columns
                Sprite sprite = grid[i][j]; //sets element into sprite for checking of objects
                if (sprite instanceof Player) { // if the Sprite is a player 
                    System.out.print("🟦 "); //prints out P
                } else if (sprite instanceof Enemy) { //if the Sprite is an enemy
                    System.out.print("🟥 "); //prints out E
                } else if (sprite instanceof Trophy ) { //if the sprite is a trophy
                    System.out.print("⬜ "); //prints out W
                } else if (sprite instanceof Treasure) { //if sprite is a treasure
                    System.out.print("🟨 "); //prints out T
                } else {
                    System.out.print("⬛ "); //everything else is a .
                }
            }
            System.out.println(); //starts the next row after the end of the columns
        }

    }

    //get object class for interact parameter that requires an object
    //returns the object at the parameter x and y values
    public Object getObjectAt(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) { //checks if its inbound
            return grid[(size - y - 1)][x]; // Return object at (x, y)
        }
        return null; //if its out of bounds, return null
    }
    //when lives equals 0, game over
    //this method displays loss screen
    public void gameover(){ //use this method to display a loss
            System.out.println();
            System.out.println("==============================");
            System.out.println("         GAME OVER          ");
            System.out.println("==============================");
            System.out.println("You lost all your lives.");
            System.out.println("Better luck next time!");
            System.out.println("==============================");
            System.out.println();
        }
        
    //when all treasures and trophy has been retrieved with lives still greater than 0
    //this method displays win screen 
    public void win(){ //use this method to display a win 
            System.out.println();
            System.out.println("==============================");
            System.out.println("         YOU WIN!!!         ");
            System.out.println("==============================");
            System.out.println("You collected all treasures ");
            System.out.println("...and reached the trophy !");
            System.out.println("==============================");
            System.out.println();
    }
}