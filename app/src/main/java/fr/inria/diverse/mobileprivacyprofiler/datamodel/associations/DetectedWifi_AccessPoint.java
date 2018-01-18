/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel.associations;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.*;

/** 
  *  
  */ 
@DatabaseTable(tableName = "detectedWifi_AccessPoint")
public class DetectedWifi_AccessPoint {
 
	public static Log log = LogFactory.getLog(DetectedWifi_AccessPoint.class);

	
	public final static String DETECTEDWIFI_ID_FIELD_NAME = "DetectedWifi_id";
	public final static String WIFIACCESSPOINT_ID_FIELD_NAME = "WifiAccessPoint_id";

	// id is generated by the database and set on the object automagically
	@DatabaseField(generatedId = true)
	protected int _id;
	
	// This is a foreign object which just stores the id from the User object in this table.
	@DatabaseField(foreign = true, columnName = DETECTEDWIFI_ID_FIELD_NAME)
	DetectedWifi detectedWifi;

	// This is a foreign object which just stores the id from the Post object in this table.
	@DatabaseField(foreign = true, columnName = WIFIACCESSPOINT_ID_FIELD_NAME)
	WifiAccessPoint wifiAccessPoint;

	/**
     * dbHelper used to autorefresh values and doing queries
     * must be set other wise most getter will return proxy that will need to be refreshed
	 */
	protected MobilePrivacyProfilerDBHelper _contextDB = null;

	/**
	 * object created from DB may need to be updated from the DB for being fully navigable
	 */
	public boolean _detectedWifi_mayNeedDBRefresh = true;
	public boolean _wifiAccessPoint_mayNeedDBRefresh = true;

	DetectedWifi_AccessPoint() {
		// for ormlite
	}

	public DetectedWifi_AccessPoint(DetectedWifi detectedWifi, WifiAccessPoint wifiAccessPoint) {
		this.detectedWifi = detectedWifi;
		this.wifiAccessPoint = wifiAccessPoint;
	}

	/** accessors for Left part */  
	public DetectedWifi getDetectedWifi() {
		try {
			if(_detectedWifi_mayNeedDBRefresh && _contextDB != null){
				_contextDB.detectedWifiDao.refresh(this.detectedWifi);
				this.detectedWifi.setContextDB(_contextDB);
				_detectedWifi_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.detectedWifi == null){
			log.warn("DetectedWifi_AccessPoint may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.detectedWifi;
	}
	public void setDetectedWifi(DetectedWifi detectedWifi) {
		this.detectedWifi = detectedWifi;
	}

	/** accessors for Right part */ 
	public WifiAccessPoint getWifiAccessPoint() {
		try {
			if(_wifiAccessPoint_mayNeedDBRefresh && _contextDB != null){
				_contextDB.wifiAccessPointDao.refresh(this.wifiAccessPoint);
				this.wifiAccessPoint.setContextDB(_contextDB);
				_wifiAccessPoint_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.wifiAccessPoint == null){
			log.warn("DetectedWifi_AccessPoint may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.wifiAccessPoint;
	}
	public void setWifiAccessPoint(WifiAccessPoint wifiAccessPoint) {
		this.wifiAccessPoint = wifiAccessPoint;
	}


	public MobilePrivacyProfilerDBHelper getContextDB(){
		return _contextDB;
	}
	public void setContextDB(MobilePrivacyProfilerDBHelper contextDB){
		this._contextDB = contextDB;
	}
}