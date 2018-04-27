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

import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.Cell_NeighboringCellHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.DetectedWifi_AccessPoint;
// Start of user code additional import for ContactPhoneNumber
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "contactPhoneNumber")
public class ContactPhoneNumber {

	public static Log log = LogFactory.getLog(ContactPhoneNumber.class);

	public static final String XML_CONTACTPHONENUMBER = "CONTACTPHONENUMBER";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_PHONENUMBER = "phoneNumber";
	public static final String XML_ATT_ROLE = "role";
	public static final String XML_REF_CONTACT = "contact";
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
	public boolean contact_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected java.lang.String phoneNumber;

	/** role of the phone number for the contact (Home, professionnal, Mobile, ...) */ 
	@DatabaseField
	protected java.lang.String role;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected Contact contact;

	// Start of user code ContactPhoneNumber additional user properties
	// End of user code
	
	public ContactPhoneNumber() {} // needed by ormlite
	public ContactPhoneNumber(java.lang.String phoneNumber, java.lang.String role) {
		super();
		this.phoneNumber = phoneNumber;
		this.role = role;
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

	public java.lang.String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(java.lang.String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public java.lang.String getRole() {
		return this.role;
	}
	public void setRole(java.lang.String role) {
		this.role = role;
	}

	public Contact getContact() {
		try {
			if(contact_mayNeedDBRefresh && _contextDB != null){
				_contextDB.contactDao.refresh(this.contact);
				contact_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.contact == null){
			log.warn("ContactPhoneNumber may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_CONTACTPHONENUMBER);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_PHONENUMBER);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.phoneNumber));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_ROLE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.role));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_CONTACTPHONENUMBER+">");
		return sb.toString();
	}
}
