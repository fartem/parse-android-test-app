<img src="media/ic_app.png" height="100px" />

![Open issues](https://img.shields.io/github/issues-raw/fartem/parse-android-test-app.svg?color=ff534a)
![Last commit](https://img.shields.io/github/last-commit/fartem/parse-android-test-app.svg?color=51539c)
![Repo size](https://img.shields.io/github/repo-size/fartem/parse-android-test-app.svg?color=02778b)
[![License](https://img.shields.io/github/license/fartem/parse-android-test-app.svg?color=7ea4b0)](https://github.com/fartem/parse-android-test-app/blob/master/LICENSE)

## About

Test Android application for [Parse test server](https://github.com/fartem/parse-test-server).

## Information

### Features

- managing simple entity (Note);
- sync data with server;
- restore data from server;
- sign up from application with email address or Facebook account;
- sign in to server with email or Facebook.

### Not handling exceptions

- Internet connection unavailable status;
- Parse Server connection unavailable status.

## Data structure

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
