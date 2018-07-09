package fr.inria.diverse.mobileprivacyprofiler.utils;


import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;

import fr.inria.diverse.mobileprivacyprofiler.activities.AdvancedScanning_CustomViewActivity;
import fr.inria.diverse.mobileprivacyprofiler.activities.Home_CustomViewActivity;
import fr.inria.diverse.mobileprivacyprofiler.broadcastReceiver.WifiScanReceiver;
import fr.inria.diverse.mobileprivacyprofiler.job.ExportDBJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanAppUsageJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanAuthenticatorJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanBatteryJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanBluetoothJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanCalendarJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanCellJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanContactJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanGeolocationJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanNetActivityJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanPhoneCallLogJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanSMSJob;

public class MobilePrivacyJobManager {

    private static final String TAG = MobilePrivacyJobManager.class.getSimpleName();
    private static WifiScanReceiver wifiScanReceiver;

    /*protected*/ MobilePrivacyJobManager(){}

    static Void scheduleExportDBJob(){
        ExportDBJob.schedule();
        return null;
    }
    static Void cancelExportDBJob(){
        ExportDBJob.cancelRequest();
        return null;
    }

    static Void scheduleScanAppUsageJob(){
        ScanAppUsageJob.schedule();
        return null;
    }
    static Void cancelScanAppUsageJob(){
        ScanAppUsageJob.cancelRequest();
        return null;
    }

    static Void scheduleScanBatteryJob(){
        ScanBatteryJob.schedule();
        return null;
    }
    static Void cancelScanBatteryJob(){
        ScanBatteryJob.cancelRequest();
        return null;
    }

    static Void scheduleScanBluetoothJob(){
        ScanBluetoothJob.schedule();
        return null;
    }
    static Void cancelScanBluetoothJob(){
        ScanBluetoothJob.cancelRequest();
        return null;
    }

    static Void scheduleScanCalendarJob(){
        ScanCalendarJob.schedule();
        return null;
    }
    static Void cancelScanCalendarJob() {
        ScanCalendarJob.cancelRequest();
        return null;
    }

    static Void scheduleScanCellJob(){
        ScanCellJob.schedule();
        return null;
    }
    static Void cancelScanCellJob(){
        ScanCellJob.cancelRequest();
        return null;
    }

    static Void scheduleScanContactJob(){
        ScanContactJob.schedule();
        return null;
    }
    static Void cancelScanContactJob() {
        ScanContactJob.cancelRequest();
        return null;
    }

    static Void scheduleScanGeolocationJob(){
        ScanGeolocationJob.schedule();
        return null;
    }
    static Void cancelScanGeolocationJob() {
        ScanGeolocationJob.cancelRequest();
        return null;
    }

    static Void scheduleScanPhoneCallJob(){
        ScanPhoneCallLogJob.schedule();
        return null;
    }
    static Void cancelScanPhoneCallJob() {
        ScanPhoneCallLogJob.cancelRequest();
        return null;
    }

    static Void scheduleScanSMSJob(){
        ScanSMSJob.schedule();
        return null;
    }
    static Void cancelScanSMSJob() {
        ScanSMSJob.cancelRequest();
        return null;
    }

    static Void scheduleScanAuthenticatorJob(){
        ScanAuthenticatorJob.schedule();
        return null;
    }
    static Void cancelScanAuthenticatorJob() {
        ScanAuthenticatorJob.cancelRequest();
        return null;
    }

    static Void scheduleScanNetActivityJob(){
        ScanNetActivityJob.schedule();
        return null;
    }
    static Void cancelScanNetActivityJob() {
        ScanNetActivityJob.cancelRequest(Home_CustomViewActivity.getContext());
        return null;
    }

    static Void registerWifiBroadcastReceiver(){
        Log.d(TAG,"RegisterWifiBroadcastReceiver");
        wifiScanReceiver = WifiScanReceiver.getInstance();
        Home_CustomViewActivity.getContext().registerReceiver(wifiScanReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        return null;
    }
    static Void unregisterWifiBroadcastReceiver() {
        Log.d(TAG,"UnregisterWifiBroadcastReceiver");
        wifiScanReceiver = WifiScanReceiver.getInstance();
        Home_CustomViewActivity.getContext().unregisterReceiver(wifiScanReceiver);
        return null;
    }

}
