package gameloft.com.easyorder_server;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Them_ban extends AppCompatActivity {
    TextView soban;
    EditText sobanthem;
    Button btn_add, btn_remove;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    int crr_table;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ban);
        //assign
        //---------------------------------------------
        soban=(TextView)findViewById(R.id.crr_table);
        sobanthem=(EditText)findViewById(R.id.num_table);
        btn_add = (Button)findViewById(R.id.add_table);
        btn_remove=(Button)findViewById(R.id.remove_table);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mProgressDialog = new ProgressDialog(this);
        //---------------------------------------------------
        mDatabaseReference.child("ban_max").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                crr_table = dataSnapshot.getValue(int.class);
                soban.setText("Số lượng bàn hiện tại là: "+ (crr_table-1) );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //------------------------------------------------------
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sobanthem.getText().toString().isEmpty()){
                    Toast.makeText(Them_ban.this, "Vui lòng nhập số bàn", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage("Đang thêm");
                mProgressDialog.show();
                int num = Integer.parseInt(sobanthem.getText().toString());
                for (int i = crr_table; i< crr_table+num; i++){
                    Table temp = new Table(i,false);
                    mDatabaseReference.child("danhSachBanAn")
                            .child("ban"+String.valueOf(i)).setValue(temp);
                }
                mDatabaseReference.child("ban_max").setValue(crr_table+num);
                mProgressDialog.dismiss();
                Toast.makeText(Them_ban.this, "Đã thêm "+ String.valueOf(num)+" bàn",
                        Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        //--------------------------------------------------------------
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sobanthem.getText().toString().isEmpty()){
                    Toast.makeText(Them_ban.this, "Vui lòng nhập số bàn", Toast.LENGTH_SHORT).show();
                    return;
                }
                showAlertDialog();
            }
        });
    }
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Nếu xóa thì mọi thông tin liên quan đến bàn sẽ biến mất, bao gồm cả bàn đang có người ngồi." +
                "Bạn có chắc muốn xóa không?");
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
                int num = Integer.parseInt(sobanthem.getText().toString());

                mProgressDialog.setMessage("Đang xóa");
                mProgressDialog.show();
                for(int i = crr_table-1; i > (crr_table-1-num); i-- ){
                    mDatabaseReference.child("danhSachBanAn").child("ban"+i).removeValue();
                }
                mDatabaseReference.child("ban_max").setValue(crr_table-num);
                Toast.makeText(Them_ban.this, "Đã xóa "+num+" bàn", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                }

}
