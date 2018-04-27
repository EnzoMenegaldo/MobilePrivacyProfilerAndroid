/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.Cell_NeighboringCellHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.DetectedWifi_AccessPoint;
// Start of user code protected additional OrmLiteDBHelper imports
import com.j256.ormlite.table.TableUtils;
// End of user code

/**
 * ORMLite Data base helper, designed to be used by android Activity
 */
public class OrmLiteDBHelper extends OrmLiteSqliteOpenHelper{
	
	public static final String LOG_TAG = "OrmLiteDBHelper";
	
	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "MobilePrivacyProfiler.db";
	// any time you make changes to your database objects, you may have to increase the database version
	// Start of user code OrmLiteDBHelper DB version MobilePrivacyProfiler
	private static final int DATABASE_VERSION = 1;
	// End of user code


	// the DAO object we use to access the MobilePrivacyProfilerDB_metadata table
	private RuntimeExceptionDao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataRuntimeDao = null;
	// the DAO object we use to access the ApplicationHistory table
	private RuntimeExceptionDao<ApplicationHistory, Integer> applicationHistoryRuntimeDao = null;
	// the DAO object we use to access the ApplicationUsageStats table
	private RuntimeExceptionDao<ApplicationUsageStats, Integer> applicationUsageStatsRuntimeDao = null;
	// the DAO object we use to access the Identity table
	private RuntimeExceptionDao<Identity, Integer> identityRuntimeDao = null;
	// the DAO object we use to access the Contact table
	private RuntimeExceptionDao<Contact, Integer> contactRuntimeDao = null;
	// the DAO object we use to access the ContactPhoneNumber table
	private RuntimeExceptionDao<ContactPhoneNumber, Integer> contactPhoneNumberRuntimeDao = null;
	// the DAO object we use to access the ContactPhysicalAddress table
	private RuntimeExceptionDao<ContactPhysicalAddress, Integer> contactPhysicalAddressRuntimeDao = null;
	// the DAO object we use to access the ContactEmail table
	private RuntimeExceptionDao<ContactEmail, Integer> contactEmailRuntimeDao = null;
	// the DAO object we use to access the KnownWifi table
	private RuntimeExceptionDao<KnownWifi, Integer> knownWifiRuntimeDao = null;
	// the DAO object we use to access the WifiAccessPoint table
	private RuntimeExceptionDao<WifiAccessPoint, Integer> wifiAccessPointRuntimeDao = null;
	// the DAO object we use to access the DetectedWifi table
	private RuntimeExceptionDao<DetectedWifi, Integer> detectedWifiRuntimeDao = null;
	// the DAO object we use to access the Geolocation table
	private RuntimeExceptionDao<Geolocation, Integer> geolocationRuntimeDao = null;
	// the DAO object we use to access the CalendarEvent table
	private RuntimeExceptionDao<CalendarEvent, Integer> calendarEventRuntimeDao = null;
	// the DAO object we use to access the PhoneCallLog table
	private RuntimeExceptionDao<PhoneCallLog, Integer> phoneCallLogRuntimeDao = null;
	// the DAO object we use to access the Cell table
	private RuntimeExceptionDao<Cell, Integer> cellRuntimeDao = null;
	// the DAO object we use to access the OtherCell table
	private RuntimeExceptionDao<OtherCell, Integer> otherCellRuntimeDao = null;
	// the DAO object we use to access the CdmaCellData table
	private RuntimeExceptionDao<CdmaCellData, Integer> cdmaCellDataRuntimeDao = null;
	// the DAO object we use to access the NeighboringCellHistory table
	private RuntimeExceptionDao<NeighboringCellHistory, Integer> neighboringCellHistoryRuntimeDao = null;
	// the DAO object we use to access the BluetoothDevice table
	private RuntimeExceptionDao<BluetoothDevice, Integer> bluetoothDeviceRuntimeDao = null;
	// the DAO object we use to access the BluetoothLog table
	private RuntimeExceptionDao<BluetoothLog, Integer> bluetoothLogRuntimeDao = null;
	// the DAO object we use to access the SMS table
	private RuntimeExceptionDao<SMS, Integer> sMSRuntimeDao = null;
	// the DAO object we use to access the BatteryUsage table
	private RuntimeExceptionDao<BatteryUsage, Integer> batteryUsageRuntimeDao = null;
	// the DAO object we use to access the WebHistory table
	private RuntimeExceptionDao<WebHistory, Integer> webHistoryRuntimeDao = null;
	
	// the DAO object we use to access the Cell_NeighboringCellHistory table implemeting the association
	private RuntimeExceptionDao<Cell_NeighboringCellHistory, Integer> cell_NeighboringCellHistoryRuntimeDao = null;
	// the DAO object we use to access the DetectedWifi_AccessPoint table implemeting the association
	private RuntimeExceptionDao<DetectedWifi_AccessPoint, Integer> detectedWifi_AccessPointRuntimeDao = null;

	public OrmLiteDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
	// Start of user code OrmLiteDBHelper onCreate MobilePrivacyProfiler
		try {
			Log.i(OrmLiteDBHelper.class.getName(), "onCreate");
			createAllTables(db);
		} catch (SQLException e) {
			Log.e(OrmLiteDBHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	// End of user code
	}
	public void createAllTables(SQLiteDatabase db)  throws SQLException {
		TableUtils.createTable(connectionSource, MobilePrivacyProfilerDB_metadata.class);
		TableUtils.createTable(connectionSource, ApplicationHistory.class);
		TableUtils.createTable(connectionSource, ApplicationUsageStats.class);
		TableUtils.createTable(connectionSource, Identity.class);
		TableUtils.createTable(connectionSource, Contact.class);
		TableUtils.createTable(connectionSource, ContactPhoneNumber.class);
		TableUtils.createTable(connectionSource, ContactPhysicalAddress.class);
		TableUtils.createTable(connectionSource, ContactEmail.class);
		TableUtils.createTable(connectionSource, KnownWifi.class);
		TableUtils.createTable(connectionSource, WifiAccessPoint.class);
		TableUtils.createTable(connectionSource, DetectedWifi.class);
		TableUtils.createTable(connectionSource, Geolocation.class);
		TableUtils.createTable(connectionSource, CalendarEvent.class);
		TableUtils.createTable(connectionSource, PhoneCallLog.class);
		TableUtils.createTable(connectionSource, Cell.class);
		TableUtils.createTable(connectionSource, OtherCell.class);
		TableUtils.createTable(connectionSource, CdmaCellData.class);
		TableUtils.createTable(connectionSource, NeighboringCellHistory.class);
		TableUtils.createTable(connectionSource, BluetoothDevice.class);
		TableUtils.createTable(connectionSource, BluetoothLog.class);
		TableUtils.createTable(connectionSource, SMS.class);
		TableUtils.createTable(connectionSource, BatteryUsage.class);
		TableUtils.createTable(connectionSource, WebHistory.class);
		TableUtils.createTable(connectionSource, Cell_NeighboringCellHistory.class);
		TableUtils.createTable(connectionSource, DetectedWifi_AccessPoint.class);
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
	// Start of user code OrmLiteDBHelper onUpgrade MobilePrivacyProfiler
		try {
			Log.i(OrmLiteDBHelper.class.getName(), "onUpgrade");
			dropAllTables(db);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(OrmLiteDBHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	// End of user code
	}
	public void dropAllTables(SQLiteDatabase db)  throws SQLException {
		TableUtils.dropTable(connectionSource, MobilePrivacyProfilerDB_metadata.class, true);
		TableUtils.dropTable(connectionSource, ApplicationHistory.class, true);
		TableUtils.dropTable(connectionSource, ApplicationUsageStats.class, true);
		TableUtils.dropTable(connectionSource, Identity.class, true);
		TableUtils.dropTable(connectionSource, Contact.class, true);
		TableUtils.dropTable(connectionSource, ContactPhoneNumber.class, true);
		TableUtils.dropTable(connectionSource, ContactPhysicalAddress.class, true);
		TableUtils.dropTable(connectionSource, ContactEmail.class, true);
		TableUtils.dropTable(connectionSource, KnownWifi.class, true);
		TableUtils.dropTable(connectionSource, WifiAccessPoint.class, true);
		TableUtils.dropTable(connectionSource, DetectedWifi.class, true);
		TableUtils.dropTable(connectionSource, Geolocation.class, true);
		TableUtils.dropTable(connectionSource, CalendarEvent.class, true);
		TableUtils.dropTable(connectionSource, PhoneCallLog.class, true);
		TableUtils.dropTable(connectionSource, Cell.class, true);
		TableUtils.dropTable(connectionSource, OtherCell.class, true);
		TableUtils.dropTable(connectionSource, CdmaCellData.class, true);
		TableUtils.dropTable(connectionSource, NeighboringCellHistory.class, true);
		TableUtils.dropTable(connectionSource, BluetoothDevice.class, true);
		TableUtils.dropTable(connectionSource, BluetoothLog.class, true);
		TableUtils.dropTable(connectionSource, SMS.class, true);
		TableUtils.dropTable(connectionSource, BatteryUsage.class, true);
		TableUtils.dropTable(connectionSource, WebHistory.class, true);
		TableUtils.dropTable(connectionSource, Cell_NeighboringCellHistory.class, true);
		TableUtils.dropTable(connectionSource, DetectedWifi_AccessPoint.class, true);
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our MobilePrivacyProfilerDB_metadata class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<MobilePrivacyProfilerDB_metadata, Integer> getMobilePrivacyProfilerDB_metadataDao() {
		if (mobilePrivacyProfilerDB_metadataRuntimeDao == null) {
			mobilePrivacyProfilerDB_metadataRuntimeDao = getRuntimeExceptionDao(MobilePrivacyProfilerDB_metadata.class);
		}
		return mobilePrivacyProfilerDB_metadataRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ApplicationHistory class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ApplicationHistory, Integer> getApplicationHistoryDao() {
		if (applicationHistoryRuntimeDao == null) {
			applicationHistoryRuntimeDao = getRuntimeExceptionDao(ApplicationHistory.class);
		}
		return applicationHistoryRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ApplicationUsageStats class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ApplicationUsageStats, Integer> getApplicationUsageStatsDao() {
		if (applicationUsageStatsRuntimeDao == null) {
			applicationUsageStatsRuntimeDao = getRuntimeExceptionDao(ApplicationUsageStats.class);
		}
		return applicationUsageStatsRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Identity class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Identity, Integer> getIdentityDao() {
		if (identityRuntimeDao == null) {
			identityRuntimeDao = getRuntimeExceptionDao(Identity.class);
		}
		return identityRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Contact class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Contact, Integer> getContactDao() {
		if (contactRuntimeDao == null) {
			contactRuntimeDao = getRuntimeExceptionDao(Contact.class);
		}
		return contactRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ContactPhoneNumber class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ContactPhoneNumber, Integer> getContactPhoneNumberDao() {
		if (contactPhoneNumberRuntimeDao == null) {
			contactPhoneNumberRuntimeDao = getRuntimeExceptionDao(ContactPhoneNumber.class);
		}
		return contactPhoneNumberRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ContactPhysicalAddress class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ContactPhysicalAddress, Integer> getContactPhysicalAddressDao() {
		if (contactPhysicalAddressRuntimeDao == null) {
			contactPhysicalAddressRuntimeDao = getRuntimeExceptionDao(ContactPhysicalAddress.class);
		}
		return contactPhysicalAddressRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ContactEmail class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ContactEmail, Integer> getContactEmailDao() {
		if (contactEmailRuntimeDao == null) {
			contactEmailRuntimeDao = getRuntimeExceptionDao(ContactEmail.class);
		}
		return contactEmailRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our KnownWifi class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<KnownWifi, Integer> getKnownWifiDao() {
		if (knownWifiRuntimeDao == null) {
			knownWifiRuntimeDao = getRuntimeExceptionDao(KnownWifi.class);
		}
		return knownWifiRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our WifiAccessPoint class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<WifiAccessPoint, Integer> getWifiAccessPointDao() {
		if (wifiAccessPointRuntimeDao == null) {
			wifiAccessPointRuntimeDao = getRuntimeExceptionDao(WifiAccessPoint.class);
		}
		return wifiAccessPointRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our DetectedWifi class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<DetectedWifi, Integer> getDetectedWifiDao() {
		if (detectedWifiRuntimeDao == null) {
			detectedWifiRuntimeDao = getRuntimeExceptionDao(DetectedWifi.class);
		}
		return detectedWifiRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Geolocation class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Geolocation, Integer> getGeolocationDao() {
		if (geolocationRuntimeDao == null) {
			geolocationRuntimeDao = getRuntimeExceptionDao(Geolocation.class);
		}
		return geolocationRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our CalendarEvent class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<CalendarEvent, Integer> getCalendarEventDao() {
		if (calendarEventRuntimeDao == null) {
			calendarEventRuntimeDao = getRuntimeExceptionDao(CalendarEvent.class);
		}
		return calendarEventRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our PhoneCallLog class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<PhoneCallLog, Integer> getPhoneCallLogDao() {
		if (phoneCallLogRuntimeDao == null) {
			phoneCallLogRuntimeDao = getRuntimeExceptionDao(PhoneCallLog.class);
		}
		return phoneCallLogRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Cell class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Cell, Integer> getCellDao() {
		if (cellRuntimeDao == null) {
			cellRuntimeDao = getRuntimeExceptionDao(Cell.class);
		}
		return cellRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our OtherCell class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<OtherCell, Integer> getOtherCellDao() {
		if (otherCellRuntimeDao == null) {
			otherCellRuntimeDao = getRuntimeExceptionDao(OtherCell.class);
		}
		return otherCellRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our CdmaCellData class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<CdmaCellData, Integer> getCdmaCellDataDao() {
		if (cdmaCellDataRuntimeDao == null) {
			cdmaCellDataRuntimeDao = getRuntimeExceptionDao(CdmaCellData.class);
		}
		return cdmaCellDataRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our NeighboringCellHistory class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<NeighboringCellHistory, Integer> getNeighboringCellHistoryDao() {
		if (neighboringCellHistoryRuntimeDao == null) {
			neighboringCellHistoryRuntimeDao = getRuntimeExceptionDao(NeighboringCellHistory.class);
		}
		return neighboringCellHistoryRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our BluetoothDevice class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<BluetoothDevice, Integer> getBluetoothDeviceDao() {
		if (bluetoothDeviceRuntimeDao == null) {
			bluetoothDeviceRuntimeDao = getRuntimeExceptionDao(BluetoothDevice.class);
		}
		return bluetoothDeviceRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our BluetoothLog class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<BluetoothLog, Integer> getBluetoothLogDao() {
		if (bluetoothLogRuntimeDao == null) {
			bluetoothLogRuntimeDao = getRuntimeExceptionDao(BluetoothLog.class);
		}
		return bluetoothLogRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SMS class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<SMS, Integer> getSMSDao() {
		if (sMSRuntimeDao == null) {
			sMSRuntimeDao = getRuntimeExceptionDao(SMS.class);
		}
		return sMSRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our BatteryUsage class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<BatteryUsage, Integer> getBatteryUsageDao() {
		if (batteryUsageRuntimeDao == null) {
			batteryUsageRuntimeDao = getRuntimeExceptionDao(BatteryUsage.class);
		}
		return batteryUsageRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our WebHistory class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<WebHistory, Integer> getWebHistoryDao() {
		if (webHistoryRuntimeDao == null) {
			webHistoryRuntimeDao = getRuntimeExceptionDao(WebHistory.class);
		}
		return webHistoryRuntimeDao;
	}

	
	
	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Cell_NeighboringCellHistory class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Cell_NeighboringCellHistory, Integer> getCell_NeighboringCellHistoryDao() {
		if (cell_NeighboringCellHistoryRuntimeDao == null) {
			cell_NeighboringCellHistoryRuntimeDao = getRuntimeExceptionDao(Cell_NeighboringCellHistory.class);
		}
		return cell_NeighboringCellHistoryRuntimeDao;
	}
	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our DetectedWifi_AccessPoint class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<DetectedWifi_AccessPoint, Integer> getDetectedWifi_AccessPointDao() {
		if (detectedWifi_AccessPointRuntimeDao == null) {
			detectedWifi_AccessPointRuntimeDao = getRuntimeExceptionDao(DetectedWifi_AccessPoint.class);
		}
		return detectedWifi_AccessPointRuntimeDao;
	}


	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		mobilePrivacyProfilerDB_metadataRuntimeDao = null;
		applicationHistoryRuntimeDao = null;
		applicationUsageStatsRuntimeDao = null;
		identityRuntimeDao = null;
		contactRuntimeDao = null;
		contactPhoneNumberRuntimeDao = null;
		contactPhysicalAddressRuntimeDao = null;
		contactEmailRuntimeDao = null;
		knownWifiRuntimeDao = null;
		wifiAccessPointRuntimeDao = null;
		detectedWifiRuntimeDao = null;
		geolocationRuntimeDao = null;
		calendarEventRuntimeDao = null;
		phoneCallLogRuntimeDao = null;
		cellRuntimeDao = null;
		otherCellRuntimeDao = null;
		cdmaCellDataRuntimeDao = null;
		neighboringCellHistoryRuntimeDao = null;
		bluetoothDeviceRuntimeDao = null;
		bluetoothLogRuntimeDao = null;
		sMSRuntimeDao = null;
		batteryUsageRuntimeDao = null;
		webHistoryRuntimeDao = null;
		cell_NeighboringCellHistoryRuntimeDao = null;
		detectedWifi_AccessPointRuntimeDao = null;
	}

	
	/**
     *
     */
	public MobilePrivacyProfilerDBHelper getMobilePrivacyProfilerDBHelper(){
		MobilePrivacyProfilerDBHelper helper = new MobilePrivacyProfilerDBHelper();
		try{
			helper.mobilePrivacyProfilerDB_metadataDao = getDao(MobilePrivacyProfilerDB_metadata.class);
			helper.applicationHistoryDao = getDao(ApplicationHistory.class);
			helper.applicationUsageStatsDao = getDao(ApplicationUsageStats.class);
			helper.identityDao = getDao(Identity.class);
			helper.contactDao = getDao(Contact.class);
			helper.contactPhoneNumberDao = getDao(ContactPhoneNumber.class);
			helper.contactPhysicalAddressDao = getDao(ContactPhysicalAddress.class);
			helper.contactEmailDao = getDao(ContactEmail.class);
			helper.knownWifiDao = getDao(KnownWifi.class);
			helper.wifiAccessPointDao = getDao(WifiAccessPoint.class);
			helper.detectedWifiDao = getDao(DetectedWifi.class);
			helper.geolocationDao = getDao(Geolocation.class);
			helper.calendarEventDao = getDao(CalendarEvent.class);
			helper.phoneCallLogDao = getDao(PhoneCallLog.class);
			helper.cellDao = getDao(Cell.class);
			helper.otherCellDao = getDao(OtherCell.class);
			helper.cdmaCellDataDao = getDao(CdmaCellData.class);
			helper.neighboringCellHistoryDao = getDao(NeighboringCellHistory.class);
			helper.bluetoothDeviceDao = getDao(BluetoothDevice.class);
			helper.bluetoothLogDao = getDao(BluetoothLog.class);
			helper.sMSDao = getDao(SMS.class);
			helper.batteryUsageDao = getDao(BatteryUsage.class);
			helper.webHistoryDao = getDao(WebHistory.class);
		helper.cell_NeighboringCellHistoryDao = getDao(Cell_NeighboringCellHistory.class);
		helper.detectedWifi_AccessPointDao = getDao(DetectedWifi_AccessPoint.class);
		} catch (SQLException e) {
			Log.e(OrmLiteDBHelper.class.getName(), "Can't get ", e);
		}
		return helper;
	}

}
