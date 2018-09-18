package com.emrehmrc.kodhamuru.soap;

import android.util.Log;

import com.emrehmrc.kodhamuru.model.BlogNotif;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BlogNotifSoap {

    private static final String NAMESPACE = "http://kodhamuru.somee.com/";
    private static final String METHODE = "BlogNotifAll";
    private static final String SOAP_ACTION = "http://www.kodhamuru.somee.com/BlogNotifAll";
    private static final String URL = "http://www.kodhamuru.somee.com/webservices/Notification.asmx";
    public BlogNotif blogNotif;
    public ArrayList<BlogNotif> blogNotifArrayList;
    private SoapObject soapObject;
    private SoapSerializationEnvelope soapSerializationEnvelope;
    private HttpTransportSE httpsTransportSE;

    public ArrayList<BlogNotif> getAll() {

        blogNotif = new BlogNotif();
        blogNotifArrayList = new ArrayList<>();
        soapObject = new SoapObject(NAMESPACE, METHODE);

        soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapSerializationEnvelope.dotNet = true;
        soapSerializationEnvelope.setOutputSoapObject(soapObject);

        httpsTransportSE = new HttpTransportSE(URL);
        httpsTransportSE.debug = true;

        try {

            httpsTransportSE.call(SOAP_ACTION, soapSerializationEnvelope);
            SoapObject response = (SoapObject) soapSerializationEnvelope.bodyIn;
            SoapObject responseTask = (SoapObject) response.getProperty(0);
            for (int i = 0; i < responseTask.getPropertyCount(); i++) {

                SoapObject responseTask2 = (SoapObject) responseTask.getProperty(i);
                String title = responseTask2.getProperty("TITLE").toString();
                String readcount = responseTask2.getProperty("READCOUNT").toString();
                String commentcount = responseTask2.getProperty("COMMENTCOUNT").toString();
                String ıd = responseTask2.getProperty("ID").toString();

                blogNotifArrayList.add(new BlogNotif(title,readcount,commentcount,ıd));
            }

        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return blogNotifArrayList;
    }


}

