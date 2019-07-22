# parse-android-test-app

Test Android application as client-side for [Parse test server](https://github.com/fartem/parse-test-server)

## 1. Features

- managing simple entity (Note);
- sync data with server;
- restore data from server;
- sing up from application with email or Facebook account;
- sing in to server with email or Facebook.

## 2.Database

ORMLite. More on [official site](http://ormlite.com/)

## 3. Data structure

### 3.1 Entity

```kotlin
abstract class Entity(

    @DatabaseField(generatedId = true)
    var id: Long = -1

)
```

### 3.2 Note

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

### 3.2.1 Save note

```kotlin
```

### 3.2.2 Delete note

```kotlin
```

### 3.2.3 Get all notes from database

```kotlin
```

### 3.2.4 Delete all notes from database

```kotlin
```

## 4. Facebook install

- [Official Guide](https://developers.facebook.com/docs/facebook-login/android)
- [Get hash with openssl from Windows](https://github.com/magus/react-native-facebook-login/issues/297#issuecomment-433816732)

## 5. Permissions

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```


