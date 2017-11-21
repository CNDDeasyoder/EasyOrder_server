package gameloft.com.easyorder_server;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    String password = "";
    String pass = "";
    private DatabaseReference mdatabaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return;
        }
    }

    public void login(View view) {
        if(!isOnline()){
            Toast.makeText(this, "Vui lòng kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }
        EditText edt1, edt2;
        edt1 = (EditText) findViewById(R.id.edt_username);
        edt2 = (EditText) findViewById(R.id.edt_pass);
        final Intent intent = new Intent(this, quan_li_ban.class);
        password = MD5Library.md5(edt2.toString());
        //mdatabaseReference.child("password").setValue(password);
        Query query = mdatabaseReference.child("password").orderByValue();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pass = dataSnapshot.getValue().toString();
                if (!pass.equals(password))
                {
                    Toast.makeText(login.this, "Mật khẩu sai!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}