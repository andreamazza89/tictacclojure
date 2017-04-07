(ns tictacclojure.player
  (:require [tictacclojure.board :as board]))

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

(defn- rate-game-outcome
  [game mark]

  ;(println board)
  ;(println mark)
  5)

(defn- rate-move
  [board mark move]
    (rate-game-outcome
      (board/add-move board [move mark])
      mark))

(defn- pick-move-for-minimax
  [game mark]
  (apply max-key
         (partial rate-move game mark)
         (board/moves-available game)))

(defmulti  pick-move (fn [[mark nature] game] [nature]))
(defmethod pick-move [:human]   [[mark _] _]    (pick-move-for-human mark))
(defmethod pick-move [:easy-ai] [[mark _] game] (pick-move-for-easy-ai game mark))
(defmethod pick-move [:minimax] [[mark _] game] (pick-move-for-minimax game mark))

(defn get-mark
  [[mark nature]]
  mark)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn foo [n] (cond
                (> n 5) "ciao"
                (= n 2) "miao"))
