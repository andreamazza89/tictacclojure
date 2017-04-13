(ns tictacclojure.console-ui-test
  (:require [clojure.test :refer :all]
            [tictacclojure.console-ui :as ui]))

(deftest console-ui
  (testing "Printing to console"
    (is (= "test message\n"
           (with-out-str (ui/print-out "test message")))
      "Prints the given message to the console with a newline"))

  (testing "Getting user input"
    (is (= "test input"
           (with-in-str "test input\n"
             (ui/get-input)))
      "Gets input from the console")
    (is (= ""
           (with-out-str
             (with-in-str "test input\n"
               (ui/get-input))))
      "Prints nothing when no prompt message is given")
    (is (= "test prompt\n"
           (with-out-str
             (with-in-str "test input\n"
               (ui/get-input "test prompt"))))
        "Prompts the user with the message provided, when present")
    (is (= "test prompt\nWrong input\ntest prompt\n"
           (with-out-str
             (with-in-str "bad input\ncorrect input\n"
               (ui/get-input "test prompt" #(= "correct input" %) "Wrong input"))))
        "Shows the error message and prompts the user again if the input is invalid")
    (is (= "test prompt\n"
           (with-out-str
             (with-in-str "correcto input\n"
               (ui/get-input "test prompt" #(= "correcto input" %) "Wrong input"))))
        "Does not show the error message if the input is valid")
    (is (with-out-str
           (= "the right kinda input"
             (with-in-str "nah\nno, I don't like your validation\nthe right kinda input\n"
               (ui/get-input "test prompt" #(= "the right kinda input" %) "Wrong input"))))
        "Keeps getting the input until it is valid")))
