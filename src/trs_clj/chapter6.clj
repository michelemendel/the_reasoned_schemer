(ns trs-clj.chapter6
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [trs-clj.utils :refer :all]))

;;6.The Fun Never Ends

;;1
;;See utils for the defn of alwayso

(run 1 [q]
  (alwayso))

;;2
(run 1 [q]
  (conde
    [s#]
    [(alwayso)]))

;;4
;;Never finishes
#_(run* [q]
    (alwayso))

;;5
;;Never finishes
#_(run* [q]
    (conde
      [s#]
      [(alwayso)]))

;;6
(run 5 [q]
  (alwayso))

;;7
(run 5 [q]
  (== 'onion q)
  (alwayso))

;;8
;;Never finishes
#_(run 1 [q]
    (alwayso)
    u#)

;;9
(run 1 [q]
  (== 'garlic q)
  s#
  (== 'onion q))

;;10
;;Never finishes
#_(run 1 [q]
    (== 'garlic q)
    (alwayso)
    (== 'onion q))

;;11
(run 1 [q]
  (conde
    [(== 'garlic q) (alwayso)]
    [(== 'onion q)])
  (== 'onion q))

;;12
;;Never finishes
#_(run 2 [q]
    (conde
      [(== 'garlic q) (alwayso)]
      [(== 'onion q)])
    (== 'onion q))

;;13
(run 5 [q]
  (conde
    [(== 'garlic q) (alwayso)]
    [(== 'onion q) (alwayso)])
  (== 'onion q))

;;14,15
;;Neither succeeds nor fails
;;See utils for the defn nevero

(nevero)

;;16
;;Never ends
#_(run 1 [q]
    (nevero))

;;17
(run 1 [q]
  u#
  (nevero))

;;18
(run 1 [q]
  (conde
    [s#]
    [(nevero)]))

;;19
(run 1 [q]
  (conde
    [(nevero)]
    [s#]))

;;20
;;Never ends
#_(run 2 [q]
    (conde
      [s#]
      [(nevero)]))

;;21
;;Never ends
#_(run 1 [q]
    (conde
      [s#]
      [(nevero)])
    u#)

;;22
(run 5 [q]
  (conde
    [(nevero)]
    [(alwayso)]
    [(nevero)]))

;;23
(run 6 [q]
  (conde
    [(== 'spicy q) (nevero)]
    [(== 'hot q) (nevero)]
    [(== 'apple q) (alwayso)]
    [(== 'cider q) (alwayso)]))

;;24
(defn very-recursiveo []
  (conde
    [(nevero)]
    [(very-recursiveo)]
    [(alwayso)]
    [(very-recursiveo)]
    [(nevero)]))

;;25
(run 3 [q]
  (very-recursiveo))












