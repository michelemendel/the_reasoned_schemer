(ns trs-clj.chapter6
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [trs-clj.utils :refer :all]))

;;6.The Fun Never Ends

;;1
#_(defn alwayso []
  (conde
    [s#]
    [(alwayso)]))

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
#_(defn nevero []
  (nevero))

;;Blows the stack
;;(nevero)

;;16
;;Blows the stack
#_(run 1 [q]
  (nevero))

;;17
;;Not the same result as TRS
;;This blows the stack
(run 1 [q]
  u#
  (nevero))

;;18
(run 1 [q]
  (conde
    [s#]
    [(nevero)]))

;;19
;;Not the same result as TRS
;;This blows the stack
#_(run 1 [q]
  (conde
    [(nevero)]
    [s#]))

;;20
;;Blows the stack
#_(run 2 [q]
  (conde
    [s#]
    [(nevero)]))

;;21
;;Blows the stack
#_(run 1 [q]
  (conde
    [s#]
    [(nevero)])
  u#)

;;22
;;Not the same result as TRS
;;This blows the stack
#_(run 5 [q]
  (conde
    [(nevero)]
    [(alwayso)]
    [(nevero)]))

;;23
;;Not the same result as TRS
;;This blows the stack
#_(run 6 [q]
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
;;Not the same result as TRS
;;This blows the stack
#_(run 3 [q]
  (very-recursiveo))
















