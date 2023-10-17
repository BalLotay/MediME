# SEG2105A Project - Group 39

In this project, our team implemented the Healthcare Appointment Management System (HAMS) for a telehealth clinic. HAMS is a mobile application designed to streamline the process of healthcare appointment scheduling and management. 

The app currently supports three types of users: Patient, Doctor, and Administrator, and features such as logging in, signing up, logging off, and has its data securely stored and authenticated online using Firebase's Realtime Database.

## Contributors:

- Balpreet Singh
- Daniel Guo
- Dipak Chinnasamy Selvam
- Leon Mathews
- Steven Yang
- Tarek Rehouli

## Setting up the app

The app can be directly installed onto an Android phone using the `app-debug.apk` file under the APK directory. Alternatively, the app can be run from the Android Studio Emulator by loading up the project under FinalProject/LoginApp. **Only use this directory as it is the final integration of all the app's features.**

Additionally, do take note of the following:

- The admin credentials are:
  + Username: admin
  + Password: pass
- Be sure to sync the project with `build.gradle.kts` Gradle file under the FinalProject/LoginApp directory **only**.

### Database

The app is able to store data on a Firebase database, as seen in the screenshot below:


![Screenshot of the database in Firebase](Images/Firebase.png)


### Note

There is a delay when clicking on the "LOGIN" button in which case the toast saying "LOGIN FAILED" comes up, probably due to a delay in fetching the data from the database. Though, this is only for a second, and works on the next try. 