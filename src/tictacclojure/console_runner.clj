(ns tictacclojure.console-runner
  (:require [tictacclojure.board :as board]
            [tictacclojure.text-prompts :as text]
            [tictacclojure.player :as player]
            [tictacclojure.game :as game]))

(defn- console-out
  [message]
  (println message))

(defn- end-game
  [game]
  (do
    (if (game/winner game)
      (console-out (text/winner-announcement (game/winner game)))
      (console-out text/draw-announcement))
    (console-out (text/render-board (:board game)))))

(defn- get-move-from-player
  [game]
  (let [current-player (game/whose-turn? game)]
    (console-out (text/turn-announcement (player/get-mark current-player)))
    (console-out (text/render-board (:board game)))
    (player/pick-move current-player (:board game))))

(defn play-game
  [game]
  (loop [game game]
    (if (game/over? game)
      (end-game game)
      (recur (game/make-move game (get-move-from-player game))))))
