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

// Start of user code additional import for ContactPhysicalAddress
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "contactPhysicalAddress")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = ContactPhysicalAddress.class)
public class ContactPhysicalAddress {

	public static Log log = LogFactory.getLog(ContactPhysicalAddress.class);

	public static final String XML_CONTACTPHYSICALADDRESS = "CONTACTPHYSICALADDRESS";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_ADDRESS = "address";
	public static final String XML_ATT_ROLE = "role";
	public static final String XML_REF_CONTACT = "contact";
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
	public boolean contact_mayNeedDBRefresh = true;
	

	/** address for the contact
(currently only a big string, may need to be changed to detailed fields) */ 
	@DatabaseField(dataType = com.j256.ormlite.field.DataType.LONG_STRING)
	protected java.lang.String address;

	/** role of the address for the contact (home, work, ...) */ 
	@DatabaseField
	protected java.lang.String role;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="contact_physicaladdress")
	protected Contact contact;

	// Start of user code ContactPhysicalAddress additional user properties
	// End of user code
	
	public ContactPhysicalAddress() {} // needed by ormlite
	public ContactPhysicalAddress(java.lang.String address, java.lang.String role) {
		super();
		this.address = address;
		this.role = role;
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

	public java.lang.String getAddress() {
		return this.address;
	}
	@JsonProperty
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getRole() {
		return this.role;
	}
	@JsonProperty
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
			log.warn("ContactPhysicalAddress may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.contact;
	}
	@JsonProperty
	public void setContact(Contact contact) {
		this.contact = contact;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_CONTACTPHYSICALADDRESS);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_ADDRESS);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.address));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_ROLE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.role));
    	sb.append("\" ");
    	sb.append(">");


		// TODO deal with other case

		sb.append("</"+XML_CONTACTPHYSICALADDRESS+">");
		return sb.toString();
	}
}
