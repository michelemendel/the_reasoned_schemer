(ns the-reasoned-schemer.chapter3
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [the-reasoned-schemer.utils :refer :all]))

;;3.Seeing Old Friends in New Ways

;;1
;;use seq? instead of list?
(seq? '((a)(a b)(c)))

;;2
(seq? '())

;;3
(seq? 's)

;;4
;;not a proper list
(seq? (llist 'd 'a 't 'e 's)) ;-> false
;;compare with
(seq? (list 'd 'a 't 'e 's)) ;-> true

;;5-8
(defn listo [l]
  (conde
    ((emptyo l))
    ((pairo l)
     (fresh (d)
       (resto l d)
       (listo d)))))

;;9,10
(run* [x]
  (listo (list 'a 'b x 's)))


























