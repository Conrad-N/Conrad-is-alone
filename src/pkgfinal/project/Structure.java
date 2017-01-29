package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Structure {

    private double height;
    private double width;
    private double x;
    private double y;

    public Structure(double x, double y, double width, double height) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    
    public void draw(DConsole dc, Player p) {
        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        dc.setPaint(Color.BLACK);
        dc.fillRect(this.x - p.getScroll(), this.y, this.width, this.height);
    }
}
