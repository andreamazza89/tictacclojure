(ns tictacclojure.core
  (:require [tictacclojure.game :as game]
            [tictacclojure.board :as board]
            [tictacclojure.text-game-options :as game-options]
            [tictacclojure.text-prompts :as text]
            [tictacclojure.console-runner :as runner]))

(defn- console-out
  [message]
  (println message))

(defn -main
  []
  (console-out text/greeting)
  (runner/play-game (game-options/create-game)))
