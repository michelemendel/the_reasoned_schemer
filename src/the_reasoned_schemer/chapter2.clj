(ns the-reasoned-schemer.chapter2
  (:require [clojure.core.logic :refer :all]))

;;2.Teaching Old Toys New Tricks

;;1
(first '(grape raisin pear))

;;2
(first '(a c o r n))

;;3
(run* [q]
  (firsto '(a c o r n ) q))

;;4
(run* [q]
  (firsto '(a c o r n ) 'a))

;;5
(run* [r]
  (fresh [x y]
    (firsto [r y] x)
    (== 'pear x)))

;;6
;;first takes one argument
;;firsto takes two arguments
(defn caro [p a]
  (fresh [d]
    (== (lcons a d) p)))

;;usage
(run* [x]
  (caro '(grape raisin pear) x))

;;How does lcons look like?
(run* [p]
  (== (lcons 'a 'd) p))

;;Here is firsto/caro directly
;;x (first) - grape
;;d (tail) - (raisin pear)
(run* [x]
  (fresh [d]
    (== '(grape raisin pear) (lcons x d))))

;;7
(cons
  (first '(grape raisin pear))
  (first '((a) (b) (c))))

;;8
;;Note the use of lcons instead of cons as in the book
;;lcons has to be used, since the 2nd argument is not necessarily a proper tail, but could be a logic variable.
(run* [r]
  (fresh [x y]
    (firsto '(grape raisin pear) x)
    (firsto '((a) (b) (c)) y)
    (== (lcons x y) r)))
;;compare to
(run* [x]
  (conde
    [(firsto '(grape raisin pear) x)]
    [(firsto '(a b c) x)]))

;;10
(rest '(grape raisin pear))

;;11
(first (rest (rest '(a c o r n))))

;;12
(run* [r]
  (fresh [v]
    (resto '(a c o r n) v)
    (fresh [w]
      (resto v w)
      (firsto w r))))

;;13
;;6
;;first takes one argument
;;firsto takes two arguments
(defn cdro [p a]
  (fresh [d]
    (== (lcons d a) p)))
;;usage
(run* [x]
  (cdro '(grape raisin pear) x))

;;14
(cons
  (rest '(grape raisin pear))
  (first '((a) (b) (c))))

;;15
(run* [r]
  (fresh [x y]
    (resto '(grape raisin pear) x)
    (firsto '((a) (b) (c)) y)
    (== (lcons x y) r)))

;;16
(run* [q]
  (resto '(a c o r n) '(c o r n)))

;;17
(run* [x]
  (resto '(c o r n) [x 'r 'n]))

;;18
(run* [l]
  (fresh [x]
    (resto l '(c o r n))
    (firsto l x)
    (== 'a x)))

;;19
(run* [l]
  (conso '(a b c) '(d e) l))

;;20
(run* [x]
  (conso x '(a b c) '(d a b c)))

;;21
(run* [r]
  (fresh [x y z]
    (== ['e 'a 'd x] r)
    (conso y ['a z 'c] r)))

;;22
(run* [x]
  (conso x ['a x 'c] ['d 'a x 'c]))

;;23
(run* [l]
  (fresh [x]
    (== ['d 'a x 'c] l)
    (conso x ['a x 'c] l)))

;;24
(run* [l]
  (fresh [x]
    (conso x ['a x 'c] l)
    (== ['d 'a x 'c] l)))

;;25
(defn conso1 [a d p]
  (firsto p a)
  (resto p d))

;;usage, but not the same result
(run* [l]
  (fresh [x]
    (conso1 x ['a x 'c] l)
    (== ['d 'a x 'c] l)))

;;26
(defn conso2 [a d p]
  (== (lcons a d) p))

(run* [l]
  (fresh [x]
    (conso2 x ['a x 'c] l)
    (== ['d 'a x 'c] l)))

;;27
(run* [l]
  (fresh [d t x y w]
    (conso w [:n :u :s] t)
    (resto l t)
    (firsto l x)
    (== :b x)
    (resto l d)
    (firsto d y)
    (== :o y)))

(run* [l] ;(:b _0 :n :u :s)
  (fresh [t w]
    (== [w :n :u :s] t) ;(_0 :n :u :s)
    (resto l t) ;(_0 . _1)
    (firsto l :b))) ;(:b . _0)

(run* [l] ;(_0 :o . _1)
  (fresh [d]
    (resto l d) ;(_0 . _1)
    (firsto d :o))) ;(:o . _0)


