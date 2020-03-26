package com.example.metronome.playlistdatabase;

import androidx.room.TypeConverter;

import com.example.metronome.songdatabase.Song;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ConvertList {

    //TODO: Comment from this page https://medium.com/@toddcookevt/android-room-storing-lists-of-objects-766cca57e3f9

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Song> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Song>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Song> someObjects) {
        return gson.toJson(someObjects);
    }
}
