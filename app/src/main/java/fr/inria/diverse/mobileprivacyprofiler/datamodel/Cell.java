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

// Start of user code additional import for Cell
// End of user code

/** 
  *  
  */ 
@DatabaseTable(tableName = "cell")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
                  property  = "_id",
				  scope = Cell.class)
public class Cell {

	public static Log log = LogFactory.getLog(Cell.class);

	public static final String XML_CELL = "CELL";
	public static final String XML_ATT_ID = "id";
	public static final String XML_ATT_CELLID = "cellId";
	public static final String XML_REF_HISTORY = "history";
	public static final String XML_REF_CDMAPOSITION = "cdmaposition";
	public static final String XML_REF_OTHERPOSITION = "otherPosition";
	public static final String XML_REF_USERMETADATA = "userMetaData";
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
	public boolean cdmaposition_mayNeedDBRefresh = true;
	@JsonIgnore
	public boolean otherPosition_mayNeedDBRefresh = true;
	@JsonIgnore
	public boolean userMetaData_mayNeedDBRefresh = true;
	

	@DatabaseField
	protected int cellId;
	

	@ForeignCollectionField(eager = false, foreignFieldName = "cells")
	@JsonIgnore
	protected ForeignCollection<NeighboringCellHistory> history;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="cell_cdmacelldata")
	protected CdmaCellData cdmaposition;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="cell_othercelldata")
	protected OtherCellData otherPosition;

	@DatabaseField(foreign = true) //, columnName = USER_ID_FIELD_NAME)
	// @JsonManagedReference(value="cell_mobileprivacyprofilerdb_metadata")
	protected MobilePrivacyProfilerDB_metadata userMetaData;

	// Start of user code Cell additional user properties
	// End of user code
	
	public Cell() {} // needed by ormlite
	public Cell(int cellId) {
		super();
		this.cellId = cellId;
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

	public int getCellId() {
		return this.cellId;
	}
	@JsonProperty
	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	public List	<NeighboringCellHistory> getHistory() {
		if(null==this.history){return null;}
		return new ArrayList<NeighboringCellHistory>(history);
	}
	

			
	public CdmaCellData getCdmaposition() {
		try {
			if(cdmaposition_mayNeedDBRefresh && _contextDB != null){
				_contextDB.cdmaCellDataDao.refresh(this.cdmaposition);
				cdmaposition_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.cdmaposition == null){
			log.warn("Cell may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.cdmaposition;
	}
	@JsonProperty
	public void setCdmaposition(CdmaCellData cdmaposition) {
		this.cdmaposition = cdmaposition;
	}			
	public OtherCellData getOtherPosition() {
		try {
			if(otherPosition_mayNeedDBRefresh && _contextDB != null){
				_contextDB.otherCellDataDao.refresh(this.otherPosition);
				otherPosition_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.otherPosition == null){
			log.warn("Cell may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.otherPosition;
	}
	@JsonProperty
	public void setOtherPosition(OtherCellData otherPosition) {
		this.otherPosition = otherPosition;
	}			
	public MobilePrivacyProfilerDB_metadata getUserMetaData() {
		try {
			if(userMetaData_mayNeedDBRefresh && _contextDB != null){
				_contextDB.mobilePrivacyProfilerDB_metadataDao.refresh(this.userMetaData);
				userMetaData_mayNeedDBRefresh = false;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}
		if(_contextDB==null && this.userMetaData == null){
			log.warn("Cell may not be properly refreshed from DB (_id="+_id+")");
		}
		return this.userMetaData;
	}
	@JsonProperty
	public void setUserMetaData(MobilePrivacyProfilerDB_metadata userMetaData) {
		this.userMetaData = userMetaData;
	}			



	public String toXML(String indent, MobilePrivacyProfilerDBHelper contextDB){
		StringBuilder sb = new StringBuilder();
		sb.append(indent+"<");
    	sb.append(XML_CELL);
		sb.append(" "+XML_ATT_ID+"=\"");
		sb.append(this._id);
    	sb.append("\" ");
		sb.append(" ");
    	sb.append(XML_ATT_CELLID);
    	sb.append("=\"");
		sb.append(this.cellId);
    	sb.append("\" ");
    	sb.append(">");


		sb.append("\n"+indent+"\t<"+XML_REF_HISTORY+">");
		if(this.history != null){
			for(NeighboringCellHistory ref : this.history){
				sb.append("\n"+ref.toXML(indent+"\t\t", contextDB));
	    	}
		}
		sb.append("</"+XML_REF_HISTORY+">");		
		if(this.cdmaposition!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_CDMAPOSITION+">");
			sb.append(this.cdmaposition.getId());
	    	sb.append("</"+XML_REF_CDMAPOSITION+">");
		}
		if(this.otherPosition!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_OTHERPOSITION+">");
			sb.append(this.otherPosition.getId());
	    	sb.append("</"+XML_REF_OTHERPOSITION+">");
		}
		if(this.userMetaData!= null){
			sb.append("\n"+indent+"\t<"+XML_REF_USERMETADATA+">");
			sb.append(this.userMetaData.getId());
	    	sb.append("</"+XML_REF_USERMETADATA+">");
		}
		// TODO deal with other case

		sb.append("</"+XML_CELL+">");
		return sb.toString();
	}
}
