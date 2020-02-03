<img src="media/logo/ic_app.png" height="100px" />

Random Notes
=============

![Travis CI](https://img.shields.io/travis/fartem/parse-android-test-app)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Parse%20Server%20Client-brightgreen.svg?style=flat)](https://android-arsenal.com/details/3/7906)
![Open issues](https://img.shields.io/github/issues-raw/fartem/parse-android-test-app.svg?color=ff534a)

About
-------------

Test Android application for [Parse test server](https://github.com/fartem/parse-test-server).

__Features__

* managing the simple entity (Note);
* sync data with the server;
* restore data from the server;
* sign up from the application with email address or Facebook account;
* sign in to the server with email or Facebook.

__Not handling exceptions__

* Internet connection unavailable status;
* Parse Server connection unavailable status.

Google auth
-------------

__Resources__

* [Official Guide](https://developers.google.com/identity/sign-in/android/start-integrating)

__Usage__

In `preferences.xml` replace this value to own analogue:

```xml
<string name="google_web_app_token_id">[APP_ID]</string>
```

Facebook auth
-------------

__Resources__

* [Official Guide](https://developers.facebook.com/docs/facebook-login/android)
* [Get hash with openssl in Windows](https://github.com/magus/react-native-facebook-login/issues/297#issuecomment-433816732)

__Usage__

In `preferences.xml` replace this values to own analogues:

```xml
<string name="facebook_app_id">[APP_ID]</string>
<string name="fb_login_protocol_scheme">fb[APP_ID]</string>
```

Screenshots
-------------

<p align="center">
  <img src="media/screenshots/screenshot_01.png" width="200" />
  <img src="media/screenshots/screenshot_02.png" width="200" />
  <img src="media/screenshots/screenshot_03.png" width="200" />
  <img src="media/screenshots/screenshot_04.png" width="200" />
</p>

Contributors
-------------------

* [@fartem](https://github.com/fartem) as Artem Fomchenkov
