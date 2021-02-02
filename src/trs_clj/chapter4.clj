(ns trs-clj.chapter4
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [trs-clj.utils :refer :all]))

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
;;(append 'a '(d e))

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

;;33,34
(run 6 [x]
  (fresh [y]
    (appendo x y '(cake & ice d t))))

;;35,36
(run 6 [y]
  (fresh [x]
    (appendo x y '(cake & ice d t))))

;;37,38
(run 6 [x y]
  (appendo x y '(cake & ice d t)))

;;39-42
;;Differs from the book. Here the value is actually the same as frame 37.
(run 7 [x y]
  (appendo x y '(cake & ice d t)))

;;43,44
;;swappendo

;;45
;;I'm using seq? instead of pair?, since seq is the abstractness that Clojure works with, while pair is what Scheme is using (the cons cell).
(defn unwrap [x]
  (cond
    (seq? x) (unwrap (first x))
    :else x))

(unwrap '((((pizza)))))

;;46
(unwrap '((((pizza pie) with)) garlic))

;;47
#_(defn unwrapo [x out]
  (conde
    [(pairo x) (fresh [a]
                 (firsto x a)
                 (unwrapo a out))]
    [(== out x)]))

;;48,49
(run* [x]
  (unwrapo '(((pizza))) x))

;;50
(run 1 [x]
  (unwrapo x 'pizza))

;;51
(run 1 [x]
  (unwrapo [[x]] 'pizza))

;;52
(run 5 [x]
  (unwrapo x 'pizza))

;;53
;;Except for the first value, the rest are not the same as the book.
(run 5 [x]
  (unwrapo x '((pizza))))
;;(((pizza))
;; (clojure.lang.LazySeq @309e4e91 . _0)
;; ((clojure.lang.LazySeq @309e4e91 . _0) . _1)
;; (((clojure.lang.LazySeq @309e4e91 . _0) . _1) . _2)
;; ((((clojure.lang.LazySeq @309e4e91 . _0) . _1) . _2) . _3))

;;54
;;Except for the first value, the rest are not the same as the book.
(run 5 [x]
  (unwrapo [[x]] 'pizza))

;;55
;;Eating pizza
