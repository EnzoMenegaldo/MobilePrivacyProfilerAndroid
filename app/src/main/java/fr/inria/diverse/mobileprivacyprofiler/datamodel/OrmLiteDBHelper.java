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


	// the DAO object we use to access the diveBudies table
	// private Dao<ApplicationHistory, Integer> applicationHistoryDao = null;
	private RuntimeExceptionDao<ApplicationHistory, Integer> applicationHistoryRuntimeDao = null;
	// the DAO object we use to access the diveBudies table
	// private Dao<ApplicationLogEntry, Integer> applicationLogEntryDao = null;
	private RuntimeExceptionDao<ApplicationLogEntry, Integer> applicationLogEntryRuntimeDao = null;
	// the DAO object we use to access the diveBudies table
	// private Dao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao = null;
	private RuntimeExceptionDao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataRuntimeDao = null;
	

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
			TableUtils.createTable(connectionSource, ApplicationHistory.class);
			TableUtils.createTable(connectionSource, ApplicationLogEntry.class);
			TableUtils.createTable(connectionSource, MobilePrivacyProfilerDB_metadata.class);
		} catch (SQLException e) {
			Log.e(OrmLiteDBHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

		// here we try inserting data in the on-create as a test
		/*RuntimeExceptionDao<DiveEntry, Integer> dao = getSimpleDataDao();
		long millis = System.currentTimeMillis();
		// create some entries in the onCreate
		DiveEntry simple = new DiveEntry(millis);
		dao.create(simple);
		simple = new DiveEntry(millis + 1);
		dao.create(simple);
		Log.i(ORMLiteDBHelper.class.getName(), "created new entries in onCreate: " + millis);
		*/
	// End of user code
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
			TableUtils.dropTable(connectionSource, ApplicationHistory.class, true);
			TableUtils.dropTable(connectionSource, ApplicationLogEntry.class, true);
			TableUtils.dropTable(connectionSource, MobilePrivacyProfilerDB_metadata.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(OrmLiteDBHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	// End of user code
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
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ApplicationLogEntry class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ApplicationLogEntry, Integer> getApplicationLogEntryDao() {
		if (applicationLogEntryRuntimeDao == null) {
			applicationLogEntryRuntimeDao = getRuntimeExceptionDao(ApplicationLogEntry.class);
		}
		return applicationLogEntryRuntimeDao;
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
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		applicationHistoryRuntimeDao = null;
		applicationLogEntryRuntimeDao = null;
		mobilePrivacyProfilerDB_metadataRuntimeDao = null;
	}

	
	/**
     *
     */
	public MobilePrivacyProfilerDBHelper getMobilePrivacyProfilerDBHelper(){
		MobilePrivacyProfilerDBHelper helper = new MobilePrivacyProfilerDBHelper();
		try{
			helper.applicationHistoryDao = getDao(ApplicationHistory.class);
			helper.applicationLogEntryDao = getDao(ApplicationLogEntry.class);
			helper.mobilePrivacyProfilerDB_metadataDao = getDao(MobilePrivacyProfilerDB_metadata.class);
		} catch (SQLException e) {
			Log.e(OrmLiteDBHelper.class.getName(), "Can't get ", e);
		}
		return helper;
	}

}
