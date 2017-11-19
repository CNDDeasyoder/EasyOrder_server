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
    private java.util.List<Queue_MonAn> List;
    public Queue_apt(Context context, int layout, List<Queue_MonAn> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        final Queue_MonAn m = List.get(i);
        TextView tname, tban,tsl;
        Button btn = (Button)view.findViewById(R.id.q_btn);
        //-----------------------------
        tname = (TextView)view.findViewById(R.id.q_ten);
        tban = (TextView)view.findViewById(R.id.q_ban);
        tsl = (TextView)view.findViewById(R.id.q_sl);
        tname.setText(m.getTen());
        tban.setText(String.valueOf(m.getBan()));
        tsl.setText(String.valueOf(m.getSl()));
        //---------------------------------------------
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase mFirebaseDatabase;
                DatabaseReference mDatabaseReference;
                mFirebaseDatabase=FirebaseDatabase.getInstance();
                mDatabaseReference=mFirebaseDatabase.getReference();
                mDatabaseReference.child("danhSachOrder").child("danhSach").child(String.valueOf(m.getTt()))
                        .removeValue();
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
}