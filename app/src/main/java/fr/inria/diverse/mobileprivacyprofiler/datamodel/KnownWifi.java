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

// Start of user code additional import for KnownWifi
// End of user code

/** 
  * Registered Wifi connexion on the device.
Ie. the device has registered it and was connected to it 
  */ 
@DatabaseTable(tableName = "knownWifi")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id")
public class KnownWifi {

	public static Log log = LogFactory.getLog(KnownWifi.class);

	public static final String XML_KNOWNWIFI = "KNOWNWIFI";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_SSID = "ssid";
	public static final String XML_ATT_LOCATION = "location";
	public static final String XML_REF_DETECTEDWIFIS = "detectedWifis";
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
	protected java.lang.String ssid;

	/** Location of the Wifi

note: a given SSID is shared by several router (Access Point) (possibly worldwide) 
so we need to check how this data is managed and have a better model */ 
	@DatabaseField
	protected java.lang.String location;
	

	@ForeignCollectionField(eager = false, foreignFieldName = "knownWifi")
	// @JsonBackReference(value="knownwifi_detectedwifi")
	protected ForeignCollection<DetectedWifi> detectedWifis;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="knownwifi_mobileprivacyprofilerdb_metadata")
	protected MobilePrivacyProfilerDB_metadata userMetaData;

	// Start of user code KnownWifi additional user properties
	// End of user code
	
	public KnownWifi() {} // needed by ormlite
	public KnownWifi(java.lang.String ssid, java.lang.String location) {
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

	public List	<DetectedWifi> getDetectedWifis() {
		if(null==this.detectedWifis){return null;}
		return new ArrayList<DetectedWifi>(detectedWifis);
	}
	
	@JsonProperty
	public void setDetectedWifis (Collection<DetectedWifi> collection){ this.detectedWifis=(ForeignCollection) collection;}

			
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
			log.warn("KnownWifi may not be properly refreshed from DB (_id="+_id+")");
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
    	sb.append(XML_KNOWNWIFI);
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


		if(this.detectedWifis != null){
			for(DetectedWifi ref : this.detectedWifis){
					
	    		sb.append("\n"+indent+"\t<"+XML_REF_DETECTEDWIFIS+" id=\"");
	    		sb.append(ref._id);
	        	sb.append("\"/>");
	    	}		
		}
		if(this.userMetaData!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERMETADATA+">");
			sb.append(this.userMetaData.getId());
	    	sb.append("</"+XML_REF_USERMETADATA+">");
		}
		// TODO deal with other case

		sb.append("</"+XML_KNOWNWIFI+">");
		return sb.toString();
	}
}
