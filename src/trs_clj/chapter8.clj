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
#_(defn bound-*o [q p n m]
    (conde
      ((== '() q) (poso p))
      ((fresh [a0 a1 a2 a3 x y z]
         (== (llist a0 x) q)
         (== (llist a1 y) p)
         (conde
           [(== '() n) (== (llist a2 z) m) (bound-*o x y z '())]
           [(== (llist a3 z) n) (bound-*o x y z m)])))))

;;17
#_(defn odd-*o [x n m p]
    (fresh (q)
      (bound-*o q p n m)
      (*o x m q)
      (+o (llist 0 q) m p)))

;;9
#_(defn *o [n m p]
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
#_(defn =lo [n m]
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

(run* [n]
  (=lo '(1 1) '(c 1)))

;;32
(run 5 [y z]
  (=lo (llist 1 y) (llist 1 z)))

;;33,34
(run 5 [y z]
  (=lo (llist 1 y) (llist 0 z)))

;;35
(run 5 [y z]
  (=lo (llist 1 y) (llist 0 1 1 0 1 z)))

;;36
;;n shorter than m
#_(defn <lo [n m]
    (conde
      [(== '() n) (poso m)]
      [(== '(1) n) (>1o m)]
      [(fresh [a x b y]
         (== (llist a x) n)
         (poso x)
         (== (llist b y) m)
         (poso y)
         (<lo x y))]))

;;37,38
(run 8 [y z]
  (<lo (llist 1 y) (llist 0 1 1 0 1 z)))

;;39
;;never ends, has no value
#_(run 1 [n]
    (<lo n n))

;;40
#_(defn <=lo [n m]
    (conde
      [(=lo n m)]
      [(<lo n m)]))

;;41
(run 8 [n m]
  (<=lo n m))

;;42
(run 1 [n m]
  (<=lo n m)
  (*o n '(0 1) m))

;;43
(run 10 [n m]
  (<=lo n m)
  (*o n '(0 1) m))

;;44,45
(run 9 [n m]
  (<=lo n m))

;;46
#_(defn <o [n m]
    (conde
      [(<lo n m)]
      [(=lo n m)
       (fresh [x]
         (poso x)
         (+o n x m))]))

#_(defn <=o [n m]
    (conde
      [(== n m)]
      [(<o n m)]))

;;47
(run* [q]
  (<o '(1 0 1) '(1 1 1)))

;;48
(run* [q]
  (<o '(1 1 1) '(1 0 1)))

;;49
(run* [q]
  (<o '(1 0 1) '(1 0 1)))

(run* [q]
  (<=o '(1 0 1) '(1 0 1)))

;;50
(run 6 [n]
  (<o n '(1 0 1)))

;;51
(run 6 [m]
  (<o '(1 0 1) m))

;;52
;;no value, never ends
#_(run* [n]
    (<o n n))

;;54-61

;;todo mendel remove
#_(defn ÷o [n m q r]
    (conde
      [(== '() q) (== n r) (<o n m)]
      [(== '(1) q) (== '() r) (== n m) (<o r m)]
      [(<o m n) (<o r m)
       (fresh [mq]
         (<=lo mq n)
         (*o m q mq)
         (+o mq r n))]))

;;53
(run 4 [n m q r]
  (÷o n m q r))

;;62-65
(run* [m]
  (fresh [r]
    (÷o '(1 0 1) m '(1 1 1) r)))

;;66
;;no value, never ends
#_(run 3 [y z]
    (÷o (llist 1 0 y) '(0 1) z '()))

;;69-72
;;TRS: lt is missing in the last fresh
#_(defn splito [n r l h]
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

;;73
(run* [l h]
  (splito '(0 0 1 0 1) '() l h))

;;74
(run* [l h]
  (splito '(0 0 1 0 1) '(1) l h))

;;75
;;not the same result as TRS
(run* [l h]
  (splito '(0 0 1 0 1) '(0 1) l h))
;;([_0 (0 1)] [() (0 1)])
;;not (((0 0 1) (0 1)))

;;76
;;not the same result as TRS
(run* [l h]
  (splito '(0 0 1 0 1) '(1 1) l h))
;;([_0 (0 1)] [() (0 1)])
;;not (((0 0 1) (0 1)))

;;77
;;not the same result as TRS
(run* [r l h]
  (splito '(0 0 1 0 1) r l h))
;;([() () (0 1 0 1)]
;; [(_0) () (1 0 1)]
;; [(_0 _1) _2 (0 1)]
;; [(_0 _1) () (0 1)]
;; [(_0 _1 _2) _3 (1)]
;; [(_0 _1 _2) () (1)]
;; [(_0 _1 _2 _3) _4 ()]
;; [(_0 _1 _2 _3) () ()]
;; [(_0 _1 _2 _3 _4 . _5) _6 ()]
;; [(_0 _1 _2 _3 _4 . _5) () ()]
;; [(_0 _1 _2 _3) _4 ()]
;; [(_0 _1 _2 _3 _4 . _5) _6 ()]
;; [(_0 _1 _2 _3) () ()]
;; [(_0 _1 _2 _3 _4 . _5) () ()]
;; [(_0 _1 _2 _3) () ()]
;; [(_0 _1 _2 _3 _4 . _5) () ()])

;;81,83
#_(defn n-wider-than-mo [n m q r]
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

#_(defn ÷o2 [n m q r]
  (conde
    [(== '() q) (== n r) (<o n m)]
    [(== '(1) q) (=lo n m) (+o r m n) (<o r m)]
    [(poso q) (<lo m n) (<o r m)
     (n-wider-than-mo n m q r)]))

;;82
;;This fails with new definition of ÷o
(run 3 [y z]
  (÷o (llist 1 0 y) '(0 1) z '()))

;;84
#_(defn exp2o [n b q]
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

#_(defn repeated-mulo [n q nq]
  (conde
    [(poso n) (== '() q) (== '(1) nq)]
    [(== '(1) q) (== n nq)]
    [(>1o q)
     (fresh [q1 nq1]
       (+o q1 '(1) q)
       (repeated-mulo n q1 nq1)
       (*o nq1 n nq))]))

#_(defn base-three-or-moreo [n b q r]
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

#_(defn logo [n b q r]
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

;;91
;;Different result than TRS
(run* [r]
  (logo '(0 1 1 1) '(0 1) '(1 1) r))
;;(() _0)

;;92
;;Different result than TRS
(run 9 [b q r]
  (logo '(0 0 1 0 0 0 1) b q r) (>1o q))
;;([() (_0 _1 . _2) (0 0 1 0 0 0 1)]
;; [(1) (_0 _1 . _2) (1 1 0 0 0 0 1)]
;; [(0 1) (0 1 1) _0]
;; [(0 1) (0 1 1) ()]
;; [(1 1) (1 1) (1 0 0 1 0 1)]
;; [(1 1) (1 1) (1 0 0 1 0 1)]
;; [(1 1) (1 1) (1 0 0 1 0 1)]
;; [(1 1) (1 1) (1 0 0 1 0 1)]
;; [(1 1) (1 1) (1 0 0 1 0 1)])

;;93
(defn expo [b q n]
  (logo n b q '()))

;;94
;;Different result than TRS
;;never ends
#_(run* [t]
  (expo '(1 1) '(1 0 1) t))


















