package gameloft.com.easyorder_server;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class QuanLiTaiKhoan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button btnDoiCode;
    private Button btnDoiPass;
    private Button btnThoat;
    private DatabaseReference mdatabaseReference = FirebaseDatabase.getInstance().getReference();

    private String code = "";
    private String pass = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_tai_khoan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnDoiCode = (Button) findViewById(R.id.btn_doi_code);
        btnDoiPass = (Button) findViewById(R.id.btn_doi_pass);
        btnThoat = (Button) findViewById(R.id.btn_thoat);

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(QuanLiTaiKhoan.this);
                builder.setTitle("Thoát ứng dụng");
                builder.setMessage("Bạn có chắc chắn muốn thoát?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(QuanLiTaiKhoan.this, login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Exit me", true);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });


        btnDoiPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(QuanLiTaiKhoan.this);
                dialog.setTitle("Đổi password");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_doi_pass);
                final EditText edtPassCu = (EditText) dialog.findViewById(R.id.edt_mat_khau_cu_dialog);
                final EditText edtPassMoi = (EditText) dialog.findViewById(R.id.edt_mat_khau_moi_dialog);
                Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy_dialog);
                Button btnDongY = (Button) dialog.findViewById(R.id.btn_dong_y_dialog);
                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtPassMoi.getText().toString().isEmpty() || edtPassCu.getText().toString().isEmpty())
                        {
                            Toast.makeText(QuanLiTaiKhoan.this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            final MD5 lib = new MD5();
                            Query query = mdatabaseReference.child("password").orderByValue();
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    pass = dataSnapshot.getValue().toString();
                                    if (!pass.equals(lib.md5(edtPassCu.getText().toString())))
                                    {
                                        Toast.makeText(QuanLiTaiKhoan.this, "Mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        code = lib.md5(edtPassMoi.getText().toString());
                                        mdatabaseReference.child("password").setValue(code);
                                        Toast.makeText(QuanLiTaiKhoan.this, "Mật khẩu đã đổi!", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });


        btnDoiCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(QuanLiTaiKhoan.this);
                dialog.setTitle("Đổi code");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_doi_code);
                final EditText edtPass = (EditText) dialog.findViewById(R.id.edt_mat_khau_dialog);
                final EditText edtCode = (EditText) dialog.findViewById(R.id.edt_code_dialog);
                Button btnHuy = (Button) dialog.findViewById(R.id.btn_huy_dialog);
                Button btnDongY = (Button) dialog.findViewById(R.id.btn_dong_y_dialog);
                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtCode.getText().toString().isEmpty() || edtPass.getText().toString().isEmpty())
                        {
                            Toast.makeText(QuanLiTaiKhoan.this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            final MD5 lib = new MD5();
                            Query query = mdatabaseReference.child("password").orderByValue();
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    pass = dataSnapshot.getValue().toString();
                                    if (!pass.equals(lib.md5(edtPass.getText().toString())))
                                    {
                                        Toast.makeText(QuanLiTaiKhoan.this, "Mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        code = edtCode.getText().toString();
                                        mdatabaseReference.child("code").setValue(code);
                                        Toast.makeText(QuanLiTaiKhoan.this, "Code đã đổi!", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
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
        getMenuInflater().inflate(R.menu.quan_li_mon_an, menu);
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

        if (id == R.id.nav_table)
        {
            Intent intent = new Intent(this, quan_li_ban.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_manager)
        {
            Intent intent = new Intent(this, quan_li_mon_an.class);
            startActivity(intent);

        }
        else
        {
            if (id == R.id.nav_add)
            {
                Intent intent = new Intent(this, them_mon_an.class);
                startActivity(intent);
            }
            else if (id == R.id.nav_order) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else if (id == R.id.account)
            {
                Intent intent = new Intent(this, QuanLiTaiKhoan.class);
                startActivity(intent);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



