(ns lab1.problem7)

(def ^:const N 10001)

(defn prime?
  [n]
  (if (and (integer? n) (>= n 2))
    (cond
      (= n 2) true
      (even? n) false
      :else (let [lim (long (Math/sqrt (double n)))]
              (not-any? #(zero? (mod n %)) (range 3 (inc lim) 2))))
    false))

;; 1) Хвостовая рекурсия 
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

;; 2) Рекурсивный вариант 
(defn nth-prime-recursive
  [n]
  (if (= n 1)
    2
    (let [prev-prime (nth-prime-recursive (dec n))]
      (loop [candidate (inc prev-prime)]
        (if (prime? candidate)
          candidate
          (recur (inc candidate)))))))

(defn solve-7-recursive
  []
  (nth-prime-recursive N))

;; 6) Ленивые бесконечные последовательности
(def primes-lazy
  (filter prime? (iterate inc 2)))

(defn solve-7-lazy []
  (nth primes-lazy (dec N)))

;; 4) Генерация при помощи ФВП (iterate/filter)
(defn solve-7-map
  []
  (nth (filter prime? (iterate inc 2)) (dec N)))

;; 3) Модульная реализация: оценка верхней границы -> генерация -> фильтрация -> извлечение
(defn upper-bound-nth-prime
  "Оценка верхней границы для n-го простого: n*(log n + log log n) (для n >= 6)."
  [n]
  (if (< n 6) 15
      (let [n-d (double n)
            ln  (Math/log n-d)
            lln (Math/log ln)]
        (long (Math/ceil (* n-d (+ ln lln)))))))

(defn primes-up-to
  [bound]
  (filter prime? (range 2 (inc bound))))

(defn solve-7-modular
  []
  (let [ub (upper-bound-nth-prime N)]
    (nth (primes-up-to ub) (dec N))))

;; 5) Loop/recur то же самое что и хвостовая рекурсия. 
(defn solve-7-loop
  []
  (loop [candidate 2 found 0]
    (if (prime? candidate)
      (if (= (inc found) N)
        candidate
        (recur (inc candidate) (inc found)))
      (recur (inc candidate) found))))

