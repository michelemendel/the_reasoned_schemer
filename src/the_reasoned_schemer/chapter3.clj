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
#_(defn listo [l]
  (conde
    ((emptyo l))
    ((pairo l)
     (fresh (d)
       (resto l d)
       (listo d)))))

;;9,11
;;Note: this is a regular list.
;;Compare to 12-18 that uses a logical list (a b c . ,x)
(run* [x]
  (listo (list 'a 'b x 's))) ;=> (_0)

;;12,13
;;Beware, will never stop finding solutions
;;Uses a logical list (a b c . ,x)
(run* [x]
  (listo (llist 'a 'b 'c x)))

;;14-17
(run 1 [x]
  (listo (llist 'a 'b 'c x)))

;;18,19
(run 5 [x]
  (listo (llist 'a 'b 'c x)))
;;=> (() (_0) (_0 _1) (_0 _1 _2) (_0 _1 _2 _3))

;;20
;;from 1.91
(run* [x y]
  (conde
    [(== 'split x) (== 'pea y)]
    [(== 'red x) (== 'bean y)]
    [(== 'green x) (== 'lentil y)]))

;;21
;;lol? stands for laughing-out-loud, because this function is very funny
;;Some people think it stands for list-of-lists?
(defn lol? [l]
  (cond
    (empty? l) true
    (seq? (first l)) (lol? (rest l))
    :else false))

(lol? '(() ()))

;;22
;;The logical version of lol?
#_(defn lolo [l]
  (conde
    [(emptyo l)]
    [(fresh [a]
       (firsto l a)
       (listo a))
     (fresh [b]
       (resto l b)
       (lolo b))]))

;;23
(run* [q]
  (fresh [x y]
    (lolo (list '(a b) (list x 'c) (list 'd y)))))

;;24
(run 1 [l]
  (lolo l))











