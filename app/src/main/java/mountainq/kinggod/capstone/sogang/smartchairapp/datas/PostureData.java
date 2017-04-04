package mountainq.kinggod.capstone.sogang.smartchairapp.datas;

/**
 * Created by dnay2 on 2017-03-28.
 */

public class PostureData {

    private String _date;
    private String _time;
    private String waist;
    private String neck;
    private String posLeft;
    private String posRight;

    public PostureData(String _date, String _time, String waist, String neck, String posLeft, String posRight) {
        this._date = _date;
        this._time = _time;
        this.waist = waist;
        this.neck = neck;
        this.posLeft = posLeft;
        this.posRight = posRight;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public String getPosLeft() {
        return posLeft;
    }

    public void setPosLeft(String posLeft) {
        this.posLeft = posLeft;
    }

    public String getPosRight() {
        return posRight;
    }

    public void setPosRight(String posRight) {
        this.posRight = posRight;
    }
}
