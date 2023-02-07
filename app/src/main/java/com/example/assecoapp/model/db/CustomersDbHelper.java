package com.example.assecoapp.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.assecoapp.model.item.Customer;
import com.example.assecoapp.model.item.CustomerClasisfication;

import java.util.ArrayList;

public class CustomersDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "customers.db";
    private static final String TAG = "CustomersDbHelper";

    /**
     * Table customer
     */
    private static final String TABLE_NAME_CUSTOMER = "customer";
    private static final String COLUMN_CUSTOMER_ID = "customer_id";
    private static final String COLUMN_CLASSIFICATION_ID = "classification_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NIP = "nip";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_DATE_TIME = "date_time";
    private static final String COLUMN_IS_DELETED = "id_deleted";

    /**
     * Table classification
     */
    private static final String TABLE_NAME_CLASSIFICATION = "classification";
    private static final String COLUMN_CLASS_ID = "classification_id";
    private static final String COLUMN_CLASS_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";

    public CustomersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableCustomer = "CREATE TABLE " + TABLE_NAME_CUSTOMER + "(" +
                COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CLASSIFICATION_ID + " INTEGER," +
                COLUMN_NAME + " TEXT," +
                COLUMN_NIP + " TEXT," +
                COLUMN_CITY + " TEXT," +
                COLUMN_DATE_TIME + " DATETIME," +
                COLUMN_IS_DELETED + " INTEGER" +
                ")";
        db.execSQL(createTableCustomer);

        String createTableClassification = "CREATE TABLE " + TABLE_NAME_CLASSIFICATION + "(" +
                COLUMN_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CLASS_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT" +
                ")";
        db.execSQL(createTableClassification);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CLASSIFICATION);
        onCreate(db);
    }

    public void addCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, customer.getCustomerName());
        cv.put(COLUMN_CLASSIFICATION_ID, customer.getClassificationId()+1);
        cv.put(COLUMN_NIP, customer.getCustomerNIP());
        cv.put(COLUMN_CITY, customer.getCustomerCity());
        cv.put(COLUMN_DATE_TIME, customer.getDateTime());
        cv.put(COLUMN_IS_DELETED, 0);
        long result = db.insert(TABLE_NAME_CUSTOMER, null, cv);
        if(result == -1)
            Log.i(TAG, "Failed addCustomer!");
        else
            Log.i(TAG, "Success addCustomer!");
    }

    public void addCustomerClassification(CustomerClasisfication customerClasisfication) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CLASS_NAME, customerClasisfication.getClassificationName());
        cv.put(COLUMN_DESCRIPTION, customerClasisfication.getClassificationDescription());
        long result = db.insert(TABLE_NAME_CLASSIFICATION, null, cv);
        if(result == -1)
            Log.i(TAG, "Failed addCustomerClassification!");
        else
            Log.i(TAG, "Success addCustomerClassification!");
    }

    private Cursor readCustomersData() {
        String query = "SELECT * FROM " + TABLE_NAME_CUSTOMER;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public ArrayList<Customer> readCustomersArray() {
        Cursor cursor = readCustomersData();
        if(cursor.getCount() == 0) {
            return new ArrayList<>();
        } else {
            ArrayList<Customer> tempArray = new ArrayList<>();
            Customer tempCustomer;
            while (cursor.moveToNext()) {
                tempCustomer = new Customer();
                tempCustomer.setCustomerId(Long.parseLong(cursor.getString(0))-1);
                tempCustomer.setClassificationId(Long.parseLong(cursor.getString(1))-1);
                tempCustomer.setCustomerName(cursor.getString(2));
                tempCustomer.setCustomerNIP(cursor.getString(3));
                tempCustomer.setCustomerCity(cursor.getString(4));
                tempCustomer.setDateTime(cursor.getString(5));
                tempCustomer.setDeleted(cursor.getInt(6) != 0);
                tempArray.add(tempCustomer);
            }
            return tempArray;
        }
    }

    private Cursor readCustomersClassificationData() {
        String query = "SELECT * FROM " + TABLE_NAME_CLASSIFICATION;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public ArrayList<CustomerClasisfication> readCustomersClassificationArray() {
        Cursor cursor = readCustomersClassificationData();
        if(cursor.getCount() == 0) {
            return new ArrayList<>();
        } else {
            ArrayList<CustomerClasisfication> tempArray = new ArrayList<>();
            CustomerClasisfication tempCustomer;
            while (cursor.moveToNext()) {
                tempCustomer = new CustomerClasisfication();
                tempCustomer.setClassificationId(Long.parseLong(cursor.getString(0))-1);
                tempCustomer.setClassificationName(cursor.getString(1));
                tempCustomer.setClassificationDescription(cursor.getString(2));
                tempArray.add(tempCustomer);
            }
            cursor.close();
            return tempArray;
        }
    }

    public void updateCustomers(Long customerID, String dateTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE_TIME, dateTime);
        cv.put(COLUMN_IS_DELETED, 1);
        long result = db.update(TABLE_NAME_CUSTOMER, cv, "customer_id=?", new String[]{String.valueOf(customerID+1)});
        if(result == -1)
            Log.i(TAG, "Failed updateCustomers!");
        else
            Log.i(TAG, "Success updateCustomers!");
    }
}
