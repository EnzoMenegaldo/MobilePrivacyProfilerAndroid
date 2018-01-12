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

import invalid.datamodel.associations.DetectedWifi_AccessPoint;
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
		sb.append("\n\t<IDENTITYS>");
		try {	
			List<Identity> identitys = dbContext.identityDao.queryForAll();
			for(Identity  identity : identitys){
				// TODO find if contained by another element, if not put it there
					sb.append("\n");
					sb.append(identity.toXML("\t\t", dbContext));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</IDENTITYS>\n");
		sb.append("\n\t<CONTACTS>");
		try {	
			List<Contact> contacts = dbContext.contactDao.queryForAll();
			for(Contact  contact : contacts){
				// TODO find if contained by another element, if not put it there
					sb.append("\n");
					sb.append(contact.toXML("\t\t", dbContext));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</CONTACTS>\n");
		sb.append("\n\t<CONTACTPHONENUMBERS>");
		try {	
			List<ContactPhoneNumber> contactPhoneNumbers = dbContext.contactPhoneNumberDao.queryForAll();
			for(ContactPhoneNumber  contactPhoneNumber : contactPhoneNumbers){
				// TODO find if contained by another element, if not put it there
				boolean isContained = false;
				if(contactPhoneNumber.getContact() != null){
					isContained = true;
				}
				if(!isContained){
					sb.append("\n");
					sb.append(contactPhoneNumber.toXML("\t\t", dbContext));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</CONTACTPHONENUMBERS>\n");
		sb.append("\n\t<CONTACTPHYSICALADDRESSS>");
		try {	
			List<ContactPhysicalAddress> contactPhysicalAddresss = dbContext.contactPhysicalAddressDao.queryForAll();
			for(ContactPhysicalAddress  contactPhysicalAddress : contactPhysicalAddresss){
				// TODO find if contained by another element, if not put it there
				boolean isContained = false;
				if(contactPhysicalAddress.getContact() != null){
					isContained = true;
				}
				if(!isContained){
					sb.append("\n");
					sb.append(contactPhysicalAddress.toXML("\t\t", dbContext));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</CONTACTPHYSICALADDRESSS>\n");
		sb.append("\n\t<CONTACTEMAILS>");
		try {	
			List<ContactEmail> contactEmails = dbContext.contactEmailDao.queryForAll();
			for(ContactEmail  contactEmail : contactEmails){
				// TODO find if contained by another element, if not put it there
				boolean isContained = false;
				if(contactEmail.getContact() != null){
					isContained = true;
				}
				if(!isContained){
					sb.append("\n");
					sb.append(contactEmail.toXML("\t\t", dbContext));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</CONTACTEMAILS>\n");
		sb.append("\n\t<KNOWNWIFIS>");
		try {	
			List<KnownWifi> knownWifis = dbContext.knownWifiDao.queryForAll();
			for(KnownWifi  knownWifi : knownWifis){
				// TODO find if contained by another element, if not put it there
					sb.append("\n");
					sb.append(knownWifi.toXML("\t\t", dbContext));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</KNOWNWIFIS>\n");
		sb.append("\n\t<WIFIACCESSPOINTS>");
		try {	
			List<WifiAccessPoint> wifiAccessPoints = dbContext.wifiAccessPointDao.queryForAll();
			for(WifiAccessPoint  wifiAccessPoint : wifiAccessPoints){
				// TODO find if contained by another element, if not put it there
					sb.append("\n");
					sb.append(wifiAccessPoint.toXML("\t\t", dbContext));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</WIFIACCESSPOINTS>\n");
		sb.append("\n\t<DETECTEDWIFIS>");
		try {	
			List<DetectedWifi> detectedWifis = dbContext.detectedWifiDao.queryForAll();
			for(DetectedWifi  detectedWifi : detectedWifis){
				// TODO find if contained by another element, if not put it there
					sb.append("\n");
					sb.append(detectedWifi.toXML("\t\t", dbContext));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</DETECTEDWIFIS>\n");
		sb.append("\n\t<GEOLOCATIONS>");
		try {	
			List<Geolocation> geolocations = dbContext.geolocationDao.queryForAll();
			for(Geolocation  geolocation : geolocations){
				// TODO find if contained by another element, if not put it there
					sb.append("\n");
					sb.append(geolocation.toXML("\t\t", dbContext));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</GEOLOCATIONS>\n");
		sb.append("\n\t<CALENDAREVENTS>");
		try {	
			List<CalendarEvent> calendarEvents = dbContext.calendarEventDao.queryForAll();
			for(CalendarEvent  calendarEvent : calendarEvents){
				// TODO find if contained by another element, if not put it there
					sb.append("\n");
					sb.append(calendarEvent.toXML("\t\t", dbContext));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append("\n\t</CALENDAREVENTS>\n");
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
			log.info("starting creation of Identity...");
			for(Identity identity : parser.identitys){
				try {
					dbContext.identityDao.create(identity);
				} catch (SQLException e) {
					log.error("cannot create Identity "+e.getMessage(),e);
				}
			}
			log.info("starting creation of Contact...");
			for(Contact contact : parser.contacts){
				try {
					dbContext.contactDao.create(contact);
				} catch (SQLException e) {
					log.error("cannot create Contact "+e.getMessage(),e);
				}
			}
			log.info("starting creation of ContactPhoneNumber...");
			for(ContactPhoneNumber contactPhoneNumber : parser.contactPhoneNumbers){
				try {
					dbContext.contactPhoneNumberDao.create(contactPhoneNumber);
				} catch (SQLException e) {
					log.error("cannot create ContactPhoneNumber "+e.getMessage(),e);
				}
			}
			log.info("starting creation of ContactPhysicalAddress...");
			for(ContactPhysicalAddress contactPhysicalAddress : parser.contactPhysicalAddresss){
				try {
					dbContext.contactPhysicalAddressDao.create(contactPhysicalAddress);
				} catch (SQLException e) {
					log.error("cannot create ContactPhysicalAddress "+e.getMessage(),e);
				}
			}
			log.info("starting creation of ContactEmail...");
			for(ContactEmail contactEmail : parser.contactEmails){
				try {
					dbContext.contactEmailDao.create(contactEmail);
				} catch (SQLException e) {
					log.error("cannot create ContactEmail "+e.getMessage(),e);
				}
			}
			log.info("starting creation of KnownWifi...");
			for(KnownWifi knownWifi : parser.knownWifis){
				try {
					dbContext.knownWifiDao.create(knownWifi);
				} catch (SQLException e) {
					log.error("cannot create KnownWifi "+e.getMessage(),e);
				}
			}
			log.info("starting creation of WifiAccessPoint...");
			for(WifiAccessPoint wifiAccessPoint : parser.wifiAccessPoints){
				try {
					dbContext.wifiAccessPointDao.create(wifiAccessPoint);
				} catch (SQLException e) {
					log.error("cannot create WifiAccessPoint "+e.getMessage(),e);
				}
			}
			log.info("starting creation of DetectedWifi...");
			for(DetectedWifi detectedWifi : parser.detectedWifis){
				try {
					dbContext.detectedWifiDao.create(detectedWifi);
				} catch (SQLException e) {
					log.error("cannot create DetectedWifi "+e.getMessage(),e);
				}
			}
			log.info("starting creation of Geolocation...");
			for(Geolocation geolocation : parser.geolocations){
				try {
					dbContext.geolocationDao.create(geolocation);
				} catch (SQLException e) {
					log.error("cannot create Geolocation "+e.getMessage(),e);
				}
			}
			log.info("starting creation of CalendarEvent...");
			for(CalendarEvent calendarEvent : parser.calendarEvents){
				try {
					dbContext.calendarEventDao.create(calendarEvent);
				} catch (SQLException e) {
					log.error("cannot create CalendarEvent "+e.getMessage(),e);
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
			log.info("starting update DB of Identity...");
			for(Identity elem : parser.identitysToUpdate){
				try {
					dbContext.identityDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update Identity "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of Contact...");
			for(Contact elem : parser.contactsToUpdate){
				try {
					dbContext.contactDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update Contact "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of ContactPhoneNumber...");
			for(ContactPhoneNumber elem : parser.contactPhoneNumbersToUpdate){
				try {
					dbContext.contactPhoneNumberDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update ContactPhoneNumber "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of ContactPhysicalAddress...");
			for(ContactPhysicalAddress elem : parser.contactPhysicalAddresssToUpdate){
				try {
					dbContext.contactPhysicalAddressDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update ContactPhysicalAddress "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of ContactEmail...");
			for(ContactEmail elem : parser.contactEmailsToUpdate){
				try {
					dbContext.contactEmailDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update ContactEmail "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of KnownWifi...");
			for(KnownWifi elem : parser.knownWifisToUpdate){
				try {
					dbContext.knownWifiDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update KnownWifi "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of WifiAccessPoint...");
			for(WifiAccessPoint elem : parser.wifiAccessPointsToUpdate){
				try {
					dbContext.wifiAccessPointDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update WifiAccessPoint "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of DetectedWifi...");
			for(DetectedWifi elem : parser.detectedWifisToUpdate){
				try {
					dbContext.detectedWifiDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update DetectedWifi "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of Geolocation...");
			for(Geolocation elem : parser.geolocationsToUpdate){
				try {
					dbContext.geolocationDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update Geolocation "+e.getMessage(),e);
				}
			}
			log.info("starting update DB of CalendarEvent...");
			for(CalendarEvent elem : parser.calendarEventsToUpdate){
				try {
					dbContext.calendarEventDao.update(elem);
				} catch (SQLException e) {
					log.error("cannot update CalendarEvent "+e.getMessage(),e);
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
