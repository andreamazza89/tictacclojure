(ns tictacclojure.easy-ai-test
  (:require [clojure.test :refer :all]
            [tictacclojure.board :as board]
            [tictacclojure.helpers :as helper]
            [tictacclojure.player :as player]))

(deftest easy-ai-player
  (testing "Picking a move"
    (let [board-with-moves (helper/create-board
            :x :o :_
            :x :_ :_
            :_ :_ :_)]
      (is (= 0 (player/pick-move [:x :easy-ai] (board/new-board)))
      "Picks the first move available (example one)")
      (is (= 2 (player/pick-move [:o :easy-ai] board-with-moves))
      "Picks the first move available (example two)"))))
