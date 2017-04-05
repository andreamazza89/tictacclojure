(ns tictacclojure.text-prompts-test
  (:require [clojure.test :refer :all]
            [tictacclojure.board :as board]
            [tictacclojure.helpers :as helper]
            [tictacclojure.text-prompts :as text]))

(deftest text-prompts
  (testing "Board rendering"
    (let [empty-board (helper/create-board
            :_ :_ :_
            :_ :_ :_
            :_ :_ :_)
          parsed-empty-board (str
            "|0||1||2|\n"
            "|3||4||5|\n"
            "|6||7||8|\n")
          board-with-moves (helper/create-board
            :x :_ :_
            :_ :_ :_
            :_ :_ :o)
          parsed-board-with-moves (str
            "|x||1||2|\n"
            "|3||4||5|\n"
            "|6||7||o|\n")
          full-board (helper/create-board
            :x :o :x
            :o :x :o
            :x :o :x)
          parsed-full-board (str
            "|x||o||x|\n"
            "|o||x||o|\n"
            "|x||o||x|\n")]

      (is (= parsed-empty-board (text/render-board empty-board))
          "Renders an empty board into its string representation")
      (is (= parsed-board-with-moves (text/render-board board-with-moves))
          "Renders a board with moves into its string representation"))))


