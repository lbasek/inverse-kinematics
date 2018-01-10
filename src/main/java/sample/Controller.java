package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


public class Controller {

    /**
     * Program views
     */

    @FXML
    private AnchorPane mainContent;

    @FXML
    private StackPane subPane;

    @FXML
    private Canvas canvas;

    @FXML
    private Button addButton;

    @FXML
    private Button clearCanvasButton;

    @FXML
    private Slider lengthSlider;

    @FXML
    private Slider segmentsSlider;

    @FXML
    private Label armLengthLabel;

    @FXML
    private Label numOfSegmentsLabel;


    /**
     * Program variables
     */

    private GraphicsContext gc;
    private Arm arm;
    private int segmentCount = 10;
    private int armLength = 150;

    @FXML
    public void initialize() {
        System.out.println("Init");

        gc = canvas.getGraphicsContext2D();
        segmentCount = ((int) segmentsSlider.getValue());
        armLength = ((int) lengthSlider.getValue());

        armLengthLabel.setText(String.format("Arm length: %s", String.valueOf(armLength)));
        numOfSegmentsLabel.setText(String.format("Number of segments: %s", String.valueOf(segmentCount)));


        initArm();

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::updateArm);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::updateArm);

        lengthSlider.valueProperty().addListener((ov, oldValue, newValue) -> {
            clearCanvas();
            armLength = newValue.intValue();
            initArm();
            armLengthLabel.setText(String.format("Arm length: %d", newValue.intValue()));
        });

        segmentsSlider.valueProperty().addListener((ov, oldValue, newValue) -> {
            clearCanvas();
            segmentCount = newValue.intValue();
            initArm();
            numOfSegmentsLabel.setText(String.format("Number of segments: %d", newValue.intValue()));
        });


    }

    private void updateArm(MouseEvent event) {
        clearCanvas();
        arm.setTarget((float) event.getX(), (float) event.getY());
        arm.update();
        arm.draw(gc);
    }

    private void initArm() {
        arm = new Arm((float) canvas.getWidth() / 2, (float) canvas.getHeight() / 2, segmentCount, armLength);
        arm.draw(gc);
    }

    @FXML
    void onAddCurveClick(MouseEvent event) {
        clearCanvas();
        initArm();
    }

    @FXML
    void onClearCanvasClick(MouseEvent event) {
        clearCanvas();
    }

    private void clearCanvas() {
        if (gc != null) gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


}
