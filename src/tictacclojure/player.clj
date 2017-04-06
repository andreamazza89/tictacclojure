(ns tictacclojure.player
  (:require [tictacclojure.board :as board-functions]))

(defn- pick-move-for-human
  []
  (Integer/parseInt (read-line)))

(defn- pick-move-for-easy-ai
  [board]
  (first (board-functions/moves-available board)))

(defn get-mark
  [[mark nature]]
  mark)

(defmulti  pick-move (fn [[mark nature] board] [nature]))
(defmethod pick-move [:human] [_ _] (pick-move-for-human))
(defmethod pick-move [:easy-ai] [_ board] (pick-move-for-easy-ai board))
