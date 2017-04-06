(ns tictacclojure.text-prompts
  (:require [tictacclojure.board :as board]))

(def greeting "Welcome to tictacclojure")

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
