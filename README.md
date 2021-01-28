# The Reasoned Schemer in Clojure core.logic

*The Reasoned Schemer, second edition  
Friedman, Daniel P.; Byrd, William E.; Kiselyov, Oleg; Hemann, Jason.*


- [The Reasoned Schemer 2ndEd at Github](https://github.com/TheReasonedSchemer2ndEd)
  - [code from the book](https://github.com/TheReasonedSchemer2ndEd/CodeFromTheReasonedSchemer2ndEd)
- [core.logic](https://github.com/clojure/core.logic)
  - [clojuredocs](https://clojuredocs.org/clojure.core.logic)

See also implementations in core.logic of TRS 1st edition
- https://github.com/martintrojer/reasoned-schemer-core.logic
- https://github.com/philoskim/reasoned-schemer-for-clojure

MicroKanren
- [µKanren: A Minimal Functional Core
  for Relational Programming](http://webyrd.net/scheme-2013/papers/HemannMuKanren2013.pdf)
- [Relational Programming in miniKanren: Techniques, Applications, and Implementations](https://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.363.5478&rep=rep1&type=pdf)
- [First-order miniKanren representation: Great for tooling and search](http://minikanren.org/workshop/2019/minikanren19-final2.pdf)
- [Implementation in Scheme](https://github.com/jasonhemann/microKanren)

---
## The Laws from the book

### The First Law of ==
(== v w) can be replaced by (≡ w v).

### The Second Law of ==
If x is fresh, then (== v x) succeeds and associates v with x, unless x occurs in v.

### The Law of conde 
Every successful conde line contributes one or more values.

### The Law of #u 
Any conde line that has #u as a top-level goal cannot contribute values.

### The Law of #s 
Any top-level #s can be removed from a conde line.


---
## Definitions and concepts

### logic variables
- Are being created of the arguments in the run and fresh functions (aka goal constructor)
  - The arguments in the run function will be the output
- Can also be created with `lvar`
- Can be unified (bound) to a concrete value or reified to a value like _0


### Every variable is initially fresh
A variable is no longer fresh if it becomes associated with a
non-variable value or if it becomes associated with a variable
that, itself, is no longer fresh.

### pair
A sequences with an improper tail.

*Note* In Lisp this is what's called a cell, i.e. (a . b). This means that a pair is not something of only two items. see https://en.wikipedia.org/wiki/Cons

### improper tail
The tail is a logic variable instead of a list

### singleton
The fn singleton? (see below) determines if its argument is a proper list of length one. 

### a proper list
A list is proper if it is the empty list or if it is a pair whose cdr is proper.


---
### TRC vs core.logic
|TRC              |core.logic       |comment
|-----------------|-----------------|-------|
| #s              | s#              |
| #u              | u#              |
| caro            | firsto          |
| cdro            | resto           |
| `(,a)           | (list a) or [a] |
| cons            | lcons           |
| null?           | empty?          |
| nullo           | emptyo          | With a fresh variable q emptyo succeeds, since q will be ()
| pair?           | se utils.clj    |
| list?           | seq?            | 1.proper lists, 2.For explanation, see https://github.com/clojure/core.logic/wiki/Differences-from-The-Reasoned-Schemer


---
### A note about the difference between the first and second edition of TRS, and it's relation to core.logic

Thanks to @rickmoynihan (slack)

*(slightly edited)*

Both editions of TRS include a complete implementation in the final chapters / appendix.  The implementation in the 1st edition is minikanren, and in the 2nd edition - as I understand - is microkanren).

If you want to use the book to understand core.logic’s implementation (rather than the high level language) then these are notable differences.
If you want to understand logic programming in the abstract; there’s mainly just superficial differences you can gloss over.

There are also some differences at the language level. They’re easy to find the equivalence for.  See pages xv and xvi in TRS2.

Also, see this conversation between Rick and William Byrd
https://groups.google.com/g/minikanren/c/fMKSBmOm_XM/m/V0K30F8lBwAJ
