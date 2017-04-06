(ns tictacclojure.text-input-parser
  (:require [tictacclojure.game :as game]
            [tictacclojure.board :as board]))

(defn parse-game
  [selection]
  (case selection
    "1" (game/create-game (board/new-board) [:x :human] [:o :human])
    "2" (game/create-game (board/new-board) [:x :human] [:o :easy-ai])
    "3" (game/create-game (board/new-board) [:x :easy-ai] [:o :easy-ai])))

(defn swap-players
  [yes-no game]
  (case yes-no
    "y" (game/swap-players game)
    "n" game))
