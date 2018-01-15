/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

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

	public Dao<ApplicationHistory, Integer> applicationHistoryDao;
	//public RuntimeExceptionDao<ApplicationHistory, Integer> applicationHistoryDao;
	public Dao<ApplicationUsageStats, Integer> applicationUsageStatsDao;
	//public RuntimeExceptionDao<ApplicationUsageStats, Integer> applicationUsageStatsDao;
	public Dao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao;
	//public RuntimeExceptionDao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao;
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
	public Dao<DetectedWifi_AccessPoint, Integer> detectedWifi_AccessPointDao;
	//public RuntimeExceptionDao<DetectedWifi_AccessPoint, Integer> detectedWifi_AccessPointDao;

	
	public MobilePrivacyProfilerDBHelper(){
	}

	public MobilePrivacyProfilerDBHelper(
		Dao<ApplicationHistory, Integer> applicationHistoryDao,
		Dao<ApplicationUsageStats, Integer> applicationUsageStatsDao,
		Dao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao,
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
        Dao<DetectedWifi_AccessPoint, Integer> detectedWifi_AccessPointDao
	){
		this.applicationHistoryDao = applicationHistoryDao;
		this.applicationUsageStatsDao = applicationUsageStatsDao;
		this.mobilePrivacyProfilerDB_metadataDao = mobilePrivacyProfilerDB_metadataDao;
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
	//End of user code

}
