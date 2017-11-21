package gameloft.com.easyorder_server;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class them_mon_an extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int Result_load_img = 1;
    private ImageView img;
    private Button btn_UpLoad, btn_Select;
    private EditText edt_name,  edt_mieuta, edt_fee;
    private StorageReference mStorageRef;
    private ProgressDialog progressDialog;
    private  FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference idmmon_data, mon_data;
    private Intent imgIntent= new Intent();
    private Uri imgSelect;
    private static String id="mon";
    private MonAn ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_an);
//-----------------------------

        edt_name = (EditText)findViewById(R.id.edt_name_add);
        edt_mieuta  = (EditText)findViewById(R.id.edt_mieuta_add);
        edt_fee = (EditText)findViewById(R.id.edt_gia_add);
        ma=new MonAn();
        //-------------------------

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        idmmon_data = mFirebaseDatabase.getReference().child("idmax");
        mon_data = mFirebaseDatabase.getReference();
        progressDialog = new ProgressDialog(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        idmmon_data.child("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value;
                value = dataSnapshot.getValue().toString();
                ma.setId(value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //id = ma.getId();
       // Toast.makeText(this, id,Toast.LENGTH_LONG).show();
        //--------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

               DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        img = (ImageView)findViewById(R.id.imgUpLoad);
        btn_Select=(Button)findViewById(R.id.btnSelect);
        btn_UpLoad = (Button)findViewById(R.id.btnUpLoad);
        /*btn_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(them_mon_an.this, ma.getId(), Toast.LENGTH_SHORT).show();
            }
        });*/
        btn_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent, Result_load_img);
            }
        });
        btn_UpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fee, mt, name;
                name = edt_name.getText().toString();
                fee = edt_fee.getText().toString();
                mt = edt_mieuta.getText().toString();
                if(name.equals("") || fee.equals("") || mt.equals("")) {
                    Toast.makeText(them_mon_an.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                int mfee = Integer.parseInt(fee);
                ma.setGia(mfee);
                ma.setMieuta(mt);
                ma.setName(name);

                if(imgSelect==null) {
                    Toast.makeText(them_mon_an.this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ma.getId().isEmpty()){
                    Toast.makeText(them_mon_an.this,"Vui lòng đợi giây lát", Toast.LENGTH_SHORT).show();
                    return;
                }
               progressDialog.setMessage("Dang upload...");
                progressDialog.setCancelable(false);
                if(isNetworkOnline()==false){
                    Toast.makeText(them_mon_an.this, "Vui lòng kết nối INTERNET", Toast.LENGTH_LONG).show();
                    return;
                }
                progressDialog.show();
                 StorageReference mStorageReference = mStorageRef.child("Image")
                         .child(String.valueOf(ma.getId()));
                mon_data.child("mon_an").child(ma.getId()).setValue(ma);
                mStorageReference.putFile(imgSelect).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(them_mon_an.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        int temp = Integer.parseInt(ma.getId()) + 1;
                        idmmon_data.child("id").setValue(temp);
                        onBackPressed();

                    }
                });
            }
        });
           }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == Result_load_img&& resultCode == RESULT_OK&& data!=null) {
            imgSelect = data.getData();
            img.setImageURI(imgSelect);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.them_mon_an, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)  {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_table) {
            Intent intent = new Intent(this, quan_li_ban.class);
            startActivity(intent);
        } else if (id == R.id.nav_manager) {
            Intent intent = new Intent(this, quan_li_mon_an.class);
            startActivity(intent);

        }
        else {
            if (id == R.id.nav_add) {
                Intent intent = new Intent(this, them_mon_an.class);
                startActivity(intent);
            } else if (id == R.id.nav_order) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }else if(id == R.id.nav_out){
                Intent intent = new Intent(this,QuanLiTaiKhoan.class);
                startActivity(intent);
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }
}