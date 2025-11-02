(ns repro.extension
  (:require ["vscode" :as vscode]
            [promesa.core :as p]))

(defn say-clipboard-content! []
  (p/let [; text (.then (vscode/env.clipboard.readText) identity) ; works fine
          text (vscode/env.clipboard.readText) ; crashes
          ]
    (vscode/window.showInformationMessage (str "clipboard contains: " text))))

(defn ^:export activate! [^js context]
  (println "activate!" context)
  (when context
    (.push (.-subscriptions context)
           (vscode/commands.registerCommand "repro.hello" #'say-clipboard-content!))))

(defn ^:after-load after []
  (println "shadow-cljs reloaded")
  (activate! nil))