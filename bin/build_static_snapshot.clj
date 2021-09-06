(require '[babashka.fs :as fs])

(doseq [dir ["build/js" "build/css" "build/images"]]
  (fs/create-dirs dir))

;;HTML
(fs/copy "public/index.html" "build/index.html")

;;JS
(fs/copy "public/js/medikanren.js" "build/js/medikanren.js")

;;CSS
(io/copy (io/file (io/resource "public/css/viewer.css"))
         (io/file "build/css/viewer.css"))
(fs/copy "public/css/app.css" "build/css/app.css")

;;Images
(fs/copy-tree "public/images" "build/images")

;;Data
