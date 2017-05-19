package com.redenergy.entity;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;

import org.junit.Test;

import com.redenergy.exception.InValidDataException;

public class SimpleNem12ParserTest
{

    @Test(expected = InValidDataException.class)
    public void testParseFileExceptionReturnNullList()
    {
        File nonExistFile = new File(System.getProperty("user.dir") + "/src/test/resources/SimpleNem12NotExists.csv");
        SimpleNem12Parser simpleNem12Parser = new SimpleNem12ParserImpl();

        simpleNem12Parser.parseSimpleNem12(nonExistFile);

    }
    
    @Test
    public void testSampleFileProvided()
    {
        File nonExistFile = new File(System.getProperty("user.dir") + "/src/test/resources/SimpleNem12.csv");
        SimpleNem12Parser simpleNem12Parser = new SimpleNem12ParserImpl();

        Collection<MeterRead> readList=simpleNem12Parser.parseSimpleNem12(nonExistFile);
        
        MeterRead read6123456789 = readList.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();
        
        assertEquals(BigDecimal.valueOf(-36.84), read6123456789.getTotalVolume());

    }

    @Test
    public void testSampleFileProvided2()
    {
        File nonExistFile = new File(System.getProperty("user.dir") + "/src/test/resources/SimpleNem12.csv");
        SimpleNem12Parser simpleNem12Parser = new SimpleNem12ParserImpl();

        Collection<MeterRead> readList=simpleNem12Parser.parseSimpleNem12(nonExistFile);
        
        MeterRead read6987654321 = readList.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst().get();
        
        assertEquals(BigDecimal.valueOf(14.33), read6987654321.getTotalVolume());

    }

}
