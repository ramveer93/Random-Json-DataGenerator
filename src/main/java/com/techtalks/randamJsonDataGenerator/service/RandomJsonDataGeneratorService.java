package com.techtalks.randamJsonDataGenerator.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techtalks.randamJsonDataGenerator.exception.RandomJsonGeneratorException;
import com.techtalks.randamJsonDataGenerator.utils.DataType;
import com.techtalks.randamJsonDataGenerator.utils.RandomJsonDataGeneratorUtils;

@Component
public class RandomJsonDataGeneratorService {

	@Autowired
	private RandomJsonDataGeneratorUtils utils;

	public String generateData(String body, int recordCount, String type, String xpath, boolean randomValues) {
		try {
			if (type.equals(DataType.JSON.toString())) {
				return parseRequestedJSON(body, recordCount, convertXpathToList(xpath), randomValues);
			}
		} catch (JSONException e) {
			throw new RandomJsonGeneratorException(e.getMessage());
		}
		return null;
	}

	private List<String> convertXpathToList(String xpath) {
		return xpath.isEmpty() ? new ArrayList<>() : Arrays.asList(xpath.trim().split("\\."));
	}

	private String parseRequestedJSON(String json, int recordCount, List<String> xpath, boolean randomValues)
			throws JSONException {
		JSONObject object = new JSONObject(json);
		object = parseXpath(object, xpath, recordCount, randomValues);
		return object.toString();
	}

	private JSONObject parseXpath(JSONObject object, List<String> xpath, int count, boolean randomValues)
			throws JSONException {
		int i = 0;
		Map<String, JSONArray> map = new ConcurrentHashMap<>();
		JSONArray ar = new JSONArray();
		while (i < xpath.size() && !xpath.isEmpty()) {
			ar = object.getJSONArray(xpath.get(i));
			object = object.getJSONArray(xpath.get(i)).getJSONObject(0);
			if (i == xpath.size() - 1) {
				if (randomValues) {
					Set<String> keys = getArrayKeySet(object);
					for (String key : keys) {
						if (object.get(key) instanceof String) {
							object.put(key, utils.getRandomString());
						} else if (object.get(key) instanceof Integer) {
							object.put(key, utils.getRandomInteger());
						}

					}
				}

				for (int j = 0; j < count - 1; j++) {

					ar.put(object);
				}
			}
			map.put(xpath.get(i), ar);
			i++;
		}
		Set<String> keys = map.keySet();
		for (String key : keys) {
			// System.out.println("key: "+key+"--->value"+map.get(key));
			if (!key.equals(xpath.get(0))) {
				map.remove(key);
			}
		}
		return xpath.isEmpty() ? object : new JSONObject(map);
	}

	private Set<String> getArrayKeySet(JSONObject object) throws JSONException {
		Iterator<?> itr = object.keys();
		Set<String> arrayKeySet = new HashSet<>();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			Object value = object.get(key);
			System.out.println("key is : " + key + "value : " + value);
			if (!value.toString().startsWith("[")) {
				arrayKeySet.add(key);
			}
		}
		return arrayKeySet;

	}

}
