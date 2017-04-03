(ns tictacclojure.board-test
  (:require [clojure.test :refer :all]
            [tictacclojure.board :as board]))

(deftest board-test
  (testing "New board"
    (is (= (board/smoke) true) "shmoke")))
