package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

/**
 * Created by dnay2 on 2017-03-25.
 */

public class HueColor {

    private boolean on;
    private int sat;
    private int bri;
    private int hue;

    public HueColor(boolean on, int sat, int bri, int hue) {
        this.on = on;
        this.sat = sat;
        this.bri = bri;
        this.hue = hue;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public int getBri() {
        return bri;
    }

    public void setBri(int bri) {
        this.bri = bri;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }
}
