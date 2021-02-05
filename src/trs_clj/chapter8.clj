(ns trs-clj.chapter8
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [clojure.pprint :refer [pprint]]
            [trs-clj.utils :refer :all]))

;;8.Just A Bit More

;;18
;;hypothetical definition
#_(defn bound-*o [q p n m]
    s#)

;;24
(defn bound-*o [q p n m]
  (conde
    ((== '() q) (poso p))
    ((fresh [a0 a1 a2 a3 x y z]
       (== (llist a0 x) q)
       (== (llist a1 y) p)
       (conde
         [(== '() n) (== (llist a2 z) m) (bound-*o x y z '())]
         [(== (llist a3 z) n) (bound-*o x y z m)])))))

;;17
(defn odd-*o [x n m p]
  (fresh (q)
    (bound-*o q p n m)
    (*o x m q)
    (+o (llist 0 q) m p)))

;;9
(defn *o [n m p]
  (conde
    [(== '() n) (== '() p)]
    [(poso n) (== '() m) (== '() p)]
    [(== '(1) n) (poso m) (== m p)]
    [(>1o n) (== '(1) m) (== n p)]
    [(fresh (x z) (== (llist 0 x) n) (poso x) (== (llist 0 z) p) (poso z) (>1o m) (*o x m z))]
    [(fresh (x y) (== (llist 1 x) n) (poso x) (== (llist 0 y) m) (poso y) (*o m n p))]
    [(fresh (x y) (== (llist 1 x) n) (poso x) (== (llist 1 y) m) (poso y) (odd-*o x n m p))]))

;;1
(run 10 [x y r]
  (*o x y r))

;;8
(run 1 [x y r]
  (== (list x y r) '((1 1) (1 1) (1 0 0 1)))
  (*o x y r))

(run 20 [s t u v a b c d]
  (fresh [x y r]
    (== [x y r] [[s t] [u v] (llist a b c d)])
    (*o x y r)))

;;19
(run 1 [n m]
  (*o n m '(1)))

;;20
;;Never ends. This neither succeeds nor fails.
(run 1 [n m]
  (>1o n)
  (>1o m)
  (*o n m '(1 1)))

;;25
(run 2 [n m]
  (*o n m '(1)))

;;26
(run* [p]
  (*o '(1 1 1) '(1 1 1 1 1 1) p))

;;27
(run* [p]
  (*o '(1 1 1) '(1 1 0 1 1 1) p))

;;28
;;length?
(defn =lo [n m] 
  (conde
    [(== '() n) (== '() m)]
    [(== '(1) n) (== '(1) m)]
    [(fresh [a x b y]
       (== (llist a x) n)
       (poso x)
       (== (llist b y) m)
       (poso y)
       (=lo x y))]))

;;29
(run* [w x y]
  (=lo (llist 1 w x y) '(0 1 1 0 1)))

;;30
(run* [b]
  (=lo '(1) (list b)))

;;31
(run* [n]
  (=lo (llist 1 0 1 n) '(0 1 1 0 1)))

;;32
(run 5 [y z]
  (=lo (llist 1 y) (llist 1 z)))

































