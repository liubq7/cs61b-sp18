public class NBody {
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int num = in.readInt();
        in.readDouble();
        Planet[] ps = new Planet[num];
        for (int i = 0; i < num; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            ps[i] = new Planet(xP, yP, xV, yV, m, img);
        }
        return ps;
    }

    public static void main(String[] args) {
        /* Collecting All Needed Input */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double uniRadius = readRadius(filename);
        Planet[] ps = readPlanets(filename);

        /* Drawing*/
        StdDraw.setScale(-uniRadius, uniRadius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");

        for (Planet p : ps) {
            p.draw();
        }

        /* Creating an Animation */
        StdDraw.enableDoubleBuffering();

        double time = 0;
        while (time <= T) {
            double[] xForces = new double[ps.length];
            double[] yForces = new double[ps.length];
            for (int i=0; i<ps.length; i++) {
                xForces[i] = ps[i].calcNetForceExertedByX(ps);
                yForces[i] = ps[i].calcNetForceExertedByY(ps);
            }
            for (int i=0; i<ps.length; i++) {
                ps[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p : ps) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        /* Printing the Universe */
        StdOut.printf("%d\n", ps.length);
        StdOut.printf("%.2e\n", uniRadius);
        for (int i = 0; i < ps.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        ps[i].xxPos, ps[i].yyPos, ps[i].xxVel,
                        ps[i].yyVel, ps[i].mass, ps[i].imgFileName);   
        }
    }
}