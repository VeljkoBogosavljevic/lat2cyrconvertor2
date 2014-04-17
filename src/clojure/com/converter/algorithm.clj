(ns com.converter.algorithm)

(def lat2cyrmap {:A "А", :a "а", :B "Б", :b "б", :C "Ц", :c "ц", :Č "Ч", :č "ч", :Ć "Ћ", :ć "ћ", :D "Д", :d "д", :Đ "Ђ", :đ "ђ", 
                 :E "Е", :e "е", :F "Ф", :f "ф", :G "Г", :g "г", :H "Х", :h "х",
                 :I "И", :i "и", :J "Ј", :j "ј", :K "К", :k "к", :L "Л", :l "л", :M "М", :m "м", :N "Н", :n "н", :O "О", :o "о", 
                 :P "П", :p "п", :R "Р", :r "р", :S "С", :s "с", :Š "Ш", :š "ш", 
                 :T "Т", :t "т", :U "У", :u "у", :V "В", :v "в", :Z "З", :z "з", :Ž "Ж", :ž "ж", :Lj "Љ", :LJ "Љ", :lj "љ",
                 :Nj "Њ", :NJ "Њ", :nj "њ", :Dj "Ђ", :DJ "Ђ", :dj "ђ", :Dz "Џ", 
                 :DZ "Џ", :dz "џ", :Dž "Џ", :DŽ "Џ", :dž "џ"})



(defn make-str-from-chars
     "make a string from a sequence of  characters"
     ([chars] (make-str-from-chars chars ""))
     ([chars result]
        (if-let [chars (seq chars)]
          (recur (next chars) (str result (first chars)))
          result)))



(defn make-chars-from-string 
  "make a sequence of characters from a string"
  ([string] (make-chars-from-string string (vector)))
  ([string result]
   (if-let [string (seq string)]
     (cond
       (= "L" (str (first string))) (cond (= "j" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "j")))
                                          (= "J" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "J"))) 
                                          :else (recur (next string) (conj result (str (first string)))))
       (= "l" (str (first string))) (if (= "j" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "j"))) (recur (next string) (conj result (str (first string)))))
       (= "N" (str (first string))) (cond (= "j" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "j")))
                                          (= "J" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "J"))) 
                                          :else (recur (next string) (conj result (str (first string)))))
       (= "n" (str (first string))) (if (= "j" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "j"))) (recur (next string) (conj result (str (first string)))))
       (= "D" (str (first string))) (cond (= "j" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "j")))
                                          (= "J" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "J")))
                                          (= "Z" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "Z")))
                                          (= "z" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "z")))
                                          (= "Ž" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "Ž")))
                                          (= "ž" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "ž")))
                                          :else (recur (next string) (conj result (str (first string)))))
       (= "d" (str (first string))) (cond (= "j" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "j")))
                                          (= "z" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "z")))
                                          (= "ž" (str (first (next string)))) (recur (next (next string)) (conj result (str (first string) "ž")))
                                          :else (recur (next string) (conj result (str (first string)))))
       :else (recur (next string) (conj result (str (first string))))
       )result)))

(defn convert-to-cyr-alg 
  "converts latin sequnece of characters to cyrillic"
  ([latintext] (convert-to-cyr-alg latintext (vector)))
  ([latintext result]
    (if (not (empty? latintext))
      (cond
        (= nil (lat2cyrmap (keyword (first latintext)))) (recur (rest latintext) (conj result (first latintext)))
        :else (recur (rest latintext) (conj result (lat2cyrmap (keyword (first latintext)))))) result)))

(defn convert-to-cyr
  "converts latin text to cyrillic"
  [latintext]
  (make-str-from-chars (convert-to-cyr-alg (make-chars-from-string latintext))))



