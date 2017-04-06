(ns tictacclojure.board
  (:require [clojure.math.numeric-tower :as math]))

(defn new-board
  []
  [0 1 2 3 4 5 6 7 8])

(defn full?
  [board]
    (every? keyword? board))

(defn add-move
  [board [move-cell mark]]
  (map
    (fn [cell]
      (if (= cell move-cell)
        mark
        cell))
    board))

(defn moves-available
  [board-with-move]
  (filter integer? board-with-move))

(defn get-cell-at
  [board cell-position]
  (nth board cell-position))

(defn- all-cells-same-mark?
  [cells]
  (= 1 (count (set cells))))

(defn rows
  [board]
  (partition 3 board))

(defn- size
  [board]
  (math/sqrt (count board)))

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
