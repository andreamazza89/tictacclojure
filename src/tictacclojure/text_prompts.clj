(ns tictacclojure.text-prompts
  (:require [tictacclojure.board :as board]))

(def newline "\n")

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
    (conj (vec (map render-cell row)) "\n")))

(defn render-board
  [board]
  (let [rows (board/rows board)]
    (reduce render-row "" rows)))
