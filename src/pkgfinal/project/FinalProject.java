
package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class FinalProject {

    public static Random randGen = new Random();

    public static void main(String[] args) throws FileNotFoundException {

        DConsole dc = new DConsole(1200, 800);
        playSound("miceonvenus");//Main background music
        dc.setMouseMode(DConsole.CURSOR_HIDDEN);
        dc.setBackground(Color.DARK_GRAY);
        ArrayList<Digit> digits = new ArrayList<>();
        ArrayList<MenuElement> elements = new ArrayList<>();
        ArrayList<Scanner> fileInputs = new ArrayList<>();
        ArrayList<Structure> structs = new ArrayList<>();
        structs.add(new Structure(0, 750, 1100, 100));
        structs.add(new Structure(1140, 700, 500, 100));
        structs.add(new Structure(1000, 705, 75, 50));
        ArrayList<SavePoint> savePoints = new ArrayList<>();
        savePoints.add(new SavePoint(1500, 650, 20, 20, 0));
        ArrayList<Player> players = new ArrayList<>();

        
        //ground level base is 750, starting point
        //yellow jump is 125, he is 40 tall and 10 wide
        //red jump is 40, he is 20 tall and 20 wide
        //blue jump is 60 high, he is 15 tall and 30 wide
        
        structs.add(new Structure(0, 750, 1100, 100)); //bottom ground1
        structs.add(new Structure(1000, 700, 700, 100)); //bottom ground 2

        //first bit
        
        //the goal here is first you need all three on the thin structure, then you need to boost the blue one off of the yellow&red to get it to
        //the highest platform.  Yellow and red do not need to get up there, they need to end on the thin platform
        structs.add(new Structure(400, 680, 120, 15)); //first thin line, red cant reach alone
        structs.add(new Structure(420, 680, 45, 53)); //only blue can go under block sicking down
        structs.add(new Structure(460, 535, 50, 120)); //now yellow jumps on red (tall tall first block)
        structs.add(new Structure(500, 730, 40, 20)); //box near the bottom under everything, if red falls off he boosts off blue
        structs.add(new Structure(590, 660, 80, 10)); //box goal to get all 3 on of part 1
        structs.add(new Structure(625, 660, 10, 125)); //pillar
       
        //part 2
        structs.add(new Structure(350, 500, 75, 20)); //bottom of highest plat
        structs.add(new Structure(350, 150, 75, 325)); //now only blue can go through
        structs.add(new Structure(250, 535, 85, 10)); //bottom high skinny
        structs.add(new Structure(175, 515, 50, 50)); //more to the left
        structs.add(new Structure(250, 460, 35, 10)); //tiny plat
        structs.add(new Structure(325, 410, 30, 65)); //side jump
        structs.add(new Structure(260, 360, 10, 10));  //super tiny
        structs.add(new Structure(340, 310, 10, 5)); //even smaller higher
        structs.add(new Structure(255, 250, 25, 20)); //after that
        structs.add(new Structure(325, 200, 100, 25)); //last jump'
        structs.add(new Structure(120, 120, 45, 335)); //blocker of the left
        structs.add(new Structure(120, 50, 100, 75));
        //now your on top of that second structure of part two
        
        //part 3
        structs.add(new Structure(510, 200, 200, 15));//right of high
        structs.add(new Structure(800, 230, 150, 25)); //lower
        structs.add(new Structure(690, 270, 200, 100)); //big fucking chunk
        structs.add(new Structure(690, 400, 200, 320));
        
        structs.add(new Structure(620, 410, 30 ,30));//fist jump for yellow
        structs.add(new Structure(470, 250, 75, 170));
        
        
        //red ladder up
        structs.add(new Structure(990, 740, 25, 10));
        structs.add(new Structure(890, 710, 25, 10));
        structs.add(new Structure(980, 680, 25, 10));
        structs.add(new Structure(890, 650, 25, 10));
        structs.add(new Structure(980, 620, 25, 10));
        structs.add(new Structure(890, 590, 25, 10));
        structs.add(new Structure(980, 560, 25, 10));
        structs.add(new Structure(890, 530, 25, 10));
        structs.add(new Structure(980, 500, 25, 10));
        structs.add(new Structure(890, 470, 25, 10));
        structs.add(new Structure(980, 440, 25, 10));
        structs.add(new Structure(890, 410, 25, 10));
        structs.add(new Structure(980, 380, 25, 10));
        structs.add(new Structure(890, 350, 25, 10));
        structs.add(new Structure(980, 320, 25, 10));
        structs.add(new Structure(890, 290, 25, 10));
        
        
        //End of Level 1 is when all
        
        structs.add(new Structure(1000, 300, 200, 400));
                
        structs.add(new Structure(995, 730, 10, 50)); //tiny abract ledge for red
        
        structs.add(new Structure(1000, 740, 76, 50)); //first lump near hole
        structs.add(new Structure(-500, 0, 505, 900)); //Behind you
        structs.add(new Structure(0, 0, 5000, 60)); //roof
        
        
        
        structs.add(new Structure(1000, 705, 75, 50));
        
        int gameState = 0;
        int currentPlayer = 0;
        int[] playersAtSaves = new int[3];

        while (true) {
            Point2D mousePos = new Point2D.Double();
            //Clear the previous elements and make new ones once before entering the while loop
            //This is done for every single gameState
            if (gameState == 0) {
                elements.clear();
                elements.add(new MenuElement(dc.getWidth() / 2, 100, 0, 0, null, "Adventure Quest: The Wandering!", null, Color.WHITE, "Times New Roman", 50));
                elements.add(new MenuElement(dc.getWidth() / 2, 400, 200, 70, "button1", "New Game", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 2, 500, 200, 70, "button1", "Load Game", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 2, 600, 200, 70, "button1", "Instructions", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                while (gameState == 0) {
                    mousePos.setLocation(dc.getMouseXPosition(), dc.getMouseYPosition());
                    if (dc.getKeyPress(27)) {
                        playSound("button2");
                        dc.pause(200);
                        System.exit(0);
                    }
                    elements.get(0).draw(dc, false);
                    //Cheking if things are moused over or pressed and reacting acordingly
                    for (int i = 1; i < elements.size(); i++) {
                        elements.get(i).draw(dc, elements.get(i).isMousedOver(mousePos));
                        elements.get(i).playSoundOnMouseOver(mousePos);
                        if (elements.get(i).isPressed(mousePos, dc.isMouseButton(1))) {
                            gameState = i;
                            if (gameState == 1) {
                                gameState = 4;
                            }
                        }
                    }

                    dc.setOrigin(DConsole.ORIGIN_CENTER);
                    dc.setPaint(Color.BLACK);
                    dc.fillEllipse(mousePos.getX(), mousePos.getY(), 10, 10);

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            } else if (gameState == 3) {
                elements.clear();
                elements.add(new MenuElement(dc.getWidth() / 2, 200, 0, 0, null, "Use 'w', 'a', 's', and 'd' to move the characters around.", null, Color.WHITE, "Times New Roman", 25));
                elements.add(new MenuElement(dc.getWidth() / 2, 250, 0, 0, null, "Use 'q' and 'e' to chang between characters. ", null, Color.WHITE, "Times New Roman", 25));
                elements.add(new MenuElement(dc.getWidth() / 2, 300, 0, 0, null, "Press 'esc' to exit any menus or the game.", null, Color.WHITE, "Times New Roman", 25));
                elements.add(new MenuElement(dc.getWidth() / 2, 350, 0, 0, null, "Press 'esc' on the main menu to close the game.", null, Color.WHITE, "Times New Roman", 25));
                while (gameState == 3) { //Load Game
                    if (dc.getKeyPress(27)) {
                        gameState = 0;
                        playSound("button2");
                    }

                    for (int i = 0; i < 4; i++) {
                        elements.get(i).draw(dc, false);
                    }

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            } else if (gameState == 2) {
                elements.clear();
                fileInputs.clear();
                fileInputs.add(new Scanner(new File("Saves/save1.txt")));
                fileInputs.add(new Scanner(new File("Saves/save2.txt")));
                fileInputs.add(new Scanner(new File("Saves/save3.txt")));
                elements.add(new MenuElement(dc.getWidth() / 2, 50, 0, 0, null, "Pick a game file to load.", null, Color.BLACK, "Times New Roman", 50));
                elements.add(new MenuElement(dc.getWidth() / 4 * 1, 350, 180, 100, "button1", fileInputs.get(0).nextLine(), Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 4 * 2, 350, 180, 100, "button1", fileInputs.get(1).nextLine(), Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 4 * 3, 350, 180, 100, "button1", fileInputs.get(2).nextLine(), Color.CYAN, Color.BLACK, "Times New Roman", 40));
                while (gameState == 2) { //Load Game
                    mousePos.setLocation(dc.getMouseXPosition(), dc.getMouseYPosition());

                    if (dc.getKeyPress(27)) {
                        gameState = 0;
                        playSound("button2");
                    }

                    elements.get(0).draw(dc, false);

                    //Draws all the elements and goes into file writing things if they're clicked on
                    for (int i = 1; i < 4; i++) {
                        elements.get(i).draw(dc, elements.get(i).isMousedOver(mousePos));
                        if (elements.get(i).isPressed(mousePos, dc.isMouseButton(1))) {
                            players.add(new Player(fileInputs.get(i - 1).nextInt(), fileInputs.get(i - 1).nextInt(), 20, 20, 4, Color.RED));
                            players.add(new Player(fileInputs.get(i - 1).nextInt(), fileInputs.get(i - 1).nextInt(), 10, 40, 7, Color.YELLOW));
                            players.add(new Player(fileInputs.get(i - 1).nextInt(), fileInputs.get(i - 1).nextInt(), 30, 15, 5, Color.BLUE));
                            gameState = 3;
                        }
                    }

                    dc.setOrigin(DConsole.ORIGIN_CENTER);
                    dc.setPaint(Color.BLACK);
                    dc.fillEllipse(mousePos.getX(), mousePos.getY(), 10, 10);

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            } else if (gameState == 4) {
                if (players.size() == 0) {
                    players.add(new Player(200, 500, 20, 20, 4, Color.RED));
                    players.add(new Player(300, 500, 10, 40, 7, Color.YELLOW));
                    players.add(new Player(400, 500, 30, 15, 5, Color.BLUE));
                }
                while (gameState == 4) { //Main game loop
                    drawPicture(dc, players.get(currentPlayer), "pixelForest");

                    if (dc.getKeyPress('q')) {
                        currentPlayer--;
                        if (currentPlayer < 0) {
                            currentPlayer = players.size() - 1;
                        }
                        players.get(currentPlayer).setScroll(dc.getWidth() / 2);
                    }
                    if (dc.getKeyPress('e')) {
                        currentPlayer++;
                        if (currentPlayer > players.size() - 1) {
                            currentPlayer = 0;
                        }
                        players.get(currentPlayer).setScroll(dc.getWidth() / 2);
                    }

                    for (Player p : players) {
                        p.gravityForce();
                        p.frictionForce();
                        p.resetGrounded();
                        p.isTouchingStructure(structs);
                        p.isTouchingPlayer(players);
                        playersAtSaves = new int[3];//Resetting all the values to 0
                        int i = p.isTouchingSavePoint(savePoints);
                        if (i != -1) { //Checking to see if all the players are at SavePoints
                            playersAtSaves[i]++;
                        }
                    }
                    //You can only control the current player
                    players.get(currentPlayer).moveCommands(dc);

                    for (Player p : players) {
                        p.recordPrevValues();
                        p.move();
                        p.draw(dc, players.get(currentPlayer));
                    }

                    players.get(currentPlayer).scroll();

                    for (Structure s : structs) {
                        s.draw(dc, players.get(currentPlayer));
                    }
                    //Display which player you're controling
                    players.get(currentPlayer).drawArrow(dc);

                    for (int i = 0; i < playersAtSaves.length; i++) {
                        if (playersAtSaves[i] == 3) { //If any set of corresponding check points is filled let them save
                            digits.clear();
                            for (int j = 0; j < 8; j++) {
                                digits.add(new Digit(dc.getWidth() / 16 * (3 + i), 500, "button1", 'a', Color.WHITE, "Times New Roman", 30));
                            }
                            int currentDigit = 0;
                            while (currentDigit != 8) {
                                dc.clear();
                                if (dc.getKeyPress(37)) {
                                    currentDigit = Math.max(currentDigit - 1, 0);
                                } else if (dc.getKeyPress(39)) {
                                    currentDigit++;
                                }
                                for (int j = 0; j < digits.size(); j++) {
                                    if (j == currentDigit) {
                                        digits.get(j).changeChar(dc);
                                        if (System.currentTimeMillis() % 1000 < 500) {
                                            digits.get(j).draw(dc);
                                        }
                                    } else {
                                        digits.get(j).draw(dc);
                                    }
                                }
                                dc.redraw();
                                dc.pause(20);
                            }
                            
                        }
                    }

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            } else { //Incase gameState end up with an invalid value for some reason.
                System.out.println("You shouldn't be seeing this.");
                System.exit(-1);
            }
        }
    }

    public static void playSound(String s) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("Sounds/" + s + ".wav")));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static void drawPicture(DConsole dc, Player p, String s) {
        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        dc.drawImage("Pictures/" + s + ".jpg", 0 - p.getScroll(), 0);
    }
}
