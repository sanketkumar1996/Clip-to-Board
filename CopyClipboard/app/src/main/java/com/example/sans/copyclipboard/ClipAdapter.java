package com.example.sans.copyclipboard;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sans on 20/8/16.
 */
public class ClipAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<String> mList;
   private LayoutInflater mInflater;
    public ClipAdapter(Context context, ArrayList<String> item) {
        super(context, R.layout.item_list);
        this.mContext = context;
        this.mList = item;
       mInflater= LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_list,null);
            holder = new ViewHolder();
            holder.text = (TextView)convertView.findViewById(R.id.textItemView);
            holder.addClipboard = (Button)convertView.findViewById(R.id.buttonAdd);
            holder.removeClipboard = (Button)convertView.findViewById(R.id.buttonRemove);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //Clip clip= (Clip) mList.get(position);
        final Clip clip = new Clip();
            clip.setText(mList.get(position).toString());
            holder.text.setText(clip.getText());
        final View finalConvertView = convertView;
        holder.addClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(clip.getText());
                Snackbar.make(finalConvertView, " Added ", Snackbar.LENGTH_SHORT).show();
            }
        });
        final View finalConvertView1 = convertView;
        holder.removeClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mList.remove(mList.get(position));
                notifyDataSetChanged();
               Snackbar.make(finalConvertView1, "Removed", Snackbar.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
    public static class ViewHolder{
        TextView text;
        Button addClipboard;
        Button removeClipboard;
    }
}
