/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;


/**
 * Context class used to simplify the access to the different DAOs of the application
 */
public class MobilePrivacyProfilerDBHelper {


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

	

}
