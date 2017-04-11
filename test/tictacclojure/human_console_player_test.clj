(ns tictacclojure.human-console-player-test
  (:require [clojure.test :refer :all]
            [tictacclojure.game :as game]
            [tictacclojure.board :as board]
            [tictacclojure.player :as player]))

(deftest human-player
  (testing "Picking a move"
    (let [new-game (game/create-game
                     (board/new-board)
                     [:o :easy-ai] [:x :easy-ai])]
      (is (= [0 :x] (with-in-str "0\n" (player/pick-move [:x :human] new-game)))
        "Fetches input and converts it to a board position (example one)")
      (is (= [5 :o] (with-in-str "5\n" (player/pick-move [:o :human] new-game)))
        "Fetches input and converts it to a board position (example two)"))))
