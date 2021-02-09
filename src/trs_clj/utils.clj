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
(declare odd-*o)
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
#_(defn ÷o [n m q r]
  (conde
    [(== '() q) (== n r) (<o n m)]
    [(== '(1) q) (== '() r) (== n m) (<o r m)]
    [(<o m n) (<o r m)
     (fresh [mq]
       (<=lo mq n)
       (*o m q mq)
       (+o mq r n))]))

;;81
(defn n-wider-than-mo [n m q r]
  (fresh [nhigh nlow qhigh qlow]
    (fresh [mqlow mrqlow rr rhigh]
      (splito n r nlow nhigh)
      (splito q r qlow qhigh)
      (conde
        ((== '() nhigh)
         (== '() qhigh)
         (-o nlow r mqlow)
         (*o m qlow mqlow))
        ((poso nhigh)
         (*o m qlow mqlow)
         (+o r mqlow mrqlow)
         (-o mrqlow nlow rr)
         (splito rr r '() rhigh)
         (÷o nhigh m qhigh rhigh))))))

(defn ÷o [n m q r]
  (conde
    [(== '() q) (== n r) (<o n m)]
    [(== '(1) q) (=lo n m) (+o r m n) (<o r m)]
    [(poso q) (<lo m n) (<o r m)
     (n-wider-than-mo n m q r)]))



;;8.69
(defn splito [n r l h]
  (conde
    [(== '() n) (== '() h) (== '() l)]
    [(fresh [b nt]
       (== (llist 0 b nt) n) (== '() r)
       (== (llist b nt) h) (== '() l))]
    [(fresh [nt]
       (== (llist 1 nt) n) (== '() r)
       (== nt h) (== '(1) l))]
    [(fresh [b nt a rt]
       (== (llist 0 b nt) n)
       (== (llist a rt) r) (== '() l)
       (splito (llist b nt) rt '() h))]
    [(fresh [nt a rt]
       (== (llist 1 nt) n)
       (== (llist a rt) r) (== '(1) l)
       (splito nt rt '() h))]
    [(fresh [b nt a rt l lt]
       (== (llist b nt) n)
       (== (llist a rt) r)
       (== (llist b lt) l)
       (poso lt)
       (splito nt rt lt h))]))

;;8.84
(defn exp2o [n b q]
  (conde
    [(== '(1) n) (== '() q)]
    [(>1o n) (== '(1) q)
     (fresh [s]
       (splito n b s '(1)))]
    [(fresh [q1 b2]
       (== (llist 0 q1) q)
       (poso q1)
       (<lo b n)
       (appendo b (llist 1 b) b2)
       (exp2o n b2 q1))]
    [(fresh [q1 nhigh b2 s]
       (== (llist 1 q1) q)
       (poso q1)
       (poso nhigh)
       (splito n b s nhigh)
       (appendo b (llist 1 b) b2)
       (exp2o nhigh b2 q1))]))

(defn repeated-mulo [n q nq]
  (conde
    [(poso n) (== '() q) (== '(1) nq)]
    [(== '(1) q) (== n nq)]
    [(>1o q)
     (fresh [q1 nq1]
       (+o q1 '(1) q)
       (repeated-mulo n q1 nq1)
       (*o nq1 n nq))]))

(defn base-three-or-moreo [n b q r]
  (fresh [bw1 bw nw nw1 qlow1 qlow s]
    (exp2o b '() bw1)
    (+o bw1 '(1) bw)
    (<lo q n)
    (fresh [q1 bwq1]
      (+o q '(1) q1)
      (*o bw q1 bwq1)
      (<o nw1 bwq1))
    (exp2o n '() nw1)
    (+o nw1 '(1) nw)
    (÷o nw bw qlow1 s)
    (+o qlow '(1) qlow1)
    (<=lo qlow q)
    (fresh [bqlow qhigh s qdhigh qd]
      (repeated-mulo b qlow bqlow)
      (÷o nw bw1 qhigh s)
      (+o qlow qdhigh qhigh)
      (+o qlow qd q)
      (<=o qd qdhigh)
      (fresh [bqd bq1 bq]
        (repeated-mulo b qd bqd)
        (*o bqlow bqd bq)
        (*o b bq bq1)
        (+o bq r n)
        (<o n bq1)))))

(defn logo [n b q r]
  (conde
    [(== '() q) (<=o n b) (+o r '(1) n)]
    [(== '(1) q) (>1o b) (=lo n b) (+o r b n)]
    [(== '(1) b) (poso q) (+o r '(1) n)]
    [(== '() b) (poso q) (== r n)]
    [(== '(0 1) b)
     (fresh [a ad dd]
       (poso dd)
       (== (llist a ad dd) n)
       (exp2o n '() q)
       (fresh [s]
         (splito n dd r s)))]
    ((<=o '(1 1) b) (<lo b n) (base-three-or-moreo n b q r))))
