/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel.xml;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmlpull.v1.XmlPullParserException;

import fr.inria.diverse.mobileprivacyprofiler.datamodel.*;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.xml.MobilePrivacyProfilerDBXMLParser.RefCommand;
// Start of user code additional import
// End of user code

/**
 * Class used to simplify the access to the XML tools in the application
 */
public class XMLHelper {
	private static Log log = LogFactory.getLog(XMLHelper.class);
	// Start of user code additional helper code 1
	// End of user code

	public static void saveDBToFile(File file,MobilePrivacyProfilerDBHelper dbContext){
		try {
			// Create file
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(generateXML4DB(dbContext));
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static String generateXML4DB(MobilePrivacyProfilerDBHelper dbContext){
		StringBuilder sb = new StringBuilder();
		sb.append("<MOBILEPRIVACYPROFILERDB>");
		sb.append("\n\t<APPLICATIONHISTORYS>");
		try {	
			List<ApplicationHistory> applicationHistorys = dbContext.applicationHistoryDao.queryForAll();
			for(ApplicationHistory  applicationHistory : applicationHistorys){
				// TODO find if contained by another element, if not put it there
					sb.append("\n");
					sb.append(applicationHistory.toXML("\t\t", dbContext));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</APPLICATIONHISTORYS>\n");
		sb.append("\n\t<APPLICATIONLOGENTRYS>");
		try {	
			List<ApplicationLogEntry> applicationLogEntrys = dbContext.applicationLogEntryDao.queryForAll();
			for(ApplicationLogEntry  applicationLogEntry : applicationLogEntrys){
				// TODO find if contained by another element, if not put it there
				boolean isContained = false;
				if(applicationLogEntry.getApplication() != null){
					isContained = true;
				}
				if(!isContained){
					sb.append("\n");
					sb.append(applicationLogEntry.toXML("\t\t", dbContext));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</APPLICATIONLOGENTRYS>\n");
		sb.append("\n\t<MOBILEPRIVACYPROFILERDB_METADATAS>");
		try {	
			List<MobilePrivacyProfilerDB_metadata> mobilePrivacyProfilerDB_metadatas = dbContext.mobilePrivacyProfilerDB_metadataDao.queryForAll();
			for(MobilePrivacyProfilerDB_metadata  mobilePrivacyProfilerDB_metadata : mobilePrivacyProfilerDB_metadatas){
				// TODO find if contained by another element, if not put it there
					sb.append("\n");
					sb.append(mobilePrivacyProfilerDB_metadata.toXML("\t\t", dbContext));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</MOBILEPRIVACYPROFILERDB_METADATAS>\n");
		sb.append("\n</MOBILEPRIVACYPROFILERDB>");
		return sb.toString();
	}
	
	public static void loadDBFromXMLFile(MobilePrivacyProfilerDBHelper dbContext, File file){
		try{
			loadDBFromXMLFile(dbContext, new FileInputStream(file));
		} catch (FileNotFoundException e) {
			log.error("File not found "+e.getMessage(),e);
		}
	}

	public static void loadDBFromXMLFile(MobilePrivacyProfilerDBHelper dbContext, InputStream inputStream){
		MobilePrivacyProfilerDBXMLParser parser = new MobilePrivacyProfilerDBXMLParser();
		try {
			log.info("starting parsing...");
			parser.parse(inputStream);
			// create the elements in the DB
			log.info("starting creation of ApplicationHistory...");
			for(ApplicationHistory applicationHistory : parser.applicationHistorys){
				try {
					dbContext.applicationHistoryDao.create(applicationHistory);
				} catch (SQLException e) {
					log.error("cannot create ApplicationHistory "+e.getMessage(),e);
				}
			}
			log.info("starting creation of ApplicationLogEntry...");
			for(ApplicationLogEntry applicationLogEntry : parser.applicationLogEntrys){
				try {
					dbContext.applicationLogEntryDao.create(applicationLogEntry);
				} catch (SQLException e) {
					log.error("cannot create ApplicationLogEntry "+e.getMessage(),e);
				}
			}
			log.info("starting creation of MobilePrivacyProfilerDB_metadata...");
			for(MobilePrivacyProfilerDB_metadata mobilePrivacyProfilerDB_metadata : parser.mobilePrivacyProfilerDB_metadatas){
				try {
					dbContext.mobilePrivacyProfilerDB_metadataDao.create(mobilePrivacyProfilerDB_metadata);
				} catch (SQLException e) {
					log.error("cannot create MobilePrivacyProfilerDB_metadata "+e.getMessage(),e);
				}
			}
			log.info("starting crossref...");
			// proceed with cross ref
			for (RefCommand command : parser.refCommands) {
				command.run();
			}
			
			// update the DB
			log.info("starting update DB of ApplicationHistory...");
			for(ApplicationHistory elem : parser.applicationHistorysToUpdate){
				try {
					dbContext.applicationHistoryDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update ApplicationHistory "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of ApplicationLogEntry...");
			for(ApplicationLogEntry elem : parser.applicationLogEntrysToUpdate){
				try {
					dbContext.applicationLogEntryDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update ApplicationLogEntry "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of MobilePrivacyProfilerDB_metadata...");
			for(MobilePrivacyProfilerDB_metadata elem : parser.mobilePrivacyProfilerDB_metadatasToUpdate){
				try {
					dbContext.mobilePrivacyProfilerDB_metadataDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update MobilePrivacyProfilerDB_metadata "+e.getMessage(),e);
				}
			}
			log.info("DB filled from XML");
		} catch (XmlPullParserException e) {
			log.error("XML parse error "+e.getMessage(),e);
		} catch (IOException e) {
			log.error("Read error "+e.getMessage(),e);
		}
		// Start of user code loadDBFromXMLFile 2
		// End of user code
	}
	
	// Start of user code additional helper code 2
	// End of user code
}
