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

(defn listo [l]
  (conde
    ((emptyo l))
    ((pairo l)
     (fresh (d)
       (resto l d)
       (listo d)))))
