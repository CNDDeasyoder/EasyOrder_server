package gameloft.com.easyorder_server;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class item_quan_li_mon_an extends AppCompatActivity {
    EditText e1,e2,e3;
   public static final String EXTRA_ID = "Id";
    String ID;
    ImageView img;
    StorageReference mStorageRef;
    StorageReference mStorageReference;
    FirebaseDatabase database;

    Button xacnhan,xoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_quan_li_mon_an);

         ID = (String) getIntent().getExtras().get(EXTRA_ID);
        e1=(EditText) findViewById(R.id.e1);
        e2=(EditText)findViewById(R.id.e2);
        e3=(EditText)findViewById(R.id.e3);
        e1.setEnabled(false);
        e2.setEnabled(false);
        e3.setEnabled(false);
        img=(ImageView)findViewById(R.id.img) ;
         mStorageRef = FirebaseStorage.getInstance().getReference();
        mStorageReference = mStorageRef.child("Image").child(ID);
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorageReference).into(img);

         database = FirebaseDatabase.getInstance();
     final   DatabaseReference myRef = database.getReference().child("mon_an").child(ID);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MonAn m=(MonAn)dataSnapshot.getValue(MonAn.class);
                e1.setText(m.getTen()+"");
                e2.setText(""+m.getSl());

                e3.setText(""+m.getGia());

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        xacnhan=(Button)findViewById(R.id.but1);
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten=e1.getText().toString();
                myRef.child("ten").setValue(ten);
                int sl=Integer.parseInt(e2.getText().toString()) ;
                myRef.child("sl").setValue(sl);
                int gia=Integer.parseInt(e3.getText().toString()) ;
                myRef.child("gia").setValue(gia);
                Toast.makeText(item_quan_li_mon_an.this, "Xấc nhận", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(item_quan_li_mon_an.this,quan_li_mon_an.class);
                startActivity(intent);

            }
        });
        xoa=(Button) findViewById(R.id.but2);
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertDialog(myRef);
            }
        });
    }
    public void sua(View v)
    {
        e1.setEnabled(true);
        e2.setEnabled(true);
        e3.setEnabled(true);
        
    }
    public void showAlertDialog(final DatabaseReference myRef){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn có muốn xóa món ăn này không?");
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


                myRef.removeValue();
                Intent intent = new Intent(item_quan_li_mon_an.this,quan_li_mon_an.class);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
