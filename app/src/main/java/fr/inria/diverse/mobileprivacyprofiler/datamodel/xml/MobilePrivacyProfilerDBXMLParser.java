/*  */
package fr.inria.diverse.mobileprivacyprofiler.datamodel.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

//import fr.inria.diverse.mobileprivacyprofiler.datamodel.associations.*;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.*;
// Start of user code additional import for MobilePrivacyProfilerDBXMLParser
// End of user code

/**
 * Root XmlPullParser for parsing the datamodel MobilePrivacyProfilerDB
 */
public class MobilePrivacyProfilerDBXMLParser {
	// Start of user code additional handler code 1
	// End of user code

	List<RefCommand> refCommands = new ArrayList<RefCommand>();

	List<ApplicationHistory> applicationHistorys = new ArrayList<ApplicationHistory>();
	List<ApplicationLogEntry> applicationLogEntrys = new ArrayList<ApplicationLogEntry>();
	List<MobilePrivacyProfilerDB_metadata> mobilePrivacyProfilerDB_metadatas = new ArrayList<MobilePrivacyProfilerDB_metadata>();
	Set<ApplicationHistory> applicationHistorysToUpdate = new HashSet<ApplicationHistory>();
	Set<ApplicationLogEntry> applicationLogEntrysToUpdate = new HashSet<ApplicationLogEntry>();
	Set<MobilePrivacyProfilerDB_metadata> mobilePrivacyProfilerDB_metadatasToUpdate = new HashSet<MobilePrivacyProfilerDB_metadata>();
	Hashtable<String, ApplicationHistory> xmlId2ApplicationHistory = new Hashtable<String, ApplicationHistory>();
	Hashtable<String, ApplicationLogEntry> xmlId2ApplicationLogEntry = new Hashtable<String, ApplicationLogEntry>();
	Hashtable<String, MobilePrivacyProfilerDB_metadata> xmlId2MobilePrivacyProfilerDB_metadata = new Hashtable<String, MobilePrivacyProfilerDB_metadata>();

	// minimize memory footprint by using static Strings
    public static final String ID_STRING = "id";

	public static final String DATACLASSIFIER_APPLICATIONHISTORYS = "APPLICATIONHISTORYS";
	public static final String DATACLASSIFIER_APPLICATIONHISTORY  = "APPLICATIONHISTORY";
	public static final String DATACLASSIFIER_APPLICATIONLOGENTRYS = "APPLICATIONLOGENTRYS";
	public static final String DATACLASSIFIER_APPLICATIONLOGENTRY  = "APPLICATIONLOGENTRY";
	public static final String DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS = "MOBILEPRIVACYPROFILERDB_METADATAS";
	public static final String DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATA  = "MOBILEPRIVACYPROFILERDB_METADATA";

	public static final String DATAATT_APPLICATIONHISTORY_appName = "appName";
	public static final String DATAATT_APPLICATIONHISTORY_APPNAME = "APPNAME";
	public static final String DATAATT_APPLICATIONHISTORY_packageName = "packageName";
	public static final String DATAATT_APPLICATIONHISTORY_PACKAGENAME = "PACKAGENAME";
	public static final String DATAREF_APPLICATIONHISTORY_logEntries = "logEntries";
	public static final String DATAATT_APPLICATIONLOGENTRY_date = "date";
	public static final String DATAATT_APPLICATIONLOGENTRY_DATE = "DATE";
	public static final String DATAATT_APPLICATIONLOGENTRY_actionDetails = "actionDetails";
	public static final String DATAATT_APPLICATIONLOGENTRY_ACTIONDETAILS = "ACTIONDETAILS";
	public static final String DATAREF_APPLICATIONLOGENTRY_application = "application";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_lastTransmissionDate = "lastTransmissionDate";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTTRANSMISSIONDATE = "LASTTRANSMISSIONDATE";



	// We don't use namespaces
    private static final String ns = null;

    public MobilePrivacyProfilerDBXMLParser() {
        
    }

	public void parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            readMobilePrivacyProfilerDB(parser);
        } finally {
            in.close();
        }
    }

	private void readMobilePrivacyProfilerDB(XmlPullParser parser)  throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, ns, "MOBILEPRIVACYPROFILERDB");
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
		 	if (name.equals(DATACLASSIFIER_APPLICATIONHISTORYS)) {
				applicationHistorys = readApplicationHistorys(parser,DATACLASSIFIER_APPLICATIONHISTORYS);
	            // applicationHistorys.addAll(readApplicationHistorys(parser,DATACLASSIFIER_APPLICATIONHISTORYS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_APPLICATIONLOGENTRYS)) {
				applicationLogEntrys = readApplicationLogEntrys(parser,DATACLASSIFIER_APPLICATIONLOGENTRYS);
	            // applicationLogEntrys.addAll(readApplicationLogEntrys(parser,DATACLASSIFIER_APPLICATIONLOGENTRYS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS)) {
				mobilePrivacyProfilerDB_metadatas = readMobilePrivacyProfilerDB_metadatas(parser,DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS);
	            // mobilePrivacyProfilerDB_metadatas.addAll(readMobilePrivacyProfilerDB_metadatas(parser,DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS));
	        } else 
			{
	            skip(parser);
	        }
	    }
		
	}

	/**
     * parser for a group of ApplicationHistory
     */
	List<ApplicationHistory> readApplicationHistorys(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<ApplicationHistory> entries = new ArrayList<ApplicationHistory>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_APPLICATIONHISTORY)) {
	            entries.add(readApplicationHistory(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of ApplicationLogEntry
     */
	List<ApplicationLogEntry> readApplicationLogEntrys(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<ApplicationLogEntry> entries = new ArrayList<ApplicationLogEntry>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_APPLICATIONLOGENTRY)) {
	            entries.add(readApplicationLogEntry(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of MobilePrivacyProfilerDB_metadata
     */
	List<MobilePrivacyProfilerDB_metadata> readMobilePrivacyProfilerDB_metadatas(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<MobilePrivacyProfilerDB_metadata> entries = new ArrayList<MobilePrivacyProfilerDB_metadata>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATA)) {
	            entries.add(readMobilePrivacyProfilerDB_metadata(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}

	ApplicationHistory readApplicationHistory(XmlPullParser parser)  throws XmlPullParserException, IOException{
		ApplicationHistory result = new ApplicationHistory();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_APPLICATIONHISTORY);
    	String currentTagName = parser.getName();
    			
    	xmlId2ApplicationHistory.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setAppName(parser.getAttributeValue(null, DATAATT_APPLICATIONHISTORY_appName));
		result.setPackageName(parser.getAttributeValue(null, DATAATT_APPLICATIONHISTORY_packageName));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_APPLICATIONHISTORY_logEntries)) {
				List<ApplicationLogEntry> entries = readApplicationLogEntrys(parser,DATAREF_APPLICATIONHISTORY_logEntries);	
				applicationLogEntrys.addAll(entries); // add for inclusion in the DB
				//result.getLogEntries().addAll(entries);  //  doesn't work and need to be done in the other way round using the opposite
				refCommands.add(new ApplicationHistory_addContainedLogEntries_RefCommand(result,entries));	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	ApplicationLogEntry readApplicationLogEntry(XmlPullParser parser)  throws XmlPullParserException, IOException{
		ApplicationLogEntry result = new ApplicationLogEntry();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_APPLICATIONLOGENTRY);
    	String currentTagName = parser.getName();
    			
    	xmlId2ApplicationLogEntry.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setDate(parser.getAttributeValue(null, DATAATT_APPLICATIONLOGENTRY_date));
		result.setActionDetails(parser.getAttributeValue(null, DATAATT_APPLICATIONLOGENTRY_actionDetails));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_APPLICATIONLOGENTRY_application)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_APPLICATIONLOGENTRY_application);
	            String id = readText(parser);
				refCommands.add(new ApplicationLogEntry_setApplication_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_APPLICATIONLOGENTRY_application);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	MobilePrivacyProfilerDB_metadata readMobilePrivacyProfilerDB_metadata(XmlPullParser parser)  throws XmlPullParserException, IOException{
		MobilePrivacyProfilerDB_metadata result = new MobilePrivacyProfilerDB_metadata();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATA);
    	String currentTagName = parser.getName();
    			
    	xmlId2MobilePrivacyProfilerDB_metadata.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setLastTransmissionDate(parser.getAttributeValue(null, DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_lastTransmissionDate));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
	        {
	            skip(parser);
	        }
	    }

		return result;
	}

   /**
    * abstract command for dealing with all task that must wait that the element have been created
	*/
	public abstract class RefCommand{
		public abstract void run();
	}
	class ApplicationHistory_addContainedLogEntries_RefCommand extends RefCommand{
		ApplicationHistory container;
		List<ApplicationLogEntry> containedElements;
		
		public ApplicationHistory_addContainedLogEntries_RefCommand(ApplicationHistory container,
				List<ApplicationLogEntry> containedElements) {
			super();
			this.container = container;
			this.containedElements = containedElements;
		}

		@Override
		public void run() {
			for (ApplicationLogEntry element : containedElements) {				
				element.setApplication(container);
				applicationLogEntrysToUpdate.add(element);
			}
		}
		
	}
	class ApplicationLogEntry_setApplication_RefCommand extends RefCommand{
		ApplicationLogEntry self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public ApplicationLogEntry_setApplication_RefCommand(ApplicationLogEntry self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setApplication(parser.xmlId2ApplicationHistory.get(referencedElementID));
			applicationLogEntrysToUpdate.add(self);
		}
	}

	// ---------- Additional helper methods
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	}

	// Start of user code additional handler code 2
	// End of user code
}
