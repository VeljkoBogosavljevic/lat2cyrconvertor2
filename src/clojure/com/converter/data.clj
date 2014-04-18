(ns com.converter.data)

; Define user structure and help functions
(defstruct user :username :password)
(def get-username (accessor user :username))
(def get-password (accessor user :password))

; Define user and initial username and password
(def user-main (atom (struct-map user :username "admin" :password "admin")))

(defn update-user "Updates user username and password from given parameters" [username password]
  (swap! user-main conj {:username username :password password}))

; Defines history of converted words and/or sentences and adds initial history
(def history (atom (vector {:lat "Clojure" :cyr "Цлојуре"} {:lat "Android" :cyr "Андроид"})))

(defn update-history "Update history from given parameters as latin and cyrillic text" [lat cyr]
  (swap! history conj {:lat lat :cyr cyr}))