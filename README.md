# parse-android-test-app

Test Android application for [Parse test server](https://github.com/fartem/parse-test-server).

## 1. Information

### 1.1 Features

__Fully working communication with Parse test server:__

- managing simple entity (Note);
- sync data with server;
- restore data from server;
- sign up from application with email address or Facebook account;
- sign in to server with email or Facebook.

### 1.2 Not handling exceptions

- Internet connection unavailable status;
- Parse Server connection unavailable status.

## 2. Parse initialization

### 2.1 Application class

```kotlin
private fun initializeParse() {
    val applicationId = "APP_ID"
    val serverAddress = "SERVER_ADDRESS"
    val clientKey = "CLIENT_KEY"

    val parseConfig = Parse.Configuration.Builder(this)
        .applicationId(applicationId)
        .clientKey(clientKey)
        .server(serverAddress)
        .build()
    Parse.initialize(parseConfig)
    ParseFacebookUtils.initialize(this)
}
```

### 2.2 Requiring parameters

| Name | Description |
| --- | --- |
| APP_ID | Parse server app id |
| SERVER_ADDRESS | Address of Parse server in your network |
| CLIENT_KEY | Parse server client key |

### 2.3 Parse API

Parse API provided methods for managing entities in app.

#### 2.3.1 Save all notes

```kotlin
fun saveAllNotes(notes: List<Note>, errorOnSave: (e: Exception) -> Unit) {
    ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| notes | List<Note> | Notes to save |
| errorOnSave | Function | Error callback |

#### 2.3.2 Restore all notes

```kotlin
fun restoreAllNotes(notes: List<Note>, afterRestore: (e: Exception?) -> Unit) {
    ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| notes | List<Note> | Notes in local database |
| afterRestore | Function | Restore callback |

### 2.4 Parse Auth API

Parse Auth API provides methods for authorization with email or Facebook account.

#### 2.4.1 Authorization check

```kotlin
fun isAuthorized() = ParseUser.getCurrentUser() != null
```

#### 2.4.2 Register with email

```kotlin
fun register(username: String, email: String, password: String,
                 afterLogin: (e: Exception?) -> Unit) {
     ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| username | String | Name |
| email | String | Email |
| password | String| Password |
| afterLogin | Function | Login callback |

#### 2.4.3 Log in with email

```kotlin
fun logInWithEmail(email: String, password: String, afterLogin: (e: Exception?) -> Unit) {
    ...                       
}
```

| Argument | Type | Description |
| --- | --- | --- |
| email | String | Email |
| password | String| Password |
| afterLogin | Function | Login callback |

#### 2.4.4 Log in with Facebook

##### 2.4.4.1 Log in method

```kotlin
fun logInWithFacebook(fragment: Fragment, afterFacebookLogin: (success: Boolean) -> Unit) {
    ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| fragment | Fragment | Fragment (can be replaced to Activity) for user data request |
| afterFacebookLogin | Function | Login callback |

##### 2.4.4.1 Handling onActivityResult

```kotlin
fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| requestCode | Int | requestCode from request |
| resultCode | Int | resultCode from request |
| data | Intent | data from request |

#### 2.4.5 Logout

```kotlin
fun logout(afterLogout: (e: Exception) -> Unit) {
    ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| afterLogout | Function | Logout callback |

## 3.Database

ORMLite. More on [official site](http://ormlite.com/).

## 4. Data structure

This section contains full list of entities using in app.

### 4.1 Entity

Base class for all database entities.

```kotlin
abstract class Entity(

    @DatabaseField(generatedId = true)
    var id: Long = -1

)
```

### 4.2 Note

```kotlin
class Note(

    @DatabaseField
    var title: String? = null,

    @DatabaseField
    var subtitle: String? = null,

    @DatabaseField
    var parseObjectId: String? = null,

    var positionInList: Int = 0

) : Entity() {

    ...

}
```

### 4.2.1 Save note

__From Note class method:__
```kotlin
fun save() {
    DatabaseFactory.get().saveNote(this)
}
```

__Example:__
```kotlin
    val newNote = Note(title, subtitle)
    newNote.save()
    
```

### 4.2.2 Delete note

__From Note class method:__
```kotlin
fun delete() {
    DatabaseFactory.get().deleteNote(this)
}
```

__Example:__
```kotlin
    val note = notes[notes.size - 1]
    note.delete()
```

### 4.2.3 Get all notes from database

__From Note class method:__
```kotlin
    fun getAllNotes() = DatabaseFactory.get().allNotes
```

__Example:__
```kotlin
    val notes = Note.getAllNotes()
```

### 4.2.4 Delete all notes from database

__From Note class method:__
```kotlin
fun deleteAllNotes() {
    DatabaseFactory.get().deleteAllNotes()
}
```

__Example:__
```kotlin
    Note.deleteAllNotes()
```

### 4.2.5 Get ParseObject for note

__From Note class method:__
```kotlin
fun getParseObject(globalAccess: Boolean = true, user: ParseUser? = null): ParseObject {
    ...
}
```

__Example:__
```kotlin
val parseNote = newNote.getParseObject(false, authUser)
```

### 4.2.6 Initialize note from ParseObject

__From Note class method:__
```kotlin
fun restoreFromParseObject(parseObject: ParseObject): ParseObject {
    ...
}
```

__Example:__
```kotlin
for (obj in parseObjects) {
    val note = Note()
    note.restoreFromParseObject(obj)
    ...
}
```

## 5. Facebook install

### 5.1 Resources

- [Official Guide](https://developers.facebook.com/docs/facebook-login/android)
- [Get hash with openssl in Windows](https://github.com/magus/react-native-facebook-login/issues/297#issuecomment-433816732)

### 5.2 Usage

In `preferences.xml` replace this values to own analogues:

```xml
<string name="facebook_app_id">[APP_ID]</string>
<string name="fb_login_protocol_scheme">fb[APP_ID]</string>
```

## 6. Permissions

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

## 7. Screenshots
<p align="center">
  <img src="media/screenshot_01.png" width="200" />
  <img src="media/screenshot_02.png" width="200" />
  <img src="media/screenshot_03.png" width="200" />
  <img src="media/screenshot_04.png" width="200" />
</p>
