package gameloft.com.easyorder_server;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.StringTokenizer;

public class Queue_apt extends BaseAdapter {
    private Context context;
    private int layout;
    private java.util.List<MonAn> List;
    public Queue_apt(Context context, int layout, List<MonAn> list) {
        this.context = context;
        this.layout = layout;
        this.List = list;
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        final MonAn m = List.get(i);
        TextView tname, tban,tsl;
        Button btn = (Button)view.findViewById(R.id.q_btn);
        //-----------------------------
        tname = (TextView)view.findViewById(R.id.q_ten);
        tban = (TextView)view.findViewById(R.id.q_ban);
        tsl = (TextView)view.findViewById(R.id.q_sl);
        tname.setText(m.getTen());
        tban.setText(String.valueOf(m.getBan()));
        tsl.setText(String.valueOf(m.getSl()));
        if(m.getState()==0) btn.setBackgroundResource(R.drawable.cook);
        if(m.getState()==1) btn.setBackgroundResource(R.drawable.check);
        //---------------------------------------------
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m.getState()==0){
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                    db.child("danhSachBanAn").child("ban"+String.valueOf(m.getBan()))
                            .child("khachHang").child("danhSachMonAn")
                            .child(String.valueOf(m.getStt())).child("state")
                            .setValue(1);
                    db.child("danhSachOrder").child("danhSach").child(String.valueOf(m.getStt()))
                            .child("state").setValue(1);
                    m.setState(1);
                    notifyDataSetChanged();
                    return;
                }
                if(m.getState()==1){
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                    db.child("danhSachBanAn").child("ban"+String.valueOf(m.getBan()))
                            .child("khachHang").child("danhSachMonAn")
                            .child(String.valueOf(m.getStt())).child("state")
                            .setValue(2);
                    db.child("danhSachOrder").child("danhSach").child(String.valueOf(m.getStt()))
                            .removeValue();
                    List.remove(i);
                    notifyDataSetChanged();
                    return;
                }
            }
        });

        return view;
    }
}