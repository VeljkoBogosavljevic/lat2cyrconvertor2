(ns com.converter.main
  (:use [neko.activity]
        [neko.threading :only [on-ui]]
        [neko.ui :only [make-ui]]
        [neko.notify]
        [neko.action-bar])
  (:import android.widget.EditText
           android.content.Intent
           android.widget.Toast)
  (:require [com.converter.data :as data]
            [com.converter.algorithm :as algorithm]))

(declare android.widget.LinearLayout mylayout)
(declare android.widget.LinearLayout homel2clayout)
(declare android.widget.LinearLayout homec2llayout)
(declare android.widget.LinearLayout historylayout)
(declare android.widget.LinearLayout changeuserlayout)
(declare exit-application login-notification login-attempt login change-layout set-tabs set-conversion-layout show-history change-credentials convert-latin)

(defn mt-text [] (atom ""))
(defn hs-text [] (atom ""))
(defn now [] (new java.util.Date))

(def text (mt-text))
(def history-text (hs-text))

(def main-layout [:linear-layout {:orientation :vertical
                                  :gravity :center
                                  :id-holder true
                                  :def `mylayout}
                  [:edit-text {:hint "Username"
                               :id ::username}]
                  [:edit-text {:hint "Password"
                               :id ::password}]
                  [:linear-layout {:orientation :horizontal
                                  :gravity :center
                                  :id-holder true}
                   [:button {:text "Login"
                             :on-click (fn [_] (login))}]
                   [:button {:text "Exit"
                             :on-click (fn [_] (exit-application))}]]
                  [:text-view {:text @text
                               :text-size [12 :dp]
                               :id ::loginNoticifation}]])

(def home-l2c-layout [:linear-layout {:orientation :vertical
                                  :gravity :center
                                  :id-holder true
                                  :def `homel2clayout}
                  [:text-view {:text "Welcome"
                               :text-size [18 :dp]
                               :id ::l2cwelcome}]
                   [:text-view { :text "Enter latin text to convert:"
                                :text-size [15 :dp]}]
                   [:edit-text {:hint "Latin"
                                :layout-width :fill
                                ;:layout-height :match
                                :id ::l2clatin}]
                  [:edit-text {:hint "Cyrillic"
                                :layout-width :fill
                                ;:layout-height :match
                                :id ::l2ccyrillic}]
                  [:linear-layout {:orientation :horizontal
                                  :gravity :center
                                  :id-holder true}
                   [:button {:text "Convert"
                             :on-click (fn [_] (convert-latin))
                             }]
                   [:button {:text "Exit"
                             :on-click (fn [_] (exit-application))}]] 
                 ])

(def home-c2l-layout [:linear-layout {:orientation :vertical
                                  :gravity :center
                                  :id-holder true
                                  :def `homec2llayout}
                  [:text-view {:text "Welcome"
                               :text-size [18 :dp]
                               :id ::c2lwelcome}]
                   [:text-view {:text "Enter cyrillic text to convert:"
                                :text-size [15 :dp]}]
                   [:edit-text {:hint "Cyrillic"
                                :layout-width :fill
                                ;:layout-height [60 :dp]
                                :id ::c2lcyrillic}]
                  [:edit-text {:hint "Latin"
                                :layout-width :fill
                               ;:layout-height [60 :dp]
                                :id ::c2llatin}]
                  [:linear-layout {:orientation :horizontal
                                  :gravity :center
                                  :id-holder true}
                   [:button {:text "Convert"
                             ;:on-click ()
                             }]
                   [:button {:text "Exit"
                             :on-click (fn [_] (exit-application))}]]  
                 ])

(def history-layout [:linear-layout {:orientation :vertical
                                  :gravity :center
                                  :id-holder true
                                  :def `historylayout}
                      
                     [:text-view {:text "History: \n"
                                :text-size [15 :dp]}]
                     [:text-view {:text "---"
                               :text-size [12 :dp]
                               :id ::historyview}]
                     [:linear-layout {:orientation :horizontal
                                  :gravity :center
                                  :id-holder true}
;                   [:button {:text "Show"
;                             :on-click (fn [_] (show-history)) ; to be commented
;                             }]
                   [:button {:text "Exit"
                             :on-click (fn [_] (exit-application))}]]])

(def change-user-layout  [:linear-layout {:orientation :vertical
                                  :gravity :center
                                  :id-holder true
                                  :def `changeuserlayout}
                          [:text-view {:text "Current username:"
                               :text-size [12 :dp]
                               :id ::changeuserutxtusername}]
                          [:text-view {:text "Current password:"
                               :text-size [12 :dp]
                               :id ::changeusertxtpassword}]
                          [:text-view {:text "\nChange user credentials: \n"
                                :text-size [15 :dp]}]
                          [:edit-text {:hint "Username"
                               :id ::changeuserusername}]
                          [:edit-text {:hint "Password"
                               :id ::changeuserpassword}]
                          [:linear-layout {:orientation :horizontal
                                  :gravity :center
                                  :id-holder true}
                           [:button {:text "Change"
                                     :on-click (fn [_] (change-credentials))
                                     }]
                           [:button {:text "Exit"
                                     :on-click (fn [_] (exit-application))}]]
                          ])


(defactivity com.converter.MainActivity
  :def a
  :on-create
  (fn [this bundle]
    (on-ui
     (set-content-view! a
      (make-ui main-layout)))))

(defn get-element [element layout]
  (str (.getText (element (.getTag layout)))))

(defn set-element [element s layout]
  (on-ui (.setText (element (.getTag layout)) s)))


(defn update-ui []
  (set-element ::loginNoticifation @text mylayout))

(defn login-attempt []
  (reset! text (str "Login failure ! Time: " (.format (java.text.SimpleDateFormat. "dd/MM/yyyy hh:mm:ss") (now)) "\n") )
  (update-ui))

 (defn login-notification []
  (fire :mynotification
      (notification :icon com.converter.R$drawable/ic_launcher
                    :ticker-text "Crypter - Login successful !"
                    :content-title "Crypter"
                    :content-text "Login successful !"
                    :action [:activity Intent/CATEGORY_HOME])))
 
 (defn login []
   (if (= (@data/user-main :username) (get-element ::username mylayout))
     (if (= (@data/user-main :password) (get-element ::password mylayout))
       (do (login-notification) (set-tabs))
       (login-attempt))
     (login-attempt)))

 (defn change-layout [layout]
   (on-ui
     (set-content-view! a
      (make-ui layout))))
 
 (defn make-history [s]
   (if (not (empty? s))
   (do
   (swap! history-text str "Latin: " ((first s) :lat) " - Cyrillic: " ((first s) :cyr) "\n")
   (recur (rest s)))))
 
 (defn reset-history []
   (reset! history-text ""))
 
 (defn show-history []
   (do (reset-history)
     (make-history @data/history)
     (set-element ::historyview @history-text historylayout)))
 
 (defn set-current-user []
   (do (set-element ::changeuserutxtusername (str "Current username: "(data/get-username @data/user-main)) changeuserlayout))
    (set-element ::changeusertxtpassword (str "Current password: "(data/get-password @data/user-main)) changeuserlayout))
 
 (defn change-credentials []
   (do (data/update-user (get-element ::changeuserusername changeuserlayout) (get-element ::changeuserpassword changeuserlayout))
     (exit-application)
     (.startActivity a (.getIntent a))))
 
 (defn convert-latin []
   (do (set-element ::l2ccyrillic (algorithm/convert-to-cyr (get-element ::l2clatin homel2clayout)) homel2clayout)
      (data/update-history (get-element ::l2clatin homel2clayout) (algorithm/convert-to-cyr (get-element ::l2clatin homel2clayout)))))
 
 (defn set-tabs[]
 (on-ui
  (neko.action-bar/setup-action-bar a
  {:title "Converter"
   :navigation-mode :tabs
   :display-options [:show-home :show-title :home-as-up]
   :subtitle "latin cyrillic"
   :tabs [
          [:tab {:text "Latin > Cyrillic"
                  :tab-listener (tab-listener
                                 :on-tab-selected (fn [tab fn]
                                                    (change-layout home-l2c-layout) ))}]
           [:tab {:text "Cyrillic > Latin"
                  :tab-listener (tab-listener
                                 :on-tab-selected (fn [tab ft]
                                                     (change-layout home-c2l-layout)))}]
           [:tab {:text "History"
                  :tab-listener (tab-listener
                                 :on-tab-selected (fn [tab ft]
                                                     (do (change-layout history-layout) (show-history))))}]
           [:tab {:text "User"
                  :tab-listener (tab-listener
                                 :on-tab-selected (fn [tab ft]
                                                     (do (change-layout change-user-layout) (set-current-user)) ))}]
           ]})))
 
 


(defn exit-application []
  (.finish a))


;(defn set-conversion-layout []
;  (do (change-layout back-layout)
;    (set-tabs)))

; (exit-application)
; (.startActivity a (.getIntent a))
