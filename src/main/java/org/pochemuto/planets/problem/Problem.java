package org.pochemuto.planets.problem;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

import org.pochemuto.planets.core.Fnum;
import org.pochemuto.planets.core.Scheme;
import org.pochemuto.planets.core.F;

/**
 * @author pochemuto
 */
public class Problem {
    public static final double G = 6.67384e-11;

    private final List<Body> bodies = new ArrayList<>();

    private double time;
    private Scheme scheme;

    public Problem(Scheme scheme) {
        this.scheme = scheme;
        time = 0;
    }

    public void next(double step) {
        final double t1 = time;
        time += step;
        final double t2 = time;
        int count = bodies.size(), i;

        double[] x = new double[count], y = new double[count], vx = new double[count], vy = new double[count];
        i = 0;
        for (Body b : bodies) {
            x[i] = b.getX();
            y[i] = b.getY();
            vx[i] = b.getVx();
            vy[i] = b.getVy();
            i++;
        }

        i = 0;
        for (Body b1 : bodies) {
            for (int j = 0; j < count; j++) {
                if (i == j) continue;

                Body b2 = bodies.get(j);
                double m2 = b2.getMass();
                double x1 = b1.getX(), y1 = b1.getY(), x2 = b2.getX(), y2 = b2.getY(), vx1 = b1.getVx(), vy1 = b1.getVy();

                double r = sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2));
                F gx = (t, f) -> G * m2 * (x2 - x1) / pow(r, 3);
                F gy = (t, f) -> G * m2 * (y2 - y1) / pow(r, 3);
                Fnum fx = new Fnum(scheme, gx, t1, vx1);
                Fnum fy = new Fnum(scheme, gy, t1, vy1);

//                System.out.println("r = " + r);
                vx[i] += scheme.y2(gx, t1, vx1, t2) - b1.getVx();
                vy[i] += scheme.y2(gy, t1, vy1, t2) - b1.getVy();
                x[i] += scheme.y2(fx, t1, x1, t2) - b1.getX();
                y[i] += scheme.y2(fy, t1, y1, t2) - b1.getY();

            }
            i++;
        }

        i = 0;
        for (Body body : bodies) {
            body.setX(x[i]);
            body.setY(y[i]);
            body.setVx(vx[i]);
            body.setVy(vy[i]);
//            System.out.println(body);
            i++;
        }

    }

    public List<Body> getBodies() {
        return bodies;
    }
}
