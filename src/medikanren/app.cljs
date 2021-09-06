(ns medikanren.app
  (:require [nextjournal.devcards :as dc]
            [nextjournal.devcards.routes :as router]
            [nextjournal.devcards.main :as devcards-main]
            [nextjournal.devcards-ui :as devcards-ui]
            [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.history :as rfh]
            [reitit.frontend.easy :as rfe]))

(defn ^:export ^:dev/after-load init []
  (rfe/start! router/router #(reset! router/match %1) {:use-fragment @router/use-fragment?})
  (r/render [router/devcards] (js/document.getElementById "app")))

(dc/defcard main
 [:div.bg-green-500 "Ohai!"])