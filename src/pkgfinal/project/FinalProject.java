package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.*;
import javax.sound.sampled.*;
import java.io.*;

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

                    if (dc.getKeyPress(27)) {
                        gameState = 0;
                        playSound("button2");
                    }

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
                        if (playersAtSaves[i] == 3) { //If any set of check points is filled let the player save
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
                            gameState = 5;
                            elements.clear();
                            fileInputs.clear();
                            fileInputs.add(new Scanner(new File("Saves/save1.txt")));
                            fileInputs.add(new Scanner(new File("Saves/save2.txt")));
                            fileInputs.add(new Scanner(new File("Saves/save3.txt")));
                            elements.add(new MenuElement(dc.getWidth() / 2, 50, 0, 0, null, "Pick a game file to save over.", null, Color.BLACK, "Times New Roman", 50));
                            elements.add(new MenuElement(dc.getWidth() / 4 * 1, 350, 180, 100, "button1", fileInputs.get(0).nextLine(), Color.CYAN, Color.BLACK, "Times New Roman", 40));
                            elements.add(new MenuElement(dc.getWidth() / 4 * 2, 350, 180, 100, "button1", fileInputs.get(1).nextLine(), Color.CYAN, Color.BLACK, "Times New Roman", 40));
                            elements.add(new MenuElement(dc.getWidth() / 4 * 3, 350, 180, 100, "button1", fileInputs.get(2).nextLine(), Color.CYAN, Color.BLACK, "Times New Roman", 40));

                            String name = new String("");
                            for (int j = 0; j < name.length(); j++) {
                                name += digits.get(j).getChar();
                            }
                            while (gameState == 5) {
                                mousePos.setLocation(dc.getMouseXPosition(), dc.getMouseYPosition());

                                if (dc.getKeyPress(27)) {
                                    gameState = 0;
                                    playSound("button2");
                                }

                                elements.get(0).draw(dc, false);

                                //Draws all the elements and goes into file writing things if they're clicked on
                                for (int j = 1; j < 4; j++) {
                                    elements.get(j).draw(dc, elements.get(j).isMousedOver(mousePos));
                                    if (elements.get(j).isPressed(mousePos, dc.isMouseButton(1))) {
                                        PrintStream fileOut = null;
                                        try {
                                            fileOut = new PrintStream(new File("Saves/save" + Integer.toString(i) + ".txt"));
                                        } catch (FileNotFoundException e) {
                                            e.getStackTrace();
                                        }
                                        fileOut.println(name);
                                        fileOut.print(players.get(0).getX());
                                        fileOut.print(players.get(0).getY());
                                        fileOut.print(players.get(1).getX());
                                        fileOut.print(players.get(1).getY());
                                        fileOut.print(players.get(2).getX());
                                        fileOut.print(players.get(2).getY());
                                        gameState = 4;
                                    }
                                }

                                dc.setOrigin(DConsole.ORIGIN_CENTER);
                                dc.setPaint(Color.BLACK);
                                dc.fillEllipse(mousePos.getX(), mousePos.getY(), 10, 10);

                                dc.redraw();
                                dc.pause(20);
                                dc.clear();
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
