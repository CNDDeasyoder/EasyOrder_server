package gameloft.com.easyorder_server;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
    }

    public void login(View view) {
        EditText edt1, edt2;
        edt1 = (EditText) findViewById(R.id.edt_username);
        edt2 = (EditText) findViewById(R.id.edt_pass);
        Intent intent = new Intent(this, quan_li_ban.class);
        startActivity(intent);
        if (edt1.getText().toString().equals("admin") && edt2.getText().toString().equals("admin")) {
           // Intent intent = new Intent(this, quan_li_ban.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Vui lòng nhập đúng tài khoản", Toast.LENGTH_SHORT).show();
        }


    }
}
/*

 */