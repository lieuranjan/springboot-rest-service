package in.gogoi.api.data.service.utils;

import lombok.Data;

/**
 * Pojo for Json data
 */
@Data
public class JsonDataPojo {

     private long linenum;
     private String date;
     private String agency;
     private String mediaSource;
     private String campaign;
     private String impressions;
     private String clicks;
     private String ctr;
     private String installs;
     private String conversionRate;
     private String sessions;
     private String loyalUsers;
     private String loyalUsersInstalls;
     private String totalCost;
     private String aveCpi;
     private String token;
     private String fromDate;
     private String toDate;
     private String timezone;
     private String createDatetime;
}
