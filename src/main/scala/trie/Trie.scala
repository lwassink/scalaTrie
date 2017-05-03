package trie

/**
  * Created by lwassink on 5/2/17.
  */
object Trie {
  class Trie[Val] {
    private var store = new Node(Map(), None)
    def put(key: String, value: Val): Unit = store = store.put(key, value, 0)
    def get(key: String): Option[Val] = store.get(key).contents
    def keysWithPrefix(pre: String): Stream[String] = store.keysWithPrefix(pre)
    def keys: Stream[String] = store.keysWithPrefix("")
    def count: Int = store.countWithPrefix("")
    def countWithPrefix(pre: String): Int = store.countWithPrefix(pre)

    private class Node(kids: Map[Char, Node], val contents: Option[Val]) {
      val count: Int =
        (if (contents.isEmpty) 0 else 1) + kids.values.foldLeft(0)((acc, node) => acc + node.count)

      def children: Char => Node = kids withDefaultValue new Node(Map(), None)

      def put(key: String, value: Val, pos: Int): Node =
        if (pos == key.length) new Node(kids, Some(value))
        else {
          val char = key.charAt(pos)
          new Node(kids updated(char, children(char).put(key, value, pos + 1)), contents)
        }

      def put(key: String, value: Val): Node = put(key, value, 0)

      def get(key: String, pos: Int): Node =
        if (pos == key.length) this else children(key.charAt(pos)).get(key, pos + 1)

      def get(key: String): Node = get(key, 0)

      def traverse[T](f: (Node, Stream[T], String) => T, prefix: String): T = {
        val keys = kids.keys.toVector.sorted.toStream
        val vals = keys.map(key => children(key).traverse[T](f, prefix ++ key.toString))
        f(this, vals, prefix)
      }

      def toStream(node: Node, v: Stream[Stream[String]], p: String): Stream[String] = {
        val base = if (node.contents.isEmpty) Stream() else Stream(p)
        v.foldLeft(base)(_ ++ _)
      }

      def keysWithPrefix(prefix: String): Stream[String] = get(prefix).traverse(toStream, prefix)

      def countWithPrefix(pre: String): Int = get(pre).count
    }
  }
}
 