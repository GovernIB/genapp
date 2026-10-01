package org.fundaciobit.genapp.common.crypt;

import java.io.File;
import java.util.Map;

import org.fundaciobit.genapp.common.filesystem.FileSystemManager;
import org.fundaciobit.genapp.common.filesystem.SimpleFileSystemManager;

/**
 * 
 * @author anadal (u80067)
 * 12 jun 2026 11:16:23
 */
public class FileSystemManagerTester {

    public static void main(String[] args) {

        try {

            File dir = new File("c:/windows/system32");

            FileSystemManager.setFilesPath(dir);

            
            {
                FileSystemManager.setFileSystemManager(new SimpleFileSystemManager());

                long start = System.currentTimeMillis();

                Map<Long, File> files = FileSystemManager.getAllFiles();

                long end = System.currentTimeMillis();

                System.out.println("FILE SIMPLE: Temps: " + (end - start) + " ms [ # FITXERS = " + files.size() + " ]");
            }

            {
                FileSystemManager.setFileSystemManager(new SimpleFileSystemManager());

                long start = System.currentTimeMillis();

                Map<Long, File> files = FileSystemManager.getAllFiles();

                long end = System.currentTimeMillis();

                System.out.println("FILE THREE: Temps: " + (end - start) + " ms [ # FITXERS = " + files.size() + " ]");
            }
            
            

            {
                FileSystemManager.setFileSystemManager(new SimpleFileSystemManager());

                long start = System.currentTimeMillis();

                String[] files = FileSystemManager.getAllFileNames();

                long end = System.currentTimeMillis();

                System.out.println("NAME SIMPLE: Temps: " + (end - start) + " ms [ # FITXERS = " + files.length + " ]");
            }

            {
                FileSystemManager.setFileSystemManager(new SimpleFileSystemManager());

                long start = System.currentTimeMillis();

                String[] files = FileSystemManager.getAllFileNames();

                long end = System.currentTimeMillis();

                System.out.println("NAME THREE: Temps: " + (end - start) + " ms [ # FITXERS = " + files.length + " ]");
            }
            

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
