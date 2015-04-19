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

/**
 * Az ellenőrzés fő eszköze.
 * Egy egyszerű, a UNIX rendszerekből jól ismert diff parancsra emlékeztető segédprogramot megvalósító osztály.
 * A parancs két fájlt vár bemenetként, az egyik az elvárt kimenet, a másik pedig a főprogram fájlba irányított kimenete.
 * Amennyiben a kapott és az elvárt kimenet ugyanaz, a tesztet sikeresnek tekintjük, különben a teszt sikertelen.
 *
 */
public class Paladiff {
    
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        System.out.println("paladiff v0.0.0.0.1");
        System.out.println("Current directory: " + System.getProperty("user.dir"));
        if (args.length == 0) {
            System.out.println("A test directory is required.");
            
            return;
        }
        
        // Megadjuk a mappát, amelyben a tesztesetek megtalálhatók.
        File directory = new File(args[0]);
        if (!directory.isDirectory()) {
            System.out.println("Invalid test directory.");
            
            return;
        }
        
        // Megkeressük a teszteseteket
        List<String> tests = Paladiff.findTests(directory);
        
        int total = 0;
        int success = 0;
        int failure = 0;
        
        // Végigmegyünk az egyes teszteseteken
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
            	// Átirányítjuk a szabványos kimenetet és bemenetet fájlba
                ps = new PrintStream(actual);

                FileInputStream fis = new FileInputStream(input);
                
                System.setOut(ps);
                System.setIn(fis);

                
                                
                // Futás
                
                phoebe.Main.main(new String[] {entry});
                
                System.setIn(new FileInputStream(FileDescriptor.in));
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                
                // Jöjjön a teszt: vizsgáljuk a teszteseteket.
                
                BufferedReader actualReader = new BufferedReader(new InputStreamReader(new FileInputStream(actual)));
                BufferedReader expectedReader = new BufferedReader(new InputStreamReader(new FileInputStream(output)));
                                                
                String actualLine;
                String expectedLine;
                
                StringBuilder outputString = new StringBuilder();
                
                boolean failed = false;
                
                while (!failed) {                    
                    if ((expectedLine = expectedReader.readLine()) != null) {
                        expectedLine.trim();
                        outputString.append("+ " + expectedLine + "\n");
                    }
                    
                    if ((actualLine = actualReader.readLine()) != null) {
                        actualLine.trim();
                        outputString.append("- " + actualLine + "\n");
                    } else {
                        if (null != expectedLine) {
                            outputString.append("- ");
                            failed = true;
                        }
                        
                        break;
                    }                    
                    
                    if (!actualLine.equals(expectedLine)) {
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
                
                System.out.println();                
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }
        }
        
        // Eeredmény kiiratása
        System.out.println(String.format("TEST SUMMARY: %d tests, %d successful, %d failures.", total, success, failure));
        
    }
    
    /**
     * Ez a metódus visszaadja a paraméterként átadott mappában megtalálható teszteseteket.
     * @param directory A könyvtár, amelyben meg kell keresni a teszteseteket.
     * @return Egy lista az adott mappában található tesztesetek neveivel.
     */
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
    
    /**
     * Ez a metódus visszaadja a teszteset nevét.
     * @param file Fájl, amelyből ki szeretnénk szedni a teszteset nevét.
     * @return A lecsupaszított teszteset név.
     */
    private static String trimExtension(File file) {
        String filename = file.getPath();
        
        return filename.substring(0, filename.lastIndexOf('.'));        
    }

}
