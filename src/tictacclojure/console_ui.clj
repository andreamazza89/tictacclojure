(ns tictacclojure.console-ui)

(defn print-out
  [message]
  (println message))

(defn- always-validate
  [user-input]
  true)

(defn get-input
  ([]
    (get-input "" always-validate ""))

  ([prompt]
    (get-input prompt always-validate ""))

  ([validate error-message]
   (get-input "" validate error-message))

  ([prompt validate error-message]
    (if (not-empty prompt) (print-out prompt))
    (let [user-input (read-line)]
      (if (validate user-input)
        user-input
        (do
          (print-out error-message)
          (recur prompt validate error-message))))))
