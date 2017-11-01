package gameloft.com.easyorder_server;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class item_quan_li_mon_an extends AppCompatActivity {
    EditText e1,e2,e3,e4;
   public static final String EXTRA_ID = "Id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_quan_li_mon_an);

        MonAn Id=(MonAn) getIntent().getSerializableExtra(EXTRA_ID);
         e1=(EditText) findViewById(R.id.e1);
        e1.setText(Id.getName()+"");
         e2=(EditText)findViewById(R.id.e2);
        e2.setText(""+Id.dang_chon);
         e3=(EditText)findViewById(R.id.e3);
        e3.setText(""+Id.getMieuta());
         e4=(EditText)findViewById(R.id.e4);
        e4.setText(""+Id.getGia());
      //  ImageView photo = (ImageView)findViewById(R.id.photo);
      //  photo.setImageResource(Id.getImageResourceId());
        e1.setEnabled(false);
        e2.setEnabled(false);
        e3.setEnabled(false);
        e4.setEnabled(false);
    }
    public void sua(View v)
    {
        e1.setEnabled(true);
        e2.setEnabled(true);
        e3.setEnabled(true);
        e4.setEnabled(true);
    }
    public void xacnhan(View view)
    {


        MonAn Id=(MonAn) getIntent().getSerializableExtra(EXTRA_ID);
        int dang_chon=Integer.parseInt(e2.getText().toString()) ;
        int gia=Integer.parseInt(e4.getText().toString()) ;
        String id=Id.getId()+"";
        String mieuta=e3.getText().toString();
        String name=e1.getText().toString();
        MonAn m=new MonAn(dang_chon,gia,id,mieuta,name);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("mon_an").child(id);
        myRef.setValue(m);

    }
    public void xoa(View v)
    {
        MonAn Id=(MonAn) getIntent().getSerializableExtra(EXTRA_ID);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("mon_an").child(Id.getId());
        myRef.removeValue();
    }
}
