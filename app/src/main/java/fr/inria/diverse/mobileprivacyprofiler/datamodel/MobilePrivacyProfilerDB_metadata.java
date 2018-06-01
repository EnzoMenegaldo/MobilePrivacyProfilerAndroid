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
// Start of user code additional import for MobilePrivacyProfilerDB_metadata
// End of user code

/** 
  * données complémentaires sur la base de donnée 
  */ 
@DatabaseTable(tableName = "mobilePrivacyProfilerDB_metadata")
public class MobilePrivacyProfilerDB_metadata {

	public static Log log = LogFactory.getLog(MobilePrivacyProfilerDB_metadata.class);

	public static final String XML_MOBILEPRIVACYPROFILERDB_METADATA = "MOBILEPRIVACYPROFILERDB_METADATA";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_LASTTRANSMISSIONDATE = "lastTransmissionDate";
	public static final String XML_ATT_LASTSCANINSTALLEDAPPLICATIONS = "lastScanInstalledApplications";
	public static final String XML_ATT_LASTSCANAPPUSAGE = "lastScanAppUsage";
	public static final String XML_ATT_LASTSMSSCAN = "lastSmsScan";
	public static final String XML_ATT_LASTCALLSCAN = "lastCallScan";
	public static final String XML_ATT_USERID = "userId";
	public static final String XML_ATT_LASTCONTACTSCAN = "lastContactScan";
	public static final String XML_REF_USERAPPLICATIONHISTORY = "userApplicationHistory";
	public static final String XML_REF_USERAUTHENTIFICATION = "userAuthentification";
	public static final String XML_REF_USERCONTACT = "userContact";
	public static final String XML_REF_USERKNOWNWIFI = "userKnownWifi";
	public static final String XML_REF_USERWEBHISTORY = "userWebHistory";
	public static final String XML_REF_USERBATTERYUSAGE = "userBatteryUsage";
	public static final String XML_REF_USERSMS = "userSMS";
	public static final String XML_REF_USERBLUETOOTHDEVICE = "userBluetoothDevice";
	public static final String XML_REF_USERCELL = "userCell";
	public static final String XML_REF_USERPHONECALLLOG = "userPhoneCallLog";
	public static final String XML_REF_USERCALENDAREVENT = "userCalendarEvent";
	public static final String XML_REF_USERGEOLOCATION = "userGeolocation";
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
	

	@DatabaseField
	protected java.util.Date lastTransmissionDate;

	@DatabaseField
	protected java.util.Date lastScanInstalledApplications;

	@DatabaseField
	protected java.util.Date lastScanAppUsage;

	@DatabaseField
	protected java.util.Date lastSmsScan;

	@DatabaseField
	protected java.util.Date lastCallScan;

	@DatabaseField
	protected java.lang.String userId;

	@DatabaseField
	protected java.util.Date lastContactScan;
	

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<ApplicationHistory> userApplicationHistory;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<Authentification> userAuthentification;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<Contact> userContact;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<KnownWifi> userKnownWifi;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<WebHistory> userWebHistory;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<BatteryUsage> userBatteryUsage;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<SMS> userSMS;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<BluetoothDevice> userBluetoothDevice;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<Cell> userCell;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<PhoneCallLog> userPhoneCallLog;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<CalendarEvent> userCalendarEvent;

	@ForeignCollectionField(eager = false, foreignFieldName = "userMetaData")
	protected ForeignCollection<Geolocation> userGeolocation;

	// Start of user code MobilePrivacyProfilerDB_metadata additional user properties
	// End of user code
	
	public MobilePrivacyProfilerDB_metadata() {} // needed by ormlite
	public MobilePrivacyProfilerDB_metadata(java.util.Date lastTransmissionDate, java.util.Date lastScanInstalledApplications, java.util.Date lastScanAppUsage, java.util.Date lastSmsScan, java.util.Date lastCallScan, java.lang.String userId, java.util.Date lastContactScan) {
		super();
		this.lastTransmissionDate = lastTransmissionDate;
		this.lastScanInstalledApplications = lastScanInstalledApplications;
		this.lastScanAppUsage = lastScanAppUsage;
		this.lastSmsScan = lastSmsScan;
		this.lastCallScan = lastCallScan;
		this.userId = userId;
		this.lastContactScan = lastContactScan;
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

	public java.util.Date getLastTransmissionDate() {
		return this.lastTransmissionDate;
	}
	public void setLastTransmissionDate(java.util.Date lastTransmissionDate) {
		this.lastTransmissionDate = lastTransmissionDate;
	}
	public java.util.Date getLastScanInstalledApplications() {
		return this.lastScanInstalledApplications;
	}
	public void setLastScanInstalledApplications(java.util.Date lastScanInstalledApplications) {
		this.lastScanInstalledApplications = lastScanInstalledApplications;
	}
	public java.util.Date getLastScanAppUsage() {
		return this.lastScanAppUsage;
	}
	public void setLastScanAppUsage(java.util.Date lastScanAppUsage) {
		this.lastScanAppUsage = lastScanAppUsage;
	}
	public java.util.Date getLastSmsScan() {
		return this.lastSmsScan;
	}
	public void setLastSmsScan(java.util.Date lastSmsScan) {
		this.lastSmsScan = lastSmsScan;
	}
	public java.util.Date getLastCallScan() {
		return this.lastCallScan;
	}
	public void setLastCallScan(java.util.Date lastCallScan) {
		this.lastCallScan = lastCallScan;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	public java.util.Date getLastContactScan() {
		return this.lastContactScan;
	}
	public void setLastContactScan(java.util.Date lastContactScan) {
		this.lastContactScan = lastContactScan;
	}

	public Collection<ApplicationHistory> getUserApplicationHistory() {
		return this.userApplicationHistory;
	}					
	public Collection<Authentification> getUserAuthentification() {
		return this.userAuthentification;
	}					
	public Collection<Contact> getUserContact() {
		return this.userContact;
	}					
	public Collection<KnownWifi> getUserKnownWifi() {
		return this.userKnownWifi;
	}					
	public Collection<WebHistory> getUserWebHistory() {
		return this.userWebHistory;
	}					
	public Collection<BatteryUsage> getUserBatteryUsage() {
		return this.userBatteryUsage;
	}					
	public Collection<SMS> getUserSMS() {
		return this.userSMS;
	}					
	public Collection<BluetoothDevice> getUserBluetoothDevice() {
		return this.userBluetoothDevice;
	}					
	public Collection<Cell> getUserCell() {
		return this.userCell;
	}					
	public Collection<PhoneCallLog> getUserPhoneCallLog() {
		return this.userPhoneCallLog;
	}					
	public Collection<CalendarEvent> getUserCalendarEvent() {
		return this.userCalendarEvent;
	}					
	public Collection<Geolocation> getUserGeolocation() {
		return this.userGeolocation;
	}					



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_MOBILEPRIVACYPROFILERDB_METADATA);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LASTTRANSMISSIONDATE);
    	sb.append("=\"");
		sb.append(this.lastTransmissionDate);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LASTSCANINSTALLEDAPPLICATIONS);
    	sb.append("=\"");
		sb.append(this.lastScanInstalledApplications);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LASTSCANAPPUSAGE);
    	sb.append("=\"");
		sb.append(this.lastScanAppUsage);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LASTSMSSCAN);
    	sb.append("=\"");
		sb.append(this.lastSmsScan);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LASTCALLSCAN);
    	sb.append("=\"");
		sb.append(this.lastCallScan);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LASTCONTACTSCAN);
    	sb.append("=\"");
		sb.append(this.lastContactScan);
    	sb.append("\" ");
    	sb.append(">");


		if(this.userApplicationHistory != null){
			for(ApplicationHistory ref : this.userApplicationHistory){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERAPPLICATIONHISTORY+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userAuthentification != null){
			for(Authentification ref : this.userAuthentification){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERAUTHENTIFICATION+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userContact != null){
			for(Contact ref : this.userContact){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERCONTACT+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userKnownWifi != null){
			for(KnownWifi ref : this.userKnownWifi){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERKNOWNWIFI+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userWebHistory != null){
			for(WebHistory ref : this.userWebHistory){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERWEBHISTORY+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userBatteryUsage != null){
			for(BatteryUsage ref : this.userBatteryUsage){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERBATTERYUSAGE+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userSMS != null){
			for(SMS ref : this.userSMS){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERSMS+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userBluetoothDevice != null){
			for(BluetoothDevice ref : this.userBluetoothDevice){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERBLUETOOTHDEVICE+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userCell != null){
			for(Cell ref : this.userCell){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERCELL+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userPhoneCallLog != null){
			for(PhoneCallLog ref : this.userPhoneCallLog){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERPHONECALLLOG+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userCalendarEvent != null){
			for(CalendarEvent ref : this.userCalendarEvent){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERCALENDAREVENT+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userGeolocation != null){
			for(Geolocation ref : this.userGeolocation){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_USERGEOLOCATION+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		// TODO deal with other case

		sb.append("</"+XML_MOBILEPRIVACYPROFILERDB_METADATA+">");
		return sb.toString();
	}
}
