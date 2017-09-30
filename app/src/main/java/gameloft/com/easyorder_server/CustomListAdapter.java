package gameloft.com.easyorder_server;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Minh Hoang on 9/30/2017.
 */

public class CustomListAdapter extends BaseAdapter {
    private List<Food> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public CustomListAdapter(Context aContext,  List<Food> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_quanlymonan, null);
            holder = new ViewHolder();
            holder.foodView = (ImageView) convertView.findViewById(R.id.imageView_food);
            holder.foodNameView = (TextView) convertView.findViewById(R.id.textView_foodName);
            holder.priceView = (TextView) convertView.findViewById(R.id.textView_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Food food = this.listData.get(position);
        holder.foodNameView.setText(food.getFoodName());
        holder.priceView.setText("Price: " + food.getPrice());

        int imageId = this.getMipmapResIdByName(food.getFoodName());

        holder.foodView.setImageResource(imageId);

        return convertView;
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        ImageView foodView;
        TextView foodNameView;
        TextView priceView;
    }
}
