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

	List<MobilePrivacyProfilerDB_metadata> mobilePrivacyProfilerDB_metadatas = new ArrayList<MobilePrivacyProfilerDB_metadata>();
	List<ApplicationHistory> applicationHistorys = new ArrayList<ApplicationHistory>();
	List<ApplicationUsageStats> applicationUsageStatss = new ArrayList<ApplicationUsageStats>();
	List<Authentification> authentifications = new ArrayList<Authentification>();
	List<Contact> contacts = new ArrayList<Contact>();
	List<ContactOrganisation> contactOrganisations = new ArrayList<ContactOrganisation>();
	List<ContactIM> contactIMs = new ArrayList<ContactIM>();
	List<ContactEvent> contactEvents = new ArrayList<ContactEvent>();
	List<ContactPhoneNumber> contactPhoneNumbers = new ArrayList<ContactPhoneNumber>();
	List<ContactPhysicalAddress> contactPhysicalAddresss = new ArrayList<ContactPhysicalAddress>();
	List<ContactEmail> contactEmails = new ArrayList<ContactEmail>();
	List<KnownWifi> knownWifis = new ArrayList<KnownWifi>();
	List<LogsWifi> logsWifis = new ArrayList<LogsWifi>();
	List<Geolocation> geolocations = new ArrayList<Geolocation>();
	List<CalendarEvent> calendarEvents = new ArrayList<CalendarEvent>();
	List<PhoneCallLog> phoneCallLogs = new ArrayList<PhoneCallLog>();
	List<Cell> cells = new ArrayList<Cell>();
	List<OtherCellData> otherCellDatas = new ArrayList<OtherCellData>();
	List<CdmaCellData> cdmaCellDatas = new ArrayList<CdmaCellData>();
	List<NeighboringCellHistory> neighboringCellHistorys = new ArrayList<NeighboringCellHistory>();
	List<BluetoothDevice> bluetoothDevices = new ArrayList<BluetoothDevice>();
	List<BluetoothLog> bluetoothLogs = new ArrayList<BluetoothLog>();
	List<SMS> sMSs = new ArrayList<SMS>();
	List<BatteryUsage> batteryUsages = new ArrayList<BatteryUsage>();
	List<WebHistory> webHistorys = new ArrayList<WebHistory>();
	Set<MobilePrivacyProfilerDB_metadata> mobilePrivacyProfilerDB_metadatasToUpdate = new HashSet<MobilePrivacyProfilerDB_metadata>();
	Set<ApplicationHistory> applicationHistorysToUpdate = new HashSet<ApplicationHistory>();
	Set<ApplicationUsageStats> applicationUsageStatssToUpdate = new HashSet<ApplicationUsageStats>();
	Set<Authentification> authentificationsToUpdate = new HashSet<Authentification>();
	Set<Contact> contactsToUpdate = new HashSet<Contact>();
	Set<ContactOrganisation> contactOrganisationsToUpdate = new HashSet<ContactOrganisation>();
	Set<ContactIM> contactIMsToUpdate = new HashSet<ContactIM>();
	Set<ContactEvent> contactEventsToUpdate = new HashSet<ContactEvent>();
	Set<ContactPhoneNumber> contactPhoneNumbersToUpdate = new HashSet<ContactPhoneNumber>();
	Set<ContactPhysicalAddress> contactPhysicalAddresssToUpdate = new HashSet<ContactPhysicalAddress>();
	Set<ContactEmail> contactEmailsToUpdate = new HashSet<ContactEmail>();
	Set<KnownWifi> knownWifisToUpdate = new HashSet<KnownWifi>();
	Set<LogsWifi> logsWifisToUpdate = new HashSet<LogsWifi>();
	Set<Geolocation> geolocationsToUpdate = new HashSet<Geolocation>();
	Set<CalendarEvent> calendarEventsToUpdate = new HashSet<CalendarEvent>();
	Set<PhoneCallLog> phoneCallLogsToUpdate = new HashSet<PhoneCallLog>();
	Set<Cell> cellsToUpdate = new HashSet<Cell>();
	Set<OtherCellData> otherCellDatasToUpdate = new HashSet<OtherCellData>();
	Set<CdmaCellData> cdmaCellDatasToUpdate = new HashSet<CdmaCellData>();
	Set<NeighboringCellHistory> neighboringCellHistorysToUpdate = new HashSet<NeighboringCellHistory>();
	Set<BluetoothDevice> bluetoothDevicesToUpdate = new HashSet<BluetoothDevice>();
	Set<BluetoothLog> bluetoothLogsToUpdate = new HashSet<BluetoothLog>();
	Set<SMS> sMSsToUpdate = new HashSet<SMS>();
	Set<BatteryUsage> batteryUsagesToUpdate = new HashSet<BatteryUsage>();
	Set<WebHistory> webHistorysToUpdate = new HashSet<WebHistory>();
	Hashtable<String, MobilePrivacyProfilerDB_metadata> xmlId2MobilePrivacyProfilerDB_metadata = new Hashtable<String, MobilePrivacyProfilerDB_metadata>();
	Hashtable<String, ApplicationHistory> xmlId2ApplicationHistory = new Hashtable<String, ApplicationHistory>();
	Hashtable<String, ApplicationUsageStats> xmlId2ApplicationUsageStats = new Hashtable<String, ApplicationUsageStats>();
	Hashtable<String, Authentification> xmlId2Authentification = new Hashtable<String, Authentification>();
	Hashtable<String, Contact> xmlId2Contact = new Hashtable<String, Contact>();
	Hashtable<String, ContactOrganisation> xmlId2ContactOrganisation = new Hashtable<String, ContactOrganisation>();
	Hashtable<String, ContactIM> xmlId2ContactIM = new Hashtable<String, ContactIM>();
	Hashtable<String, ContactEvent> xmlId2ContactEvent = new Hashtable<String, ContactEvent>();
	Hashtable<String, ContactPhoneNumber> xmlId2ContactPhoneNumber = new Hashtable<String, ContactPhoneNumber>();
	Hashtable<String, ContactPhysicalAddress> xmlId2ContactPhysicalAddress = new Hashtable<String, ContactPhysicalAddress>();
	Hashtable<String, ContactEmail> xmlId2ContactEmail = new Hashtable<String, ContactEmail>();
	Hashtable<String, KnownWifi> xmlId2KnownWifi = new Hashtable<String, KnownWifi>();
	Hashtable<String, LogsWifi> xmlId2LogsWifi = new Hashtable<String, LogsWifi>();
	Hashtable<String, Geolocation> xmlId2Geolocation = new Hashtable<String, Geolocation>();
	Hashtable<String, CalendarEvent> xmlId2CalendarEvent = new Hashtable<String, CalendarEvent>();
	Hashtable<String, PhoneCallLog> xmlId2PhoneCallLog = new Hashtable<String, PhoneCallLog>();
	Hashtable<String, Cell> xmlId2Cell = new Hashtable<String, Cell>();
	Hashtable<String, OtherCellData> xmlId2OtherCellData = new Hashtable<String, OtherCellData>();
	Hashtable<String, CdmaCellData> xmlId2CdmaCellData = new Hashtable<String, CdmaCellData>();
	Hashtable<String, NeighboringCellHistory> xmlId2NeighboringCellHistory = new Hashtable<String, NeighboringCellHistory>();
	Hashtable<String, BluetoothDevice> xmlId2BluetoothDevice = new Hashtable<String, BluetoothDevice>();
	Hashtable<String, BluetoothLog> xmlId2BluetoothLog = new Hashtable<String, BluetoothLog>();
	Hashtable<String, SMS> xmlId2SMS = new Hashtable<String, SMS>();
	Hashtable<String, BatteryUsage> xmlId2BatteryUsage = new Hashtable<String, BatteryUsage>();
	Hashtable<String, WebHistory> xmlId2WebHistory = new Hashtable<String, WebHistory>();

	// minimize memory footprint by using static Strings
    public static final String ID_STRING = "id";

	public static final String DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS = "MOBILEPRIVACYPROFILERDB_METADATAS";
	public static final String DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATA  = "MOBILEPRIVACYPROFILERDB_METADATA";
	public static final String DATACLASSIFIER_APPLICATIONHISTORYS = "APPLICATIONHISTORYS";
	public static final String DATACLASSIFIER_APPLICATIONHISTORY  = "APPLICATIONHISTORY";
	public static final String DATACLASSIFIER_APPLICATIONUSAGESTATSS = "APPLICATIONUSAGESTATSS";
	public static final String DATACLASSIFIER_APPLICATIONUSAGESTATS  = "APPLICATIONUSAGESTATS";
	public static final String DATACLASSIFIER_AUTHENTIFICATIONS = "AUTHENTIFICATIONS";
	public static final String DATACLASSIFIER_AUTHENTIFICATION  = "AUTHENTIFICATION";
	public static final String DATACLASSIFIER_CONTACTS = "CONTACTS";
	public static final String DATACLASSIFIER_CONTACT  = "CONTACT";
	public static final String DATACLASSIFIER_CONTACTORGANISATIONS = "CONTACTORGANISATIONS";
	public static final String DATACLASSIFIER_CONTACTORGANISATION  = "CONTACTORGANISATION";
	public static final String DATACLASSIFIER_CONTACTIMS = "CONTACTIMS";
	public static final String DATACLASSIFIER_CONTACTIM  = "CONTACTIM";
	public static final String DATACLASSIFIER_CONTACTEVENTS = "CONTACTEVENTS";
	public static final String DATACLASSIFIER_CONTACTEVENT  = "CONTACTEVENT";
	public static final String DATACLASSIFIER_CONTACTPHONENUMBERS = "CONTACTPHONENUMBERS";
	public static final String DATACLASSIFIER_CONTACTPHONENUMBER  = "CONTACTPHONENUMBER";
	public static final String DATACLASSIFIER_CONTACTPHYSICALADDRESSS = "CONTACTPHYSICALADDRESSS";
	public static final String DATACLASSIFIER_CONTACTPHYSICALADDRESS  = "CONTACTPHYSICALADDRESS";
	public static final String DATACLASSIFIER_CONTACTEMAILS = "CONTACTEMAILS";
	public static final String DATACLASSIFIER_CONTACTEMAIL  = "CONTACTEMAIL";
	public static final String DATACLASSIFIER_KNOWNWIFIS = "KNOWNWIFIS";
	public static final String DATACLASSIFIER_KNOWNWIFI  = "KNOWNWIFI";
	public static final String DATACLASSIFIER_LOGSWIFIS = "LOGSWIFIS";
	public static final String DATACLASSIFIER_LOGSWIFI  = "LOGSWIFI";
	public static final String DATACLASSIFIER_GEOLOCATIONS = "GEOLOCATIONS";
	public static final String DATACLASSIFIER_GEOLOCATION  = "GEOLOCATION";
	public static final String DATACLASSIFIER_CALENDAREVENTS = "CALENDAREVENTS";
	public static final String DATACLASSIFIER_CALENDAREVENT  = "CALENDAREVENT";
	public static final String DATACLASSIFIER_PHONECALLLOGS = "PHONECALLLOGS";
	public static final String DATACLASSIFIER_PHONECALLLOG  = "PHONECALLLOG";
	public static final String DATACLASSIFIER_CELLS = "CELLS";
	public static final String DATACLASSIFIER_CELL  = "CELL";
	public static final String DATACLASSIFIER_OTHERCELLDATAS = "OTHERCELLDATAS";
	public static final String DATACLASSIFIER_OTHERCELLDATA  = "OTHERCELLDATA";
	public static final String DATACLASSIFIER_CDMACELLDATAS = "CDMACELLDATAS";
	public static final String DATACLASSIFIER_CDMACELLDATA  = "CDMACELLDATA";
	public static final String DATACLASSIFIER_NEIGHBORINGCELLHISTORYS = "NEIGHBORINGCELLHISTORYS";
	public static final String DATACLASSIFIER_NEIGHBORINGCELLHISTORY  = "NEIGHBORINGCELLHISTORY";
	public static final String DATACLASSIFIER_BLUETOOTHDEVICES = "BLUETOOTHDEVICES";
	public static final String DATACLASSIFIER_BLUETOOTHDEVICE  = "BLUETOOTHDEVICE";
	public static final String DATACLASSIFIER_BLUETOOTHLOGS = "BLUETOOTHLOGS";
	public static final String DATACLASSIFIER_BLUETOOTHLOG  = "BLUETOOTHLOG";
	public static final String DATACLASSIFIER_SMSS = "SMSS";
	public static final String DATACLASSIFIER_SMS  = "SMS";
	public static final String DATACLASSIFIER_BATTERYUSAGES = "BATTERYUSAGES";
	public static final String DATACLASSIFIER_BATTERYUSAGE  = "BATTERYUSAGE";
	public static final String DATACLASSIFIER_WEBHISTORYS = "WEBHISTORYS";
	public static final String DATACLASSIFIER_WEBHISTORY  = "WEBHISTORY";

	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_lastTransmissionDate = "lastTransmissionDate";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTTRANSMISSIONDATE = "LASTTRANSMISSIONDATE";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_lastScanInstalledApplications = "lastScanInstalledApplications";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTSCANINSTALLEDAPPLICATIONS = "LASTSCANINSTALLEDAPPLICATIONS";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_lastScanAppUsage = "lastScanAppUsage";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTSCANAPPUSAGE = "LASTSCANAPPUSAGE";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_lastSmsScan = "lastSmsScan";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTSMSSCAN = "LASTSMSSCAN";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_lastCallScan = "lastCallScan";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTCALLSCAN = "LASTCALLSCAN";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_userId = "userId";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_USERID = "USERID";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_lastContactScan = "lastContactScan";
	public static final String DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTCONTACTSCAN = "LASTCONTACTSCAN";
	public static final String DATAATT_APPLICATIONHISTORY_appName = "appName";
	public static final String DATAATT_APPLICATIONHISTORY_APPNAME = "APPNAME";
	public static final String DATAATT_APPLICATIONHISTORY_packageName = "packageName";
	public static final String DATAATT_APPLICATIONHISTORY_PACKAGENAME = "PACKAGENAME";
	public static final String DATAATT_APPLICATIONHISTORY_userId = "userId";
	public static final String DATAATT_APPLICATIONHISTORY_USERID = "USERID";
	public static final String DATAREF_APPLICATIONHISTORY_usageStats = "usageStats";
	public static final String DATAATT_APPLICATIONUSAGESTATS_totalTimeInForeground = "totalTimeInForeground";
	public static final String DATAATT_APPLICATIONUSAGESTATS_TOTALTIMEINFOREGROUND = "TOTALTIMEINFOREGROUND";
	public static final String DATAATT_APPLICATIONUSAGESTATS_lastTimeUsed = "lastTimeUsed";
	public static final String DATAATT_APPLICATIONUSAGESTATS_LASTTIMEUSED = "LASTTIMEUSED";
	public static final String DATAATT_APPLICATIONUSAGESTATS_firstTimeStamp = "firstTimeStamp";
	public static final String DATAATT_APPLICATIONUSAGESTATS_FIRSTTIMESTAMP = "FIRSTTIMESTAMP";
	public static final String DATAATT_APPLICATIONUSAGESTATS_lastTimeStamp = "lastTimeStamp";
	public static final String DATAATT_APPLICATIONUSAGESTATS_LASTTIMESTAMP = "LASTTIMESTAMP";
	public static final String DATAATT_APPLICATIONUSAGESTATS_requestedInterval = "requestedInterval";
	public static final String DATAATT_APPLICATIONUSAGESTATS_REQUESTEDINTERVAL = "REQUESTEDINTERVAL";
	public static final String DATAATT_APPLICATIONUSAGESTATS_userId = "userId";
	public static final String DATAATT_APPLICATIONUSAGESTATS_USERID = "USERID";
	public static final String DATAREF_APPLICATIONUSAGESTATS_application = "application";
	public static final String DATAATT_AUTHENTIFICATION_packageName = "packageName";
	public static final String DATAATT_AUTHENTIFICATION_PACKAGENAME = "PACKAGENAME";
	public static final String DATAATT_AUTHENTIFICATION_name = "name";
	public static final String DATAATT_AUTHENTIFICATION_NAME = "NAME";
	public static final String DATAATT_AUTHENTIFICATION_type = "type";
	public static final String DATAATT_AUTHENTIFICATION_TYPE = "TYPE";
	public static final String DATAATT_AUTHENTIFICATION_userId = "userId";
	public static final String DATAATT_AUTHENTIFICATION_USERID = "USERID";
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
	public static final String DATAATT_CONTACT_displayName = "displayName";
	public static final String DATAATT_CONTACT_DISPLAYNAME = "DISPLAYNAME";
	public static final String DATAATT_CONTACT_nameSuffix = "nameSuffix";
	public static final String DATAATT_CONTACT_NAMESUFFIX = "NAMESUFFIX";
	public static final String DATAATT_CONTACT_nickname = "nickname";
	public static final String DATAATT_CONTACT_NICKNAME = "NICKNAME";
	public static final String DATAATT_CONTACT_relation = "relation";
	public static final String DATAATT_CONTACT_RELATION = "RELATION";
	public static final String DATAATT_CONTACT_website = "website";
	public static final String DATAATT_CONTACT_WEBSITE = "WEBSITE";
	public static final String DATAATT_CONTACT_scanTimeStamp = "scanTimeStamp";
	public static final String DATAATT_CONTACT_SCANTIMESTAMP = "SCANTIMESTAMP";
	public static final String DATAATT_CONTACT_userId = "userId";
	public static final String DATAATT_CONTACT_USERID = "USERID";
	public static final String DATAREF_CONTACT_phoneNumbers = "phoneNumbers";
	public static final String DATAREF_CONTACT_physicalAddresses = "physicalAddresses";
	public static final String DATAREF_CONTACT_emails = "emails";
	public static final String DATAREF_CONTACT_contactOrganisation = "contactOrganisation";
	public static final String DATAREF_CONTACT_contactIM = "contactIM";
	public static final String DATAREF_CONTACT_contactEvent = "contactEvent";
	public static final String DATAATT_CONTACTORGANISATION_company = "company";
	public static final String DATAATT_CONTACTORGANISATION_COMPANY = "COMPANY";
	public static final String DATAATT_CONTACTORGANISATION_title = "title";
	public static final String DATAATT_CONTACTORGANISATION_TITLE = "TITLE";
	public static final String DATAATT_CONTACTORGANISATION_userId = "userId";
	public static final String DATAATT_CONTACTORGANISATION_USERID = "USERID";
	public static final String DATAREF_CONTACTORGANISATION_referencedContact = "referencedContact";
	public static final String DATAATT_CONTACTIM_protocole = "protocole";
	public static final String DATAATT_CONTACTIM_PROTOCOLE = "PROTOCOLE";
	public static final String DATAATT_CONTACTIM_imId = "imId";
	public static final String DATAATT_CONTACTIM_IMID = "IMID";
	public static final String DATAATT_CONTACTIM_userId = "userId";
	public static final String DATAATT_CONTACTIM_USERID = "USERID";
	public static final String DATAREF_CONTACTIM_contact = "contact";
	public static final String DATAATT_CONTACTEVENT_startDate = "startDate";
	public static final String DATAATT_CONTACTEVENT_STARTDATE = "STARTDATE";
	public static final String DATAATT_CONTACTEVENT_type = "type";
	public static final String DATAATT_CONTACTEVENT_TYPE = "TYPE";
	public static final String DATAATT_CONTACTEVENT_userId = "userId";
	public static final String DATAATT_CONTACTEVENT_USERID = "USERID";
	public static final String DATAREF_CONTACTEVENT_contact = "contact";
	public static final String DATAATT_CONTACTPHONENUMBER_phoneNumber = "phoneNumber";
	public static final String DATAATT_CONTACTPHONENUMBER_PHONENUMBER = "PHONENUMBER";
	public static final String DATAATT_CONTACTPHONENUMBER_role = "role";
	public static final String DATAATT_CONTACTPHONENUMBER_ROLE = "ROLE";
	public static final String DATAATT_CONTACTPHONENUMBER_userId = "userId";
	public static final String DATAATT_CONTACTPHONENUMBER_USERID = "USERID";
	public static final String DATAREF_CONTACTPHONENUMBER_contact = "contact";
	public static final String DATAATT_CONTACTPHYSICALADDRESS_address = "address";
	public static final String DATAATT_CONTACTPHYSICALADDRESS_ADDRESS = "ADDRESS";
	public static final String DATAATT_CONTACTPHYSICALADDRESS_role = "role";
	public static final String DATAATT_CONTACTPHYSICALADDRESS_ROLE = "ROLE";
	public static final String DATAATT_CONTACTPHYSICALADDRESS_userId = "userId";
	public static final String DATAATT_CONTACTPHYSICALADDRESS_USERID = "USERID";
	public static final String DATAREF_CONTACTPHYSICALADDRESS_contact = "contact";
	public static final String DATAATT_CONTACTEMAIL_email = "email";
	public static final String DATAATT_CONTACTEMAIL_EMAIL = "EMAIL";
	public static final String DATAATT_CONTACTEMAIL_role = "role";
	public static final String DATAATT_CONTACTEMAIL_ROLE = "ROLE";
	public static final String DATAATT_CONTACTEMAIL_userId = "userId";
	public static final String DATAATT_CONTACTEMAIL_USERID = "USERID";
	public static final String DATAREF_CONTACTEMAIL_contact = "contact";
	public static final String DATAATT_KNOWNWIFI_ssid = "ssid";
	public static final String DATAATT_KNOWNWIFI_SSID = "SSID";
	public static final String DATAATT_KNOWNWIFI_bssid = "bssid";
	public static final String DATAATT_KNOWNWIFI_BSSID = "BSSID";
	public static final String DATAATT_KNOWNWIFI_isConfiguredWifi = "isConfiguredWifi";
	public static final String DATAATT_KNOWNWIFI_ISCONFIGUREDWIFI = "ISCONFIGUREDWIFI";
	public static final String DATAATT_KNOWNWIFI_userId = "userId";
	public static final String DATAATT_KNOWNWIFI_USERID = "USERID";
	public static final String DATAREF_KNOWNWIFI_logsWifi = "logsWifi";
	public static final String DATAATT_LOGSWIFI_timeStamp = "timeStamp";
	public static final String DATAATT_LOGSWIFI_TIMESTAMP = "TIMESTAMP";
	public static final String DATAATT_LOGSWIFI_userId = "userId";
	public static final String DATAATT_LOGSWIFI_USERID = "USERID";
	public static final String DATAREF_LOGSWIFI_knownWifi = "knownWifi";
	public static final String DATAATT_GEOLOCATION_date = "date";
	public static final String DATAATT_GEOLOCATION_DATE = "DATE";
	public static final String DATAATT_GEOLOCATION_latitude = "latitude";
	public static final String DATAATT_GEOLOCATION_LATITUDE = "LATITUDE";
	public static final String DATAATT_GEOLOCATION_longitude = "longitude";
	public static final String DATAATT_GEOLOCATION_LONGITUDE = "LONGITUDE";
	public static final String DATAATT_GEOLOCATION_precision = "precision";
	public static final String DATAATT_GEOLOCATION_PRECISION = "PRECISION";
	public static final String DATAATT_GEOLOCATION_altitude = "altitude";
	public static final String DATAATT_GEOLOCATION_ALTITUDE = "ALTITUDE";
	public static final String DATAATT_GEOLOCATION_verticalPrecision = "verticalPrecision";
	public static final String DATAATT_GEOLOCATION_VERTICALPRECISION = "VERTICALPRECISION";
	public static final String DATAATT_GEOLOCATION_userId = "userId";
	public static final String DATAATT_GEOLOCATION_USERID = "USERID";
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
	public static final String DATAATT_CALENDAREVENT_eventId = "eventId";
	public static final String DATAATT_CALENDAREVENT_EVENTID = "EVENTID";
	public static final String DATAATT_CALENDAREVENT_userId = "userId";
	public static final String DATAATT_CALENDAREVENT_USERID = "USERID";
	public static final String DATAATT_PHONECALLLOG_phoneNumber = "phoneNumber";
	public static final String DATAATT_PHONECALLLOG_PHONENUMBER = "PHONENUMBER";
	public static final String DATAATT_PHONECALLLOG_date = "date";
	public static final String DATAATT_PHONECALLLOG_DATE = "DATE";
	public static final String DATAATT_PHONECALLLOG_duration = "duration";
	public static final String DATAATT_PHONECALLLOG_DURATION = "DURATION";
	public static final String DATAATT_PHONECALLLOG_callType = "callType";
	public static final String DATAATT_PHONECALLLOG_CALLTYPE = "CALLTYPE";
	public static final String DATAATT_PHONECALLLOG_userId = "userId";
	public static final String DATAATT_PHONECALLLOG_USERID = "USERID";
	public static final String DATAATT_CELL_cellId = "cellId";
	public static final String DATAATT_CELL_CELLID = "CELLID";
	public static final String DATAATT_CELL_userId = "userId";
	public static final String DATAATT_CELL_USERID = "USERID";
	public static final String DATAREF_CELL_history = "history";
	public static final String DATAREF_CELL_cdmaposition = "cdmaposition";
	public static final String DATAREF_CELL_otherPosition = "otherPosition";
	public static final String DATAATT_OTHERCELLDATA_lacTac = "lacTac";
	public static final String DATAATT_OTHERCELLDATA_LACTAC = "LACTAC";
	public static final String DATAATT_OTHERCELLDATA_type = "type";
	public static final String DATAATT_OTHERCELLDATA_TYPE = "TYPE";
	public static final String DATAATT_OTHERCELLDATA_userId = "userId";
	public static final String DATAATT_OTHERCELLDATA_USERID = "USERID";
	public static final String DATAATT_OTHERCELLDATA_Mcc = "Mcc";
	public static final String DATAATT_OTHERCELLDATA_MCC = "MCC";
	public static final String DATAATT_OTHERCELLDATA_Mnc = "Mnc";
	public static final String DATAATT_OTHERCELLDATA_MNC = "MNC";
	public static final String DATAREF_OTHERCELLDATA_identity = "identity";
	public static final String DATAATT_CDMACELLDATA_longitude = "longitude";
	public static final String DATAATT_CDMACELLDATA_LONGITUDE = "LONGITUDE";
	public static final String DATAATT_CDMACELLDATA_latitude = "latitude";
	public static final String DATAATT_CDMACELLDATA_LATITUDE = "LATITUDE";
	public static final String DATAATT_CDMACELLDATA_userId = "userId";
	public static final String DATAATT_CDMACELLDATA_USERID = "USERID";
	public static final String DATAREF_CDMACELLDATA_identity = "identity";
	public static final String DATAATT_NEIGHBORINGCELLHISTORY_date = "date";
	public static final String DATAATT_NEIGHBORINGCELLHISTORY_DATE = "DATE";
	public static final String DATAATT_NEIGHBORINGCELLHISTORY_strength = "strength";
	public static final String DATAATT_NEIGHBORINGCELLHISTORY_STRENGTH = "STRENGTH";
	public static final String DATAATT_NEIGHBORINGCELLHISTORY_userId = "userId";
	public static final String DATAATT_NEIGHBORINGCELLHISTORY_USERID = "USERID";
	public static final String DATAREF_NEIGHBORINGCELLHISTORY_cells = "cells";
	public static final String DATAATT_BLUETOOTHDEVICE_mac = "mac";
	public static final String DATAATT_BLUETOOTHDEVICE_MAC = "MAC";
	public static final String DATAATT_BLUETOOTHDEVICE_name = "name";
	public static final String DATAATT_BLUETOOTHDEVICE_NAME = "NAME";
	public static final String DATAATT_BLUETOOTHDEVICE_type = "type";
	public static final String DATAATT_BLUETOOTHDEVICE_TYPE = "TYPE";
	public static final String DATAATT_BLUETOOTHDEVICE_userId = "userId";
	public static final String DATAATT_BLUETOOTHDEVICE_USERID = "USERID";
	public static final String DATAREF_BLUETOOTHDEVICE_bluetoothLog = "bluetoothLog";
	public static final String DATAATT_BLUETOOTHLOG_date = "date";
	public static final String DATAATT_BLUETOOTHLOG_DATE = "DATE";
	public static final String DATAATT_BLUETOOTHLOG_userId = "userId";
	public static final String DATAATT_BLUETOOTHLOG_USERID = "USERID";
	public static final String DATAREF_BLUETOOTHLOG_device = "device";
	public static final String DATAATT_SMS_date = "date";
	public static final String DATAATT_SMS_DATE = "DATE";
	public static final String DATAATT_SMS_phoneNumber = "phoneNumber";
	public static final String DATAATT_SMS_PHONENUMBER = "PHONENUMBER";
	public static final String DATAATT_SMS_type = "type";
	public static final String DATAATT_SMS_TYPE = "TYPE";
	public static final String DATAATT_SMS_userId = "userId";
	public static final String DATAATT_SMS_USERID = "USERID";
	public static final String DATAATT_BATTERYUSAGE_date = "date";
	public static final String DATAATT_BATTERYUSAGE_DATE = "DATE";
	public static final String DATAATT_BATTERYUSAGE_level = "level";
	public static final String DATAATT_BATTERYUSAGE_LEVEL = "LEVEL";
	public static final String DATAATT_BATTERYUSAGE_isPugged = "isPugged";
	public static final String DATAATT_BATTERYUSAGE_ISPUGGED = "ISPUGGED";
	public static final String DATAATT_BATTERYUSAGE_plugType = "plugType";
	public static final String DATAATT_BATTERYUSAGE_PLUGTYPE = "PLUGTYPE";
	public static final String DATAATT_BATTERYUSAGE_userId = "userId";
	public static final String DATAATT_BATTERYUSAGE_USERID = "USERID";
	public static final String DATAATT_WEBHISTORY_date = "date";
	public static final String DATAATT_WEBHISTORY_DATE = "DATE";
	public static final String DATAATT_WEBHISTORY_application = "application";
	public static final String DATAATT_WEBHISTORY_APPLICATION = "APPLICATION";
	public static final String DATAATT_WEBHISTORY_hostname = "hostname";
	public static final String DATAATT_WEBHISTORY_HOSTNAME = "HOSTNAME";
	public static final String DATAATT_WEBHISTORY_ipDestination = "ipDestination";
	public static final String DATAATT_WEBHISTORY_IPDESTINATION = "IPDESTINATION";
	public static final String DATAATT_WEBHISTORY_userId = "userId";
	public static final String DATAATT_WEBHISTORY_USERID = "USERID";



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
		 	if (name.equals(DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS)) {
				mobilePrivacyProfilerDB_metadatas = readMobilePrivacyProfilerDB_metadatas(parser,DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS);
	            // mobilePrivacyProfilerDB_metadatas.addAll(readMobilePrivacyProfilerDB_metadatas(parser,DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATAS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_APPLICATIONHISTORYS)) {
				applicationHistorys = readApplicationHistorys(parser,DATACLASSIFIER_APPLICATIONHISTORYS);
	            // applicationHistorys.addAll(readApplicationHistorys(parser,DATACLASSIFIER_APPLICATIONHISTORYS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_APPLICATIONUSAGESTATSS)) {
				applicationUsageStatss = readApplicationUsageStatss(parser,DATACLASSIFIER_APPLICATIONUSAGESTATSS);
	            // applicationUsageStatss.addAll(readApplicationUsageStatss(parser,DATACLASSIFIER_APPLICATIONUSAGESTATSS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_AUTHENTIFICATIONS)) {
				authentifications = readAuthentifications(parser,DATACLASSIFIER_AUTHENTIFICATIONS);
	            // authentifications.addAll(readAuthentifications(parser,DATACLASSIFIER_AUTHENTIFICATIONS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CONTACTS)) {
				contacts = readContacts(parser,DATACLASSIFIER_CONTACTS);
	            // contacts.addAll(readContacts(parser,DATACLASSIFIER_CONTACTS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CONTACTORGANISATIONS)) {
				contactOrganisations = readContactOrganisations(parser,DATACLASSIFIER_CONTACTORGANISATIONS);
	            // contactOrganisations.addAll(readContactOrganisations(parser,DATACLASSIFIER_CONTACTORGANISATIONS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CONTACTIMS)) {
				contactIMs = readContactIMs(parser,DATACLASSIFIER_CONTACTIMS);
	            // contactIMs.addAll(readContactIMs(parser,DATACLASSIFIER_CONTACTIMS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CONTACTEVENTS)) {
				contactEvents = readContactEvents(parser,DATACLASSIFIER_CONTACTEVENTS);
	            // contactEvents.addAll(readContactEvents(parser,DATACLASSIFIER_CONTACTEVENTS));
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
		 	if (name.equals(DATACLASSIFIER_LOGSWIFIS)) {
				logsWifis = readLogsWifis(parser,DATACLASSIFIER_LOGSWIFIS);
	            // logsWifis.addAll(readLogsWifis(parser,DATACLASSIFIER_LOGSWIFIS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_GEOLOCATIONS)) {
				geolocations = readGeolocations(parser,DATACLASSIFIER_GEOLOCATIONS);
	            // geolocations.addAll(readGeolocations(parser,DATACLASSIFIER_GEOLOCATIONS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CALENDAREVENTS)) {
				calendarEvents = readCalendarEvents(parser,DATACLASSIFIER_CALENDAREVENTS);
	            // calendarEvents.addAll(readCalendarEvents(parser,DATACLASSIFIER_CALENDAREVENTS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_PHONECALLLOGS)) {
				phoneCallLogs = readPhoneCallLogs(parser,DATACLASSIFIER_PHONECALLLOGS);
	            // phoneCallLogs.addAll(readPhoneCallLogs(parser,DATACLASSIFIER_PHONECALLLOGS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CELLS)) {
				cells = readCells(parser,DATACLASSIFIER_CELLS);
	            // cells.addAll(readCells(parser,DATACLASSIFIER_CELLS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_OTHERCELLDATAS)) {
				otherCellDatas = readOtherCellDatas(parser,DATACLASSIFIER_OTHERCELLDATAS);
	            // otherCellDatas.addAll(readOtherCellDatas(parser,DATACLASSIFIER_OTHERCELLDATAS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_CDMACELLDATAS)) {
				cdmaCellDatas = readCdmaCellDatas(parser,DATACLASSIFIER_CDMACELLDATAS);
	            // cdmaCellDatas.addAll(readCdmaCellDatas(parser,DATACLASSIFIER_CDMACELLDATAS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_NEIGHBORINGCELLHISTORYS)) {
				neighboringCellHistorys = readNeighboringCellHistorys(parser,DATACLASSIFIER_NEIGHBORINGCELLHISTORYS);
	            // neighboringCellHistorys.addAll(readNeighboringCellHistorys(parser,DATACLASSIFIER_NEIGHBORINGCELLHISTORYS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_BLUETOOTHDEVICES)) {
				bluetoothDevices = readBluetoothDevices(parser,DATACLASSIFIER_BLUETOOTHDEVICES);
	            // bluetoothDevices.addAll(readBluetoothDevices(parser,DATACLASSIFIER_BLUETOOTHDEVICES));
	        } else 
		 	if (name.equals(DATACLASSIFIER_BLUETOOTHLOGS)) {
				bluetoothLogs = readBluetoothLogs(parser,DATACLASSIFIER_BLUETOOTHLOGS);
	            // bluetoothLogs.addAll(readBluetoothLogs(parser,DATACLASSIFIER_BLUETOOTHLOGS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_SMSS)) {
				sMSs = readSMSs(parser,DATACLASSIFIER_SMSS);
	            // sMSs.addAll(readSMSs(parser,DATACLASSIFIER_SMSS));
	        } else 
		 	if (name.equals(DATACLASSIFIER_BATTERYUSAGES)) {
				batteryUsages = readBatteryUsages(parser,DATACLASSIFIER_BATTERYUSAGES);
	            // batteryUsages.addAll(readBatteryUsages(parser,DATACLASSIFIER_BATTERYUSAGES));
	        } else 
		 	if (name.equals(DATACLASSIFIER_WEBHISTORYS)) {
				webHistorys = readWebHistorys(parser,DATACLASSIFIER_WEBHISTORYS);
	            // webHistorys.addAll(readWebHistorys(parser,DATACLASSIFIER_WEBHISTORYS));
	        } else 
			{
	            skip(parser);
	        }
	    }
		
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
     * parser for a group of Authentification
     */
	List<Authentification> readAuthentifications(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<Authentification> entries = new ArrayList<Authentification>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_AUTHENTIFICATION)) {
	            entries.add(readAuthentification(parser));
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
     * parser for a group of ContactOrganisation
     */
	List<ContactOrganisation> readContactOrganisations(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<ContactOrganisation> entries = new ArrayList<ContactOrganisation>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_CONTACTORGANISATION)) {
	            entries.add(readContactOrganisation(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of ContactIM
     */
	List<ContactIM> readContactIMs(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<ContactIM> entries = new ArrayList<ContactIM>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_CONTACTIM)) {
	            entries.add(readContactIM(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of ContactEvent
     */
	List<ContactEvent> readContactEvents(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<ContactEvent> entries = new ArrayList<ContactEvent>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_CONTACTEVENT)) {
	            entries.add(readContactEvent(parser));
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
     * parser for a group of LogsWifi
     */
	List<LogsWifi> readLogsWifis(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<LogsWifi> entries = new ArrayList<LogsWifi>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_LOGSWIFI)) {
	            entries.add(readLogsWifi(parser));
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
	/**
     * parser for a group of PhoneCallLog
     */
	List<PhoneCallLog> readPhoneCallLogs(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<PhoneCallLog> entries = new ArrayList<PhoneCallLog>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_PHONECALLLOG)) {
	            entries.add(readPhoneCallLog(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of Cell
     */
	List<Cell> readCells(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<Cell> entries = new ArrayList<Cell>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_CELL)) {
	            entries.add(readCell(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of OtherCellData
     */
	List<OtherCellData> readOtherCellDatas(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<OtherCellData> entries = new ArrayList<OtherCellData>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_OTHERCELLDATA)) {
	            entries.add(readOtherCellData(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of CdmaCellData
     */
	List<CdmaCellData> readCdmaCellDatas(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<CdmaCellData> entries = new ArrayList<CdmaCellData>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_CDMACELLDATA)) {
	            entries.add(readCdmaCellData(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of NeighboringCellHistory
     */
	List<NeighboringCellHistory> readNeighboringCellHistorys(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<NeighboringCellHistory> entries = new ArrayList<NeighboringCellHistory>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_NEIGHBORINGCELLHISTORY)) {
	            entries.add(readNeighboringCellHistory(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of BluetoothDevice
     */
	List<BluetoothDevice> readBluetoothDevices(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<BluetoothDevice> entries = new ArrayList<BluetoothDevice>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_BLUETOOTHDEVICE)) {
	            entries.add(readBluetoothDevice(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of BluetoothLog
     */
	List<BluetoothLog> readBluetoothLogs(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<BluetoothLog> entries = new ArrayList<BluetoothLog>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_BLUETOOTHLOG)) {
	            entries.add(readBluetoothLog(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of SMS
     */
	List<SMS> readSMSs(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<SMS> entries = new ArrayList<SMS>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_SMS)) {
	            entries.add(readSMS(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of BatteryUsage
     */
	List<BatteryUsage> readBatteryUsages(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<BatteryUsage> entries = new ArrayList<BatteryUsage>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_BATTERYUSAGE)) {
	            entries.add(readBatteryUsage(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}
	/**
     * parser for a group of WebHistory
     */
	List<WebHistory> readWebHistorys(XmlPullParser parser, final String containingTag)  throws XmlPullParserException, IOException{
		ArrayList<WebHistory> entries = new ArrayList<WebHistory>();
		parser.require(XmlPullParser.START_TAG, ns, containingTag);
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
			if (name.equals(DATACLASSIFIER_WEBHISTORY)) {
	            entries.add(readWebHistory(parser));
	        } else {
	            skip(parser);
	        }
	    }
		entries.trimToSize();
		return entries;
	}

	MobilePrivacyProfilerDB_metadata readMobilePrivacyProfilerDB_metadata(XmlPullParser parser)  throws XmlPullParserException, IOException{
		MobilePrivacyProfilerDB_metadata result = new MobilePrivacyProfilerDB_metadata();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_MOBILEPRIVACYPROFILERDB_METADATA);
    	String currentTagName = parser.getName();
    			
    	xmlId2MobilePrivacyProfilerDB_metadata.put(parser.getAttributeValue(null, ID_STRING),result);		
		// TODO lastTransmissionDate = parser.getAttributeValue(null, DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTTRANSMISSIONDATE);
		// TODO lastScanInstalledApplications = parser.getAttributeValue(null, DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTSCANINSTALLEDAPPLICATIONS);
		// TODO lastScanAppUsage = parser.getAttributeValue(null, DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTSCANAPPUSAGE);
		// TODO lastSmsScan = parser.getAttributeValue(null, DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTSMSSCAN);
		// TODO lastCallScan = parser.getAttributeValue(null, DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTCALLSCAN);
		result.setUserId(parser.getAttributeValue(null, DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_userId));
		// TODO lastContactScan = parser.getAttributeValue(null, DATAATT_MOBILEPRIVACYPROFILERDB_METADATA_LASTCONTACTSCAN);
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
	ApplicationHistory readApplicationHistory(XmlPullParser parser)  throws XmlPullParserException, IOException{
		ApplicationHistory result = new ApplicationHistory();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_APPLICATIONHISTORY);
    	String currentTagName = parser.getName();
    			
    	xmlId2ApplicationHistory.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setAppName(parser.getAttributeValue(null, DATAATT_APPLICATIONHISTORY_appName));
		result.setPackageName(parser.getAttributeValue(null, DATAATT_APPLICATIONHISTORY_packageName));
		result.setUserId(parser.getAttributeValue(null, DATAATT_APPLICATIONHISTORY_userId));
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
		// TODO totalTimeInForeground = parser.getAttributeValue(null, DATAATT_APPLICATIONUSAGESTATS_TOTALTIMEINFOREGROUND);
		result.setLastTimeUsed(parser.getAttributeValue(null, DATAATT_APPLICATIONUSAGESTATS_lastTimeUsed));
		result.setFirstTimeStamp(parser.getAttributeValue(null, DATAATT_APPLICATIONUSAGESTATS_firstTimeStamp));
		result.setLastTimeStamp(parser.getAttributeValue(null, DATAATT_APPLICATIONUSAGESTATS_lastTimeStamp));
		// TODO requestedInterval = parser.getAttributeValue(null, DATAATT_APPLICATIONUSAGESTATS_REQUESTEDINTERVAL);
		result.setUserId(parser.getAttributeValue(null, DATAATT_APPLICATIONUSAGESTATS_userId));
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
	Authentification readAuthentification(XmlPullParser parser)  throws XmlPullParserException, IOException{
		Authentification result = new Authentification();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_AUTHENTIFICATION);
    	String currentTagName = parser.getName();
    			
    	xmlId2Authentification.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setPackageName(parser.getAttributeValue(null, DATAATT_AUTHENTIFICATION_packageName));
		result.setName(parser.getAttributeValue(null, DATAATT_AUTHENTIFICATION_name));
		result.setType(parser.getAttributeValue(null, DATAATT_AUTHENTIFICATION_type));
		result.setUserId(parser.getAttributeValue(null, DATAATT_AUTHENTIFICATION_userId));
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
		result.setDisplayName(parser.getAttributeValue(null, DATAATT_CONTACT_displayName));
		result.setNameSuffix(parser.getAttributeValue(null, DATAATT_CONTACT_nameSuffix));
		result.setNickname(parser.getAttributeValue(null, DATAATT_CONTACT_nickname));
		result.setRelation(parser.getAttributeValue(null, DATAATT_CONTACT_relation));
		result.setWebsite(parser.getAttributeValue(null, DATAATT_CONTACT_website));
		// TODO scanTimeStamp = parser.getAttributeValue(null, DATAATT_CONTACT_SCANTIMESTAMP);
		result.setUserId(parser.getAttributeValue(null, DATAATT_CONTACT_userId));
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
					// TODO deal with owned ref contactOrganisation
			if (currentTagName.equals(DATAREF_CONTACT_contactIM)) {
				List<ContactIM> entries = readContactIMs(parser,DATAREF_CONTACT_contactIM);	
				contactIMs.addAll(entries); // add for inclusion in the DB
				//result.getContactIM().addAll(entries);  //  doesn't work and need to be done in the other way round using the opposite
				refCommands.add(new Contact_addContainedContactIM_RefCommand(result,entries));	    
	        } else
			if (currentTagName.equals(DATAREF_CONTACT_contactEvent)) {
				List<ContactEvent> entries = readContactEvents(parser,DATAREF_CONTACT_contactEvent);	
				contactEvents.addAll(entries); // add for inclusion in the DB
				//result.getContactEvent().addAll(entries);  //  doesn't work and need to be done in the other way round using the opposite
				refCommands.add(new Contact_addContainedContactEvent_RefCommand(result,entries));	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	ContactOrganisation readContactOrganisation(XmlPullParser parser)  throws XmlPullParserException, IOException{
		ContactOrganisation result = new ContactOrganisation();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_CONTACTORGANISATION);
    	String currentTagName = parser.getName();
    			
    	xmlId2ContactOrganisation.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setCompany(parser.getAttributeValue(null, DATAATT_CONTACTORGANISATION_company));
		result.setTitle(parser.getAttributeValue(null, DATAATT_CONTACTORGANISATION_title));
		result.setUserId(parser.getAttributeValue(null, DATAATT_CONTACTORGANISATION_userId));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_CONTACTORGANISATION_referencedContact)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_CONTACTORGANISATION_referencedContact);
	            String id = readText(parser);
				refCommands.add(new ContactOrganisation_setReferencedContact_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_CONTACTORGANISATION_referencedContact);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	ContactIM readContactIM(XmlPullParser parser)  throws XmlPullParserException, IOException{
		ContactIM result = new ContactIM();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_CONTACTIM);
    	String currentTagName = parser.getName();
    			
    	xmlId2ContactIM.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setProtocole(parser.getAttributeValue(null, DATAATT_CONTACTIM_protocole));
		result.setImId(parser.getAttributeValue(null, DATAATT_CONTACTIM_imId));
		result.setUserId(parser.getAttributeValue(null, DATAATT_CONTACTIM_userId));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_CONTACTIM_contact)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_CONTACTIM_contact);
	            String id = readText(parser);
				refCommands.add(new ContactIM_setContact_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_CONTACTIM_contact);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	ContactEvent readContactEvent(XmlPullParser parser)  throws XmlPullParserException, IOException{
		ContactEvent result = new ContactEvent();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_CONTACTEVENT);
    	String currentTagName = parser.getName();
    			
    	xmlId2ContactEvent.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setStartDate(parser.getAttributeValue(null, DATAATT_CONTACTEVENT_startDate));
		result.setType(parser.getAttributeValue(null, DATAATT_CONTACTEVENT_type));
		result.setUserId(parser.getAttributeValue(null, DATAATT_CONTACTEVENT_userId));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_CONTACTEVENT_contact)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_CONTACTEVENT_contact);
	            String id = readText(parser);
				refCommands.add(new ContactEvent_setContact_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_CONTACTEVENT_contact);	    
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
		result.setUserId(parser.getAttributeValue(null, DATAATT_CONTACTPHONENUMBER_userId));
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
		result.setUserId(parser.getAttributeValue(null, DATAATT_CONTACTPHYSICALADDRESS_userId));
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
		result.setUserId(parser.getAttributeValue(null, DATAATT_CONTACTEMAIL_userId));
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
		result.setBssid(parser.getAttributeValue(null, DATAATT_KNOWNWIFI_bssid));
		// TODO isConfiguredWifi = parser.getAttributeValue(null, DATAATT_KNOWNWIFI_ISCONFIGUREDWIFI);
		result.setUserId(parser.getAttributeValue(null, DATAATT_KNOWNWIFI_userId));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_KNOWNWIFI_logsWifi)) {
				List<LogsWifi> entries = readLogsWifis(parser,DATAREF_KNOWNWIFI_logsWifi);	
				logsWifis.addAll(entries); // add for inclusion in the DB
				//result.getLogsWifi().addAll(entries);  //  doesn't work and need to be done in the other way round using the opposite
				refCommands.add(new KnownWifi_addContainedLogsWifi_RefCommand(result,entries));	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	LogsWifi readLogsWifi(XmlPullParser parser)  throws XmlPullParserException, IOException{
		LogsWifi result = new LogsWifi();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_LOGSWIFI);
    	String currentTagName = parser.getName();
    			
    	xmlId2LogsWifi.put(parser.getAttributeValue(null, ID_STRING),result);		
		// TODO timeStamp = parser.getAttributeValue(null, DATAATT_LOGSWIFI_TIMESTAMP);
		result.setUserId(parser.getAttributeValue(null, DATAATT_LOGSWIFI_userId));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_LOGSWIFI_knownWifi)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_LOGSWIFI_knownWifi);
	            String id = readText(parser);
				refCommands.add(new LogsWifi_setKnownWifi_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_LOGSWIFI_knownWifi);	    
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
		// TODO date = parser.getAttributeValue(null, DATAATT_GEOLOCATION_DATE);
		result.setLatitude(parser.getAttributeValue(null, DATAATT_GEOLOCATION_latitude));
		result.setLongitude(parser.getAttributeValue(null, DATAATT_GEOLOCATION_longitude));
		result.setPrecision(parser.getAttributeValue(null, DATAATT_GEOLOCATION_precision));
		result.setAltitude(parser.getAttributeValue(null, DATAATT_GEOLOCATION_altitude));
		result.setVerticalPrecision(parser.getAttributeValue(null, DATAATT_GEOLOCATION_verticalPrecision));
		result.setUserId(parser.getAttributeValue(null, DATAATT_GEOLOCATION_userId));
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
		// TODO eventId = parser.getAttributeValue(null, DATAATT_CALENDAREVENT_EVENTID);
		result.setUserId(parser.getAttributeValue(null, DATAATT_CALENDAREVENT_userId));
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
	PhoneCallLog readPhoneCallLog(XmlPullParser parser)  throws XmlPullParserException, IOException{
		PhoneCallLog result = new PhoneCallLog();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_PHONECALLLOG);
    	String currentTagName = parser.getName();
    			
    	xmlId2PhoneCallLog.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setPhoneNumber(parser.getAttributeValue(null, DATAATT_PHONECALLLOG_phoneNumber));
		// TODO date = parser.getAttributeValue(null, DATAATT_PHONECALLLOG_DATE);
		// TODO duration = parser.getAttributeValue(null, DATAATT_PHONECALLLOG_DURATION);
		result.setCallType(parser.getAttributeValue(null, DATAATT_PHONECALLLOG_callType));
		result.setUserId(parser.getAttributeValue(null, DATAATT_PHONECALLLOG_userId));
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
	Cell readCell(XmlPullParser parser)  throws XmlPullParserException, IOException{
		Cell result = new Cell();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_CELL);
    	String currentTagName = parser.getName();
    			
    	xmlId2Cell.put(parser.getAttributeValue(null, ID_STRING),result);		
		// TODO cellId = parser.getAttributeValue(null, DATAATT_CELL_CELLID);
		result.setUserId(parser.getAttributeValue(null, DATAATT_CELL_userId));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_CELL_history)) {
				List<NeighboringCellHistory> entries = readNeighboringCellHistorys(parser,DATAREF_CELL_history);	
				neighboringCellHistorys.addAll(entries); // add for inclusion in the DB
				//result.getHistory().addAll(entries);  //  doesn't work and need to be done in the other way round using the opposite
				refCommands.add(new Cell_addContainedHistory_RefCommand(result,entries));	    
	        } else
					// TODO deal with owned ref cdmaposition
					// TODO deal with owned ref otherPosition
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	OtherCellData readOtherCellData(XmlPullParser parser)  throws XmlPullParserException, IOException{
		OtherCellData result = new OtherCellData();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_OTHERCELLDATA);
    	String currentTagName = parser.getName();
    			
    	xmlId2OtherCellData.put(parser.getAttributeValue(null, ID_STRING),result);		
		// TODO lacTac = parser.getAttributeValue(null, DATAATT_OTHERCELLDATA_LACTAC);
		result.setType(parser.getAttributeValue(null, DATAATT_OTHERCELLDATA_type));
		result.setUserId(parser.getAttributeValue(null, DATAATT_OTHERCELLDATA_userId));
		result.setMcc(parser.getAttributeValue(null, DATAATT_OTHERCELLDATA_Mcc));
		result.setMnc(parser.getAttributeValue(null, DATAATT_OTHERCELLDATA_Mnc));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_OTHERCELLDATA_identity)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_OTHERCELLDATA_identity);
	            String id = readText(parser);
				refCommands.add(new OtherCellData_setIdentity_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_OTHERCELLDATA_identity);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	CdmaCellData readCdmaCellData(XmlPullParser parser)  throws XmlPullParserException, IOException{
		CdmaCellData result = new CdmaCellData();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_CDMACELLDATA);
    	String currentTagName = parser.getName();
    			
    	xmlId2CdmaCellData.put(parser.getAttributeValue(null, ID_STRING),result);		
		// TODO longitude = parser.getAttributeValue(null, DATAATT_CDMACELLDATA_LONGITUDE);
		// TODO latitude = parser.getAttributeValue(null, DATAATT_CDMACELLDATA_LATITUDE);
		result.setUserId(parser.getAttributeValue(null, DATAATT_CDMACELLDATA_userId));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_CDMACELLDATA_identity)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_CDMACELLDATA_identity);
	            String id = readText(parser);
				refCommands.add(new CdmaCellData_setIdentity_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_CDMACELLDATA_identity);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	NeighboringCellHistory readNeighboringCellHistory(XmlPullParser parser)  throws XmlPullParserException, IOException{
		NeighboringCellHistory result = new NeighboringCellHistory();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_NEIGHBORINGCELLHISTORY);
    	String currentTagName = parser.getName();
    			
    	xmlId2NeighboringCellHistory.put(parser.getAttributeValue(null, ID_STRING),result);		
		// TODO date = parser.getAttributeValue(null, DATAATT_NEIGHBORINGCELLHISTORY_DATE);
		// TODO strength = parser.getAttributeValue(null, DATAATT_NEIGHBORINGCELLHISTORY_STRENGTH);
		result.setUserId(parser.getAttributeValue(null, DATAATT_NEIGHBORINGCELLHISTORY_userId));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_NEIGHBORINGCELLHISTORY_cells)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_NEIGHBORINGCELLHISTORY_cells);
	            String id = readText(parser);
				refCommands.add(new NeighboringCellHistory_setCells_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_NEIGHBORINGCELLHISTORY_cells);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	BluetoothDevice readBluetoothDevice(XmlPullParser parser)  throws XmlPullParserException, IOException{
		BluetoothDevice result = new BluetoothDevice();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_BLUETOOTHDEVICE);
    	String currentTagName = parser.getName();
    			
    	xmlId2BluetoothDevice.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setMac(parser.getAttributeValue(null, DATAATT_BLUETOOTHDEVICE_mac));
		result.setName(parser.getAttributeValue(null, DATAATT_BLUETOOTHDEVICE_name));
		// TODO type = parser.getAttributeValue(null, DATAATT_BLUETOOTHDEVICE_TYPE);
		result.setUserId(parser.getAttributeValue(null, DATAATT_BLUETOOTHDEVICE_userId));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_BLUETOOTHDEVICE_bluetoothLog)) {
				List<BluetoothLog> entries = readBluetoothLogs(parser,DATAREF_BLUETOOTHDEVICE_bluetoothLog);	
				bluetoothLogs.addAll(entries); // add for inclusion in the DB
				//result.getBluetoothLog().addAll(entries);  //  doesn't work and need to be done in the other way round using the opposite
				refCommands.add(new BluetoothDevice_addContainedBluetoothLog_RefCommand(result,entries));	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	BluetoothLog readBluetoothLog(XmlPullParser parser)  throws XmlPullParserException, IOException{
		BluetoothLog result = new BluetoothLog();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_BLUETOOTHLOG);
    	String currentTagName = parser.getName();
    			
    	xmlId2BluetoothLog.put(parser.getAttributeValue(null, ID_STRING),result);		
		// TODO date = parser.getAttributeValue(null, DATAATT_BLUETOOTHLOG_DATE);
		result.setUserId(parser.getAttributeValue(null, DATAATT_BLUETOOTHLOG_userId));
		while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        currentTagName = parser.getName();
			if (currentTagName.equals(DATAREF_BLUETOOTHLOG_device)) {	
				parser.require(XmlPullParser.START_TAG, ns, DATAREF_BLUETOOTHLOG_device);
	            String id = readText(parser);
				refCommands.add(new BluetoothLog_setDevice_RefCommand(result,id, this));
				parser.require(XmlPullParser.END_TAG, ns, DATAREF_BLUETOOTHLOG_device);	    
	        } else
	        {
	            skip(parser);
	        }
	    }

		return result;
	}
	SMS readSMS(XmlPullParser parser)  throws XmlPullParserException, IOException{
		SMS result = new SMS();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_SMS);
    	String currentTagName = parser.getName();
    			
    	xmlId2SMS.put(parser.getAttributeValue(null, ID_STRING),result);		
		result.setDate(parser.getAttributeValue(null, DATAATT_SMS_date));
		result.setPhoneNumber(parser.getAttributeValue(null, DATAATT_SMS_phoneNumber));
		result.setType(parser.getAttributeValue(null, DATAATT_SMS_type));
		result.setUserId(parser.getAttributeValue(null, DATAATT_SMS_userId));
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
	BatteryUsage readBatteryUsage(XmlPullParser parser)  throws XmlPullParserException, IOException{
		BatteryUsage result = new BatteryUsage();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_BATTERYUSAGE);
    	String currentTagName = parser.getName();
    			
    	xmlId2BatteryUsage.put(parser.getAttributeValue(null, ID_STRING),result);		
		// TODO date = parser.getAttributeValue(null, DATAATT_BATTERYUSAGE_DATE);
		// TODO level = parser.getAttributeValue(null, DATAATT_BATTERYUSAGE_LEVEL);
		// TODO isPugged = parser.getAttributeValue(null, DATAATT_BATTERYUSAGE_ISPUGGED);
		result.setPlugType(parser.getAttributeValue(null, DATAATT_BATTERYUSAGE_plugType));
		result.setUserId(parser.getAttributeValue(null, DATAATT_BATTERYUSAGE_userId));
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
	WebHistory readWebHistory(XmlPullParser parser)  throws XmlPullParserException, IOException{
		WebHistory result = new WebHistory();

		parser.require(XmlPullParser.START_TAG, ns, DATACLASSIFIER_WEBHISTORY);
    	String currentTagName = parser.getName();
    			
    	xmlId2WebHistory.put(parser.getAttributeValue(null, ID_STRING),result);		
		// TODO date = parser.getAttributeValue(null, DATAATT_WEBHISTORY_DATE);
		result.setApplication(parser.getAttributeValue(null, DATAATT_WEBHISTORY_application));
		result.setHostname(parser.getAttributeValue(null, DATAATT_WEBHISTORY_hostname));
		result.setIpDestination(parser.getAttributeValue(null, DATAATT_WEBHISTORY_ipDestination));
		result.setUserId(parser.getAttributeValue(null, DATAATT_WEBHISTORY_userId));
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
	class Contact_setContainedContactOrganisation_RefCommand extends RefCommand{
	Contact container;
		ContactOrganisation containedElement;
		
		public Contact_setContainedContactOrganisation_RefCommand(Contact container,
				ContactOrganisation containedElement) {
			super();
			this.container = container;
			this.containedElement = containedElement;
		}

		@Override
		public void run() {
			containedElement.setReferencedContact(container);
			contactOrganisationsToUpdate.add(containedElement);			
		}
		
	}
	class Contact_addContainedContactIM_RefCommand extends RefCommand{
		Contact container;
		List<ContactIM> containedElements;
		
		public Contact_addContainedContactIM_RefCommand(Contact container,
				List<ContactIM> containedElements) {
			super();
			this.container = container;
			this.containedElements = containedElements;
		}

		@Override
		public void run() {
			for (ContactIM element : containedElements) {				
				element.setContact(container);
				contactIMsToUpdate.add(element);
			}
		}
		
	}
	class Contact_addContainedContactEvent_RefCommand extends RefCommand{
		Contact container;
		List<ContactEvent> containedElements;
		
		public Contact_addContainedContactEvent_RefCommand(Contact container,
				List<ContactEvent> containedElements) {
			super();
			this.container = container;
			this.containedElements = containedElements;
		}

		@Override
		public void run() {
			for (ContactEvent element : containedElements) {				
				element.setContact(container);
				contactEventsToUpdate.add(element);
			}
		}
		
	}
	class ContactOrganisation_setReferencedContact_RefCommand extends RefCommand{
		ContactOrganisation self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public ContactOrganisation_setReferencedContact_RefCommand(ContactOrganisation self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setReferencedContact(parser.xmlId2Contact.get(referencedElementID));
			contactOrganisationsToUpdate.add(self);
		}
	}
	class ContactIM_setContact_RefCommand extends RefCommand{
		ContactIM self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public ContactIM_setContact_RefCommand(ContactIM self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setContact(parser.xmlId2Contact.get(referencedElementID));
			contactIMsToUpdate.add(self);
		}
	}
	class ContactEvent_setContact_RefCommand extends RefCommand{
		ContactEvent self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public ContactEvent_setContact_RefCommand(ContactEvent self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setContact(parser.xmlId2Contact.get(referencedElementID));
			contactEventsToUpdate.add(self);
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
	class KnownWifi_addContainedLogsWifi_RefCommand extends RefCommand{
		KnownWifi container;
		List<LogsWifi> containedElements;
		
		public KnownWifi_addContainedLogsWifi_RefCommand(KnownWifi container,
				List<LogsWifi> containedElements) {
			super();
			this.container = container;
			this.containedElements = containedElements;
		}

		@Override
		public void run() {
			for (LogsWifi element : containedElements) {				
				element.setKnownWifi(container);
				logsWifisToUpdate.add(element);
			}
		}
		
	}
	class LogsWifi_setKnownWifi_RefCommand extends RefCommand{
		LogsWifi self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public LogsWifi_setKnownWifi_RefCommand(LogsWifi self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setKnownWifi(parser.xmlId2KnownWifi.get(referencedElementID));
			logsWifisToUpdate.add(self);
		}
	}
	class Cell_addContainedHistory_RefCommand extends RefCommand{
		Cell container;
		List<NeighboringCellHistory> containedElements;
		
		public Cell_addContainedHistory_RefCommand(Cell container,
				List<NeighboringCellHistory> containedElements) {
			super();
			this.container = container;
			this.containedElements = containedElements;
		}

		@Override
		public void run() {
			for (NeighboringCellHistory element : containedElements) {				
				element.setCells(container);
				neighboringCellHistorysToUpdate.add(element);
			}
		}
		
	}
	class Cell_setContainedCdmaposition_RefCommand extends RefCommand{
	Cell container;
		CdmaCellData containedElement;
		
		public Cell_setContainedCdmaposition_RefCommand(Cell container,
				CdmaCellData containedElement) {
			super();
			this.container = container;
			this.containedElement = containedElement;
		}

		@Override
		public void run() {
			containedElement.setIdentity(container);
			cdmaCellDatasToUpdate.add(containedElement);			
		}
		
	}
	class Cell_setContainedOtherPosition_RefCommand extends RefCommand{
	Cell container;
		OtherCellData containedElement;
		
		public Cell_setContainedOtherPosition_RefCommand(Cell container,
				OtherCellData containedElement) {
			super();
			this.container = container;
			this.containedElement = containedElement;
		}

		@Override
		public void run() {
			containedElement.setIdentity(container);
			otherCellDatasToUpdate.add(containedElement);			
		}
		
	}
	class OtherCellData_setIdentity_RefCommand extends RefCommand{
		OtherCellData self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public OtherCellData_setIdentity_RefCommand(OtherCellData self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setIdentity(parser.xmlId2Cell.get(referencedElementID));
			otherCellDatasToUpdate.add(self);
		}
	}
	class CdmaCellData_setIdentity_RefCommand extends RefCommand{
		CdmaCellData self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public CdmaCellData_setIdentity_RefCommand(CdmaCellData self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setIdentity(parser.xmlId2Cell.get(referencedElementID));
			cdmaCellDatasToUpdate.add(self);
		}
	}
	class NeighboringCellHistory_setCells_RefCommand extends RefCommand{
		NeighboringCellHistory self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public NeighboringCellHistory_setCells_RefCommand(NeighboringCellHistory self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setCells(parser.xmlId2Cell.get(referencedElementID));
			neighboringCellHistorysToUpdate.add(self);
		}
	}
	class BluetoothDevice_addContainedBluetoothLog_RefCommand extends RefCommand{
		BluetoothDevice container;
		List<BluetoothLog> containedElements;
		
		public BluetoothDevice_addContainedBluetoothLog_RefCommand(BluetoothDevice container,
				List<BluetoothLog> containedElements) {
			super();
			this.container = container;
			this.containedElements = containedElements;
		}

		@Override
		public void run() {
			for (BluetoothLog element : containedElements) {				
				element.setDevice(container);
				bluetoothLogsToUpdate.add(element);
			}
		}
		
	}
	class BluetoothLog_setDevice_RefCommand extends RefCommand{
		BluetoothLog self;
		String referencedElementID;
		MobilePrivacyProfilerDBXMLParser parser;
		
		public BluetoothLog_setDevice_RefCommand(BluetoothLog self,
				String referencedElementID, MobilePrivacyProfilerDBXMLParser parser) {
			super();
			this.self = self;
			this.referencedElementID = referencedElementID;
			this.parser = parser;
		}

		@Override
		public void run() {
			self.setDevice(parser.xmlId2BluetoothDevice.get(referencedElementID));
			bluetoothLogsToUpdate.add(self);
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
