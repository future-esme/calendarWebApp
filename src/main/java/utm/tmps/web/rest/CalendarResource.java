package utm.tmps.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utm.tmps.domain.enumeration.MONTH;
import utm.tmps.service.CalendarService;
import utm.tmps.service.dto.CalendarDTO;

@RestController
@RequestMapping("/api/calendar")
public class CalendarResource {
    private final Logger log = LoggerFactory.getLogger(CalendarResource.class);

    private final CalendarService calendarService;

    public CalendarResource(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    public ResponseEntity<CalendarDTO> getCalendarForCurrentUser(
        @RequestParam("month") MONTH month,
        @RequestParam("year") Integer year) {
        return ResponseEntity.ok(null);
    }
}
