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

// Start of user code additional import for CdmaCellData
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "cdmaCellData")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = CdmaCellData.class)
public class CdmaCellData {

	public static Log log = LogFactory.getLog(CdmaCellData.class);

	public static final String XML_CDMACELLDATA = "CDMACELLDATA";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_LONGITUDE = "longitude";
	public static final String XML_ATT_LATITUDE = "latitude";
	public static final String XML_ATT_USERID = "userId";
	public static final String XML_REF_IDENTITY = "identity";
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
	public boolean identity_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected int longitude;

	@DatabaseField
	protected int latitude;

	@DatabaseField
	protected java.lang.String userId;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="cell_cdmacelldata")
	protected Cell identity;

	// Start of user code CdmaCellData additional user properties
	// End of user code
	
	public CdmaCellData() {} // needed by ormlite
	public CdmaCellData(int longitude, int latitude, java.lang.String userId) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
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

	public int getLongitude() {
		return this.longitude;
	}
	@JsonProperty
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public int getLatitude() {
		return this.latitude;
	}
	@JsonProperty
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	@JsonProperty
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public Cell getIdentity() {
		try {
			if(identity_mayNeedDBRefresh && _contextDB != null){
				_contextDB.cellDao.refresh(this.identity);
				identity_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.identity == null){
			log.warn("CdmaCellData may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.identity;
	}
	@JsonProperty
	public void setIdentity(Cell identity) {
		this.identity = identity;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_CDMACELLDATA);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LONGITUDE);
    	sb.append("=\"");
		sb.append(this.longitude);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LATITUDE);
    	sb.append("=\"");
		sb.append(this.latitude);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_USERID);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.userId));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_CDMACELLDATA+">");
		return sb.toString();
	}
}
