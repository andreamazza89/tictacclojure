(ns tictacclojure.game-test
  (:require [clojure.test :refer :all]
            [tictacclojure.board :as board]
            [tictacclojure.game :as game]
            [tictacclojure.helpers :as helper]))

(deftest game-test
  (testing "Turn management"
    (let [game-one (game/create-game (board/new-board) :x :o)
          game-two (game/create-game (board/new-board) :o :x)]
      (is (= :x (game/whose-turn? game-one))
          "In a new game the first player provided is the one who goes first (example one)")
      (is (= :o (game/whose-turn? game-two))
          "In a new game the first player provided is the one who goes first (example two)")
      (is (= :o (game/whose-turn? (game/make-move game-one 0)))
          "After a move the current player is updated (example one)")
      (is (= :x (game/whose-turn? (game/make-move game-two 0)))
          "After a move the current player is updated (example two)")))

  (testing "Board transformations"
    (let [game-one (game/create-game (board/new-board) :x :o)
          game-two (game/create-game (board/new-board) :o :x)
          move-one-postition 0
          move-two-postition 5
          board-one-after-move (:board (game/make-move game-one move-one-postition))
          board-two-after-move (:board (game/make-move game-two move-two-postition))]
      (is (= :x (board/get-cell-at board-one-after-move move-one-postition))
          "Adds current players's flag to the board at the position provided (example one)")
      (is (= :o (board/get-cell-at board-two-after-move move-two-postition))
          "Adds current players's flag to the board at the position provided (example two)")))

  (testing "Rules"
    (let [tie-board (helper/create-board
            :o :x :o
            :o :x :x
            :x :o :x)
          board-w-horizontal-x-win (helper/create-board
            :x :x :x
            :_ :o :o
            :_ :_ :_)
          board-w-vertical-o-win (helper/create-board
            :o :x :x
            :o :_ :_
            :o :_ :_)
          new-game (game/create-game (board/new-board) :x :o)
          game-w-tie (game/create-game tie-board :o :x)
          game-w-horizontal-x-win (game/create-game board-w-horizontal-x-win :o :x)
          game-w-vertical-o-win (game/create-game board-w-vertical-o-win :x :o)]
      (is (= false (game/over? new-game))
          "A brand new game is not over")
      (is (= true (game/over? game-w-tie))
          "The game is over when the board is full")
      (is (= true (game/over? game-w-horizontal-x-win))
          "The game is over if someone has won")
      (is (= :x (game/winner game-w-horizontal-x-win))
          "Declares a winner when there is one (x)")
      (is (= :o (game/winner game-w-vertical-o-win))
          "Declares a winner when there is one (o)")
      (is (= nil (game/winner game-w-tie))
          "A tie means there is no winner"))))
