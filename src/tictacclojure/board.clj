(ns tictacclojure.board
  (:require [clojure.math.numeric-tower :as math]))

(defn new-board
  ([] (new-board 3))
  ([size] (vec
            (range
              (* size size)))))

(defn full?
  [board]
    (every? keyword? board))

(defn is-empty?
  [board]
  (every? integer? board))

(defn- replace-position-with-mark
  [mark replace-position current-position]
  (if
    (= replace-position current-position)
    mark
    current-position))

(defn add-move
  [board [position mark]]
  (map
    (partial replace-position-with-mark mark position)
    board))

(defn positions-available
  [board]
  (filter integer? board))

(defn position-available?

  [board position]
  (boolean
    (some #(= position %) (positions-available board))))

(defn get-cell-at
  [board cell-position]
  (nth board cell-position))

(defn- all-cells-same-mark?
  [cells]
  (= 1 (count (set cells))))

(defn- size
  [board]
  (math/sqrt (count board)))

(defn rows
  [board]
  (partition (size board) board))

(defn- rotate-left
  [[first-cell & other-cells]]
  (conj (vec other-cells) first-cell))

(defn- columns
  [board]
  (loop
    [board board columns-left (size board) columns []]
    (if (= 0 columns-left)
      columns
      (recur (rotate-left board) (dec columns-left) (conj columns (take-nth (size board) board))))))

(defn- downwards-diagonal
  [board]
  (map-indexed
    (fn [index row] (nth row index))
    (rows board)))

(defn- upwards-diagonal
  [board]
  (downwards-diagonal (flatten (reverse (rows board)))))

(defn- diagonals
  [board]
  [(upwards-diagonal board) (downwards-diagonal board)])

(defn find-mark-from-full-line
  [board]
  (let [lines (concat (columns board) (rows board) (diagonals board))]
    (ffirst (filter all-cells-same-mark? lines))))
