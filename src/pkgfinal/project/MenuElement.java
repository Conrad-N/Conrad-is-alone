package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import static pkgfinal.project.FinalProject.playSound;

public class MenuElement {

    int x;
    int y;
    int height;
    int width;
    boolean mousedOver;
    Rectangle2D rect;
    String text;
    String sound;
    Color boxColor;
    Color textColor;
    Font smallFont;
    Font bigFont;

    public MenuElement(int x, int y, int width, int height, String sound, String text, Color boxColor, Color textColor, String font, int fontSize) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.rect = new Rectangle2D.Double(x - width / 2, y - height / 2, width, height);
        this.text = text;
        this.boxColor = boxColor;
        this.textColor = textColor;
        this.smallFont = new Font(font, Font.PLAIN, fontSize);
        this.bigFont = new Font(font, Font.BOLD, (int) (fontSize * 1.1));
        this.sound = sound;
    }

    //Plays sounds if something gets moused over, but it makes sure the sound is only played once
    public void playSoundOnMouseOver(Point2D p) {
        if (rect.contains(p) && !mousedOver) {
            mousedOver = true;
            playSound(this.sound);
        } else if (!rect.contains(p)) {
            mousedOver = false;
        }
    }

    public boolean isMousedOver(Point2D p) {
        return rect.contains(p);
    }

    public boolean isPressed(Point2D p, boolean isMousePressed) {
        return (isMousePressed && this.isMousedOver(p));
    }

    //The string of the name of the audio file
    public String getSound() {
        return this.sound;
    }

    //Draw things with a bigger box/font if needed
    public void draw(DConsole dc, boolean big) {
        dc.setOrigin(DConsole.ORIGIN_CENTER);
        if (boxColor != null) {
            dc.setPaint(boxColor);
            if (big) {
                dc.fillRect(this.x, this.y, this.width * 1.1, this.height * 1.1);
            } else {
                dc.fillRect(this.x, this.y, this.width, this.height);
            }
        }

        if (this.text != null) {
            dc.setPaint(this.textColor);
            if (big) {
                dc.setFont(this.bigFont);
            } else {
                dc.setFont(this.smallFont);
            }
            dc.drawString(this.text, this.x, this.y - this.height * 0.12);
        }
    }

    public void setText(String s) {
        this.text = s;
    }
}
