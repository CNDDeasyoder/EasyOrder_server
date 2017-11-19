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

public class ThongTinBan_apt extends BaseAdapter {
    private Context context;
    private int layout;
    private java.util.List<MonAn> List;
    public ThongTinBan_apt(Context context, int layout, List<MonAn> list) {
        this.context = context;
        this.layout = layout;
        this.List = list;
    }
    Dialog dialog;

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

        final MonAn m = List.get(i);
        TextView tname, tgia,tsl;
        Button btn = (Button)view.findViewById(R.id.btn_xoa_mon);
                //-----------------------------
        tname = (TextView)view.findViewById(R.id.table_info_name);
        tgia = (TextView)view.findViewById(R.id.table_info_gia);
        tsl = (TextView)view.findViewById(R.id.table_info_sl);
        tname.setText(m.getName());
        tgia.setText(String.valueOf(m.getGia()));
        tsl.setText(String.valueOf(m.getDang_chon()));
        //---------------------------------------------
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Xac nhan xoa mon an?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase mFirebaseDatabase;
                        DatabaseReference mDatabaseReference;
                        mFirebaseDatabase=FirebaseDatabase.getInstance();
                        mDatabaseReference=mFirebaseDatabase.getReference();
                        mDatabaseReference.child("danhSachBanAn").child("ban"+String.valueOf(quan_li_ban.table_number))
                                .child("khachHang").child("danhSachMonAn").child(String.valueOf(m.getTt())).removeValue();
                        Intent intent = new Intent(context,Thong_Tin_Ban.class);
                        context.startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }
}
