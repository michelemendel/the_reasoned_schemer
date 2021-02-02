(ns trs-clj.chapter5
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [trs-clj.utils :refer :all]))

;;5.Members only

;;1
(defn mem [x l]
  (cond
    (empty? l) false
    (= (first l) x) l
    :else (mem x (rest l))))

(mem 'fig '(roll okra fig beet roll pea))

;;2
(mem 'fig '(roll okra beet beet roll pea))

;;3
(mem 'roll
     (mem 'fig
          '(roll okra fig beet roll pea)))

;;4
(defn memo [x l out]
  (conde
    [(firsto l x) (== l out)]
    [(fresh [d]
       (resto l d)
       (memo x d out))]))

;;5
(run* [q]
  (memo 'fig '(pea) '(pea)))

;;6
(run* [out]
  (memo 'fig '(fig) out))

;;7
(run* [out]
  (memo 'fig '(fig pea) out))

;;8
(run* [r]
  (memo r
        '(roll okra fig beet fig pea)
        '(fig beet fig pea)))

;;9
(run* [x]
  (memo 'fig '(fig pea) (list 'pea x)))

;;10
(run* [x]
  (memo 'fig '(fig pea) (list x 'pea)))

;;11
(run* [out]
  (memo 'fig '(beet fig pea) out))

;;12
;;(run 1 [out] g)

;;13
(run 1 [out]
  (memo 'fig '(fig fig pea) out))

;;14-17
(run* [out]
  (memo 'fig '(fig fig pea) out))

;;18
(run* [out]
  (fresh [x]
    (memo 'fig (list 'a x 'c 'fig 'e) out)))

;;19-22
(run 5 [x y]
  (memo 'fig (llist 'fig 'd 'fig 'e y) x))

;;23
(defn rember [x l]
  (cond
    (empty? l) ()
    (= x (first l)) (rest l)
    :else (cons (first l)
                (rember x (rest l)))))

;;24
(rember 'pea (list 'a 'b 'pea 'd 'pea 'e))

;;25
;;There is a rembero in core.util, but it gives different result from rembero in TRS.

;;From TRS
#_(defn rembero2 [x l out]
  (conde
    [(emptyo l) (== '() out)]
    [(conso x out l)]
    [(fresh (a d res)
       (conso a d l)
       (conso a res out)
       (rembero2 x d res))]))

;;26
;;Not the same result as TRS
(run* [out]
  (rembero2 'pea '(pea) out))

;;With core.logic/rembero we get a different result:
;;(())

;;27
;;Not the same result as TRS
(run* [out]
  (rembero2 'pea '(pea pea) out))

;;With core.logic/rembero we get a different result:
;;((pea))

;;28-47
;;Not the same result as TRS
(run* [out]
  (fresh [y z]
    (rembero2 y (list 'a 'b y 'd z 'e) out)))

;;With core.logic/rembero we get a different result:
;;((b a d _0 e)
;; (a b d _0 e)
;; ((a b d _0 e) :- (!= (_1 b)) (!= (_1 a)))
;; ((a b _0 d e) :- (!= (_0 _0)) (!= (_0 b)) (!= (_0 d)) (!= (_0 a))))

;;48-55
;;Not the same result as TRS
(run* [y z]
  (rembero2 y (list y 'd z 'e) (list y 'd 'e)))

;;With core.logic/rembero we get a different result:
;;([d d])

;;56-60
;;Not the same result as TRS
(run 4 [y z w out]
  (rembero2 y (llist z w) out))

;;With core.logic/rembero we get a different result:
;;([_0 _0 _1 _1]
;; ([_0 _1 (_0 . _2) (_1 . _2)] :- (!= (_1 _0)))
;; ([_0 _1 (_2 _0 . _3) (_1 _2 . _3)] :- (!= (_2 _0)) (!= (_1 _0)))
;; ([_0 _1 (_2 _3 _0 . _4) (_1 _2 _3 . _4)] :- (!= (_2 _0)) (!= (_1 _0)) (!= (_3 _0))))

;;61
(run 5 [y z w out]
  (rembero2 y (llist z w) out))

;;With core.logic/rembero we get a different result:
;;([_0 _1 (_2 _3 _4 _0 . _5) (_1 _2 _3 _4 . _5)]
;; :- (!= (_2 _0)) (!= (_1 _0)) (!= (_3 _0)) (!= (_4 _0)))
