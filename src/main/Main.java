package main;

import java.io.File;

import com.sun.org.apache.xerces.internal.util.URI;

public class Main {

    public static void main(String[] args) {
        File file = new File("res/corpus/ALLEMAND/example2.txt");
            java.net.URI uri = file.toURI();
            

            System.out.println(file.toPath().get);       

    }

}
