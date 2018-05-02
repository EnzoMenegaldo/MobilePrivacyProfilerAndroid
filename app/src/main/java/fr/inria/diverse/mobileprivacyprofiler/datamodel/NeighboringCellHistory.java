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
// Start of user code additional import for NeighboringCellHistory
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "neighboringCellHistory")
public class NeighboringCellHistory {

	public static Log log = LogFactory.getLog(NeighboringCellHistory.class);

	public static final String XML_NEIGHBORINGCELLHISTORY = "NEIGHBORINGCELLHISTORY";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_DATE = "date";
	public static final String XML_ATT_STRENGTH = "strength";
	public static final String XML_REF_CELLS = "cells";
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
	public boolean cells_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected java.util.Date date;

	@DatabaseField
	protected int strength;
	

	/** observed cells at that date */ 
	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	protected Cell cells;

	// Start of user code NeighboringCellHistory additional user properties
	// End of user code
	
	public NeighboringCellHistory() {} // needed by ormlite
	public NeighboringCellHistory(java.util.Date date, int strength) {
		super();
		this.date = date;
		this.strength = strength;
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

	public java.util.Date getDate() {
		return this.date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public int getStrength() {
		return this.strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/** observed cells at that date */ 
	public Cell getCells() {
		try {
			if(cells_mayNeedDBRefresh && _contextDB != null){
				_contextDB.cellDao.refresh(this.cells);
				cells_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.cells == null){
			log.warn("NeighboringCellHistory may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.cells;
	}
	public void setCells(Cell cells) {
		this.cells = cells;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_NEIGHBORINGCELLHISTORY);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_DATE);
    	sb.append("=\"");
		sb.append(this.date);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_STRENGTH);
    	sb.append("=\"");
		sb.append(this.strength);
    	sb.append("\" ");
    	sb.append(">");


		if(this.cells!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_CELLS+">");
			sb.append(this.cells.getId());
	    	sb.append("</"+XML_REF_CELLS+">");
		}
		// TODO deal with other case

		sb.append("</"+XML_NEIGHBORINGCELLHISTORY+">");
		return sb.toString();
	}
}
