(ns euler.problem24-test
  (:require
   [clojure.java.shell :as shell]
   [clojure.string :as str]
   [clojure.test :refer [deftest is testing]]
   [lab1.problem24 :as p24]))

(defn run-python-script [script-path]
  (let [result (shell/sh "python3" script-path)]
    (if (zero? (:exit result))
      (str/trim (:out result))
      (throw (Exception. (str "Python error: " (:err result)))))))

(deftest factorial-test
  (is (= 1 (p24/factorial 0)))
  (is (= 1 (p24/factorial 1)))
  (is (= 120 (p24/factorial 5))))

(deftest solve-24-all-implementations
  (testing "Все реализации задачи 24 должны вернуть одинаковый результат"
    (is (= "2783915460" (p24/solve-24-tail)))
    (is (= "2783915460" (p24/solve-24-recursive)))
    (is (= "2783915460" (p24/solve-24-modular)))
    (is (= "2783915460" (p24/solve-24-bruteforce)))))

(deftest consistency-check
  (testing "Сравнение всех реализаций задачи 24"
    (let [results [(p24/solve-24-tail)
                   (p24/solve-24-recursive)
                   (p24/solve-24-modular)
                   (p24/solve-24-bruteforce)]]
      (is (apply = results)))))

(deftest euler24-correctness
  (testing "Сравнение всех реализаций задачи 24 (миллионная перестановка)"
    (let [expected "2783915460"
          clojure-results [(p24/solve-24-tail)
                           (p24/solve-24-recursive)
                           (p24/solve-24-modular)
                           (p24/solve-24-bruteforce)]
          python-result (run-python-script "./src/lab1/python_euler24.py")]
      (is (every? #(= expected %) clojure-results))
      (is (= expected python-result)))))
