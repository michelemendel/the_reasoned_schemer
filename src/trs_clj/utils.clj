(ns trs-clj.utils
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]))

(defn pair? [x]
  (or (lcons? x)
      (and (coll? x)
           (boolean (seq x)))))

(defn pairo [p]
  (fresh [a d]
    (conso a d p)))

(defn singleton? [l]
  (cond
    (pair? l) (empty? (rest l))
    :else false))

(defn singletono [l]
  (fresh [a]
    (== (list a) l)))

;;List of singletons
(defn loso
  [l]
  (conde
    [(emptyo l)]
    [(fresh [a]
       (firsto l a)
       (singletono a))
     (fresh [d]
       (resto l d)
       (loso d))]))

(defn listo [l]
  (conde
    ((emptyo l))
    ((pairo l)
     (fresh (d)
       (resto l d)
       (listo d)))))

;;List of lists
(defn lolo [l]
  (conde
    [(emptyo l)]
    [(fresh [a]
       (firsto l a)
       (listo a))
     (fresh [b]
       (resto l b)
       (lolo b))]))

(defn proper-membero [x l]
  (conde
    [(firsto l x)
     (fresh [d]
       (resto l d)
       (listo d))]
    [(fresh [d]
       (resto l d)
       (proper-membero x d))]))

(defn unwrapo [x out]
  (conde
    [(pairo x) (fresh [a]
                 (firsto x a)
                 (unwrapo a out))]
    [(== out x)]))

;;There is a rembero in core.util, but it gives different result from rembero in TRS.
(defn rembero2 [x l out]
  (conde
    [(emptyo l) (== '() out)]
    [(conso x out l)]
    [(fresh (a d res)
       (conso a d l)
       (conso a res out)
       (rembero2 x d res))]))

#_(defn alwayso-bad []
    (conde
      [s#]
      [(alwayso-bad)]))

#_(defn nevero-bad []
    (nevero-bad))

(defn alwayso []
  (fn [s]
    (fn []
      ((conde
         [s#]
         [(alwayso)])
       s))))

(defn nevero []
  (fn [s]
    (fn []
      ((nevero)
       s))))

(defn bit-xoro [x y r]
  (conde
    [(== 0 x) (== 0 y) (== 0 r)]
    [(== 0 x) (== 1 y) (== 1 r)]
    [(== 1 x) (== 0 y) (== 1 r)]
    [(== 1 x) (== 1 y) (== 0 r)]))

(defn bit-ando [x y r]
  (conde
    [(== 0 x) (== 0 y) (== 0 r)]
    [(== 1 x) (== 0 y) (== 0 r)]
    [(== 0 x) (== 1 y) (== 0 r)]
    [(== 1 x) (== 1 y) (== 1 r)]))

(defn half-addero [x y r c]
  (all
    (bit-xoro x y r)
    (bit-ando x y c)))

(defn full-addero [b x y r c]
  (fresh [w xy wz]
    (half-addero x y w xy)
    (half-addero w b r wz)
    (bit-xoro xy wz c)))

(defn poso [n]
  (fresh [a d]
    (== (llist a d) n)))

;;7.83
(defn >1o [n]
  (fresh [a ad dd]
    (== (llist a ad dd) n)))

(declare addero)

(defn gen-addero [b n m r]
  (fresh [a c d e x y z]
    (== (llist a x) n)
    (== (llist d y) m) (poso y)
    (== (llist c z) r) (poso z)
    (all
      (full-addero b a d c e)
      (addero e x y z))))

(defn addero [b n m r]
  (conde
    [(== 0 b) (== '() m) (== n r)]
    [(== 0 b) (== '() n) (== m r) (poso m)]
    [(== 1 b) (== '() m) (addero 0 n '(1) r)]
    [(== 1 b) (== '() n) (poso m) (addero 0 '(1) m r)]
    [(== '(1) n) (== '(1) m) (fresh [a c]
                               (== (list a c) r)
                               (full-addero b 1 1 a c))]
    [(== '(1) n) (gen-addero b n m r)]
    [(== '(1) m) (>1o n) (>1o r) (addero b '(1) n r)]
    [(>1o n) (gen-addero b n m r)]))

;;7.114
(defn +o [n m k]
  (addero 0 n m k))

;;7.116
(defn -o [n m k]
  (+o m k n))

;;7.120
(defn lengtho [l n]
  (conde
    [(emptyo l) (== '() n)]
    [(fresh [d res]
       (resto l d)
       (+o '(1) res n)
       (lengtho d res))]))

;;8.9
(defn *o [n m p]
  (conde
    [(== '() n) (== '() p)]
    [(poso n) (== '() m) (== '() p)]
    [(== '(1) n) (poso m) (== m p)]
    [(>1o n) (== '(1) m) (== n p)]
    [(fresh (x z) (== (llist 0 x) n) (poso x) (== (llist 0 z) p) (poso z) (>1o m) (*o x m z))]
    [(fresh (x y) (== (llist 1 x) n) (poso x) (== (llist 0 y) m) (poso y) (*o m n p))]
    [(fresh (x y) (== (llist 1 x) n) (poso x) (== (llist 1 y) m) (poso y) (odd-*o x n m p))]))

;;8.24
(defn bound-*o [q p n m]
  (conde
    ((== '() q) (poso p))
    ((fresh [a0 a1 a2 a3 x y z]
       (== (llist a0 x) q)
       (== (llist a1 y) p)
       (conde
         [(== '() n) (== (llist a2 z) m) (bound-*o x y z '())]
         [(== (llist a3 z) n) (bound-*o x y z m)])))))

;;8.17
(defn odd-*o [x n m p]
  (fresh (q)
    (bound-*o q p n m)
    (*o x m q)
    (+o (llist 0 q) m p)))

;;8.9
(defn *o [n m p]
  (conde
    [(== '() n) (== '() p)]
    [(poso n) (== '() m) (== '() p)]
    [(== '(1) n) (poso m) (== m p)]
    [(>1o n) (== '(1) m) (== n p)]
    [(fresh (x z) (== (llist 0 x) n) (poso x) (== (llist 0 z) p) (poso z) (>1o m) (*o x m z))]
    [(fresh (x y) (== (llist 1 x) n) (poso x) (== (llist 0 y) m) (poso y) (*o m n p))]
    [(fresh (x y) (== (llist 1 x) n) (poso x) (== (llist 1 y) m) (poso y) (odd-*o x n m p))]))

;;8.28
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

;;8.36
;;n shorter than m
(defn <lo [n m]
  (conde
    [(== '() n) (poso m)]
    [(== '(1) n) (>1o m)]
    [(fresh [a x b y]
       (== (llist a x) n)
       (poso x)
       (== (llist b y) m)
       (poso y)
       (<lo x y))]))

;;8.40
(defn <=lo [n m]
  (conde
    [(=lo n m)]
    [(<lo n m)]))

;;8.46
(defn <o [n m]
  (conde
    [(<lo n m)]
    [(=lo n m)
     (fresh [x]
       (poso x)
       (+o n x m))]))

;;8.46
(defn <=o [n m]
  (conde
    [(== n m)]
    [(<o n m)]))

;;8.54
(defn ÷o [n m q r]
  (conde
    [(== '() q) (== n r) (<o n m)]
    [(== '(1) q) (== '() r) (== n m) (<o r m)]
    [(<o m n) (<o r m)
     (fresh [mq]
       (<=lo mq n)
       (*o m q mq)
       (+o mq r n))]))

