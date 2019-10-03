package com.example.nusrat.smarthealthmonitoringsystem;

public class ulpoadUserData {
    private String HighPresure,LowPresure,Sugar, msg,date,stomach;

    public ulpoadUserData(String highPresure, String lowPresure, String sugar,String date, String msg,String stomach) {

        this.stomach=stomach;
        HighPresure = highPresure;
        LowPresure = lowPresure;
        Sugar = sugar;
        this.msg = msg;
        this.date=date;


    }

    public ulpoadUserData(String highPresure, String lowPresure,String date, String msg,String stomach) {

        this.stomach=stomach;
        HighPresure = highPresure;
        LowPresure = lowPresure;
        this.msg = msg;
        this.date=date;
    }


    public ulpoadUserData( String sugar,String date, String msg,String stomach) {

        this.stomach=stomach;
        Sugar = sugar;
        this.msg = msg;
        this.date=date;
    }



    public ulpoadUserData(){}


    public String getStomach() {
        return stomach;
    }

    public void setStomach(String stomach) {
        this.stomach = stomach;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setHighPresure(String highPresure) {
        HighPresure = highPresure;
    }

    public void setLowPresure(String lowPresure) {
        LowPresure = lowPresure;
    }

    public void setSugar(String sugar) {
        Sugar = sugar;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }




    public String getHighPresure() {
        return HighPresure;
    }

    public String getLowPresure() {
        return LowPresure;
    }

    public String getSugar() {
        return Sugar;
    }

    public String getMsg() {
        return msg;
    }
}
