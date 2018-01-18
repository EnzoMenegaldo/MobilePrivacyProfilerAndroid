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
// Start of user code additional import for DetectedWifi
// End of user code

/** 
  * log entry for a given detected wifi 
  */ 
@DatabaseTable(tableName = "detectedWifi")
public class DetectedWifi {

	public static Log log = LogFactory.getLog(DetectedWifi.class);

	public static final String XML_DETECTEDWIFI = "DETECTEDWIFI";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_STARTDETECTIONDATE = "startDetectionDate";
	public static final String XML_ATT_ENDDETECTIONDATE = "endDetectionDate";
	public static final String XML_ATT_HASCONNECTED = "hasConnected";
	public static final String XML_ATT_SSID = "ssid";
	public static final String XML_REF_ACCESSPOINTS = "accessPoints";
	public static final String XML_REF_KNOWNWIFI = "knownWifi";
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
	public boolean knownWifi_mayNeedDBRefresh = true;
	

	/** approximate date where the WifiAccessPoint has been detected */ 
	@DatabaseField
	protected java.lang.String startDetectionDate;

	/** if reached, 
approximate time where the WifiAccessPoint was not received anymore */ 
	@DatabaseField
	protected java.lang.String endDetectionDate;

	/** indicates wether the device succefuly connect to the wifi during this session */ 
	@DatabaseField
	protected int hasConnected;

	/** SSID detected during the session
(note: should we need to check what happen if several network conflicts by using the same SSID / man in the middle attack ) */ 
	@DatabaseField
	protected java.lang.String ssid;
	

	/** access points detected during the session */ 
	// work in progress, find association 
	// Association many to many DetectedWifi_AccessPoint
	@ForeignCollectionField(eager = false, foreignFieldName = "detectedWifi")	
	protected ForeignCollection<DetectedWifi_AccessPoint> detectedWifi_AccessPoint;

	/** access points detected during the session 
	  * Attention, returned list is readonly
      */
	public List<WifiAccessPoint> getAccessPoints(){
		List<WifiAccessPoint> result = new ArrayList<WifiAccessPoint>();
		
		for (DetectedWifi_AccessPoint aDetectedWifi_AccessPoint : detectedWifi_AccessPoint) {
			if(_contextDB != null) aDetectedWifi_AccessPoint.setContextDB(_contextDB);
			result.add(aDetectedWifi_AccessPoint.getWifiAccessPoint());
		}
		return result;
	}
	public void addWifiAccessPoint(WifiAccessPoint wifiAccessPoint){
		try {
			_contextDB.detectedWifi_AccessPointDao.create(new DetectedWifi_AccessPoint( this, wifiAccessPoint));
		} catch (SQLException e) {
			log.error("Pb while adding association detectedWifi_AccessPoint",e);
		}
	}
	// end work in progress 	

				

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected KnownWifi knownWifi;

	// Start of user code DetectedWifi additional user properties
	// End of user code
	
	public DetectedWifi() {} // needed by ormlite
	public DetectedWifi(java.lang.String startDetectionDate, java.lang.String endDetectionDate, int hasConnected, java.lang.String ssid) {
		super();
		this.startDetectionDate = startDetectionDate;
		this.endDetectionDate = endDetectionDate;
		this.hasConnected = hasConnected;
		this.ssid = ssid;
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

	public java.lang.String getStartDetectionDate() {
		return this.startDetectionDate;
	}
	public void setStartDetectionDate(java.lang.String startDetectionDate) {
		this.startDetectionDate = startDetectionDate;
	}
	public java.lang.String getEndDetectionDate() {
		return this.endDetectionDate;
	}
	public void setEndDetectionDate(java.lang.String endDetectionDate) {
		this.endDetectionDate = endDetectionDate;
	}
	public int getHasConnected() {
		return this.hasConnected;
	}
	public void setHasConnected(int hasConnected) {
		this.hasConnected = hasConnected;
	}
	public java.lang.String getSsid() {
		return this.ssid;
	}
	public void setSsid(java.lang.String ssid) {
		this.ssid = ssid;
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
			log.warn("DetectedWifi may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.knownWifi;
	}
	public void setKnownWifi(KnownWifi knownWifi) {
		this.knownWifi = knownWifi;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_DETECTEDWIFI);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_STARTDETECTIONDATE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.startDetectionDate));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_ENDDETECTIONDATE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.endDetectionDate));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_HASCONNECTED);
    	sb.append("=\"");
		sb.append(this.hasConnected);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_SSID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.ssid));
    	sb.append("\" ");
    	sb.append(">");


		
		for(WifiAccessPoint ref : this.getAccessPoints()){
    		sb.append("\n"+indent+"\t<"+XML_REF_ACCESSPOINTS+" id=\"");
    		sb.append(ref._id);
        	sb.append("\"/>");
			
    	}
			
		if(this.knownWifi!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_KNOWNWIFI+">");
			sb.append(this.knownWifi.getId());
	    	sb.append("</"+XML_REF_KNOWNWIFI+">");
		}
		// TODO deal with other case

		sb.append("</"+XML_DETECTEDWIFI+">");
		return sb.toString();
	}
}