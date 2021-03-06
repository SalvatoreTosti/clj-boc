(ns editor.ui.views.grid-test
  (:require [clojure.test :refer :all]
            [editor.ui.views.grid :refer :all]))

(deftest build-image-positions-test
  (testing "testing item position grid generation"
    (are [w x y z] (= z (build-image-positions w x y))
      [0 0] [1 1] nil {}
      [0 0] [1 1] ["a"] {[0 0] "a"}
      [0 0] [1 1] ["a" "b" "c"] {[0 0] "a"}
      [0 0] [2 2] ["a" "b" "c"] {[0 0] "a", [1 0] "b", [0 1] "c"}))
  (testing "testing item position grid generation from view"
    (are [x y z] (= z (build-image-positions x y))
      {:start [0 0], :end [1 1]} ["a"] {[0 0] "a"}
      {:start [0 0], :end [1 1]} ["a", "b"] {[0 0] "a"}
      {:start [0 0], :end [2 2]} ["a", "b"] {[0 0] "a", [1 0] "b"})))

(deftest build-entity-positions-test
  (testing "testing entity position grid generation"
    (are [x y] (= y (build-entity-positions x))
      {:start [0 0] :end [1 1]} {[0 0] :Apple}
      {:start [0 0] :end [2 2]} {[0 0] :Apple [1 0] :Apple2})))

(deftest make-grid-view-test
  (testing "testing make grid view"
    (are [x y z] (= z (y (make-grid-view x {:tile-map {:0 0}})))
      {:position [1 1]
       :width 5
       :height 5
       :outline-id :0
       :cursor-id :0} :kind :grid
      {:position [1 1]
       :width 5
       :height 5
       :outline-id :0
       :cursor-id :0} :selected-id :2
      {:position [1 1]
       :width 5
       :height 5
       :outline-id :0
       :cursor-id :0} :entity-positions
      {[2 2] :Apple
       [3 2] :Apple2})))
(deftest test-pickle-grid-view []
  (testing "testing pickle grid view"
    (are [x y z] (= z (y (pickle-grid-view x)))
      {:id 0
       :kind :test
       :position [0 0]
       :width 1
       :height 1
       :outline-id :1
       :pixel-coordinates {}
       :selected-id :2
       :display-ids {:1 "image-data"}
       :entity-positions {[0 0] :1}} :id 0
      {:id 0
       :kind :test
       :position [0 0]
       :width 1
       :height 1
       :outline-id :1
       :pixel-coordinates {}
       :selected-id :2
       :display-ids {:1 "image-data"}
       :entity-positions {[0 0] :1}} :kind :test
      {:id 0
       :kind :test
       :position [0 0]
       :width 1
       :height 1
       :outline-id :1
       :pixel-coordinates {}
       :selected-id :2
       :display-ids {:1 "image-data"}
       :entity-positions {[0 0] :1}} :position [0 0]
      {:id 0
       :kind :test
       :position [0 0]
       :width 1
       :height 1
       :outline-id :1
       :pixel-coordinates {}
       :selected-id :2
       :display-ids {:1 "image-data"}
       :entity-positions {[0 0] :1}} :width 1
      {:id 0
       :kind :test
       :position [0 0]
       :width 1
       :height 1
       :outline-id :1
       :pixel-coordinates {}
       :selected-id :2
       :display-ids {:1 "image-data"}
       :entity-positions {[0 0] :1}} :height 1
      {:id 0
       :kind :test
       :position [0 0]
       :width 1
       :height 1
       :outline-id :1
       :pixel-coordinates {}
       :selected-id :2
       :display-ids {:1 "image-data"}
       :entity-positions {[0 0] :1}} :outline-id :1
      {:id 0
       :kind :test
       :position [0 0]
       :width 1
       :height 1
       :outline-id :1
       :pixel-coordinates {}
       :selected-id :2
       :display-ids {:1 "image-data"}
       :entity-positions {[0 0] :1}} :pixel-coordinates {}
      {:id 0
       :kind :test
       :position [0 0]
       :width 1
       :height 1
       :outline-id :1
       :pixel-coordinates {}
       :selected-id :2
       :display-ids {:1 "image-data"}
       :entity-positions {[0 0] :1}} :selected-id :2
      {:id 0
       :kind :test
       :position [0 0]
       :width 1
       :height 1
       :outline-id :1
       :pixel-coordinates {}
       :selected-id :2
       :display-ids {:1 "image-data"}
       :entity-positions {[0 0] :1}} :display-ids {:1 "image-data"}
      {:id 0
       :kind :test
       :position [0 0]
       :width 1
       :height 1
       :outline-id :1
       :pixel-coordinates {}
       :selected-id :2
       :display-ids {:1 "image-data"}
       :entity-positions {[0 0] :1}} :entity-positions {[0 0] :1})))
