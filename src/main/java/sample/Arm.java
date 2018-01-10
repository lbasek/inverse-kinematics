package sample;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;


public class Arm {
    private static final float STARTING_ANGLE = -90f;
    private List<Segment> segments = new ArrayList<>();
    private Vector2 target, base;

    public Arm(float x, float y, int numOfSegments, float armLength) {

        float segmentLength = armLength / (float) numOfSegments;

        //First segment
        segments.add(new Segment(x, y, segmentLength, STARTING_ANGLE));

        //Other segments
        for (int i = 1; i < numOfSegments; i++) {
            segments.add(new Segment(segments.get(i - 1), segmentLength, STARTING_ANGLE));
        }

        // Add children to all segments except last
        for (int i = 0; i < numOfSegments - 1; i++) {
            segments.get(i).setChild(segments.get(i + 1));
        }

        // Start point
        base = new Vector2(x, y);

        // End point
        target = new Vector2();
    }

    public void update() {
        // Set coordinates for last segment
        segments.get(segments.size() - 1).update(target.x, target.y);

        // Update segments to follow own parent
        for (int i = segments.size() - 2; i >= 0; i--) {
            segments.get(i).update();
        }

        segments.get(0).setA(base);

        for (int i = 1; i < segments.size(); i++) {
            segments.get(i).setA(segments.get(i).getParentB());
        }
    }

    // Set target point
    public void setTarget(float x, float y) {
        target.set(x, y);
    }

    // Draw all segments
    public void draw(GraphicsContext gc) {
        for (Segment segment : segments) segment.draw(gc);
    }

}