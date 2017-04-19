(ns tictacclojure.core
  (:require [tictacclojure.game :as game]
            [tictacclojure.board :as board]
            [tictacclojure.console-ui :as ui]
            [tictacclojure.text-game-options :as game-options]
            [tictacclojure.text-prompts :as text]
            [tictacclojure.console-runner :as runner]))

(defn -main
  []
  (ui/clear-screen)
  (ui/print-out text/greeting)
  (runner/play-game (game-options/create-game)))
