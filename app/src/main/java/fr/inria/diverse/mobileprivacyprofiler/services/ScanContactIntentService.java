package fr.inria.diverse.mobileprivacyprofiler.services;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.inria.diverse.mobileprivacyprofiler.activities.Home_CustomViewActivity;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.Contact;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactEmail;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactEvent;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactIM;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactOrganisation;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactPhoneNumber;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.ContactPhysicalAddress;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDBHelper;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.MobilePrivacyProfilerDB_metadata;
import fr.inria.diverse.mobileprivacyprofiler.datamodel.OrmLiteDBHelper;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ScanContactIntentService extends IntentService {

    private static final String TAG = ScanContactIntentService.class.getSimpleName();
    private volatile OrmLiteDBHelper dbHelper;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SCAN_CONTACTS = "fr.inria.diverse.mobileprivacyprofiler.services.action.SCAN_CONTACTS";

    public ScanContactIntentService() {
        super("ScanContactIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static Void startActionScanContacts() {
        Intent intent = new Intent(Home_CustomViewActivity.getContext(), ScanContactIntentService.class);
        intent.setAction(ACTION_SCAN_CONTACTS);
        Home_CustomViewActivity.getContext().startService(intent);
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SCAN_CONTACTS.equals(action)) {
                handleActionScanContact();
            }
        }
    }

    /**
     * Handle action ScanContact in the provided background thread with the provided
     * parameters.
     */
    private void handleActionScanContact (){
        // Return all raw_contacts android_id in a list: contactIdList
        List<Integer> rawContactIdList = new ArrayList<Integer>();

        ContentResolver contentResolver = getContentResolver();

        //update last ContactScan and flushing previous contact set
        getDBHelper().getMobilePrivacyProfilerDBHelper().flushContactDataSet();

        MobilePrivacyProfilerDB_metadata metadata = getDeviceDBMetadata();
        Date scanTimeStamp = new Date();
        metadata.setLastContactScan(scanTimeStamp);
        getDBHelper().getMobilePrivacyProfilerDB_metadataDao().update(metadata);

        // Row contacts content uri( access raw_contacts table. ).
        Uri rawContactUri = ContactsContract.RawContacts.CONTENT_URI;
        // Return android_id column in contacts raw_contacts table.
        String queryContactColumnArr[] = {ContactsContract.RawContacts._ID};
        // Query raw_contacts table and return raw_contacts table android_id.
        Cursor rawContactCursor = contentResolver.query(rawContactUri,queryContactColumnArr, null, null, null);
        if(rawContactCursor!=null) {
            rawContactCursor.moveToFirst();
            if(0!=rawContactCursor.getCount()){
                do {
                    int idColumnIndex = rawContactCursor.getColumnIndex(ContactsContract.RawContacts._ID);
                    int rawContactsId = rawContactCursor.getInt(idColumnIndex);
                    rawContactIdList.add(new Integer(rawContactsId));

                } while (rawContactCursor.moveToNext());
            }
        }

        rawContactCursor.close();
        // end of contactIdList creation

        Log.d(TAG, " Number of contact to process : " + rawContactIdList.size());

        // Loop in the raw contacts list to query contact details
        for(Integer rawContactId :rawContactIdList) {


            Log.d(TAG, "raw contact id : " + rawContactId.intValue());

            // Data content uri (access data table. )
            Uri dataContentUri = ContactsContract.Data.CONTENT_URI;

            // Build query columns name array.
            List<String> queryColumnList = new ArrayList<String>();

            // ContactsContract.Data.CONTACT_ID = "contact_id";
            queryColumnList.add(ContactsContract.Data.CONTACT_ID);

            // ContactsContract.Data.MIMETYPE = "mimetype";
            queryColumnList.add(ContactsContract.Data.MIMETYPE);

            queryColumnList.add(ContactsContract.Data.DATA1);
            queryColumnList.add(ContactsContract.Data.DATA2);
            queryColumnList.add(ContactsContract.Data.DATA3);
            queryColumnList.add(ContactsContract.Data.DATA4);
            queryColumnList.add(ContactsContract.Data.DATA5);
            queryColumnList.add(ContactsContract.Data.DATA6);
            queryColumnList.add(ContactsContract.Data.DATA7);
            queryColumnList.add(ContactsContract.Data.DATA8);
            queryColumnList.add(ContactsContract.Data.DATA9);
            queryColumnList.add(ContactsContract.Data.DATA10);
            queryColumnList.add(ContactsContract.Data.DATA11);
            queryColumnList.add(ContactsContract.Data.DATA12);
            queryColumnList.add(ContactsContract.Data.DATA13);
            queryColumnList.add(ContactsContract.Data.DATA14);
            queryColumnList.add(ContactsContract.Data.DATA15);

            // Translate column name list to array.
            String queryColumnArr[] = queryColumnList.toArray(new String[queryColumnList.size()]);

            // Build query condition string. Query rows by contact id.
            StringBuffer whereClauseBuf = new StringBuffer();
            whereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID);
            whereClauseBuf.append("=");
            whereClauseBuf.append(rawContactId);

            // Query data table and return related contact data.
            Cursor contactDetailCursor = contentResolver.query(dataContentUri, queryColumnArr, whereClauseBuf.toString(), null, null);

            /* If this cursor return database table row data
               If do not check cursor.getCount() then it will throw error
               android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0.
               */
            if(contactDetailCursor!=null && contactDetailCursor.getCount() > 0)
            {
                StringBuffer lineBuf = new StringBuffer();
                contactDetailCursor.moveToFirst();

                lineBuf.append("Raw Contact Id : ");
                lineBuf.append(rawContactId);

                long contactId = contactDetailCursor.getLong(contactDetailCursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                lineBuf.append(" , Contact Id : ");
                lineBuf.append(contactId);


                //Creation of data structure to collect details from a contact
                Contact contact = new Contact();
                List<ContactEmail> emailList = new ArrayList();
                ContactOrganisation organisation = new ContactOrganisation();
                List<ContactPhoneNumber> phoneNumberList = new ArrayList();
                List<ContactPhysicalAddress> physicalAddressList = new ArrayList();
                List<ContactIM> imList = new ArrayList();
                List<ContactEvent> eventList = new ArrayList();

                do{
                    // First get mimetype column value.
                    String mimeType = contactDetailCursor.getString(contactDetailCursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                    lineBuf.append(" \r\n , MimeType : ");
                    lineBuf.append(mimeType);

                    List<String> dataValueList = getColumnValueByMimetype(contactDetailCursor, mimeType);


                    Log.d(TAG, "reformating details to objects");
                    switch (dataValueList.get(0)){
                        //TODO change the List<String> to a Map
                        case "EMAIL":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            ContactEmail contactEmail = new ContactEmail(dataValueList.get(1),dataValueList.get(2),getDeviceDBMetadata().getUserId());
                            emailList.add(contactEmail);
                            break;
                        case "IM":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            ContactIM contactIM = new ContactIM(dataValueList.get(1),dataValueList.get(2),getDeviceDBMetadata().getUserId());
                            imList.add(contactIM);
                            break;
                        case "NICKNAME":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1));
                            contact.setNickname(dataValueList.get(1));
                            break;
                        case "ORGANISATION":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            organisation = new ContactOrganisation(dataValueList.get(1),dataValueList.get(2),getDeviceDBMetadata().getUserId());
                            break;
                        case "PHONE_NUMBER":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            ContactPhoneNumber contactPhoneNumber = new ContactPhoneNumber(dataValueList.get(1),dataValueList.get(2),getDeviceDBMetadata().getUserId());
                            phoneNumberList.add(contactPhoneNumber);
                            break;
                        case "NAME":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2)+" : "+dataValueList.get(3)+" : "+dataValueList.get(4)+" : "+dataValueList.get(5)+" : "+dataValueList.get(6));
                            contact.setDisplayName(dataValueList.get(1));
                            contact.setNamePrefix(dataValueList.get(2));
                            contact.setFirstName(dataValueList.get(3));
                            contact.setMiddleName(dataValueList.get(4));
                            contact.setLastName(dataValueList.get(5));
                            contact.setNameSuffix(dataValueList.get(6));
                            contact.setScanTimeStamp(scanTimeStamp);
                            break;
                        case "PHYSICAL_ADDRESS":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            ContactPhysicalAddress contactPhysicalAddress = new ContactPhysicalAddress(dataValueList.get(1),dataValueList.get(2),getDeviceDBMetadata().getUserId());
                            physicalAddressList.add(contactPhysicalAddress);
                            break;
                        case "WEBSITE":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1));
                            String temp = contact.getWebsite();
                            temp +=", ";
                            temp += dataValueList.get(1);
                            contact.setWebsite(temp);
                            break;
                        case "EVENT":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            ContactEvent contactEvent = new ContactEvent(dataValueList.get(1),dataValueList.get(2),getDeviceDBMetadata().getUserId());
                            eventList.add(contactEvent);
                            break;
                        case "RELATION_TYPE":
                            Log.d(TAG, dataValueList.get(0)+" : "+dataValueList.get(1)+" : "+dataValueList.get(2));
                            String tempo = contact.getRelation();
                            tempo +="; ";
                            tempo += dataValueList.get(1);
                            tempo += " : ";
                            tempo += dataValueList.get(2);
                            contact.setRelation(tempo);
                            break;
                        case "UNPROCESSED_ITEM":
                            Log.d(TAG, dataValueList.get(0));
                            break;
                    }


                }while(contactDetailCursor.moveToNext());
                contactDetailCursor.close();

                // feeding the DB from the collected data
                try {
                    //add a new contact
                    contact.setUserId(getDeviceDBMetadata().getUserId());
                    getDBHelper().getContactDao().create(contact);
                    Log.d(TAG, "Added a new contact : " + contact.getDisplayName());

                    for(ContactEmail contactEmail: emailList){
                        contactEmail.setContact(contact);
                        contactEmail.setUserId(getDeviceDBMetadata().getUserId());
                        getDBHelper().getContactEmailDao().create(contactEmail);
                        Log.d(TAG, "Added a new email : " + contactEmail.getEmail());
                    }
                    if(null!=organisation.getCompany()) {
                        organisation.setReferencedContact(contact);
                        organisation.setUserId(getDeviceDBMetadata().getUserId());
                        getDBHelper().getContactOrganisationDao().create(organisation);
                        Log.d(TAG, "Added a new organisation : " + organisation.getCompany());
                    }

                    for(ContactPhoneNumber contactPhoneNumber : phoneNumberList){
                        contactPhoneNumber.setContact(contact);
                        contactPhoneNumber.setUserId(getDeviceDBMetadata().getUserId());
                        getDBHelper().getContactPhoneNumberDao().create(contactPhoneNumber);
                        Log.d(TAG, "Added a new phoneNumber : "+contactPhoneNumber.getPhoneNumber());
                    }

                    for(ContactPhysicalAddress contactPhysicalAddress : physicalAddressList){
                        contactPhysicalAddress.setContact(contact);
                        contactPhysicalAddress.setUserId(getDeviceDBMetadata().getUserId());
                        getDBHelper().getContactPhysicalAddressDao().create(contactPhysicalAddress);
                        Log.d(TAG, "Added a new physicalAddress : "+contactPhysicalAddress.getAddress());
                    }

                    for(ContactIM contactIM : imList){
                        contactIM.setContact(contact);
                        contactIM.setUserId(getDeviceDBMetadata().getUserId());
                        getDBHelper().getContactIMDao().create(contactIM);
                        Log.d(TAG, "Added a new instant messenger : "+contactIM.getImId());
                    }

                    for(ContactEvent contactEvent : eventList){
                        contactEvent.setContact(contact);
                        contactEvent.setUserId(getDeviceDBMetadata().getUserId());
                        getDBHelper().getContactEventDao().create(contactEvent);
                        Log.d(TAG, "Added a new contactEvent : "+contactEvent.getType()+" : "+contactEvent.getStartDate());
                    }
                } catch (Exception e) { Log.d(TAG, "Error while feeding the DB");e.printStackTrace(); }
            }



        }//end of contact detail processing

    }


    /**
     *
     *  Return data column value by mimetype column value.
     *  Because for each mimetype there has not only one related value,
     *  such as Organization.CONTENT_ITEM_TYPE need return company, department, title, job description etc.
     *  So the return is a list string, each string for one column value.
     * */
    private List<String> getColumnValueByMimetype(Cursor cursor, String mimeType)
    {
        List<String> toReturn = new ArrayList<String>();

        switch (mimeType)
        {
            // Get email data
            case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE :
                // Email.ADDRESS == data1
                String emailAddress = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                // Email.TYPE == data2
                int emailType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                String emailTypeStr = getEmailTypeString(emailType);


                toReturn.add("EMAIL");
                toReturn.add(emailAddress);

                if("Custom"!=emailTypeStr)  {toReturn.add(emailTypeStr);}
                else                        {
                    // Email.LABEL == data3 (if type = custom then read the label)
                    String emailLabel = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.LABEL));
                    toReturn.add(emailLabel);
                }

                break;

            // Get im data
            case ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE:
                // Im.PROTOCOL == data5
                int imProtocol = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL));
                // Im.DATA == data1
                String imId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));

                String imProtocolString = getImProtocolTypeString(cursor,imProtocol);

                toReturn.add("IM");
                toReturn.add(imProtocolString);
                toReturn.add(imId);
                break;

            // Get nickname
            case ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE:
                // Nickname.NAME == data1
                String nickName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));

                toReturn.add("NICKNAME");
                toReturn.add(nickName);
                break;

            // Get organization data
            case ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE:
                // Organization.COMPANY == data1
                String company = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
                // Organization.TITLE == data4
                String title = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));

                toReturn.add("ORGANISATION");
                toReturn.add(company);
                toReturn.add(title);
                break;

            // Get phone number
            case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                // Phone.NUMBER == data1
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                // Phone.TYPE == data2
                int phoneTypeInt = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                String phoneTypeStr = getPhoneTypeString(cursor,phoneTypeInt);

                toReturn.add("PHONE_NUMBER");
                toReturn.add(phoneNumber);
                toReturn.add(phoneTypeStr);
                break;


            // Get display name
            case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                // StructuredName.DISPLAY_NAME == data1
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
                // StructuredName.PREFIX == data4
                String namePrefix = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PREFIX));
                // StructuredName.GIVEN_NAME == data2
                String givenName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                // StructuredName.MIDDLE_NAME == data5
                String middleName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME));
                // StructuredName.FAMILY_NAME == data3
                String familyName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                // StructuredName.SUFFIX == data6
                String nameSuffix = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.SUFFIX));

                toReturn.add("NAME");
                toReturn.add(displayName);
                toReturn.add(namePrefix);
                toReturn.add(givenName);
                toReturn.add(middleName);
                toReturn.add(familyName);
                toReturn.add(nameSuffix);
                break;

            // Get postal address
            case ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE:
                // StructuredPostal.COUNTRY == data10
                String country = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                // StructuredPostal.CITY == data7
                String city = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                // StructuredPostal.REGION == data8
                String region = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                // StructuredPostal.STREET == data4
                String street = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                // StructuredPostal.POSTCODE == data9
                String postcode = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                // StructuredPostal.TYPE == data2
                int postType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                String postTypeStr = getEmailTypeString(postType); // TYPE_MOBILE isn't displayed other value remain the same than EmailType


                toReturn.add("PHYSICAL_ADDRESS");
                toReturn.add("Country : "+country+", City : "+city+", Region : "+region+",Street : "+street+",Postcode : "+postcode);
                if("Custom"!=postTypeStr)  {toReturn.add(postTypeStr);}
                else                       {
                    // Email.LABEL == data3 (if type = custom then read the label)
                    String addressTypeLabel = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.LABEL));
                    toReturn.add(addressTypeLabel);
                }

                break;


            // Get website
            case ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE:
                // Website.URL == data1
                String websiteUrl = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
                // Website.TYPE == data2
                int websiteTypeInt = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE));
                String webSiteTypeStr = getEmailTypeString(websiteTypeInt);

                toReturn.add("WEBSITE");

                if("Custom"!=webSiteTypeStr)  {toReturn.add(webSiteTypeStr+" : "+websiteUrl);}
                else                          {
                    // Website.LABEL == data3 (if type = custom then read the label)
                    String webSiteTypeLabel = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.LABEL));
                    toReturn.add(webSiteTypeLabel+" : "+websiteUrl);
                }
                break;

            // Get Event
            case ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE:
                // Event.START_DATE == data1
                String startDate = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                // Event.TYPE == data2
                int eventType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE));
                String eventTypeString = getEventTypeString(cursor,eventType);

                toReturn.add("EVENT");
                toReturn.add(startDate);
                toReturn.add(eventTypeString);
                break;


            // Get Relation
            case ContactsContract.CommonDataKinds.Relation.CONTENT_ITEM_TYPE:
                // Relation.DATA == data1
                String relationName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Relation.DATA));
                // Relation.TYPE == data2
                int relationType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Relation.TYPE));
                String relationTypeString = getRelationTypeString(cursor,relationType);

                toReturn.add("RELATION_TYPE");
                toReturn.add(relationName);
                toReturn.add(relationTypeString);
                break;

            default:
                toReturn.add("UNPROCESSED_ITEM");
        }

        return toReturn;
    }

    private String getImProtocolTypeString(Cursor cursor,int dataType){
        String toReturn ="";
        if(ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM == dataType)
        {
            toReturn = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL));
        }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_MSN==dataType)     { toReturn = "MSN"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_YAHOO==dataType)   { toReturn = "YAHOO"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_SKYPE==dataType)   { toReturn = "SKYPE"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ==dataType)      { toReturn = "QQ"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_GOOGLE_TALK==dataType) { toReturn = "GOOGLE_TALK"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_ICQ==dataType)     { toReturn = "ICQ"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_JABBER==dataType)  { toReturn = "JABBER"; }
        else if(ContactsContract.CommonDataKinds.Im.PROTOCOL_NETMEETING==dataType) { toReturn = "NETMEETING"; }

        return toReturn;
    }

    /**
     *  Get email type related string format value.
     * */
    private String getEmailTypeString(int dataType)
    {
        String ret = "";
        if(ContactsContract.CommonDataKinds.Email.TYPE_HOME == dataType)
        { ret = "Home"; }
        else if(ContactsContract.CommonDataKinds.Email.TYPE_WORK==dataType)
        { ret = "Work"; }
        else if(ContactsContract.CommonDataKinds.Email.TYPE_MOBILE==dataType)
        { ret = "Mobile"; }
        else if(ContactsContract.CommonDataKinds.Email.TYPE_OTHER==dataType)
        { ret = "Other"; }
        else if(ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM==dataType)
        { ret = "Custom"; }
        return ret;
    }

    /**
     *  Get phone type related string format value.
     * */
    private String getPhoneTypeString(Cursor cursor, int dataType)
    {
        String toReturn = "";

        switch (dataType){
            case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
                // Phone.LABEL == data3
                toReturn = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:          toReturn = "Work";      break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE :       toReturn = "Mobile";    break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME :         toReturn = "Home";      break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK :     toReturn = "Fax work";  break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME :     toReturn = "Fax home";  break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_PAGER :        toReturn = "Pager";     break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER :        toReturn = "Other";     break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK :     toReturn = "Callback";  break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_CAR :          toReturn = "Car";       break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN : toReturn = "Company main";break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_ISDN :         toReturn = "ISDN";      break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN :         toReturn = "Main";      break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX :    toReturn = "Other fax"; break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_RADIO :        toReturn = "Radio";     break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_TELEX :        toReturn = "Telex";     break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD :      toReturn = "TTY_TDD";   break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE :  toReturn = "Mobile";    break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER :   toReturn = "Pager";     break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT :    toReturn = "Assistant"; break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MMS :          toReturn = "MMS";       break;
        }//end switch
        return toReturn;
    }
    /**
     *  Get event type related string format value.
     * */
    private String getEventTypeString(Cursor cursor, int dataType) {
        String toReturn ="";
        switch (dataType){
            case ContactsContract.CommonDataKinds.Event.TYPE_CUSTOM:        toReturn = "Custom";        break;
            case ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY:   toReturn = "Anniversary";   break;
            case ContactsContract.CommonDataKinds.Event.TYPE_OTHER:         toReturn = "Other";         break;
            case ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY:      toReturn = "Birthday";      break;
        }
        if("Custom"==toReturn){ toReturn = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.LABEL)); }

        return toReturn;
    }

    /**
     *  Translate the Relation  int type to related string value.
     * */
    private String getRelationTypeString(Cursor cursor, int relationType) {
        String toReturn = "";

        switch (relationType){
            case ContactsContract.CommonDataKinds.Relation.TYPE_CUSTOM:
                // Relation.LABEL == data3
                toReturn = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Relation.LABEL));
                break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_ASSISTANT:          toReturn = "Assistant";     break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_BROTHER :           toReturn = "Brother";       break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_CHILD :             toReturn = "Child";         break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_DOMESTIC_PARTNER :  toReturn = "Domestic_Partner";  break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_FATHER :            toReturn = "Father";        break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_FRIEND :            toReturn = "Friend";        break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_MANAGER :           toReturn = "Manager";       break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_MOTHER :            toReturn = "Mother";        break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_PARENT :            toReturn = "Parent";        break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_PARTNER :           toReturn = "Partner";       break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_REFERRED_BY :       toReturn = "Referred_by";   break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_RELATIVE :          toReturn = "Relative";      break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_SISTER :            toReturn = "Sister";        break;
            case ContactsContract.CommonDataKinds.Relation.TYPE_SPOUSE :            toReturn = "Spouse";        break;
        }//end switch
        return toReturn;
    }

    private OrmLiteDBHelper getDBHelper(){
        if(dbHelper == null){
            dbHelper = OpenHelperManager.getHelper(this, OrmLiteDBHelper.class);
        }
        return dbHelper;
    }

    private MobilePrivacyProfilerDB_metadata getDeviceDBMetadata(){
        return getDBHelper().getMobilePrivacyProfilerDBHelper().getDeviceDBMetadata();}
}
