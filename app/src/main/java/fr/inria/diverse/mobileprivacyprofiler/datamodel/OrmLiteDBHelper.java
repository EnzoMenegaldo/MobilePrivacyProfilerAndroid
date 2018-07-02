/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
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
	// the DAO object we use to access the Authentification table
	private RuntimeExceptionDao<Authentification, Integer> authentificationRuntimeDao = null;
	// the DAO object we use to access the Contact table
	private RuntimeExceptionDao<Contact, Integer> contactRuntimeDao = null;
	// the DAO object we use to access the ContactOrganisation table
	private RuntimeExceptionDao<ContactOrganisation, Integer> contactOrganisationRuntimeDao = null;
	// the DAO object we use to access the ContactIM table
	private RuntimeExceptionDao<ContactIM, Integer> contactIMRuntimeDao = null;
	// the DAO object we use to access the ContactEvent table
	private RuntimeExceptionDao<ContactEvent, Integer> contactEventRuntimeDao = null;
	// the DAO object we use to access the ContactPhoneNumber table
	private RuntimeExceptionDao<ContactPhoneNumber, Integer> contactPhoneNumberRuntimeDao = null;
	// the DAO object we use to access the ContactPhysicalAddress table
	private RuntimeExceptionDao<ContactPhysicalAddress, Integer> contactPhysicalAddressRuntimeDao = null;
	// the DAO object we use to access the ContactEmail table
	private RuntimeExceptionDao<ContactEmail, Integer> contactEmailRuntimeDao = null;
	// the DAO object we use to access the KnownWifi table
	private RuntimeExceptionDao<KnownWifi, Integer> knownWifiRuntimeDao = null;
	// the DAO object we use to access the LogsWifi table
	private RuntimeExceptionDao<LogsWifi, Integer> logsWifiRuntimeDao = null;
	// the DAO object we use to access the Geolocation table
	private RuntimeExceptionDao<Geolocation, Integer> geolocationRuntimeDao = null;
	// the DAO object we use to access the CalendarEvent table
	private RuntimeExceptionDao<CalendarEvent, Integer> calendarEventRuntimeDao = null;
	// the DAO object we use to access the PhoneCallLog table
	private RuntimeExceptionDao<PhoneCallLog, Integer> phoneCallLogRuntimeDao = null;
	// the DAO object we use to access the Cell table
	private RuntimeExceptionDao<Cell, Integer> cellRuntimeDao = null;
	// the DAO object we use to access the OtherCellData table
	private RuntimeExceptionDao<OtherCellData, Integer> otherCellDataRuntimeDao = null;
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
		TableUtils.createTable(connectionSource, Authentification.class);
		TableUtils.createTable(connectionSource, Contact.class);
		TableUtils.createTable(connectionSource, ContactOrganisation.class);
		TableUtils.createTable(connectionSource, ContactIM.class);
		TableUtils.createTable(connectionSource, ContactEvent.class);
		TableUtils.createTable(connectionSource, ContactPhoneNumber.class);
		TableUtils.createTable(connectionSource, ContactPhysicalAddress.class);
		TableUtils.createTable(connectionSource, ContactEmail.class);
		TableUtils.createTable(connectionSource, KnownWifi.class);
		TableUtils.createTable(connectionSource, LogsWifi.class);
		TableUtils.createTable(connectionSource, Geolocation.class);
		TableUtils.createTable(connectionSource, CalendarEvent.class);
		TableUtils.createTable(connectionSource, PhoneCallLog.class);
		TableUtils.createTable(connectionSource, Cell.class);
		TableUtils.createTable(connectionSource, OtherCellData.class);
		TableUtils.createTable(connectionSource, CdmaCellData.class);
		TableUtils.createTable(connectionSource, NeighboringCellHistory.class);
		TableUtils.createTable(connectionSource, BluetoothDevice.class);
		TableUtils.createTable(connectionSource, BluetoothLog.class);
		TableUtils.createTable(connectionSource, SMS.class);
		TableUtils.createTable(connectionSource, BatteryUsage.class);
		TableUtils.createTable(connectionSource, WebHistory.class);
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
		TableUtils.dropTable(connectionSource, Authentification.class, true);
		TableUtils.dropTable(connectionSource, Contact.class, true);
		TableUtils.dropTable(connectionSource, ContactOrganisation.class, true);
		TableUtils.dropTable(connectionSource, ContactIM.class, true);
		TableUtils.dropTable(connectionSource, ContactEvent.class, true);
		TableUtils.dropTable(connectionSource, ContactPhoneNumber.class, true);
		TableUtils.dropTable(connectionSource, ContactPhysicalAddress.class, true);
		TableUtils.dropTable(connectionSource, ContactEmail.class, true);
		TableUtils.dropTable(connectionSource, KnownWifi.class, true);
		TableUtils.dropTable(connectionSource, LogsWifi.class, true);
		TableUtils.dropTable(connectionSource, Geolocation.class, true);
		TableUtils.dropTable(connectionSource, CalendarEvent.class, true);
		TableUtils.dropTable(connectionSource, PhoneCallLog.class, true);
		TableUtils.dropTable(connectionSource, Cell.class, true);
		TableUtils.dropTable(connectionSource, OtherCellData.class, true);
		TableUtils.dropTable(connectionSource, CdmaCellData.class, true);
		TableUtils.dropTable(connectionSource, NeighboringCellHistory.class, true);
		TableUtils.dropTable(connectionSource, BluetoothDevice.class, true);
		TableUtils.dropTable(connectionSource, BluetoothLog.class, true);
		TableUtils.dropTable(connectionSource, SMS.class, true);
		TableUtils.dropTable(connectionSource, BatteryUsage.class, true);
		TableUtils.dropTable(connectionSource, WebHistory.class, true);
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
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Authentification class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Authentification, Integer> getAuthentificationDao() {
		if (authentificationRuntimeDao == null) {
			authentificationRuntimeDao = getRuntimeExceptionDao(Authentification.class);
		}
		return authentificationRuntimeDao;
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
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ContactOrganisation class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ContactOrganisation, Integer> getContactOrganisationDao() {
		if (contactOrganisationRuntimeDao == null) {
			contactOrganisationRuntimeDao = getRuntimeExceptionDao(ContactOrganisation.class);
		}
		return contactOrganisationRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ContactIM class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ContactIM, Integer> getContactIMDao() {
		if (contactIMRuntimeDao == null) {
			contactIMRuntimeDao = getRuntimeExceptionDao(ContactIM.class);
		}
		return contactIMRuntimeDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ContactEvent class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ContactEvent, Integer> getContactEventDao() {
		if (contactEventRuntimeDao == null) {
			contactEventRuntimeDao = getRuntimeExceptionDao(ContactEvent.class);
		}
		return contactEventRuntimeDao;
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
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our LogsWifi class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<LogsWifi, Integer> getLogsWifiDao() {
		if (logsWifiRuntimeDao == null) {
			logsWifiRuntimeDao = getRuntimeExceptionDao(LogsWifi.class);
		}
		return logsWifiRuntimeDao;
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
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our OtherCellData class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<OtherCellData, Integer> getOtherCellDataDao() {
		if (otherCellDataRuntimeDao == null) {
			otherCellDataRuntimeDao = getRuntimeExceptionDao(OtherCellData.class);
		}
		return otherCellDataRuntimeDao;
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
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		mobilePrivacyProfilerDB_metadataRuntimeDao = null;
		applicationHistoryRuntimeDao = null;
		applicationUsageStatsRuntimeDao = null;
		authentificationRuntimeDao = null;
		contactRuntimeDao = null;
		contactOrganisationRuntimeDao = null;
		contactIMRuntimeDao = null;
		contactEventRuntimeDao = null;
		contactPhoneNumberRuntimeDao = null;
		contactPhysicalAddressRuntimeDao = null;
		contactEmailRuntimeDao = null;
		knownWifiRuntimeDao = null;
		logsWifiRuntimeDao = null;
		geolocationRuntimeDao = null;
		calendarEventRuntimeDao = null;
		phoneCallLogRuntimeDao = null;
		cellRuntimeDao = null;
		otherCellDataRuntimeDao = null;
		cdmaCellDataRuntimeDao = null;
		neighboringCellHistoryRuntimeDao = null;
		bluetoothDeviceRuntimeDao = null;
		bluetoothLogRuntimeDao = null;
		sMSRuntimeDao = null;
		batteryUsageRuntimeDao = null;
		webHistoryRuntimeDao = null;
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
			helper.authentificationDao = getDao(Authentification.class);
			helper.contactDao = getDao(Contact.class);
			helper.contactOrganisationDao = getDao(ContactOrganisation.class);
			helper.contactIMDao = getDao(ContactIM.class);
			helper.contactEventDao = getDao(ContactEvent.class);
			helper.contactPhoneNumberDao = getDao(ContactPhoneNumber.class);
			helper.contactPhysicalAddressDao = getDao(ContactPhysicalAddress.class);
			helper.contactEmailDao = getDao(ContactEmail.class);
			helper.knownWifiDao = getDao(KnownWifi.class);
			helper.logsWifiDao = getDao(LogsWifi.class);
			helper.geolocationDao = getDao(Geolocation.class);
			helper.calendarEventDao = getDao(CalendarEvent.class);
			helper.phoneCallLogDao = getDao(PhoneCallLog.class);
			helper.cellDao = getDao(Cell.class);
			helper.otherCellDataDao = getDao(OtherCellData.class);
			helper.cdmaCellDataDao = getDao(CdmaCellData.class);
			helper.neighboringCellHistoryDao = getDao(NeighboringCellHistory.class);
			helper.bluetoothDeviceDao = getDao(BluetoothDevice.class);
			helper.bluetoothLogDao = getDao(BluetoothLog.class);
			helper.sMSDao = getDao(SMS.class);
			helper.batteryUsageDao = getDao(BatteryUsage.class);
			helper.webHistoryDao = getDao(WebHistory.class);
		} catch (SQLException e) {
			Log.e(OrmLiteDBHelper.class.getName(), "Can't get ", e);
		}
		return helper;
	}

}
