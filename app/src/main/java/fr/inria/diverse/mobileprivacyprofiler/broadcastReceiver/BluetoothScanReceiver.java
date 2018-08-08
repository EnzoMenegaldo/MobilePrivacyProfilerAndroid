package fr.inria.diverse.mobileprivacyprofiler.broadcastReceiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Date;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.BluetoothLog;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDBHelper;

/**
 * This receiver gathers all intents which correspond to a bluetooth connection and then store the log in the database
 */
public class BluetoothScanReceiver extends BroadcastReceiver {

    public static final BluetoothScanReceiver INSTANCE = new BluetoothScanReceiver();
    private static final String TAG = BluetoothScanReceiver.class.getSimpleName();

    private BluetoothScanReceiver(){super();}


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            fr.inria.diverse.mobileprivacyprofiler.datamodel.BluetoothDevice bluetoothDevice;

            Date date = new Date();

            if (!MobilePrivacyProfilerDBHelper.getDBHelper(context).getMobilePrivacyProfilerDBHelper().isRecordedBluetoothDevice(device.getAddress())) {
                bluetoothDevice = new fr.inria.diverse.mobileprivacyprofiler.datamodel.BluetoothDevice();
                bluetoothDevice.setMac(device.getAddress());
                bluetoothDevice.setName(device.getName());
                bluetoothDevice.setType(device.getType());
                bluetoothDevice.setUserId(MobilePrivacyProfilerDBHelper.getDBHelper(context).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata().getUserId());

                Log.d(TAG, "Create new BluetoothDevice : MAC : " + bluetoothDevice.getMac() +
                        ", Name : " + bluetoothDevice.getName() +
                        ", type : " + bluetoothDevice.getType()
                        + ", UserId : " + MobilePrivacyProfilerDBHelper.getDBHelper(context).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata().getUserId()
                );

                MobilePrivacyProfilerDBHelper.getDBHelper(context).getBluetoothDeviceDao().create(bluetoothDevice);
            }else{
                bluetoothDevice = MobilePrivacyProfilerDBHelper.getDBHelper(context).getMobilePrivacyProfilerDBHelper().getBluetoothDeviceFromMac(device.getAddress());
            }

            BluetoothLog newBluetoothLog = new BluetoothLog();
            newBluetoothLog.setDevice(bluetoothDevice);
            newBluetoothLog.setDate(date);
            newBluetoothLog.setUserId(MobilePrivacyProfilerDBHelper.getDBHelper(context).getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata().getUserId());

            Log.d(TAG, "Create new BluetoothLog : date : " + date.toString() +
                    ", MAC : " + newBluetoothLog.getDevice().getMac()
            );

            MobilePrivacyProfilerDBHelper.getDBHelper(context).getBluetoothLogDao().create(newBluetoothLog);
        }
    }
}
