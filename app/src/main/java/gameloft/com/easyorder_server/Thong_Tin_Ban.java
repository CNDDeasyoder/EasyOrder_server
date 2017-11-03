package gameloft.com.easyorder_server;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Thong_Tin_Ban extends AppCompatActivity {
    Button btn_set_true, btn_set_false;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    ProgressDialog pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong__tin__ban);
        //------------------------------------------------
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();
        btn_set_true=(Button)findViewById(R.id.btn_set_true);
        btn_set_false=(Button)findViewById(R.id.btn_set_false);
        pro=new ProgressDialog(this);
        //------------------------------------------------
        btn_set_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pro.setMessage("Đang thay đổi trạng thái");
                pro.setCancelable(false);
                mDatabaseReference.child("danhSachBanAn").child("ban" + String.valueOf(quan_li_ban.table_number)).child("state").setValue(true);
                pro.dismiss();
                onBackPressed();
            }
        });
        btn_set_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pro.setMessage("Đang thay đổi trạng thái");
                pro.setCancelable(false);
                mDatabaseReference.child("danhSachBanAn").child("ban" + String.valueOf(quan_li_ban.table_number)).child("state").setValue(false);
                pro.dismiss();
                onBackPressed();
            }
        });
        //------------------------------------------------

    }
}
