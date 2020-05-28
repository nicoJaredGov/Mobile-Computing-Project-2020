package com.example.newproject2020;

import org.json.JSONException;

public interface RequestHandler {
    public void processResponse(String response) throws JSONException;
}
