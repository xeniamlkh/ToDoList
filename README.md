# What is To Do List App?
To Do List is **a study project** in Kotlin for Android devices. Please be aware that this project was made for **learning purposes** only and will not be updated or supported further.
This README file has two paragraphs:

## Description
### General
To Do List App’s function is to create **to-do notes** and track your functionality.

On the calendar, you can move back and forth, choose a date, and create a to-do note for that date. When you finish a task, you can mark a note with a check mark in the checkbox near the note.
Additionally, you can display all unfinished tasks and mark them as finished, or display all finished tasks and delete them forever.

Moreover, the app shows **today’s weather** in the top-right corner of the screen. Knowing the current weather can help you plan your activities for the day, and don't forget to bring an umbrella or use sunscreen. To see the weather, it is required to allow the app to use your **location**.
### Technical
The further technical description aims to show the structure of the study project, its functionality, and what instruments have been implemented.

* When the app starts for the first time, it shows a **permission request** for the user's current location.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/6c6093b8-0283-4091-9ca0-aef40c41b2c2" width="281" height="623">
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/db0851b8-1821-45e8-82fa-f3ca5b9297f7" width="283" height="626">

  *Android 8.1-11 (API level 21-30)* &emsp; &emsp; &emsp; *Android 12 (API level 31) or higher*

&nbsp;
* After permissions are granted, the main screen is loading.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/519f4233-b1a6-45db-8846-b26b1a131ed7" width="281" height="626">

&nbsp;
* If no permissions are allowed, the app will show the “Have a nice day!” quotation.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/289375bc-5394-43b7-8981-a1c5711d3856" width="284" height="626">

&nbsp;
* To create your first task, write it in the “Today I plan to do” input field and click the Save button.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/2144bb8b-98d7-4316-a27d-0bcbcd28891f" width="287" height="626">

&nbsp;
* If a note is missing, you can’t save it. A warning will appear.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/f2528d85-e127-4b70-8ab7-e6d994320429" width="283" height="622">

&nbsp;
* A note will be shown in the list below.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/8f27c34c-ebbc-4ae9-a40c-16c96310d1b5" width="283" height="627">

&nbsp;
* If you want to edit or delete the created note, choose the side **menu** in the item.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/649f8453-f8cb-4c03-8dd7-dba32a93dd0b" width="282" height="621">

&nbsp;
* If you decide to delete the note, an **alert dialogue** is shown. The note will be deleted from the **Room database** by its ID.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/c569f336-e572-4e19-bcbd-218bca5b30c6" width="281" height="618">

&nbsp;
* If you decide to edit the note, the edit fragment will open, where you can update the note. To save an updated note, choose the Save button; to cancel all changes and return to the previous screen, choose the Cancel button.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/b60aafa4-9ed7-4a57-a512-bb9988ad6033" width="279" height="624">

&nbsp;
* After the update or deletion, a **snackbar** is shown.

  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/24fa7341-f10d-4c0d-b0f2-a223a7a83d0d" width="290" height="629">
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/5e0e7e23-caed-43e1-a93d-9a118cf4d12a" width="291" height="631">


&nbsp;
* To choose another date for your to-do tasks, click on the date. A **DatePicker dialogue** will be shown.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/fcc59373-8554-4beb-93e2-6b503f17f18c" width="283" height="626">

&nbsp;
* After choosing a proper date, you can create a note for this date.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/7676410a-031f-47a5-b97d-80db1be0dee7" width="284" height="628">
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/7a94fa39-67a8-4244-b5ce-5882177c7c6f" width="283" height="628">

&nbsp;
* If you have finished your planned task, you can mark your note as finished by putting a check mark in the **checkbox** near the note. In this case, a note will be stroked through.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/38a23ccb-e9ee-4a58-a410-3065431cc277" width="288" height="630">

&nbsp;
* Additionally, you can see a list of all your finished or unfinished tasks, regardless of date. You can delete all finished tasks simultaneously by clicking on the “DELETE FINISHED?” **FAB button**.
  
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/b54584a1-4f36-4035-9736-757b3f578f5e" width="283" height="621">
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/d2314fab-fda9-4f9f-8043-aa2cd726e896" width="286" height="626">

&nbsp;
* If there are no finished tasks, the “No finished tasks” snackbar is shown. If there are unfinished tasks, “All work is done! No unfinished tasks” snackbar is shown.

  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/8dfdb125-7af1-4ed4-b471-b94e843e605b" width="293" height="628">
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/c93df55a-ed39-4209-8247-5a687f8a9eab" width="287" height="627">

&nbsp;
* The weather window shows a current location, temperature in Celsius, a description of the current weather condition (rain, sunshine, etc.), and an icon. The weather is loading from Openweathermap.org by using **Retrofit**. Icons are saved locally in drawables.

![Screenshot 2024-05-25 153242](https://github.com/xeniamlkh/ToDoList/assets/89986215/c95db44e-873e-4f20-8589-d7be6b61e83e)
![Screenshot 2024-05-25 161420](https://github.com/xeniamlkh/ToDoList/assets/89986215/eeb1be57-bcd3-423f-a923-5f18088569a3)
![Screenshot 2024-05-25 173957](https://github.com/xeniamlkh/ToDoList/assets/89986215/f3d38f38-ce80-4d21-8912-f7e6594fe812)
![Screenshot 2024-05-25 182148](https://github.com/xeniamlkh/ToDoList/assets/89986215/7d1e43b7-7e85-4bc7-bd66-c08c3d3bd0a9)
  
&nbsp;
* The To Do List app has an **icon** and supports a **dark theme**.
  
  ![Screenshot 2024-05-25 162725](https://github.com/xeniamlkh/ToDoList/assets/89986215/f4495213-99b7-4889-866d-29d2d876e385)
  ![Screenshot 2024-05-25 162816](https://github.com/xeniamlkh/ToDoList/assets/89986215/bd4bb973-0335-4ecd-b207-46fc07de6eec)
  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/375c33e8-4472-4263-8022-f061273507fb" width="278" height="619">

&nbsp;
* The To Do List app supports **back-button navigation** and **screen rotation**.

  <img src="https://github.com/xeniamlkh/ToDoList/assets/89986215/4487d9b5-dd4b-4d30-af24-54b2060d1528" width="1135" height="560">

&nbsp;
* **minSdk = 27**; **targetSdk = 34**. The To Do LIst app supports platform 8.1 (Oreo) and above.

## What was used in this project?
* Android Architecture: UI, Model
* Room database
* Repositories for a Room database and Network connection
* Retrofit
* Multiple Runtime Location Permissions request through Permission Launcher and register Activity for Result
* Internet permission
* Permission Rationale
* Android Location Manager API
* Openweathermap API key proper storing in a file that has been added to gitignore
* ViewModel
* LiveData
* RecyclerView with click-action interfaces
* View Bindings
* Date Picker
* Fragment Manager for shifting between fragments
* OnBackPressedDispatcher for handling the Back button events
* Nested Fragments
* SavedInstanceState for saving data through screen rotation
* Popup menu
* AlertDialog
* Flow
* Coroutines
* Guidlines
* Constraint Layout
* Styles and Themes
* Loading Progress Bar
* Snackbars
* Landscape view
