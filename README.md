<img src="media/ic_app.png" height="100px" />

![Open issues](https://img.shields.io/github/issues-raw/fartem/parse-android-test-app.svg?color=ff534a)
![Last commit](https://img.shields.io/github/last-commit/fartem/parse-android-test-app.svg?color=51539c)
![Repo size](https://img.shields.io/github/repo-size/fartem/parse-android-test-app.svg?color=02778b)
[![License](https://img.shields.io/github/license/fartem/parse-android-test-app.svg?color=7ea4b0)](https://github.com/fartem/parse-android-test-app/blob/master/LICENSE)

## About

Test Android application for [Parse test server](https://github.com/fartem/parse-test-server).

## Information

### Features

__Fully working communication with Parse test server:__

- managing simple entity (Note);
- sync data with server;
- restore data from server;
- sign up from application with email address or Facebook account;
- sign in to server with email or Facebook.

### Not handling exceptions

- Internet connection unavailable status;
- Parse Server connection unavailable status.

## Parse initialization

### Application class

```kotlin
private fun initializeParse() {
    val serverAddress = "SERVER_ADDRESS"
    val applicationId = "APP_ID"
    val clientKey = "CLIENT_KEY"
    
    ParseApi.initialize(this, serverAddress, applicationId, clientKey)
    ParseAuth.initialize(this)
}
```

### ParseApi class

```kotlin
fun initialize(context: Context, serverAddress: String, applicationId: String,
               clientKey: String) {
    val parseConfig = Parse.Configuration.Builder(context)
        .server(serverAddress)
        .applicationId(applicationId)
        .clientKey(clientKey)
        .build()
    Parse.initialize(parseConfig)
}
```

### Requiring parameters

| Name | Description |
| --- | --- |
| SERVER_ADDRESS | Address of Parse server in your network |
| APP_ID | Parse server app id |
| CLIENT_KEY | Parse server client key |

### Parse API

Parse API provided methods for managing entities in app.

#### Save all notes

```kotlin
fun saveAllNotes(notes: List<Note>, errorOnSave: (e: Exception) -> Unit) {
    ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| notes | `List<Note>` | Notes to save |
| errorOnSave | `Function` | Error callback |

#### Restore all notes

```kotlin
fun restoreAllNotes(notes: List<Note>, afterRestore: (e: Exception?) -> Unit) {
    ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| notes | `List<Note>` | Notes in local database |
| afterRestore | `Function` | Restore callback |

### Parse Auth API

Parse Auth API provides methods for authorization with email or Facebook account.

#### Authorization check

```kotlin
fun isAuthorized() = ParseUser.getCurrentUser() != null
```

#### Register with email

```kotlin
fun register(username: String, email: String, password: String,
                 afterLogin: (e: Exception?) -> Unit) {
     ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| username | `String` | Name |
| email | `String` | Email |
| password | `String` | Password |
| afterLogin | `Function` | Login callback |

#### Log in with email

```kotlin
fun logInWithEmail(email: String, password: String, afterLogin: (e: Exception?) -> Unit) {
    ...                       
}
```

| Argument | Type | Description |
| --- | --- | --- |
| email | `String` | Email |
| password | `String` | Password |
| afterLogin | `Function` | Login callback |

#### Log in with Facebook

##### Log in method

```kotlin
fun logInWithFacebook(fragment: Fragment, afterFacebookLogin: (success: Boolean) -> Unit) {
    ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| fragment | `Fragment` | Fragment (can be replaced to Activity) for user data request |
| afterFacebookLogin | `Function` | Login callback |

##### Handling onActivityResult

```kotlin
fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| requestCode | `Int` | requestCode from request |
| resultCode | `Int` | resultCode from request |
| data | `Intent` | data from request |

#### Logout

```kotlin
fun logout(afterLogout: (e: Exception) -> Unit) {
    ...
}
```

| Argument | Type | Description |
| --- | --- | --- |
| afterLogout | `Function` | Logout callback |

## Database

ORMLite. More on [official site](http://ormlite.com/).

## Data structure

This section contains full list of entities using in app.

### Entity

Base class for all database entities.

```kotlin
abstract class Entity(

    @DatabaseField(generatedId = true)
    var id: Long = -1

)
```

### Note

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

### Save note

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

### Delete note

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

### Get all notes from database

__From Note class method:__
```kotlin
    fun getAllNotes() = DatabaseFactory.get().allNotes
```

__Example:__
```kotlin
    val notes = Note.getAllNotes()
```

### Delete all notes from database

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

### Get ParseObject for note

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

### Initialize note from ParseObject

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

## Facebook install

### Resources

- [Official Guide](https://developers.facebook.com/docs/facebook-login/android)
- [Get hash with openssl in Windows](https://github.com/magus/react-native-facebook-login/issues/297#issuecomment-433816732)

### Usage

In `preferences.xml` replace this values to own analogues:

```xml
<string name="facebook_app_id">[APP_ID]</string>
<string name="fb_login_protocol_scheme">fb[APP_ID]</string>
```

## Permissions

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

## Screenshots

<p align="center">
  <img src="media/screenshot_01.png" width="200" />
  <img src="media/screenshot_02.png" width="200" />
  <img src="media/screenshot_03.png" width="200" />
  <img src="media/screenshot_04.png" width="200" />
</p>
