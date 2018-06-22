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

// Start of user code additional import for BluetoothLog
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "bluetoothLog")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = BluetoothLog.class)
public class BluetoothLog {

	public static Log log = LogFactory.getLog(BluetoothLog.class);

	public static final String XML_BLUETOOTHLOG = "BLUETOOTHLOG";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_DATE = "date";
	public static final String XML_ATT_USERID = "userId";
	public static final String XML_REF_DEVICE = "device";
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
	public boolean device_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected java.util.Date date;

	@DatabaseField
	protected java.lang.String userId;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="bluetoothdevice_bluetoothlog")
	protected BluetoothDevice device;

	// Start of user code BluetoothLog additional user properties
	// End of user code
	
	public BluetoothLog() {} // needed by ormlite
	public BluetoothLog(java.util.Date date, java.lang.String userId) {
		super();
		this.date = date;
		this.userId = userId;
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

	public java.util.Date getDate() {
		return this.date;
	}
	@JsonProperty
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	@JsonProperty
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public BluetoothDevice getDevice() {
		try {
			if(device_mayNeedDBRefresh && _contextDB != null){
				_contextDB.bluetoothDeviceDao.refresh(this.device);
				device_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.device == null){
			log.warn("BluetoothLog may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.device;
	}
	@JsonProperty
	public void setDevice(BluetoothDevice device) {
		this.device = device;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_BLUETOOTHLOG);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DATE);
    	sb.append("=\"");
		sb.append(this.date);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_BLUETOOTHLOG+">");
		return sb.toString();
	}
}
