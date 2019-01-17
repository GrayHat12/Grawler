/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawler;

import java.util.Scanner;

/**
 *
 * @author root
 */
public class Grawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {
        Spider spider=new Spider();
        Scanner in=new Scanner(System.in);
        System.out.println("args[0]");
        String args1=in.nextLine();
        System.out.println("args[1]");
        int args2=in.nextInt();
        spider.search(args1,args2);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
