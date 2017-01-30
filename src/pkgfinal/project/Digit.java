package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import static pkgfinal.project.FinalProject.playSound;

public class Digit {

    int x;
    int y;
    char character;
    String sound;
    Color textColor;
    Font font;

    public Digit(int x, int y, String sound, char c, Color textColor, String font, int fontSize) {
        this.x = x;
        this.y = y;
        this.character = c;
        this.textColor = textColor;
        this.font = new Font(font, Font.PLAIN, fontSize);
        this.sound = sound;
    }

    //Draw things with a bigger box/font if needed
    public void draw(DConsole dc) {
        dc.setPaint(this.textColor);
        dc.setFont(this.font);
        dc.drawString(this.character, this.x, this.y);
    }

    public void changeChar(DConsole dc) {
        if (dc.getKeyPress(38)) {
            this.character++;
            if (this.character < 'A') {
                this.character = 'A';
            }
            playSound(this.sound);
        } else if (dc.getKeyPress(40)) {
            this.character--;
            if (this.character > 'z') {
                this.character = 'z';
            }
            playSound(this.sound);
        }
    }
    
    public char getChar() {
        return character;
    }
}
