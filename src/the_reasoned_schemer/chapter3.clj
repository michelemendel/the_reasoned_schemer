(ns the-reasoned-schemer.chapter3
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [the-reasoned-schemer.utils :refer :all]))

;;3.Seeing Old Friends in New Ways

;;1
;;use seq? instead of list?
(seq? '((a) (a b) (c)))

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
(run* [_q]
  (fresh [x y]
    (lolo (list '(a b) (list x 'c) (list 'd y)))))

;;24
(run 1 [l]
  (lolo l))

;;25
(run 1 [q]
  (fresh [x]
    (lolo (llist '(a b) x))))

;;26
(run 1 [x]
  (lolo (llist '(a b) '(c d) x)))

;;27
(run 5 [x]
  (lolo (llist '(a b) '(c d) x)))
;;=> (() (()) ((_0)) (() ()) ((_0 _1)))

;;28
(llist '(a b) '(c d) '(() ()))
;;=> ((a b) (c d) () ())

;;29
(run 5 [x]
  (lolo x))

;;32
#_(defn singletono [l]
    (fresh [a]
      (== [a] l)))

;;Try it
(run* [l]
  (== l '())
  (singletono l))

;;33
;;List of singletons
#_(defn loso
  [l]
  (conde
    [(emptyo l)]
    [(fresh [a]
       (firsto l a)
       (singletono a))
     (fresh [d]
       (resto l d)
       (loso d))]))

;;34,35,37
(run 1 [z]
  (loso (llist '(g) z)))

;;36
(llist '(g) '())

;;38,39
(run 5 [z]
  (loso (llist '(g) z)))

;;40
(llist '(g) '((_0) (_1) (_2)))
;;=> ((g) (_0) (_1) (_2))
;;We don't see the logical list ((g) . ((_0) (_1) (_2)))

;;41
(run 4 [r]
  (fresh [w x y z]
    (loso (llist '(g) (llist 'e w) (llist x y) z))
    (== (list w (llist x y) z) r)))

;;Note: If instead of list we use llist in the second clause, we'll get a different result
(run 4 [r]
  (fresh [w x y z]
    (loso (llist '(g) (llist 'e w) (llist x y) z))
    (== (llist w (llist x y) z) r)))

;;42
(llist '(g)
       (llist 'e '(() (_0) ((_1) (_2))))
       (llist '(() (_0) ((_1) (_2))) '(() (_0) ((_1) (_2))))
       '(() (_0) ((_1) (_2))))
;;((g)
;; (e () (_0) ((_1) (_2))) ;Only this answer is given in the book
;; ((() (_0) ((_1) (_2))) () (_0) ((_1) (_2)))
;; () (_0)
;; ((_1) (_2)))

;;43
(run 3 [out]
  (fresh [w x y z]
    (== (llist '(g) (llist 'e w) (llist x y) z) out)
    (loso out)))
;;(((g) (e) (_0)) ;This list is not part of the answer in the book
;; ((g) (e) (_0) (_1))
;; ((g) (e) (_0) (_1) (_2)))

;;44
(defn member? [x l]
  (cond
    (nil? l) false
    (== (first l) x) true
    :else (member? x (next l))))

(member? 'olive '(virgin olive oil))

;;45,46,47
;;There is a membero in core.logic

;;48
(run* [q]
  (membero 'olive '(virgin olive oil)))

;;49
(run 1 [y]
  (membero y '(hummus with pita)))

;;50
(run 1 [y]
  (membero y '(with pita)))

;;52
(run* [y]
  (membero y '()))

;;53
(run* [y]
  (membero y '(hummus with pita)))

;;54
;;-

;;55
(run* [y]
  (fresh [l]
    (== l (llist 'pear 'grape 'peaches))
    (membero y l)))

;;Compare to
(run* [y]
  (fresh [l]
    (== l (list 'pear 'grape 'peaches))
    (membero y l)))

;;Note: This will never end
(run* [y]
  (fresh [l]
    (membero y l)
    (== l (llist 'pear 'grape 'peaches))))

;;56,57,58
(run* [x]
  (membero 'e (list 'pasta x 'fagioli)))

;;59
(run 1 [x]
  (membero 'e (list 'pasta 'e x 'fagioli)))

;;60
(run 1 [x]
  (membero 'e (list 'pasta x 'e 'fagioli)))

;;61,62
(run* [x y]
  (membero 'e (list 'pasta x 'fagioli y)))

;;63
(run* [q]
  (fresh [x y]
    (== (list 'pasta x 'fagioli y) q)
    (membero 'e q)))

;;64
(run 1 [l]
  (membero 'tofu l))

;;65
;;-

;;66
;;Never finishes, since there are infinite lists that answer to this query
#_(run* [l]
  (membero 'tofu l))

;;67
(run 5 [l]
    (membero 'tofu l))

;;68-72
;;about proper lists and membero

;;73
#_(defn proper-membero [x l]
  (conde
    [(firsto l x)
     (fresh [d]
       (resto l d)
       (listo d))]
    [(fresh [d]
       (resto l d)
       (proper-membero x d))]))

;;74
(run 12 [l]
  (proper-membero 'tofu l))

;;75
(defn proper-member?
  [x l]
  (cond
    (empty? l) false
    (= (first l) x) (seq? (rest l))
    :else (proper-member? x (rest l))))

