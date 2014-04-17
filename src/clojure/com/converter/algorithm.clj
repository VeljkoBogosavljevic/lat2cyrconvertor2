(ns com.converter.algorithm)

(def lat2cyrmap {:A "А", :a "а", :B "Б", :b "б", :C "Ц", :c "ц", :Č "Ч", :č "ч", :Ć "Ћ", :ć "ћ", :D "Д", :d "д", :Đ "Ђ", :đ "ђ", 
                 :E "Е", :e "е", :F "Ф", :f "ф", :G "Г", :g "г", :H "Х", :h "х",
                 :I "И", :i "и", :J "Ј", :j "ј", :K "К", :k "к", :L "Л", :l "л", :M "М", :m "м", :N "Н", :n "н", :O "О", :o "о", 
                 :P "П", :p "п", :R "Р", :r "р", :S "С", :s "с", :Š "Ш", :š "ш", 
                 :T "Т", :t "т", :U "У", :u "у", :V "В", :v "в", :Z "З", :z "з", :Ž "Ж", :ž "ж", :Lj "Љ", :LJ "Љ", :lj "љ",
                 :Nj "Њ", :NJ "Њ", :nj "њ", :Dj "Ђ", :DJ "Ђ", :dj "ђ", :Dz "Џ", 
                 :DZ "Џ", :dz "џ", :Dž "Џ", :DŽ "Џ", :dž "џ"})

(def cyr2latmap {:А "A", :а "a", :Б "B", :б "b", :В "V", :в "v", :Г "G", :г "g", :Д "D", :д "d", :Ђ "Đ", :ђ "đ", :Е "E", :е "e", 
                 :Ж "Ž", :ж "ž", :З "Z", :з "z", :И "I", :и "i", :Ј "J", :ј "j", :К "K", :к "k", :Л "L", :л "l", :Љ "Lj", :љ "lj",
                 :М "M", :м "m", :Н "N", :н "n", :Њ "Nj", :њ "nj", :О "O", :о "o", :П "P", :п "p", :Р "R", :р "r", :С "S", :с "s",
                 :Т "T", :т "t", :Ћ "Ć", :ћ "ć", :У "U", :у "u", :Ф "F", :ф "f", :Х "H", :х "h", :Ц "C", :ц "c", :Ч "Č", :ч "č", 
                 :Џ "Dž", :џ "dž", :Ш "Š", :ш "š"})



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

(defn convert-to-lat-alg 
  "converts cyrillic sequnece of characters to latin"
  ([cyrillictext] (convert-to-lat-alg cyrillictext (vector)))
  ([cyrillictext result]
    (if (not (empty? cyrillictext))
      (cond
        (= nil (cyr2latmap (keyword (first cyrillictext)))) (recur (rest cyrillictext) (conj result (first cyrillictext)))
        :else (recur (rest cyrillictext) (conj result (cyr2latmap (keyword (first cyrillictext)))))) result)))

(defn convert-to-cyr
  "converts latin text to cyrillic"
  [latintext]
  (make-str-from-chars (convert-to-cyr-alg (make-chars-from-string latintext))))

(defn convert-to-lat
  "converts cyrillic text to latin"
  [cyrillictext]
  (make-str-from-chars (convert-to-lat-alg (make-chars-from-string cyrillictext))))



