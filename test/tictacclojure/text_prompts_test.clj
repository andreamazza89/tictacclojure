(ns tictacclojure.text-prompts-test
  (:require [clojure.test :refer :all]
            [tictacclojure.board :as board]
            [tictacclojure.helpers :as helper]
            [tictacclojure.text-prompts :as text]))

(deftest text-prompts
  (testing "Board rendering"
    (let [blu-x "\u001b[34mx\u001b[0m"
          red-o  "\u001b[31mo\u001b[0m"
          empty-board (helper/create-board
            :_ :_ :_
            :_ :_ :_
            :_ :_ :_)
          parsed-empty-board (str
            "| 0 | 1 | 2 |\n"
            "\n"
            "| 3 | 4 | 5 |\n"
            "\n"
            "| 6 | 7 | 8 |\n"
            "\n")
          board-with-moves (helper/create-board
            :x :_ :_
            :_ :_ :_
            :_ :_ :o)
          parsed-board-with-moves (str
            "| " blu-x " | 1 | 2 |\n"
            "\n"
            "| 3 | 4 | 5 |\n"
            "\n"
            "| 6 | 7 | " red-o " |\n"
            "\n")
          full-board (helper/create-board
            :x :o :x
            :o :x :o
            :x :o :x)
          parsed-full-board (str
            "| " blu-x " | " red-o " | " blu-x " |\n"
            "\n"
            "| " red-o " | " blu-x " | " red-o " |\n"
            "\n"
            "| " blu-x " | " red-o " | " blu-x " |\n"
            "\n")
          full-board-4x4(helper/create-board
            :x :o :x :o
            :o :x :o :_
            :x :o :x :_
            :x :o :_ :o)
          parsed-full-board-4x4 (str
            "| " blu-x " | " red-o " | " blu-x " | " red-o " |\n"
            "\n"
            "| " red-o " | " blu-x " | " red-o " | 7 |\n"
            "\n"
            "| " blu-x " | " red-o " | " blu-x " |11 |\n"
            "\n"
            "| " blu-x " | " red-o " |14 | " red-o " |\n"
            "\n")]

      (is (= parsed-empty-board (text/render-board empty-board))
        "Renders an empty board into its string representation")
      (is (= parsed-board-with-moves (text/render-board board-with-moves))
        "Renders a board with moves into its string representation")
      (is (= parsed-full-board (text/render-board full-board))
        "Renders a full board into its string representation")
      (is (= parsed-full-board-4x4 (text/render-board full-board-4x4))
        "Renders a full board (4x4) into its string representation")))

  (testing "Announce winner"
    (is (= "The winner was: winner-one" (text/winner-announcement :winner-one))
      "Includes the string representation of the winner keyword (example one)")
    (is (= "The winner was: winner-two" (text/winner-announcement :winner-two))
      "Includes the string representation of the winner keyword (example two)"))

  (testing "Announce turn"
    (is (= "It's player-one's turn, please pick a move" (text/turn-announcement :player-one))
      "Includes the string representation of the next player's keyword (example one)")
    (is (= "It's player-two's turn, please pick a move" (text/turn-announcement :player-two))
      "Includes the string representation of the next player's keyword (example two)")))
