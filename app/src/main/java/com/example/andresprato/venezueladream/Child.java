package com.example.andresprato.venezueladream;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Child {

    private String firstName;
    private String lastName;
    private String description;
    private int age;
    private String imageUrl;


    public Child(String fName, String lName, String desc, String img, String dob) {
        firstName = fName;
        lastName = lName;
        description = desc;
        imageUrl = img;
        calculateAge(dob);

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getAge() {
        return age;
    }

    private void calculateAge(String dob) {

        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);

        try {
            Calendar dateOfBirth = Calendar.getInstance();
            dateOfBirth.setTime(sdf.parse(dob));

            int birthYear = dateOfBirth.get(Calendar.YEAR);
            int todayYear = today.get(Calendar.YEAR);

            if (birthYear > 50) {
                birthYear += 1900;
            } else {
                birthYear +=2000;
            }

            int diff = todayYear - birthYear;

            if (dateOfBirth.get(Calendar.MONTH) > today.get(Calendar.MONTH) ||
                    (dateOfBirth.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                            dateOfBirth.get(Calendar.DATE) > today.get(Calendar.DATE))) {
                diff--;
            }

            age = diff;

        } catch (Exception e) {
            e.printStackTrace();
            age = 0;
        }
    }
}
