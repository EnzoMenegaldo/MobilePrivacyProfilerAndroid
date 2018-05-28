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
	public static final String XML_REF_USERBLETOOTHDEVICE = "userBletoothDevice";
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
	public boolean userApplicationHistory_mayNeedDBRefresh = true;
	public boolean userAuthentification_mayNeedDBRefresh = true;
	public boolean userContact_mayNeedDBRefresh = true;
	public boolean userKnownWifi_mayNeedDBRefresh = true;
	public boolean userWebHistory_mayNeedDBRefresh = true;
	public boolean userBatteryUsage_mayNeedDBRefresh = true;
	public boolean userSMS_mayNeedDBRefresh = true;
	public boolean userBletoothDevice_mayNeedDBRefresh = true;
	public boolean userCell_mayNeedDBRefresh = true;
	public boolean userPhoneCallLog_mayNeedDBRefresh = true;
	public boolean userCalendarEvent_mayNeedDBRefresh = true;
	public boolean userGeolocation_mayNeedDBRefresh = true;
	

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
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected ApplicationHistory userApplicationHistory;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected Authentification userAuthentification;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected Contact userContact;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected KnownWifi userKnownWifi;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected WebHistory userWebHistory;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected BatteryUsage userBatteryUsage;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected SMS userSMS;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected BluetoothDevice userBletoothDevice;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected Cell userCell;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected PhoneCallLog userPhoneCallLog;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected CalendarEvent userCalendarEvent;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected Geolocation userGeolocation;

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

	public ApplicationHistory getUserApplicationHistory() {
		try {
			if(userApplicationHistory_mayNeedDBRefresh && _contextDB != null){
				_contextDB.applicationHistoryDao.refresh(this.userApplicationHistory);
				userApplicationHistory_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userApplicationHistory == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userApplicationHistory;
	}
	public void setUserApplicationHistory(ApplicationHistory userApplicationHistory) {
		this.userApplicationHistory = userApplicationHistory;
	}			
	public Authentification getUserAuthentification() {
		try {
			if(userAuthentification_mayNeedDBRefresh && _contextDB != null){
				_contextDB.authentificationDao.refresh(this.userAuthentification);
				userAuthentification_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userAuthentification == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userAuthentification;
	}
	public void setUserAuthentification(Authentification userAuthentification) {
		this.userAuthentification = userAuthentification;
	}			
	public Contact getUserContact() {
		try {
			if(userContact_mayNeedDBRefresh && _contextDB != null){
				_contextDB.contactDao.refresh(this.userContact);
				userContact_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userContact == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userContact;
	}
	public void setUserContact(Contact userContact) {
		this.userContact = userContact;
	}			
	public KnownWifi getUserKnownWifi() {
		try {
			if(userKnownWifi_mayNeedDBRefresh && _contextDB != null){
				_contextDB.knownWifiDao.refresh(this.userKnownWifi);
				userKnownWifi_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userKnownWifi == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userKnownWifi;
	}
	public void setUserKnownWifi(KnownWifi userKnownWifi) {
		this.userKnownWifi = userKnownWifi;
	}			
	public WebHistory getUserWebHistory() {
		try {
			if(userWebHistory_mayNeedDBRefresh && _contextDB != null){
				_contextDB.webHistoryDao.refresh(this.userWebHistory);
				userWebHistory_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userWebHistory == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userWebHistory;
	}
	public void setUserWebHistory(WebHistory userWebHistory) {
		this.userWebHistory = userWebHistory;
	}			
	public BatteryUsage getUserBatteryUsage() {
		try {
			if(userBatteryUsage_mayNeedDBRefresh && _contextDB != null){
				_contextDB.batteryUsageDao.refresh(this.userBatteryUsage);
				userBatteryUsage_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userBatteryUsage == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userBatteryUsage;
	}
	public void setUserBatteryUsage(BatteryUsage userBatteryUsage) {
		this.userBatteryUsage = userBatteryUsage;
	}			
	public SMS getUserSMS() {
		try {
			if(userSMS_mayNeedDBRefresh && _contextDB != null){
				_contextDB.sMSDao.refresh(this.userSMS);
				userSMS_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userSMS == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userSMS;
	}
	public void setUserSMS(SMS userSMS) {
		this.userSMS = userSMS;
	}			
	public BluetoothDevice getUserBletoothDevice() {
		try {
			if(userBletoothDevice_mayNeedDBRefresh && _contextDB != null){
				_contextDB.bluetoothDeviceDao.refresh(this.userBletoothDevice);
				userBletoothDevice_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userBletoothDevice == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userBletoothDevice;
	}
	public void setUserBletoothDevice(BluetoothDevice userBletoothDevice) {
		this.userBletoothDevice = userBletoothDevice;
	}			
	public Cell getUserCell() {
		try {
			if(userCell_mayNeedDBRefresh && _contextDB != null){
				_contextDB.cellDao.refresh(this.userCell);
				userCell_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userCell == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userCell;
	}
	public void setUserCell(Cell userCell) {
		this.userCell = userCell;
	}			
	public PhoneCallLog getUserPhoneCallLog() {
		try {
			if(userPhoneCallLog_mayNeedDBRefresh && _contextDB != null){
				_contextDB.phoneCallLogDao.refresh(this.userPhoneCallLog);
				userPhoneCallLog_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userPhoneCallLog == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userPhoneCallLog;
	}
	public void setUserPhoneCallLog(PhoneCallLog userPhoneCallLog) {
		this.userPhoneCallLog = userPhoneCallLog;
	}			
	public CalendarEvent getUserCalendarEvent() {
		try {
			if(userCalendarEvent_mayNeedDBRefresh && _contextDB != null){
				_contextDB.calendarEventDao.refresh(this.userCalendarEvent);
				userCalendarEvent_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userCalendarEvent == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userCalendarEvent;
	}
	public void setUserCalendarEvent(CalendarEvent userCalendarEvent) {
		this.userCalendarEvent = userCalendarEvent;
	}			
	public Geolocation getUserGeolocation() {
		try {
			if(userGeolocation_mayNeedDBRefresh && _contextDB != null){
				_contextDB.geolocationDao.refresh(this.userGeolocation);
				userGeolocation_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userGeolocation == null){
			log.warn("MobilePrivacyProfilerDB_metadata may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userGeolocation;
	}
	public void setUserGeolocation(Geolocation userGeolocation) {
		this.userGeolocation = userGeolocation;
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


		if(this.userApplicationHistory!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERAPPLICATIONHISTORY+">");
			sb.append(this.userApplicationHistory.getId());
	    	sb.append("</"+XML_REF_USERAPPLICATIONHISTORY+">");
		}
		if(this.userAuthentification!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERAUTHENTIFICATION+">");
			sb.append(this.userAuthentification.getId());
	    	sb.append("</"+XML_REF_USERAUTHENTIFICATION+">");
		}
		if(this.userContact!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERCONTACT+">");
			sb.append(this.userContact.getId());
	    	sb.append("</"+XML_REF_USERCONTACT+">");
		}
		if(this.userKnownWifi!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERKNOWNWIFI+">");
			sb.append(this.userKnownWifi.getId());
	    	sb.append("</"+XML_REF_USERKNOWNWIFI+">");
		}
		if(this.userWebHistory!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERWEBHISTORY+">");
			sb.append(this.userWebHistory.getId());
	    	sb.append("</"+XML_REF_USERWEBHISTORY+">");
		}
		if(this.userBatteryUsage!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERBATTERYUSAGE+">");
			sb.append(this.userBatteryUsage.getId());
	    	sb.append("</"+XML_REF_USERBATTERYUSAGE+">");
		}
		if(this.userSMS!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERSMS+">");
			sb.append(this.userSMS.getId());
	    	sb.append("</"+XML_REF_USERSMS+">");
		}
		if(this.userBletoothDevice!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERBLETOOTHDEVICE+">");
			sb.append(this.userBletoothDevice.getId());
	    	sb.append("</"+XML_REF_USERBLETOOTHDEVICE+">");
		}
		if(this.userCell!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERCELL+">");
			sb.append(this.userCell.getId());
	    	sb.append("</"+XML_REF_USERCELL+">");
		}
		if(this.userPhoneCallLog!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERPHONECALLLOG+">");
			sb.append(this.userPhoneCallLog.getId());
	    	sb.append("</"+XML_REF_USERPHONECALLLOG+">");
		}
		if(this.userCalendarEvent!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERCALENDAREVENT+">");
			sb.append(this.userCalendarEvent.getId());
	    	sb.append("</"+XML_REF_USERCALENDAREVENT+">");
		}
		if(this.userGeolocation!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERGEOLOCATION+">");
			sb.append(this.userGeolocation.getId());
	    	sb.append("</"+XML_REF_USERGEOLOCATION+">");
		}
		// TODO deal with other case

		sb.append("</"+XML_MOBILEPRIVACYPROFILERDB_METADATA+">");
		return sb.toString();
	}
}
