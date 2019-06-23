package main.com.chenwan.coding2019.download.impl;

import main.com.chenwan.coding2019.download.api.Connection;
import main.com.chenwan.coding2019.download.api.ConnectionException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * @program: base
 * @description:
 * @author: cliffcw
 * @create: 2019-06-23 13:51
 */
public class ConnectionImpl implements Connection {


    URL url;
    static final int BUFFER_SIZE = 1024;

    ConnectionImpl(String _url) throws ConnectionException {
        try {
            url = new URL(_url);
        } catch (MalformedURLException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    public byte[] read(int startPos, int endPos) throws Exception {
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

        httpConn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);

        InputStream is  = httpConn.getInputStream();

        byte[] buff = new byte[BUFFER_SIZE];

        int totalLen = endPos - startPos + 1;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        while(baos.size() < totalLen){

            int len = is.read(buff); //Reads some number of bytes from the input stream and stores them into the buffer array
            if (len < 0) {
                break;
            }
            baos.write(buff,0, len); //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
        }

        if(baos.size() > totalLen){ //copy this smaller byte array to larger byte array
            byte[] data = baos.toByteArray();
            return Arrays.copyOf(data, totalLen);
        }

        return baos.toByteArray();
    }

    @Override
    public int getContentLength() {
        URLConnection con;
        try {
            con = url.openConnection();

            return con.getContentLength();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public void close() {

    }
}
