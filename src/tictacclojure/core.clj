(ns tictacclojure.core
  (:require [tictacclojure.game :as game]
            [tictacclojure.board :as board]
            [tictacclojure.console-runner :as runner]))

(defn -main
  []
  (runner/play-game (game/create-game (board/new-board) :x :o)))
