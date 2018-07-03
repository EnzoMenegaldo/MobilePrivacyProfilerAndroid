package fr.inria.diverse.mobileprivacyprofiler.utils;

import java.util.concurrent.Callable;

/**
 * This enum is used to list all the available services associated to the method which run the service
 */
public enum JobEnum {
    SCHEDULE_EXPORT_DATA(MobilePrivacyJobManager::scheduleExportDBJob,MobilePrivacyJobManager::cancelExportDBJob),
    SCHEDULE_SCAN_APP_USAGE(MobilePrivacyJobManager::scheduleScanAppUsageJob,MobilePrivacyJobManager::cancelScanAppUsageJob),
    SCHEDULE_SCAN_BATTERY_USAGE(MobilePrivacyJobManager::scheduleScanBatteryJob,MobilePrivacyJobManager::cancelScanBatteryJob),
    SCHEDULE_RECORD_LOCATION(MobilePrivacyJobManager::scheduleScanGeolocationJob,MobilePrivacyJobManager::cancelScanGeolocationJob),
    SCHEDULE_SCAN_AUTHENTICATORS(MobilePrivacyJobManager::scheduleScanAuthenticatorJob,MobilePrivacyJobManager::cancelScanAuthenticatorJob),
    //SCHEDULE_NET_ACTIVITY(),
    SCHEDULE_SCAN_CELL_INFO(MobilePrivacyJobManager::scheduleScanCellJob,MobilePrivacyJobManager::cancelScanCellJob),
    SCHEDULE_SCAN_WIFI(MobilePrivacyJobManager::registerWifiBroadcastReceiver,MobilePrivacyJobManager::unregisterWifiBroadcastReceiver),
    SCHEDULE_SCAN_BLUETOOTH(MobilePrivacyJobManager::scheduleScanBluetoothJob,MobilePrivacyJobManager::cancelScanBluetoothJob),
    SCHEDULE_SCAN_CONTACTS(MobilePrivacyJobManager::scheduleScanContactJob,MobilePrivacyJobManager::cancelScanContactJob),
    SCHEDULE_SCAN_SMS(MobilePrivacyJobManager::scheduleScanSMSJob,MobilePrivacyJobManager::cancelScanSMSJob),
    SCHEDULE_SCAN_CALL_HISTORY(MobilePrivacyJobManager::scheduleScanPhoneCallJob,MobilePrivacyJobManager::cancelScanPhoneCallJob),
    SCHEDULE_SCAN_CALENDAR_EVENT(MobilePrivacyJobManager::scheduleScanCalendarJob,MobilePrivacyJobManager::cancelScanCalendarJob);

    //We could have used A Consumer instead of Callable but this necessitates an API >= 24
    private Callable<Void> run ;
    private Callable<Void> cancel;


    private boolean isSelected;

    JobEnum(Callable<Void> run, Callable<Void> cancel) {
        this.run =run ;
        this.cancel = cancel;
        isSelected = true;
    }


    /**
     * Run the job
     */
    public void run(){
        try {

            this.run.call();
            this.isSelected=true;
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
            this.isSelected=false;
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

}
