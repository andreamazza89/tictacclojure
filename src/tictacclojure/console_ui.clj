(ns tictacclojure.console-ui
  (:require [tictacclojure.text-prompts :as prompts]))

(defn print-out
  [message]
  (println message))

(defn clear-screen
  []
  (print prompts/clear-screen))

(defn- always-validate
  [user-input]
  true)

(defn get-input
  ([]
    (get-input "" always-validate ""))

  ([prompt]
    (get-input prompt always-validate ""))

  ([is-valid? error-message]
   (get-input "" is-valid? error-message))

  ([prompt is-valid? error-message]
    (if (not-empty prompt) (print-out prompt))
    (let [user-input (read-line)]
      (if (is-valid? user-input)
        user-input
        (do
          (print-out error-message)
          (recur prompt is-valid? error-message))))))
