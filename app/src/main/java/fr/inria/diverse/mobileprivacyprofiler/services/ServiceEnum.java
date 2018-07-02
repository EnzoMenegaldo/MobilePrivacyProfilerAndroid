package fr.inria.diverse.mobileprivacyprofiler.services;

import android.content.Context;
import android.content.Intent;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import fr.inria.diverse.mobileprivacyprofiler.activities.Home_CustomViewActivity;

/**
 * This enum is used to list all the available services associated to the method which run the service
 */
public enum ServiceEnum {
    ACTION_SCAN_INSTALLED_APPLICATIONS(ScanActivityIntentService::startActionScanInstalledApplications),
    ACTION_SCAN_APP_USAGE(ScanActivityIntentService::startActionScanAppUsage),
    ACTION_SCAN_BATTERY_USAGE(ScanActivityIntentService::startActionScanBatteryUsage),
    ACTION_RECORD_LOCATION(ScanActivityIntentService::startActionRecordLocation),
    ACTION_SCAN_AUTHENTICATORS(ScanActivityIntentService::startActionScanAuthenticators),
    ACTION_NET_ACTIVITY(ScanActivityIntentService::startActionNetActivity),
    ACTION_SCAN_CELL_INFO(ScanConnectionIntentService::startActionScanCellInfo),
    ACTION_SCAN_WIFI(ScanConnectionIntentService::startActionScanWifi),
    ACTION_SCAN_BLUETOOTH(ScanConnectionIntentService::startActionScanBluetooth),
    ACTION_SCAN_CONTACTS(ScanContactIntentService::startActionScanContacts),
    ACTION_SCAN_SMS(ScanSocialIntentService::startActionScanSms),
    ACTION_SCAN_CALL_HISTORY(ScanSocialIntentService::startActionScanCallHistory),
    ACTION_SCAN_CALENDAR_EVENT(ScanSocialIntentService::startActionScanCalendarEvent);

    //We could have used A Consumer instead of Callable but this necessitates an API >= 24
    private Callable<Void> service ;


    private boolean isSelected;


    ServiceEnum(Callable<Void> service) {
        this.service = service ;
        isSelected = false;
    }

    public Callable<Void> getService() {
        return  this.service ;
    }

    /**
     * Call the service
     */
    public void call(){
        try {
            service.call();
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
