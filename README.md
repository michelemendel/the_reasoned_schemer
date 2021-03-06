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

Mini/MicroKanren
- http://minikanren.org
- [µKanren: A Minimal Functional Core
  for Relational Programming](http://webyrd.net/scheme-2013/papers/HemannMuKanren2013.pdf)
- [Relational Programming in miniKanren: Techniques, Applications, and Implementations](https://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.363.5478&rep=rep1&type=pdf)
- [First-order miniKanren representation: Great for tooling and search](http://minikanren.org/workshop/2019/minikanren19-final2.pdf)
- [Implementation in Scheme](https://github.com/jasonhemann/microKanren)

---
## The Laws & Commandments from the book

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


### The Law of Swapping conde Lines 
Swapping two conde lines does not affect the values contributed by conde.

### The Law of conda
The first conda line whose question succeeds is the only line that can contribute values.

### The Law of condu
condu behaves like conda, except that a successful question succeeds only once.

### The First Commandment
Within each sequence of goals, move non-recursive goals before recursive goals.  
This is also mentioned by William Byrd in the video [miniKanren Philosophy - William Byrd & Daniel Friedman](https://www.youtube.com/watch?v=fHK-uS-Iedc&t=1069s).

### The Second Commandment (Initial)
If prior to determining the question of a conda line a variable is fresh, it must remain fresh in that line’s question.

### The Second Commandment (Final) 
If prior to determining the question of a conda or condu line a variable is fresh, it must remain fresh in that line’s question.


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
The fn singleton? (see utils.clj) determines if its argument is a proper list of length one. 

### a proper list
A list is proper if it is the empty list or if it is a pair whose cdr is proper.
(llist ...) is not a proper list

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
| pair?           | pair?           | se utils.clj
| list?           | seq?            | 1.proper lists, 2.For explanation, see https://github.com/clojure/core.logic/wiki/Differences-from-The-Reasoned-Schemer

### Discrepancies and notable issues
- 1.67-70 - A difficulty
- 1.55 - A question
- 2.25 - Different result than TRS
- 3.41 - Order of clauses changes the result
- 3.42 - Different result than TRS
- 3.43 - Different result than TRS
- 4.39 - Different result than TRS
- 4.53 - Different result than TRS
- 5.26-28,48,56,61 - Using core.util/rembero gives very different results. I have to read this chapter again.
- 8.75-77 - Different result than TRS
- 8.91-92,94 - Different result than TRS
- 9.56 - Different result than TRS

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

---
### MiniKanren vs Prolog
https://stackoverflow.com/questions/28467011/what-are-the-main-technical-differences-between-prolog-and-minikanren-with-resp

---
### Predicate Logic Resolution

INSEADO
- Implications out
- Negations in
- Standardize variables
- Existentials out (∃)
- Alls out (∀)
- Distribution
- Operators out

*example: [8 2 8 2 Clausal Form 9 min](https://www.youtube.com/watch?v=6JmxpTj-f3o&ab_channel=OsirisSalazar)*

---
### SICP
https://youtu.be/GReBwkGFZcs?list=PLE18841CABEA24090&t=3264