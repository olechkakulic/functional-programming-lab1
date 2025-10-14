(ns lab1.problem24
  (:require [clojure.string :refer [join]]))

;; Быстрый factorial (возвращает BigInt при больших n)
(defn factorial [n]
  (reduce *' 1 (range 1 (inc n))))

;; ----- аналитический алгоритм: nth lexicographic permutation -----
;; items - последовательность (вектор или список) элементов
;; n - 1-based индекс искомой перестановки
(defn nth-lexicographic-permutation
  [items n]
  (let [items (vec items)
        n (dec n)]
    (loop [avail items
           k n
           result []]
      (if (empty? avail)
        result
        (let [m (count avail)
              f (factorial (dec m))
              idx (quot k f)
              chosen (nth avail idx)
              new-avail (vec (concat (subvec avail 0 idx) (subvec avail (inc idx))))]
          (recur new-avail (rem k f) (conj result chosen)))))))

(defn solve-24 []
  (join (nth-lexicographic-permutation (map str (range 0 10)) 1000000)))

;; ----- хвостовой вариант (альтернативная стилистика) -----
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

;; ----- ленивый брутфорс -----
(defn permutations
  [coll]
  (if (empty? coll)
    (list '())
    (apply concat
           (for [x coll]
             (map #(cons x %) (permutations (remove #{x} coll)))))))

(defn solve-24-bruteforce []
  (let [perms (permutations (map str (range 0 10)))]
    (join (nth (seq perms) (dec 1000000)))))
