(ns zap.core
  (:use compojure.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [clojure.data.json :as json]
            [zap.views :as views]
            [zap.models :as models]))

(defroutes api-routes
  (GET "/projects" []
    (json/write-str (models/all-projects)))
  (GET "/issues" []
    (json/write-str (models/all-issues)))
  (GET "/issue/:id" [id]
    (json/write-str (models/issue-by-id id)))
  (GET "/project/:id" [id]
    (if-let [proj (models/project-by-id id)]
      (json/write-str proj)
      {:status 404 :body ""}))
  (GET "/project/:pid/issues" [pid]
    (json/write-str (models/issues-by-project pid)))
  (GET "/project/:pid/issue/:iid" [pid iid]
    (json/write-str (models/issue-by-id iid)))

  (POST "/projects" [& params]
    (views/create-project params))
  (DELETE "/project/:id" [id]
    (views/delete-project id))
  (PUT "/project/:id" [id & params]
    (views/edit-project id params)))

(defroutes app-routes
  (GET "/" []
       (views/index))
  (GET "/projects" []
       (views/projects))
  (GET "/projects/new" []
       (views/new-project))
 (GET "/issues" []
       (views/issues))
 (GET "/issue/:id" [id]
       (views/issue id))
  (POST "/projects" [& params]
        (views/make-project params))
  (POST "/issue/:id/comments" [id & params]
        (views/make-comment id params))
  (POST "/issue/:id/close" [id & params]
        (views/close-issue id params))
        
  (GET "/project/:id/issues" [id]
       (views/issues-by-project id))
  (GET "/project/:id/issue/new" [id]
       (views/new-issue id))
  (POST "/project/:id/issues" [id & params]
        (views/make-issue id params)))

(defroutes all-routes
  (context "" [] app-routes)
  (context "/api" [] api-routes))

(def app
  (-> all-routes
      (wrap-resource "public")
      wrap-keyword-params
      wrap-params))
