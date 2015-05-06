package com.company;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.tools.DatasetTools;
import net.sf.javaml.tools.data.ARFFHandler;

import java.io.File;

public class Reader
{
    private Dataset readData, data = new DefaultDataset();

    public Reader () {}

    /**
     * @param name Type of sound, can be Hat/Snare/Clap/Kick
     * @param start Index of file to start reading from
     * @param end Index of file to stop reading at
     * @param classIndex Index to assign to class
     * @return A Dataset object containing the data that has been read
     */
    public Dataset readARFF(String name, int start, int end, int classIndex) {
            for (int i = start; i < end; i++) {
                try {
                    readData = ARFFHandler.loadARFF(new File("ARFF\\" + name + "\\" + i + ".arff"), classIndex);
                } catch (java.io.FileNotFoundException e) {
                    System.out.println("error: " + e.getMessage());
                }
                DatasetTools.merge(data, readData);
            }
        return data;
    }
}
