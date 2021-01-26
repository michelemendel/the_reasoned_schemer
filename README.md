# The Reasoned Schemer (second edition) in Clojure core.logic

- https://github.com/clojure/core.logic
- https://clojuredocs.org/clojure.core.logic

See also
- https://github.com/martintrojer/reasoned-schemer-core.logic
- https://github.com/philoskim/reasoned-schemer-for-clojure


### Every variable is initially fresh. 
A variable is no longer fresh if it becomes associated with a 
non-variable value or if it becomes associated with a variable 
that, itself, is no longer fresh.


### The First Law of ≡
(≡ v w) can be replaced by (≡ w v).


### The Second Law of ≡
If x is fresh, then (≡ v x) succeeds and associates v with x, unless x occurs in v.

### The Law of conde 
Every successful conde line contributes one or more values.


### TRC vs core.logic
|TRC              |core.logic       |
|-----------------|-----------------|
| #s              | s#              |
| #u              | u#              |
| caro            | firsto          |
| cdro            | resto           |
| `(,a)           | (list a) or [a] |
| cons            | lcons           |
| null?           | empty?          |
| nullo           | emptyo          |
| pair?           | se fn below     |


```clojure
(defn pair? [x]
  (or (lcons? x) (and (coll? x) (seq x))))
```
```clojure
(defn pairo [p]
  (fresh [a d]
         (conso a d p)))
```

### A note about the difference between the first and second edition of The Reasoned Schemer

Thanks to @rickmoynihan (slack)

*(slightly edited)*

Both editions of TRS include a complete implementation in the final chapters / appendix.  The implementation in the 1st edition is minikanren, and in the 2nd edition - as I understand - is microkanren).

If you want to use the book to understand core.logic’s implementation (rather than the high level language) then these are important differences.
If you want to understand logic programming in the abstract; there’s mainly just superficial differences you can gloss over.

