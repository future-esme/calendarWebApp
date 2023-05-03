package utm.tmps.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utm.tmps.domain.enumeration.Month;
import utm.tmps.service.CalendarFacadeService;
import utm.tmps.service.dto.CalendarDTO;

@RestController
@RequestMapping("/api/calendar")
public class CalendarResource {

    private final Logger log = LoggerFactory.getLogger(CalendarResource.class);

    private final CalendarFacadeService calendarFacadeService;

    public CalendarResource(CalendarFacadeService calendarFacadeService) {
        this.calendarFacadeService = calendarFacadeService;
    }

    @GetMapping
    public ResponseEntity<CalendarDTO> getCalendarForCurrentUser(@RequestParam("month") Month month, @RequestParam("year") Integer year) {
        log.debug("REST request ti get calendar for current user");
        return ResponseEntity.ok(calendarFacadeService.getCalendarForCurrentUser(month, year));
    }
}
