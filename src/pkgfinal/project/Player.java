package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Player {

    private boolean isGrounded;
    private boolean isAlive;
    private boolean scrollingRight;
    private boolean scrollingLeft;
    private double xChange;
    private double yChange;
    private double x;
    private double y;
    private double width;
    private double height;
    private double prevX;
    private double prevY;
    private int jumpHeight;
    private int scroll;
    private Color color;

    public Player(double x, double y, double width, double height, int jumpHeight, Color color) {
        this.isGrounded = true;
        this.isAlive = true;
        this.xChange = 0;
        this.yChange = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.jumpHeight = jumpHeight;
        this.prevX = x;
        this.prevY = y;
        this.xChange = 0;
        this.scroll = 0;
        this.color = color;
    }

    public boolean isAlive() {
        return this.isAlive;
    }
    //Check players current position in both dimentions and compare it to the structure
    public void isTouchingStructure(ArrayList<Structure> structs) {
        for (Structure s : structs) {//Also checks the players previous position
            if (this.y + this.height > s.getY() && this.y < s.getY() + s.getHeight()) {
                if (this.x + this.width > s.getX() && this.prevX + this.width <= s.getX()) {
                    this.x = s.getX() - this.width;
                    this.xChange = 0;
                } else if (this.x < s.getX() + s.getWidth() && this.prevX >= s.getX() + s.getWidth()) {
                    this.x = s.getX() + s.getWidth();
                    this.xChange = 0;
                }
            }
            if (this.x + this.width > s.getX() && this.x < s.getX() + s.getWidth()) {
                if (this.y + this.height > s.getY() && this.prevY + this.height <= s.getY()) {
                    this.y = s.getY() - this.height;
                    this.yChange = 0;
                    this.isGrounded = true;                                     //If the player is standing on something let them move
                } else if (this.y < s.getY() + s.getHeight() && this.prevY >= s.getY() + s.getHeight()) {
                    this.y = s.getY() + s.getHeight();
                    this.yChange = 0;
                }
            }
        }
    }
    //Same thing as above except it takes in players rather than structures
    public void isTouchingPlayer(ArrayList<Player> players) {
        for (Player p : players) {
            if (this.y + this.height > p.getY() && this.y < p.getY() + p.getHeight()) {
                if (this.x + this.width > p.getX() && this.prevX + this.width <= p.getX()) {
                    this.x = p.getX() - this.width;
                    this.xChange = 0;
                } else if (this.x < p.getX() + p.getWidth() && this.prevX >= p.getX() + p.getWidth()) {
                    this.x = p.getX() + p.getWidth();
                    this.xChange = 0;
                }
            }
            if (this.x + this.width > p.getX() && this.x < p.getX() + p.getWidth()) {
                if (this.y + this.height > p.getY() && this.prevY + this.height <= p.getY()) {
                    this.y = p.getY() - this.height;
                    this.yChange = 0;
                    this.isGrounded = true;
                } else if (this.y < p.getY() + p.getHeight() && this.prevY >= p.getY() + p.getHeight()) {
                    this.y = p.getY() + p.getHeight();
                    this.yChange = 0;
                }
            }
        }
    }
    
    public int isTouchingSavePoint(ArrayList<SavePoint> savePoints) {
        for (SavePoint s : savePoints) {
             if (s.getRect().contains(new Rectangle2D.Double(this.x, this.y, this.width, this.height - 1))) {
                 return s.getNum();
             }
        }
        return -1;
    }

    public double getScroll() {
        return this.scroll;
    }

    public void setScroll(int i) {
        this.scroll = (int) (this.x) - i;
    }

    public void recordPrevValues() {
        this.prevX = this.x;
        this.prevY = this.y;
    }

    public void gravityForce() {
        this.yChange += 0.2;
    }
    //Check lateral movement or let the player jump if they're on the ground
    public void moveCommands(DConsole dc) {
        if (this.isGrounded && dc.getKeyPress('w')) {
            yChange = -this.jumpHeight;
        }
        if (dc.isKeyPressed('d')) {
            this.xChange = Math.min(xChange + 1, 3);
        }
        if (dc.isKeyPressed('a')) {
            this.xChange = Math.max(xChange - 1, -3);
        }
    }

    public void move() {
        this.x += this.xChange;
        this.y += this.yChange;
    }
    //Slow the players movement as they slide along the floor
    public void frictionForce() {
        if (this.isGrounded) {
            xChange *= 0.8;
        }
    }

    public void draw(DConsole dc, Player p) {
        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        dc.setPaint(this.color);
        dc.fillRect(this.x - p.scroll, this.y, this.width, this.height);
        dc.setPaint(Color.BLACK);
        dc.drawRect(this.x - p.scroll, this.y, this.width, this.height);
    }
    //Drawing a triangle above the players head
    public void drawArrow(DConsole dc) {
        double[] Xs = {this.x + this.width / 2 - 7 - this.scroll, this.x + this.width / 2 - this.scroll, this.x + this.width / 2 + 7 - this.scroll};
        double[] Ys = {this.y - 11, this.y - 5, this.y - 11};
        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        dc.setPaint(Color.WHITE);
        dc.fillPolygon(Xs, Ys);
        dc.setPaint(Color.BLACK);
        dc.drawPolygon(Xs, Ys);
    }
    //Scroll the screen, this will simply add a position modifier when drawing things.
    public void scroll() {
        if (this.x - this.scroll > 1000) {
            this.scrollingRight = true;
        } else if (this.x - scroll < 200) {
            this.scrollingLeft = true;
        } else if (this.x - this.scroll < 800 && this.x - this.scroll > 400) {
            this.scrollingLeft = false;
            this.scrollingRight = false;
        }

        if (this.scrollingRight) {
            this.scroll += 6;
        } else if (this.scrollingLeft) {
            this.scroll -= 6;
        }
    }

    public void resetGrounded() {
        this.isGrounded = false;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }
}
