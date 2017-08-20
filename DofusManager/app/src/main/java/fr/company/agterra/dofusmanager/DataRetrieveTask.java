package fr.company.agterra.dofusmanager;

import android.os.AsyncTask;
import android.os.SystemClock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Agterra on 20/08/2017.
 */

public class DataRetrieveTask extends AsyncTask <URL, Integer, String>{

    private static String baseEquipementURL = "https://www.dofus.com/fr/mmorpg/encyclopedie/equipements?size=96";

    private static String requestPropertyKey = "User-Agent";

    private static String requestPropertyValue = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    private URL equipementURL;

    private StringBuilder fileString;

    @Override
    protected String doInBackground(URL... urls) {

        try{

            int pagesNumber = this.getNumberOfPage();

            ArrayList <String> equipementsIDs = this.getAllEquipementsIDs(pagesNumber);

        }
        catch (Exception e)
        {

            System.out.println(e.getMessage());

        }

        return fileString.toString();

    }

    @Override
    protected void onPostExecute(String xmlFile) {

        super.onPostExecute(xmlFile);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);

    }

    private int getNumberOfPage() throws Exception
    {

        int highestPage = 0;


        this.equipementURL =  new URL(baseEquipementURL);

        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

        URLConnection urlConnection = this.equipementURL.openConnection();

        urlConnection.setRequestProperty(requestPropertyKey, requestPropertyValue);

        urlConnection.connect();


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String line = new String();

        this.fileString = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null)
        {

            this.fileString.append("\n" + line);

            String pageString = "<a href=\"/fr/mmorpg/encyclopedie/equipements?size=96&amp;page=";

            int pageNumberLineIndex = line.indexOf(pageString);

            int pageNumberEndLineIndex = line.indexOf("\">");

            if(pageNumberLineIndex != -1)
            {

                String pageNumberString = line.substring(pageNumberLineIndex + pageString.length(), pageNumberEndLineIndex);

                int page = Integer.parseInt(pageNumberString);

                if (page > highestPage)
                    highestPage = page;

            }

        }

        bufferedReader.close();

        return highestPage;

    }

    private ArrayList<String> getAllEquipementsIDs(int numberOfPages) throws Exception
    {

        ArrayList <String> equipementsIDs = new ArrayList<>();

        for (int i = 1; i <= numberOfPages; i++)
        {

            this.equipementURL =  new URL(baseEquipementURL + "&page=" + i);

            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

            URLConnection urlConnection = this.equipementURL.openConnection();

            urlConnection.setRequestProperty(requestPropertyKey, requestPropertyValue);

            urlConnection.connect();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line = new String();

            while ((line = bufferedReader.readLine()) != null)
            {

                String pageString = "<td><span class=\"ak-linker\"><a href=\"/fr/mmorpg/encyclopedie/equipements/";

                int itemIDFirstIndex = line.indexOf(pageString);

                int itemIDLastIndex = line.indexOf("\"><img");

                if(itemIDFirstIndex != -1)
                {

                    String itemID = line.substring(itemIDFirstIndex + pageString.length(), itemIDLastIndex);

                    equipementsIDs.add(itemID);

                    System.out.println(itemID);

                }

            }


        }

        System.out.println(equipementsIDs.size());

        return equipementsIDs;

    }



}
