(ns tictacclojure.helpers)

;artificial board creation
(defn- mark-to-cell
  [index mark]
  (if (= :_ mark) index mark))

(defn create-board
  [& marks]
  (into [] (map-indexed mark-to-cell marks)))
