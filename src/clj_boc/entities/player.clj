(ns clj-boc.entities.player
  (:use [clj-boc.entities.core :only [Entity]]
        [clj-boc.entities.aspects.mobile :only [Mobile move can-move?]]
        [clj-boc.entities.aspects.digger :only [Digger dig can-dig?]]
        [clj-boc.coordinates :only [destination-coords]]
        [clj-boc.world :only [find-empty-tile get-tile-kind set-tile-floor]]))

(defrecord Player [id glyph color location])

(defn check-tile
  [world dest pred]
  (pred (get-tile-kind world dest)))

(extend-type Player Entity
  (tick [this world]
        world))
(extend-type Player Mobile
  (move [this world dest]
        {:pre [(can-move? this world dest)]}
        (assoc-in world [:entities :player :location] dest))
  (can-move? [this world dest]
             (check-tile world dest #{:floor})))

(extend-type Player Digger
  (dig [this world dest]
       {:pre [(can-dig? this world dest)]}
       (set-tile-floor world dest))
  (can-dig? [this world dest]
            (check-tile world dest #{:wall})))

(defn move-player [world dir]
  (let [player (get-in world [:entities :player])
        target (destination-coords (:location player) dir)]
    (cond
      (can-move? player world target) (move player world target)
      (can-dig? player world target) (dig player world target)
      :else world)))

(defn make-player [world]
  (->Player :player "@" :yellow (find-empty-tile world)))
