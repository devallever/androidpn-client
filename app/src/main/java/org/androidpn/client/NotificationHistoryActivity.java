package org.androidpn.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.androidpn.demoapp.R;
import org.litepal.crud.DataSupport;

import java.util.List;

public class NotificationHistoryActivity extends Activity {

    private ListView mListView;

    private NotificationHistoryAdapter mAdapter;

    private List<NotificationHistory> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_history);
        mList = DataSupport.findAll(NotificationHistory.class);
        mListView = (ListView)findViewById(R.id.id_notification_history_list_view);
        mAdapter = new NotificationHistoryAdapter(this, mList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NotificationHistory notificationHistory = mList.get(i);
                Intent intent = new Intent(NotificationHistoryActivity.this, NotificationDetailsActivity.class);
                intent.putExtra(Constants.NOTIFICATION_API_KEY,
                        notificationHistory.getApiKey());
                intent
                        .putExtra(Constants.NOTIFICATION_TITLE,
                                notificationHistory.getTitle());
                intent.putExtra(Constants.NOTIFICATION_MESSAGE,
                        notificationHistory.getMessage());
                intent.putExtra(Constants.NOTIFICATION_URI, notificationHistory.getUri());
                intent.putExtra(Constants.NOTIFICATION_IMAGE_URL,notificationHistory.getImageUrl());
                startActivity(intent);
            }
        });

        registerForContextMenu(mListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,0,0,"删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0){
            //删除操作
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = adapterContextMenuInfo.position;
            NotificationHistory notificationHistory = mList.get(index);
            notificationHistory.delete();
            mList.remove(index);
            mAdapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    public static void startSelf(Context context){
        Intent intent = new Intent(context, NotificationHistoryActivity.class);
        context.startActivity(intent);
    }
}
