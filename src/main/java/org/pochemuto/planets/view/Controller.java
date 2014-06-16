package org.pochemuto.planets.view;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.pochemuto.planets.methods.Euler;
import org.pochemuto.planets.problem.Body;
import org.pochemuto.planets.problem.Problem;
import org.pochemuto.planets.problem.planets.Earth;
import org.pochemuto.planets.problem.planets.Moon;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
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
    private Circle moonImage;

    private final Point viewCenter = new Point(50, 50);
    private final Point deltaDrag = new Point();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int t = 2;
        Random r = new Random();
//        while (t-- > 0) {
//            Body b = new Body(r.nextDouble());
//            b.setX(r.nextDouble());
//            b.setY(r.nextDouble());
//            b.setVx((r.nextDouble() - 0.5) * 10);
//            b.setVy((r.nextDouble() - 0.5) * 10);
//
//            Circle c = new Circle();
//            c.setFill(Color.BLACK);
//            c.setRadius(10);
//            c.layoutXProperty().bind(Bindings.multiply(b.xProperty(), SCALE));
//            c.layoutYProperty().bind(Bindings.multiply(b.yProperty(), SCALE));
//
//            pane.getChildren().add(c);
//            problem.getBodies().add(b);
//        }

//        Body sun = new Sun();
//        sun.setX(0.5);
//        sun.setY(0.5);
//
//        Circle sunImage = new Circle();
//        sunImage.setRadius(30);
//        sunImage.setFill(Color.YELLOW);
//        sunImage.layoutXProperty().bind(Bindings.multiply(sun.xProperty(), SCALE));
//        sunImage.layoutYProperty().bind(Bindings.multiply(sun.yProperty(), SCALE));
//
//        pane.getChildren().add(sunImage);
//        problem.getBodies().add(sun);

        double moonDistance = 284.401e9;
        double moonSpeed = 11.98e2;

//        moonDistance /= 30;
        moonSpeed *= 1.2;

        SCALE = 1 / moonDistance * 200;

        Body earth = new Earth();
        earth.setX(moonDistance);
        earth.setY(moonDistance);
        earth.setVy(0);

        Circle image = new Circle();
        image.setRadius(30);
        image.setFill(Color.BLUE);
        bind(earth, image);

        pane.getChildren().add(image);
        problem.getBodies().add(earth);

        Body moon = new Moon();
        moon.setX(moonDistance);
        moon.setY(0);
        moon.setVx(moonSpeed);
        moon.setVy(0);

        earth.setVx(-moonSpeed * moon.getMass() / earth.getMass());

        this.moonImage = image = new Circle();
        image.setRadius(7);
        image.setFill(Color.LIGHTGRAY);
        bind(moon, image);


        path.getElements().addAll(new MoveTo(moonImage.getLayoutY(), moonImage.getLayoutY()));


        pane.getChildren().add(image);
//        path.layoutXProperty().bind(viewCenter.xProperty());
//        path.layoutYProperty().bind(viewCenter.yProperty());
        problem.getBodies().add(moon);


        final double step = 1;
        AnimationTimer timer = new AnimationTimer() {
            private long start;

            @Override
            public void handle(long l) {
                if (l - start > 0.01 * 1e9) {
                    problem.next(20e5);
                    start = l;
                }
                    path.getElements().add(new LineTo(moonImage.getLayoutX(), moonImage.getLayoutY()));

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
}
