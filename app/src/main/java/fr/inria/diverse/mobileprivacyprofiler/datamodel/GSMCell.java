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

import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.GSMCell_NeighboringCellHistory;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.DetectedWifi_AccessPoint;
// Start of user code additional import for GSMCell
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "gSMCell")
public class GSMCell {

	public static Log log = LogFactory.getLog(GSMCell.class);

	public static final String XML_GSMCELL = "GSMCELL";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_CELLIDENTITY = "cellIdentity";
	public static final String XML_ATT_GEOLOCATION = "geolocation";
	public static final String XML_REF_HISTORY = "history";
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
	

	@DatabaseField
	protected java.lang.String cellIdentity;

	@DatabaseField
	protected java.lang.String geolocation;
	

	// work in progress, find association 
	// Association many to many GSMCell_NeighboringCellHistory
	@ForeignCollectionField(eager = false, foreignFieldName = "gSMCell")	
	protected ForeignCollection<GSMCell_NeighboringCellHistory> gSMCell_NeighboringCellHistory;

	/**  
	  * Attention, returned list is readonly
      */
	public List<NeighboringCellHistory> getHistory(){
		List<NeighboringCellHistory> result = new ArrayList<NeighboringCellHistory>();
		
		for (GSMCell_NeighboringCellHistory aGSMCell_NeighboringCellHistory : gSMCell_NeighboringCellHistory) {
			if(_contextDB != null) aGSMCell_NeighboringCellHistory.setContextDB(_contextDB);
			result.add(aGSMCell_NeighboringCellHistory.getNeighboringCellHistory());
		}
		return result;
	}
	public void addNeighboringCellHistory(NeighboringCellHistory neighboringCellHistory){
		try {
			_contextDB.gSMCell_NeighboringCellHistoryDao.create(new GSMCell_NeighboringCellHistory( this, neighboringCellHistory));
		} catch (SQLException e) {
			log.error("Pb while adding association gSMCell_NeighboringCellHistory",e);
		}
	}
	// end work in progress 	

				

	// Start of user code GSMCell additional user properties
	// End of user code
	
	public GSMCell() {} // needed by ormlite
	public GSMCell(java.lang.String cellIdentity, java.lang.String geolocation) {
		super();
		this.cellIdentity = cellIdentity;
		this.geolocation = geolocation;
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

	public java.lang.String getCellIdentity() {
		return this.cellIdentity;
	}
	public void setCellIdentity(java.lang.String cellIdentity) {
		this.cellIdentity = cellIdentity;
	}
	public java.lang.String getGeolocation() {
		return this.geolocation;
	}
	public void setGeolocation(java.lang.String geolocation) {
		this.geolocation = geolocation;
	}




	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_GSMCELL);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_CELLIDENTITY);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.cellIdentity));
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_GEOLOCATION);
    	sb.append("=\"");
		sb.append(StringEscapeUtils.escapeXml(this.geolocation));
    	sb.append("\" ");
    	sb.append(">");


		
		for(NeighboringCellHistory ref : this.getHistory()){
    		sb.append("\n"+indent+"\t<"+XML_REF_HISTORY+" id=\"");
    		sb.append(ref._id);
        	sb.append("\"/>");
			
    	}
			
		// TODO deal with other case

		sb.append("</"+XML_GSMCELL+">");
		return sb.toString();
	}
}
