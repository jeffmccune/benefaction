(ns puppetlabs.benefaction-core-test
  (:require [clojure.test :refer :all]
            [puppetlabs.benefaction-core :refer :all]))

(deftest hello-test
  (testing "says hello to caller"
    (is (= "Hello, foo!" (hello "foo")))))
