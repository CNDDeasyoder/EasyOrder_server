package gameloft.com.easyorder_server;

/**
 * Created by Tonqt Thonq on 10/21/2017.
 */
import java.io.Serializable;
@SuppressWarnings("serial")
public class MonAn implements Serializable{
    int dang_chon=0;
    int gia;
    String name, mieuta;

    String id;



    public MonAn() {
    }

    public MonAn(int dang_chon,int gia,String id, String mieuta,String name  ) {
        this.name = name;
        this.mieuta = mieuta;
        this.gia = gia;
        this.id = id;
        this.dang_chon=dang_chon;

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
