package gameloft.com.easyorder_server;

/**
 * Created by Tonqt Thonq on 11/3/2017.
 */

public class Table {
    int banSo;
    boolean state;

    public Table(int banSo, boolean state) {
        this.banSo = banSo;
        this.state = state;
    }

    public int getBanSo() {
        return banSo;
    }

    public void setBanSo(int banSo) {
        this.banSo = banSo;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
