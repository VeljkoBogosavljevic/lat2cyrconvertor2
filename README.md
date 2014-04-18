lat2cyrconvertor2
=================

Clojure Android Latin to Cyrillic Converter


This is Android Latin to Cyrillic and Cyrillic to Latin converter developed in Clojure using lein droid "0.2.2" plugin. Plugin dependency for lein droid is defined in ~lein.profiles.clj:

:plugins [
            [lein-droid "0.2.2"]
            ...
            ]

In ~lein.profiles.clj is also defined java and android sdk path. Make sure there is no spaces in paths !

:java-cmd "C:\\Program Files\\Java\\jdk1.7.0_03\\bin\\java.exe"
:android {:sdk-path "C:\\Users\\Veljko\\AppData\\Local\\Android\\android-studio\\sdk"}





