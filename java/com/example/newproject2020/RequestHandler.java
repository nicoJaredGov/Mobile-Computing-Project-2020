package com.example.newproject2020;

import android.app.Activity;
import android.content.Context;

import org.json.JSONException;

public interface RequestHandler {
    public void processResponse(String response) throws JSONException;
}
