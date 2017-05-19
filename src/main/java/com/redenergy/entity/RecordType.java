package com.redenergy.entity;

import java.util.stream.Stream;

public enum RecordType
{
    START_RECORD(100), END_RECORD(900), START_METER_RECORD(200), METER_READ_RECORD(300);
    int num;
    private RecordType(int num)
    {
        this.num = num;
    }
    public static RecordType getRecordType(int num)
    {
        return Stream.of(RecordType.values()).filter(e -> e.num == num).findFirst().orElse(null);
    }
}
