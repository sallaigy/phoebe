package paladiff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class Paladiff {
    
    public static void main(String[] args) {
        System.out.println("paladiff v0.0.0.0.1");
        System.out.println("Current directory: " + System.getProperty("user.dir"));
        if (args.length == 0) {
            System.out.println("A test directory is required.");
            
            return;
        }
        
        File directory = new File(args[0]);
        if (!directory.isDirectory()) {
            System.out.println("Invalid test directory.");
            
            return;
        }
        
        List<String> tests = Paladiff.findTests(directory);
        
        int total = 0;
        int success = 0;
        int failure = 0;
        
        for (String entry : tests) {
            total++;
            System.out.print("Running test: " + entry);

            File input = new File(entry.concat(".in"));
            File output = new File(entry.concat(".out"));
            File actual = new File("test.tmp");
            
            if (!output.exists() || !input.exists()) {
                System.out.println(" SKIPPED");
                continue;
            }
            
            PrintStream ps = null;
            
            try {
                ps = new PrintStream(actual);

                FileInputStream fis = new FileInputStream(input);
                
                System.setOut(ps);
                System.setIn(fis);

                Scanner scanner = new Scanner(System.in);
                String line;
                                
                // Futás
                
                phoebe.Main.main(new String[] {entry});
                
                System.setIn(new FileInputStream(FileDescriptor.in));
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                
                // Jöjjön a teszt
                
                BufferedReader actualReader = new BufferedReader(new InputStreamReader(new FileInputStream(actual)));
                BufferedReader expectedReader = new BufferedReader(new InputStreamReader(new FileInputStream(output)));
                
                StringBuilder actualString = new StringBuilder();
                StringBuilder expectedString = new StringBuilder();
                                
                String actualLine;
                String expectedLine;
                
                StringBuilder outputString = new StringBuilder();
                
                boolean failed = false;
                
                while (!failed) {                    
                    if ((expectedLine = expectedReader.readLine()) != null) {
                        outputString.append("+ " + expectedLine + "\n");
                    }
                    
                    if ((actualLine = actualReader.readLine()) != null) {
                        outputString.append("- " + actualLine + "\n");
                    } else {
                        if (null != expectedLine) {
                            failed = true;
                        }
                        
                        break;
                    }
                    
                    if (actualLine.equals(expectedLine)) {
                        failed = true;
                    }
                }
                
                if (failed) {
                    failure++;
                    System.out.println(" FAILURE");
                    System.out.println(outputString.toString());
                } else {
                    success++;
                    System.out.println(" SUCCESS");                    
                }
                
                /*
                while ((line = actualReader.readLine()) != null) {
                    actualString.append(line);
                }
                
                while ((line = expectedReader.readLine()) != null) {
                    expectedString.append(line);
                } 
                
                if (expectedString.equals(actualString)) {
                    success++;
                    System.out.println(" SUCCESS");
                } else {
                    failure++;
                    System.out.println(" FAILURE");
                    
                    System.out.println(String.format("\nExpected: %s\n----------\nActual:   %s\n", expectedString.toString(), actualString.toString()));
                } */
                
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        }
        
        System.out.println(String.format("TEST SUMMARY: %d tests, %d successful, %d failures.", total, success, failure));
        
    }
    
    private static List<String> findTests(File directory) {
        List<String> tests = new ArrayList<String>();
        File[] inputFiles = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".in");
            }
        }); 
        
        
        for (File file : inputFiles) {
            String testName = Paladiff.trimExtension(file);
            
            tests.add(testName);
        }
        
        return tests;
    }
    
    private static String trimExtension(File file) {
        String filename = file.getPath();
        
        return filename.substring(0, filename.lastIndexOf('.'));        
    }

}
