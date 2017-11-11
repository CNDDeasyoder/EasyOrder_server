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
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class quan_li_ban extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GridView grv;
    ArrayList<Table> arrayList = new ArrayList<Table>();
    TableApater apater;
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
        grv = (GridView)findViewById(R.id.gr_table);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        apater = new TableApater(this, R.layout.item_table,arrayList);
        grv.setAdapter(apater);

        mReference=mFirebaseDatabase.getReference().child("danhSachBanAn");
       mReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               arrayList.clear();
               for(DataSnapshot data : dataSnapshot.getChildren()){

                   boolean boo = data.child("state").getValue(boolean.class);
                   int id = data.child("banSo").getValue(int.class);
                   Table temp = new Table(id,boo);
                   arrayList.add(temp);
                    Collections.sort(arrayList, new Comparator<Table>() {
                        @Override
                        public int compare(Table t1, Table t2) {
                            if(t1.getBanSo()<t2.getBanSo()) return -1;
                            else if (t1.getBanSo()==t2.getBanSo()) return 1;else
                                return 0;
                        }
                    });
                   apater.notifyDataSetChanged();
                   }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

       /*arrayList.add(new Table(1,true));
        arrayList.add(new Table(2,false));
        arrayList.add(new Table(1,true));
        arrayList.add(new Table(2,false));arrayList.add(new Table(1,true));
        arrayList.add(new Table(2,false));arrayList.add(new Table(1,true));
        arrayList.add(new Table(2,false));arrayList.add(new Table(1,true));
        arrayList.add(new Table(2,false));arrayList.add(new Table(1,true));
        arrayList.add(new Table(2,false));*/

        //----------------------------------------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
