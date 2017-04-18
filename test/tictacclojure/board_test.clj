(ns tictacclojure.board-test
  (:require [clojure.test :refer :all]
            [tictacclojure.board :as board]
            [tictacclojure.helpers :as helper]))

(deftest board-test
  (testing "Board creation"
    (is (= 9 (count (board/new-board)))
        "Defaults to a 3x3 board")
    (is (= 16 (count (board/new-board 4)))
        "Creates a 4x4 board"))

  (testing "Full board"
    (let [new-board (board/new-board)
          board-with-move (helper/create-board
            :x :_ :_
            :_ :_ :_
            :_ :_ :_)
          full-board (helper/create-board
            :x :o :x
            :o :x :o
            :x :o :x)
          full-board-4x4(helper/create-board
            :x :o :x :o
            :o :x :o :x
            :x :o :x :x
            :x :o :o :o)]
      (is (= false (board/full? new-board))
          "A new board is not full")
      (is (= false (board/full? board-with-move))
          "A board with a move on it is not full")
      (is (= true (board/full? full-board))
          "A full board is full (3x3)")
      (is (= true (board/full? full-board-4x4))
          "A full board is full (4x4)")))

  (testing "Empty board"
    (let [empty-board (board/new-board)
          board-with-move (helper/create-board
            :x :_ :_
            :_ :_ :_
            :_ :_ :_)]
      (is (= true (board/is-empty? empty-board))
          "An empty board is empty")
      (is (= false (board/is-empty? board-with-move))
          "A board with a move on it is not empty")))

  (testing "Moves available"
    (let [board-one (helper/create-board
           :x :_ :_
           :_ :_ :_
           :_ :_ :_)
          board-two (helper/create-board
           :_ :x :_
           :_ :_ :_
           :_ :_ :o)
          board-three-4x4 (helper/create-board
           :_ :x :_ :_
           :_ :_ :_ :_
           :_ :_ :o :x
           :_ :o :_ :_)]
      (is (= [1 2 3 4 5 6 7 8]
             (board/moves-available board-one))
          "Only available moves are reported (example one)")
      (is (= [0 2 3 4 5 6 7]
             (board/moves-available board-two))
          "Only available moves are reported (example two)")
      (is (= [0 2 3 4 5 6 7 8 9 12 14 15]
             (board/moves-available board-three-4x4))
          "Only available moves are reported (example three 4x4)")))

  (testing "Making a move"
    (let [board-with-x-move (board/add-move (board/new-board) [0 :x])
          board-with-o-move (board/add-move (board/new-board) [1 :o])
          board-with-x-move-4x4 (board/add-move (board/new-board 4) [14 :x])]
      (is (= :x (board/get-cell-at board-with-x-move 0))
          "Adds the given mark to the given position (example one)")
      (is (= :o (board/get-cell-at board-with-o-move 1))
          "Adds the given mark to the given position (example two)")
      (is (= :x (board/get-cell-at board-with-x-move-4x4 14))
          "Adds the given mark to the given position (example three-4x4)")))

  (testing "Checking lines contents"
    (let [board-w-horizontal-x-line (helper/create-board
            :_ :_ :_
            :x :x :x
            :_ :_ :_)
          board-w-vertical-o-line (helper/create-board
            :_ :_ :o
            :_ :_ :o
            :_ :_ :o)
          board-w-vertical-x-line-4x4 (helper/create-board
            :_ :_ :x :_
            :_ :_ :x :_
            :_ :_ :x :_
            :_ :_ :x :_)
          board-w-up-diagonal-x-line (helper/create-board
            :_ :_ :x
            :_ :x :_
            :x :_ :_)
          board-w-down-diagonal-o-line (helper/create-board
            :o :_ :_
            :_ :o :_
            :_ :_ :o)
          board-w-down-diagonal-o-line-4x4 (helper/create-board
            :o :_ :_ :_
            :_ :o :_ :_
            :_ :_ :o :_
            :_ :_ :_ :o)]
      (is (= :x (board/find-mark-from-full-line board-w-horizontal-x-line))
          "Finds the mark that is the same in a row (x)")
      (is (= :o (board/find-mark-from-full-line board-w-vertical-o-line))
          "Finds the mark that is the same in a column (o)")
      (is (= :x (board/find-mark-from-full-line board-w-vertical-x-line-4x4))
          "Finds the mark that is the same in a column (x-4x4)")
      (is (= :x (board/find-mark-from-full-line board-w-up-diagonal-x-line))
          "Finds the mark that is the same in an upwards diagonal (x)")
      (is (= :o (board/find-mark-from-full-line board-w-down-diagonal-o-line))
          "Finds the mark that is the same in a downwards diagonal (o)")
      (is (= :o (board/find-mark-from-full-line board-w-down-diagonal-o-line-4x4))
          "Finds the mark that is the same in a downwards diagonal (o-4x4)")
      (is (= nil (board/find-mark-from-full-line (board/new-board)))
          "An empty board has no lines with the same mark"))))
