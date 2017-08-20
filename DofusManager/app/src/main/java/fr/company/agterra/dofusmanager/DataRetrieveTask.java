package fr.company.agterra.dofusmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import fr.company.agterra.dofusmanager.Objects.Item;

/**
 * Created by Agterra on 20/08/2017.
 */

public class DataRetrieveTask extends AsyncTask <URL, Integer, String>{

    private static String maxPageNumberFileName = "maxPageNumber";

    private static String itemsIDsFileName = "itemsIDs";

    private static String baseEquipementURL = "https://www.dofus.com/fr/mmorpg/encyclopedie/equipements?size=96";

    private static String requestPropertyKey = "User-Agent";

    private static String requestPropertyValue = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    private URL equipementURL;

    private StringBuilder fileString;

    private Context currentContext;


    public DataRetrieveTask(Context currentContext)
    {

        this.currentContext = currentContext;

    }

    @Override
    protected String doInBackground(URL... urls) {

        try{

            System.out.println("Synchronizing base...");

            int pagesNumber = this.getNumberOfPage();

            ArrayList <String> equipementsIDs = this.getAllEquipementsIDs(pagesNumber);

            ArrayList <Item> allEquipements = this.getAllEquipements(equipementsIDs);

            System.out.println("All your bases are belong to us");

        }
        catch (Exception e)
        {

            System.out.println("Woops...");

            System.out.println(e.getMessage());

        }

        return "done";

    }

    @Override
    protected void onPostExecute(String xmlFile) {

        super.onPostExecute(xmlFile);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);

    }

    private int getNumberOfPage() throws Exception {

        System.out.println("Retrieving number of pages...");

        File file = new File(this.currentContext.getFilesDir(), maxPageNumberFileName);

        if(!file.exists()) {

            System.out.println("Stored number of page not found...");

            int highestPage = 0;


            this.equipementURL = new URL(baseEquipementURL);

            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

            URLConnection urlConnection = this.equipementURL.openConnection();

            urlConnection.setRequestProperty(requestPropertyKey, requestPropertyValue);

            urlConnection.connect();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line = new String();

            this.fileString = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {

                this.fileString.append("\n" + line);

                String pageString = "<a href=\"/fr/mmorpg/encyclopedie/equipements?size=96&amp;page=";

                int pageNumberLineIndex = line.indexOf(pageString);

                int pageNumberEndLineIndex = line.indexOf("\">");

                if (pageNumberLineIndex != -1) {

                    String pageNumberString = line.substring(pageNumberLineIndex + pageString.length(), pageNumberEndLineIndex);

                    int page = Integer.parseInt(pageNumberString);

                    if (page > highestPage)
                        highestPage = page;

                }

            }

            String pageMaxNumber = String.valueOf(highestPage);


            FileOutputStream fileOutputStream = new FileOutputStream(file);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(pageMaxNumber);

            objectOutputStream.close();

            fileOutputStream.close();

            System.out.println("Maximum number of pages set!");

            return highestPage;

        }
        else {

            FileInputStream fileInputStream = this.currentContext.openFileInput(maxPageNumberFileName);

            ObjectInputStream objecInputStream = new ObjectInputStream(fileInputStream);

            String pageNumber = (String) objecInputStream.readObject();

            objecInputStream.close();

            fileInputStream.close();

            System.out.println("Maximum number of pages found!");

            return Integer.parseInt(pageNumber);

        }

    }

    private ArrayList<String> getAllEquipementsIDs(int numberOfPages) throws Exception
    {

        System.out.println("Getting all items ids...");

        File file = new File(this.currentContext.getFilesDir(), itemsIDsFileName);

        ArrayList <String> equipementsIDs = new ArrayList<>();

        if(!file.exists()) {

            System.out.println("Stored items ids not found...");

            for (int i = 1; i <= numberOfPages; i++) {

                this.equipementURL = new URL(baseEquipementURL + "&page=" + i);

                CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

                URLConnection urlConnection = this.equipementURL.openConnection();

                urlConnection.setRequestProperty(requestPropertyKey, requestPropertyValue);

                urlConnection.connect();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line = new String();

                while ((line = bufferedReader.readLine()) != null) {

                    String pageString = "<td><span class=\"ak-linker\"><a href=\"/fr/mmorpg/encyclopedie/equipements/";

                    int itemIDFirstIndex = line.indexOf(pageString);

                    int itemIDLastIndex = line.indexOf("\"><img");

                    if (itemIDFirstIndex != -1) {

                        String itemID = line.substring(itemIDFirstIndex + pageString.length(), itemIDLastIndex);

                        equipementsIDs.add(itemID);

                    }

                }

                FileOutputStream fileOutputStream = new FileOutputStream(file);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                objectOutputStream.writeObject(equipementsIDs);

            }

            System.out.println("All items ids set!");

            return equipementsIDs;

        }
        else
        {

            FileInputStream fileInputStream = new FileInputStream(file);

            ObjectInputStream objecInputStream = new ObjectInputStream(fileInputStream);

            ArrayList storedItemsIDs = (ArrayList) objecInputStream.readObject();

            objecInputStream.close();

            fileInputStream.close();

            System.out.println("All items ids found!");

            return storedItemsIDs;

        }


        // Could use this value to do a checksum with the number of items we should get
       // System.out.println(equipementsIDs.size());

    }

    private ArrayList<Item> getAllEquipements (ArrayList <String> equipementsIDs) throws Exception
    {

        ArrayList <Item> allEquipements  = new ArrayList<>();

        for (int i = 0; i <= equipementsIDs.size(); i++)
        {

            this.equipementURL =  new URL(baseEquipementURL + "/" + equipementsIDs.get(i));

            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

            URLConnection urlConnection = this.equipementURL.openConnection();

            urlConnection.setRequestProperty(requestPropertyKey, requestPropertyValue);

            urlConnection.connect();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            this.fileString = new StringBuilder();

            String line = new String();

            while ((line = bufferedReader.readLine()) != null)
            {

                this.fileString.append("\n"+line);

            }

            Item item = new Item();

            String itemTypeString = "\"ak-encyclo-detail-type col-xs-6\"";

            int itemTypeFirstIndex = this.fileString.indexOf(itemTypeString);

            int itemTypeLastIndex = this.fileString.indexOf("</span></div><div class=\"ak-encyclo-detail-type col-xs-6 text-right\">Level: ");

            System.out.println("index: " + itemTypeFirstIndex);

            if(itemTypeFirstIndex != -1)
            {

                String itemType = this.fileString.substring(itemTypeFirstIndex + itemTypeString.length(), itemTypeLastIndex);

                int startIndex = itemType.indexOf("<span>");

                int endIndex = itemType.indexOf("</span>");

                itemType = itemType.substring(startIndex, endIndex);

                for (ItemType type : ItemType.values())
                {

                    if(type.name().equalsIgnoreCase(itemType))
                        item.setType(type);


                    System.out.println(type.name());


                }

            }

        }

        return allEquipements;

    }

}
