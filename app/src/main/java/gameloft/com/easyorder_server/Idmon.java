package gameloft.com.easyorder_server;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Idmon {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
}
