package com.redenergy.entity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Stream;
import com.redenergy.exception.InValidDataException;
import com.redenergy.exception.MeterReadException;

/**
 * 
 * @author Nolan.Tellis
 * 
 * @see MeterReaderBuilder for parsing data
 * 
 *      This Class is implementation of {@link SimpleNem12Parser} which is a
 *      contract which needdd to be implemented.
 *      The methods throws runtime exceptions for invalid file or any other
 *      errors as
 *      <br>
 *      it needs to agree with the contract which does not throw an exception
 */
public class SimpleNem12ParserImpl implements SimpleNem12Parser
{
    private MeterReaderBuilder meterReaderBuilder = new MeterReaderBuilder();

    public SimpleNem12ParserImpl()
    {
        super();
    }

    /**
     * 
     * @param meterReaderBuilder
     * @see MeterReaderBuilder
     *      Generating this constructor for future flexibility to provide any
     *      type of
     *      builder to read meter in different format.
     */
    public SimpleNem12ParserImpl(MeterReaderBuilder meterReaderBuilder)
    {
        super();
        this.meterReaderBuilder = meterReaderBuilder;
    }

    @Override
    public Collection<MeterRead> parseSimpleNem12(File simpleNem12File)
    {
        if (!simpleNem12File.exists())
        {
            // throwing a runtime exception as contract needs to be kept with
            // thirdparty.
            throw new InValidDataException("File Does not exists");
        }
        Path path = Paths.get(simpleNem12File.getAbsolutePath());
        try (Stream<String> lines = Files.lines(path))
        {
            // In Case there are invalid records in the file then a custom
            // exception is thrown.
            lines.forEach(dataPoint -> {
                try
                {
                    meterReaderBuilder.accept(dataPoint);
                }
                catch (MeterReadException e)
                {
                    throw new InValidDataException("Issue while Reading specific meter record");
                }
            });
        }
        catch (IOException e)
        {
            throw new InValidDataException("Issue while Reading file with meter record");
        }
        return meterReaderBuilder.getMeterReadList();
    }
}
