package com.blogspot.tsprates.kmeans;

import java.util.Arrays;
import java.util.Random;

public class App
{

    private static final Dataset dataset;
    private static final int numCols;
    private static final int numLines;
    private static final int numClusters = 4;
    private static final Random R = new Random();
    

    static
    {
        dataset = new Dataset("ruspini.txt", "\\s+");
        numCols = dataset.getNumCols();
        numLines = dataset.getNumLines();
    }

    public static void main(String[] args)
    {
        double[][] data = dataset.read();
        kmeans(data, numCols, numLines, numClusters);
    }

    private static int[] kmeans(double[][] dataset, int numCols, int numLines, int numClusters)
    {
        int epoch = 0;

        double[][] center = new double[numClusters][numCols];
        double[][] distCenter = new double[numClusters][numLines];

        int[] cluster = new int[numLines];
        int[] lastCluster = new int[numLines];

        int[] numCluster = new int[numClusters];
        double[][] sumCluster = new double[numClusters][numCols];

        int[] centerIndex = new int[numClusters];

        for (int i = 0; i < numClusters;)
        {
            boolean found = false;
            int rand = R.nextInt(numLines + 1);

            for (int k = 0; k < i; k++)
            {
                if (rand == centerIndex[k])
                {
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                centerIndex[i] = rand;
                i += 1;
            }

        }

        // generate random centers (clusters)
        for (int i = 0; i < numClusters; i++)
        {
            System.arraycopy(dataset[centerIndex[i]], 0, center[i], 0, numCols);
        }

        while (epoch < 10000)
        {
            System.arraycopy(cluster, 0, lastCluster, 0, numLines);

            for (int i = 0; i < numLines; i++)
            {
                // calculate the Euclidean distance to the centers
                for (int j = 0; j < numClusters; j++)
                {
                    double total = 0.0;
                    for (int k = 0; k < numCols; k++)
                    {
                        total += Math.pow(dataset[i][k] - center[j][k], 2.0);
                    }
                    distCenter[j][i] = Math.sqrt(total);
                }
            }
            
            for (int i = 0; i < numLines; i++)
            {
        	int smallest = 0;
        	                
        	// calculate the closer center
                for (int j = 1; j < numClusters; j++)
                {
                    if (distCenter[j][i] < distCenter[smallest][i])
                    {
                	smallest = j;
                    }
                }
                
                cluster[i] = smallest;
            }
                        
            // reset the quantity and sum relative to each center
            for (int i = 0; i < numClusters; i++)
            {
                numCluster[i] = 0;
                for (int j = 0; j < numCols; j++)
                {
                    sumCluster[i][j] = 0;
                }
            }

            // update centers
            for (int i = 0; i < numLines; i++)
            {
        	int k = cluster[i];
        	numCluster[k] += 1;
                
                for (int j = 0; j < numCols; j++)
                {
                    sumCluster[k][j] += dataset[i][j];
                }
            }
            
            for (int i = 0; i < numClusters; i++)
            {
                for (int j = 0; j < numCols; j++)
                {
                    center[i][j] = sumCluster[i][j] / numCluster[i];
                }
            }
            
            // check if there's any changes in the clusters
            if (Arrays.equals(cluster, lastCluster))
            {
                break;
            }

            epoch++;
        }

        // show graphic
        Chart chart = new Chart(dataset, center, cluster, numClusters);
	chart.show();
                
        return cluster;
    }

}
