(defproject euler "0.1.0-SNAPSHOT"
  :description "Project Euler solutions for lab — problems 7 and 24"
  :url "https://example.org/your-repo"
  :license {:name "MIT"}

  :min-lein-version "2.9.0"
  :dependencies [[org.clojure/clojure "1.12.2"]]
  :main ^:skip-aot lab1.core
  :target-path "target/%s"
  :plugins [[lein-cljfmt "0.8.2"]
            [lein-kibit "0.1.8"]
            [lein-bikeshed "0.5.2"]]
  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev     {:dependencies [[midje "1.10.9"]
                                      [org.clojure/test.check "1.1.1"]
                                      [pjstadig/humane-test-output "0.11.0"]
                                      [clj-kondo "2023.10.20"]]}}
  :test-selectors {:default     (complement :integration)
                   :integration :integration
                   :all         (constantly true)}
  :aliases {"lint" ["do"
                    ["cljfmt" "check"]
                    ["kibit"]
                    ["bikeshed" "--max-line-length" "120"]
                    ["run" "-m" "clj-kondo.main" "--lint" "src" "test"]]})
