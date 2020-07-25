package in.gogoi.api.data.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
public class DataUtils {

    public static final String SAMPLE_DATA_PATH = ApplicationContext.CLASSPATH_URL_PREFIX + "sample_data.txt";
    @Autowired
    private ApplicationContext context;
    public final String DELIMITER = ",";

    /**
     * Preparing delimited data samples
     * @param multiple
     * @param token
     * @param fromDate
     * @param toDate
     * @param timezone
     * @return
     * @throws Exception
     */
    public String getLines(int multiple, String token, String fromDate, String toDate, String timezone) throws Exception {
        Resource resource = context.getResource(SAMPLE_DATA_PATH);
        StringBuilder responseList = new StringBuilder();
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            List<String> rawData = buffer.lines().collect(Collectors.toList());
            String header = rawData.remove(0);
            responseList
                    .append("linenum")
                    .append(DELIMITER)
                    .append(header)
                    .append(DELIMITER)
                    .append("token")
                    .append(DELIMITER)
                    .append("fromdate")
                    .append(DELIMITER)
                    .append("todate")
                    .append(DELIMITER)
                    .append("timezone")
                    .append(DELIMITER)
                    .append("createdatetime");
            int i = 1;
            long counter = 1;
            while (i <= multiple) {
                for (String line : rawData) {
                    responseList
                            .append("\n")
                            .append(counter++)
                            .append(DELIMITER)
                            .append(line)
                            .append(DELIMITER)
                            .append(token)
                            .append(DELIMITER)
                            .append(fromDate)
                            .append(DELIMITER)
                            .append(toDate)
                            .append(DELIMITER)
                            .append(timezone)
                            .append(DELIMITER)
                            .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")));
                    TimeUnit.MILLISECONDS.sleep(3);
                }
                TimeUnit.SECONDS.sleep(1);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error while generation data," + e);
        }

        return responseList.toString();
    }

    /**
     * Preparing json data samples
     * @param limit
     * @param total
     * @param token
     * @param fromDate
     * @param toDate
     * @param timezone
     * @return
     * @throws Exception
     */
    public DataResult jsonLines(int limit, int total, int offset, String token, String fromDate, String toDate, String timezone) throws Exception {
        Resource resource = context.getResource(SAMPLE_DATA_PATH);
        DataResult result = new DataResult();
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            List<String> rawData = buffer.lines().collect(Collectors.toList());
            rawData.remove(0);
            long counter = offset;
            int offsetCounter = 1;
            List<JsonDataPojo> records = new ArrayList<>();
            result.setId(UUID.randomUUID().toString());
            result.setLimit(limit);
            result.setTotal(total);
            boolean keepFlow = true;
            JsonDataPojo pojo = null;
            while (keepFlow) {
                for (String line : rawData) {
                    pojo = new JsonDataPojo();
                    pojo.setLinenum(counter);
                    //data line
                    String[] lineParts = line.split(",", -1);
                    pojo.setDate(lineParts[0]);
                    pojo.setAgency(lineParts[1]);
                    pojo.setMediaSource(lineParts[2]);
                    pojo.setCampaign(lineParts[3]);
                    pojo.setImpressions(lineParts[4]);
                    pojo.setClicks(lineParts[5]);
                    pojo.setCtr(lineParts[6]);
                    pojo.setInstalls(lineParts[7]);
                    pojo.setConversionRate(lineParts[8]);
                    pojo.setSessions(lineParts[9]);
                    pojo.setLoyalUsers(lineParts[10]);
                    pojo.setLoyalUsersInstalls(lineParts[11]);
                    pojo.setTotalCost(lineParts[12]);
                    pojo.setAveCpi(lineParts[13]);
                    pojo.setToken(token);
                    pojo.setFromDate(fromDate);
                    pojo.setToDate(toDate);
                    pojo.setTimezone(timezone);
                    pojo.setCreateDatetime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")));
                    records.add(pojo);
                    if (offsetCounter == limit || counter == total) {
                        keepFlow = false;
                        break;
                    }
                    counter++;
                    offsetCounter++;
                    TimeUnit.MILLISECONDS.sleep(2);
                }
                TimeUnit.SECONDS.sleep(1);
            }
            result.setRecords(records);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error while generation data," + e);
        }
        //convert to json String
        return result;
    }
}
