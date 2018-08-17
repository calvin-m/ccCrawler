package com.calvinmai;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        //"https://raw.githubusercontent.com/OnAssignment/compass-interview/master/data.json";
        System.out.println("Start crawler");
        Crawler crawler = new Crawler("https://raw.githubusercontent.com/OnAssignment/compass-interview/master/data.json");
        crawler.run();
        crawler.printResult();
        System.out.println("*** End application. **** (Developed by Calvin Mai)");

    }
}
