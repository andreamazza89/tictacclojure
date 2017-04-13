(ns tictacclojure.text-input-parser
  (:require [tictacclojure.game :as game]
            [tictacclojure.board :as board]))

(def boards-available
  {"3" (board/new-board 3)
   "4" (board/new-board 4)})

(def valid-board-sizes (keys boards-available))

(defn parse-board
  [board-size]
  (get boards-available board-size))

(defn validate-board-size
  [board-size]
  (boolean
    (some #(= board-size %) valid-board-sizes)))

(defn- create-game-with-board
  [player-1 player-2 board]
  (game/create-game board player-1 player-2))

(def game-type-creators-available
  {"1" (partial create-game-with-board [:x :human]   [:o :human])
   "2" (partial create-game-with-board [:x :human]   [:o :easy-ai])
   "3" (partial create-game-with-board [:x :easy-ai] [:o :easy-ai])
   "4" (partial create-game-with-board [:x :human]   [:o :minimax])})

(def valid-game-types (keys game-type-creators-available))

(defn parse-game
  [game-type board]
  ((get game-type-creators-available game-type) board))

(defn validate-game-type
  [game-type]
  (boolean
    (some #(= game-type %) valid-game-types)))

(defn swap-players
  [yes-no game]
  (case yes-no
    "y" (game/swap-players game)
    "n" game))

(defn validate-swap-players
  [yes-no]
  (boolean
    (some #(= yes-no %) ["y" "n"])))
