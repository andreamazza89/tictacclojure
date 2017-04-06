(ns tictacclojure.console-runner-test
  (:require [clojure.test :refer :all]
            [tictacclojure.game :as game]
            [tictacclojure.board :as board]
            [tictacclojure.text-prompts :as text]
            [tictacclojure.helpers :as helper]
            [tictacclojure.console-runner :as runner]))

(deftest console-runner
  (testing "human-v-human"
    (is (= (str
             "Welcome to tictacclojure\n"
             "It's x's turn, please pick a move\n"
             (text/render-board (helper/create-board
                                  :_ :_ :_
                                  :_ :_ :_
                                  :_ :_ :_))
             "\n"
             "It's o's turn, please pick a move\n"
             (text/render-board (helper/create-board
                                  :x :_ :_
                                  :_ :_ :_
                                  :_ :_ :_))
             "\n"
             "It's x's turn, please pick a move\n"
             (text/render-board (helper/create-board
                                  :x :_ :_
                                  :o :_ :_
                                  :_ :_ :_))
             "\n"
             "It's o's turn, please pick a move\n"
             (text/render-board (helper/create-board
                                  :x :x :_
                                  :o :_ :_
                                  :_ :_ :_))
             "\n"
             "It's x's turn, please pick a move\n"
             (text/render-board (helper/create-board
                                  :x :x :_
                                  :o :o :_
                                  :_ :_ :_))
             "\n"
             "The winner was: x\n"
             (text/render-board (helper/create-board
                                  :x :x :x
                                  :o :o :_
                                  :_ :_ :_))
             "\n")
           (with-out-str
             (with-in-str "0\n3\n1\n4\n2\n"
               (runner/play-game (game/create-game (board/new-board) [:x :human] [:o :human]))))))

    (let [draw-board (helper/create-board
                                  :o :x :o
                                  :o :x :x
                                  :x :o :x)]
      (is (= (str
             "Welcome to tictacclojure\n"
             "It was a draw!\n"
             (text/render-board draw-board)
             "\n")
           (with-out-str
             (runner/play-game (game/create-game draw-board [:x :human] [:o :human]))))))))
