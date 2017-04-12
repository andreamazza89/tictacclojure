(ns tictacclojure.minimax-player-test
  (:require [clojure.test :refer :all]
            [tictacclojure.board :as board]
            [tictacclojure.helpers :as helper]
            [tictacclojure.game :as game]
            [tictacclojure.player :as player]))

(deftest minmax-player
  (testing "Minimax"
    (let [game-w-empty-board (game/create-game (helper/create-board
            :_ :_ :_
            :_ :_ :_
            :_ :_ :_) [:x :minimax] [:o :minimax])
          game-w-one-move-in-board (game/create-game (helper/create-board
            :o :o :_ :x
            :_ :_ :_ :_
            :_ :o :_ :_
            :o :x :_ :x) [:x :minimax] [:o :minimax])
          game-w-almost-full-board (game/create-game (helper/create-board
            :o :x :o
            :o :x :x
            :x :_ :x) [:o :minimax] [:x :minimax])
          game-w-winnable-board-one (game/create-game (helper/create-board
            :o :o :_
            :x :x :_
            :x :o :o) [:x :minimax] [:o :minimax])
          game-w-winnable-board-two (game/create-game (helper/create-board
            :x :_ :x
            :o :o :_
            :_ :_ :_) [:x :minimax] [:o :minimax])
          game-w-blockable-board (game/create-game (helper/create-board
            :_ :_ :x
            :_ :_ :o
            :x :_ :o) [:o :minimax] [:x :minimax])
          game-w-forkable-board (game/create-game (helper/create-board
            :x :_ :_
            :_ :o :_
            :_ :_ :o) [:x :minimax] [:o :minimax])]

      (time (println (player/pick-move [:x :minimax] game-w-one-move-in-board)))

      (is (= [0 :x] (player/pick-move [:x :minimax] game-w-empty-board))
          "Chooses the topleft corner at the start of the game")
      (is (= [7 :o] (player/pick-move [:o :minimax] game-w-almost-full-board))
          "If there is only one move available, it makes that move")
      (is (= [7 :o] (player/pick-move [:o :minimax] game-w-almost-full-board))
          "If there is only one move available, it makes that move")
      (is (= [5 :x] (player/pick-move [:x :minimax] game-w-winnable-board-one))
          "It chooses the winning move if there is one (example one)")
      (is (= [1 :x] (player/pick-move [:x :minimax] game-w-winnable-board-two))
          "It chooses the winning move if there is one (example two)")
      (is (= [4 :o] (player/pick-move [:o :minimax] game-w-blockable-board))
          "It stops the opponent from winning")
      (is (= [6 :x] (player/pick-move [:x :minimax] game-w-forkable-board))
         "It stops the opponent from creating a fork"))))
