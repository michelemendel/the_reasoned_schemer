(ns trs-clj.utils
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]))

(defn pair? [x]
  (or (lcons? x)
      (and (coll? x)
           (boolean (seq x)))))

(defn pairo [p]
  (fresh [a d]
         (conso a d p)))

(defn singleton? [l]
  (cond
    (pair? l) (empty? (rest l))
    :else false))

(defn singletono [l]
  (fresh [a]
    (== (list a) l)))

;;List of singletons
(defn loso
  [l]
  (conde
    [(emptyo l)]
    [(fresh [a]
       (firsto l a)
       (singletono a))
     (fresh [d]
       (resto l d)
       (loso d))]))

(defn listo [l]
  (conde
    ((emptyo l))
    ((pairo l)
     (fresh (d)
       (resto l d)
       (listo d)))))

;;List of lists
(defn lolo [l]
  (conde
    [(emptyo l)]
    [(fresh [a]
       (firsto l a)
       (listo a))
     (fresh [b]
       (resto l b)
       (lolo b))]))

(defn proper-membero [x l]
  (conde
    [(firsto l x)
     (fresh [d]
       (resto l d)
       (listo d))]
    [(fresh [d]
       (resto l d)
       (proper-membero x d))]))

(defn unwrapo [x out]
  (conde
    [(pairo x) (fresh [a]
                 (firsto x a)
                 (unwrapo a out))]
    [(== out x)]))