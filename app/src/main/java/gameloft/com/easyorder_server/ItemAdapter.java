package gameloft.com.easyorder_server;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.RemoteMessage;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Tonqt Thonq on 11/3/2017.
 */

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ItemThuCh> List;
    public ItemAdapter(Context context, int layout, List<ItemThuCh> list) {
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
        return List.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(layout, null);

        final TextView txtReason = (TextView)view.findViewById(R.id.txtReason);
        final TextView txtType = (TextView)view.findViewById(R.id.txtType);
        final TextView txtCategory = (TextView)view.findViewById(R.id.txtCategory);
        final TextView txtTime = (TextView)view.findViewById(R.id.txtTime);
        final TextView txtMoney = (TextView)view.findViewById(R.id.txtMoney);
        final ImageView img = (ImageView)view.findViewById(R.id.imgTime);
        final Spinner select = (Spinner)view.findViewById(R.id.Select);
        final ItemThuCh crrItem = List.get(i);

        String[] items = new String[]{"...","Sửa", "Xóa"};
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);
        select.setAdapter(adapter);
        select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context,select.getSelectedItem().toString(),Toast.LENGTH_SHORT);
                Log.e("select: ",select.getSelectedItem().toString());
                if(select.getSelectedItem().toString().equals("Sửa"))
                {
                    GV.crrItem= crrItem;
                    Intent intent = new Intent(context,SuaItem.class);
                    context.startActivity(intent);
                }
                if(select.getSelectedItem().toString().equals("Xóa"))
                {
                    GV.crrItem= crrItem;
                    showAlertDialog(context,crrItem.key);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        NumberFormat formatter = NumberFormat.getNumberInstance();
        txtReason.setText(crrItem.note);
        txtType.setText(crrItem.type);
        txtMoney.setText(String.valueOf(formatter.format(crrItem.money)));
        txtCategory.setText(crrItem.category);
        txtTime.setText(crrItem.date + "  " +crrItem.time);
//        img.setScaleType(ImageView.ScaleType.CENTER);

        if(crrItem.type.equals("Thu")) txtType.setTextColor(Color.BLUE);
        if(crrItem.type.equals("Chi")) txtType.setTextColor(Color.RED);
        if(crrItem.type.equals("Cho vay")) txtType.setTextColor(Color.GREEN);
        if(crrItem.type.equals("Vay")) txtType.setTextColor(Color.parseColor("#BA55D3"));
        txtType.setTypeface(null, Typeface.BOLD);




//        final Button btn = (Button)view.findViewById(R.id.btn_table);
//        final Table tb = List.get(i);
//        btn.setText(""+String.valueOf(tb.getBanSo()));
//        if( tb.getState() == 0) {
//            btn.setBackgroundResource(R.drawable.ban_xanh);
//        } else if (tb.getState() == 1) btn.setBackgroundResource(R.drawable.ban_do);
//        else if(tb.getState()==3){
//            btn.setBackgroundResource(R.drawable.ban_vang);
//            NotificationManager msgmng = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            Notification msg = new Notification.Builder(context)
//                    .setSmallIcon(R.drawable.ban_vang)
//                    .setContentTitle("Có yêu cầu giúp đỡ")
//                    .setContentText("Bàn "+tb.getBanSo()+" đang yêu cầu giúp đỡ")
//                    .getNotification();
//            msgmng.notify(tb.getBanSo(),msg);
//            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
//            mDatabaseReference.child("danhSachBanAn").child("ban"+tb.getBanSo()).child("state").setValue(1);
//        } else if(tb.getState()==2) {
//            btn.setBackgroundResource(R.drawable.ban_vang);
//            NotificationManager msgmng = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            Notification msg = new Notification.Builder(context)
//                    .setSmallIcon(R.drawable.ban_vang)
//                    .setContentTitle("Có yêu cầu thanh toán")
//                    .setContentText("Bàn "+tb.getBanSo()+" đang yêu cầu thanh toán")
//                    .getNotification();
//            msgmng.notify(tb.getBanSo(),msg);
//
//        }
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                quan_li_ban.table_number = tb.getBanSo();
//                Intent intent = new Intent(context,Thong_Tin_Ban.class);
//                context.startActivity(intent);
//            }
//        });

        return view;
    }
    public void showAlertDialog(Context context, final String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Bạn chắc chắn muốn xóa chứ");
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int o) {
                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                mDatabaseReference.child(GV.userName).child("listAction").child(key).removeValue();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
