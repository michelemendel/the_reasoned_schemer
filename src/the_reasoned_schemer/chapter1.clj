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

(run* [q]
      (== '(((pea)) pod)
          (llist '((pea)) q)))

;;34
;;`(((,q)) pod) '(((pea)) pod))
(run* [q]
      (== (list (list (list q)) 'pod)
          '(((pea)) pod)))

(run* [q]
      (== (list (lcons (lcons q '()) '()) 'pod)
          '(((pea)) pod)))







