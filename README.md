# Scala Trie

This is my implementation of the Trie data structure in Scala.
It has a symbol-table interface inspired by [this](http://algs4.cs.princeton.edu/52trie/TST.java.html) Java implementation due to Sedgwick and Wayne.
The public methods are
```
  Trie[Val]#put(key: String, value: Val): Unit
  Trie[Val]#get(key: String): Option[Val]
  Trie[Val]#keys: Stream[String]
  Trie[Val]#keysWithPrefix(pre: String): Stream[String]
```
Note in particular that values are returned as options, so `get('x')` would return `None` if `x` was not in the Trie.
Also, the iterators `keys` and `keysWithPrefix` both returns streams of strings.
They will return only the keys corresponding to actual values, and they will return those keys in alphabetical order.

Internally `Trie` uses a class `Node`.
This class is purely functional and is never mutated.
My main goal for this project was to write a functional implementation of the Trie data structure in idiomatic Scala.
While it could be made more eficient, I feel that `Node` achieves this goal.
