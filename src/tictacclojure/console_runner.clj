(ns tictacclojure.console-runner
  (:require [tictacclojure.board :as board]
            [tictacclojure.text-prompts :as text]
            [tictacclojure.player :as player]
            [tictacclojure.game :as game]))

(defn- console-out
  [message]
  (println message))

(defn play-game
  [game]
  (console-out text/greeting)
  (loop [game game]
    (if (game/over? game)
      (do
        (if (game/winner game)
          (console-out (text/winner-announcement (game/winner game)))
          (console-out text/draw-announcement))
        (console-out (text/render-board (:board game))))
      (recur (game/make-move
               game
               (do
                 (console-out (text/turn-announcement (game/whose-turn? game)))
                 (console-out (text/render-board (:board game)))
                 (player/pick-move :human (:board game) (game/whose-turn? game))))))))

