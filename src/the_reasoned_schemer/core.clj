(ns the-reasoned-schemer.core
  (:require [clojure.core.logic :refer :all]))

(run* [q]
      (== q true))

;;1.Playthings

(run* [q]
      u#)

(run* [q]
      (== 'pea 'pod))

(run* [q]
      (== q 'pea))

(run* [q]
      (== 'pea q))

(run* [q]
      s#)

(run* [q]
      (== 'pea 'pea))

(run* [q]
      (== q q))

(run* [q]
      (fresh (x)
             (== 'pea q)))

(run* [q]
      (fresh (x)
             (== 'pea x)))

;;notice the different syntax in core.logic
;;lcons
;;25
(run* [q]
      (fresh (x)
             (== (lcons x '()) q)))

;;`(,x) is shorthand for (cons x '())
;;notice the different syntax in core.logic
;;(list ,x) = (list x) = (lcons x '())
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

;;33
(run* [q]
      (== '(((pea)) pod)
          (llist '((pea)) q)))

;;34
(run* [q]
      (== (lcons (list (lcons q '())) 'pod)
          '(((pea)) pod)))







