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

// Start of user code additional import for OtherCellData
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "otherCellData")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id")
public class OtherCellData {

	public static Log log = LogFactory.getLog(OtherCellData.class);

	public static final String XML_OTHERCELLDATA = "OTHERCELLDATA";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_LACTAC = "lacTac";
	public static final String XML_ATT_TYPE = "type";
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
	protected int lacTac;

	@DatabaseField
	protected java.lang.String type;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="cell_othercelldata")
	protected Cell identity;

	// Start of user code OtherCellData additional user properties
	// End of user code
	
	public OtherCellData() {} // needed by ormlite
	public OtherCellData(int lacTac, java.lang.String type) {
		super();
		this.lacTac = lacTac;
		this.type = type;
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

	public int getLacTac() {
		return this.lacTac;
	}
	@JsonProperty
	public void setLacTac(int lacTac) {
		this.lacTac = lacTac;
	}
	public java.lang.String getType() {
		return this.type;
	}
	@JsonProperty
	public void setType(java.lang.String type) {
		this.type = type;
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
			log.warn("OtherCellData may not be properly refreshed from DB (_id="+_id+")");
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
    	sb.append(XML_OTHERCELLDATA);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_LACTAC);
    	sb.append("=\"");
		sb.append(this.lacTac);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_TYPE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.type));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_OTHERCELLDATA+">");
		return sb.toString();
	}
}
