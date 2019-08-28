(ns editor.ui.views.grid
  (:use
    [gloom.ui.core :only [draw-tile]]
    [editor.ui.views.core :only [make-view mouse->grid draw-view-outline]]))

(defn- build-image-positions [[start-x start-y] [end-x end-y] display-ids]
  (let [positions (for [y (range start-y end-y)
                        x (range start-x end-x)]
                    [x y])]
    (->> display-ids
         (zipmap positions)
         (into {}))))

(defn- draw-image-grid [view state]
  (doseq [[[x y] id] (:item-positions view)]
    (draw-tile x y (:tile-map state) id)))

(defn- draw [view state]
  (draw-view-outline view state)
  (draw-image-grid view state))

(defn- on-click [[mouse-x mouse-y] view state]
  (let [tile-id (get (:item-positions view) (mouse->grid view))]
    (if tile-id
      (assoc-in state [:editor :views (:id view) :selected-id] tile-id)
      state)))

(defn make-grid-view [{:keys [] :as view-data} state]
  (let [view (make-view view-data)
        display-ids (->> state
                         :tile-map
                         keys
                         (sort-by #(bigdec (name %))))]
        (assoc
          view
          :selected-id :2
          :display-ids display-ids
          :item-positions (build-image-positions (:start view) (:end view) display-ids)
          :draw-fn draw
          :on-click-fn on-click)))
