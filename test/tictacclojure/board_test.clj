(ns tictacclojure.board-test
  (:require [clojure.test :refer :all]
            [tictacclojure.board :as board]))

(deftest board-test
  (testing "New board"
    (let [new-board (board/new-board)
          board-with-move (board/add-move new-board [0 :x])]
      (is (= (board/empty? new-board) true) "A new board is empty")
      (is (= (board/empty? board-with-move) false) "A board with a move is not empty")))

   (testing "Moves available"
     (let [board-one [:x 1 2 3 4 5 6 7 8]
           board-two [:x :o 2 3 4 5 6 7 8]]
       (is (= [1 2 3 4 5 6 7 8]
              (board/moves-available board-one)) "Only available moves are reported (example one)")
       (is (= [2 3 4 5 6 7 8]
              (board/moves-available board-two)) "Only available moves are reported (example two)")))

   (testing "Making a move"
     (is (= [:x] (board/add-move [0] [0 :x])) "Adds the given mark to the given position (example one)")
     (is (= [0 :o] (board/add-move [0 1] [1 :o])) "Adds the given mark to the given position (example two)")))
