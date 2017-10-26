package gameloft.com.easyorder_server;

/**
 * Created by Tonqt Thonq on 10/21/2017.
 */

public class MonAn {
    String name, mieuta;
    int gia;
    String id;
    int dang_chon=0;

    public MonAn() {
    }

    public MonAn(String name, String mieuta, int gia, String id) {
        this.name = name;
        this.mieuta = mieuta;
        this.gia = gia;
        this.id = id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMieuta() {
        return mieuta;
    }

    public void setMieuta(String mieuta) {
        this.mieuta = mieuta;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}
