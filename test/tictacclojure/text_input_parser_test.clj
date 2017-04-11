(ns tictacclojure.text-input-parser-test
  (:require [tictacclojure.game :as game]
            [clojure.test :refer :all]
            [tictacclojure.player :as player]
            [tictacclojure.board :as board]
            [tictacclojure.text-input-parser :as parser]))

(deftest text-input-parser
  (testing "Board size"
    (is (=
         (board/new-board 3)
         (parser/parse-board "3"))
        "Parses a 3x3 board")
    (is (=
         (board/new-board 4)
         (parser/parse-board "4"))
        "Parses a 4x4 board"))

  (testing "Game mode"
    (is (=
          (game/create-game (board/new-board 4) [:x :human] [:o :human])
          (parser/parse-game "1" (board/new-board 4)))
      "Parses user selection into a Human v Human game")
    (is (=
          (game/create-game (board/new-board 3) [:x :human] [:o :easy-ai])
          (parser/parse-game "2" (board/new-board 3)))
      "Parses user selection into a Human v easy-ai game")
    (is (=
          (game/create-game (board/new-board 4) [:x :easy-ai] [:o :easy-ai])
          (parser/parse-game "3" (board/new-board 4)))
      "Parses user selection into an easy-ai v easy-ai game")
    (is (=
          (game/create-game (board/new-board 3) [:x :human] [:o :minimax])
          (parser/parse-game "4" (board/new-board 3)))
      "Parses user selection into a human v minimax game"))

  (testing "Swap order"
    (let [game-one (game/create-game (board/new-board) [:x :human] [:o :human])
          swapped-game-one (parser/swap-players "y" game-one)
          swapped-game-two (parser/swap-players "n" game-one)]
      (is (= :o
             (player/get-mark (game/whose-turn? swapped-game-one)))
          "Swaps the players if the user wants to")
      (is (= :x
             (player/get-mark (game/whose-turn? swapped-game-two)))
          "Does not swap the players if the user does not want to"))))
