(ns tictacclojure.minimax
  (:require [tictacclojure.board :as board]
            [tictacclojure.game :as game]))

(def initial-depth 0)
(def best-score 100)
(def tie-score 0)
(declare rate-move-memo)

(defn- pick-first-move-available
  [moves-available maximising-mark]
  [(first moves-available) maximising-mark])

(defn- rate-intermediate-board
  [game maximising-mark depth alpha beta]
  (let [maximising? (= maximising-mark (first (game/whose-turn? game)))
        moves-available (board/moves-available (:board game))
        scored-moves (map
                       (partial rate-move-memo game maximising-mark (inc depth) alpha beta)
                       moves-available)]
    (if maximising?
        (apply max scored-moves)
        (apply min scored-moves))))

(defn- rate-game-outcome
  [game maximising-mark depth]
  (let [winners-mark (game/winner game)]
  (cond
    (= winners-mark maximising-mark) (- best-score depth)
    (= winners-mark nil)             tie-score
    :else                            (- depth best-score))))

(defn- rate-move
  [game maximising-mark depth alpha beta position]
  (let [game-after-move (game/make-move game [position (first (game/whose-turn? game))])]
    (if (game/over? game-after-move)
      (rate-game-outcome game-after-move maximising-mark depth)
      (rate-intermediate-board game-after-move maximising-mark depth alpha beta))))

(def rate-move-memo (memoize rate-move))

(defn- pick-best-move
  [game moves-available maximising-mark alpha beta]
  (let [best-position (apply max-key
                        (partial rate-move-memo game maximising-mark initial-depth alpha beta)
                        moves-available)]
  [best-position maximising-mark]))

(defn pick-move
  [game maximising-mark]
  (let [board (:board game)
        moves-available (board/moves-available board)]
    (if (board/is-empty? board)
      (pick-first-move-available moves-available maximising-mark)
      (pick-best-move game moves-available maximising-mark -999 999))))

(def memo-pick-move (memoize pick-move))
