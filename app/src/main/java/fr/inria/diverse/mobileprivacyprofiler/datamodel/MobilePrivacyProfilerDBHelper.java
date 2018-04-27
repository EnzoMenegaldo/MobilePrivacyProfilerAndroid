/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.GSMCell_NeighboringCellHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.DetectedWifi_AccessPoint;

//Start of user code additional import for MobilePrivacyProfilerDBHelper
import android.util.Log;
import java.util.List;
//End of user code
/**
 * Context class used to simplify the access to the different DAOs of the application
 */
public class MobilePrivacyProfilerDBHelper {
	//Start of user code additional variables for MobilePrivacyProfilerDBHelper
	private static final String TAG = MobilePrivacyProfilerDBHelper.class.getSimpleName();
	//End of user code

	public Dao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao;
	//public RuntimeExceptionDao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao;
	public Dao<ApplicationHistory, Integer> applicationHistoryDao;
	//public RuntimeExceptionDao<ApplicationHistory, Integer> applicationHistoryDao;
	public Dao<ApplicationUsageStats, Integer> applicationUsageStatsDao;
	//public RuntimeExceptionDao<ApplicationUsageStats, Integer> applicationUsageStatsDao;
	public Dao<Identity, Integer> identityDao;
	//public RuntimeExceptionDao<Identity, Integer> identityDao;
	public Dao<Contact, Integer> contactDao;
	//public RuntimeExceptionDao<Contact, Integer> contactDao;
	public Dao<ContactPhoneNumber, Integer> contactPhoneNumberDao;
	//public RuntimeExceptionDao<ContactPhoneNumber, Integer> contactPhoneNumberDao;
	public Dao<ContactPhysicalAddress, Integer> contactPhysicalAddressDao;
	//public RuntimeExceptionDao<ContactPhysicalAddress, Integer> contactPhysicalAddressDao;
	public Dao<ContactEmail, Integer> contactEmailDao;
	//public RuntimeExceptionDao<ContactEmail, Integer> contactEmailDao;
	public Dao<KnownWifi, Integer> knownWifiDao;
	//public RuntimeExceptionDao<KnownWifi, Integer> knownWifiDao;
	public Dao<WifiAccessPoint, Integer> wifiAccessPointDao;
	//public RuntimeExceptionDao<WifiAccessPoint, Integer> wifiAccessPointDao;
	public Dao<DetectedWifi, Integer> detectedWifiDao;
	//public RuntimeExceptionDao<DetectedWifi, Integer> detectedWifiDao;
	public Dao<Geolocation, Integer> geolocationDao;
	//public RuntimeExceptionDao<Geolocation, Integer> geolocationDao;
	public Dao<CalendarEvent, Integer> calendarEventDao;
	//public RuntimeExceptionDao<CalendarEvent, Integer> calendarEventDao;
	public Dao<PhoneCallLog, Integer> phoneCallLogDao;
	//public RuntimeExceptionDao<PhoneCallLog, Integer> phoneCallLogDao;
	public Dao<GSMCell, Integer> gSMCellDao;
	//public RuntimeExceptionDao<GSMCell, Integer> gSMCellDao;
	public Dao<NeighboringCellHistory, Integer> neighboringCellHistoryDao;
	//public RuntimeExceptionDao<NeighboringCellHistory, Integer> neighboringCellHistoryDao;
	public Dao<BluetoothDevice, Integer> bluetoothDeviceDao;
	//public RuntimeExceptionDao<BluetoothDevice, Integer> bluetoothDeviceDao;
	public Dao<BluetoothLog, Integer> bluetoothLogDao;
	//public RuntimeExceptionDao<BluetoothLog, Integer> bluetoothLogDao;
	public Dao<SMS, Integer> sMSDao;
	//public RuntimeExceptionDao<SMS, Integer> sMSDao;
	public Dao<BatteryUsage, Integer> batteryUsageDao;
	//public RuntimeExceptionDao<BatteryUsage, Integer> batteryUsageDao;
	public Dao<WebHistory, Integer> webHistoryDao;
	//public RuntimeExceptionDao<WebHistory, Integer> webHistoryDao;
	public Dao<GSMCell_NeighboringCellHistory, Integer> gSMCell_NeighboringCellHistoryDao;
	//public RuntimeExceptionDao<GSMCell_NeighboringCellHistory, Integer> gSMCell_NeighboringCellHistoryDao;
	public Dao<DetectedWifi_AccessPoint, Integer> detectedWifi_AccessPointDao;
	//public RuntimeExceptionDao<DetectedWifi_AccessPoint, Integer> detectedWifi_AccessPointDao;

	
	public MobilePrivacyProfilerDBHelper(){
	}

	public MobilePrivacyProfilerDBHelper(
		Dao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao,
		Dao<ApplicationHistory, Integer> applicationHistoryDao,
		Dao<ApplicationUsageStats, Integer> applicationUsageStatsDao,
		Dao<Identity, Integer> identityDao,
		Dao<Contact, Integer> contactDao,
		Dao<ContactPhoneNumber, Integer> contactPhoneNumberDao,
		Dao<ContactPhysicalAddress, Integer> contactPhysicalAddressDao,
		Dao<ContactEmail, Integer> contactEmailDao,
		Dao<KnownWifi, Integer> knownWifiDao,
		Dao<WifiAccessPoint, Integer> wifiAccessPointDao,
		Dao<DetectedWifi, Integer> detectedWifiDao,
		Dao<Geolocation, Integer> geolocationDao,
		Dao<CalendarEvent, Integer> calendarEventDao,
		Dao<PhoneCallLog, Integer> phoneCallLogDao,
		Dao<GSMCell, Integer> gSMCellDao,
		Dao<NeighboringCellHistory, Integer> neighboringCellHistoryDao,
		Dao<BluetoothDevice, Integer> bluetoothDeviceDao,
		Dao<BluetoothLog, Integer> bluetoothLogDao,
		Dao<SMS, Integer> sMSDao,
		Dao<BatteryUsage, Integer> batteryUsageDao,
		Dao<WebHistory, Integer> webHistoryDao,
        Dao<GSMCell_NeighboringCellHistory, Integer> gSMCell_NeighboringCellHistoryDao,
		Dao<DetectedWifi_AccessPoint, Integer> detectedWifi_AccessPointDao
	){
		this.mobilePrivacyProfilerDB_metadataDao = mobilePrivacyProfilerDB_metadataDao;
		this.applicationHistoryDao = applicationHistoryDao;
		this.applicationUsageStatsDao = applicationUsageStatsDao;
		this.identityDao = identityDao;
		this.contactDao = contactDao;
		this.contactPhoneNumberDao = contactPhoneNumberDao;
		this.contactPhysicalAddressDao = contactPhysicalAddressDao;
		this.contactEmailDao = contactEmailDao;
		this.knownWifiDao = knownWifiDao;
		this.wifiAccessPointDao = wifiAccessPointDao;
		this.detectedWifiDao = detectedWifiDao;
		this.geolocationDao = geolocationDao;
		this.calendarEventDao = calendarEventDao;
		this.phoneCallLogDao = phoneCallLogDao;
		this.gSMCellDao = gSMCellDao;
		this.neighboringCellHistoryDao = neighboringCellHistoryDao;
		this.bluetoothDeviceDao = bluetoothDeviceDao;
		this.bluetoothLogDao = bluetoothLogDao;
		this.sMSDao = sMSDao;
		this.batteryUsageDao = batteryUsageDao;
		this.webHistoryDao = webHistoryDao;
		this.gSMCell_NeighboringCellHistoryDao = gSMCell_NeighboringCellHistoryDao;
		this.detectedWifi_AccessPointDao = detectedWifi_AccessPointDao;
	}

	//Start of user code additional methods for MobilePrivacyProfilerDBHelper

	/** find ApplicationHistory in the base using appName
	 * @param appName
	 * @return
	 */
	public ApplicationHistory queryApplicationHistoryByAppName(String appName) {
		try {
			ApplicationHistory queryApplicationHistory = new ApplicationHistory();
			queryApplicationHistory.setAppName(appName);
			List<ApplicationHistory> fichesDeLaBase = this.applicationHistoryDao.queryForMatching(queryApplicationHistory);
			if(fichesDeLaBase.size() != 1){
				Log.d(TAG,"Application with appName "+queryApplicationHistory.getAppName()+ " doesn't exist in the base");
				return null;
			}
			return fichesDeLaBase.get(0);
		} catch (SQLException e) {
			Log.e(TAG,"error while querying application with appName "+appName+ " in the base", e);
		}
		return null;
	}

	/** find ApplicationHistory in the base using appName
	 * @param packageName
	 * @return
	 */
	public ApplicationHistory queryApplicationHistoryByPackageName(String packageName) {
		try {
			ApplicationHistory queryApplicationHistory = new ApplicationHistory();
			queryApplicationHistory.setPackageName(packageName);
			List<ApplicationHistory> fichesDeLaBase = this.applicationHistoryDao.queryForMatching(queryApplicationHistory);
			if(fichesDeLaBase.size() != 1){
				Log.d(TAG,"Application with packageName "+queryApplicationHistory.getPackageName()+ " doesn't exist in the base");
				return null;
			}
			return fichesDeLaBase.get(0);
		} catch (SQLException e) {
			Log.e(TAG,"error while querying application with packageName "+packageName+ " in the base", e);
		}
		return null;
	}

	/**
	 * On the device this table contains a single entry
	 * @return
	 */
	public MobilePrivacyProfilerDB_metadata getDeviceDBMetadata() {
		MobilePrivacyProfilerDB_metadata metadata;
		CloseableIterator<MobilePrivacyProfilerDB_metadata> it = this.mobilePrivacyProfilerDB_metadataDao.iterator();
		if(it.hasNext()){
			metadata = it.next();
			it.closeQuietly();
		} else {
			metadata = new MobilePrivacyProfilerDB_metadata();
			try {
				this.mobilePrivacyProfilerDB_metadataDao.create(metadata);
			} catch (SQLException e) {
				Log.e(TAG,"error while creating MobilePrivacyProfilerDB_metadata", e);
			}
		}
		return metadata;
	}
	/**
	 * Get the last battery entry
	 * @return last BatteryUsage
	 */
	public BatteryUsage getLastBatteryUsage () {
		//TODO
		/* PreparedQuery<BatteryUsage> queryLastBatteryUsage;
		BatteryUsage lastBatteryUsage=null;

		queryLastBatteryUsage=

		lastBatteryUsage=this.queryForFirst(queryLastBatteryUsage);
		//Query for and return the first item in the object table which matches the PreparedQuery.
*/
		return null;
	}

	//End of user code

}
