package org.androidpn.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.androidpn.demoapp.R;

import java.util.List;

public class NotificationHistoryAdapter extends BaseAdapter {
    private List<NotificationHistory> mNotificationHistoryList;
    private Context mContext;
    public NotificationHistoryAdapter(Context context, List<NotificationHistory> notificationHistoryList){
        mContext = context;
        mNotificationHistoryList = notificationHistoryList;
    }
    @Override
    public int getCount() {
        return mNotificationHistoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return mNotificationHistoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView != null){
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_notification_history,viewGroup,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        NotificationHistory notificationHistory = mNotificationHistoryList.get(position);
        viewHolder.tvTitle.setText(notificationHistory.getTitle());
        viewHolder.tvTime.setText(notificationHistory.getTime());

        return view;
    }

    private class ViewHolder{
        TextView tvTitle;
        TextView tvTime;
        public ViewHolder(View itemView){
            tvTitle = (TextView)itemView.findViewById(R.id.id_item_notification_history_tv_title);
            tvTime = (TextView)itemView.findViewById(R.id.id_item_notification_history_tv_time);
        }
    }
}
