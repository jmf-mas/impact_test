package numberrangesummarizer;
// we use the interface Collection to implement our collection of numbers.
import java.util.Collection;
// we choose ArrayList as an implementation of the interface Collection.
import java.util.ArrayList;
// we use the interface Iterator to loop over items of our collection.
import java.util.Iterator;
/*
This implementation considers as a valid input a comma-delimted list of numbers (integers).
When another format than this above is provided, it is considered as an excpetion.
We allow increasing, decreasing or both sequences. We also allow duplications.
*/

public class NumberSummarizer implements NumberRangeSummarizer {
    

    //collect the input and return it in the required format. 
    public Collection<Integer> collect(String input) {
        // the argument <input> is a comma delimited collection of numbers.
        // So the following command convert <input> to an array of numbers.
        String[] numbers = input.split(",");
        // the following instruction creates an empty collection of type ArrayList
        Collection<Integer> collectionNumbers = new ArrayList<>();
        //check whether the input provided is a list of comma-delimited integers.
        try {
            // this for loop adds int-converted numbers to the collection. 
            for (String number : numbers) {
                // here we remove white space around a number and we convert to an integer.
                collectionNumbers.add(Integer.parseInt(number.trim()));
            }
            return collectionNumbers;
        } catch (Exception e) {
            System.out.println("values read is not a comma delimited list of integers.");
        }
        return null;

    }

    //get the summarized string
    public String summarizeCollection(Collection<Integer> input) {
        // check if the input has the right format.
        if (input != null) {
            // this instruction gets the items contained in the collection <input>.
            Iterator<Integer> iterator = input.iterator();
            // we get the first item of the collection
            int startNumber = iterator.next();
            // this variable will save the last value of the collection encountered
            int endNumber = startNumber;
            // this variable will check the range 
            int temporaryEndNumber = startNumber;
            // this variable is true when a range is detected in the collection.
            boolean isRangeFound = false;
            // the result variable
            String results = "";
            // loop over the elements of the collection.
            while (iterator.hasNext()) {
                // get the next item in the collection.
                endNumber = iterator.next();
                // compute the difference between two items to detect a range. For example,
                // if the difference between the 3rd and the 6th values is 3, it means there
                // is a range between the 3rd and the 6th values.
                int difference = endNumber - temporaryEndNumber;

                // here we check whether a number is not in a range.
                if (results.length() == 0 && (difference > 1 || difference < -1) && !isRangeFound) {
                    results += "" + startNumber;
                    startNumber = endNumber;
                    temporaryEndNumber = endNumber;

                    // here we check whether there is a range
                } else if (difference == 1 || difference == 0 || difference == -1) {
                    isRangeFound = true;
                    temporaryEndNumber = endNumber;
                    // here we treat a range
                } else if (isRangeFound) {
                    if (results.length() == 0) {
                        results += "" + startNumber + "-" + temporaryEndNumber;
                    } else {
                        results += ", " + startNumber + "-" + temporaryEndNumber;
                    }
                    // we mark the end of a range
                    startNumber = endNumber;
                    temporaryEndNumber = endNumber;
                    isRangeFound = false;

                    // this instruction runs when no range is detected
                } else {
                    results += ", " + startNumber;
                    startNumber = endNumber;
                    temporaryEndNumber= endNumber;
                }
            }
            // check whether the results only contain one value or one range.
            // this is to avoid that the value in the result starts with a comma. (e.g. result = ", 1-3")
            if (results.length() == 0) {
                // if the last value or range is not included in the results.
                if (!isRangeFound) {
                    results += "" + endNumber;
                } else {
                    results += "" + startNumber + "-" + temporaryEndNumber;
                }
             // if the last value or range is not included in the results.
            } else if (!isRangeFound) {
                results += ", " + endNumber;
            } else {
                results += ", " + startNumber + "-" + temporaryEndNumber;
            }
            return results;
        }

        return "";
    }

    public static void unit_tests() {
        NumberSummarizer runner = new NumberSummarizer();
        System.out.println("Unit test:");
        System.out.println("---------------");

        // unit test using the example provided. This example has an increasing sequence of numbers.
        System.out.print("(Case 0): ");
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test with a list of decreasing numbers.
        System.out.print("(Case 1): ");
        input = "31,24, 23, 22, 21, 15, 14, 13, 12, 8, 7, 6,3,1";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test with white spaces between numbers. This example has an increasing sequence of numbers.
        System.out.print("(Case 2): ");
        input = "1,2, 3,8, 12,15,21,22,23,24,25,26";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test with a list of unsorted elements
        System.out.print("(Case 3): ");
        input = "2,1,5 ,4, 3,0";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test of a list of duplicated numbers.
        System.out.print("(Case 4): ");
        input = "2,2,2 ,3, 4,7, 9, 10, 11, 13,15";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test of a list of alphanumeric values.
        System.out.print("(Case 5): ");
        input = "1,A3, 6, 7, 8,12,13,14,15,21,22,23,24,31";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test of a list of positive and negative numbers.
        System.out.print("(Case 6): ");
        input = "-3,-2,-1 ,1, 2,3, 9, 10, 11, 13,16, -17, -16, -20";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test of an empty list of numbers.
        System.out.print("(Case 7): ");
        input = "";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test of a string.
        System.out.print("(Case 8): ");
        input = "unit test";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test of a list with float numbers.
        System.out.print("(Case 9): ");
        input = "1, 3.5, 7, 7.5, 10, 15, 16, 17.5";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test of a list two numbers.
        System.out.print("(Case 10): ");
        input = "1, 2";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));

        // unit test of a list one number.
        System.out.print("(Case 11): ");
        input = "1";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));
        
        // unit test of a string of numbers but with a wrong delimitator.
        System.out.print("(Case 12): ");
        input = "1;  2; 5, 6, 7";
        System.out.print("input = ");
        System.out.print(input);
        System.out.print(" ==> result = ");
        System.out.println(runner.summarizeCollection(runner.collect(input)));
    }

    public static void main(String args[]) {
        unit_tests();
    }

}
