(ns tictacclojure.core
  (:require [tictacclojure.game :as game]
            [tictacclojure.board :as board]
            [tictacclojure.text-prompts :as text]
            [tictacclojure.console-runner :as runner]))

(defn- console-out
  [message]
  (println message))

(defn -main
  []
  (console-out text/greeting)
  (runner/play-game (game/create-game (board/new-board) [:x :human] [:o :easy-ai])))
