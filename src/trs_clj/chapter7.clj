(ns trs-clj.chapter7
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]
            [clojure.pprint :refer [pprint]]
            [trs-clj.utils :refer :all]))

;;7.A Bit Too Much

;;5
(defn bit-xoro [x y r]
  (conde
    [(== 0 x) (== 0 y) (== 0 r)]
    [(== 0 x) (== 1 y) (== 1 r)]
    [(== 1 x) (== 0 y) (== 1 r)]
    [(== 1 x) (== 1 y) (== 0 r)]))

;;6
(run* [x y]
  (bit-xoro x y 0))

;;7,8
(run* [x y]
  (bit-xoro x y 1))

;;9
(run* [x y r]
  (bit-xoro x y r))

;;10
(defn bit-ando [x y r]
  (conde
    [(== 0 x) (== 0 y) (== 0 r)]
    [(== 1 x) (== 0 y) (== 0 r)]
    [(== 0 x) (== 1 y) (== 0 r)]
    [(== 1 x) (== 1 y) (== 1 r)]))

;;11
(run* [x y]
  (bit-ando x y 1))

;;12
(defn half-addero [x y r c]
  (all
    (bit-xoro x y r)
    (bit-ando x y c)))

(run* [r]
  (half-addero 1 1 r 1))

;;13
(run* [x y r c]
  (half-addero x y r c))

;;15
(defn full-addero [b x y r c]
  (fresh [w xy wz]
    (half-addero x y w xy)
    (half-addero w b r wz)
    (bit-xoro xy wz c)))

(run* [r c]
  (full-addero 0 1 1 r c))

;;16
(run* [r c]
  (full-addero 1 1 1 r c))

;;17
(run* [b x y r c]
  (full-addero b x y r c))

(pprint (run* [b x y r c]
          (full-addero b x y r c)))

;;42
(defn build-num [n]
  (cond
    (zero? n) '()
    (even? n) (cons 0 (build-num (/ n 2)))
    :else (cons 1 (build-num (/ (dec n) 2)))))

;;39
(build-num 0)

;;40
(build-num 36)

;;41
(build-num 19)

;;43
(defn build-num2 [n]
  (cond
    (odd? n) (cons 1 (build-num (/ (dec n) 2)))
    (and (even? n) (not (zero? n))) (cons 0 (build-num (/ n 2)))
    (zero? n) '()))

;;77
(defn poso [n]
  (fresh [a d]
    (== (llist a d) n)))

(run* [q]
  (poso '(0 1 1)))

;;78
(run* [q]
  (poso '(1)))

;;79
(run* [q]
  (poso '()))

;;80
(run* [r]
  (poso r))

;;83
(defn >1o [n]
  (fresh [a ad dd]
    (== (llist a ad dd) n)))

(run* [q]
  (>1o '(0 1 1)))

;;84
(run* [q]
  (>1o '(0 1)))

;;85
(run* [q]
  (>1o '(1)))

;;86
(run* [q]
  (>1o '()))

;;87,88
(run* [r]
  (>1o r))

;;104
;;b - carry
;;n,m,r - numbers
;;x,y,z - numbers
;;a,c,d,e - bits

;;b + n + m = r
(defn gen-addero [b n m r]
  (fresh [a c d e x y z]
    (== (llist a x) n)
    (== (llist d y) m) (poso y)
    (== (llist c z) r) (poso z)
    (all
      (full-addero b a d c e)
      (addero e x y z))))

(defn addero [b n m r]
  (conde
    [(== 0 b) (== '() m) (== n r)]
    [(== 0 b) (== '() n) (== m r) (poso m)]
    [(== 1 b) (== '() m) (addero 0 n '(1) r)]
    [(== 1 b) (== '() n) (poso m) (addero 0 '(1) m r)]
    [(== '(1) n) (== '(1) m) (fresh [a c]
                               (== (list a c) r)
                               (full-addero b 1 1 a c))]
    [(== '(1) n) (gen-addero b n m r)]
    [(== '(1) m) (>1o n) (>1o r) (addero b '(1) n r)]
    [(>1o n) (gen-addero b n m r)]))

;;106
(run* [s]
  (gen-addero 1 '(0 1 1) '(1 1) s))

;;112,113
(run* [x y]
  (addero 0 x y '(1 0 1)))

;;114
(defn +o [n m k]
  (addero 0 n m k))

;;115
(run* [x y]
  (+o x y '(1 0 1)))

;;116
(defn -o [n m k]
  (+o m k n))

;;117
;;Return ((1 1)), not the same as TRS
(run* [q]
  (-o '(0 0 0 1) '(1 0 1) q))

;;118
(run* [q]
  (-o '(0 1 1) '(0 1 1) q))

;;119
(run* [q]
  (-o '(0 1 1) '(0 0 0 1) q))






















