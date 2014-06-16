package org.pochemuto.planets.problem;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author pochemuto
 */
public class Body {
    private final ReadOnlyDoubleProperty mass;
    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    private final DoubleProperty vx = new SimpleDoubleProperty();
    private final DoubleProperty vy = new SimpleDoubleProperty();

    public Body(double mass) {
        this.mass = new ReadOnlyDoubleWrapper(mass).getReadOnlyProperty();
    }

    public double getMass() {
        return mass.get();
    }

    public ReadOnlyDoubleProperty massProperty() {
        return mass;
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public double getVx() {
        return vx.get();
    }

    public DoubleProperty vxProperty() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx.set(vx);
    }

    public double getVy() {
        return vy.get();
    }

    public DoubleProperty vyProperty() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy.set(vy);
    }

    @Override
    public String toString() {
        return "Body{" +
                "mass=" + mass +
                ", x=" + x +
                ", y=" + y +
                ", vx=" + vx +
                ", vy=" + vy +
                '}';
    }
}
