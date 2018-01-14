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

(defn join-link []
  (html
    [:a.btn.btn-primary.btn-block
     {:href "/join" :style "margin-top:10px;"}
     "Signup"]))

(defn login-form []
  (html5
    [:form.form-horizontal
     {:method "POST" :action "/login"}
     [:div.form-group
      [:label.control-label.col-sm-2 {:for :name-input}
       "Username"]
      [:div.col-sm-10
       [:input#name-input.form-control
        {:name :username
         :placeholder "username"}]]]
     [:div.form-group
      [:label.control-label.col-sm-2 {:for :pass-input}
       "Password"]
      [:div.col-sm-10
       [:input#pass-input.form-control
        {:name :password
         :type :password
         :placeholder "password"}]]]
     [:div.form-group
      [:div.col-sm-offset-2.col-sm-10
       [:input.btn.btn-success.btn-block
        {:type :submit
         :value "Log In"}]]
      [:div.col-sm-10 (join-link)]]]))

(defn signup-form []
  (html5
    [:form.form-horizontal
     {:method "POST" :action "/signup"}
     [:div.form-group
      [:label.control-label.col-sm-2 {:for :name-input}
       "Username"]
      [:div.col-sm-10
       [:input#name-input.form-control
        {:name :username
         :placeholder "username"}]]]
     [:div.form-group
      [:label.control-label.col-sm-2 {:for :pass-input}
       "Password"]
      [:div.col-sm-10
       [:input#pass-input.form-control
        {:name :password
         :type :password
         :placeholder "password"}]]]
     [:div.form-group
      [:div.col-sm-offset-2.col-sm-10
       [:input.btn.btn-outline-primary.btn-block
        {:type :submit
         :value "Signup"}]]]]))

(defn logout-form []
  (html
    [:div
     [:a.btn.btn-dark
      {:href "/logout" :style "margin-top:10px;"}
      "Logout"]]))

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
      (if (= checked true)
        [:input.btn.btn-success.btn-xs
         {:type :submit
          :value (str checked)}]
        [:input.btn.btn-xs
         {:type :submit
          :value (str checked)}])]]))

(defn login-page []
  (html5 {:lang :en}
         [:head
          [:title "todo-login"]
          [:meta {:name :viewport
                  :content "width=device-width, initial-scale=1.0"}]
          [:link {:href "/css/bootstrap.min.css"
                  :rel :stylesheet}]]
         [:body
          [:div.col-sm-6
           [:h2 "Welcome"]
           (login-form)]]))

(defn signup-page []
  (html5 {:lang :en}
         [:head
          [:title "todo-signup"]
          [:meta {:name :viewport
                  :content "width=device-width, initial-scale=1.0"}]
          [:link {:href "/css/bootstrap.min.css"
                  :rel :stylesheet}]]
         [:body
          [:div.col-sm-6
           [:h2 "Signup"]
           (signup-form)]]))

(defn items-page [user-name items]
  (html5 {:lang :en}
         [:head
          [:title "todo-app"]
          [:meta {:name :viewport
                  :content "width=device-width, initial-scale=1.0"}]
          [:link {:href "/css/bootstrap.min.css"
                  :rel :stylesheet}]]
         [:body
          [:div.container
           [:div {:style "text-align:right;"} (logout-form)]
           [:div [:h2 (str "Hello " user-name)]]]
          [:div.container
           [:h1 {:style "text-align:center;"} "Todo-List"]
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
              [:h5 "There are no items."])]
           [:div.col-sm-6
            [:h2 "Create a New Item"]
            (new-item)]]
          [:script {:src "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min/js"}]
          [:script {:src "/js/bootstrap.min.js"}]]))
