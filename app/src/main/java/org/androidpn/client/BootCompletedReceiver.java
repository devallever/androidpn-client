package org.androidpn.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.androidpn.demoapp.R;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Boot Complete", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean autoStart = sharedPreferences.getBoolean(Constants.SETTINGS_AUTO_START, true);
        if (autoStart){
            //启动服务
            ServiceManager serviceManager = new ServiceManager(context);
            serviceManager.setNotificationIcon(R.drawable.notification);
            serviceManager.startService();
        }
    }
}
