package com.task.delivery.optimization;

import com.task.delivery.dto.GeocodeDto;

import java.util.List;

public class AdvancedOptimization extends  Optimizer{

    @Override
    protected List<GeocodeDto> calculateOptimizationResults(List<GeocodeDto> geocodeDtos, GeocodeDto startGeo) {
        return null;
    }
}
