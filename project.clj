(defproject org.clojure/test.check "0.9.1-SNAPSHOT"
  :description "A QuickCheck inspired property-based testing library."
  :url "https://github.com/clojure/test.check"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies []
  :source-paths ["src/main/clojure"]
  :test-paths ["src/test/clojure"]
  :jvm-opts ^:replace ["-Xmx512m" "-server"]
  :profiles {:codox {:dependencies [[viebel/codox-klipse-theme "0.0.3"]]}
             :dev {:dependencies [[org.clojure/clojure "1.8.0"]
                                  [org.clojure/clojurescript "1.9.293"]]}
             :self-host {:dependencies [[org.clojure/clojure "1.8.0"]
                                        [org.clojure/clojurescript "1.9.227"]]
                         :main clojure.main
                         :global-vars {*warn-on-reflection* false}}}
  :global-vars {*warn-on-reflection* true}
  :plugins [[lein-codox "0.10.1"]
            [lein-cljsbuild "1.1.4"]]
  :codox {:namespaces [clojure.test.check
                       clojure.test.check.clojure-test
                       clojure.test.check.generators
                       clojure.test.check.properties]
          :metadata {:doc/format :markdown}
          :themes [:default [:klipse {:klipse/selector ".clojure"
                                      :klipse/explicit-load true
                                      :klipse/external-libs  "src/main/clojure"
                                      :klipse/require-statement "(ns my.test
                                                                  (:require [clojure.test.check :as tc :refer [quick-check]]
                                                                            [clojure.test.check.generators :as gen]
                                                                            [clojure.test.check.properties :as prop :include-macros true]))"}]]
          }
  :cljsbuild
  {:builds
   [{:id "node-dev"
     :source-paths ["src/main/clojure" "src/test/clojure"
                    "src/target/cljs/node"]
     :notify-command ["node" "resources/run.js"]
     :compiler {:optimizations :none
                :static-fns true
                :target :nodejs
                :output-to "target/cljs/node_dev/tests.js"
                :output-dir "target/cljs/node_dev/out"
                :source-map true}}
    {:id "browser-dev"
     :source-paths ["src/main/clojure" "src/test/clojure"
                    "src/target/cljs/browser"]
     :compiler {:optimizations :none
                :static-fns true
                :output-to "target/cljs/browser_dev/tests.js"
                :output-dir "target/cljs/browser_dev/out"
                :source-map true}}
    {:id "node-adv"
     :source-paths ["src/main/clojure" "src/test/clojure"
                    "src/target/cljs/node"]
     :notify-command ["node" "target/cljs/node_adv/tests.js"]
     :compiler {:optimizations :advanced
                :target :nodejs
                :pretty-print false
                :output-to "target/cljs/node_adv/tests.js"
                :output-dir "target/cljs/node_adv/out"}}
    {:id "browser-adv"
     :source-paths ["src/main/clojure" "src/test/clojure"
                    "src/target/cljs/browser"]
     :compiler {:optimizations :advanced
                :pretty-print false
                :output-to "target/cljs/browser_adv/tests.js"
                :output-dir "target/cljs/browser_adv/out"}}]})
