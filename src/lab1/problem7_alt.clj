(ns lab1.problem7-alt)

(defn sieve
  []
  (letfn [(composite? [n primes]
            (some #(zero? (mod n %))
                  (take-while #(<= (* % %) n) primes)))
          (sieve-gen [n primes]
            (lazy-seq
             (if (composite? n primes)
               (sieve-gen (inc n) primes)
               (cons n (sieve-gen (inc n) (conj primes n))))))]
    (cons 2 (sieve-gen 3 [2]))))

(defn solve-7-sieve
  []
  (nth (sieve) 10000))

