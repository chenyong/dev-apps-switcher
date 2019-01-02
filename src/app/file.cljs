
(ns app.file
  (:require [clojure.string :as string] [app.schema :as schema] ["fs" :as fs])
  (:require-macros [clojure.core.strint :refer [<<]]))

(defn write-ts-file! [enabled-apps]
  (println "files to write:" enabled-apps)
  (let [import-lines (->> enabled-apps
                          (sort (fn [app] (.indexOf schema/all-apps app)))
                          (map (fn [app] (get schema/app-imports app)))
                          (string/join "\n"))
        import-names (->> enabled-apps
                          (sort (fn [app] (.indexOf schema/all-apps app)))
                          (map (fn [app] (str app "Config,")))
                          (string/join "\n"))]
    (fs/writeFileSync
     "dev-apps.ts"
     (<<
      "~{import-lines}\n\nconst apps = [\n~{import-names}\n];\n\nexport function getAppsConfig() {\n  return apps;\n}\n"))))