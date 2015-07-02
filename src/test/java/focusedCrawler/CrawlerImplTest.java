package focusedCrawler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.junit.Test;

import focusedCrawler.crawler.CrawlerImpl;

public class CrawlerImplTest {

    @Test
    public void redirectURLExtractionShouldWork() throws MalformedURLException, IOException {

        // insert some test URLs which are redirecting in nature
        String[] testURLs = {
                "http://en.wikipedia.org/wiki/ZMapp",
                "http://en.wikipedia.org/wiki/Dengue_fever",
                "http://en.wikipedia.org/wiki/Viral_hemorrhagic_fever",
                "http://www.youtube.com/user/CBSNewsOnline",
                "http://legalterms.cbsinteractive.com/terms-of-use",
                "http://www.samaritanspurse.org/our-ministry/check-out/",
                "http://pinterest.com/medindia/",
                "http://www.facebook.com/sharer.php?u=http://www.mayoclinic.org/diseases-conditions/ebola-virus/basics/symptoms/CON-20031241",
                "http://www.nytimes.com/2014/10/09/nyregion/amid-concern-about-virus-in-us-new-york-hospital-says-its-ready-for-the-worst.html?_r=5",
                "http://twitter.com/#!/RxEconsult",
                "http://intelligence.businessinsider.com/",
                "http://twitter.com/home?status=Ebola+virus+-+Symptoms%20-%20http%3a%2f%2fwww.nhs.uk%2fConditions%2febola-virus%2fPages%2fSymptoms.aspx&WT.mc_id=50411",
                "http://www.nhs.uk/Personalisation/Registration.aspx?ReturnUrl=http%3a%2f%2fwww.nhs.uk%2fConditions%2febola-virus%2fPages%2fSymptoms.aspx",
                "http://www.thelancet.com/journals/lancet/article/PIIS0140-6736(10)60667-8/fulltext",
                "http://www.youtube.com/user/RxEconsult",
                "http://onesource.thomsonreuters.com/",
                "http://www.youtube.com/user/SamaritansPurseVideo",
                "http://www.facebook.com/Health24",
                "http://ce.mayo.edu/",
                "http://www.linkedin.com/groups/Health24-Network-4195423?home=&gid=4195423&trk=anet_ug_hm",
                "http://twitter.com/", "http://pinterest.com/mashable/" };

        ArrayList<String> positiveURLs = new ArrayList<String>();

        for (String s : testURLs) {
            if (isRedirecting(s))
                positiveURLs.add(s);
        }

        if (positiveURLs.size() > 0) {
            // check the Method
            CrawlerImpl myImpl = new CrawlerImpl();

            for (String eachURL : positiveURLs) {

                URLConnection myURLConnection = (new URL(eachURL)).openConnection();
                String redirect = myImpl.getRedirectedLocation(myURLConnection,
                        myURLConnection.getHeaderFields());
                assertEquals("Redirect URL extractor fails! ", true,
                        (redirect != null && !redirect.isEmpty()));
            }

        }

        // check them first, and store the tested URLs

        // perform the test on tested URLs

    }

    private boolean isNull(String redirect) {
        // if()
        return false;
    }

    public boolean isRedirecting(String url) {

        URL myUrl;
        try {
            myUrl = new URL(url);
            URLConnection conn = myUrl.openConnection();
            if (conn instanceof HttpURLConnection) {
                HttpURLConnection myHttpUrlConnection = (HttpURLConnection) conn;
                int responseCode;
                try {
                    responseCode = myHttpUrlConnection.getResponseCode();
                    if (responseCode == 301 || responseCode == 302 || responseCode == 303
                            || responseCode == 304 || responseCode == 305 || responseCode == 306
                            || responseCode == 307)
                        return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return false;
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        return false;
    }

}