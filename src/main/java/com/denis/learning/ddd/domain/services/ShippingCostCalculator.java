package com.denis.learning.ddd.domain.services;

import com.denis.learning.ddd.value.objects.Consignment;
import com.denis.learning.ddd.value.objects.Money;
import com.denis.learning.ddd.value.objects.WeightBand;
import com.denis.learning.ddd.value.objects.WeightInKg;

/**
 * Created by denis.shuvalov on 21/02/2018.
 * <p>
 * The ShippingCostCalculator encapsulates the domain logic that calculates the shipping cost of
 * consignment based on the weight of a collection of consignments, along with the weight of a box. By
 * organizing this logic under a specific domain service and naming it, you can explicitly talk to domain
 * experts about a particular piece of domain logic such as a policy or a process in a concise manner. The
 * ShippingCostCalculator may have been an implicit concept held by the business, but by naming it,
 * you have ensured it is now explicit and should be added to the UL and the analysis model.
 */
public class ShippingCostCalculator {
    private Iterable<WeightBand> weightBand;
    private WeightInKg boxWeightInKg;

    public ShippingCostCalculator(Iterable<WeightBand> weightBand, WeightInKg boxWeightInKg) {
        this.weightBand = weightBand;
        this.boxWeightInKg = boxWeightInKg;
    }

    public Money calculateCostToShip(Iterable<Consignment> consignments) {
        WeightInKg weight = getTotalWeight(consignments);

        //reverse sort list
        //weightBand.orderBy(x -> x.forConsignmentsUpToThisWeightInKg.value)

        // Get first match
        //WeightBand weightBand = weightBand.firstOrDefault(x -> x.isWithinBand(weight));

        //return weightBand.price();
        return null;
    }

    private WeightInKg getTotalWeight(Iterable<Consignment> consignments) {
        WeightInKg totalWeight = new WeightInKg();

        // Calculate the weight of the items
        for (Consignment consignment : consignments) {
            totalWeight.add(consignment.consignmentWeight());
        }

        // Add a box weight
        totalWeight.add(boxWeightInKg);

        return totalWeight;
    }

}
