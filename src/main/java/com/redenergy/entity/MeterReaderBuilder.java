package com.redenergy.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.redenergy.exception.MeterReadException;

public class MeterReaderBuilder
{
    private List<MeterRead>          meterReadList = new ArrayList<>();
    private RecordType               currentRecordType;
    private MeterRead                currentMeterRead;
    private static DateTimeFormatter formatter     = DateTimeFormatter.ofPattern("yyyyMMdd");

    public void accept(String dataPoint) throws MeterReadException
    {
        String[] dataPoints = dataPoint.split(",");
        currentRecordType = RecordType.getRecordType(Integer.parseInt(dataPoints[0]));
        switch (currentRecordType)
        {
            case START_RECORD:
                // ignoring state as file is considered to be valid
//                System.out.println("Started Reading Meter File");
                break;
            case END_RECORD:
                //ignoring state as file is considered to be valid
//                System.out.println("Finished Reading Meter File");
                break;
            case START_METER_RECORD:
                if (dataPoints[1].length() != 10)
                {
                    throw new MeterReadException("Meter Id " + dataPoints[1] + " is not of valid length");
                }
                currentMeterRead = new MeterRead(dataPoints[1], EnergyUnit.valueOf(dataPoints[2]));
                meterReadList.add(currentMeterRead);
                break;
            case METER_READ_RECORD:
                LocalDate localDate = LocalDate.parse(dataPoints[1], formatter);
                currentMeterRead.appendVolume(localDate,
                        new MeterVolume(BigDecimal.valueOf(Double.valueOf(dataPoints[2])), Quality.valueOf(dataPoints[3])));
                break;
                
             default :
                 throw new MeterReadException("Invalid Data in Meter Reader File");
        }
    }

    public Collection<MeterRead> getMeterReadList()
    {
        return meterReadList;
    }

    public RecordType getCurrentRecordType()
    {
        return currentRecordType;
    }
}
