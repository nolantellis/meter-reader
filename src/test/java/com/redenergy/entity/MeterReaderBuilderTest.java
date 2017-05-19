package com.redenergy.entity;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.redenergy.exception.MeterReadException;

public class MeterReaderBuilderTest
{

    MeterReaderBuilder builder;
    
    @Before
    public void setUp()
    {
        builder=new MeterReaderBuilder();
    }
    
    @Test
    public void testInitialBuilderState()
    {
        assertNull(builder.getCurrentRecordType());
    }
    
    @Test
    public void testGetStartState() throws MeterReadException
    {
        builder.accept("100");
        assertEquals(RecordType.START_RECORD, builder.getCurrentRecordType());
        builder.accept("200,1111222233,KWH");
        assertEquals(RecordType.START_METER_RECORD, builder.getCurrentRecordType());
    }
    
    @Test(expected=MeterReadException.class)
    public void testGetStartMeterInvalidId() throws MeterReadException
    {
        builder.accept("100");
        assertEquals(RecordType.START_RECORD, builder.getCurrentRecordType());
        builder.accept("200,11,KWH");
        assertEquals(RecordType.START_METER_RECORD, builder.getCurrentRecordType());
    }
    
    @Test
    public void testGetStartMeter() throws MeterReadException
    {
        builder.accept("100");
        assertEquals(RecordType.START_RECORD, builder.getCurrentRecordType());
        builder.accept("200,1111111111,KWH");
        assertEquals(RecordType.START_METER_RECORD, builder.getCurrentRecordType());
        MeterRead read=new MeterRead("1111111111", EnergyUnit.KWH);
        assertTrue(builder.getMeterReadList().contains(read));
        
    }
    
    @Test
    public void testSingleMeterRead() throws MeterReadException
    {
        builder.accept("100");
        assertEquals(RecordType.START_RECORD, builder.getCurrentRecordType());
        builder.accept("200,1111111111,KWH");
        assertEquals(RecordType.START_METER_RECORD, builder.getCurrentRecordType());
        MeterRead read=new MeterRead("1111111111", EnergyUnit.KWH);
        assertTrue(builder.getMeterReadList().contains(read));
        builder.accept("300,20161115,32.0,A");
        List<MeterRead> readerList=(List<MeterRead>) builder.getMeterReadList();
        MeterRead read2=readerList.get(0);
        MeterVolume volume=read2.getMeterVolume(LocalDate.of(2016, 11, 15));
        MeterVolume expectedVolume=new MeterVolume(BigDecimal.valueOf(Double.valueOf("32.0")), Quality.A);
        assertEquals(read2,read);
        assertEquals(volume.getVolume(),expectedVolume.getVolume());
        assertEquals(volume.getQuality(),expectedVolume.getQuality());
        
    }

}
