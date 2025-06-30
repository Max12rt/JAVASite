package com.example.demo.Entity;

import com.example.demo.AlcoholPreference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "alcohol_preferences")
public class AlcoholPreferenceEntity {

    @Id
    private String id;
    private String userId;
    private AlcoholPreference preference;

    public AlcoholPreferenceEntity() {}

    public AlcoholPreferenceEntity(String userId, AlcoholPreference preference) {
        this.userId = userId;
        this.preference = preference;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public AlcoholPreference getPreference() { return preference; }
    public void setPreference(AlcoholPreference preference) { this.preference = preference; }
}
