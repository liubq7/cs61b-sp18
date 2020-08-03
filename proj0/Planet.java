public class Planet {
    public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
    public static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = xxPos - p.xxPos;
        double dy = yyPos - p.yyPos;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public double calcForceExertedBy(Planet p) {
        return (G / Math.pow(calcDistance(p), 2) * mass * p.mass);
    }

    public double calcForceExertedByX(Planet p) {
        return (calcForceExertedBy(p) * (p.xxPos - xxPos) / calcDistance(p));
    }
    public double calcForceExertedByY(Planet p) {
        return (calcForceExertedBy(p) * (p.yyPos - yyPos) / calcDistance(p));
    }

    public double calcNetForceExertedByX(Planet[] ps) {
        double xForce = 0;
        for (Planet p : ps) {
            if (!equals(p)) {
                xForce += calcForceExertedByX(p);
            }
        }
        return xForce;
    }
    public double calcNetForceExertedByY(Planet[] ps) {
        double yForce = 0;
        for (Planet p : ps) {
            if (!equals(p)) {
                yForce += calcForceExertedByY(p);
            }
        }
        return yForce;
    }

    public void update(double dt, double fX, double fY) {
        double ax = fX / mass;
        double ay = fY / mass;
        xxVel = xxVel + ax * dt;
        yyVel = yyVel + ay * dt;
        xxPos = xxPos + xxVel * dt;
        yyPos = yyPos + yyVel * dt;
    }
}