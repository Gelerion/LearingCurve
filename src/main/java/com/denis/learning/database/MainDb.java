package com.denis.learning.database;

import com.denis.learning.database.controller.RecordsFile;
import com.denis.learning.database.repository.RecordReader;
import com.denis.learning.database.repository.RecordWriter;
import com.denis.learning.database.repository.RecordsFileException;

import java.io.IOException;
import java.time.LocalDate;

public class MainDb {
    public static void main(String[] args) throws IOException, RecordsFileException, ClassNotFoundException {
        RecordsFile dbManager = new RecordsFile("/home/denis-shuvalov/Documents/Other/Data/CustomDb/database.rfc", 2);

        RecordWriter writer = new RecordWriter("user");
        writer.writeObject("Denis Shuvalov");
        dbManager.insertRecord(writer);

        writer = new RecordWriter("lastAccess");
        writer.writeObject(LocalDate.now());
        dbManager.insertRecord(writer);

        writer = new RecordWriter("wife");
        writer.writeObject("Elena Moroz");
        dbManager.insertRecord(writer);

        //RecordsFile dbManager = new RecordsFile("/home/denis-shuvalov/Documents/Other/Data/CustomDb/database.rfc", "rw");

        RecordReader recordReader = dbManager.readRecord("user");
        String user = (String) recordReader.readObject();

        RecordReader lastAccessReader = dbManager.readRecord("lastAccess");
        LocalDate date = (LocalDate) lastAccessReader.readObject();

        RecordReader wifeReader = dbManager.readRecord("wife");
        String wife = (String) wifeReader.readObject();

        System.out.println("user = " + user);
        System.out.println("lastAccess = " + date);
        System.out.println("wife = " + wife);
    }
}
