/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.DetectedWifi_AccessPoint;
// Start of user code additional import for ApplicationUsageStats
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "applicationUsageStats")
public class ApplicationUsageStats {

	public static Log log = LogFactory.getLog(ApplicationUsageStats.class);

	public static final String XML_APPLICATIONUSAGESTATS = "APPLICATIONUSAGESTATS";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_TOTALTIMEINFOREGROUND = "totalTimeInForeground";
	public static final String XML_ATT_LASTTIMEUSED = "lastTimeUsed";
	public static final String XML_ATT_FIRSTTIMESTAMP = "firstTimeStamp";
	public static final String XML_ATT_LASTTIMESTAMP = "lastTimeStamp";
	public static final String XML_REF_APPLICATION = "application";
	// id is generated by the database and set on the object automagically
	@DatabaseField(generatedId = true)
	protected int _id;

	/**
     * dbHelper used to autorefresh values and doing queries
     * must be set other wise most getter will return proxy that will need to be refreshed
	 */
	protected MobilePrivacyProfilerDBHelper _contextDB = null;

	/**
	 * object created from DB may need to be updated from the DB for being fully navigable
	 */
	public boolean application_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected java.lang.String totalTimeInForeground;

	@DatabaseField
	protected java.lang.String lastTimeUsed;

	/** Get the beginning of the time range this UsageStats represents, measured in milliseconds since the epoch. */ 
	@DatabaseField
	protected java.lang.String firstTimeStamp;

	/** Get the end of the time range this UsageStats represents, measured in milliseconds since the epoch. */ 
	@DatabaseField
	protected java.lang.String lastTimeStamp;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected ApplicationHistory application;

	// Start of user code ApplicationUsageStats additional user properties
	// End of user code
	
	public ApplicationUsageStats() {} // needed by ormlite
	public ApplicationUsageStats(java.lang.String totalTimeInForeground, java.lang.String lastTimeUsed, java.lang.String firstTimeStamp, java.lang.String lastTimeStamp) {
		super();
		this.totalTimeInForeground = totalTimeInForeground;
		this.lastTimeUsed = lastTimeUsed;
		this.firstTimeStamp = firstTimeStamp;
		this.lastTimeStamp = lastTimeStamp;
	} 

	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}

	public MobilePrivacyProfilerDBHelper getContextDB(){
		return _contextDB;
	}
	public void setContextDB(MobilePrivacyProfilerDBHelper contextDB){
		this._contextDB = contextDB;
	}

	public java.lang.String getTotalTimeInForeground() {
		return this.totalTimeInForeground;
	}
	public void setTotalTimeInForeground(java.lang.String totalTimeInForeground) {
		this.totalTimeInForeground = totalTimeInForeground;
	}
	public java.lang.String getLastTimeUsed() {
		return this.lastTimeUsed;
	}
	public void setLastTimeUsed(java.lang.String lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}
	public java.lang.String getFirstTimeStamp() {
		return this.firstTimeStamp;
	}
	public void setFirstTimeStamp(java.lang.String firstTimeStamp) {
		this.firstTimeStamp = firstTimeStamp;
	}
	public java.lang.String getLastTimeStamp() {
		return this.lastTimeStamp;
	}
	public void setLastTimeStamp(java.lang.String lastTimeStamp) {
		this.lastTimeStamp = lastTimeStamp;
	}

	public ApplicationHistory getApplication() {
		try {
			if(application_mayNeedDBRefresh && _contextDB != null){
				_contextDB.applicationHistoryDao.refresh(this.application);
				application_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.application == null){
			log.warn("ApplicationUsageStats may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.application;
	}
	public void setApplication(ApplicationHistory application) {
		this.application = application;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_APPLICATIONUSAGESTATS);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_TOTALTIMEINFOREGROUND);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.totalTimeInForeground));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LASTTIMEUSED);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.lastTimeUsed));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_FIRSTTIMESTAMP);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.firstTimeStamp));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LASTTIMESTAMP);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.lastTimeStamp));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_APPLICATIONUSAGESTATS+">");
		return sb.toString();
	}
}
