(ns trs-clj.chapter9
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [clojure.pprint :refer [pprint]]
            [trs-clj.utils :refer :all]))

;;9.Thin Ice

;;1
(conda
  [(u# s#)]
  [(s# u#)])

;;2
(conda
  [(u# s#)]
  [(s# s#)])

;;3
(conda
  [(s# u#)]
  [(s# s#)])

;;4
(conda
  [(s# s#)]
  [(s# u#)])

;;5
(run* [x]
  (conda
    [(== 'olive x) s#]
    [s# (== 'oil x)]))

;;7
(run* [x]
  (conda
    [(== 'virgin x) u#]
    [(== 'olive x) s#]
    [s# (== 'oil x)]))

;;8
(run* [q]
  (fresh [x y]
    (== 'split x)
    (== 'pea y)
    (conda
      [(== 'split x) (== x y)]
      [s# s#])))

;;9,10
(run* [q]
  (fresh [x y]
    (== 'split x)
    (== 'pea y)
    (conda
      [(== x y) (== 'split x)]
      [s# s#])))

;;11
(defn not-pastao [x]
  (conda
    [(== 'pasta x) u#]
    [s# s#]))

(run* [x]
  (conda
    [(not-pastao x) u#]
    [(== 'spaghetti x) s#]))

;;12
(run* [x]
  (== 'spaghetti x)
  (conda
    [(not-pastao x) u#]
    [(== 'spaghetti x) s#]))

;;13
;;no value, never finishes
#_(run* [q]
    (conda
      [(alwayso) s#]
      [s# u#]))

;;14
(run* [q]
  (condu
    [(alwayso) s#]
    [s# u#]))

;;15
;;no value, never finishes
(run* [q]
  (condu
    [s# (alwayso)]
    [s# u#]))

;;17
;;no value, never finishes
(run 1 [q]
  (condu
    [s# (alwayso)]
    [s# u#])
  u#)

;;17
;;no value, never finishes
(run 1 [q]
  (condu
    [(alwayso) s#]
    [s# u#])
  u#)

;;20
(defn teacupo [t]
  (conde
    [(== 'tea t)]
    [(== 'cup t)]))

;;21
;;onceo exists in core.logic
#_(defn onceo [g]
    (condu
      [g s#]
      [s# u#]))

(run* [x]
  (onceo (teacupo x)))

;;22
(run* [r]
  (conde
    [(teacupo r) s#]
    [(== false r) s#]))

;;23
(run* [r]
  (conda
    [(teacupo r) s#]
    [s# (== false r)]))

;;24
(run* [r]
  (== false r)
  (conda
    [(teacupo r) s#]
    [(== false r) s#]
    [s# u#]))

;;25
(run* [r]
  (== false r)
  (condu
    [(teacupo r) s#]
    [(== false r) s#]
    [s# u#]))

;;26
(defn bumpo [n x]
  (conde
    [(== n x)]
    [(fresh [m]
       (-o n '(1) m)
       (bumpo m x))]))

(run* [x]
  (bumpo '(1 1 1) x))

;;27-39
(defn gen&test+o [i j k]
  (onceo
    (fresh [x y z]
      (+o x y z)
      (== i x)
      (== j y)
      (== k z))))

(run* [q]
  (gen&test+o '(0 0 1) '(1 1) '(1 1 1)))

;;40,42
;;no value, never finishes
(run 1 [q]
  (gen&test+o '(0 0 1) '(1 1) '(0 1 1)))

;;43-55
(defn enumerate+o-old [r n]
  (fresh [i j k]
    (bumpo n i)
    (bumpo n j)
    (+o i j k)
    (gen&test+o i j k)
    (== (list i j k) r)))

(run* [s]
  (enumerate+o-old s '(1 1)))

;;58
(defn enumerate+o [r n]
  (fresh [i j k]
    (bumpo n i)
    (bumpo n j)
    (+o i j k)
    (onceo
      (fresh [x y z]
        (+o x y z)
        (== i x)
        (== j y)
        (== k z)))
    (== (list i j k) r)))

(run* [s]
  (enumerate+o s '(1 1)))


;;56
;;Not the same as TRS
(run 1 [s]
  (enumerate+o s '(1 1 1)))
;;(((1 1 1) (1 1 1) 0 1 1 1))


;;59
(defn enumerateo [op r n]
  (fresh [i j k]
    (bumpo n i)
    (bumpo n j)
    (op i j k)
    (onceo
      (fresh [x y z]
        (op x y z)
        (== i x)
        (== j y)
        (== k z)))
    (== (list i j k) r)))












