package com.algorithmia.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;

import com.algorithmia.APIException;
import com.algorithmia.client.*;

/**
 * A reference to a file in the data api
 */
public class DataFile extends DataObject {

    public DataFile(HttpClient client, String dataUrl) {
        super(client, dataUrl);
    }

     /**
      * Returns whether the file exists in the Data API
      * @return true iff the file exists
      * @throws APIException if there were any problems communicating with the DataAPI
      */
    public boolean exists() throws APIException {
        HttpResponse response = client.head(getUrl());
        int status = response.getStatusLine().getStatusCode();
        if(status != 200 && status != 404) {
            throw APIException.fromHttpResponse(response);
        }
        return (200 == status);
    }

    /**
     * Gets the data for this file and saves it to a local temporary file
     * @return the data as a local temporary file
     * @throws APIException if there were any problems communicating with the DataAPI
     * @throws IOException if there were any problems consuming the response content
     */
    public File getFile() throws APIException, IOException {
        File tempFile = File.createTempFile(getName(), null);
        FileOutputStream outputStream = new FileOutputStream(tempFile);
        IOUtils.copy(getInputStream(), outputStream);
        return tempFile;
    }

    /**
     * Gets the data for this file as an InputStream
     * @return the data as an InputStream
     * @throws APIException if there were any problems communicating with the Algorithmia API
     * @throws IOException if there were any problems consuming the response content
     */
    public InputStream getInputStream() throws APIException, IOException {
        final HttpResponse response = client.get(getUrl());
        HttpClientHelpers.throwIfNotOk(response);
        return response.getEntity().getContent();
    }

    /**
     * Gets the data for this file as as string
     * @return the data as a String
     * @throws APIException if there were any problems communicating with the Algorithmia API
     * @throws IOException if there were any problems consuming the response content
     */
    public String getString() throws IOException {
        return IOUtils.toString(getInputStream(), "UTF-8");
    }

    /**
     * Gets the data for this file as as string
     * @return the data as a String
     * @throws APIException if there were any problems communicating with the Algorithmia API
     * @throws IOException if there were any problems consuming the response content
     */
    public byte[] getBytes() throws IOException {
        return IOUtils.toByteArray(getInputStream());
    }

    /**
     * Upload string data to this file as text
     * @param data the data to upload
     * @throws APIException if there were any problems communicating with the Algorithmia API
     */
    public void put(String data) throws APIException {
        HttpResponse response = client.put(getUrl(), HttpClientHelpers.stringEntity(data, ContentType.TEXT_PLAIN));
        HttpClientHelpers.throwIfNotOk(response);
    }

    /**
     * Upload raw data to this file as binary
     * @param data the data to upload
     * @throws APIException if there were any problems communicating with the Algorithmia API
     */
    public void put(byte[] data) throws APIException {
        HttpResponse response = client.put(getUrl(), HttpClientHelpers.byteArrayEntity(data, ContentType.APPLICATION_OCTET_STREAM));
        HttpClientHelpers.throwIfNotOk(response);
    }

    /**
     * Upload raw data to this file as an input stream
     * @param is the input stream of data to upload
     * @throws APIException if there were any problems communicating with the Algorithmia API
     */
    public void put(InputStream is) throws APIException {
        HttpResponse response = client.put(getUrl(), HttpClientHelpers.inputStreamEntity(is, ContentType.APPLICATION_OCTET_STREAM));
        HttpClientHelpers.throwIfNotOk(response);
    }

    /**
     * Upload new data to this file from an existing file. These is a convenience wrapper for using InputStream.
     * @param file the file to upload data from
     * @throws APIException if there were any problems communicating with the Algorithmia API
     * @throws FileNotFoundException if the specified file does not exist
    */
    public void put(File file) throws APIException, FileNotFoundException {
        put(new FileInputStream(file));
    }

    /**
     * Deletes this file.
     * @throws APIException if there were any problems communicating with the Algorithmia API
     */
    public void delete() throws APIException {
        HttpResponse response = client.delete(getUrl());
        HttpClientHelpers.throwIfNotOk(response);
    }

}
