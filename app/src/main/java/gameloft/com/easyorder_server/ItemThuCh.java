package gameloft.com.easyorder_server;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

/**
 * Created by tonqthonq97 on 12/16/2018.
 */

public class ItemThuCh {
    public long money;
    String key;
    public String type;
    public String category;
    public String note;
    //public Calendar time;
    public String date;
    public String time;

    public ItemThuCh(String key, long money, String type, String category, String note, String date, String time) {
        this.money = money;
        this.type = type;
        this.note = note;
        this.category = category;
        this.date = date;
        this.time = time;
        this.key = key;
        int d = Integer.valueOf(date.split("/")[0]);
        int M = Integer.valueOf(date.split("/")[1])+1;
        int y = Integer.valueOf(date.split("/")[2]);
        int h = Integer.valueOf(time.split(":")[0]);
        int m = Integer.valueOf(time.split(":")[1]);
        //this.time = Calendar.getInstance();
        //this.time.set(y,M,d,h,m);
    }

    public ItemThuCh(){

    }


}
