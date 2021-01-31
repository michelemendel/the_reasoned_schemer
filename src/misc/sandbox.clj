(ns misc.sandbox
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [clojure.core.logic.fd :as fd]
            [trs-clj.utils :refer :all]))


(defn sqro [p n]
  (println [p n (number? p) (number? n)] "\n"
           (project [p] (p)))
  (if (and (number? n) (< n 0))
    u#
    (project [p] (== (fd/= p p) n))))

[(sqro 4 16)
 (sqro 4 3)
 (sqro 4 -3)]

(run 2 [q]
  (sqro 4 q))

(run 2 [q]
  (sqro q 4))







