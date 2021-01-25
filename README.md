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


