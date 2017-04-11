(ns tictacclojure.text-input-parser
  (:require [tictacclojure.game :as game]
            [tictacclojure.board :as board]))

(defn parse-board
  [board-size]
  (case board-size
    "3" (board/new-board 3)
    "4" (board/new-board 4)))

(defn parse-game
  [selection board]
  (case selection
    "1" (game/create-game board [:x :human] [:o :human])
    "2" (game/create-game board [:x :human] [:o :easy-ai])
    "3" (game/create-game board [:x :easy-ai] [:o :easy-ai])))

(defn swap-players
  [yes-no game]
  (case yes-no
    "y" (game/swap-players game)
    "n" game))
