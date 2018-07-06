package fr.inria.diverse.mobileprivacyprofiler.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.Date;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.services.ScanConnectionIntentService;

public class WifiScanReceiver extends BroadcastReceiver {

    public static final WifiScanReceiver INSTANCE = new WifiScanReceiver();
    private static final String TAG = WifiScanReceiver.class.getSimpleName();

    private WifiScanReceiver(){super();}

    /*public synchronized static WifiScanReceiver getInstance() {

        if (mWifiScanReceiver == null)
            mWifiScanReceiver = new WifiScanReceiver();

        return mWifiScanReceiver;

    }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        Date now = new Date();
        Date lastScan = OpenHelperManager.getHelper(context, OrmLiteDBHelper.class).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata().getLastWifiScan();
        if(null==lastScan||now.after(new Date(lastScan.getTime()+900000))){
        ScanConnectionIntentService.startActionScanWifi();
        }
        else{
            Log.d(TAG,"Too early for a new WifiScan now : "+now+"("+now.getTime()+") waiting : "+new Date(lastScan.getTime()+90000).toString());
        }
    }
}
