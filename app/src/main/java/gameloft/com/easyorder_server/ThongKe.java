package gameloft.com.easyorder_server;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ThongKe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        ArrayList<ItemThuCh> arrayList = GV.arrayList;
        long sumThu = 0;
        long sumChi = 0;
        long sumChoVay =0;
        long sumVay = 0;

        long sumThuMonth = 0;
        long sumChiMonth = 0;
        long sumChoVayMonth =0;
        long sumVayMonth = 0;

        long sumThuYear = 0;
        long sumChiYear = 0;
        long sumChoVayYear =0;
        long sumVayYear = 0;

        int thisMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);

        for(ItemThuCh item : arrayList){
            String date = item.date;
            int month = Integer.parseInt(date.split("/")[1]);
            int year = Integer.parseInt(date.split("/")[2]);
            switch (item.type){
                case "Thu":
                    if(month==thisMonth&&year==thisYear) sumThuMonth+=item.money;
                    if(year==thisYear) sumThuYear+=item.money;
                    sumThu+=item.money;
                    break;
                case "Chi":
                    if(month==thisMonth&&year==thisYear) sumChiMonth+=item.money;
                    if(year==thisYear) sumChiYear+=item.money;
                    sumChi+=item.money;
                    break;
                case "Cho vay":
                    if(month==thisMonth&&year==thisYear) sumChoVayMonth+=item.money;
                    if(year==thisYear) sumChoVayYear+=item.money;
                    sumChoVay+=item.money;
                    break;
                case "Vay":
                    if(month==thisMonth&&year==thisYear) sumVayMonth+=item.money;
                    if(year==thisYear) sumVayYear+=item.money;
                    sumVay+=item.money;
            }
        }
        TextView tvThu = (TextView)findViewById(R.id.txtThu);
        TextView tvChi = (TextView)findViewById(R.id.txtChi);
        TextView tvChoVay = (TextView)findViewById(R.id.txtChoVay);
        TextView tvVay = (TextView)findViewById(R.id.txtVay);
        TextView tvThuChi = (TextView)findViewById(R.id.txtThuChi);
        NumberFormat format = NumberFormat.getNumberInstance();

        tvThu.setText("Tổng thu: "+format.format(sumThu));
        tvChi.setText("Tổng chi: "+format.format(sumChi));
        tvChoVay.setText("Tổng cho vay: "+format.format(sumChoVay));
        tvVay.setText("Tổng vay: "+format.format(sumVay));
        tvThuChi.setText("Thu - Chi: " + format.format(sumThu-sumChi));
        if((sumThu-sumChi)>0){
            tvThuChi.setTextColor(Color.GREEN);
        } else
        {
            tvThuChi.setTextColor(Color.RED);
        }

        PieChartView chartToday = (PieChartView)findViewById(R.id.chartToday);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(sumThu, Color.BLUE).setLabel("Thu: "+format.format(sumThu)));
        pieData.add(new SliceValue(sumChi, Color.RED).setLabel("Chi: "+format.format(sumChi)));
        pieData.add(new SliceValue(sumVay, Color.MAGENTA).setLabel("Vay: "+format.format(sumVay)));
        pieData.add(new SliceValue(sumChoVay, Color.GREEN).setLabel("Cho vay: "+format.format(sumChoVay)));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        chartToday.setPieChartData(pieChartData);

        PieChartView chartMonth = (PieChartView)findViewById(R.id.chartMonth);
        List<SliceValue> pieData1 = new ArrayList<>();
        pieData1.add(new SliceValue(sumThuMonth, Color.BLUE).setLabel("Thu: "+format.format(sumThuMonth)));
        pieData1.add(new SliceValue(sumChiMonth, Color.RED).setLabel("Chi: "+format.format(sumChiMonth)));
        pieData1.add(new SliceValue(sumVayMonth, Color.MAGENTA).setLabel("Vay: "+format.format(sumVayMonth)));
        pieData1.add(new SliceValue(sumChoVayMonth, Color.GREEN).setLabel("Cho vay: "+format.format(sumChoVayMonth)));

        PieChartData pieChartData1 = new PieChartData(pieData1);
        pieChartData1.setHasLabels(true);
        chartMonth.setPieChartData(pieChartData1);

        PieChartView chartYear = (PieChartView)findViewById(R.id.chartYear);
        List<SliceValue> pieData2 = new ArrayList<>();
        pieData2.add(new SliceValue(sumThuYear, Color.BLUE).setLabel("Thu: "+format.format(sumThuYear)));
        pieData2.add(new SliceValue(sumChiYear, Color.RED).setLabel("Chi: "+format.format(sumChiYear)));
        pieData2.add(new SliceValue(sumVayYear, Color.MAGENTA).setLabel("Vay: "+format.format(sumVayYear)));
        pieData2.add(new SliceValue(sumChoVayYear, Color.GREEN).setLabel("Cho vay: "+format.format(sumChoVayYear)));

        PieChartData pieChartData2 = new PieChartData(pieData2);
        pieChartData2.setHasLabels(true);
        chartYear.setPieChartData(pieChartData2);



    }
}
