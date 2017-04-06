(ns tictacclojure.player
  (:require [tictacclojure.board :as board-functions]))

(defn- pick-move-for-human
  []
  (Integer/parseInt (read-line)))

(defn- pick-move-for-easy-ai
  [board]
  (first (board-functions/moves-available board)))

(defmulti  pick-move (fn [nature board mark] [nature]))
(defmethod pick-move [:human] [_ _ _] (pick-move-for-human))
(defmethod pick-move [:easy-ai] [_ board _] (pick-move-for-easy-ai board))
