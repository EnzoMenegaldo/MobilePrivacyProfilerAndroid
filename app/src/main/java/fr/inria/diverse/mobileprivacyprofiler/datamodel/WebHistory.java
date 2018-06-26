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

// Start of user code additional import for WebHistory
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "webHistory")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = WebHistory.class)
public class WebHistory {

	public static Log log = LogFactory.getLog(WebHistory.class);

	public static final String XML_WEBHISTORY = "WEBHISTORY";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_DATE = "date";
	public static final String XML_ATT_APPLICATION = "application";
	public static final String XML_ATT_HOSTNAME = "hostname";
	public static final String XML_ATT_IPDESTINATION = "ipDestination";
	public static final String XML_ATT_USERID = "userId";
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
	

	@DatabaseField
	protected java.util.Date date;

	/** application that requested the url */ 
	@DatabaseField
	protected java.lang.String application;

	@DatabaseField
	protected java.lang.String hostname;

	@DatabaseField
	protected java.lang.String ipDestination;

	@DatabaseField
	protected java.lang.String userId;
	

	// Start of user code WebHistory additional user properties
	// End of user code
	
	public WebHistory() {} // needed by ormlite
	public WebHistory(java.util.Date date, java.lang.String application, java.lang.String hostname, java.lang.String ipDestination, java.lang.String userId) {
		super();
		this.date = date;
		this.application = application;
		this.hostname = hostname;
		this.ipDestination = ipDestination;
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

	public java.util.Date getDate() {
		return this.date;
	}
	@JsonProperty
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public java.lang.String getApplication() {
		return this.application;
	}
	@JsonProperty
	public void setApplication(java.lang.String application) {
		this.application = application;
	}
	public java.lang.String getHostname() {
		return this.hostname;
	}
	@JsonProperty
	public void setHostname(java.lang.String hostname) {
		this.hostname = hostname;
	}
	public java.lang.String getIpDestination() {
		return this.ipDestination;
	}
	@JsonProperty
	public void setIpDestination(java.lang.String ipDestination) {
		this.ipDestination = ipDestination;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	@JsonProperty
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}




	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_WEBHISTORY);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DATE);
    	sb.append("=\"");
		sb.append(this.date);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_APPLICATION);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.application));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_HOSTNAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.hostname));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_IPDESTINATION);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.ipDestination));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_WEBHISTORY+">");
		return sb.toString();
	}
}
