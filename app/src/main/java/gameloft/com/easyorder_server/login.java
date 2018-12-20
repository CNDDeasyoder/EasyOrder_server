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

import org.json.JSONObject;

import java.util.Objects;

public class login extends AppCompatActivity {
    String password = "";
    String pass = "";
    private DatabaseReference mdatabaseReference = FirebaseDatabase.getInstance().getReference();
    boolean isShowRegister = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return;
        }
        EditText edtPass2 = (EditText)findViewById(R.id.edt_pass2);
        edtPass2.setVisibility(View.INVISIBLE);
        EditText edtEmail = (EditText)findViewById(R.id.edt_Email);
        edtEmail.setVisibility(View.INVISIBLE);


    }

    public void login(View view) {
        if(!isOnline()){
            Toast.makeText(this, "Vui lòng kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }
        MD5 lib = new MD5();
        EditText edt1, edt2;
        edt1 = (EditText) findViewById(R.id.edt_username);
        edt2 = (EditText) findViewById(R.id.edt_pass);
        final Intent intent = new Intent(this, quan_li_ban.class);
        final String username = edt1.getText().toString();
        password = lib.md5(edt2.getText().toString().trim());
        try{
            Query query = mdatabaseReference.child(username).orderByValue();
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot);
                    if(dataSnapshot.getValue()==null){
                        Toast.makeText(login.this, "Sai ten dang nhap!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    pass = dataSnapshot.child("password").getValue().toString();
                    if (!pass.equals(password))
                    {
                        Toast.makeText(login.this, "Mật khẩu sai!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String email = dataSnapshot.child("email").getValue(String.class);
                        GV.userName = username;
                        GV.isLogin = true;
                        GV.email = email;
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (NullPointerException e ) {
            Toast.makeText(login.this, "Sai ten dang nhap!", Toast.LENGTH_SHORT).show();
        }

    }

    public void register(View view) {
        if(!this.isShowRegister){
            this.isShowRegister = true;
            EditText edt = (EditText) findViewById(R.id.edt_pass2);
            edt.setVisibility(View.VISIBLE);
            EditText edt1 = (EditText) findViewById(R.id.edt_Email);
            edt1.setVisibility(View.VISIBLE);
            return;
        }
        if(!isOnline()){
            Toast.makeText(this, "Vui lòng kết nối mạng", Toast.LENGTH_SHORT).show();
            return;
        }
        MD5 lib = new MD5();
        EditText edt1, edt2;
        edt1 = (EditText) findViewById(R.id.edt_username);
        edt2 = (EditText) findViewById(R.id.edt_pass);
        EditText edt3 = (EditText) findViewById(R.id.edt_pass2);
        final Intent intent = new Intent(this, quan_li_ban.class);
        final String username = edt1.getText().toString();
        String pass1 = edt2.getText().toString();
        String pass2 = edt3.getText().toString();
        final String email = ((EditText) findViewById(R.id.edt_Email)).getText().toString();
        if(!pass1.equals(pass2)){
            Toast.makeText(login.this, "2 Mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
            return;
        }
        password = lib.md5(edt2.getText().toString().trim());
        try{
            Query query = mdatabaseReference.child(username);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot);
                    if(dataSnapshot.getValue()!=null){
                        Toast.makeText(login.this, "Đã tồn tại username" + username, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        mdatabaseReference.child(username).child("password").setValue(password);
                        mdatabaseReference.child(username).child("email").setValue(email);
                        GV.userName = username;
                        GV.isLogin = true;
                        Toast.makeText(login.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e ) {
            Toast.makeText(login.this, "Xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }

    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}