package code;

import java.io.*;
import java.util.Scanner;


public class Assignment1 {

    public int[][] denseMatrixMult(int[][] A, int[][] B, int size) {
        if (size == 1) {
            int result[][] = initMatrix(1);
            result[0][0] = A[0][0] * B[0][0];
            return result;
        } else {
            int half = size / 2;
            int zero[][] = initMatrix(half);
            int Minor0[][] = denseMatrixMult(sum(A, A, 0, 0, half, half, half), sum(B, B, 0, 0, half, half, half), half);
            int Minor1[][] = denseMatrixMult(sum(A, A, half, 0, half, half, half), sum(B, zero, 0, 0, 0, 0, half), half);
            int Minor2[][] = denseMatrixMult(sum(A, zero, 0, 0, 0, 0, half), sub(B, B, 0, half, half, half, half), half);
            int Minor3[][] = denseMatrixMult(sum(A, zero, half, half, 0, 0, half), sub(B, B, half, 0, 0, 0, half), half);
            int Minor4[][] = denseMatrixMult(sum(A, A, 0, 0, 0, half, half), sum(B, zero, half, half, 0, 0, half), half);
            int Minor5[][] = denseMatrixMult(sub(A, A, half, 0, 0, 0, half), sum(B, B, 0, 0, 0, half, half), half);
            int Minor6[][] = denseMatrixMult(sub(A, A, 0, half, half, half, half), sum(B, B, half, 0, half, half, half), half);

            int resultMatrix[][] = initMatrix(size);

            int Cofactor00[][] = sub(sum(Minor0, Minor3, 0, 0, 0, 0, half), sub(Minor4, Minor6, 0, 0, 0, 0, half), 0, 0, 0, 0, half);
            int Cofactor01[][] = sum(Minor2, Minor4, 0, 0, 0, 0, half);
            int Cofactor10[][] = sum(Minor1, Minor3, 0, 0, 0, 0, half);
            int Cofactor11[][] = sum(sub(Minor0, Minor1, 0, 0, 0, 0, half), sum(Minor2, Minor5, 0, 0, 0, 0, half), 0, 0, 0, 0, half);


            for (int i = 0; i < half; i++)
                for (int j = 0; j < half; j++)
                    resultMatrix[i][j] = Cofactor00[i][j];

            for (int i = 0; i < half; i++)
                for (int j = half; j < size; j++)
                    resultMatrix[i][j] = Cofactor01[i][j - half];

            for (int i = half; i < size; i++)
                for (int j = 0; j < half; j++)
                    resultMatrix[i][j] = Cofactor10[i - half][j];

            for (int i = half; i < size; i++)
                for (int j = half; j < size; j++)
                    resultMatrix[i][j] = Cofactor11[i - half][j - half];

            return resultMatrix;
        }
    }

    public int[][] sum(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n) {
        int result[][] = initMatrix(n);

        for (int i = x1; i < x1 + n; i++)
            for (int j = y1; j < y1 + n; j++)
                result[i - x1][j - y1] = A[i][j];
        for (int i = x2; i < x2 + n; i++)
            for (int j = y2; j < y2 + n; j++)
                result[i - x2][j - y2] += B[i][j];

        return result;
    }


    public int[][] sub(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int n) {
        int result[][] = initMatrix(n);

        for (int i = x1; i < x1 + n; i++)
            for (int j = y1; j < y1 + n; j++)
                result[i - x1][j - y1] = A[i][j];
        for (int i = x2; i < x2 + n; i++)
            for (int j = y2; j < y2 + n; j++)
                result[i - x2][j - y2] -= B[i][j];

        return result;
    }


    public int[][] initMatrix(int n) {
        return new int[n][n];
    }

    public void printMatrix(int n, int[][] A) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(A[i][j] + " ");
            System.out.println();
        }
    }

    public int[][] readMatrix(String filename, int n) throws Exception {
        String cwd = System.getProperty("user.dir");
        File inFile = new File(cwd + "\\" + filename);
        Scanner sc = new Scanner(inFile);
        int retMatrix[][] = initMatrix(n);
        while (sc.hasNextInt())
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    retMatrix[i][j] = sc.nextInt();
        sc.close();
        return retMatrix;
    }

}