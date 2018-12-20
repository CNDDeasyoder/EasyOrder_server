package gameloft.com.easyorder_server;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class quan_li_ban extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listView;
    ArrayList<ItemThuCh> arrayList = new ArrayList<ItemThuCh>();
    ItemAdapter apater;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mReference;
    static int table_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_ban);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //---------------------------------------------------------------------------------
        //Hiền code 3/11
        //---------------------------------------------------------------------------------
        listView = (ListView)findViewById(R.id.listView);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        apater = new ItemAdapter(this, R.layout.item_table,arrayList);
        listView.setAdapter(apater);

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ItemThuCh crrItem = (ItemThuCh) adapterView.getItemAtPosition(i);
//                Toast.makeText(quan_li_ban.this,crrItem.note,Toast.LENGTH_SHORT).show();
//
//                return false;
//            }
//        });

        mReference=mFirebaseDatabase.getReference().child(GV.userName).child("listAction");
       mReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               arrayList.clear();
               for(DataSnapshot data : dataSnapshot.getChildren()){
                   ItemThuCh temp = data.getValue(ItemThuCh.class);
                   arrayList.add(temp);
                   }
               apater.notifyDataSetChanged();
               GV.arrayList = arrayList;
//               Collections.sort(arrayList, new Comparator<Table>() {
//                   @Override
//                   public int compare(Table t1, Table t2) {
//                       if(t1.getBanSo()<t2.getBanSo()) return -1;
//                       else if (t1.getBanSo()==t2.getBanSo()) return 0;else
//                           return 1;
//                   }
//               });
               apater.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
        //----------------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent =  new Intent(quan_li_ban.this, Them_ban.class);
                startActivity(mIntent);
            }
        });

        FloatingActionButton btnTongHop = (FloatingActionButton) findViewById(R.id.btnTongHop);
        btnTongHop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent =  new Intent(quan_li_ban.this, ThongKe.class);
                startActivity(mIntent);
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
        getMenuInflater().inflate(R.menu.quan_li_ban, menu);
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
            } else if(id == R.id.nav_out){
                Intent intent = new Intent(this,QuanLiTaiKhoan.class);
                startActivity(intent);
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
