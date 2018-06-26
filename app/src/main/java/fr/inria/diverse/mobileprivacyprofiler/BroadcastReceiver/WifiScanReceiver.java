package fr.inria.diverse.mobileprivacyprofiler.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import fr.inria.diverse.mobileprivacyprofiler.services.ScanConnectionIntentService;

public class WifiScanReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ScanConnectionIntentService.startActionScanWifi(context);
    }
}
