import exception.ImporterException;
import format.SensorValues;
import model.FailedMeasure;
import model.Measure;
import model.WeatherStation;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ltisseyre on 22/01/17.
 */
public class FileImporter {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String PROPERTIES_FILE = "mshpoa-importer.properties";
    private static final String COMMENT_FIELD = "comment";
    private static final String SENSOR_FORMAT = "sensor.format.";
    private static final String DATE_PATTERN = "date.format";
    private static final String FORMAT_DELIMITER = "\\|";
    private static final String SEPARATOR = "field.separator";
    private static final int WEATHER_STATION_HEADING = 2;

    //String represents the code used for the sensor type. e.g: T, P, H
    //LinkedList<SensorValues> represents a list of value type for a sensor. e.g: UNIT, DATE, VALUE
    private HashMap<String, LinkedList<SensorValues>> availableSensorFormats = new HashMap<>();

    private String commentString = "#";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private String fieldSeparator = ";";

    /**
     * Load configuration from "PROPERTIES_FILE"
     * @throws IOException
     * @throws ImporterException
     */
    public FileImporter() throws IOException, ImporterException {

        Properties properties = new Properties();

        //open PROPERTIES_FILE
        try(InputStream is = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)){
            if(is == null){
                throw new ImporterException("Properties file path is invalid : " + PROPERTIES_FILE);
            }
            properties.load(is);
            properties.entrySet().forEach(this::importProperty);
        }
    }

    /**
     * process file
     * @param path
     * @throws IOException
     */
    public List<WeatherStation> importFile(String path) {

        List<WeatherStation> stations = null;

        //load all line from file as stream
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            //remove string after "commentString"
            //skip blank line
            //collect result as List
            List<String> rows = stream
                    .map(s-> s.contains(commentString) ? s.substring(0, s.indexOf(commentString)) : s)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());

            stations = consumeWeatherStation(rows);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("An exception occurs while processing the file " + path);
        }

        return stations;
    }

    /**
     * This method consume weather station
     * @param rows
     * @return
     */
    private List<WeatherStation> consumeWeatherStation(List<String> rows) throws ImporterException {
        List<WeatherStation> weatherStations = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            WeatherStation station = new WeatherStation();

            String[] heading = rows.get(i).split(fieldSeparator);
            if(heading.length == WEATHER_STATION_HEADING){
                //get header
                station.setName(heading[0].trim());
                int measureSize = Integer.parseInt(heading[1].trim());

                //set cursor to weather station
                int weatherStationCursor = i;

                //loop on measures
                for(int j=weatherStationCursor+1; j<weatherStationCursor+1+measureSize && j<rows.size(); j++){
                    consumeMeasures(station, rows.get(j));
                    i++;
                }
            } else {
                throw new ImporterException("The length of WEATHER_STATION_HEADING is invalid.");
            }

            weatherStations.add(station);
        }
        return weatherStations;
    }

    /**
     * This method add Measure or FailedMeasure to weatherStation
     * @param weatherStation
     */
    private void consumeMeasures(WeatherStation weatherStation, String row){
        String[] data = row.split(fieldSeparator);

        //there is always a value for data[0] because the row can't be null or empty
        String code = data[0];
        LinkedList<SensorValues> sensorValues = availableSensorFormats.get(code);

        if(sensorValues != null){
            //verify the count for a measure
            if(data.length-1 == sensorValues.size()){
                //start i to 1 to skip sensor code
                int i =1;
                //try to create measure
                try{
                    Measure measure = new Measure(code);

                    for (SensorValues sensorValue : sensorValues) {
                        switch (sensorValue){
                            case DATE:
                                measure.setDate(dateFormat.parse(data[i]));
                                break;
                            case UNIT:
                                measure.setUnit(data[i]);
                                break;
                            case VALUE:
                                measure.setValue(Double.parseDouble(data[i]));
                                break;
                        }
                        i++;
                    }
                    weatherStation.addMeasure(measure);

                } catch(Exception e){
                    weatherStation.addFailedMeasure(new FailedMeasure(row, sensorValues.toString(), "Unable to parse measure. Data type may be incorrect."));
                    LOGGER.debug(e.getMessage());
                }

            } else {
                weatherStation.addFailedMeasure(new FailedMeasure(row, sensorValues.toString(), "Incorrect parameter number."));
            }
        } else {
            weatherStation.addFailedMeasure(new FailedMeasure(row, "", "Unrecognized sensor type. " + code));
        }
    }

    /**
     * This method set value of "commentString"
     * and fill "availableSensorFormats" array
     * @param entry an entry read from .properties file
     */
    private void importProperty(Map.Entry<Object, Object> entry) {
        String key = (String) entry.getKey();
        String value = (String)entry.getValue();

        if(key.equals(COMMENT_FIELD)){
            commentString = value;
        } else if(key.startsWith(SENSOR_FORMAT)){
            String sensorType = key.substring(SENSOR_FORMAT.length());
            LinkedList<SensorValues> sensorValues = new LinkedList<>();

            for (String format : value.split(FORMAT_DELIMITER)) {
                if(EnumUtils.isValidEnum(SensorValues.class, format)){
                    sensorValues.add(EnumUtils.getEnum(SensorValues.class, format));
                } else {
                    LOGGER.warn("Key " + key + ": Invalid enum " + format);
                }
            }

            availableSensorFormats.put(sensorType, sensorValues);
        } else if (key.equals(DATE_PATTERN)) {
            dateFormat = new SimpleDateFormat(value);
        } else if (key.equals(SEPARATOR)){
            fieldSeparator = value;
        } else {
            LOGGER.warn("Unhandled property " + key);
        }
    }
}
