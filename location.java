import api.GeoLocation;

public class location implements GeoLocation {
    private double x;
    private double y;
    private double z;

    public location(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public location (location other){
        this.x=other.x;
        this.y=other.y;
        this.y=other.y;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        return Math.sqrt(Math.pow((this.x-g.x()),2) + Math.pow((this.y-g.y()),2) + Math.pow((this.z-g.z()),2));
    }
}
