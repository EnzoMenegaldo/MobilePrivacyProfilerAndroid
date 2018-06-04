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

import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.DetectedWifi_AccessPoint;
// Start of user code additional import for WifiAccessPoint
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "wifiAccessPoint")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id")
public class WifiAccessPoint {

	public static Log log = LogFactory.getLog(WifiAccessPoint.class);

	public static final String XML_WIFIACCESSPOINT = "WIFIACCESSPOINT";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_SSID = "ssid";
	public static final String XML_ATT_LOCATION = "location";
	public static final String XML_REF_DETECTEDWIFIS = "detectedWifis";
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
	protected java.lang.String ssid;

	@DatabaseField
	protected java.lang.String location;
	

	// work in progress, find association 
	// Association many to many DetectedWifi_AccessPoint
	@ForeignCollectionField(eager = false, foreignFieldName = "wifiAccessPoint")
	// @JsonBackReference(value="detectedwifi_accesspoint")	
	protected ForeignCollection<DetectedWifi_AccessPoint> detectedWifi_AccessPoint;

	/**  
	  * Attention, returned list is readonly
      */
	public List<DetectedWifi> getDetectedWifis(){
		List<DetectedWifi> result = new ArrayList<DetectedWifi>();
		
		for (DetectedWifi_AccessPoint aDetectedWifi_AccessPoint : detectedWifi_AccessPoint) {
			if(_contextDB != null) aDetectedWifi_AccessPoint.setContextDB(_contextDB);
			result.add(aDetectedWifi_AccessPoint.getDetectedWifi());
		}
		return result;
	}
	/*public void addDetectedWifi(DetectedWifi detectedWifi){
		try {
			_contextDB.detectedWifi_AccessPointDao.create(new DetectedWifi_AccessPoint( detectedWifi, this));		
		} catch (SQLException e) {
			log.error("Pb while adding association detectedWifi_AccessPoint",e);
		}
	}*/
	// end work in progress 	

				

	// Start of user code WifiAccessPoint additional user properties
	// End of user code
	
	public WifiAccessPoint() {} // needed by ormlite
	public WifiAccessPoint(java.lang.String ssid, java.lang.String location) {
		super();
		this.ssid = ssid;
		this.location = location;
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

	public java.lang.String getSsid() {
		return this.ssid;
	}
	@JsonProperty
	public void setSsid(java.lang.String ssid) {
		this.ssid = ssid;
	}
	public java.lang.String getLocation() {
		return this.location;
	}
	@JsonProperty
	public void setLocation(java.lang.String location) {
		this.location = location;
	}




	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_WIFIACCESSPOINT);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_SSID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.ssid));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LOCATION);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.location));
    	sb.append("\" ");
    	sb.append(">");


		
		for(DetectedWifi ref : this.getDetectedWifis()){
    		sb.append("\n"+indent+"\t<"+XML_REF_DETECTEDWIFIS+" id=\"");
    		sb.append(ref._id);
        	sb.append("\"/>");
			
    	}
			
		// TODO deal with other case

		sb.append("</"+XML_WIFIACCESSPOINT+">");
		return sb.toString();
	}
}
