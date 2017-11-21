package gameloft.com.easyorder_server;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Tonqt Thonq on 11/3/2017.
 */

public class TableApater extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Table> List;
    public TableApater(Context context, int layout, List<Table> list) {
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
    public View getView(int i, View view, final ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(layout, null);

        final Button btn = (Button)view.findViewById(R.id.btn_table);
        final Table tb = List.get(i);
        btn.setText(""+String.valueOf(tb.getBanSo()));
        if( tb.getState() == 0) {
            btn.setBackgroundResource(R.drawable.ban_xanh);
        } else if (tb.getState() == 1) btn.setBackgroundResource(R.drawable.ban_do);
        else if(tb.getState()==2) btn.setBackgroundResource(R.drawable.ban_vang);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quan_li_ban.table_number=tb.getBanSo();
                Intent intent = new Intent(context,Thong_Tin_Ban.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
