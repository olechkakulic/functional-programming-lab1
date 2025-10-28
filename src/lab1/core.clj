(ns lab1.core
  (:require
   [clojure.java.shell :as shell]
   [clojure.string :as str]
   [lab1.problem7 :as p7]
   [lab1.problem24 :as p24]))

(defn run-python [script]
  (let [result (shell/sh "python3" script)]
    (if (zero? (:exit result))
      (str/trim (:out result))
      (throw (Exception. (str "Python error: " (:err result)))))))

(defn -main
  "Печатает результаты всех реализаций задач."
  [& _]
  (println "Решение задачи 7 (найти 10_001-ое простое число)")
  (println "tail-recursive:  " (p7/solve-7-tail))
  (println "recursive:       " (p7/solve-7-recursive))
  (println "modular (filter/reduce idea):" (p7/solve-7-modular))
  (println "map-based:       " (p7/solve-7-map))
  (println "loop/recur:      " (p7/solve-7-loop))
  (println "lazy filter:     " (p7/solve-7-lazy))
  (println "Python:" (run-python "./src/lab1/python_euler7.py"))
  (println "\nВсе реализации должны давать одинаковый ответ: 104743")

  (println "\nПечатает результаты всех реализаций задачи 24.")
  (println "Project Euler #24 — Millionth lexicographic permutation")

  (println "tail variant (nth-perm-tail):" (p24/solve-24-tail))
  (println "recursive (non-tail):" (p24/solve-24-recursive))
  (println "modular (generate/filter/reduce):" (p24/solve-24-modular))
  (println "bruteforce (lazy):" (p24/solve-24-bruteforce))
  (println "Python:" (run-python "./src/lab1/python_euler24.py"))
  (System/exit 0))
