package com.example.hospital;

import android.content.Context;
import android.content.Intent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Appointment {
    private String ID;
    private String doctorID;
    private String patientID;
    private Date date;
    private int numberInQueue;

    public Appointment(String ID, String doctorID, String patientID, Date date, int numberInQueue) {
        this(doctorID, patientID, date, numberInQueue);
        this.ID = ID;
    }

    public Appointment(String doctorID, String patientID, Date date, int numberInQueue) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.date = date;
        this.numberInQueue = numberInQueue;
    }


    public static int numberInQueue(Date date, Doctor doctor, Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        ResultSet resultSet = DataBase.excutQuery("select * from Appiontment where doctor_id = '" + doctor.getID() + "'"
                + " and date = '" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "'",context);
        int i = 1;
        try{
            while (resultSet.next())
            {
                if(Integer.parseInt(resultSet.getString("number_in_queue")) != i)
                {
                    return i;
                }
                i++;
            }
        }catch (SQLException e)
        {
        }
        return i;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberInQueue() {
        return numberInQueue;
    }

    public void setNumberInQueue(int numberInQueue) {
        this.numberInQueue = numberInQueue;
    }

    public static ArrayList<Appointment> getPatientAppointments(String attribute , String value, Context context) {
        ResultSet resultSet = DataBase.excutQuery("select * from Appiontment where "+attribute+" = '" +
                value + "'", context);
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Appointment appointment = new Appointment(resultSet.getString("id"),resultSet.getString("doctor_id"),
                        resultSet.getString("patient_id"),Appointment.convertDate(resultSet.getString("date")),
                        Integer.parseInt(resultSet.getString("number_in_queue")));
                appointments.add(appointment);
            }
        } catch (SQLException e)
        {
        }
        return appointments;
    }

    public static Date convertDate(String date)
    {
        String[] Date = date.split("-");
        return new Date(Integer.parseInt(Date[0]) - 1900,Integer.parseInt(Date[1]) - 1,Integer.parseInt(Date[2]));
    }
}
