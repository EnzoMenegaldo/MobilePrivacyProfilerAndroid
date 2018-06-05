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

// Start of user code additional import for BatteryUsage
// End of user code

/** 
  * https://developer.android.com/training/monitoring-device-state/battery-monitoring.html 
  */ 
@DatabaseTable(tableName = "batteryUsage")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = BatteryUsage.class)
public class BatteryUsage {

	public static Log log = LogFactory.getLog(BatteryUsage.class);

	public static final String XML_BATTERYUSAGE = "BATTERYUSAGE";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_DATE = "date";
	public static final String XML_ATT_LEVEL = "level";
	public static final String XML_ATT_ISPUGGED = "isPugged";
	public static final String XML_ATT_PLUGTYPE = "plugType";
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
	protected java.util.Date date;

	@DatabaseField
	protected int level;

	@DatabaseField
	protected int isPugged;

	/** Possible type :
"BATTERY_PLUGGED_USB",
"BATTERY_PLUGGED_AC",
"BATTERY_PLUGGED_WIRELESS",
 "BATTERY_NOT_PLUGGED" */ 
	@DatabaseField
	protected java.lang.String plugType;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="batteryusage_mobileprivacyprofilerdb_metadata")
	protected MobilePrivacyProfilerDB_metadata userMetaData;

	// Start of user code BatteryUsage additional user properties

	// End of user code
	
	public BatteryUsage() {} // needed by ormlite
	public BatteryUsage(java.util.Date date, int level, int isPugged, java.lang.String plugType) {
		super();
		this.date = date;
		this.level = level;
		this.isPugged = isPugged;
		this.plugType = plugType;
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

	public java.util.Date getDate() {
		return this.date;
	}
	@JsonProperty
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public int getLevel() {
		return this.level;
	}
	@JsonProperty
	public void setLevel(int level) {
		this.level = level;
	}
	public int getIsPugged() {
		return this.isPugged;
	}
	@JsonProperty
	public void setIsPugged(int isPugged) {
		this.isPugged = isPugged;
	}
	public java.lang.String getPlugType() {
		return this.plugType;
	}
	@JsonProperty
	public void setPlugType(java.lang.String plugType) {
		this.plugType = plugType;
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
			log.warn("BatteryUsage may not be properly refreshed from DB (_id="+_id+")");
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
    	sb.append(XML_BATTERYUSAGE);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DATE);
    	sb.append("=\"");
		sb.append(this.date);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LEVEL);
    	sb.append("=\"");
		sb.append(this.level);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_ISPUGGED);
    	sb.append("=\"");
		sb.append(this.isPugged);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_PLUGTYPE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.plugType));
    	sb.append("\" ");
    	sb.append(">");


		if(this.userMetaData!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERMETADATA+">");
			sb.append(this.userMetaData.getId());
	    	sb.append("</"+XML_REF_USERMETADATA+">");
		}
		// TODO deal with other case

		sb.append("</"+XML_BATTERYUSAGE+">");
		return sb.toString();
	}
}
