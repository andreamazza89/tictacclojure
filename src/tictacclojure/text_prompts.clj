(ns tictacclojure.text-prompts
  (:require [tictacclojure.board :as board]))

(def greeting "Welcome to tictacclojure")

(def board-size "Please select a board size:\n3->3x3\n4->4x4")

(def game-options "Please select a game mode:\n1. Human V Human\n2. Human V Easy-Ai\n3. Easy-Ai V Easy-Ai")

(def swap-players "Would you like to swap who goes first? (y/n)")

(def draw-announcement "It was a draw!")

(defn turn-announcement
  [next-player]
  (str "It's " (name next-player) "'s turn, please pick a move"))

(defn winner-announcement
  [winner]
  (str "The winner was: " (name winner)))

(defn- surround-with-spacer
  [cell-content]
  (let [cell-spacer "|"]
  (str cell-spacer cell-content cell-spacer)))

(defn- render-cell
  [cell]
  (if (integer? cell)
    (surround-with-spacer cell)
    (surround-with-spacer (name cell))))

(defn- render-row
  [parsed-board row]
  (apply str parsed-board
    (conj (vec (map render-cell row)) (with-out-str (newline)))))

(defn render-board
  [board]
  (let [rows (board/rows board)]
    (reduce render-row "" rows)))
