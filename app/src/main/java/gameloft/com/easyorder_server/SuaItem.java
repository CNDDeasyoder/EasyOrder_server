package gameloft.com.easyorder_server;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SuaItem extends AppCompatActivity {
    TextView soban;
    EditText sobanthem;
    Button btn_add, btn_remove;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    int crr_table;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ban);
        //assign
        //---------------------------------------------
        final Spinner category = (Spinner) findViewById(R.id.categoryList);
        String[] items = new String[]{"Tiêu dùng hằng ngày", "Tiền nhà", "Tiền điện nước", "Khác"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        category.setAdapter(adapter);

        final Spinner type = (Spinner) findViewById(R.id.typeList);
        String[] itemsType = new String[]{"Thu", "Chi", "Cho vay", "Vay"};
        final ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsType);
        type.setAdapter(adapterType);
//
        System.out.println(category.getSelectedItem().toString());

        final Button btnDate = (Button) findViewById(R.id.btnDate);
        final Calendar myCalendar = Calendar.getInstance();
        SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");
        btnDate.setText(spdf.format(myCalendar));
        final Button btnTime = (Button) findViewById(R.id.btnTime);
        SimpleDateFormat spdt = new SimpleDateFormat("hh:mm");
        btnTime.setText(spdt.format(myCalendar));


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                updateBtnDate(dayOfMonth, monthOfYear, year);
            }
        };

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                updateBtnTime(h, m);
            }
        };
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SuaItem.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(SuaItem.this, time, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
            }
        });

        //main code
            btnDate.setText(GV.crrItem.date);
            btnTime.setText(GV.crrItem.time);
            ((EditText) findViewById(R.id.note)).setText(GV.crrItem.note);
            ((EditText) findViewById(R.id.edtMoney)).setText(String.valueOf(GV.crrItem.money));
        //


        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setText("Sửa");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String moneyy = ((EditText) findViewById(R.id.edtMoney)).getText().toString();

                    final String categoryText = category.getSelectedItem().toString();
                    final String typeText = type.getSelectedItem().toString();
                    final String note = ((EditText) findViewById(R.id.note)).getText().toString();
                    final String dateText = btnDate.getText().toString();
                    final String timeText = btnTime.getText().toString();
                    if (categoryText.equals("") || typeText.equals("") || moneyy.equals("")) {
                        Toast.makeText(SuaItem.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ;
                    final long money = Long.parseLong(moneyy);
                    Calendar calendarKey = Calendar.getInstance();
                    ItemThuCh item = new ItemThuCh(GV.crrItem.key, money, typeText, categoryText, note, dateText, timeText);
                    Log.e("key",GV.crrItem.key);
                    mDatabaseReference.child(GV.userName).child("listAction").child(GV.crrItem.key).setValue(item);

                    onBackPressed();
                } catch (Exception e) {
                    ((EditText) findViewById(R.id.note)).setText(e.toString());
                }

            }
        });
    }

    public  void updateBtnDate(int d, int m, int y){
        Button btn = (Button) findViewById(R.id.btnDate);
        btn.setText(d+"/"+(m+1)+"/"+y);
    }

    public void updateBtnTime(int h, int m){
        Button btn = (Button) findViewById(R.id.btnTime);
        btn.setText(h+":"+m);
    }
}
