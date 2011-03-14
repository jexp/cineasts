package org.neo4j.movies.movieimport;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.Map;

public class MovieDbLocalStorage {

    private String storagePath;
    protected ObjectMapper mapper;

    public MovieDbLocalStorage(String storagePath) {
        this.storagePath = storagePath;
        ensureStorageDirectoryExists();
        mapper = new ObjectMapper();
    }

    private void ensureStorageDirectoryExists() {
        File storageDirectory = new File(storagePath);
        if (!storageDirectory.isDirectory()) {
            if (!storageDirectory.mkdirs()) {
                throw new RuntimeException("Failed to create storage dir");
            }
        }
    }

    public boolean hasMovie(String movieId) {
        return fileForMovie(movieId).exists();
    }

    private File fileForMovie(String movieId) {
        return new File(storagePath, String.format("movie_%s.json", movieId));
    }

    public Map loadMovie(String movieId) {
        File storageFile = fileForMovie(movieId);
        return (Map) loadJsonValue(storageFile);
    }

    private Object loadJsonValue(File storageFile) {
        try {
            return mapper.readValue(storageFile, Object.class);
        } catch (Exception e) {
            throw new MovieDbException("Failed to load JSON from storage for file " + storageFile.getPath(), e);
        }
    }

    public void storeMovie(String movieId, Map movieData) {
        File storageFile = fileForMovie(movieId);
        storeJsonValue(movieData, storageFile);
    }

    private void storeJsonValue(Object jsonData, File storageFile) {
        try {
            mapper.writeValue(storageFile,jsonData);
        } catch (Exception e) {
            throw new MovieDbException("Failed to store JSON to storage for file " + storageFile.getPath(), e);
        }
    }

    public boolean hasPerson(String personId) {
        return fileForPerson(personId).exists();
    }

    private File fileForPerson(String personId) {
        return new File(storagePath, String.format("person_%s.json", personId));
    }

    public Map loadPerson(String personId) {
        File storageFile = fileForPerson(personId);
        return(Map)loadJsonValue(storageFile);
    }

    public void storePerson(String personId, Map personJson) {
        File storageFile = fileForPerson(personId);
        storeJsonValue(personJson, storageFile);
    }
}
