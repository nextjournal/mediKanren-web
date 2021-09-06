(require '[babashka.fs :as fs])

(doseq [dir ["build/js" "build/css" "build/images"]]
  (fs/create-dirs dir))

;;HTML
(fs/copy "public/index.html" "build/index.html")

;;JS
(fs/copy "public/js/medikanren.js" "build/js/medikanren.js")

;;CSS
(fs/copy "public/css/app.css" "build/css/app.css")

;;Images
;;Data
