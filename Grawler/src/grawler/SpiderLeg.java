/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawler;

import java.io.IOException;
//import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
//import java.util.concurrent.TimeUnit;
//import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;

/**
 *
 * @author root
 */
public class SpiderLeg {

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux i686; rv:64.0) Gecko/20100101 Firefox/64.0";
    private List<String> links = new LinkedList<String>();
    private Document htmlDocument;
    //private final long PERIOD=10L;
    
    public boolean crawl(String url, String search) {
        try {
            //Connection connection=Jsoup.connect(url).userAgent(USER_AGENT);
            Response connection = Jsoup.connect(url).userAgent(USER_AGENT).referrer("https://www.google.com").timeout(22000).followRedirects(true).execute();
            Document htmlDocument = connection.parse();
            this.htmlDocument = htmlDocument;
            if (connection.statusCode() == 200) {
                System.out.println("\n**Visiting OK** Recieved web page at " + url);
            }
            if (connection.statusCode() == 201) {
                System.out.println("\n**Visiting CREATED** Recieved web page at " + url);
            }
            if (connection.statusCode() == 202) {
                System.out.println("\n**Visiting ACCEPTED** Recieved web page at " + url);
            }
            if (connection.statusCode() == 203) {
                System.out.println("\n**Visiting INFORMATIVE** Recieved web page at " + url);
            }
            if (!connection.contentType().contains("text/html")) {
                System.out.println("**FAILURE** Retrieved Something other than HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for (Element link : linksOnPage) {
                this.links.add(link.absUrl("href"));
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error IOE - at link : "+url);
            //e.printStackTrace();
            return false;
        }
    }

    public boolean searchForWord(String searchWord) throws Exception {
        if (this.htmlDocument == null) {
            System.out.println("ERROR! Call crawl() before analysis");
            return false;
        }
        
        System.out.println("Searching for the word " + searchWord + "...");
        this.htmlDocument.outputSettings().escapeMode(Entities.EscapeMode.extended);
        this.htmlDocument.outputSettings().escapeMode(Entities.EscapeMode.base);
        this.htmlDocument.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
        String bodyText = this.htmlDocument.text();
        boolean output=bodyText.toLowerCase().contains(searchWord.toLowerCase());
        String data = bodyText;
        if(output)
        {
            for(int i=416;i<data.length();)
            {
                if(data.charAt(i)=='<')
                    i=div(i+1,data);
                else if(data.charAt(i)=='[')
                    i=div2(i+1,data);
                else if(data.charAt(i)=='{')
                    i=div3(i+1,data);
                else
                    i+=1;
                System.out.print(data.charAt(i-1));
                if(data.charAt(i-1)=='.')
                    delay(10l);
                else if(data.charAt(i-1)==',')
                    delay(20l);
                else if(data.charAt(i-1)=='('||data.charAt(i-1)==')')
                    delay(15l);
            }
        }
        else
            System.out.println("**No references for the exact word - case insensitive**");
        return output;
    }

    public List<String> getLinks() {
        return this.links;
    }

    private void delay(long PERIOD) {
        long lastTime=System.currentTimeMillis();
        boolean sleep=true;
        while(sleep)
        {
            long thisTime=System.currentTimeMillis();
            if(thisTime>=lastTime+PERIOD)
                sleep=false;
        }
    }
    
    private int div(int i,String data)
    {
        //int main=i;
        for(;i<data.length();)
        {
            if(data.charAt(i)=='<')
                i=div(i+1,data);
            else if(data.charAt(i)=='>')
                break;
            else
                i+=1;
        }
        return i-1;
    }
    private int div2(int i,String data)
    {
        //int main=i;
        for(;i<data.length();)
        {
            if(data.charAt(i)=='[')
                i=div2(i+1,data);
            else if(data.charAt(i)==']')
                break;
            else
                i+=1;
        }
        return i-1;
    }
    private int div3(int i,String data)
    {
        //int main=i;
        for(;i<data.length();)
        {
            if(data.charAt(i)=='{')
                i=div2(i+1,data);
            else if(data.charAt(i)=='}')
                break;
            else
                i+=1;
        }
        return i-1;
    }
}
