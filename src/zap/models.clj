(ns zap.models
  (:refer-clojure :exclude [comment update])
  (:require [korma.core :refer :all]
            [korma.db :refer :all]
            [clojure.string :as string]))

(defdb zap
  (sqlite3 {:db "zap.db"}))

(defentity project
  (entity-fields :id :name))

(declare comment)
(defentity issue
    (entity-fields :id :project_id :title :description :user :status)
    (has-many comment))

(defentity status
  (entity-fields :id :name))

  ; future database field
(defentity tag
  (entity-fields :id :issue_id :tag))

(defentity comment
  (entity-fields :id :issue_id :content)
  (belongs-to issue))

(defn all-projects []
  (select project))

(defn create-project [proj]
  (insert project (values proj)))

(defn project-by-id [id]
  (first (select project (where {:id id}))))

(defn all-issues []
  (select issue))

(defn- issue-query []
  (-> (select* issue)
      (fields :issue.id ;[:issue.id :id]
                  :project_id
                  :title
                  :description
                  :user
                  [:status.id :status_id]
                  [:status.name :status_name])
      (join status (= :issue.status :status.id))))

(defn issues-by-project [id]
  (->  (issue-query)
         (where {:issue.project_id id})
         exec))

(defn issue-by-id [id]
  (-> (issue-query)
      (where {:issue.id id})
      exec
      first))

(defn comments-by-issue [id]
  (select comment
          (where {:issue_id id})
          (order :id)))

(defn status-by-name [s]
  (first(select status (where {:name s}))))
  
(defn delete-project [id]
  (delete project (where (:id id))))

(defn update-project [id params]
  (update project
          (set-fields params)
          (where {:id id})))

(defn create-issue [params]
  (insert issue (values (select-keys params [:project_id :title :user :description :status]))))

(defn create-comment [params]
  (insert comment (values (select-keys params [:issue_id :content]))))

(defn close-issue [id sid]
  (update issue
          (set-fields {:status sid})
          (where {:id id})))

;

(defn find-issues [q]
  (let [q (str "%" (string/lower-case q) "%")]
    (-> (issue-query)
        (where (or (like (sqlfn lower :issue.title) q)
                          (like (sqlfn lower :issue.description) q)))
        exec)))