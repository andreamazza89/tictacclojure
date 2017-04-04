(ns tictacclojure.board)

(defn new-board
  []
  [0 1 2 3 4 5 6 7 8])

(defn empty?
  [board]
  (every? integer? board))

(defn add-move
  [board [position mark]]
  (map
    (fn [cell] (if (= cell position) mark cell))
    board))

(defn moves-available
  [board-with-move]
  (filter integer? board-with-move))
