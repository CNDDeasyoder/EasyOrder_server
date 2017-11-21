package gameloft.com.easyorder_server;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView lv;
    DatabaseReference mDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;
    ArrayList<Queue_MonAn> arrayList = new ArrayList<Queue_MonAn>();
    Queue_apt apt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.q_lv);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        apt = new Queue_apt(this,R.layout.item_queue,arrayList);
        lv.setAdapter(apt);
        //----------------------------------
        mDatabaseReference.child("danhSachOrder").child("danhSach")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren()){
                            Queue_MonAn monAn = new Queue_MonAn();
                            monAn.setTt(Integer.parseInt(data.getKey()));
                            monAn.setBan(data.child("ban").getValue(int.class));
                            monAn.setSl(data.child("sl").getValue(int.class));
                            monAn.setTen(data.child("ten").getValue(String.class));
                            monAn.setGia(0);
                            arrayList.add(monAn);
                            Collections.sort(arrayList, new Comparator<Queue_MonAn>() {
                                @Override
                                public int compare(Queue_MonAn m1, Queue_MonAn m2) {
                                    if(m1.getTt()<m2.getTt()) return -1;
                                    else if(m1.getTt()==m2.getTt()) return 1;
                                    else return 0;
                                }
                            });
                            apt.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });






        //---------------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
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
    public boolean onNavigationItemSelected(MenuItem item) {
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
