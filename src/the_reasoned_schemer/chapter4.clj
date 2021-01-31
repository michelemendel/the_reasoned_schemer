(ns the-reasoned-schemer.chapter4
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [the-reasoned-schemer.utils :refer :all]))

;;4.Double Your Fun

;;1
;;Note: Since Clojure doesn't have cons cells, I added
;; (if (seq? t) t (list t)) to make frame 5 work.
(defn append
  [l t]
  (cond (empty? l) (if (seq? t) t (list t))
        :else (cons (first l) (append (rest l) t))))

(append '(a b c) '(d e))

;;2
(append '(a b c) '())

;;3
(append '() '(d e))

;;4
(append 'a '(d e))

;;5-7
(append '(d e) 'a)

;;8-16
;;There is an appendo in core.logic

;;17
(run 6 [x]
  (fresh [y z]
    (appendo x y z)))
;; (()
;; (_0)
;; (_0 _1)
;; (_0 _1 _2)
;; (_0 _1 _2 _3)
;; (_0 _1 _2 _3 _4))

;;18,19
(run 6 [y]
  (fresh [x z]
    (appendo x y z)))
;; (_0 _0 _0 _0 _0 _0)

;;20
(run 6 [z]
  (fresh [x y]
    (appendo x y z)))
;; (_0
;; (_0 . _1)
;; (_0 _1 . _2)
;; (_0 _1 _2 . _3)
;; (_0 _1 _2 _3 . _4)
;; (_0 _1 _2 _3 _4 . _5))

;;21
(run 6 [x y z]
  (appendo x y z))
;;([() _0 _0]
;; [(_0) _1 (_0 . _1)]
;; [(_0 _1) _2 (_0 _1 . _2)]
;; [(_0 _1 _2) _3 (_0 _1 _2 . _3)]
;; [(_0 _1 _2 _3) _4 (_0 _1 _2 _3 . _4)]
;; [(_0 _1 _2 _3 _4) _5 (_0 _1 _2 _3 _4 . _5)])

;;22
(run* [x]
  (appendo '(cake) '(tastes yummy) x))

;;23
(run* [x]
  (fresh [y]
    (appendo (list 'cake '& 'ice y) '(tastes yummy) x)))

;;24
(run* [x]
  (fresh [y]
    (appendo (list 'cake '& 'ice 'cream) y x)))

;;25
(run 1 [x]
  (fresh [y]
    (appendo (llist 'cake '& 'ice y) '(d t) x)))

;;26
(run 5 [x]
  (fresh [y]
    (appendo (llist 'cake '& 'ice y) '(d t) x)))

;;27
(run 5 [y]
  (fresh [x]
    (appendo (llist 'cake '& 'ice y) '(d t) x)))

;;28,29
(run 1 [x]
  (appendo (llist 'cake '& 'ice '(_0 _1 _2)) '(d t) x))
;;((cake & ice _0 _1 _2 d t))

;;((cake & ice d t)
;; (cake & ice _0 d t)
;; (cake & ice _0 _1 d t)
;; (cake & ice _0 _1 _2 d t)    <-- same as this from 26
;; (cake & ice _0 _1 _2 _3 d t))

;;30
(run 5 [x]
  (fresh [y]
    (appendo (llist 'cake '& 'ice y) (llist 'd 't y) x)))

;;31,32
(run 5 [x]
  (fresh [z]
    (appendo '(cake & ice cream) (llist 'd 't z) x)))
;;((cake & ice cream d t . _0))












































