(ns tictacclojure.text-prompts
  (:require [tictacclojure.board :as board]
            [clojure.string :refer :all]))

(def carriage-return (with-out-str (newline)))

(def greeting "Welcome to tictacclojure")

(def board-size "Please select a board size:\n3->3x3\n4->4x4")

(def invalid-board-size "Invalid board size: please try again.")

(def game-options "Please select a game mode:\n1. Human V Human\n2. Human V Easy-Ai\n3. Easy-Ai V Easy-Ai\n4. Human V MiniMax")

(def invalid-game-type "Invalid game mode: please try again.")

(def swap-players "Would you like to swap who goes first? (y/n)")

(def invalid-answer-to-swap-players "Invalid answer, please try again.")

(def draw-announcement "It was a draw!")

(def invalid-move "Invalid move: please try again.")

(defn turn-announcement
  [next-player]
  (str "It's " (name next-player) "'s turn, please pick a move"))

(defn winner-announcement
  [winner]
  (str "The winner was: " (name winner)))

(defn- adjust-double-digits
  [rendered-board]
  (replace rendered-board #"\s(\d\d)\s" "$1 "))

(defn- remove-double-spacers
  [rendered-board]
  (replace rendered-board "||" "|"))

(defn- surround-with-spacer
  [cell-content]
  (let [cell-spacer-left "| "
        cell-spacer-right " |"]
  (str cell-spacer-left cell-content cell-spacer-right)))

(defn- render-cell
  [cell]
  (if (integer? cell)
    (surround-with-spacer cell)
    (surround-with-spacer (name cell))))

(defn- render-row
  [parsed-board row]
  (apply str parsed-board
    (conj (vec (map render-cell row)) carriage-return carriage-return)))

(defn render-board
  [board]
  (let [rows (board/rows board)]
    (->
      (reduce render-row "" rows)
      (remove-double-spacers)
      (adjust-double-digits))))
