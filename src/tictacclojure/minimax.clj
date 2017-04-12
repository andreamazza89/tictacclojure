(ns tictacclojure.minimax
  (:require [tictacclojure.board :as board]
            [tictacclojure.game :as game]))

(def initial-depth 0)
(def plus-infinity 99999999)
(def minus-infinity (- plus-infinity))
(def best-score 100)
(def tie-score 0)
(declare rate-move-memo)

(def calls-to-rate-move (ref 0))

(defn- pick-first-move-available
  [moves-available maximising-mark]
  [(first moves-available) maximising-mark])

(defn- pruned-move-rating-map
  [game maximising-mark depth alpha beta moves-available]
  (map (partial rate-move-memo game maximising-mark (inc depth) alpha beta)
       moves-available))

(defn- JUlio-max
  [game maximising-mark depth alpha beta best-score-so-far move]
  (if (<= beta alpha)
        (reduced best-score-so-far)
        (max best-score-so-far (rate-move-memo game maximising-mark (inc depth) (max best-score-so-far alpha) beta move))))

(defn- JUlio-min
  [game maximising-mark depth alpha beta best-score-so-far move]
  (if (<= beta alpha)
        (reduced best-score-so-far)
        (min best-score-so-far (rate-move-memo game maximising-mark (inc depth) alpha (min best-score-so-far beta) move))))

(defn- rate-intermediate-board
  [game maximising-mark depth alpha beta]
  (let [maximising? (= maximising-mark (first (game/whose-turn? game)))
        moves-available (board/moves-available (:board game))]
    (if maximising?

;; pruned version below:
        (reduce (partial JUlio-max game maximising-mark depth alpha beta)
                minus-infinity
                moves-available)
        (reduce (partial JUlio-min game maximising-mark depth alpha beta)
                plus-infinity
                moves-available))))

;;; non-pruned vestion below:
    ;    (apply max (map
    ;                 (partial rate-move-memo game maximising-mark (inc depth) alpha beta)
    ;                 moves-available))
    ;    (apply min (map
    ;                 (partial rate-move-memo game maximising-mark (inc depth) alpha beta)
    ;                 moves-available)))))

(defn- rate-game-outcome
  [game maximising-mark depth]
  (let [winners-mark (game/winner game)]
  (cond
    (= winners-mark maximising-mark) (- best-score depth)
    (= winners-mark nil)             tie-score
    :else                            (- depth best-score))))

(defn- rate-move
  [game maximising-mark depth alpha beta position]

  (dosync (alter calls-to-rate-move inc))

  (let [game-after-move (game/make-move game [position (first (game/whose-turn? game))])]
    (if (game/over? game-after-move)
      (rate-game-outcome game-after-move maximising-mark depth)
      (rate-intermediate-board game-after-move maximising-mark depth alpha beta))))

(def rate-move-memo (memoize rate-move))
;(def rate-move-memo rate-move)

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

      (let [bla (pick-best-move game moves-available maximising-mark minus-infinity plus-infinity)]
          (println (str "========================" (deref calls-to-rate-move)))
          bla))))

(def memo-pick-move (memoize pick-move))
