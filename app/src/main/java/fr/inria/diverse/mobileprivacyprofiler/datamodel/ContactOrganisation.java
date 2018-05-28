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
// Start of user code additional import for ContactOrganisation
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "contactOrganisation")
public class ContactOrganisation {

	public static Log log = LogFactory.getLog(ContactOrganisation.class);

	public static final String XML_CONTACTORGANISATION = "CONTACTORGANISATION";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_COMPANY = "company";
	public static final String XML_ATT_TITLE = "title";
	public static final String XML_REF_REFERENCEDCONTACT = "referencedContact";
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
	public boolean referencedContact_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected java.lang.String company;

	@DatabaseField
	protected java.lang.String title;
	

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected Contact referencedContact;

	// Start of user code ContactOrganisation additional user properties
	// End of user code
	
	public ContactOrganisation() {} // needed by ormlite
	public ContactOrganisation(java.lang.String company, java.lang.String title) {
		super();
		this.company = company;
		this.title = title;
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

	public java.lang.String getCompany() {
		return this.company;
	}
	public void setCompany(java.lang.String company) {
		this.company = company;
	}
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public Contact getReferencedContact() {
		try {
			if(referencedContact_mayNeedDBRefresh && _contextDB != null){
				_contextDB.contactDao.refresh(this.referencedContact);
				referencedContact_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.referencedContact == null){
			log.warn("ContactOrganisation may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.referencedContact;
	}
	public void setReferencedContact(Contact referencedContact) {
		this.referencedContact = referencedContact;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_CONTACTORGANISATION);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_COMPANY);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.company));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_TITLE);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.title));
    	sb.append("\" ");
    	sb.append(">");


		if(this.referencedContact!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_REFERENCEDCONTACT+">");
			sb.append(this.referencedContact.getId());
	    	sb.append("</"+XML_REF_REFERENCEDCONTACT+">");
		}
		// TODO deal with other case

		sb.append("</"+XML_CONTACTORGANISATION+">");
		return sb.toString();
	}
}
