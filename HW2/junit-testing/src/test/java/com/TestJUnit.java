package com;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestJUnit {

    @Test
    public void Q1default(){
        RunningAverage avg = new RunningAverage();
        double result = avg.getCurrentAverage();
        double size = avg.getPopulationSize();

        assertTrue(result == 0);
        assertTrue(size == 0);
    }
    @CsvSource({
            "0,    1",
            "1,    2",
            "49,  51",
            "1,  100"
    })
    @ParameterizedTest(name = "{0}, {1}")
    public void Q1explicit(double avgr,int numb) throws Exception {
        RunningAverage avg = new RunningAverage(avgr,numb);
        double result = avg.getCurrentAverage();
        int size = avg.getPopulationSize();
        assertTrue(result == avgr);
        assertTrue(size == numb);
    }




    @ParameterizedTest()
    @CsvSource({
            "0,    1,   1",
            "1,    2,   3",
            "49,  51, 100",
            "1,  100, 101"
    })
    public void Q2add(double first,double second,double third) throws Exception {
        RunningAverage avg = new RunningAverage(3,5);
        int initialSize = avg.getPopulationSize();
        List<Double> list = new ArrayList<>();
        list.add((double) first);
        list.add((double) second);
        list.add((double) third);
        double actual = (3*5 + first + second + third)/8 ;

        List<Double> copy = new ArrayList<>(list);
        double newavg = avg.addElements(list);
        double result = avg.getCurrentAverage();
        List<Double> empty = new ArrayList<>();
        double newavgg = avg.addElements(empty);

        assertAll(
                () -> assertEquals(result,actual,"a)Adds and removes the given population to and from the RunningAverage, respectively."),
                () -> assertEquals(copy,list,"b)Must not change the contents of the given List."),
                () ->         assertEquals(newavg,actual,"c)Must return the new average."),
                () ->        assertEquals(newavgg,result,"d)Must return the current average if the given List object is empty or null."),
                () ->         assertEquals(initialSize+3,8,"e)Must update the population size, accordingly.")



        );




    }
    @ParameterizedTest()
    @CsvSource({
            "0,    1,   1",
            "1,    2,   3",
            "4,  1, 6",
            "1,  2, 8"
    })
    public void Q2remove(double first,double second,double third) throws Exception {
        RunningAverage avg = new RunningAverage(3,5);
        int initialSize = avg.getPopulationSize();
        List<Double> list = new ArrayList<>();
        list.add((double) first);
        list.add((double) second);
        list.add((double) third);
        double actual = (3*5 - first - second - third)/2 ;

        List<Double> copy = new ArrayList<>(list);
        boolean exceptionThrown3 = false;
        double newavg = 0;
        try {
            newavg = avg.removeElements(list);
        } catch (Exception e) {
            exceptionThrown3 = true;
        }
        double result = avg.getCurrentAverage();

        boolean exceptionthrown4 = false;
        List<Double> empty = new ArrayList<>();
        double stillavg = 0;
        try {
            stillavg = avg.removeElements(empty);
        } catch (Exception e) {
            exceptionthrown4 = true;
        }
        double finalNewavg = newavg;
        double finalstillavg = stillavg;
        boolean finalExceptionThrown = exceptionThrown3;
        boolean finalExceptionthrown = exceptionthrown4;
        assertAll(
                () -> assertEquals(result,actual,"a)Adds and removes the given population to and from the RunningAverage, respectively."),
                () -> assertTrue(copy.equals(list),"b)Must not change the contents of the given List."),
                () ->         assertTrue(!finalExceptionThrown &&finalNewavg ==actual,"c)Must return the new average."),
                () ->        assertTrue(!finalExceptionthrown &&finalstillavg==result,"d)Must return the current average if the given List object is empty or null."),
                () ->         assertEquals(initialSize-3,avg.getPopulationSize(),"e)Must update the population size, accordingly.")



        );




    }
    @ParameterizedTest()
    @ValueSource(ints = {-1,-2,-3,-4})
    public void Q3(int i) {
        int size = 0;
        boolean exceptionThrown = false;
        try {
            RunningAverage avg = new RunningAverage(5, i);
            size = avg.getPopulationSize();
        }
        catch (Exception e){
            exceptionThrown = true;
        }
        assertTrue((exceptionThrown)||size >= 0,"3. You may assume that the initial population size is NOT negative.");
    }
    @Test
    public void Q4() throws Exception {
        RunningAverage avg = new RunningAverage(5, 1);
        boolean exceptionThrown = false;

        try {
            List<Double> temp = new ArrayList<>();
            temp.add(0.0);
            temp.add(0.0);
            avg.removeElements(temp);
        }
        catch(Exception e){
            exceptionThrown = true;
        }
        int expected = avg.getPopulationSize();
        assertTrue((exceptionThrown)||expected >= 0,"4.The population size can never be negative.");

    }
    @ParameterizedTest()
    @CsvSource({
            "0,    1,   1,3",
            "1,    2,   3,4",
            "4,  1, 6,5",
            "1,  2, 8,6"
    })
    public void Q5(double avg1,int size1,double avg2,int size2) throws Exception {
        RunningAverage temp1 = new RunningAverage(avg1, size1);
        RunningAverage temp2 = new RunningAverage(avg2, size2);
        RunningAverage result= new RunningAverage();
        boolean exceptionThrown = false;
        try {
            result = RunningAverage.combine(temp1, temp2);
        }
        catch (Exception e){
            exceptionThrown = true;
        }
        double actualavg = (avg1*size1 + avg2*size2)/(size1+size2);
        int actualsize = size1 + size2;
        RunningAverage finalResult = result;
        boolean finalExceptionThrown = exceptionThrown;
        assertAll(
                () -> assertTrue(  finalResult instanceof RunningAverage,"a)Must create a new RunningAverage object"),
                () -> assertTrue(!finalExceptionThrown,"b. Must combine the two averages and their respective population sizes."),
                () -> assertTrue(finalResult.getCurrentAverage() == actualavg && finalResult.getPopulationSize() == actualsize,"c. The new currentAverage and the populationSize variables must be equal to the average of the overall population and\n" +
                        "sum of the two population sizes, respectively.")
        );


    }


}