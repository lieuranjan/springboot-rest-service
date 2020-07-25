package in.gogoi.api.data.service.utils;

import lombok.Data;

import java.util.List;

/**
 * Output format of json data
 */
@Data
public class DataResult {

    private String id;
    private int limit;
    private long total;
    private List<JsonDataPojo> records;
}
