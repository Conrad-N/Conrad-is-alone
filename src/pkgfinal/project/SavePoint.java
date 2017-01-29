package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class SavePoint {

    private int num;
    private Rectangle2D.Double rect;

    public SavePoint(double x, double y, double width, double height, int num) {
        this.num = num;
        this.rect = new Rectangle2D.Double(x, y, width, height);
    }

    public Rectangle2D.Double getRect() {
        return rect;
    }

    public void draw(DConsole dc, Player p) {
        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        dc.setPaint(Color.WHITE);
        dc.drawRect(rect.getX() - p.getScroll(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public int getNum() {
        return num;
    }
}