package com;
import java.util.*;

/**
 * @author Yavuz Koroglu
 */
public class CorrectedRunningAverage
{
    private Double currentAverage = 0.0;
    private Integer populationSize = 0;
    /**
     * Default Constructor.
     */
    public CorrectedRunningAverage()
    {
        this.currentAverage = 0.0;
        this.populationSize = 0;
    }

    /**
     * Explicit Value Constructor.
     */
    public CorrectedRunningAverage(double lastAverage, int lastPopulationSize) throws Exception {
        if(lastAverage<0 || lastPopulationSize<0) throw new IllegalArgumentException();
        this.currentAverage = lastAverage;
        this.populationSize = lastPopulationSize;
        roundoff();
    }

    /**
     * Copy Constructor.
     */
    public CorrectedRunningAverage(CorrectedRunningAverage lastAverage)
    {
        this.currentAverage = lastAverage.currentAverage;
        this.populationSize = lastAverage.populationSize;
        roundoff();
    }

    /**
     * Getter for currentAverage
     */
    public Double getCurrentAverage()
    {
        roundoff();
        return currentAverage;
    }
    private void roundoff(){
        currentAverage = Math.round(currentAverage * 1000.0) / 1000.0;

    }
    /**
     * Getter for populationSize
     */
    public Integer getPopulationSize()
    {
        return populationSize;
    }

    /**
     * Adds elements to the population and returns the new average.
     */
    public Double addElements(List<Double> addedPopulation)
    {
        if (addedPopulation.size() == 0 || addedPopulation == null) {
            return this.currentAverage;
        }

        double sum = this.currentAverage * this.populationSize;
        for (double element : addedPopulation) {
            sum += element;
            this.populationSize++;
        }

        this.currentAverage = sum / this.populationSize;
        roundoff();
        return this.currentAverage;
    }

    /**
     * Removes elements to the population and returns the new average.
     */
    public Double removeElements(List<Double> removedPopulation) throws IllegalAccessException {
        if (removedPopulation.size() == 0 || removedPopulation == null) {
            return this.currentAverage;
        }
        if(populationSize<removedPopulation.size()) throw new IllegalAccessException();
        double sum = this.currentAverage * this.populationSize;
        for (double element : removedPopulation) {
            sum -= element;
            this.populationSize--;
        }

        this.currentAverage = sum / this.populationSize;
        roundoff();
        return this.currentAverage;
    }

    /**
     * Combines two running averages and returns a new running average
     */
    static public CorrectedRunningAverage combine(final CorrectedRunningAverage avg1, final CorrectedRunningAverage avg2) throws Exception {
        return new CorrectedRunningAverage
                (
                        (avg1.getCurrentAverage() * avg1.getPopulationSize() + avg2.getCurrentAverage() * avg2.getPopulationSize())
                                / (avg1.getPopulationSize() + avg2.getPopulationSize()),
                        avg1.getPopulationSize() + avg2.getPopulationSize()
                );
    }
}