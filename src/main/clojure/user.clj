(ns user
  (:require
    [toolkit.nrepl.core :as tnc])
  (:import
    [com.xux.elib.service PostService StorageService])
  (:gen-class))

(defn- init
  "init bindings"
  ([]
   (def spring (tnc/ctx-get "context"))
   (init spring))
  ([spring]
   (def storeService (.getBean spring StorageService))
   (def postService (.getBean spring PostService))))


