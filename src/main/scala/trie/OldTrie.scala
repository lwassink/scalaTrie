package trie

/**
  * Created by lwassink on 5/2/17.
  */
object OldTrie {
  abstract class Node {
    val isLeaf: Boolean
    def isEmpty: Boolean
    def contains(word: String): Boolean
    val children: Array[NonEmpty]
    val char: Char
  }

  class NonEmpty(val char: Char, val children: Array[NonEmpty], val isLeaf: Boolean) extends Node {
    def contains(word: String): Boolean =
      if (word.length == 0) true
      else if (word.length() == 1) this.findChild(word(0)).isLeaf
      else this.findChild(word(0)).contains(word.substring(1))

    def findChild(char: Char): Node = {
      def findChild(list: Array[NonEmpty]): Node =
        if (list.length == 0) new Empty
        else if (list(0).char == char) list(0)
        else findChild(list.drop(1))

      findChild(children)
    }

    def findIndex(char: Char): Integer = {
      def findIndex(list: Array[NonEmpty], idx: Integer): Integer =
        if (list.length == 0) throw new IndexOutOfBoundsException
        else if (list(0).char == char) idx
        else findIndex(list.drop(1), idx + 1)

      findIndex(children, 0)
    }

    def addChild(t: NonEmpty): NonEmpty = {
      val child = findChild(t.char)
      if (child.isEmpty) new NonEmpty(this.char, this.children ++ Array(t), this.isLeaf)
      else {
        val idx = findIndex(t.char)
        val newChildren = children.slice(0, idx) ++
          Array(new NonEmpty(child.char, child.children, child.isLeaf)) ++
          children.drop(idx + 1)
        new NonEmpty(char, newChildren, isLeaf)
      }
    }

    def insert(word: String): NonEmpty = {
      if (word.length == 0) return this

      val first = word(0)
      val rest = word.drop(1)

      if (rest == "") {
        this.addChild(new NonEmpty(first, Array(), this.findChild(first).isLeaf))
      } else {
        this.addChild(new NonEmpty(first, Array(), true).insert(rest))
      }
    }

    def isEmpty = false
  }

  class Empty extends Node {
    val isLeaf = false
    def isEmpty = true
    def contains(word: String) = false
    val children = Array()
    val char = throw new IllegalArgumentException
  }

  class OldTrie {
    var root = new NonEmpty('a', Array(), false)

    def addWord(word: String): Unit = {
      root = root.insert(word)
    }

    def contains(word: String): Boolean = root.contains(word)
  }
}
