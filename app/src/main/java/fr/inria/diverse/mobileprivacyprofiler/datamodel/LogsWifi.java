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

// Start of user code additional import for LogsWifi
// End of user code

/** 
  * log entry for a given detected wifi 
  */ 
@DatabaseTable(tableName = "logsWifi")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = LogsWifi.class)
public class LogsWifi {

	public static Log log = LogFactory.getLog(LogsWifi.class);

	public static final String XML_LOGSWIFI = "LOGSWIFI";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_TIMESTAMP = "timeStamp";
	public static final String XML_ATT_USERID = "userId";
	public static final String XML_REF_KNOWNWIFI = "knownWifi";
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
	public boolean knownWifi_mayNeedDBRefresh = true;
	

	/** approximate date where the WifiAccessPoint has been detected */ 
	@DatabaseField
	protected java.util.Date timeStamp;

	@DatabaseField
	protected java.lang.String userId;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="knownwifi_logswifi")
	protected KnownWifi knownWifi;

	// Start of user code LogsWifi additional user properties
	// End of user code
	
	public LogsWifi() {} // needed by ormlite
	public LogsWifi(java.util.Date timeStamp, java.lang.String userId) {
		super();
		this.timeStamp = timeStamp;
		this.userId = userId;
	} 

	public int getId() {
		return _id;
	}
	@JsonProperty
	public void setId(int id) {
		this._id = id;
	}

	public int get_Id() {
	return this._id;
	}

	public MobilePrivacyProfilerDBHelper getContextDB(){
		return _contextDB;
	}
	@JsonIgnore
	public void setContextDB(MobilePrivacyProfilerDBHelper contextDB){
		this._contextDB = contextDB;
	}

	public java.util.Date getTimeStamp() {
		return this.timeStamp;
	}
	@JsonProperty
	public void setTimeStamp(java.util.Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	@JsonProperty
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public KnownWifi getKnownWifi() {
		try {
			if(knownWifi_mayNeedDBRefresh && _contextDB != null){
				_contextDB.knownWifiDao.refresh(this.knownWifi);
				knownWifi_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.knownWifi == null){
			log.warn("LogsWifi may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.knownWifi;
	}
	@JsonProperty
	public void setKnownWifi(KnownWifi knownWifi) {
		this.knownWifi = knownWifi;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_LOGSWIFI);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_TIMESTAMP);
    	sb.append("=\"");
		sb.append(this.timeStamp);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_LOGSWIFI+">");
		return sb.toString();
	}
}
