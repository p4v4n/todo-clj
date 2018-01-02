(ns todo-clj.items.view
  (:require [hiccup.page :refer [html5]]
            [hiccup.core :refer [html h]]))

(defn new-item []
  (html5
    [:form.form-horizontal
     {:method "POST" :action "/items"}
     [:div.form-group
      [:label.control-label.col-sm-2 {:for :name-input}
       "Name"]
      [:div.col-sm-10
       [:input#name-input.form-control
        {:name :name
         :placeholder "Name"}]]]
     [:div.form-group
      [:label.control-label.col-sm-2 {:for :desc-input}
       "Description"]
      [:div.col-sm-10
       [:input#desc-input.form-control
        {:name :description
         :placeholder "Description"}]]]
     [:div.form-group
      [:div.col-sm-offset-2.col-sm-10
       [:input.btn.btn-primary
        {:type :submit
         :value "Create Item"}]]]]))

(defn delete-item-form [id]
  (html
    [:form
     {:method "POST" :action (str "/items/" id)}
     [:input {:type :hidden
              :name "_method"
              :value "DELETE"}]
     [:div.btn-group
      [:input.btn.btn-danger.btn-xs
       {:type :submit
        :value "Delete"}]]]))

(defn update-item-form [id checked]
  (html
    [:form
     {:method "POST" :action (str "/update/" id)}
     [:div.btn-group
      [:input.btn.btn-xs
       {:type :submit
        :value (str checked)}]]]))

(defn items-page [items]
  (html5 {:lang :en}
         [:head
          [:title "todo-app"]
          [:meta {:name :viewport
                  :content "width=device-width, initial-scale=1.0"}]
          [:link {:href "/css/bootstrap.min.css"
                  :rel :stylesheet}]]
         [:body
          [:div.container
           [:h1 "My Items"]
           [:div.row
            (if (seq items)
              [:table.table.table-stripped
               [:thead
                [:tr
                 [:th.col-sm-14]
                 [:th "Name"]
                 [:th "Description"]
                 [:th "Completed"]]]
               [:tbody
                (for [i items]
                  [:tr
                   [:td (delete-item-form (:id i))]
                   [:td (h (:name i))]
                   [:td (h (:description i))]
                   [:td (update-item-form (:id i) (:checked i))]])]]
              [:div.col-sm-offset-1 "There are no items."])]
           [:div.col-sm-6
            [:h2 "Create a New Item"]
            (new-item)]]
          [:script {:src "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min/js"}]
          [:script {:src "/js/bootstrap.min.js"}]]))
