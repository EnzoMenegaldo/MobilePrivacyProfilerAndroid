# Mobile Privacy Profiler


About
--------------
It's an Android application whose goal is to collect all available personal information in a mobile device.
This project is realized in collaboration with members of the University of Rennes 2 in order to try to sensitize mobile device users about the easiness of collecting personal data from their device without being aware of that.

Compatibility
--------------

The application necessitate at least a device running Android 6.0 ( aka Marshmallow ).

Current database schema
--------------

![database schema](https://gitlab.inria.fr/mobile-privacy-profiler/MobilePrivacyProfilerAndroid/raw/master/app/documentation/MobilePrivacyProfilerDBClassDiagram.png "database schema")

Current page navigation diagram
--------------

![page navigation diagram](https://gitlab.inria.fr/mobile-privacy-profiler/documentation/blob/master/maquette-app-profile.pdf "page navigation diagram")

How to use it ?
--------------
 1. The user must provide an email address
 2. Create an user accound with the email on the server.
 3. Start the app and enter his credentials
 4. Accept all the permissions requested or it will continue to ask and block the app
 5. Start the collection by pressing the big button
    
How does it work ?
--------------

 1. Each type of data is collected by a specific service and saved in a local database using [OrmLite](http://ormlite.com/sqlite_java_android_orm.shtml).
 2. Each service is associated to a [Job](https://developer.android.com/reference/android/app/job/JobService).
 3. The app runs indefinetely and the [JobScheduler](https://developer.android.com/reference/android/app/job/JobScheduler) triggers the different jobs.
 4. The data are sent to a remonte server hosted by IRISA. The server is accessible at the following the address : [https://userprivacydataserver.diverse-team.fr](https://userprivacydataserver.diverse-team.fr)
    
Things to do 
--------------

 1. Get text file containing the CGU from Rennes 2 and replace the current cgu_profile.txt in the folder res/raw
 2. Get json file containing the FAQ and replace the current faq_profile.json in the folder res/raw

