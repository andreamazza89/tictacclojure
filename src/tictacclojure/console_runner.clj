(ns tictacclojure.console-runner
  (:require [tictacclojure.board :as board]
            [tictacclojure.text-prompts :as prompts]
            [tictacclojure.console-ui :as ui]
            [tictacclojure.player :as player]
            [tictacclojure.game :as game]))

(defn- end-game
  [game]
  (do
    (ui/clear-screen)
    (if (game/winner game)
      (ui/print-out (prompts/winner-announcement (game/winner game)))
      (ui/print-out prompts/draw-announcement))
    (ui/print-out (prompts/render-board (:board game)))))

(defn- get-move-from-player
  [game]
  (let [current-player (game/whose-turn? game)]
    (ui/clear-screen)
    (ui/print-out (prompts/turn-announcement (player/get-mark current-player)))
    (ui/print-out (prompts/render-board (:board game)))
    (player/pick-move current-player game)))

(defn play-game
  [game]
  (loop [game game]
    (if (game/over? game)
      (end-game game)
      (recur (game/make-move game (get-move-from-player game))))))
