(ns misc.sandbox
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [clojure.core.logic.fd :as fd]
            [trs-clj.utils :refer :all]))


(defn sqro [p n]
  (fd/* p p n))

(run 2 [q]
  (fd/in q (fd/interval 0 Integer/MAX_VALUE))
  (sqro 3 q))

(run 2 [q]
  (fd/in q (fd/interval -100 100))
  (sqro q 16))

[(sqro 4 16)
 (sqro 4 3)
 (sqro 4 -3)]




(run 10 [q]
  (fresh [n]
    (fd/in n (fd/interval 0 10))
    ;;(fd/in n (fd/domain 0 5))
    ;;(fd/in n (fd/interval 0 Integer/MAX_VALUE))
    (== q n))
  )

