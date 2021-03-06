(ns cider.nrepl.middleware.trace-test
  (:use clojure.test
        cider.nrepl.middleware.test-transport
        cider.nrepl.middleware.trace))

(deftest test-toogle-trace-op
  (let [transport (test-transport)]
    (toggle-trace {:transport transport :ns "clojure.core" :sym "map"})
    (is (= (messages transport) [{:value "#'clojure.core/map traced." :status #{:done}}]))
    (toggle-trace {:transport transport :ns "clojure.core" :sym "map"})
    (is (= (messages transport) [{:value "#'clojure.core/map traced." :status #{:done}}
                                 {:value "#'clojure.core/map untraced." :status #{:done}}]))))

(deftest test-toogle-trace-op-missing-var
  (let [transport (test-transport)]
    (toggle-trace {:transport transport :ns "clojure.core" :sym "mappp"})
    (is (= (messages transport) [{:value "no such var" :status #{:toggle-trace-error :done}}]))))
