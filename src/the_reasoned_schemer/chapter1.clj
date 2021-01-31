(ns the-reasoned-schemer.chapter1
  (:refer-clojure :exclude [==])
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
  (fresh [x]
    (== 'pea q)))

;;24
(run* [q]
  (fresh [x]
    (== 'pea x)))

;;notice the different syntax in core.logic
;;lcons
;;25
(run* [q]
  (fresh [x]
    (== (lcons x ()) q)))

;;`(,x) is shorthand for (cons x '()) or (llist x)
;;notice the different syntax in core.logic
;;(list ,x) = (list x) = (lcons x ())
;;26
(run* [q]
  (fresh [x]
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
  (fresh [x]
    (== (list (list (list q)) 'pod)
        (list (list (list x)) 'pod))))

;;36
(run* [q]
  (fresh [x]
    (== (list (list (list q)) x)
        (list (list (list x)) 'pod))))

;;37
;;`(,x ,x)
(run* [q]
  (fresh [x]
    (== (list x x) q)))

;;38
(run* [q]
  (fresh [x]
    (fresh [y]
      (== (list q y) (list (list x y) x)))))

;;40
(run* [q]
  (fresh [x]
    (== 'pea q)))

;;41
(run* [q]
  (fresh [x]
    (fresh [y]
      (== (list x y) q))))

;;42
;;same as 41

;;43
(run* [q]
  (fresh [x]
    (fresh [y]
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
;;conj2 - What is this in core.logic?
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

;;55
;;disjunction
;;disj2 - Q:Is this the same as conde? A:see 88
(run* [q]
  (conde [u#] [u#]))

;;56
(run* [q]
  (conde [(== 'olive q)]
         [u#]))

;;57
(run* [q]
  (conde [u#]
         [(== 'oil q)]))

;;58
(run* [q]
  (conde [(== 'olive q)]
         [(== 'oil q)]))

;;59
;;Notice the independent reification in each disjunctive clause
(run* [q]
  (fresh [x]
    (fresh [y]
      (conde
        [(== (list x y) q)]
        [(== (list y x) q)]))))

;;61
(run* [q]
  (conde [(== 'olive q)]
         [(== 'oil q)]))
;;the same as
(run* [q]
  (conde [(== 'oil q)]
         [(== 'olive q)]))

;;62
(run* [x]
  (conde [(== 'olive x) u#]
         [(== 'oil x)]))

;;63
(run* [x]
  (conde [(== 'olive x) s#]
         [(== 'oil x)]))

;;64
(run* [x]
  (conde [(== 'oil x)]
         [(== 'olive x) s#]))

;;65
(run* [x]
  (conde
    [(== 'virgin x) u#]
    [(conde
       [(== 'olive x)]
       [(conde
          [s#]
          [(== 'oil x)])])]))

;;67
;;See book: Don't know how to write nested conjunctions,
;;but this gives the same result.
(run* [r]
  (fresh [x]
    (fresh [y]
      (== 'split x)
      (== 'pea y)
      (== (list x y) r))))

;;68
;;Since I don't know how to write nested conjunctions, this will look the same as 67
;;see 88

;;70
;;See book: Don't know how to write nested conjunctions,
;;but this gives the same result.
;;see 88
(run* [r]
  (fresh [x y]
    (== 'split x)
    (== 'pea y)
    (== (list x y) r)))

;;72,73
;;Not the same result as 65-70
(run* [r x y]
  (== 'split x)
  (== 'pea y)
  (== (list x y) r))

;;74,75
;;Now it's the same result as 65-70
(run* [x y]
  (== 'split x)
  (== 'pea y))

;;76
(run* [x y]
  (conde
    [(== 'split x) (== 'pea y)]
    [(== 'red x) (== 'bean y)]))

;;77
;;Note that this is the same as 78, since there is no conj2 function in core.logic. Well, I haven't found it.
;;see 88
(run* [r]
  (fresh [x y]
    (conde
      [(== 'split x) (== 'pea y)]
      [(== 'red x) (== 'bean y)])
    (== (list x y 'soup) r)))

;;79-81 - Here the book removes conj2, which makes the syntax similar to the examples above.

;;82
;;No defrel macro in core.logic, but we'll use a regular defn instead.
(defn teacupo [t]
  (conde [(== 'tea t)]
         [(== 'cup t)]))
;;or
(defn teacupo [t]
  (conde ((== 'tea t))
         ((== 'cup t))))

;;83
(run* [x]
  (teacupo x))

;;84
(run* [x y]
  (conde
    [(teacupo x) (== true y)]
    [(== false x) (== true y)]))
;;or
(run* [x y]
  (conde
    ((teacupo x) (== true y))
    ((== false x) (== true y))))

;;85
(run* [x y]
  (teacupo x)
  (teacupo y))
;;or, written without the relation function
(run* [x y]
  (conde [(== 'tea x)]
         [(== 'cup x)])
  (conde [(== 'tea y)]
         [(== 'cup y)]))

;;86
(run* [x y]
  (teacupo x)
  (teacupo x))

;;87
(run* [x y]
  (conde
    [(teacupo x) (teacupo x)]
    [(== false x) (teacupo y)]))

;;88
;;conde is now introduced
;;e stands for every

;;89 - see 62

;;90 - z is not reified
(run* [x y]
  (conde
    [(fresh [z]
       (== 'lentil z))]
    [(== x y)]))
;;compare to this, where z is reified
(run* [x y z]
  (conde
    [(== 'lentil z)]
    [(== x y)]))

;;91
(run* [x y]
  (conde
    [(== 'split x) (== 'pea y)]
    [(== 'red x) (== 'bean y)]
    [(== 'green x) (== 'lentil y)]))































