package in.gogoi.api.data.service.utils;

import lombok.Data;

/**
 * Pojo for rest object
 */
@Data
public class ApiResponse {
    private String status;
    private DataResult result;
}

