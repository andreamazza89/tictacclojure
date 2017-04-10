(ns tictacclojure.minimax
  (:require [tictacclojure.board :as board]
            [tictacclojure.game :as game]))

(def initial-depth 0)
(def best-score 100)
(def tie-score 0)
(declare rate-move)

(defn- pick-first-move-available
  [moves-available maximising-mark]
  [(first moves-available) maximising-mark])

(defn- rate-game-outcome
  [game maximising-mark depth]
  (let [winners-mark (game/winner game)]
  (cond
    (= winners-mark maximising-mark) (- best-score depth)
    (= winners-mark nil)             tie-score
    :else                            (- depth best-score))))

(defn- rate-intermediate-move
  [game maximising-mark depth]
  (let [maximising? (= maximising-mark (first (game/whose-turn? game)))
        moves-available (board/moves-available (:board game))
        moves-scores (map
                       (partial rate-move game maximising-mark (inc depth))
                       moves-available)]
    (if maximising?
        (apply max moves-scores)
        (apply min moves-scores))))

(defn- rate-move
  [game maximising-mark depth position]
  (let [game-after-move (game/make-move game [position (first (game/whose-turn? game))])]
    (if (game/over? game-after-move)
      (rate-game-outcome game-after-move maximising-mark depth)
      (rate-intermediate-move game-after-move maximising-mark depth))))

(defn- pick-best-move
  [game moves-available maximising-mark]
  [(apply max-key
     (partial rate-move game maximising-mark initial-depth)
     moves-available) maximising-mark])

(defn pick-move
  [game maximising-mark]
  (let [board (:board game)
        moves-available (board/moves-available board)]
    (if (board/is-empty? board)
      (pick-first-move-available moves-available maximising-mark)
      (pick-best-move game moves-available maximising-mark))))
