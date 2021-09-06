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

(defn concept-table [data]
  [:div.overflow-x-auto.rounded.min-h.shadow.border.border-gray-300
   {:style {:min-height 100}}
   [:table.min-w-full.divide-y.divide-gray-200.text-xs
    [:thead.bg-gray-50.text-gray-500
     [:tr.border-b.border-gray-200
      [:th.text-left.px-2 "KG"]
      [:th.text-left.px-2 "CURIE"]
      [:th.text-left.px-2 "Category"]
      [:th.text-left.px-2 "Name"]]]
    (into [:tbody]
          (map (fn [{:keys [kg curie category name]}]
                 [:tr
                  [:td.px-2 kg]
                  [:td.px-2 curie]
                  [:td.px-2 category]
                  [:td.px-2 name]])
               data))]])

(defn x-table [data]
  [:div.overflow-x-auto.rounded.shadow.border.border-gray-300.flex-auto
   {:style {:min-height 100}}
   [:table.min-w-full.divide-y.divide-gray-200.text-xs
    [:thead.bg-gray-50.text-gray-500
     [:tr.border-b.border-gray-200
      [:th.text-left.px-2 "KG"]
      [:th.text-left.px-2 "CURIE"]
      [:th.text-left.px-2 "Category"]
      [:th.text-left.px-2 "Name"]
      [:th.text-left.px-2 "Max PubMed #"]
      [:th.text-left.px-2 "Min PubMed #"]
      [:th.text-left.px-2 "Predicates"]
      [:th.text-left.px-2 "Path Length"]
      [:th.text-left.px-2 "Path Confidence"]]]
    (into [:tbody]
          (map (fn [{:keys [kg curie category name max-pubmed-no min-pubmed-no predicates path-length path-confidence]}]
                 [:tr
                  [:td.px-2 kg]
                  [:td.px-2 curie]
                  [:td.px-2 category]
                  [:td.px-2 name]
                  [:td.px-2 max-pubmed-no]
                  [:td.px-2 min-pubmed-no]
                  [:td.px-2 predicates]
                  [:td.px-2 path-length]
                  [:td.px-2 path-confidence]])
               data))]])

(defn paths-table [data]
  [:div.overflow-x-auto.rounded.shadow.border.border-gray-300.flex-auto
   {:style {:min-height 100}}
   [:table.min-w-full.divide-y.divide-gray-200.text-xs
    [:thead.bg-gray-50.text-gray-500
     [:tr.border-b.border-gray-200
      [:th.text-left.px-2 "KG"]
      [:th.text-left.px-2 "EID"]
      [:th.text-left.px-2 "Subject"]
      [:th.text-left.px-2 "Predicate"]
      [:th.text-left.px-2 "Object"]
      [:th.text-left.px-2 "Subj Cat"]
      [:th.text-left.px-2 "Obj Cat"]
      [:th.text-left.px-2 "PubMed #"]]]
    (into [:tbody]
          (map (fn [{:keys [kg eid subject predicate object subj-cat obj-cat pubmed-no]}]
                 [:tr
                  [:td.px-2 kg]
                  [:td.px-2 eid]
                  [:td.px-2 subject]
                  [:td.px-2 predicate]
                  [:td.px-2 object]
                  [:td.px-2 subj-cat]
                  [:td.px-2 obj-cat]
                  [:td.px-2 pubmed-no]])
               data))]])

(defn property-value-table [data]
  [:div.overflow-x-auto.rounded.shadow.border.border-gray-300.flex-auto
   {:style {:min-width 200 :min-height 100}}
   [:table.divide-y.divide-gray-200.text-xs.min-w-full
    [:thead.bg-gray-50.text-gray-500
     [:tr.border-b.border-gray-200
      [:th.text-left.px-2 "Property"]
      [:th.text-left.px-2 "Value"]]]
    (into [:tbody]
          (map (fn [{:keys [property value]}]
                 [:tr
                  [:td.px-2 property]
                  [:td.px-2 value]])
               data))]])

(defn url-table [data]
  [:div.overflow-x-auto.rounded.shadow.border.border-gray-300.flex-auto
   {:style {:min-width 200 :min-height 100}}
   [:table.divide-y.divide-gray-200.text-xs.min-w-full
    [:thead.bg-gray-50.text-gray-500
     [:tr.border-b.border-gray-200
      [:th.text-left.px-2 "URL"]]]
    (into [:tbody]
          (map (fn [{:keys [url]}]
                 [:tr
                  [:td.px-2 url]])
               data))]])

(defn row-label [text]
  [:label.mr-3.whitespace-nowrap.flex-shrink-0
   {:style {:width 90}}
   text])

(defn col-label [text]
  [:label.block.mb-1
   text])

(dc/defcard explorer
  [:div.text-sm
   [:div.grid.grid-cols-2.gap-8
    [:div
     [:div.flex.items-center
      [row-label "Concept 1"]
      [:input {:type "text"}]]
     [:div.flex.mt-3
      [row-label "Concept 1"]
      [:div.flex-auto
       [concept-table []]]]]
    [:div
     (let [input-id "c1-include-curie-searches"]
       [:div.flex.items-center
        {:style {:height 33}}
        [:input {:type "checkbox" :id input-id}]
        [:label.ml-2 {:for input-id} "Show concept synonyms for CURIE searches"]])
     [:div.flex.mt-3
      [row-label "Predicate 1"]
      [:textarea {:style {:min-height 100}}]]]]
   [:div.flex.items-center.relative.my-6
    {:style {:height 22}}
    [:div.border-t.border-gray-200.w-full]
    [:div.text-gray-500.absolute.bg-white.px-3
     {:style {:top 0 :left "50%" :transform "translateX(-50%)"}}
     "Concept 1 → Predicate 1 → [X] → Predicate 2 → Concept 2"]]
   [:div.grid.grid-cols-2.gap-8
    [:div
     [:div.flex.items-center
      [row-label "Concept 2"]
      [:input {:type "text"}]]
     [:div.flex.mt-3
      [row-label "Predicate 2"]
      [:textarea {:style {:min-height 100}}]]]
    [:div
     (let [input-id "c2-include-curie-searches"]
       [:div.flex.items-center
        {:style {:height 33}}
        [:input {:type "checkbox" :id input-id}]
        [:label.ml-2 {:for input-id} "Show concept synonyms for CURIE searches"]])
     [:div.flex.mt-3
      [row-label "Concept 2"]
      [:div.flex-auto
       [concept-table []]]]]]
   [:div.border-t.border-gray-200.mt-6.pt-6
    [:div.flex.items-center
     [:label.mr-3.whitespace-nowrap.flex-shrink-0.text-right
      {:style {:width 90}}
      "Find in X’s"]
     [:input {:type "text"}]
     [:button.ml-3 "Previous"]
     [:button.ml-3 "Next"]]
    [:div.flex.mt-4
     [:label.mr-3.whitespace-nowrap.flex-shrink-0.text-right
      {:style {:width 90}}
      "X"]
     [x-table []]]
    [:div.flex.mt-4
     [:label.mr-3.whitespace-nowrap.flex-shrink-0.text-right
      {:style {:width 90}}
      "Paths"]
     [paths-table []]]
    [:div.flex.mt-4.ml-3
     {:style {:padding-left 90}}
     [:div.flex-auto
      [col-label "Subject"]
      [property-value-table []]]
     [:div.flex-auto.ml-4
      [col-label "Edge"]
      [property-value-table []]]
     [:div.flex-auto.ml-4
      [col-label "Object"]
      [property-value-table []]]
     [:div.flex-auto.ml-4
      [col-label "Pubmed"]
      [url-table []]]]
    [:div.flex.mt-4.ml-3
     {:style {:padding-left 90}}
     [:div.flex-auto
      [col-label "Publication Date"]
      [:input.w-full {:type "text"}]]
     [:div.flex-auto.ml-4
      [col-label "Subject Score"]
      [:input.w-full {:type "text"}]]
     [:div.flex-auto.ml-4
      [col-label "Object Score"]
      [:input.w-full {:type "text"}]]]]])