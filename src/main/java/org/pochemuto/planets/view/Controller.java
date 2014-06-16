package org.pochemuto.planets.view;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.pochemuto.planets.methods.Euler;
import org.pochemuto.planets.problem.Body;
import org.pochemuto.planets.problem.Problem;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

/**
 * @author pochemuto
 */
public class Controller implements Initializable {
    private final Problem problem = new Problem(new Euler());
    public Path path;
    private double SCALE = 400;

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    @FXML
    private Pane pane;

    private final Point viewCenter = new Point(50, 50);
    private final Point deltaDrag = new Point();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Random r = new Random();

        int count = 10;
        SCALE = 600;
        while (--count > 0) {
            Body b = new Body(r.nextDouble() * 100);

            b.setX(r.nextDouble());
            b.setY(r.nextDouble());
            b.setVx((r.nextDouble() - 0.5)/5000);
            b.setVy((r.nextDouble() - 0.5)/5000);

            Circle image = new Circle();
            image.setRadius(3);
            image.setFill(Color.GRAY);

            bind(b, image);
            pane.getChildren().add(image);
            problem.getBodies().add(b);
        }

        AnimationTimer timer = new AnimationTimer() {
            private long start;

            @Override
            public void handle(long l) {
                if (l - start > 0.01 * 1e9) {
                    problem.next(2);
                    start = l;
                }

            }
        };
        timer.start();
    }

    private void bind(Body body, Shape shape) {
        shape.layoutXProperty().bind(Bindings.multiply(body.xProperty(), SCALE).add(viewCenter.xProperty()));
        shape.layoutYProperty().bind(Bindings.multiply(body.yProperty(), SCALE).add(viewCenter.yProperty()));
    }

    public void paneDrag(MouseEvent event) {
        viewCenter.setX(event.getX() + deltaDrag.getX());
        viewCenter.setY(event.getY() + deltaDrag.getY());
    }

    public void panePress(MouseEvent event) {
        deltaDrag.setX(viewCenter.getX() - event.getX());
        deltaDrag.setY(viewCenter.getY() - event.getY());
    }

    public void paneClick(MouseEvent event) {
//        problem.next(0.1);
    }
}
