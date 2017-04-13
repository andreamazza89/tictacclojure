(ns tictacclojure.text-game-options
  (:require [tictacclojure.text-prompts :as prompts]
            [tictacclojure.board :as board]
            [tictacclojure.console-ui :as ui]
            [tictacclojure.text-input-parser :as parser]))

(defn- ask-user-for-board-size
  []
  (ui/get-input
    prompts/board-size
    parser/validate-board-size
    prompts/invalid-board-size))

(defn- ask-user-for-game-type
  []
  (ui/get-input
    prompts/game-options
    parser/validate-game-type
    prompts/invalid-game-type))

(defn- ask-user-to-swap-players-order
  []
  (ui/get-input
    prompts/swap-players
    parser/validate-swap-players
    prompts/invalid-answer-to-swap-players))

(defn create-game
  []
  (let [chosen-board-size   (ask-user-for-board-size)
        chosen-game-type    (ask-user-for-game-type)
        swap-players-order? (ask-user-to-swap-players-order)
        chosen-board (parser/parse-board  chosen-board-size)
        chosen-game  (parser/parse-game   chosen-game-type chosen-board)
        swapped-game (parser/swap-players swap-players-order? chosen-game)]

      swapped-game))
