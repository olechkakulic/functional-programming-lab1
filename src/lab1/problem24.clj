(ns lab1.problem24
  (:require [clojure.string :refer [join]]))

(defn factorial
  [n]
  (reduce *' 1 (range 1 (inc n))))

;; 1) ХВОСТОВАЯ РЕКУРСИЯ
(defn nth-perm-tail
  [digits k]
  (let [k (dec k)] 
    (loop [elems (vec digits)
           kk k             
           acc []]          
      (if (empty? elems)
        (join acc) 
        (let [m (count elems)
              f (factorial (dec m)) 
              idx (quot kk f)       
              chosen (nth elems idx) 
              new-elems (vec (concat (subvec elems 0 idx) (subvec elems (inc idx))))] 
          (recur new-elems (rem kk f) (conj acc chosen))))))) 

(defn solve-24-tail []
  (nth-perm-tail (map str (range 0 10)) 1000000))

;; 2) ОБЫЧНАЯ РЕКУРСИЯ 
(defn nth-perm-recursive
  [items k]
  (let [items (vec items)
        kk (dec k)]
    (if (empty? items)
      '() ; Базовый случай
      (let [m (count items)
            f (factorial (dec m))
            idx (quot kk f)
            chosen (nth items idx)
            remaining (vec (concat (subvec items 0 idx) (subvec items (inc idx))))
            remk (rem kk f)]
        (cons chosen (nth-perm-recursive remaining (inc remk)))))))

(defn solve-24-recursive []
  (join (nth-perm-recursive (map str (range 0 10)) 1000000)))

;; 3) МОДУЛЬНАЯ РЕАЛИЗАЦИЯ (генерация, фильтрация, свёртка)
(defn lazy-permutations
  [coll]
  (letfn [(lp [c]
            (lazy-seq
             (if (empty? c)
               (list '())
               (apply concat
                      (for [x c]
                        (map #(cons x %) (lp (remove #{x} c))))))))]
    (lp (vec coll))))

(defn generate-perms
  [digits]
  (lazy-permutations digits))

(defn find-nth-by-reduce
  [perms n]
  (second 
   (reduce (fn [[i _] p]
             (if (= i n)
               (reduced [i p]) 
               [(inc i) p]))
           [1 nil] 
           perms)))

(defn solve-24-modular
  []
  (let [perms (generate-perms (map str (range 0 10)))
        nth-perm (find-nth-by-reduce perms 1000000)]
    (join nth-perm)))

;; 6) РАБОТА С БЕСКОНЕЧНЫМИ (ЛЕНИВЫМИ) СПИСКАМИ
(defn solve-24-bruteforce
  []
  (let [all-perms (lazy-permutations (map str (range 0 10)))]
    (join (nth all-perms (dec 1000000)))))