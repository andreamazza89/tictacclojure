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
  (let [position (Integer/parseInt
                   (ui/get-input
                     (partial validate-text-move board)
                     prompts/invalid-move))]
    [position mark]))

(defn- pick-move-for-easy-ai
  [game mark]
  (let [board (:board game)]
  [(first (board/positions-available board)) mark]))

(defn- pick-move-for-minimax
  [game maximising-mark]
  (minimax/memo-pick-move game maximising-mark))

(defmulti  pick-move (fn [[mark nature] game] [nature]))
(defmethod pick-move [:human]   [[mark _] game] (pick-move-for-human    game mark))
(defmethod pick-move [:easy-ai] [[mark _] game] (pick-move-for-easy-ai  game mark))
(defmethod pick-move [:minimax] [[mark _] game] (pick-move-for-minimax  game mark))

(defn get-mark
  [[mark nature]]
  mark)
