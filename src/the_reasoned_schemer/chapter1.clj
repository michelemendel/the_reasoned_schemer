(ns the-reasoned-schemer.chapter1
  (:require [clojure.core.logic :refer :all]))

;;1.Playthings

;;7
(run* [q]
  u#)

;;10
(run* [q]
  (== 'pea 'pod))

;;11
(run* [q]
  (== q 'pea))

;;14,15
(run* [q]
  (== 'pea q))

;;16,17
(run* [q]
  s#)

;;19
(run* [q]
  (== 'pea 'pea))

;;20
(run* [q]
  (== q q))

;;21
(run* [q]
  (fresh (x)
    (== 'pea q)))

;;24
(run* [q]
  (fresh (x)
    (== 'pea x)))

;;notice the different syntax in core.logic
;;lcons
;;25
(run* [q]
  (fresh (x)
    (== (lcons x ()) q)))

;;`(,x) is shorthand for (cons x '())
;;notice the different syntax in core.logic
;;(list ,x) = (list x) = (lcons x ())
;;26
(run* [q]
  (fresh (x)
    (== (list x) q)))

;;32
(run* [q]
  (== '((('pea)) 'pod)
      '((('pea)) 'pod)))

;;33
;;`(((pea)) ,q) -> (lcons '((pea)) q)
(run* [q]
  (== '(((pea)) pod)
      (lcons '((pea)) q)))
;;or
(run* [q]
  (== '(((pea)) pod)
      (llist '((pea)) q)))

;;34
;;`(((,q)) pod) '(((pea)) pod))
(run* [q]
  (== (list (list (list q)) 'pod)
      '(((pea)) pod)))
;;or
(run* [q]
  (== (list (lcons (lcons q '()) '()) 'pod)
      '(((pea)) pod)))

;;35
;;`(((,q)) pod) `(((,x)) pod))
(run* [q]
  (fresh (x)
    (== (list (list (list q)) 'pod)
        (list (list (list x)) 'pod))))

;;36
(run* [q]
  (fresh (x)
    (== (list (list (list q)) x)
        (list (list (list x)) 'pod))))

;;37
;;`(,x ,x)
(run* [q]
  (fresh (x)
    (== (list x x) q)))

;;38
(run* [q]
  (fresh (x)
    (fresh (y)
      (== (list q y) (list (list x y) x)))))

;;40
(run* [q]
  (fresh (x)
    (== 'pea q)))

;;41
(run* [q]
  (fresh (x)
    (fresh (y)
      (== (list x y) q))))

;;42
;;same as 41

;;43
(run* [q]
  (fresh (x)
    (fresh (y)
      (== (list x y x) q))))

;;44
(run* [q]
  (== '(pea) 'pea))

;;45
(run* [x]
  (== '(pea pod) x)
  (== (list x) x))

;;46
;;occurs
(run* [x]
  (== (list x) x))

;;50
;;conjunction
;;conj2
(run* [q]
  (s# s#))
;;or
(run* [q]
  s# s#)

;;51
(run* [q]
  s#
  (== 'corn q))
;;or, but not sure why this works
(run* [q]
  (s# (== 'corn q)))

;;52
(run* [q]
  u#
  (== 'corn q))
;;doesn't work
(run* [q]
  (u# (== 'corn q)))

;;53
(run* [q]
  (== 'corn q)
  (== 'meal q))

;;54
(run* [q]
  (== 'corn q)
  (== 'corn q))




































