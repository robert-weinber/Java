package Modell;

public class Ember {

    private int ID;
    private String veznev;
    private String kernev;
    private String kezdes;
    private String pozicio;
    private int oraber;

    public Ember(int id, String veznev, String kernev, String kezdes, String pozicio, int oraber) {
        this.ID = id;
        this.veznev = veznev;
        this.kernev = kernev;
        this.kezdes = kezdes;
        this.pozicio = pozicio;
        this.oraber = oraber;
    }

    public Ember(int id, String veznev, String kernev, String pozicio) {
        this.ID = id;
        this.veznev = veznev;
        this.kernev = kernev;
        this.pozicio = pozicio;
    }

    public int getID() {
        return ID;
    }

    public String getVeznev() {
        return veznev;
    }

    public String getKernev() {
        return kernev;
    }

    public String getKezdes() {
        return kezdes;
    }

    public String getNev() {
        return veznev + ' ' + kernev;
    }

    public String getPozicio() {
        return pozicio;
    }

    public int getOraber() {
        return oraber;
    }

    public Object[] getAdatok() {
        return new Object[]{ID + "", veznev + " " + kernev, pozicio, kezdes, oraber + ""};
    }

    @Override
    public String toString() {
        return veznev + " " + kernev;
    }

}
