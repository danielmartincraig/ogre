(ns clojurewerkz.ogre.transform.map-test
  (:use [clojure.test])
  (:require [clojurewerkz.ogre.core :as q]
            [clojurewerkz.ogre.vertex :as v]
            [clojurewerkz.ogre.graph :as g]
            [clojurewerkz.ogre.test-util :as u]))

(deftest test-map-step
  (testing "g(v1).map"
    (let [g (u/classic-tinkergraph)
          m (q/query (v/find-by-id g 1)
                     q/map
                     q/first-into-map!)]
      (is (= "marko" (:name m)))
      (is (= 29 (:age m)))
      (is (= 2 (count m)))))
  (testing "g(v1).map('name' 'id')"
    (let [g (u/classic-tinkergraph)
          m (q/query (v/find-by-id g 1)
                     (q/map :name :id)
                     q/first-into-map!)]
      (is (= "marko" (:name m)))
      (is (= nil (:age m)))
      (is (= 2 (count m)))))
  (testing "g(v1).out('knows').map()"
    (let [g (u/classic-tinkergraph)
          ms (q/query (v/find-by-id g 1)
                      (q/--> [:knows])
                      q/map
                      q/all-into-maps!)
          vadas (first (filter #(= "vadas" (:name %)) ms))
          josh  (first (filter #(= "josh" (:name %)) ms))]
      (is (= 27 (:age vadas)))
      (is (= 32 (:age josh)))
      (is (= 2 (count ms))))))
