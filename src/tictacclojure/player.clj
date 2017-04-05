(ns tictacclojure.player)

(defmulti  pick-move (fn [nature board mark] [nature]))
(defmethod pick-move [:human] [_ _ _] (Integer/parseInt (read-line)))
