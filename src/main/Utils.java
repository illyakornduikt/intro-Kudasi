package main;

public final class Utils {

    private Utils() {}

    public static boolean collideCircles(float x1, float y1, float r1, float x2, float y2, float r2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) <= r1 * r1 + r2 * r2;
    }

    public static boolean pointInCircle(float cx, float cy, float r, float px, float py) {
        return (cx - px) * (cx - px) + (cy - py) * (cy - py) <= r * r;
    }

}
