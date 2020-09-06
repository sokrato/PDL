(ns elib.core
  (:require
    [clojure.string :as str])
  (:gen-class))

(defn -main [& args]
  (doseq [arg args]
    (println (str/upper-case arg))))
