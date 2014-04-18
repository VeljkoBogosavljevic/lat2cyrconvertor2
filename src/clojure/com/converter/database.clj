(ns com.converter.database)
(require '[clojure.java.jdbc :as jdbc])

; Defines Database specifications
(def db-spec {:classname "org.sqlite.JDBC"
                 :subprotocol "sqlite"
                 :subname "converter.db"})

; Defines tables and delete records if they exists
(jdbc/with-connection db-spec
  (jdbc/do-commands "CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, username VARCHAR(100), password VARCHAR(100))"
                    "DELETE FROM user"
                    "CREATE TABLE IF NOT EXISTS history (id INTEGER PRIMARY KEY, latin TEXT, cyrillic TEXT)"
                    "DELETE FROM history"))

; Sets initial user in user table
(jdbc/with-connection db-spec
     (jdbc/insert-record :user
                         {:id 1 :username "admin" :password "admin"}))

;Sets initial data in history table
(jdbc/with-connection db-spec
     (jdbc/insert-records :history
                         {:latin "Clojure" :cyrillic "Цлојуре"}
                         {:latin "Android" :cyrillic "Андроид"}))

(defn- select-user "Select record from user table" [] 
  (jdbc/with-connection db-spec
     (jdbc/with-query-results res ["SELECT * FROM user"]
       (doall res))))

(defn update-user "Updates username and password of user from given parameters [username password]" [username password]
  (jdbc/with-connection db-spec
     (jdbc/update-values :user [:id 1]
                         {:username username :password password})))

(defn get-username "Returns user username" []
  ((first (select-user)) :username))

(defn get-password "Returns user password" []
  ((first (select-user)) :password))

(defn select-history "Select record from user table" [] 
  (jdbc/with-connection db-spec
     (jdbc/with-query-results res ["SELECT * FROM history"]
       (doall res))))

(defn insert-history-data "Insert new row in history table from given parameter [latin cyrillic]" [latin cyrillic]
  (jdbc/with-connection db-spec
     (jdbc/insert-record :history
                         {:latin latin :cyrillic cyrillic})))