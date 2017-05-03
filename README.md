# Scala Trie

A functional implementation of the Trie data structure in Scala.

## API

The `Trie` class has a symbol-table interface inspired by [this](http://algs4.cs.princeton.edu/52trie/TST.java.html) Java implementation due to Sedgewick and Wayne.
The public methods are
```
  Trie[Val]#put(key: String, value: Val): Unit
  Trie[Val]#get(key: String): Option[Val]
  Trie[Val]#keys: Stream[String]
  Trie[Val]#keysWithPrefix(pre: String): Stream[String]
  Trie[Val]#count: Int
  Trie[Val]#countWithPrefix(pre: String): Int
```
Note in particular that values are returned as options, so `get('x')` would return `None` if `x` was not in the Trie.
Also, the iterators `keys` and `keysWithPrefix` both returns streams of strings.
They will return only the keys corresponding to actual values, and they will return those keys in alphabetical order.

## Implementation

Internally `Trie` uses a class `Node`.
This class is purely functional and is never mutated.
My main goal for this project was to write a functional implementation of the Trie data structure in idiomatic Scala.
While it could be made more efficient, I feel that `Node` achieves this goal.

## Testing

This project includes tests written in scalatest.
I chose to use `FunSpec` because my previous testing experience was with `RSpec` in Ruby.
