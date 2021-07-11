package com.example.miniventilator.patient;

public class patienthelper {
    public String patient_name, patient_username, patient_email, patient_password, patient_age, patient_gender;

    public patienthelper(){

    }

    public patienthelper(String patient_name, String patient_username, String patient_email, String patient_password, String patient_age, String patient_gender) {
        this.patient_name = patient_name;
        this.patient_username = patient_username;
        this.patient_email = patient_email;
        this.patient_password = patient_password;
        this.patient_age = patient_age;
        this.patient_gender = patient_gender;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_username() {
        return patient_username;
    }

    public void setPatient_username(String patient_username) {
        this.patient_username = patient_username;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }

    public String getPatient_password() {
        return patient_password;
    }

    public void setPatient_password(String patient_password) {
        this.patient_password = patient_password;
    }

    public String getPatient_age() {
        return patient_age;
    }

    public void setPatient_age(String patient_age) {
        this.patient_age = patient_age;
    }

    public String getPatient_gender() {
        return patient_gender;
    }

    public void setPatient_gender(String patient_gender) {
        this.patient_gender = patient_gender;
    }
}
