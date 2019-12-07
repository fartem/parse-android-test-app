<img src="media/ic_app.png" height="100px" />

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-site-brightgreen?style=flat-square)](https://android-arsenal.com/details/3/7906)
![Open issues](https://img.shields.io/github/issues-raw/fartem/parse-android-test-app.svg?color=ff534a&style=flat-square)
![Last commit](https://img.shields.io/github/last-commit/fartem/parse-android-test-app.svg?color=51539c&style=flat-square)
![Repo size](https://img.shields.io/github/repo-size/fartem/parse-android-test-app.svg?color=02778b&style=flat-square)
[![License](https://img.shields.io/github/license/fartem/parse-android-test-app.svg?color=7ea4b0&style=flat-square)](https://github.com/fartem/parse-android-test-app/blob/master/LICENSE)

## About

Test Android application for [Parse test server](https://github.com/fartem/parse-test-server).

## Information

### Features

- managing the simple entity (Note);
- sync data with the server;
- restore data from the server;
- sign up from the application with email address or Facebook account;
- sign in to the server with email or Facebook.

### Not handling exceptions

- Internet connection unavailable status;
- Parse Server connection unavailable status.

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
