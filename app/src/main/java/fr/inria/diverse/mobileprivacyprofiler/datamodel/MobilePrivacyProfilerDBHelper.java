/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;


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
	public Dao<ApplicationLogEntry, Integer> applicationLogEntryDao;
	//public RuntimeExceptionDao<ApplicationLogEntry, Integer> applicationLogEntryDao;
	public Dao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao;
	//public RuntimeExceptionDao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao;

	
	public MobilePrivacyProfilerDBHelper(){
	}

	public MobilePrivacyProfilerDBHelper(
		Dao<ApplicationHistory, Integer> applicationHistoryDao,
		Dao<ApplicationLogEntry, Integer> applicationLogEntryDao,
		Dao<MobilePrivacyProfilerDB_metadata, Integer> mobilePrivacyProfilerDB_metadataDao        
	){
		this.applicationHistoryDao = applicationHistoryDao;
		this.applicationLogEntryDao = applicationLogEntryDao;
		this.mobilePrivacyProfilerDB_metadataDao = mobilePrivacyProfilerDB_metadataDao;
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
