(ns the-reasoned-schemer.utils
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

