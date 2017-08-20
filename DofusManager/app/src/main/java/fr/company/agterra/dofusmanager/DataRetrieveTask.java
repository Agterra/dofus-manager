package fr.company.agterra.dofusmanager;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;

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

                System.out.println(line);

                this.fileString.append(line);

            }

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

}
