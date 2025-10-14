(ns euler.problem7-test
  (:require [clojure.java.shell :as shell]
            [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            [lab1.problem7 :as p7]
            [lab1.problem7-alt :as p7alt]))

(defn run-python-script [script-path]
  (let [result (shell/sh "python3" script-path)]
    (if (zero? (:exit result))
      (str/trim (:out result))
      (throw (Exception. (str "Python error: " (:err result)))))))

(deftest prime?-basics
  (testing "Корректность функции prime?"
    (is (false? (p7/prime? 0)))
    (is (false? (p7/prime? 1)))
    (is (true? (p7/prime? 2)))
    (is (true? (p7/prime? 3)))
    (is (false? (p7/prime? 4)))
    (is (true? (p7/prime? 29)))
    (is (false? (p7/prime? 100)))))

(deftest small-primes
  (testing "Первые простые числа через ленивую последовательность"
    (is (= [2 3 5 7 11 13]
           (take 6 p7/primes-lazy)))))

(deftest solve-7-all-implementations
  (testing "Все реализации должны вернуть 10001-й простой"
    (is (= 104743 (p7/solve-7-tail)))
    (is (= 104743 (p7/solve-7-recursive)))
    (is (= 104743 (p7/solve-7-modular)))
    (is (= 104743 (p7/solve-7-map)))
    (is (= 104743 (p7/solve-7-loop)))
    (is (= 104743 (p7/solve-7-lazy)))
    (is (= 104743 (p7alt/solve-7-sieve)))))

(deftest consistency-check
  (testing "Все реализации задачи 7 возвращают одинаковый результат"
    (let [results (map (fn [f] (f))
                       [p7/solve-7-tail
                        p7/solve-7-recursive
                        p7/solve-7-modular
                        p7/solve-7-map
                        p7/solve-7-loop
                        p7/solve-7-lazy
                        p7alt/solve-7-sieve])]
      (is (apply = results)))))

(deftest euler7-correctness
  (testing "Сравнение всех реализаций задачи 7 (10001-е простое число)"
    (let [expected "104743"
          clojure-results [(str (p7/solve-7-tail))
                           (str (p7/solve-7-recursive))
                           (str (p7/solve-7-map))
                           (str (p7/solve-7-loop))
                           (str (p7/solve-7-lazy))
                           (str (p7alt/solve-7-sieve))]
          python-result (run-python-script "./src/lab1/python_euler7.py")]
      (is (every? #(= expected %) clojure-results))
      (is (= expected python-result)))))
