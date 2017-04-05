(ns tictacclojure.game
  (:require [tictacclojure.board :as board]))

(defn create-game
  [board & players]
  {:board board :players players})

(defn whose-turn?
  [game]
  (first (:players game)))

(defn make-move
  [game cell-position]
  (let [current-player (whose-turn? game)
        new-board (board/add-move (:board game) [cell-position current-player])
        new-players (reverse (:players game))]
        {:board new-board :players new-players}))

(defn winner
  [game]
  (let [board (:board game)]
    (board/find-line-with-same-mark board)))

(defn over?
  [game]
  (let [board (:board game)]
    (boolean (or (board/full? board) (winner game)))))
