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
// Start of user code additional import for PhoneCallLog
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "phoneCallLog")
public class PhoneCallLog {

	public static Log log = LogFactory.getLog(PhoneCallLog.class);

	public static final String XML_PHONECALLLOG = "PHONECALLLOG";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_PHONENUMBER = "phoneNumber";
	public static final String XML_ATT_DATE = "date";
	public static final String XML_ATT_DURATION = "duration";
	public static final String XML_ATT_CALLTYPE = "callType";
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
	

	/** phone number or unknown */ 
	@DatabaseField
	protected java.lang.String phoneNumber;

	@DatabaseField
	protected java.lang.String date;

	/** duration of the call in ms */ 
	@DatabaseField
	protected int duration;

	/** missed, rejected, outgoing, incoming, blocked, voicemail */ 
	@DatabaseField
	protected java.lang.String callType;
	

	// Start of user code PhoneCallLog additional user properties
	// End of user code
	
	public PhoneCallLog() {} // needed by ormlite
	public PhoneCallLog(java.lang.String phoneNumber, java.lang.String date, int duration, java.lang.String callType) {
		super();
		this.phoneNumber = phoneNumber;
		this.date = date;
		this.duration = duration;
		this.callType = callType;
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

	public java.lang.String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(java.lang.String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public java.lang.String getDate() {
		return this.date;
	}
	public void setDate(java.lang.String date) {
		this.date = date;
	}
	public int getDuration() {
		return this.duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public java.lang.String getCallType() {
		return this.callType;
	}
	public void setCallType(java.lang.String callType) {
		this.callType = callType;
	}




	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_PHONECALLLOG);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_PHONENUMBER);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.phoneNumber));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DATE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.date));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DURATION);
    	sb.append("=\"");
		sb.append(this.duration);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_CALLTYPE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.callType));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_PHONECALLLOG+">");
		return sb.toString();
	}
}