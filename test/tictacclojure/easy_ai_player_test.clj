(ns tictacclojure.easy-ai-player-test
  (:require [clojure.test :refer :all]
            [tictacclojure.board :as board]
            [tictacclojure.game :as game]
            [tictacclojure.helpers :as helper]
            [tictacclojure.player :as player]))

(deftest easy-ai-player
  (testing "Picking a move"
    (let [new-game (game/create-game (board/new-board) [:o :easy-ai] [:x :easy-ai])
          game-with-moves (game/create-game (helper/create-board
            :x :o :_
            :x :_ :_
            :_ :_ :_) [:o :easy-ai] [:x :easy-ai])]
      (is (= [0 :x] (player/pick-move [:x :easy-ai] new-game))
      "Picks the first move available (example one)")
      (is (= [2 :o] (player/pick-move [:o :easy-ai] game-with-moves))
      "Picks the first move available (example two)"))))
