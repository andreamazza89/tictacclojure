(ns tictacclojure.game
  (:require [tictacclojure.board :as board]
            [tictacclojure.player :as player]))

(defn create-game
  [board & players]
  {:board board :players players})

(defn whose-turn?
  [game]
  (first (:players game)))

(defn swap-players
  [game]
  {:board (:board game) :players (reverse (:players game))})

(defn make-move
  [game move]
  (let [current-player (whose-turn? game)
        new-board (board/add-move (:board game) move)
        new-players (:players (swap-players game))]
        {:board new-board :players new-players}))

(defn winner
  [game]
  (let [board (:board game)]
    (board/find-mark-from-full-line board)))

(defn over?
  [game]
  (let [board (:board game)]
    (boolean (or (board/full? board) (winner game)))))
