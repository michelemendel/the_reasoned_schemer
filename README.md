# The Reasoned Schemer (second edition) in Clojure core.logic


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
- `#s s#`
- `#u u#`
- `caro firsto`
- `cdro resto`
- `(,a) (list a) or [a]
- cons lcons


## License

Copyright © 2021 Mendel

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
