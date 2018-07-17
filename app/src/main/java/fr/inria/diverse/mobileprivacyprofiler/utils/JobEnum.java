package fr.inria.diverse.mobileprivacyprofiler.utils;

import java.util.concurrent.Callable;

import fr.inria.diverse.mobileprivacyprofiler.R;

/**
 * This enum is used to list all the available services associated to the method which run the service
 */
public enum JobEnum {
    SCHEDULE_EXPORT_DATA(R.string.job_export_data,MobilePrivacyJobManager::scheduleExportDBJob,MobilePrivacyJobManager::cancelExportDBJob),
    SCHEDULE_SCAN_APP_USAGE(R.string.job_scan_app_usage,MobilePrivacyJobManager::scheduleScanAppUsageJob,MobilePrivacyJobManager::cancelScanAppUsageJob),
    SCHEDULE_SCAN_BATTERY_USAGE(R.string.job_scan_battery_usage,MobilePrivacyJobManager::scheduleScanBatteryJob,MobilePrivacyJobManager::cancelScanBatteryJob),
    SCHEDULE_RECORD_LOCATION(R.string.job_record_location,MobilePrivacyJobManager::scheduleScanGeolocationJob,MobilePrivacyJobManager::cancelScanGeolocationJob),
    SCHEDULE_SCAN_AUTHENTICATORS(R.string.job_scan_authenticators,MobilePrivacyJobManager::scheduleScanAuthenticatorJob,MobilePrivacyJobManager::cancelScanAuthenticatorJob),
    SCHEDULE_NET_ACTIVITY(R.string.job_scan_net_activity,MobilePrivacyJobManager::scheduleScanNetActivityJob,MobilePrivacyJobManager::cancelScanNetActivityJob),
    SCHEDULE_SCAN_CELL_INFO(R.string.job_scan_cell_info,MobilePrivacyJobManager::scheduleScanCellJob,MobilePrivacyJobManager::cancelScanCellJob),
    SCHEDULE_SCAN_WIFI(R.string.job_scan_wifi,MobilePrivacyJobManager::registerWifiBroadcastReceiver,MobilePrivacyJobManager::unregisterWifiBroadcastReceiver),
    SCHEDULE_SCAN_BLUETOOTH(R.string.job_scan_bluetooth,MobilePrivacyJobManager::scheduleScanBluetoothJob,MobilePrivacyJobManager::cancelScanBluetoothJob),
    SCHEDULE_SCAN_CONTACTS(R.string.job_scan_contacts,MobilePrivacyJobManager::scheduleScanContactJob,MobilePrivacyJobManager::cancelScanContactJob),
    SCHEDULE_SCAN_SMS(R.string.job_scan_sms,MobilePrivacyJobManager::scheduleScanSMSJob,MobilePrivacyJobManager::cancelScanSMSJob),
    SCHEDULE_SCAN_CALL_HISTORY(R.string.job_scan_call_history,MobilePrivacyJobManager::scheduleScanPhoneCallJob,MobilePrivacyJobManager::cancelScanPhoneCallJob),
    SCHEDULE_SCAN_CALENDAR_EVENT(R.string.job_scan_calendar_event,MobilePrivacyJobManager::scheduleScanCalendarJob,MobilePrivacyJobManager::cancelScanCalendarJob);

    //We could have used A Consumer instead of Callable but this necessitates an API >= 24
    private Callable<Void> run ;
    private Callable<Void> cancel;

    private int name;
    private boolean isSelected;

    JobEnum(int name, Callable<Void> run, Callable<Void> cancel) {
        this.run =run ;
        this.cancel = cancel;
        this.name = name;
        isSelected = true;
    }


    /**
     * Run the job
     */
    public void run(){
        try {

            this.run.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancel the job
     */
    public void cancel(){
        try {
            this.cancel.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean bool){
        isSelected = bool;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
