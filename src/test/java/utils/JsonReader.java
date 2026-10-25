package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonReader {

    public static List<Map<String, String>>readData(String path) throws IOException{
        return new ObjectMapper().readValue(new File(path),
                new TypeReference<List<Map<String, String>>>() {});
    }
}
