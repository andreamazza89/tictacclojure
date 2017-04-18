(ns tictacclojure.text-game-options
  (:require [tictacclojure.text-prompts :as text]
            [tictacclojure.board :as board]
            [tictacclojure.text-input-parser :as parser]))

(defn- console-out
  [message]
  (println message))

(defn- console-in
  []
  (read-line))

(defn create-game
  []
  (console-out text/board-size)
  (let [chosen-board (parser/parse-board (console-in))]
    (console-out text/game-options)
    (let [chosen-game (parser/parse-game (console-in) chosen-board)]
      (console-out text/swap-players)
      (parser/swap-players (console-in) chosen-game))))
