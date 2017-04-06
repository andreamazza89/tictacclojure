(ns tictacclojure.player-test
  (:require [clojure.test :refer :all]
            [tictacclojure.player :as player]))

(deftest player
  (testing "Player's mark"
    (is (= :x (player/get-mark [:x :human]))
      "Knows a player's mark (example one)")
    (is (= :o (player/get-mark [:o :monster]))
      "Knows a player's mark (example two)")))
