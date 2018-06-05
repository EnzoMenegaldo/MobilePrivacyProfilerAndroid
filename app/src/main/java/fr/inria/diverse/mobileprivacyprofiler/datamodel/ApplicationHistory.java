/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Start of user code additional import for ApplicationHistory
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "applicationHistory")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = ApplicationHistory.class)
public class ApplicationHistory {

	public static Log log = LogFactory.getLog(ApplicationHistory.class);

	public static final String XML_APPLICATIONHISTORY = "APPLICATIONHISTORY";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_APPNAME = "appName";
	public static final String XML_ATT_PACKAGENAME = "packageName";
	public static final String XML_REF_USAGESTATS = "usageStats";
	public static final String XML_REF_USERMETADATA = "userMetaData";
	// id is generated by the database and set on the object automagically
	@DatabaseField(generatedId = true)
	protected int _id;

	/**
     * dbHelper used to autorefresh values and doing queries
     * must be set other wise most getter will return proxy that will need to be refreshed
	 */
	@JsonIgnore
	protected MobilePrivacyProfilerDBHelper _contextDB = null;

	/**
	 * object created from DB may need to be updated from the DB for being fully navigable
	 */
	@JsonIgnore
	public boolean userMetaData_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected java.lang.String appName;

	@DatabaseField
	protected java.lang.String packageName;
	

	@ForeignCollectionField(eager = false, foreignFieldName = "application")
	@JsonIgnore
	protected ForeignCollection<ApplicationUsageStats> usageStats;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="applicationhistory_mobileprivacyprofilerdb_metadata")
	protected MobilePrivacyProfilerDB_metadata userMetaData;

	// Start of user code ApplicationHistory additional user properties
	// End of user code
	
	public ApplicationHistory() {} // needed by ormlite
	public ApplicationHistory(java.lang.String appName, java.lang.String packageName) {
		super();
		this.appName = appName;
		this.packageName = packageName;
	} 

	public int getId() {
		return _id;
	}
	@JsonProperty
	public void setId(int id) {
		this._id = id;
	}

	public MobilePrivacyProfilerDBHelper getContextDB(){
		return _contextDB;
	}
	@JsonIgnore
	public void setContextDB(MobilePrivacyProfilerDBHelper contextDB){
		this._contextDB = contextDB;
	}

	public java.lang.String getAppName() {
		return this.appName;
	}
	@JsonProperty
	public void setAppName(java.lang.String appName) {
		this.appName = appName;
	}
	public java.lang.String getPackageName() {
		return this.packageName;
	}
	@JsonProperty
	public void setPackageName(java.lang.String packageName) {
		this.packageName = packageName;
	}

	public List	<ApplicationUsageStats> getUsageStats() {
		if(null==this.usageStats){return null;}
		return new ArrayList<ApplicationUsageStats>(usageStats);
	}
	

			
	public MobilePrivacyProfilerDB_metadata getUserMetaData() {
		try {
			if(userMetaData_mayNeedDBRefresh && _contextDB != null){
				_contextDB.mobilePrivacyProfilerDB_metadataDao.refresh(this.userMetaData);
				userMetaData_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userMetaData == null){
			log.warn("ApplicationHistory may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userMetaData;
	}
	@JsonProperty
	public void setUserMetaData(MobilePrivacyProfilerDB_metadata userMetaData) {
		this.userMetaData = userMetaData;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_APPLICATIONHISTORY);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_APPNAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.appName));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_PACKAGENAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.packageName));
    	sb.append("\" ");
    	sb.append(">");


		sb.append("\n"+indent+"\t<"+XML_REF_USAGESTATS+">");
		if(this.usageStats != null){
			for(ApplicationUsageStats ref : this.usageStats){
				sb.append("\n"+ref.toXML(indent+"\t\t", contextDB));
	    	}
		}
		sb.append("</"+XML_REF_USAGESTATS+">");		
		if(this.userMetaData!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERMETADATA+">");
			sb.append(this.userMetaData.getId());
	    	sb.append("</"+XML_REF_USERMETADATA+">");
		}
		// TODO deal with other case

		sb.append("</"+XML_APPLICATIONHISTORY+">");
		return sb.toString();
	}
}
