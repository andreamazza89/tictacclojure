(ns tictacclojure.text-game-options
  (:require [tictacclojure.text-prompts :as text]
            [tictacclojure.text-input-parser :as parser]))

(defn- console-out
  [message]
  (println message))

(defn- console-in
  []
  (read-line))

(defn create-game
  []
  (console-out text/game-options)
  (let [chosen-game (parser/parse-game (console-in))]
    (console-out text/swap-players)
    (parser/swap-players (console-in) chosen-game)))

