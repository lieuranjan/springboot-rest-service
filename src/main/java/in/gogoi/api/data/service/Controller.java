package in.gogoi.api.data.service;

import in.gogoi.api.data.service.utils.ApiResponse;
import in.gogoi.api.data.service.utils.DataResult;
import in.gogoi.api.data.service.utils.DataUtils;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.UUID;

@RestController
@RequestMapping("/api/mock/data")
public class Controller {

    @Autowired
    private DataUtils dataUtils;

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    public String getAll() throws Exception {
        val token = UUID.randomUUID().toString();
        return dataUtils.getLines(1, token, LocalDate.now().minusDays(1).toString(), LocalDate.now().toString(), Calendar.getInstance().getTimeZone().getID());
    }

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/{x}", method = RequestMethod.GET)
    public String getGivenCount(@PathVariable("x") int x) throws Exception {
        int multiple = x;
        String token = UUID.randomUUID().toString();
        return dataUtils.getLines(multiple, token, LocalDate.now().minusDays(1).toString(), LocalDate.now().toString(), Calendar.getInstance().getTimeZone().getID());
    }

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v2", method = RequestMethod.GET)
    public String getResultWithRequetsParam(
            @RequestParam(value = "token", defaultValue = "") String token,
            @RequestParam(value = "from", defaultValue = "") String fromDate,
            @RequestParam(value = "to", defaultValue = "") String toDate,
            @RequestParam(value = "timezone", defaultValue = "") String timeZone,
            @RequestParam(value = "x", defaultValue = "2") int multiple) throws Exception {
        if (token.isEmpty()) {
            token = UUID.randomUUID().toString();
        }
        if (fromDate.isEmpty()) {
            fromDate = LocalDate.now().minusDays(1).toString();
        }
        if (toDate.isEmpty()) {
            toDate = LocalDate.now().toString();
        }
        if (timeZone.isEmpty()) {
            timeZone = Calendar.getInstance().getTimeZone().getID();
        }

        return dataUtils.getLines(multiple, token, fromDate, toDate, timeZone);
    }


    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v3", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public DataResult getResultWithRequetsParam2(
            @RequestParam(value = "limit", defaultValue = "100") int limit,
            @RequestParam(value = "offset", defaultValue = "1") int offset,
            @RequestParam(value = "total", defaultValue = "350") int total) throws Exception {

        DataResult result = dataUtils.jsonLines(limit, total, offset, UUID.randomUUID().toString(), LocalDate.now().minusDays(1).toString(), LocalDate.now().toString(), Calendar.getInstance().getTimeZone().getID());
        return result;
    }

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v4", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getResultWithRequetsParam3(
            @RequestParam(value = "limit", defaultValue = "100") int limit,
            @RequestParam(value = "offset", defaultValue = "1") int offset,
            @RequestParam(value = "total", defaultValue = "350") int total) throws Exception {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus("success");
        DataResult result = dataUtils.jsonLines(limit, total, offset, UUID.randomUUID().toString(), LocalDate.now().minusDays(1).toString(), LocalDate.now().toString(), Calendar.getInstance().getTimeZone().getID());
        apiResponse.setResult(result);
        return apiResponse;
    }

}
