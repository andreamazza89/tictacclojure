(ns tictacclojure.player
  (:require [tictacclojure.board :as board]
            [tictacclojure.minimax :as minimax]))

(defn- console-in
  []
  (read-line))

(defn- pick-move-for-human
  [mark]
  [(Integer/parseInt (console-in)) mark])

(defn- pick-move-for-easy-ai
  [game mark]
  (let [board (:board game)]
  [(first (board/moves-available board)) mark]))

(defn- pick-move-for-minimax
  [game maximising-mark]
  (minimax/memo-pick-move game maximising-mark))

(defmulti  pick-move (fn [[mark nature] game] [nature]))
(defmethod pick-move [:human]   [[mark _] _]    (pick-move-for-human mark))
(defmethod pick-move [:easy-ai] [[mark _] game] (pick-move-for-easy-ai game mark))
(defmethod pick-move [:minimax] [[mark _] game] (pick-move-for-minimax game mark))

(defn get-mark
  [[mark nature]]
  mark)
