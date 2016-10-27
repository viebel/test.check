# Generator Examples

The following examples assume you have the following namespace alias:

```clojure
(require '[clojure.test.check.generators :as gen])
```

For the most part, these are in order of simplest to most complex. They also
skip over some of the built-in, basic generators.

## Integers 5 through 9, inclusive

```klipse
(def five-through-nine (gen/choose 5 9))
(gen/sample five-through-nine)
```

## A random element from a vector

```klipse
(def languages (gen/elements ["clojure" "haskell" "erlang" "scala" "python"]))
(gen/sample languages)
```

## An integer or nil

```klipse
(def int-or-nil (gen/one-of [gen/int (gen/return nil)]))
(gen/sample int-or-nil)
```

## An integer 90% of the time, nil 10%

```klipse
(def mostly-ints (gen/frequency [[9 gen/int] [1 (gen/return nil)]]))
(gen/sample mostly-ints)
```

## Even, positive integers

```klipse
(def even-and-positive (gen/fmap #(* 2 %) gen/pos-int))
(gen/sample even-and-positive 20)
```

## Powers of two

Let's generate exponents with `gen/s-pos-int` (strictly positive integers), and then apply the lambda to them:

```klipse
(def powers-of-two (gen/fmap #(int (Math/pow 2 %)) gen/s-pos-int))
(gen/sample powers-of-two)
```

## Sorted seq of integers

Here, we apply the sort function to each generated vector:

```klipse
(def sorted-vec (gen/fmap sort (gen/vector gen/int)))
(gen/sample sorted-vec)
```

## An integer and a boolean

```klipse
(def int-and-boolean (gen/tuple gen/int gen/boolean))
(gen/sample int-and-boolean)
```

## Any number but 5

```klipse
(def anything-but-five (gen/such-that #(not= % 5) gen/int))
(gen/sample anything-but-five)
```

It's important to note that `such-that` should only be used for predicates that
are _very_ likely to match. For example, you should _not_ use `such-that` to
filter out random vectors that are not sorted, as is this is exceedingly
unlikely to happen randomly. If you want sorted vectors, just sort them using
`gen/fmap` and `sort`.

## A vector and a random element from it

```klipse
(def vector-and-elem (gen/bind (gen/not-empty (gen/vector gen/int))
                               #(gen/tuple (gen/return %) (gen/elements %))))
(gen/sample vector-and-elem)
```

`gen/bind` and `gen/fmap` are similar: they're both binary functions that take
a generator and a function as arguments (though their argument order is
reversed). They differ in what the provided function's return value should be.
The function provided to `gen/fmap` should return a _value_. We saw that
earlier when we used `gen/fmap` to sort a vector. `sort` returns a normal
value. The function provided to `gen/bind` should return a _generator_. Notice
how above we're providing a function that returns a `gen/tuple` generator? The
decision of which to use depends on whether you want to simply transform the
_value_ of a generator (sort it, multiply it by two, etc.), or create an
entirely new generator out of it.

---

Go [back](intro.md) to the intro.
