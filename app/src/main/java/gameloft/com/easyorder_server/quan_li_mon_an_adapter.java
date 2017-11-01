package gameloft.com.easyorder_server;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Minh Hoang on 10/31/2017.
 */

public class quan_li_mon_an_adapter extends ArrayAdapter<String>{
    private Context mContext;
    private int mLayout;
    private ArrayList<String> mList;
    public quan_li_mon_an_adapter(Context context,int resource, ArrayList<String> list)
    {
        super(context, resource, list);
        mContext = context;
        mLayout = resource;
        mList = list;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            System.out.println("Create New View");
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mLayout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.text = (TextView)convertView.findViewById(R.id.tvquanlimonan);


            convertView.setTag(viewHolder);
        }
        else{
            System.out.println("Re-use View");
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final String m = mList.get(position);
        viewHolder.text.setText(m);


        return convertView;
    }

    private class ViewHolder{
        public TextView text;

    }
}
