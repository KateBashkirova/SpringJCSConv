package ics;

import ics.icsClasses.EventStatus;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;

import static ics.icsClasses.FormatHelper.*;
import static ics.icsClasses.Reminder.*;

/**
 * The main controller
 */
@Controller
public class FormProcessingController {

    /**
     * Method displays a page with a form
     * @return page with a form
     */
    @RequestMapping(value = "/createMeeting", method = RequestMethod.GET)
    public ModelAndView showMeetingForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meeting");
        return modelAndView;
    }

    /**
     * Method processes information about event and generates ics file as a result
     * @param meeting class with event parameters
     * @return ics file
     */
    @RequestMapping(value = "/createMeeting", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity createMeeting(@RequestBody Meeting meeting) {
        ArrayList<String> meetingInfo = new ArrayList<>();
        meetingInfo.add("BEGIN:VCALENDAR\r\n" +
                "VERSION:2.0\r\n" +
                "PRODID:ktbrv\r\n" +
                "CALSCALE:GREGORIAN\r\n" +
                "BEGIN:VTIMEZONE\r\n" +
                "TZID=" + getTimezoneId(meeting) + "\r\n" +
                "X-LIC-LOCATION:" + getTimezoneId(meeting) + "\r\n" +
                "BEGIN:STANDARD\r\n" +
                "TZOFFSETFROM:" + getTimezoneOffSet(meeting) + "\r\n" +
                "TZOFFSETTO:" + getTimezoneOffSet(meeting) + "\r\n" +
                "DTSTART:19700101T000000\r\n" +
                "END:STANDARD\r\n" +
                "END:VTIMEZONE\r\n" +
                "BEGIN:VEVENT\r\n" +
                "DTSTAMP:" + formatDate(DATE_T_TIME_Z, new Date()) + "\r\n" +
                "DTSTART;TZID=" + getStartDateString(meeting) + "\r\n" +
                "DTEND;TZID=" + getEndDateString(meeting) + "\r\n" +
                "SUMMARY:" + meeting.getSummary() + "\r\n");
        //если у мероприятия есть описание
        if (!meeting.getDescription().trim().isEmpty()) {
            meetingInfo.add("DESCRIPTION:" + meeting.getDescription() + "\r\n");
        }
        meetingInfo.add("LOCATION:" + meeting.getLocation() + "\r\n");

        //добавить статус мероприятия
        EventStatus eventStatus = EventStatus.valueOf(
                meeting.getEventStatus().replace(" ", "_").toUpperCase()
        );
        meetingInfo.add(eventStatus.getConfig());

        //добавить напоминание о встрече (если нужно)
        if (!getReminderTime(meeting).equalsIgnoreCase("null")) {
            meetingInfo.add("BEGIN:VALARM\r\n" + "ACTION:DISPLAY\r\n");
            meetingInfo.add("TRIGGER:" + getReminderTime(meeting));
            meetingInfo.add("END:VALARM\r\n");
        }
        meetingInfo.add("END:VEVENT\r\n" + "END:VCALENDAR");

        //преобразовать ArrayList в String
        String completeMeetingInfo = String.join("", meetingInfo);

        //сформировать и вернуть файл
        ByteArrayResource bt = new ByteArrayResource(completeMeetingInfo.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/ics"));
        headers.add("Content-Disposition", "attachment;filename=" + meeting.getSummary() + ".ics");
        return new ResponseEntity(bt, headers, HttpStatus.OK);
    }
}


