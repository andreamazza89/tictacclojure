(ns tictacclojure.human-console-player-test
  (:require [clojure.test :refer :all]
            [tictacclojure.board :as board]
            [tictacclojure.player :as player]))

(deftest human-player
  (testing "Picking a move"
    (is (= 0 (with-in-str "0\n" (player/pick-move [:x :human] (board/new-board))))
      "Fetches input and converts it to a board position (example one)")
    (is (= 5 (with-in-str "5\n" (player/pick-move [:x :human] (board/new-board))))
      "Fetches input and converts it to a board position (example two)")))
