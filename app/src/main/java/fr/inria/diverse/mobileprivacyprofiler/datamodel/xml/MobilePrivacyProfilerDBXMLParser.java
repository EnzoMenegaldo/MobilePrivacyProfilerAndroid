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
	List<ApplicationUsageStats> applicationUsageStatss = new ArrayList<ApplicationUsageStats>();
	List<MobilePrivacyProfilerDB_metadata> mobilePrivacyProfilerDB_metadatas = new ArrayList<MobilePrivacyProfilerDB_metadata>();
	List<Identity> identitys = new ArrayList<Identity>();
	List<Contact> contacts = new ArrayList<Contact>();
	List<ContactPhoneNumber> contactPhoneNumbers = new ArrayList<ContactPhoneNumber>();
	List<ContactPhysicalAddress> contactPhysicalAddresss = new ArrayList<ContactPhysicalAddress>();
	List<ContactEmail> contactEmails = new ArrayList<ContactEmail>();
	List<KnownWifi> knownWifis = new ArrayList<KnownWifi>();
	List<WifiAccessPoint> wifiAccessPoints = new ArrayList<WifiAccessPoint>();
	List<DetectedWifi> detectedWifis = new ArrayList<DetectedWifi>();
	List<Geolocation> geolocations = new ArrayList<Geolocation>();
	List<CalendarEvent> calendarEvents = new ArrayList<CalendarEvent>();
	Set<ApplicationHistory> applicationHistorysToUpdate = new HashSet<ApplicationHistory>();
	Set<ApplicationUsageStats> applicationUsageStatssToUpdate = new HashSet<ApplicationUsageStats>();
	Set<MobilePrivacyProfilerDB_metadata> mobilePrivacyProfilerDB_metadatasToUpdate = new HashSet<MobilePrivacyProfilerDB_metadata>();
	Set<Identity> identitysToUpdate = new HashSet<Identity>();
	Set<Contact> contactsToUpdate = new HashSet<Contact>();
	Set<ContactPhoneNumber> contactPhoneNumbersToUpdate = new HashSet<ContactPhoneNumber>();
	Set<ContactPhysicalAddress> contactPhysicalAddresssToUpdate = new HashSet<ContactPhysicalAddress>();
	Set<ContactEmail> contactEmailsToUpdate = new HashSet<ContactEmail>();
	Set<KnownWifi> knownWifisToUpdate = new HashSet<KnownWifi>();
	Set<WifiAccessPoint> wifiAccessPointsToUpdate = new HashSet<WifiAccessPoint>();
	Set<DetectedWifi> detectedWifisToUpdate = new HashSet<DetectedWifi>();
	Set<Geolocation> geolocationsToUpdate = new HashSet<Geolocation>();
	Set<CalendarEvent> calendarEventsToUpdate = new HashSet<CalendarEvent>();
	Hashtable<String, ApplicationHistory> xmlId2ApplicationHistory = new Hashtable<String, ApplicationHistory>();
	Hashtable<String, ApplicationUsageStats> xmlId2ApplicationUsageStats = new Hashtable<String, ApplicationUsageStats>();
	Hashtable<String, MobilePrivacyProfilerDB_metadata> xmlId2MobilePrivacyProfilerDB_metadata = new Hashtable<String, MobilePrivacyProfilerDB_metadata>();
	Hashtable<String, Identity> xmlId2Identity = new Hashtable<String, Identity>();
	Hashtable<String, Contact> xmlId2Contact = new Hashtable<String, Contact>();
	Hashtable<String, ContactPhoneNumber> xmlId2ContactPhoneNumber = new Hashtable<String, ContactPhoneNumber>();
	Hashtable<String, ContactPhysicalAddress> xmlId2ContactPhysicalAddress = new Hashtable<String, ContactPhysicalAddress>();
	Hashtable<String, ContactEmail> xmlId2ContactEmail = new Hashtable<String, ContactEmail>();
	Hashtable<String, KnownWifi> xmlId2KnownWifi = new Hashtable<String, KnownWifi>();
	Hashtable<String, WifiAccessPoint> xmlId2WifiAccessPoint = new Hashtable<String, WifiAccessPoint>();
	Hashtable<String, DetectedWifi> xmlId2DetectedWifi = new Hashtable<String, DetectedWifi>();
	Hashtable<String, Geolocation> xmlId2Geolocation = new Hashtable<String, Geolocation>();
	Hashtable<String, CalendarEvent> xmlId2CalendarEvent = new Hashtable<String, CalendarEvent>();

	// minimize memory footprint by using static Strings
    public static final String ID_STRING = "id";

	public static final String DATACLASSIFIER_APPLICATIONHISTORYS = "APPLICATIONHISTORYS";
	public static final String DATACLASSIFIER_APPLICATIONHISTORY  = "APPLICATIONHISTORY";
	public static final String DATACLASSIFIER_APPLICATIONUSAGESTATSS = "APPLICATIONUSAGESTATSS";
	public static final String DATACLASSIFIER_APPLICATIONUSAGESTATS  = "APPLICATIONUSAGESTATS";
	public static final String DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS = "MOBILEPRIVACYPROFILERDB_METADATAS";
	public static final String DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATA  = "MOBILEPRIVACYPROFILERDB_METADATA";
	public static final String DATACLASSIFIER_IDENTITYS = "IDENTITYS";
	public static final String DATACLASSIFIER_IDENTITY  = "IDENTITY";
	public static final String DATACLASSIFIER_CONTACTS = "CONTACTS";
	public static final String DATACLASSIFIER_CONTACT  = "CONTACT";
	public static final String DATACLASSIFIER_CONTACTPHONENUMBERS = "CONTACTPHONENUMBERS";
	public static final String DATACLASSIFIER_CONTACTPHONENUMBER  = "CONTACTPHONENUMBER";
	public static final String DATACLASSIFIER_CONTACTPHYSICALADDRESSS = "CONTACTPHYSICALADDRESSS";
	public static final String DATACLASSIFIER_CONTACTPHYSICALADDRESS  = "CONTACTPHYSICALADDRESS";
	public static final String DATACLASSIFIER_CONTACTEMAILS = "CONTACTEMAILS";
	public static final String DATACLASSIFIER_CONTACTEMAIL  = "CONTACTEMAIL";
	public static final String DATACLASSIFIER_KNOWNWIFIS = "KNOWNWIFIS";
	public static final String DATACLASSIFIER_KNOWNWIFI  = "KNOWNWIFI";
	public static final String DATACLASSIFIER_WIFIACCESSPOINTS = "WIFIACCESSPOINTS";
	public static final String DATACLASSIFIER_WIFIACCESSPOINT  = "WIFIACCESSPOINT";
	public static final String DATACLASSIFIER_DETECTEDWIFIS = "DETECTEDWIFIS";
	public static final String DATACLASSIFIER_DETECTEDWIFI  = "DETECTEDWIFI";
	public static final String DATACLASSIFIER_GEOLOCATIONS = "GEOLOCATIONS";
	public static final String DATACLASSIFIER_GEOLOCATION  = "GEOLOCATION";
	public static final String DATACLASSIFIER_CALENDAREVENTS = "CALENDAREVENTS";
	public static final String DATACLASSIFIER_CALENDAREVENT  = "CALENDAREVENT";

	public static final String DATAATT_APPLICATIONHISTORY_appName = "appName";
	public static final String DATAATT_APPLICATIONHISTORY_APPNAME = "APPNAME";
	public static final String DATAATT_APPLICATIONHISTORY_packageName = "packageName";
	public static final String DATAATT_APPLICATIONHISTORY_PACKAGENAME = "PACKAGENAME";
	public static final String DATAREF_APPLICATIONHISTORY_usageStats = "usageStats";
	public static final String DATAATT_APPLICATIONUSAGESTATS_totalTimeInForeground = "totalTimeInForeground";
	public static final String DATAATT_APPLICATIONUSAGESTATS_TOTALTIMEINFOREGROUND = "TOTALTIMEINFOREGROUND";
	public static final String DATAATT_APPLICATIONUSAGESTATS_lastTimeUsed = "lastTimeUsed";
	public static final String DATAATT_APPLICATIONUSAGESTATS_LASTTIMEUSED = "LASTTIMEUSED";
	public static final String DATAATT_APPLICATIONUSAGESTATS_firstTimeStamp = "firstTimeStamp";
	public static final String DATAATT_APPLICATIONUSAGESTATS_FIRSTTIMESTAMP = "FIRSTTIMESTAMP";
	public static final String DATAATT_APPLICATIONUSAGESTATS_lastTimeStamp = "lastTimeStamp";
	public static final String DATAATT_APPLICATIONUSAGESTATS_LASTTIMESTAMP = "LASTTIMESTAMP";
	public static final String DATAREF_APPLICATIONUSAGESTATS_application = "application";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_lastTransmissionDate = "lastTransmissionDate";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTTRANSMISSIONDATE = "LASTTRANSMISSIONDATE";
	public static final String DATAATT_IDENTITY_provider = "provider";
	public static final String DATAATT_IDENTITY_PROVIDER = "PROVIDER";
	public static final String DATAATT_IDENTITY_login = "login";
	public static final String DATAATT_IDENTITY_LOGIN = "LOGIN";
	public static final String DATAATT_IDENTITY_displayName = "displayName";
	public static final String DATAATT_IDENTITY_DISPLAYNAME = "DISPLAYNAME";
	public static final String DATAATT_IDENTITY_associatedServices = "associatedServices";
	public static final String DATAATT_IDENTITY_ASSOCIATEDSERVICES = "ASSOCIATEDSERVICES";
	public static final String DATAATT_CONTACT_surname = "surname";
	public static final String DATAATT_CONTACT_SURNAME = "SURNAME";
	public static final String DATAATT_CONTACT_firstName = "firstName";
	public static final String DATAATT_CONTACT_FIRSTNAME = "FIRSTNAME";
	public static final String DATAATT_CONTACT_middleName = "middleName";
	public static final String DATAATT_CONTACT_MIDDLENAME = "MIDDLENAME";
	public static final String DATAATT_CONTACT_lastName = "lastName";
	public static final String DATAATT_CONTACT_LASTNAME = "LASTNAME";
	public static final String DATAATT_CONTACT_namePrefix = "namePrefix";
	public static final String DATAATT_CONTACT_NAMEPREFIX = "NAMEPREFIX";
	public static final String DATAREF_CONTACT_phoneNumbers = "phoneNumbers";
	public static final String DATAREF_CONTACT_physicalAddresses = "physicalAddresses";
	public static final String DATAREF_CONTACT_emails = "emails";
	public static final String DATAATT_CONTACTPHONENUMBER_phoneNumber = "phoneNumber";
	public static final String DATAATT_CONTACTPHONENUMBER_PHONENUMBER = "PHONENUMBER";
	public static final String DATAATT_CONTACTPHONENUMBER_role = "role";
	public static final String DATAATT_CONTACTPHONENUMBER_ROLE = "ROLE";
	public static final String DATAREF_CONTACTPHONENUMBER_contact = "contact";
	public static final String DATAATT_CONTACTPHYSICALADDRESS_address = "address";
	public static final String DATAATT_CONTACTPHYSICALADDRESS_ADDRESS = "ADDRESS";
	public static final String DATAATT_CONTACTPHYSICALADDRESS_role = "role";
	public static final String DATAATT_CONTACTPHYSICALADDRESS_ROLE = "ROLE";
	public static final String DATAREF_CONTACTPHYSICALADDRESS_contact = "contact";
	public static final String DATAATT_CONTACTEMAIL_email = "email";
	public static final String DATAATT_CONTACTEMAIL_EMAIL = "EMAIL";
	public static final String DATAATT_CONTACTEMAIL_role = "role";
	public static final String DATAATT_CONTACTEMAIL_ROLE = "ROLE";
	public static final String DATAREF_CONTACTEMAIL_contact = "contact";
	public static final String DATAATT_KNOWNWIFI_ssid = "ssid";
	public static final String DATAATT_KNOWNWIFI_SSID = "SSID";
	public static final String DATAATT_KNOWNWIFI_location = "location";
	public static final String DATAATT_KNOWNWIFI_LOCATION = "LOCATION";
	public static final String DATAREF_KNOWNWIFI_detectedWifis = "detectedWifis";
	public static final String DATAATT_WIFIACCESSPOINT_ssid = "ssid";
	public static final String DATAATT_WIFIACCESSPOINT_SSID = "SSID";
	public static final String DATAATT_WIFIACCESSPOINT_Location = "Location";
	public static final String DATAATT_WIFIACCESSPOINT_LOCATION = "LOCATION";
	public static final String DATAREF_WIFIACCESSPOINT_detectedWifis = "detectedWifis";
	public static final String DATAATT_DETECTEDWIFI_startDetectionDate = "startDetectionDate";
	public static final String DATAATT_DETECTEDWIFI_STARTDETECTIONDATE = "STARTDETECTIONDATE";
	public static final String DATAATT_DETECTEDWIFI_endDetectionDate = "endDetectionDate";
	public static final String DATAATT_DETECTEDWIFI_ENDDETECTIONDATE = "ENDDETECTIONDATE";
	public static final String DATAATT_DETECTEDWIFI_hasConnected = "hasConnected";
	public static final String DATAATT_DETECTEDWIFI_HASCONNECTED = "HASCONNECTED";
	public static final String DATAATT_DETECTEDWIFI_ssid = "ssid";
	public static final String DATAATT_DETECTEDWIFI_SSID = "SSID";
	public static final String DATAREF_DETECTEDWIFI_accessPoints = "accessPoints";
	public static final String DATAREF_DETECTEDWIFI_knownWifi = "knownWifi";
	public static final String DATAATT_GEOLOCATION_date = "date";
	public static final String DATAATT_GEOLOCATION_DATE = "DATE";
	public static final String DATAATT_GEOLOCATION_position = "position";
	public static final String DATAATT_GEOLOCATION_POSITION = "POSITION";
	public static final String DATAATT_GEOLOCATION_precision = "precision";
	public static final String DATAATT_GEOLOCATION_PRECISION = "PRECISION";
	public static final String DATAATT_GEOLOCATION_altitude = "altitude";
	public static final String DATAATT_GEOLOCATION_ALTITUDE = "ALTITUDE";
	public static final String DATAATT_CALENDAREVENT_EventLabel = "EventLabel";
	public static final String DATAATT_CALENDAREVENT_EVENTLABEL = "EVENTLABEL";
	public static final String DATAATT_CALENDAREVENT_startDate = "startDate";
	public static final String DATAATT_CALENDAREVENT_STARTDATE = "STARTDATE";
	public static final String DATAATT_CALENDAREVENT_endDate = "endDate";
	public static final String DATAATT_CALENDAREVENT_ENDDATE = "ENDDATE";
	public static final String DATAATT_CALENDAREVENT_place = "place";
	public static final String DATAATT_CALENDAREVENT_PLACE = "PLACE";
	public static final String DATAATT_CALENDAREVENT_participants = "participants";
	public static final String DATAATT_CALENDAREVENT_PARTICIPANTS = "PARTICIPANTS";



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
		 	if (name.equals(DATACLASSIFIER_APPLICATIONUSAGESTATSS)) {
				applicationUsageStatss = readApplicationUsageStatss(parser,DATACLASSIFIER_APPLICATIONUSAGESTATSS);
	            // applicationUsageStatss.addAll(readApplicationUsageStatss(parser,DATACLASSIFIER_APPLICATIONUSAGESTATSS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS)) {
				mobilePrivacyProfilerDB_metadatas = readMobilePrivacyProfilerDB_metadatas(parser,DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS);
	            // mobilePrivacyProfilerDB_metadatas.addAll(readMobilePrivacyProfilerDB_metadatas(parser,DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_IDENTITYS)) {
				identitys = readIdentitys(parser,DATACLASSIFIER_IDENTITYS);
	            // identitys.addAll(readIdentitys(parser,DATACLASSIFIER_IDENTITYS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CONTACTS)) {
				contacts = readContacts(parser,DATACLASSIFIER_CONTACTS);
	            // contacts.addAll(readContacts(parser,DATACLASSIFIER_CONTACTS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CONTACTPHONENUMBERS)) {
				contactPhoneNumbers = readContactPhoneNumbers(parser,DATACLASSIFIER_CONTACTPHONENUMBERS);
	            // contactPhoneNumbers.addAll(readContactPhoneNumbers(parser,DATACLASSIFIER_CONTACTPHONENUMBERS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CONTACTPHYSICALADDRESSS)) {
				contactPhysicalAddresss = readContactPhysicalAddresss(parser,DATACLASSIFIER_CONTACTPHYSICALADDRESSS);
	            // contactPhysicalAddresss.addAll(readContactPhysicalAddresss(parser,DATACLASSIFIER_CONTACTPHYSICALADDRESSS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CONTACTEMAILS)) {
				contactEmails = readContactEmails(parser,DATACLASSIFIER_CONTACTEMAILS);
	            // contactEmails.addAll(readContactEmails(parser,DATACLASSIFIER_CONTACTEMAILS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_KNOWNWIFIS)) {
				knownWifis = readKnownWifis(parser,DATACLASSIFIER_KNOWNWIFIS);
	            // knownWifis.addAll(readKnownWifis(parser,DATACLASSIFIER_KNOWNWIFIS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_WIFIACCESSPOINTS)) {
				wifiAccessPoints = readWifiAccessPoints(parser,DATACLASSIFIER_WIFIACCESSPOINTS);
	            // wifiAccessPoints.addAll(readWifiAccessPoints(parser,DATACLASSIFIER_WIFIACCESSPOINTS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_DETECTEDWIFIS)) {
				detectedWifis = readDetectedWifis(parser,DATACLASSIFIER_DETECTEDWIFIS);
	            // detectedWifis.addAll(readDetectedWifis(parser,DATACLASSIFIER_DETECTEDWIFIS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_GEOLOCATIONS)) {
				geolocations = readGeolocations(parser,DATACLASSIFIER_GEOLOCATIONS);
	            // geolocations.addAll(readGeolocations(parser,DATACLASSIFIER_GEOLOCATIONS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CALENDAREVENTS)) {
				calendarEvents = readCalendarEvents(parser,DATACLASSIFIER_CALENDAREVENTS);
	            // calendarEvents.addAll(readCalendarEvents(parser,DATACLASSIFIER_CALENDAREVENTS));
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
     * parser for a group of ApplicationUsageStats
     */
	List<ApplicationUsageStats> readApplicationUsageStatss(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<ApplicationUsageStats> entries = new ArrayList<ApplicationUsageStats>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_APPLICATIONUSAGESTATS)) {
	            entries.add(readApplicationUsageStats(parser));
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
	/**
     * parser for a group of Identity
     */
	List<Identity> readIdentitys(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<Identity> entries = new ArrayList<Identity>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_IDENTITY)) {
	            entries.add(readIdentity(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of Contact
     */
	List<Contact> readContacts(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<Contact> entries = new ArrayList<Contact>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_CONTACT)) {
	            entries.add(readContact(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of ContactPhoneNumber
     */
	List<ContactPhoneNumber> readContactPhoneNumbers(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<ContactPhoneNumber> entries = new ArrayList<ContactPhoneNumber>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_CONTACTPHONENUMBER)) {
	            entries.add(readContactPhoneNumber(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of ContactPhysicalAddress
     */
	List<ContactPhysicalAddress> readContactPhysicalAddresss(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<ContactPhysicalAddress> entries = new ArrayList<ContactPhysicalAddress>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_CONTACTPHYSICALADDRESS)) {
	            entries.add(readContactPhysicalAddress(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of ContactEmail
     */
	List<ContactEmail> readContactEmails(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<ContactEmail> entries = new ArrayList<ContactEmail>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_CONTACTEMAIL)) {
	            entries.add(readContactEmail(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of KnownWifi
     */
	List<KnownWifi> readKnownWifis(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<KnownWifi> entries = new ArrayList<KnownWifi>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_KNOWNWIFI)) {
	            entries.add(readKnownWifi(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of WifiAccessPoint
     */
	List<WifiAccessPoint> readWifiAccessPoints(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<WifiAccessPoint> entries = new ArrayList<WifiAccessPoint>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_WIFIACCESSPOINT)) {
	            entries.add(readWifiAccessPoint(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of DetectedWifi
     */
	List<DetectedWifi> readDetectedWifis(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<DetectedWifi> entries = new ArrayList<DetectedWifi>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_DETECTEDWIFI)) {
	            entries.add(readDetectedWifi(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of Geolocation
     */
	List<Geolocation> readGeolocations(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<Geolocation> entries = new ArrayList<Geolocation>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_GEOLOCATION)) {
	            entries.add(readGeolocation(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of CalendarEvent
     */
	List<CalendarEvent> readCalendarEvents(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<CalendarEvent> entries = new ArrayList<CalendarEvent>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_CALENDAREVENT)) {
	            entries.add(readCalendarEvent(parser));
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
			if (currentTagName.equals(DATAREF_APPLICATIONHISTORY_usageStats)) {
				List<ApplicationUsageStats> entries = readApplicationUsageStatss(parser,DATAREF_APPLICATIONHISTORY_usageStats);	
				applicationUsageStatss.addAll(entries); // add for inclusion in the DB
				//result.getUsageStats().addAll(entries);  //  doesn't work and need to be done in the other way round using the opposite
				refCommands.add(new ApplicationHistory_addContainedUsageStats_RefCommand(result,entries));	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	ApplicationUsageStats readApplicationUsageStats(XmlPullParser parser)  throws XmlPullParserException, IOException{
		ApplicationUsageStats result = new ApplicationUsageStats();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_APPLICATIONUSAGESTATS);
    	String currentTagName = parser.getName();
    			
    	xmlId2ApplicationUsageStats.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setTotalTimeInForeground(parser.getAttributeValue(null, DATAATT_APPLICATIONUSAGESTATS_totalTimeInForeground));
		result.setLastTimeUsed(parser.getAttributeValue(null, DATAATT_APPLICATIONUSAGESTATS_lastTimeUsed));
		result.setFirstTimeStamp(parser.getAttributeValue(null, DATAATT_APPLICATIONUSAGESTATS_firstTimeStamp));
		result.setLastTimeStamp(parser.getAttributeValue(null, DATAATT_APPLICATIONUSAGESTATS_lastTimeStamp));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_APPLICATIONUSAGESTATS_application)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_APPLICATIONUSAGESTATS_application);
	            String id = readText(parser);
				refCommands.add(new ApplicationUsageStats_setApplication_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_APPLICATIONUSAGESTATS_application);	    
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
	Identity readIdentity(XmlPullParser parser)  throws XmlPullParserException, IOException{
		Identity result = new Identity();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_IDENTITY);
    	String currentTagName = parser.getName();
    			
    	xmlId2Identity.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setProvider(parser.getAttributeValue(null, DATAATT_IDENTITY_provider));
		result.setLogin(parser.getAttributeValue(null, DATAATT_IDENTITY_login));
		result.setDisplayName(parser.getAttributeValue(null, DATAATT_IDENTITY_displayName));
		result.setAssociatedServices(parser.getAttributeValue(null, DATAATT_IDENTITY_associatedServices));
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
	Contact readContact(XmlPullParser parser)  throws XmlPullParserException, IOException{
		Contact result = new Contact();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_CONTACT);
    	String currentTagName = parser.getName();
    			
    	xmlId2Contact.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setSurname(parser.getAttributeValue(null, DATAATT_CONTACT_surname));
		result.setFirstName(parser.getAttributeValue(null, DATAATT_CONTACT_firstName));
		result.setMiddleName(parser.getAttributeValue(null, DATAATT_CONTACT_middleName));
		result.setLastName(parser.getAttributeValue(null, DATAATT_CONTACT_lastName));
		result.setNamePrefix(parser.getAttributeValue(null, DATAATT_CONTACT_namePrefix));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_CONTACT_phoneNumbers)) {
				List<ContactPhoneNumber> entries = readContactPhoneNumbers(parser,DATAREF_CONTACT_phoneNumbers);	
				contactPhoneNumbers.addAll(entries); // add for inclusion in the DB
				//result.getPhoneNumbers().addAll(entries);  //  doesn't work and need to be done in the other way round using the opposite
				refCommands.add(new Contact_addContainedPhoneNumbers_RefCommand(result,entries));	    
	        } else
			if (currentTagName.equals(DATAREF_CONTACT_physicalAddresses)) {
				List<ContactPhysicalAddress> entries = readContactPhysicalAddresss(parser,DATAREF_CONTACT_physicalAddresses);	
				contactPhysicalAddresss.addAll(entries); // add for inclusion in the DB
				//result.getPhysicalAddresses().addAll(entries);  //  doesn't work and need to be done in the other way round using the opposite
				refCommands.add(new Contact_addContainedPhysicalAddresses_RefCommand(result,entries));	    
	        } else
			if (currentTagName.equals(DATAREF_CONTACT_emails)) {
				List<ContactEmail> entries = readContactEmails(parser,DATAREF_CONTACT_emails);	
				contactEmails.addAll(entries); // add for inclusion in the DB
				//result.getEmails().addAll(entries);  //  doesn't work and need to be done in the other way round using the opposite
				refCommands.add(new Contact_addContainedEmails_RefCommand(result,entries));	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	ContactPhoneNumber readContactPhoneNumber(XmlPullParser parser)  throws XmlPullParserException, IOException{
		ContactPhoneNumber result = new ContactPhoneNumber();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_CONTACTPHONENUMBER);
    	String currentTagName = parser.getName();
    			
    	xmlId2ContactPhoneNumber.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setPhoneNumber(parser.getAttributeValue(null, DATAATT_CONTACTPHONENUMBER_phoneNumber));
		result.setRole(parser.getAttributeValue(null, DATAATT_CONTACTPHONENUMBER_role));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_CONTACTPHONENUMBER_contact)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_CONTACTPHONENUMBER_contact);
	            String id = readText(parser);
				refCommands.add(new ContactPhoneNumber_setContact_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_CONTACTPHONENUMBER_contact);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	ContactPhysicalAddress readContactPhysicalAddress(XmlPullParser parser)  throws XmlPullParserException, IOException{
		ContactPhysicalAddress result = new ContactPhysicalAddress();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_CONTACTPHYSICALADDRESS);
    	String currentTagName = parser.getName();
    			
    	xmlId2ContactPhysicalAddress.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setAddress(parser.getAttributeValue(null, DATAATT_CONTACTPHYSICALADDRESS_address));
		result.setRole(parser.getAttributeValue(null, DATAATT_CONTACTPHYSICALADDRESS_role));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_CONTACTPHYSICALADDRESS_contact)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_CONTACTPHYSICALADDRESS_contact);
	            String id = readText(parser);
				refCommands.add(new ContactPhysicalAddress_setContact_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_CONTACTPHYSICALADDRESS_contact);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	ContactEmail readContactEmail(XmlPullParser parser)  throws XmlPullParserException, IOException{
		ContactEmail result = new ContactEmail();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_CONTACTEMAIL);
    	String currentTagName = parser.getName();
    			
    	xmlId2ContactEmail.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setEmail(parser.getAttributeValue(null, DATAATT_CONTACTEMAIL_email));
		result.setRole(parser.getAttributeValue(null, DATAATT_CONTACTEMAIL_role));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_CONTACTEMAIL_contact)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_CONTACTEMAIL_contact);
	            String id = readText(parser);
				refCommands.add(new ContactEmail_setContact_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_CONTACTEMAIL_contact);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	KnownWifi readKnownWifi(XmlPullParser parser)  throws XmlPullParserException, IOException{
		KnownWifi result = new KnownWifi();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_KNOWNWIFI);
    	String currentTagName = parser.getName();
    			
    	xmlId2KnownWifi.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setSsid(parser.getAttributeValue(null, DATAATT_KNOWNWIFI_ssid));
		result.setLocation(parser.getAttributeValue(null, DATAATT_KNOWNWIFI_location));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
					// TODO deal with ref detectedWifis
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	WifiAccessPoint readWifiAccessPoint(XmlPullParser parser)  throws XmlPullParserException, IOException{
		WifiAccessPoint result = new WifiAccessPoint();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_WIFIACCESSPOINT);
    	String currentTagName = parser.getName();
    			
    	xmlId2WifiAccessPoint.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setSsid(parser.getAttributeValue(null, DATAATT_WIFIACCESSPOINT_ssid));
		result.setLocation(parser.getAttributeValue(null, DATAATT_WIFIACCESSPOINT_Location));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
					// TODO deal with ref detectedWifis
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	DetectedWifi readDetectedWifi(XmlPullParser parser)  throws XmlPullParserException, IOException{
		DetectedWifi result = new DetectedWifi();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_DETECTEDWIFI);
    	String currentTagName = parser.getName();
    			
    	xmlId2DetectedWifi.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setStartDetectionDate(parser.getAttributeValue(null, DATAATT_DETECTEDWIFI_startDetectionDate));
		result.setEndDetectionDate(parser.getAttributeValue(null, DATAATT_DETECTEDWIFI_endDetectionDate));
		// TODO hasConnected = parser.getAttributeValue(null, DATAATT_DETECTEDWIFI_HASCONNECTED);
		result.setSsid(parser.getAttributeValue(null, DATAATT_DETECTEDWIFI_ssid));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
					// TODO deal with ref accessPoints
			if (currentTagName.equals(DATAREF_DETECTEDWIFI_knownWifi)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_DETECTEDWIFI_knownWifi);
	            String id = readText(parser);
				refCommands.add(new DetectedWifi_setKnownWifi_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_DETECTEDWIFI_knownWifi);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	Geolocation readGeolocation(XmlPullParser parser)  throws XmlPullParserException, IOException{
		Geolocation result = new Geolocation();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_GEOLOCATION);
    	String currentTagName = parser.getName();
    			
    	xmlId2Geolocation.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setDate(parser.getAttributeValue(null, DATAATT_GEOLOCATION_date));
		result.setPosition(parser.getAttributeValue(null, DATAATT_GEOLOCATION_position));
		// TODO precision = parser.getAttributeValue(null, DATAATT_GEOLOCATION_PRECISION);
		// TODO altitude = parser.getAttributeValue(null, DATAATT_GEOLOCATION_ALTITUDE);
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
	CalendarEvent readCalendarEvent(XmlPullParser parser)  throws XmlPullParserException, IOException{
		CalendarEvent result = new CalendarEvent();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_CALENDAREVENT);
    	String currentTagName = parser.getName();
    			
    	xmlId2CalendarEvent.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setEventLabel(parser.getAttributeValue(null, DATAATT_CALENDAREVENT_EventLabel));
		result.setStartDate(parser.getAttributeValue(null, DATAATT_CALENDAREVENT_startDate));
		result.setEndDate(parser.getAttributeValue(null, DATAATT_CALENDAREVENT_endDate));
		result.setPlace(parser.getAttributeValue(null, DATAATT_CALENDAREVENT_place));
		result.setParticipants(parser.getAttributeValue(null, DATAATT_CALENDAREVENT_participants));
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
	class ApplicationHistory_addContainedUsageStats_RefCommand extends RefCommand{
		ApplicationHistory container;
		List<ApplicationUsageStats> containedElements;
		
		public ApplicationHistory_addContainedUsageStats_RefCommand(ApplicationHistory container,
				List<ApplicationUsageStats> containedElements) {
			super();
			this.container = container;
			this.containedElements = containedElements;
		}

		@Override
		public void run() {
			for (ApplicationUsageStats element : containedElements) {				
				element.setApplication(container);
				applicationUsageStatssToUpdate.add(element);
			}
		}
		
	}
	class ApplicationUsageStats_setApplication_RefCommand extends RefCommand{
		ApplicationUsageStats self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public ApplicationUsageStats_setApplication_RefCommand(ApplicationUsageStats self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setApplication(parser.xmlId2ApplicationHistory.get(referencedElementID));
			applicationUsageStatssToUpdate.add(self);
		}
	}
	class Contact_addContainedPhoneNumbers_RefCommand extends RefCommand{
		Contact container;
		List<ContactPhoneNumber> containedElements;
		
		public Contact_addContainedPhoneNumbers_RefCommand(Contact container,
				List<ContactPhoneNumber> containedElements) {
			super();
			this.container = container;
			this.containedElements = containedElements;
		}

		@Override
		public void run() {
			for (ContactPhoneNumber element : containedElements) {				
				element.setContact(container);
				contactPhoneNumbersToUpdate.add(element);
			}
		}
		
	}
	class Contact_addContainedPhysicalAddresses_RefCommand extends RefCommand{
		Contact container;
		List<ContactPhysicalAddress> containedElements;
		
		public Contact_addContainedPhysicalAddresses_RefCommand(Contact container,
				List<ContactPhysicalAddress> containedElements) {
			super();
			this.container = container;
			this.containedElements = containedElements;
		}

		@Override
		public void run() {
			for (ContactPhysicalAddress element : containedElements) {				
				element.setContact(container);
				contactPhysicalAddresssToUpdate.add(element);
			}
		}
		
	}
	class Contact_addContainedEmails_RefCommand extends RefCommand{
		Contact container;
		List<ContactEmail> containedElements;
		
		public Contact_addContainedEmails_RefCommand(Contact container,
				List<ContactEmail> containedElements) {
			super();
			this.container = container;
			this.containedElements = containedElements;
		}

		@Override
		public void run() {
			for (ContactEmail element : containedElements) {				
				element.setContact(container);
				contactEmailsToUpdate.add(element);
			}
		}
		
	}
	class ContactPhoneNumber_setContact_RefCommand extends RefCommand{
		ContactPhoneNumber self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public ContactPhoneNumber_setContact_RefCommand(ContactPhoneNumber self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setContact(parser.xmlId2Contact.get(referencedElementID));
			contactPhoneNumbersToUpdate.add(self);
		}
	}
	class ContactPhysicalAddress_setContact_RefCommand extends RefCommand{
		ContactPhysicalAddress self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public ContactPhysicalAddress_setContact_RefCommand(ContactPhysicalAddress self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setContact(parser.xmlId2Contact.get(referencedElementID));
			contactPhysicalAddresssToUpdate.add(self);
		}
	}
	class ContactEmail_setContact_RefCommand extends RefCommand{
		ContactEmail self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public ContactEmail_setContact_RefCommand(ContactEmail self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setContact(parser.xmlId2Contact.get(referencedElementID));
			contactEmailsToUpdate.add(self);
		}
	}
	// class KnownWifi_addDetectedWifis_RefCommand extends RefCommand{
	// class WifiAccessPoint_addDetectedWifis_RefCommand extends RefCommand{
	// class DetectedWifi_addAccessPoints_RefCommand extends RefCommand{
	class DetectedWifi_setKnownWifi_RefCommand extends RefCommand{
		DetectedWifi self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public DetectedWifi_setKnownWifi_RefCommand(DetectedWifi self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setKnownWifi(parser.xmlId2KnownWifi.get(referencedElementID));
			detectedWifisToUpdate.add(self);
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
