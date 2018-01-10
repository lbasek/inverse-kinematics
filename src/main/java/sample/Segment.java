package sample;

import com.badlogic.gdx.math.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class Segment {
    private Vector2 a;
    private Vector2 b;
    private float length;
    private float angle;
    private Segment parent;
    private Segment child;

    Segment(float x, float y, float length, float angle) {
        this.a = new Vector2(x, y);
        this.b = new Vector2();
        this.length = length;
        this.angle = angle;
        calcB();
    }

    Segment(Segment parent, float length, float angle) {
        this.a = parent.getB();
        this.b = new Vector2();
        this.length = length;
        this.angle = angle;
        this.parent = parent;
        calcB();
    }

    private void calcB() {
        float dx = length * (float) Math.cos(angle * Math.PI / 180f);
        float dy = length * (float) Math.sin(angle * Math.PI / 180f);
        b.set(a.x + dx, a.y + dy);
    }

    private void follow(float tX, float tY) {
        Vector2 target = new Vector2(tX, tY);
        Vector2 dir = new Vector2(tX - a.x, tY - a.y);
        angle = dir.angle();
        dir.setLength(length);
        dir.scl(-1);
        a = target.add(dir);
    }

    public void follow() {
        follow(child.getA().x, child.getA().y);
    }

    public void update(float x, float y) {
        follow(x, y);
        calcB();
    }

    public void update() {
        follow();
        calcB();
    }

    public void setChild(Segment child) {
        this.child = child;
    }

    public void setA(Vector2 a) {
        this.a = a;
        calcB();
    }

    public Vector2 getA() {
        return a;
    }

    public Vector2 getB() {
        return b;
    }

    public Segment getParent() {
        return parent;
    }

    public Vector2 getParentB() {
        return getParent().getB();
    }

    void draw(GraphicsContext gc) {
        gc.setStroke(javafx.scene.paint.Color.GRAY);
        gc.setLineWidth(5);
        gc.strokeLine(a.x, a.y, b.x, b.y);
    }


}
