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
// Start of user code additional import for Authentification
// End of user code

/** 
  * Identity known on the device 
  */ 
@DatabaseTable(tableName = "authentification")
public class Authentification {

	public static Log log = LogFactory.getLog(Authentification.class);

	public static final String XML_AUTHENTIFICATION = "AUTHENTIFICATION";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_PACKAGENAME = "packageName";
	public static final String XML_ATT_NAME = "name";
	public static final String XML_ATT_TYPE = "type";
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
	

	/** pachageName related to this Autenticator
 */ 
	@DatabaseField
	protected java.lang.String packageName;

	/** name related to this Authenticator */ 
	@DatabaseField
	protected java.lang.String name;

	/** type is a unique string that identify an authenticator */ 
	@DatabaseField
	protected java.lang.String type;
	

	// Start of user code Authentification additional user properties
	// End of user code
	
	public Authentification() {} // needed by ormlite
	public Authentification(java.lang.String packageName, java.lang.String name, java.lang.String type) {
		super();
		this.packageName = packageName;
		this.name = name;
		this.type = type;
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

	public java.lang.String getPackageName() {
		return this.packageName;
	}
	public void setPackageName(java.lang.String packageName) {
		this.packageName = packageName;
	}
	public java.lang.String getName() {
		return this.name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getType() {
		return this.type;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}




	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_AUTHENTIFICATION);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_PACKAGENAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.packageName));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_NAME);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.name));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_TYPE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.type));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_AUTHENTIFICATION+">");
		return sb.toString();
	}
}
