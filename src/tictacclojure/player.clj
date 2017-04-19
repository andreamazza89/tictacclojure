(ns tictacclojure.player
  (:require [tictacclojure.board :as board]
            [tictacclojure.console-ui :as ui]
            [tictacclojure.text-prompts :as prompts]
            [tictacclojure.minimax :as minimax]))

(defn- validate-text-move
  [board text-position]
  (try
    (let [position (Integer/parseInt text-position)]
      (board/position-available? board position))
    (catch NumberFormatException exception false)))

(defn- pick-move-for-human
  [{:keys [board]} mark]
  (let [chosen-position (Integer/parseInt
                   (ui/get-input
                     (partial validate-text-move board)
                     prompts/invalid-move))]
    [chosen-position mark]))

(defn- pick-move-for-easy-ai
  [game mark]
  (let [board (:board game)
        chosen-position (first (board/positions-available board))]
    [chosen-position mark]))

(defn- pick-move-for-minimax
  [game mark]
  (let [chosen-position (minimax/memo-pick-best-position game mark)]
    [chosen-position mark]))

(defmulti  pick-move (fn [[mark nature] game] [nature]))
(defmethod pick-move [:human]   [[mark _] game] (pick-move-for-human    game mark))
(defmethod pick-move [:easy-ai] [[mark _] game] (pick-move-for-easy-ai  game mark))
(defmethod pick-move [:minimax] [[mark _] game] (pick-move-for-minimax  game mark))

(defn get-mark
  [[mark nature]]
  mark)
