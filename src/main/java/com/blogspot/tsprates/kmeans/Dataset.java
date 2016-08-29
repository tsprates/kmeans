package com.blogspot.tsprates.kmeans;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class Dataset
{

    private String delim;
    private int numCols;
    private List<String> lines;

    public Dataset(String file, String delim)
    {
        this.delim = delim;

        try
        {
            lines = FileUtils.readLines(new File(file));
            String line = lines.get(0);
            String[] tokens = line.trim().split(delim);
            numCols = tokens.length;

        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public double[][] read()
    {
        double[][] data = new double[getNumLines()][getNumCols()];

        try
        {
            int numLine = 0;
            for (String line : lines)
            {
                String[] tokens = line.trim().split(delim);

                for (int i = 0; i < tokens.length; i++)
                {
                    data[numLine][i] = Double.valueOf(tokens[i]);
                }
                numLine++;
            }

            return data;
        }
        catch (NumberFormatException ex)
        {
            throw new RuntimeException(ex);
        }

    }

    /**
     * @return the numLines
     */
    public int getNumLines()
    {
        return lines.size();
    }

    /**
     * @return the numCols
     */
    public int getNumCols()
    {
        return numCols;
    }

}
