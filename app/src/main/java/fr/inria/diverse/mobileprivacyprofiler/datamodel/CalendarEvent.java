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

// Start of user code additional import for CalendarEvent
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "calendarEvent")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = CalendarEvent.class)
public class CalendarEvent {

	public static Log log = LogFactory.getLog(CalendarEvent.class);

	public static final String XML_CALENDAREVENT = "CALENDAREVENT";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_EVENTLABEL = "eventLabel";
	public static final String XML_ATT_STARTDATE = "startDate";
	public static final String XML_ATT_ENDDATE = "endDate";
	public static final String XML_ATT_PLACE = "place";
	public static final String XML_ATT_PARTICIPANTS = "participants";
	public static final String XML_ATT_EVENTID = "eventId";
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
	protected java.lang.String eventLabel;

	@DatabaseField
	protected java.util.Date startDate;

	@DatabaseField
	protected java.util.Date endDate;

	/** place where the event is supposed to occur */ 
	@DatabaseField
	protected java.lang.String place;

	/** list of participants (raw string for the moment) */ 
	@DatabaseField(dataType = com.j256.ormlite.field.DataType.LONG_STRING)
	protected java.lang.String participants;

	@DatabaseField
	protected long eventId;

	@DatabaseField
	protected java.lang.String userId;
	

	// Start of user code CalendarEvent additional user properties
	// End of user code
	
	public CalendarEvent() {} // needed by ormlite
	public CalendarEvent(java.lang.String eventLabel, java.util.Date startDate, java.util.Date endDate, java.lang.String place, java.lang.String participants, long eventId, java.lang.String userId) {
		super();
		this.eventLabel = eventLabel;
		this.startDate = startDate;
		this.endDate = endDate;
		this.place = place;
		this.participants = participants;
		this.eventId = eventId;
		this.userId = userId;
	} 

	public int get_id() {
		return _id;
	}
	@JsonProperty
	public void set_id(int id) {
		this._id = id;
	}

	public MobilePrivacyProfilerDBHelper getContextDB(){
		return _contextDB;
	}
	@JsonIgnore
	public void setContextDB(MobilePrivacyProfilerDBHelper contextDB){
		this._contextDB = contextDB;
	}

	public java.lang.String getEventLabel() {
		return this.eventLabel;
	}
	@JsonProperty
	public void setEventLabel(java.lang.String eventLabel) {
		this.eventLabel = eventLabel;
	}
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	@JsonProperty
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	@JsonProperty
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}
	public java.lang.String getPlace() {
		return this.place;
	}
	@JsonProperty
	public void setPlace(java.lang.String place) {
		this.place = place;
	}
	public java.lang.String getParticipants() {
		return this.participants;
	}
	@JsonProperty
	public void setParticipants(java.lang.String participants) {
		this.participants = participants;
	}
	public long getEventId() {
		return this.eventId;
	}
	@JsonProperty
	public void setEventId(long eventId) {
		this.eventId = eventId;
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
    	sb.append(XML_CALENDAREVENT);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_EVENTLABEL);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.eventLabel));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_STARTDATE);
    	sb.append("=\"");
		sb.append(this.startDate);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_ENDDATE);
    	sb.append("=\"");
		sb.append(this.endDate);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_PLACE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.place));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_PARTICIPANTS);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.participants));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_EVENTID);
    	sb.append("=\"");
		sb.append(this.eventId);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_CALENDAREVENT+">");
		return sb.toString();
	}
}
