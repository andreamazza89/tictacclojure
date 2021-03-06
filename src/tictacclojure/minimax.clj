(ns tictacclojure.minimax
  (:require [tictacclojure.board :as board]
            [tictacclojure.game :as game]))

(def initial-depth 0)
(def maximum-depth 5)
(def maximum-moves-to-evaluate 12)
(def plus-infinity 99999999)
(def minus-infinity (- plus-infinity))
(def best-score (+ 1 maximum-depth))
(def tie-score 0)
(declare rate-move-memoed)

(defn- max-reduce
  [game maximising-mark depth alpha beta best-score-so-far move]
  (if (<= beta alpha)
    (reduced best-score-so-far)
    (max
      best-score-so-far
      (rate-move-memoed game maximising-mark (inc depth) (max best-score-so-far alpha) beta move))))

(defn- min-reduce
  [game maximising-mark depth alpha beta best-score-so-far move]
  (if (<= beta alpha)
    (reduced best-score-so-far)
    (min
      best-score-so-far
      (rate-move-memoed game maximising-mark (inc depth) alpha (min best-score-so-far beta) move))))

(defn- rate-intermediate-board
  [game maximising-mark depth alpha beta]
  (let [maximising? (= maximising-mark (first (game/whose-turn? game)))
        positions-available (board/positions-available (:board game))]
    (if maximising?
      (reduce (partial max-reduce game maximising-mark depth alpha beta)
              minus-infinity
              positions-available)
      (reduce (partial min-reduce game maximising-mark depth alpha beta)
              plus-infinity
              positions-available))))

(defn- rate-game-outcome
  [game maximising-mark depth]
  (let [winners-mark (game/winner game)]
  (cond
    (= winners-mark maximising-mark) (- best-score depth)
    (= winners-mark nil)             tie-score
    :else                            (- depth best-score))))

(defn- rate-move
  [game maximising-mark depth alpha beta position]
  (let [game-after-move (game/make-move game [position (first (game/whose-turn? game))])
        positions-available (board/positions-available (:board game))]
    (if (or
          (game/over? game-after-move)
          (>= depth maximum-depth)
          (>= (count positions-available) maximum-moves-to-evaluate))
      (rate-game-outcome game-after-move maximising-mark depth)
      (rate-intermediate-board game-after-move maximising-mark depth alpha beta))))

(def rate-move-memoed (memoize rate-move))

(defn- pick-best-position
  [game maximising-mark]
  (let [positions-available (board/positions-available (:board game))
        alpha minus-infinity
        beta plus-infinity
        best-position (apply max-key
                        (partial rate-move-memoed game maximising-mark initial-depth alpha beta)
                        positions-available)]
  best-position))

(def memo-pick-best-position (memoize pick-best-position))
