(ns lab1.problem7)

;; Константа — целевой индекс
(def ^:const N 10001)

;; Безопасная проверка на простоту: возвращает false для nil / нецелых / < 2
(defn prime? [n]
  (if (and (integer? n) (>= n 2))
    (cond
      (= n 2) true
      (even? n) false
      :else (let [lim (long (Math/sqrt (double n)))]
              (not-any? #(zero? (mod n %)) (range 3 (inc lim) 2))))
    false))

;; Хвостовая рекурсия (monolithic)
(defn nth-prime-tail
  "Возвращает n-ый простой (1-based). Хвостовая рекурсия."
  [n]
  (loop [candidate 2 found 0]
    (if (prime? candidate)
      (if (= (inc found) n)
        candidate
        (recur (inc candidate) (inc found)))
      (recur (inc candidate) found))))

(defn solve-7-tail []
  (nth-prime-tail N))

;; Рекурсивный вариант
(defn nth-prime-recursive
  ([n] (trampoline nth-prime-recursive n 2 0))
  ([n candidate found]
   (if (prime? candidate)
     (if (= (inc found) n)
       candidate
       #(nth-prime-recursive n (inc candidate) (inc found)))
     #(nth-prime-recursive n (inc candidate) found))))

(defn solve-7-recursive []
  (nth-prime-recursive N))

;; Ленивые бесконечные последовательности
(def primes-lazy
  (filter prime? (iterate inc 2)))

(defn solve-7-lazy []
  (nth primes-lazy (dec N)))

;; Map / iterate вариант (простой)
(defn solve-7-map []
  (nth (filter prime? (iterate inc 2)) (dec N)))

;; Модульная реализация: оценка верхней границы → генерация → фильтрация → извлечение
(defn upper-bound-nth-prime
  "Оценка верхней границы для n-го простого: n*(log n + log log n) (для n >= 6)."
  [n]
  (if (< n 6) 15
      (let [n-d (double n)
            ln  (Math/log n-d)
            lln (Math/log ln)]
        (long (Math/ceil (* n-d (+ ln lln)))))))

(defn primes-up-to
  "Ленивая последовательность простых до bound (через prime?)."
  [bound]
  (filter prime? (range 2 (inc bound))))

(defn solve-7-modular []
  (let [ub (upper-bound-nth-prime N)]
    (nth (primes-up-to ub) (dec N))))

;; Loop/recur (явный цикл)
(defn solve-7-loop []
  (loop [candidate 2 found 0]
    (if (prime? candidate)
      (if (= (inc found) N)
        candidate
        (recur (inc candidate) (inc found)))
      (recur (inc candidate) found))))

(defn all-solves []
  {:tail (solve-7-tail)
   :rec  (solve-7-recursive)
   :mod  (solve-7-modular)
   :map  (solve-7-map)
   :loop (solve-7-loop)
   :lazy (solve-7-lazy)})