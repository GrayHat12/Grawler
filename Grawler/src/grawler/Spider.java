/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawler;

import java.net.URLEncoder;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author root
 */
public class Spider {
    //private static int MAX_PAGES_TO_SEARCH=10;
    private Set<String> pagesVisited=new HashSet<String>();
    private List<String> pagesToVisit=new LinkedList<String>();
    private int viss=0;
    
    public void search(String searchWord,int MAX_PAGES_TO_SEARCH)throws Exception
    {
        String url="http://www.google.com/search?q="+URLEncoder.encode(searchWord,"UTF-8");
        while(this.pagesVisited.size()<MAX_PAGES_TO_SEARCH)
        {
            String currentUrl;
            SpiderLeg leg=new SpiderLeg();
            if(this.pagesToVisit.isEmpty())
            {
                currentUrl=url;
                this.pagesVisited.add(url);
            }
            else
                currentUrl=this.nextUrl();
            leg.crawl(currentUrl,searchWord);
            boolean success=leg.searchForWord(searchWord);
            if(success)
            {
                System.out.println(String.format("\n**SUCCESS** Word %s",searchWord,currentUrl));
                //break;
            }
            this.pagesToVisit.addAll(leg.getLinks());
            //System.out.println("\n\nPages Visited = "+this.pagesVisited);
            //System.out.println("\n\nPages To Visit = "+this.pagesToVisit);
        }
        System.out.println("\n\n\n**DONE** Visited "+this.pagesVisited.size()+" web page(s)");
    }
    
    private String nextUrl()
    {
        String nextUrl=this.pagesToVisit.get(viss);
        viss+=1;
        //this.pagesToVisit.remove(0);
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
}