package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers){
        // TODO : Implement your solution here
        int k = 0;
        int sum = 0;
        while (sum < inputNumbers.size()){
            k++;
            sum += k;
        }
        try {
            Collections.sort(inputNumbers);
            Queue<Integer> queueNumbers = new LinkedList<>(inputNumbers);
            int[][] pyramid = new int[k][k * 2 - 1];
            int center = pyramid[0].length / 2;
            for (int i = 0; i < pyramid.length; i++) {
                for (int j = center - i; j <= center + i; j += 2) {
                    pyramid[i][j] = queueNumbers.remove();
                }
            }
            return pyramid;
        } catch (Exception | Error e){
            throw new CannotBuildPyramidException();
        }
    }


}
