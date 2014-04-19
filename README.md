Clojure Android Latin/Cyrillic Converter
=================



This is Android Latin to Cyrillic and Cyrillic to Latin converter developed in Clojure using lein droid "0.2.2" plugin.

Documentation: https://github.com/clojure-android/lein-droid

Plugin dependency for lein droid is defined in ~lein.profiles.clj:

:plugins [
            [lein-droid "0.2.2"]
            ...
            ]

In ~lein.profiles.clj is also defined java and android sdk path. Make sure there is no spaces in paths !

:java-cmd "C:\\Program Files\\Java\\jdk1.7.0_03\\bin\\java.exe"

:android {:sdk-path "C:\\Users\\Veljko\\AppData\\Local\\Android\\android-studio\\sdk"}

For list of lein droid commands enter lein droid in cmd.

Usage
=================

Application enables conversion of latin text to cyrillic and vice versa, cyrillic text to latin, both using Clojure algorithm, review history of converted words and changing user credentials. Application is developed as modern Android application with described funcionality divided by tabs:


1. LATIN > CYRILLIC - converts latin text to cyrillic
2. CYRILLIC > LATIN - converts cyrillic text to latin
3. HISTORY - shows history of converted words
4. USER - show current username and password and enables to change user credentials

To access application, first must to log in. Initial username and password are: usermane>admin , password>admin.

To install application on mobile device, just download lat2cyrconvertor2/target/converter-debug.apk and install it on your device.

For application screenshots check out https://github.com/VeljkoBogosavljevic/lat2cyrconvertor2/wiki. 

References
=================
Practical Clojure - Luke VanderHart, Stuart Sierra
Programming Collective Intellinegce - Toby Segaran
https://github.com/clojure-android/lein-droid
https://github.com/krisc/events/blob/master/tutorial.md
https://github.com/clojure-android/neko/wiki
http://clojure-android.github.io/neko/
and other tuttorials

License
=================
Distributed under the Eclipse Public License, the same as Clojure.
