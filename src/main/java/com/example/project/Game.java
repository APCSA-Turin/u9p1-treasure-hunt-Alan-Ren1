package com.example.project;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size; 
    private int difficulty; // 1=easy, 2=medium, 3=hard

    public Game(int size){ //the constructor should call initialize() and play()
        this.difficulty = 2; // Default medium difficulty
        
        if (size > 0) { //once size has been chosen
            this.size = size; //initalize size
            initialize(); //initialize the game values
            play(); //run the game 
        } else {
            Scanner scanner = new Scanner(System.in); // create a scanner
            selectDifficulty(scanner); //if size was not chosen, select difficulty to choose size
            initialize(); //initialize the game values 
            play(); //run the game
            scanner.close(); //close the scanner
        }
    }

    public static void clearScreen() { //do not modify
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-based (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){ //write your game logic here
        Scanner scanner = new Scanner(System.in); //create scanner
        boolean playing = true; //initialize playing to true
        
        while (playing) { //while playing is true
            boolean gameRunning = true; // initalize gameRunning to true because the game is running
            while (gameRunning) { //while gameRunning is true
                try {
                    Thread.sleep(100); // Wait for 1/10 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clearScreen(); // Clear the screen at the beginning of the while loop

                showGameStats(); //show the current game stats 
                System.out.print("Enter a direction (w,a,s,d) or 'q' to exit: "); //prints the controls of the game
                String input = scanner.nextLine().toLowerCase(); //set input to what the user types in lowercase 
                
                if (input.equals("q")) { //if user inputs q
                    gameRunning = false; //set gameRunning to false 
                    playing = false; //set playing to false to stop the loop
                } else if (input.equals("w") || input.equals("a") || input.equals("s") || input.equals("d")) { //if they input directional letters
                    boolean valid = player.isValid(size, input); //set valid to whether the coordinates the sprite is going to is within bounds
                    if (valid) { //if it is within bounds
                        int targetX = player.getX(); //set targetX to the player's x value
                        int targetY = player.getY(); //set targetY to the player's y value
                        
                        //update coordinates based on direction
                        if (input.equals("w")) {
                        targetY += 1;
                        }
                        else if (input.equals("s")) {
                         targetY -= 1;
                        }
                        else if (input.equals("a")) { 
                        targetX -= 1;
                        }
                        else if (input.equals("d")) {
                         targetX += 1;
                        }

                        Object obj = grid.getObjectAt(targetX, targetY); //set obj to the object where the sprite is going to 
                        player.interact(size, input, 2, obj); //interact with the object 
                        if (obj instanceof Trophy  && player.getTreasureCount() == 2) { //if trophy and treasure count is 2, then win
                        player.move(input); //move player
                        grid.placeSprite(player, input); //place player on the grid
                        clearScreen(); //clear the screen
                        grid.win(); //show win screen
                        gameRunning = false; //set gameRunning to false 
                        }
                        else if (player.getLives() == 0) { //if no more lives
                            clearScreen(); //clear the screen
                            grid.gameover(); //show gameover screen
                            gameRunning = false; //set gameRunning to false
                        }
                        else if (!(obj instanceof Trophy)) { //as long as it is not trophy, move and place sprite
                            player.move(input);
                            grid.placeSprite(player, input);
                        }
                    }
                }
            }
            // play again prompt
            if (playing && gameRunning == false) { //if game is not running and not playing
                System.out.print("Play again? (y): "); //print out prompt to play again
                String choice = scanner.nextLine().toLowerCase(); //set choice to the next user input in lowercase
                if (choice.equals("y")) { //if user types y
                    // reset game with new difficulty selection
                    selectDifficulty(scanner);
                    initialize();
                } else {
                    //otherwise stop playing / break
                    playing = false;
                }
            }
        }
        scanner.close();
    }

    //initialize method to initialize game to be ready to play
    public void initialize(){ //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
        grid = new Grid(size); //create new grid
        player = new Player(0, 0); //set player at 0, 0
        
        // set lives based on difficulty
        if (difficulty == 1) { //difficulty 1
            player.setLives(3); //3 lives
        } else if (difficulty == 2) { //difficulty 2 
            player.setLives(2); //2 lives
        } else { //otherwise difficulty 3 
            player.setLives(1); //1 life
        }
        
        //create enemies based on difficulty
        int enemyCount; //create enemyCount integer
        if (difficulty == 1) { //difficulty 1
            enemyCount = 2; //2 enemies
        } else if (difficulty == 2) { //difficulty 2
            enemyCount = 3; //3 enemies
        } else { //otherwise difficulty 3
            enemyCount = 5; //5 enemies
        }
        
        //create enemies
        enemies = new Enemy[enemyCount]; //create an array of enemies

        for (int i = 0; i < enemyCount; i++) {
            int x = (int)(Math.random() * size); //random x between 0 and size-1
            int y = (int)(Math.random() * size); //random y between 0 and size-1
        
            enemies[i] = new Enemy(x, y); //enemy at index i is created with the x and y coordinates
            grid.placeSprite(enemies[i]); //place enemy
        }
        //create treasures
        treasures = new Treasure[2]; //2 treasures are created in the treasures array
        treasures[0] = new Treasure(2, 2); //set first treasure to 2,2
        treasures[1] = new Treasure(size-3, size-3); //set second treasure to size-3, size-3
        for (Treasure treasure : treasures) {
            grid.placeSprite(treasure); //place all of the treasures
        }
        
        // preate trophy
        trophy = new Trophy(size-1, size-1);  //create trophy at size-1, size-1
        grid.placeSprite(trophy); //place the trophy
        
        grid.placeSprite(player); //place player 
    }

    public void selectDifficulty(Scanner scanner) {
        System.out.println("\nChoose difficulty level:"); 
        System.out.println("1. Easy (8x8 grid, 2 enemies, 3 lives)");
        System.out.println("2. Medium (10x10 grid, 3 enemies, 2 lives)");
        System.out.println("3. Hard (12x12 grid, 5 enemies, 1 life)");
        
        while (true) { //while loop to keep iterating until there is a valid input
            System.out.print("Enter choice (1-3): "); 
            String input = scanner.nextLine(); //scan the next line for input
            
            if (input.equals("1")) { //if player types 1
                difficulty = 1; //difficulty is set to 1
                size = 8; //size is set to 8
                break; //stops selectdifficulty to initialize
            } else if (input.equals("2")) { //if player types 2
                difficulty = 2; //difficulty is set to 2
                size = 10; //size is set to 10
                break; //stops selectdifficulty to initialize
            } else if (input.equals("3")) { //if player types 3
                difficulty = 3; //difficulty is set to 3
                size = 12; //size is set to 10
                break; //stops selectdifficulty to initialize
            }
            System.out.println("Invalid input. Please enter 1, 2, or 3.");
        }
    }

    //shows the stats of the running game
    public void showGameStats() {
        String difficultyString; //create the string difficultyString variable 
        if (difficulty == 1) { //if difficulty is 1
            difficultyString = "Easy"; //set difficultyString to Easy
        } else if (difficulty == 2) { //if difficulty is 2
            difficultyString = "Medium"; //set difficultyString to Medium
        } else { //otherwise (3)
            difficultyString = "Hard"; //set difficultyString to Hard
        }
        grid.display(); //display the grid

        System.out.println("Difficulty: " + difficultyString); //prints difficulty
        System.out.println("Player: " + player.getCoords()); //prints the coordinates of the player
        System.out.println("Player: "+ player.getRowCol(size)); //prints the row col of the player
        System.out.println("Treasure Collected: " + player.getTreasureCount() + "/2"); //prints the number of treasure the player got out of two
        System.out.println("Lives remaining: " + player.getLives()); //prints the number of lives remaining the player has
    }
    public static void main(String[] args) {
        new Game(0); // Start with size 0 to display difficulty selection
    }
}