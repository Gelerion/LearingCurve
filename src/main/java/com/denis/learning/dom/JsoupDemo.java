package com.denis.learning.dom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupDemo {
    public static void main(String[] args) {
        String html = "<html>" +
                "           <head>" +
                "                   <title>First parse</title>" +
                "           </head>" +
                "           <body>"  +
                "               <p>Parsed HTML into a doc.</p>" +
                "           </body>" +
                "       </html>";

        Document doc = Jsoup.parse(html);
        //System.out.println(doc);
        Elements elements = doc.getAllElements();


        String htmlFragment = "<div><p>Lorem ipsum.</p>";
        Document fragmentDoc = Jsoup.parseBodyFragment(htmlFragment);
        Elements select = fragmentDoc.select("div");
        System.out.println("select = " + select);

    }
}
