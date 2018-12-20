package gameloft.com.easyorder_server;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Them_ban extends AppCompatActivity {
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        final Spinner category = (Spinner) findViewById(R.id.categoryList);
        String[] items = new String[]{"Tiêu dùng hằng ngày", "Tiền nhà", "Tiền điện nước","Khác"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        category.setAdapter(adapter);

        final Spinner type = (Spinner) findViewById(R.id.typeList);
        String[] itemsType = new String[]{"Thu", "Chi", "Cho vay","Vay"};
        final ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsType);
        type.setAdapter(adapterType);
//
        System.out.println(category.getSelectedItem().toString());

        final Button btnDate = (Button)findViewById(R.id.btnDate);
        final Calendar myCalendar = Calendar.getInstance();
        SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/yyyy");
        btnDate.setText(spdf.format(myCalendar));
        final Button btnTime = (Button)findViewById(R.id.btnTime);
        SimpleDateFormat spdt = new SimpleDateFormat("hh:mm");
        btnTime.setText(spdt.format(myCalendar));



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                updateBtnDate(dayOfMonth,monthOfYear,year);
            }
        };

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                updateBtnTime(h,m);
            }
        };
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Them_ban.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(Them_ban.this,time,myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE),true).show();
            }
        });

        //main code

        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String moneyy = ((EditText)findViewById(R.id.edtMoney)).getText().toString();

                    final String categoryText = category.getSelectedItem().toString();
                    final String typeText = type.getSelectedItem().toString();
                    final String note = ((EditText)findViewById(R.id.note)).getText().toString();
                    final String dateText = btnDate.getText().toString();
                    final String timeText = btnTime.getText().toString();
                    if(categoryText.equals("")||typeText.equals("")||moneyy.equals("")){
                        Toast.makeText(Them_ban.this,"Vui lòng nhập đủ thông tin",Toast.LENGTH_SHORT).show();
                        return;
                    };
                    final long money = Long.parseLong(moneyy);
                    Calendar calendarKey = Calendar.getInstance();
                    String key = calendarKey.get(Calendar.YEAR)+""+calendarKey.get(Calendar.MONTH)+
                            calendarKey.get(Calendar.DAY_OF_MONTH)+calendarKey.get(Calendar.HOUR_OF_DAY)
                            +calendarKey.get(Calendar.MINUTE)+calendarKey.get(Calendar.MILLISECOND);
                    ItemThuCh item = new ItemThuCh(key,money,typeText,categoryText,note,dateText,timeText);
                    mDatabaseReference.child(GV.userName).child("listAction").child(key).setValue(item);
                    String bodyEmail = GV.userName+" vừa " + typeText + " số tiền: " + moneyy
                            + "\nNội dung: "+note;
                    onBackPressed();
                    GMailSender sender = new GMailSender("doanltm2018@gmail.com", "hien300HIE");

                    sender.sendMail("Thông báo từ quản lí thu chi",
                            bodyEmail,
                            GV.email,
                            GV.email);

                } catch (Exception e){
                    ((EditText)findViewById(R.id.note)).setText(e.toString());
                }

                //mDatabaseReference.child(GV.userName).
            }
        });
//
//        //---------------------------------------------------
//        mDatabaseReference.child("ban_max").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                crr_table = dataSnapshot.getValue(int.class);
//                soban.setText("Số lượng bàn hiện tại là: "+ (crr_table-1) );
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        //------------------------------------------------------
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(sobanthem.getText().toString().isEmpty()){
//                    Toast.makeText(Them_ban.this, "Vui lòng nhập số bàn", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                mProgressDialog.setCancelable(false);
//                mProgressDialog.setMessage("Đang thêm");
//                mProgressDialog.show();
//                int num = Integer.parseInt(sobanthem.getText().toString());
//                for (int i = crr_table; i< crr_table+num; i++){
//                    Table temp = new Table(i,0);
//                    mDatabaseReference.child("danhSachBanAn")
//                            .child("ban"+String.valueOf(i)).setValue(temp);
//                }
//                mDatabaseReference.child("ban_max").setValue(crr_table+num);
//                mProgressDialog.dismiss();
//                Toast.makeText(Them_ban.this, "Đã thêm "+ String.valueOf(num)+" bàn",
//                        Toast.LENGTH_SHORT).show();
//                onBackPressed();
//            }
//        });
//
//        //--------------------------------------------------------------
//        btn_remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(sobanthem.getText().toString().isEmpty()){
//                    Toast.makeText(Them_ban.this, "Vui lòng nhập số bàn", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                showAlertDialog();
//            }
//        });
    }
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Nếu xóa thì mọi thông tin liên quan đến bàn sẽ biến mất, bao gồm cả bàn đang có người ngồi." +
                "Bạn có chắc muốn xóa không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int o) {
                int num = Integer.parseInt(sobanthem.getText().toString());

                mProgressDialog.setMessage("Đang xóa");
                mProgressDialog.show();
                for(int i = crr_table-1; i > (crr_table-1-num); i-- ){
                    mDatabaseReference.child("danhSachBanAn").child("ban"+i).removeValue();
                }
                mDatabaseReference.child("ban_max").setValue(crr_table-num);
                Toast.makeText(Them_ban.this, "Đã xóa "+num+" bàn", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
