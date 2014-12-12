;; Definition for user agents. This is a good template to build on to produce
;; smarter agents.

(ns pucks.agents.user  
  (:use [pucks globals util vec2D]
        [pucks.agents active]))

(defn user-proposals [p]
 (let [goodness (filter :vent (:sensed p))
       badness (filter :zapper (:sensed p))]
  {:acceleration (if (empty? badness)   
                   1            
                   (* 0.25 (- (length (:velocity p)))))
  :rotation 
         (if (empty? badness)
              (if (empty? goodness)
                (:rotation p)
                (direction->rotation (:position (first goodness))))
               (+ (direction->rotation (:position (first badness))) pi))})) 

(defn user []
  (merge (active)
         {:user true
          :proposal-function user-proposals}))