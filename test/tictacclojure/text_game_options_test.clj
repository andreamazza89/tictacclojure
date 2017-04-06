(ns tictacclojure.text-game-options-test
  (:require [clojure.test :refer :all]
            [tictacclojure.game :as game]
            [tictacclojure.board :as board]
            [tictacclojure.text-game-options :as options]))

(deftest text-game-options
  (testing "Game selection"
    (is (= (str "Please select a game mode:\n1. Human V Human\n2. Human V Easy-Ai\n3. Easy-Ai V Easy-Ai\n"
                "Would you like to swap who goes first? (y/n)\n")
           (with-out-str
             (with-in-str "1\ny\n"
               (options/create-game))))
      "Prompts the user for game options")
    (with-out-str
      (is (= (game/create-game (board/new-board) [:o :easy-ai] [:x :human])
           (with-in-str "2\ny\n"
             (options/create-game)))
      "Prompts the user for game options"))))
