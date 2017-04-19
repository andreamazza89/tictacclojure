(ns tictacclojure.text-game-options-test
  (:require [clojure.test :refer :all]
            [tictacclojure.game :as game]
            [tictacclojure.board :as board]
            [tictacclojure.text-prompts :as prompts]
            [tictacclojure.text-game-options :as options]))

(deftest text-game-options
  (testing "Game selection"
    (is (= (str "Please select a board size:\n3->3x3\n4->4x4\n"
                prompts/clear-screen
                "Please select a game mode:\n1. Human V Human\n2. Human V Easy-Ai\n3. Easy-Ai V Easy-Ai\n4. Human V MiniMax\n"
                prompts/clear-screen
                "Would you like to swap who goes first? (y/n)\n")
           (with-out-str
             (with-in-str "3\n1\ny\n"
               (options/create-game))))
      "Prompts the user for game options")
    (is (= (str "Please select a board size:\n3->3x3\n4->4x4\n"
                "Invalid board size: please try again.\n"
                "Please select a board size:\n3->3x3\n4->4x4\n"
                prompts/clear-screen
                "Please select a game mode:\n1. Human V Human\n2. Human V Easy-Ai\n3. Easy-Ai V Easy-Ai\n4. Human V MiniMax\n"
                prompts/clear-screen
                "Would you like to swap who goes first? (y/n)\n")
           (with-out-str
             (with-in-str "42\n3\n1\ny\n"
               (options/create-game))))
      "Prompts the user for game options using board validation")
    (is (= (str "Please select a board size:\n3->3x3\n4->4x4\n"
                prompts/clear-screen
                "Please select a game mode:\n1. Human V Human\n2. Human V Easy-Ai\n3. Easy-Ai V Easy-Ai\n4. Human V MiniMax\n"
                "Invalid game mode: please try again.\n"
                "Please select a game mode:\n1. Human V Human\n2. Human V Easy-Ai\n3. Easy-Ai V Easy-Ai\n4. Human V MiniMax\n"
                prompts/clear-screen
                "Would you like to swap who goes first? (y/n)\n")
           (with-out-str
             (with-in-str "3\nlol\n1\ny\n"
               (options/create-game))))
      "Prompts the user for game options using game type validation")
    (is (= (str "Please select a board size:\n3->3x3\n4->4x4\n"
                prompts/clear-screen
                "Please select a game mode:\n1. Human V Human\n2. Human V Easy-Ai\n3. Easy-Ai V Easy-Ai\n4. Human V MiniMax\n"
                prompts/clear-screen
                "Would you like to swap who goes first? (y/n)\n"
                "Invalid answer, please try again.\n"
                "Would you like to swap who goes first? (y/n)\n")
           (with-out-str
             (with-in-str "3\n1\nsure\ny\n"
               (options/create-game))))
      "Prompts the user for game options using game type validation")
    (with-out-str
      (is (= (game/create-game (board/new-board 4) [:o :easy-ai] [:x :human])
           (with-in-str "4\n2\ny\n"
             (options/create-game)))
      "Generates a game based on the user's input (4x4, humanVhuman, swap)"))))
