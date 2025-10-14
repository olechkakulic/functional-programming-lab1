(ns euler.problem24-test
  (:require
   [clojure.java.shell :as shell]
   [clojure.string :as str]
   [clojure.test :refer [deftest is testing]]
   [lab1.problem24 :as p24]
   [lab1.problem24-alt :as p24alt]))

(defn run-python-script [script-path]
  (let [result (shell/sh "python3" script-path)]
    (if (zero? (:exit result))
      (str/trim (:out result))
      (throw (Exception. (str "Python error: " (:err result)))))))

(deftest factorial-test
  (is (= 1 (p24/factorial 0)))
  (is (= 1 (p24/factorial 1)))
  (is (= 120 (p24/factorial 5))))

(deftest small-permutations
  (testing "Малые множества (сравнение с известными перестановками)"
    ;; Для [0 1 2] все перестановки:
    ;; 012, 021, 102, 120, 201, 210
    (is (= ["0" "1" "2"]
           (p24/nth-lexicographic-permutation ["0" "1" "2"] 1)))
    (is (= ["2" "1" "0"]
           (p24/nth-lexicographic-permutation ["0" "1" "2"] 6)))))

(deftest solve-24-all-implementations
  (testing "Все реализации задачи 24 должны вернуть одинаковый результат"
    (is (= "2783915460" (p24/solve-24)))
    (is (= "2783915460" (p24/solve-24-tail)))
    (is (= "2783915460" (p24alt/millionth-permutation)))))

(deftest consistency-check
  (testing "Сравнение всех реализаций задачи 24"
    (let [results [(p24/solve-24)
                   (p24/solve-24-tail)
                   (p24alt/millionth-permutation)]]
      (is (apply = results)))))

(deftest euler24-correctness
  (testing "Сравнение всех реализаций задачи 24 (миллионная перестановка)"
    (let [expected "2783915460"
          clojure-results [(p24/solve-24)
                           (p24/solve-24-tail)
                           (p24alt/millionth-permutation)]
          python-result (run-python-script "./src/lab1/python_euler24.py")]
      (is (every? #(= expected %) clojure-results))
      (is (= expected python-result)))))
