/**
  * Created by lwassink on 5/2/17.
  */

import org.scalatest.FunSpec
import trie.Trie._

class TrieSuite extends FunSpec {
  val populatedTrie = new Trie[Int]
  populatedTrie.put("cat", 1)
  populatedTrie.put("car", 2)
  populatedTrie.put("carts", 3)
  populatedTrie.put("dad", 4)
  populatedTrie.put("dog", 5)

  describe("Trie") {
    describe("#get") {
      it("returns Some[value] for a key with a value") {
        assert(populatedTrie.get("car") == Some(2))
        assert(populatedTrie.get("carts") == Some(3))
      }

      it("returns None for a key with no value") {
        assert(populatedTrie.get("cart") == None)
      }

      it("returns None for a key not in the Trie") {
        assert(populatedTrie.get("x") == None)
      }
    }

    describe("#put") {
      val blankTrie = new Trie[Int]

      it("adds a new value") {
        blankTrie.put("apple", 7)
        assert(blankTrie.get("apple") == Some(7))
      }

      it("or overwrites an old one") {
        blankTrie.put("apple", 5)
        assert(blankTrie.get("apple") == Some(5))
      }
    }

    describe("#keys") {
      it("returns a stream") {
        assert(populatedTrie.keys.isInstanceOf[Stream[String]])
      }

      it("returns the keys in alphabetic order") {
        assert(populatedTrie.keys.toList == List("car", "carts", "cat", "dad", "dog"))
      }
    }

    describe("#keysWithPrefix") {
      it("returns a stream") {
        assert(populatedTrie.keysWithPrefix("c").isInstanceOf[Stream[String]])
      }

      it("returns only the keys begining with the prefix") {
        assert(populatedTrie.keysWithPrefix("c").toList == List("car", "carts", "cat"))
        assert(populatedTrie.keysWithPrefix("da").toList == List("dad"))
        assert(populatedTrie.keysWithPrefix("x").isEmpty)
      }
    }
  }
}
