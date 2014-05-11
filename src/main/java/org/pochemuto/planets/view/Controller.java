package org.pochemuto.planets.view;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import org.pochemuto.planets.methods.Euler;
import org.pochemuto.planets.problem.Body;
import org.pochemuto.planets.problem.Problem;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author pochemuto
 */
public class Controller implements Initializable{
    private final Problem problem = new Problem(new Euler());
    private final int SIZE = 900;
    public Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int t = 10;
        Random r = new Random();
        while (t-- > 0) {
            Body b = new Body(r.nextInt(10) + 10);
            b.setX(r.nextDouble());
            b.setY(r.nextDouble());
            b.setVx(r.nextDouble());
            b.setVy(r.nextDouble());

            Circle c = new Circle();
            c.setFill(Color.BLACK);
            c.setRadius(10);
            c.layoutXProperty().bind(Bindings.multiply(b.xProperty(), SIZE));
            c.layoutYProperty().bind(Bindings.multiply(b.yProperty(), SIZE));

            pane.getChildren().add(c);
            problem.getBodies().add(b);
        }


    }
}
