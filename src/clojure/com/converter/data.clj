(ns com.converter.data)

(defstruct user :username :password)
(def get-username (accessor user :username))
(def get-password (accessor user :password))

(def user-main (atom (struct-map user :username "veljko" :password "1989")))

(defn update-user [username password]
  (swap! user-main conj {:username username :password password}))

(def history (atom (vector {:lat "Clojure" :cyr "Цлојуре"} {:lat "Android" :cyr "Андроид"})))

(defn update-history [lat cyr]
  (swap! history conj {:lat lat :cyr cyr}))