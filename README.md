<img src="media/logo/ic_app.png" height="100px" />

Random Notes
=============

[![Travis CI](https://img.shields.io/travis/fartem/parse-android-test-app)](https://travis-ci.org/fartem/parse-android-test-app)
[![Codebeat](https://codebeat.co/badges/674dcb3e-246b-4790-9a63-32f1c1c28371)](https://codebeat.co/projects/github-com-fartem-parse-android-test-app-master)
[![Codecov](https://codecov.io/gh/fartem/parse-android-test-app/branch/master/graph/badge.svg)](https://codecov.io/gh/fartem/parse-android-test-app)
[![Hits-of-Code](https://hitsofcode.com/github/fartem/parse-android-test-app)](https://hitsofcode.com/view/github/fartem/parse-android-test-app)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Parse%20Server%20Client-brightgreen.svg?style=flat)](https://android-arsenal.com/details/3/7906)

About
-------------

Test Android application for [Parse test server](https://github.com/fartem/parse-test-server).

__Features__

* sync data with the server;
* restore data from the server;
* sign up from the application with email address, Google or Facebook;
* sign in to the server with email address, Google or Facebook.

__Not handling exceptions__

* Internet disconnect;
* Parse Server connection status.

Google auth
-------------

__Resources__

* [Official Guide](https://developers.google.com/identity/sign-in/android/start-integrating).

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

Downloads
---------

<img src="media/qrcodes/github_download.png" height="150px" />

Screenshots
-------------

<p align="center">
  <img src="media/screenshots/screenshot_01.png" width="200" />
  <img src="media/screenshots/screenshot_02.png" width="200" />
  <img src="media/screenshots/screenshot_03.png" width="200" />
  <img src="media/screenshots/screenshot_04.png" width="200" />
</p>

How to contribute
-------------

Read [Commit Convention](https://github.com/fartem/repository-rules/blob/master/commit-convention/COMMIT_CONVENTION.md). Make sure your build is green before you contribute your pull request. Then:

```shell
gradlew clean
gradlew build
gradlew connectedCheck
```

If you don't see any error messages, submit your pull request.

Contributors
-------------------

* [@fartem](https://github.com/fartem) as Artem Fomchenkov
