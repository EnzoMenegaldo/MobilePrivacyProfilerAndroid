package fr.inria.diverse.mobileprivacyprofiler.utils;


import fr.inria.diverse.mobileprivacyprofiler.job.ExportDBJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanAppUsageJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanAuthenticatorJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanBatteryJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanBluetoothJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanCalendarJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanCellJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanContactJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanGeolocationJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanPhoneCallLogJob;
import fr.inria.diverse.mobileprivacyprofiler.job.ScanSMSJob;

public class MobilePrivacyJobManager {

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

}
