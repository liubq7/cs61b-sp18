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

        /* Drawing the Background */
        StdDraw.setScale(-uniRadius, uniRadius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");

        for (Planet p : ps) {
            p.draw();
        }
    }
}